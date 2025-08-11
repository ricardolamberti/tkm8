package pss.core.win;

import java.awt.Color;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFFormulaEvaluator;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;

import pss.JPath;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObject;
import pss.core.services.records.JRecord;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.win.totalizer.JTotalizer;
import pss.core.win.totalizer.JTotalizer.Properties;
import pss.core.winUI.lists.JColumnaLista;
import pss.core.winUI.lists.JGrupoColumnaLista;
import pss.core.winUI.lists.JWinList;

public class JWinsExcel {

	JWins wins = null;
	XSSFCellStyle stringCellStyle = null;
	XSSFCellStyle datetime = null;
	XSSFCellStyle numberCellStyle = null;
	XSSFCellStyle floatCellStyle = null;
	XSSFCellStyle currencyCellStyle = null;
	XSSFFont cc = null;
	JMap<String, XSSFCellStyle> styleMap = JCollectionFactory.createMap();

	public JWins getWins() {
		return wins;
	}

	public void setWins(JWins wins) {
		this.wins = wins;
	}

	public JWinsExcel() {

	}

	public String toExcel() throws Exception {

		int maxrows = 1000;

		SXSSFWorkbook workbook = new SXSSFWorkbook();

		CreationHelper createHelper = workbook.getCreationHelper();
		SXSSFSheet sheet = workbook.createSheet("Data");
		sheet.trackAllColumnsForAutoSizing();
		sheet.setRandomAccessWindowSize(maxrows);

		Font headerFont = createCellFont(workbook);
		headerFont.setBold(true);
		headerFont.setColor(IndexedColors.WHITE.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		headerCellStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		JWinList collist = new JWinList(wins); // WinList fantasma para poder
		// obtener las
		// columnas de details
		collist.setForExport(true);
		wins.ConfigurarColumnasLista(collist);
		wins.createTotalizer(collist);

		JTotalizer tot = collist.getTotalizer();

		JList<JColumnaLista> cl = collist.GetColumnasLista();
		// JMap<String, String> formulas = JCollectionFactory.createMap();

		JColumnaLista c = cl.getElementAt(0);
		c.getGrupo();

		int headers = 0;
		Row headerGroupRow = null;
		int gfrom[] = null;
		int gto[] = null;
		int gidx = 0;

		JList<JGrupoColumnaLista> group = collist.GetGrupoColumnasLista();
		if (group != null) {
			gfrom = new int[group.size() + 1];
			gto = new int[group.size() + 1];
			headerGroupRow = sheet.createRow(headers);
			headers = 1;
			gfrom[gidx] = 0;
		}
		String currGroup = "";

		// Create a Row
		Row headerRow = sheet.createRow(headers);
		int icell = 0;
		// Create cells
		for (int i = 0; i < cl.size(); i++) {

			JGrupoColumnaLista g = cl.getElementAt(i).getGrupo();
			String titulo = cl.getElementAt(i).GetTitulo();
			String campo = cl.getElementAt(i).GetCampo();
			if (titulo.equals(""))
				continue;
			if (campo.equals(""))
				continue;

			if (headerGroupRow != null) {
				String tit = "";
				if (g != null) {
					tit = g.getTitulo();
				}
				Cell cell = headerGroupRow.createCell(icell);
				cell.setCellStyle(headerCellStyle);

				if (currGroup.equals(tit) == false) {
					gto[gidx] = icell - 1;
					gidx++;
					gfrom[gidx] = icell;
					currGroup = g.getTitulo();
					cell.setCellValue(currGroup);
				}

			}

			Cell cell = headerRow.createCell(icell++);

			cell.setCellValue(titulo);
			cell.setCellStyle(headerCellStyle);

		}

		if (headerGroupRow != null) {
			gto[gidx] = icell - 1;
			for (int i = 0; i < group.size() + 1; i++) {
				try {
					sheet.addMergedRegion(new CellRangeAddress(0, 0, gfrom[i], gto[i]));
				} catch (Exception eee) {
				}
			}
		}

		JRecord rec = null;
		wins.readAll();
		wins.firstRecord();
		int rownum = 1 + headers;
		while (wins.nextRecord()) {

			JWin win = wins.getRecord();
			rec = (JRecord) wins.getRecord().GetBaseDato();
			Row row = sheet.createRow(rownum++);

			if (rownum > maxrows)
				sheet.untrackAllColumnsForAutoSizing();

			icell = 0;
			for (int i = 0; i < cl.size(); i++) {
				String campo = cl.getElementAt(i).GetCampo();
				String titulo = cl.getElementAt(i).GetTitulo();
				if (titulo.equals(""))
					continue;
				if (campo.equals(""))
					continue;
				Cell cell = row.createCell(icell++);

				JObject<?> obj = rec.getPropDeep(campo);

				XSSFCellStyle cs = setCellStyleAndValue(obj, cell, workbook, createHelper);
				setStyleColor(win, campo, workbook, cs, cell);

			}

			if (wins.hasSubDetail(true)) {
				JWins details = wins.getSubDetails(win, true);
				if (details == null)
					continue;
				JWinList oWinList = new JWinList(details); // WinList fantasma
															// para
				details.ConfigurarColumnasListaInternal(oWinList);
				details.readAll();
				if (!details.nextRecord())
					continue;

				JList<JColumnaLista> cld = oWinList.GetColumnasLista();
				Row headerRowd = sheet.createRow(rownum++);
				int icelld = 0;
				// Create cells
				Cell cell = headerRowd.createCell(icelld++);
				cell.setCellValue("");
				for (int i = 0; i < cld.size(); i++) {
					String titulo = cld.getElementAt(i).GetTitulo();
					String campo = cld.getElementAt(i).GetCampo();
					if (titulo.equals(""))
						continue;
					if (campo.equals(""))
						continue;
					Cell celld = headerRowd.createCell(icelld++);
					celld.setCellValue(titulo);
					celld.setCellStyle(headerCellStyle);
				}

				while (true) {

					JWin wind = details.getRecord();
					JRecord recd = (JRecord) details.getRecord().GetBaseDato();
					Row rowd = sheet.createRow(rownum++);
					icelld = 0;
					Cell celle = rowd.createCell(icelld++);
					celle.setCellValue("");
					for (int i = 0; i < cld.size(); i++) {
						String campo = cld.getElementAt(i).GetCampo();
						String titulo = cld.getElementAt(i).GetTitulo();
						if (titulo.equals(""))
							continue;
						if (campo.equals(""))
							continue;
						Cell celld = rowd.createCell(icelld++);

						JObject<?> obj = recd.getPropDeep(campo);

						XSSFCellStyle cs = setCellStyleAndValue(obj, celld, workbook, createHelper);
						setStyleColor(wind, campo, workbook, cs, celld);

					}
					if (!details.nextRecord())
						break;
				}
			}

			double rest = ((double) rownum) % ((double) maxrows);
			if (rest == 0.0f) {
				sheet.flushRows(maxrows);
			}

		}

		if (tot != null && rec != null) {
			if (tot.hasAny()) {
				CellStyle footerCellStyle = workbook.createCellStyle();
				footerCellStyle.setFont(headerFont);
				footerCellStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
				footerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				Row row = sheet.createRow(rownum);
				// row.setRowStyle(footerCellStyle);
				char cchar = 'A';
				icell = 0;
				for (int i = 0; i < cl.size(); i++) {
					String titulo = cl.getElementAt(i).GetTitulo();
					String campo = cl.getElementAt(i).GetCampo();
					if (titulo.equals(""))
						continue;
					if (campo.equals(""))
						continue;

					Properties prop = tot.getProp(campo);
					footerCellStyle = workbook.createCellStyle();
					footerCellStyle.setFont(headerFont);
					footerCellStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
					footerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					Cell cell = row.createCell(icell);
					cell.setCellStyle(footerCellStyle);
					cell.setCellValue("");
					if (prop != null) {
						if (prop.getOperation().equals(JTotalizer.OPER_VALUE) == false) {

							JObject<?> obj = rec.getProp(campo);
							if (obj.isCurrency() || obj.isFloat())
								footerCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,##0.00"));
							else
								footerCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,##0"));
							cell.setCellStyle(footerCellStyle);
							String oper = prop.getOperation();
							if (oper.equalsIgnoreCase(JTotalizer.OPER_STATICSUM))
								oper = JTotalizer.OPER_SUM;
							if (oper.equalsIgnoreCase(JTotalizer.OPER_CURRSTATICSUM))
								oper = JTotalizer.OPER_SUM;
							if (oper.equalsIgnoreCase(JTotalizer.OPER_CURRSUM))
								oper = JTotalizer.OPER_SUM;
							int inirow = 2;
							if (headerGroupRow != null)
								inirow = 3;
							// PssLogger.logDebug("headerGroupRow: " +
							// (headerGroupRow!=null));
							// PssLogger.logDebug("initrown: " + inirow);
							cell.setCellFormula(oper + "(" + cchar + inirow + ":" + cchar + ((rownum) + "") + ")");
						} else {
							cell.setCellValue(prop.getValue().toString());
						}
					}

					icell++;
					cchar++;

				}

				if (rownum < maxrows) {
					try {
						SXSSFFormulaEvaluator.evaluateAllFormulaCells(workbook,true);
					} catch (Throwable ee) {
					}
				}

			}
		}

		if (rownum < maxrows)
			for (int i = 0; i < cl.size(); i++) {
				sheet.autoSizeColumn(i);
			}

		String file = BizUsuario.getUsr().getCompany() + "/" + BizUsuario.getUsr().GetUsuario() + "." + JTools.getValidFilename(wins.GetTitle()) + ".xlsx";
		// Write the output to a file
		JTools.createDirectories(JPath.PssPathTempFiles(), file);
		FileOutputStream fileOut = new FileOutputStream(JPath.PssPathTempFiles() + "/" + file);
		workbook.write(fileOut);
		fileOut.close();

		// Closing the workbook
		workbook.close();

		return file;

	}

	private XSSFCellStyle setCellStyleAndValue(JObject<?> obj, Cell cell, SXSSFWorkbook workbook, CreationHelper createHelper) throws Exception {
		String type = obj.getObjectType();
		if (obj.isNull()) {
			if (stringCellStyle == null)
				stringCellStyle = (XSSFCellStyle) workbook.createCellStyle();
			cell.setCellValue("");
			cell.setCellType(CellType.STRING);
			cell.setCellStyle(stringCellStyle);
			return stringCellStyle;
		}

		if (obj.isDate() || obj.isDateTime()) {
			if (datetime == null)
				datetime = (XSSFCellStyle) workbook.createCellStyle();
			datetime.setDataFormat(createHelper.createDataFormat().getFormat(BizUsuario.getUsr().getObjCountry().getShortDateFormat() + " " + BizUsuario.getUsr().getObjCountry().getLongTimeFormat()));
			cell.setCellValue(((JDate) obj).getValue());
			cell.setCellStyle(datetime);
			return datetime;
		}

		if (numberCellStyle == null)
			numberCellStyle = (XSSFCellStyle) workbook.createCellStyle();
		numberCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,##0"));

		if (obj.isInteger()) {
			cell.setCellValue(((JInteger) obj).getValue());
			cell.setCellStyle(numberCellStyle);
			cell.setCellType(CellType.NUMERIC);
			return numberCellStyle;
		}

		if (obj.isFloat() || obj.isCurrency()) {
			if (floatCellStyle == null)
				floatCellStyle = (XSSFCellStyle) workbook.createCellStyle();
			floatCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,##0.00"));
			cell.setCellValue(((JFloat) obj).getValue());
			cell.setCellStyle(floatCellStyle);
			cell.setCellType(CellType.NUMERIC);
			return floatCellStyle;
		}

		if (obj.isLong()) {
			cell.setCellValue(((JLong) obj).getValue());
			cell.setCellStyle(numberCellStyle);
			cell.setCellType(CellType.NUMERIC);
			return numberCellStyle;
		}

		if (stringCellStyle == null)
			stringCellStyle = (XSSFCellStyle) workbook.createCellStyle();
		cell.setCellValue(obj.toString());
		cell.setCellType(CellType.STRING);
		cell.setCellStyle(stringCellStyle);
		return stringCellStyle;

		// XSSFCellStyle currencyCellStyle = workbook.createCellStyle();
		// currencyCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("$#,##0.00"));

	}

	private void setStyleColor(JWin win, String campo, SXSSFWorkbook workbook, XSSFCellStyle style, Cell cell) throws Exception {
		boolean created = false;
		if (cc == null) {
			cc = createCellFont(workbook);
		}
		XSSFFont currFont = cc;
		String styleName = null;

		String field = campo;
		String color = win.getFieldForeground(field);
		String back = win.getFieldBackground(field);
		if (color != null || back != null) {
			styleName = color + back;
			created = true;
			style = getStyleCell(styleName, workbook, style);
		}

		if (color != null) {
			currFont = createCellFont(workbook);
			XSSFColor c = new XSSFColor(Color.decode("#" + color));
			currFont.setColor(c);
		} else {
			currFont.setColor(IndexedColors.BLACK.getIndex());
		}
		if (back != null) {
			XSSFColor c = new XSSFColor(Color.decode("#" + back));

			style.setFillBackgroundColor(c);
			style.setFillPattern(FillPatternType.LEAST_DOTS);
		}

		style.setFont(currFont);
		if (created)
			cell.setCellStyle(style);

	}

	private XSSFCellStyle getStyleCell(String styleName, SXSSFWorkbook workbook, XSSFCellStyle style) {
		XSSFCellStyle ret = styleMap.getElement(styleName);
		if (ret != null)
			return ret;

		ret = (XSSFCellStyle) workbook.createCellStyle();
		ret.setDataFormat(style.getDataFormat());

		styleMap.addElement(styleName, ret);

		return ret;
	}

	private XSSFFont createCellFont(Workbook workbook) {
		XSSFFont cc = (XSSFFont) workbook.createFont();
		cc.setFontHeightInPoints((short) 10);
		return cc;
	}

}
