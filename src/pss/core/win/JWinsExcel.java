package pss.core.win;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFFormulaEvaluator;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import pss.JPath;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JCurrency;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.collections.JList;
import pss.core.win.totalizer.JTotalizer;
import pss.core.win.totalizer.JTotalizer.Properties;
import pss.core.winUI.lists.JColumnaLista;
import pss.core.winUI.lists.JGrupoColumnaLista;
import pss.core.winUI.lists.JWinList;

public class JWinsExcel {

	JWins wins = null;
	CellStyle stringCellStyle = null;
	CellStyle datetime = null;
	CellStyle numberCellStyle = null;
	CellStyle floatCellStyle = null;
	CellStyle currencyCellStyle = null;
	Font cc = null;
	Map<String, CellStyle> styleMap = new TreeMap<String, CellStyle>();

	public JWins getWins() {
		return wins;
	}

	public void setWins(JWins wins) {
		this.wins = wins;
	}

	public JWinsExcel() {
	}


	boolean isXlsx;
	private void fillExcel(Workbook workbook, Sheet sheet, CreationHelper createHelper, int maxrows) throws Exception {
		String model = (wins.getModelExcel() != null) ? wins.getModelExcel().toLowerCase() : "xlsx";
		isXlsx = model.toLowerCase().equals("xlsx");
	

		if (numberCellStyle == null) {
			numberCellStyle = workbook.createCellStyle();
			numberCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("###,###,##0"));
		}
		if (floatCellStyle == null) {
			floatCellStyle = workbook.createCellStyle();
			floatCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("###,###,##0.00"));
		}
		if (currencyCellStyle == null) {
			currencyCellStyle = workbook.createCellStyle();
			currencyCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("$ ###,###,##0.00"));
		}
		// Estilo de cabecera de columnas (texto blanco sobre fondo negro)
		Font headerFont = createCellFont(workbook);
		headerFont.setBold(true);
		headerFont.setColor(IndexedColors.WHITE.getIndex());

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		headerCellStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		// Estilo para las líneas de encabezado que viene de getExcelHeader() (texto
		// negro, fondo blanco, bold)
		Font userHeaderFont = createCellFont(workbook);
		userHeaderFont.setBold(true);
		userHeaderFont.setColor(IndexedColors.BLACK.getIndex());

		CellStyle userHeaderCellStyle = workbook.createCellStyle();
		userHeaderCellStyle.setFont(userHeaderFont);
		userHeaderCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		userHeaderCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		// Construimos un "phantom" JWinList
		JWinList collist = new JWinList(wins);
		collist.setForExport(true);
		wins.ConfigurarColumnasLista(collist);
		wins.createTotalizer(collist);
		JTotalizer tot = collist.getTotalizer();

		JList<JColumnaLista> cl = collist.GetColumnasLista();
		// Contamos las columnas visibles para poder hacer merges
		int visibleCols = 0;
		for (int i = 0; i < cl.size(); i++) {
			JColumnaLista col = cl.getElementAt(i);
			if (col.isVisible() && !col.GetTitulo().isEmpty() && !col.GetCampo().isEmpty()) {
				visibleCols++;
			}
		}

		// 1) Insertamos las líneas de encabezado (getExcelHeader())
		int rownum = 0;
		String[] excelHeader = getWins().getExcelHeader();
		if (excelHeader != null && excelHeader.length > 0) {
			for (String headerLine : excelHeader) {
				Row row = sheet.createRow(rownum++);
				Cell cell = row.createCell(0);
				cell.setCellValue(headerLine);
				cell.setCellStyle(userHeaderCellStyle);
				if (visibleCols > 0) {
					sheet.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, 0, visibleCols - 1));
				}
			}
		}

		int headers = rownum;
		Row headerGroupRow = null;
		int[] gfrom = null;
		int[] gto = null;
		int gidx = 0;

		JList<JGrupoColumnaLista> group = collist.GetGrupoColumnasLista();
		if (group != null && group.size() > 0) {
			gfrom = new int[group.size() + 1];
			gto = new int[group.size() + 1];
			headerGroupRow = sheet.createRow(headers);
			headers++;
			gfrom[gidx] = 0;
		}

		// 2) Creamos la fila de cabecera real de columnas
		Row headerRow = sheet.createRow(headers);
		headers++;

		// Llenamos las cabeceras
		int icell = 0;
		String currGroup = "";
		for (int i = 0; i < cl.size(); i++) {
			JColumnaLista col = cl.getElementAt(i);
			JGrupoColumnaLista grp = col.getGrupo();

			if (!col.isVisible())
				continue;
			if (col.GetTitulo().isEmpty())
				continue;
			if (col.GetCampo().isEmpty())
				continue;

			// Si hay grouping, poner texto de grupo
			if (headerGroupRow != null) {
				String gTitle = (grp != null) ? grp.getTitulo() : "";
				Cell cell = headerGroupRow.createCell(icell);
				cell.setCellStyle(headerCellStyle);
				if (!currGroup.equals(gTitle)) {
					gto[gidx] = icell - 1;
					gidx++;
					gfrom[gidx] = icell;
					currGroup = gTitle;
					cell.setCellValue(currGroup);
				}
			}

			Cell cell = headerRow.createCell(icell++);
			cell.setCellValue(col.GetTitulo());
			cell.setCellStyle(headerCellStyle);
		}

		if (headerGroupRow != null) {
			gto[gidx] = icell - 1;
			for (int i = 0; i < group.size() + 1; i++) {
				try {
					sheet.addMergedRegion(new CellRangeAddress(headerGroupRow.getRowNum(), headerGroupRow.getRowNum(), gfrom[i], gto[i]));
				} catch (Exception ignore) {
				}
			}
		}

		// 3) Empezamos con las filas de datos
		int dataStartRow = headers;
		rownum = dataStartRow; // dataStartRow es la primera fila de datos
		JRecord rec = null;
		wins.readAll();
		wins.firstRecord();

		while (wins.nextRecord()) {
			JWin win = wins.getRecord();
			rec = (JRecord) win.GetBaseDato();
			Row row = sheet.createRow(rownum++);
			if (rownum > maxrows) {
				if (isXlsx)
					((SXSSFSheet) sheet).untrackAllColumnsForAutoSizing();
			}

			icell = 0;
			for (int i = 0; i < cl.size(); i++) {
				JColumnaLista col = cl.getElementAt(i);
				if (!col.isVisible())
					continue;
				if (col.GetTitulo().isEmpty())
					continue;
				if (col.GetCampo().isEmpty())
					continue;
				String campo = col.GetCampo();
				Cell cell = row.createCell(icell++);
				JObject<?> obj = rec.getPropDeep(campo, false);
				if (obj == null) {
					obj = new JString();
					obj.setValue("");
				}
				CellStyle cs = setCellStyleAndValue(obj, cell, workbook, createHelper);
				setStyleColor(win, campo, workbook, cs, cell);

			}

			// Si tenemos sub-detalles
			if (wins.hasSubDetail(true)) {
				JWins details = win.getWinsDetail();
				if (details == null)
					continue;
				JWinList oWinList = new JWinList(details);
				details.ConfigurarColumnasListaInternal(oWinList);
				details.readAll();
				if (!details.nextRecord())
					continue;

				// Header de detalle
				JList<JColumnaLista> cld = oWinList.GetColumnasLista();
				Row headerRowd = sheet.createRow(rownum++);
				int icelld = 0;
				Cell cell = headerRowd.createCell(icelld++);
				cell.setCellValue(""); // una celda vacía en la primera columna
				for (int i = 0; i < cld.size(); i++) {
					JColumnaLista dcol = cld.getElementAt(i);
					if (!dcol.isVisible())
						continue;
					if (dcol.GetTitulo().isEmpty())
						continue;
					if (dcol.GetCampo().isEmpty())
						continue;

					Cell celld = headerRowd.createCell(icelld++);
					celld.setCellValue(dcol.GetTitulo());
					celld.setCellStyle(headerCellStyle);
				}

				// Filas de detalle
				do {
					JWin wind = details.getRecord();
					JRecord recd = (JRecord) wind.GetBaseDato();
					Row rowd = sheet.createRow(rownum++);
					icelld = 0;
					Cell celle = rowd.createCell(icelld++);
					celle.setCellValue(""); // primera columna vacía

					for (int i = 0; i < cld.size(); i++) {
						JColumnaLista dcol = cld.getElementAt(i);
						if (!dcol.isVisible())
							continue;
						if (dcol.GetTitulo().isEmpty())
							continue;
						if (dcol.GetCampo().isEmpty())
							continue;

						String campo = dcol.GetCampo();
						Cell celld = rowd.createCell(icelld++);
						JObject<?> obj = recd.getPropDeep(campo);

						CellStyle cs = setCellStyleAndValue(obj, celld, workbook, createHelper);
						setStyleColor(wind, campo, workbook, cs, celld);

					}
				} while (details.nextRecord());
			}

			// flush en chunks
			double rest = ((double) rownum) % ((double) maxrows);
			if (rest == 0.0) {
				if (isXlsx)
					((SXSSFSheet) sheet).flushRows(maxrows);
			}
		}


		// 4) Totales del JTotalizer (ya existente en tu código)
		if (tot != null && rec != null && tot.hasAny()) {
			CellStyle footerCellStyle = workbook.createCellStyle();
			footerCellStyle.setFont(headerFont);
			footerCellStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
			footerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			Row row = sheet.createRow(rownum);
			// cchar y schar para ir generando referencias A, B, C... Z, AA, AB...
			char cchar = 'A';
			String schar = "";
			int icellFooter = 0;

			for (int i = 0; i < cl.size(); i++) {
				JColumnaLista col = cl.getElementAt(i);
				if (!col.isVisible())
					continue;
				if (col.GetTitulo().isEmpty())
					continue;
				if (col.GetCampo().isEmpty())
					continue;

				String campo = col.GetCampo();
				Cell cell = row.createCell(icellFooter);
				cell.setCellStyle(footerCellStyle);
				cell.setCellValue("");

				Properties prop = tot.getProp(campo);
				if (prop != null) {
					String oper = prop.getOperation();
					if (!oper.equals(JTotalizer.OPER_VALUE)) {
						// Decidir formato
						JObject<?> obj = rec.getProp(campo);
						if (obj.isCurrency() || obj.isFloat()) {
							footerCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,##0.00"));
						} else {
							footerCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,##0"));
						}
						cell.setCellStyle(footerCellStyle);

						if (oper.equalsIgnoreCase(JTotalizer.OPER_STATICSUM) || oper.equalsIgnoreCase(JTotalizer.OPER_CURRSTATICSUM) || oper.equalsIgnoreCase(JTotalizer.OPER_CURRSUM)) {
							oper = JTotalizer.OPER_SUM;
						}
						// Construimos formula
						// La primera fila de datos sería (headerGroupRow != null) ? 3 : 2,
						// etc.
						// Ajusta según tu layout
						int inirow = (headerGroupRow != null) ? (headerGroupRow.getRowNum() + 2) : (headerRow.getRowNum() + 1);
						// Ejemplo: SUM(A3:A{rownum})
						String colRef = (schar.length() == 0) ? ("" + cchar) : (schar + cchar);
						cell.setCellFormula(oper + "(" + colRef + inirow + ":" + colRef + (rownum) + ")");
					} else {
						cell.setCellValue(prop.getValue().toString());
					}
				}

				icellFooter++;
				// Incrementar la referencia de columna (A, B, C, ..., Z, AA...)
				if (cchar == 'Z') {
					cchar = 'A';
					if (schar.length() == 0) {
						schar = "A";
					} else {
						char cc = schar.charAt(0);
						cc++;
						schar = "" + cc;
					}
				} else {
					cchar++;
				}
			}

			// Si estamos por debajo de maxrows podemos evaluar
			if (rownum < maxrows) {
				try {
					if (isXlsx)
						SXSSFFormulaEvaluator.evaluateAllFormulaCells((SXSSFWorkbook) workbook, true);
				} catch (Throwable ee) {
					// no-op
				}
			}

			rownum++; // movernos debajo de la fila de totales
		}

		// 5) NUEVO: Footer personalizado según getWins().getExcelFooter()
		// Aquí sumamos SOLO las columnas listadas en excelFooter[] (por campo).
		String[] excelFooterCols = getWins().getExcelFooter();
		if (excelFooterCols != null && excelFooterCols.length > 0) {

			// Construir un set con los campos a sumar
			Set<String> footerColsSet = new HashSet<>(Arrays.asList(excelFooterCols));

			// Creamos un estilo de "footer" (fondo negro, texto blanco, bold)
			CellStyle blackFooterStyle = workbook.createCellStyle();
			Font blackFooterFont = createCellFont(workbook);
			blackFooterFont.setBold(true);
			blackFooterFont.setColor(IndexedColors.WHITE.getIndex());
			blackFooterStyle.setFont(blackFooterFont);
			blackFooterStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
			blackFooterStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			// Creamos la fila para el footer
			Row rowFooter = sheet.createRow(rownum++);

			// Para convertir la indexación de la columna en A, B, C...
			char cchar = 'A';
			String schar = "";
			int colIndexFooter = 0;

			// Data comienza en dataStartRow, termina en rownum - 2 (si no hubo
			// totales)
			// o rownum - 1 (según tu layout). Ajusta si tu total se metió en rownum
			// ya.
			// Supongamos que la última fila de datos real es (rowFooter.getRowNum() -
			// 1).
			int lastDataRow = rowFooter.getRowNum();

			for (int i = 0; i < cl.size(); i++) {
				JColumnaLista col = cl.getElementAt(i);
				if (!col.isVisible())
					continue;
				if (col.GetTitulo().isEmpty())
					continue;
				if (col.GetCampo().isEmpty())
					continue;

				// Creamos la celda
				Cell cell = rowFooter.createCell(colIndexFooter);
				cell.setCellStyle(blackFooterStyle);

				// Si el campo de esta columna está en el set => aplicamos SUM
				if (footerColsSet.contains(col.GetCampo())) {
					// Ajustar formato
					JObject<?> exampleObj = (rec != null) ? rec.getProp(col.GetCampo()) : null;
					if (exampleObj != null && (exampleObj.isCurrency() || exampleObj.isFloat())) {
						blackFooterStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,##0.00"));
					} else {
						blackFooterStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,##0"));
					}

					String colRef = (schar.length() == 0) ? ("" + cchar) : (schar + cchar);
					// SUM desde dataStartRow + 1 hasta lastDataRow (ambos inclusive)
					// Ojo con la numeración. Excel es 1-based en filas, SXSSF 0-based.
					int firstDataRow = dataStartRow + 1;
					cell.setCellFormula("SUM(" + colRef + firstDataRow + ":" + colRef + lastDataRow + ")");
				} else {
					cell.setCellValue("");
				}

				// Siguiente columna
				colIndexFooter++;
				if (cchar == 'Z') {
					cchar = 'A';
					if (schar.length() == 0) {
						schar = "A";
					} else {
						char cc = schar.charAt(0);
						cc++;
						schar = "" + cc;
					}
				} else {
					cchar++;
				}
			}

			// Evaluar
			if (rownum < maxrows) {
				try {
					if (isXlsx)
						SXSSFFormulaEvaluator.evaluateAllFormulaCells((SXSSFWorkbook) workbook, true);
				} catch (Throwable ee) {
					// no-op
				}
			}
		}

		// 6) Auto-size si no es muy grande
		if (rownum < maxrows) {
			int autoIndex = 0;
			for (int i = 0; i < cl.size(); i++) {
				JColumnaLista col = cl.getElementAt(i);
				if (!col.isVisible())
					continue;
				if (col.GetTitulo().isEmpty())
					continue;
				if (col.GetCampo().isEmpty())
					continue;
				sheet.autoSizeColumn(autoIndex++);
			}
		}

	}

	public String toExcel() throws Exception {
		String model = (wins.getModelExcel() != null) ? wins.getModelExcel().toLowerCase() : "xlsx";
		isXlsx = model.equals("xlsx");
		int maxrows = 1000;

		Workbook workbook;
		Sheet sheet;
		CreationHelper createHelper;

		if (isXlsx) {
			workbook = new SXSSFWorkbook();
			((SXSSFWorkbook) workbook).setCompressTempFiles(true);
			sheet = ((SXSSFWorkbook) workbook).createSheet("Data");
			((SXSSFSheet) sheet).trackAllColumnsForAutoSizing();
			((SXSSFSheet) sheet).setRandomAccessWindowSize(maxrows);
		} else {
			workbook = new HSSFWorkbook();
			sheet = workbook.createSheet("Data");
		}

		createHelper = workbook.getCreationHelper();
		fillExcel(workbook, sheet, createHelper, maxrows);

		String file = BizUsuario.getUsr().getCompany() + "/" +(wins.getExcelName()==null? 
				  BizUsuario.getUsr().GetUsuario()+"_"+JTools.getValidFilename(wins.GetTitle())+"_"+JDateTools.dateToStr(new Date(), "ddMMyyyyhhmmss"):
					wins.getExcelName()) + (isXlsx ? ".xlsx" : ".xls");
		JTools.createDirectories(JPath.PssPathTempFiles(), file);
    try (FileOutputStream out = new FileOutputStream(JPath.PssPathTempFiles()+ "/"+file)) {
			workbook.write(out);
			out.close();
			workbook.close(); 	 	 	
				
		}
		return file;
	}

	public byte[] toExcelBytes() throws Exception {
		String model = (wins.getModelExcel() != null) ? wins.getModelExcel().toLowerCase() : "xlsx";
		isXlsx = model.equals("xlsx");
		int maxrows = 1000;

		Workbook workbook;
		Sheet sheet;
		CreationHelper createHelper;

		if (isXlsx) {
			workbook = new SXSSFWorkbook();
			((SXSSFWorkbook) workbook).setCompressTempFiles(true);
			sheet = ((SXSSFWorkbook) workbook).createSheet("Data");
			((SXSSFSheet) sheet).trackAllColumnsForAutoSizing();
			((SXSSFSheet) sheet).setRandomAccessWindowSize(maxrows);
		} else {
			workbook = new HSSFWorkbook();
			sheet = workbook.createSheet("Data");
		}

			createHelper = workbook.getCreationHelper();
			fillExcel(workbook, sheet, createHelper, maxrows);
	
			try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
				workbook.write(out);
				workbook.close();
				return out.toByteArray();
			}
	}
	
	public static byte[] mergeExcels(List<SimpleEntry<String, byte[]>> sheets) throws Exception {
    Workbook finalWorkbook = new XSSFWorkbook();

    for (SimpleEntry<String, byte[]> entry : sheets) {
        String sheetName = entry.getKey();
        byte[] excelBytes = entry.getValue();

        try (InputStream in = new ByteArrayInputStream(excelBytes)) {
            Workbook sourceWorkbook = WorkbookFactory.create(in);
            Sheet sourceSheet = sourceWorkbook.getSheetAt(0);
            Sheet newSheet = finalWorkbook.createSheet(sheetName);
            copySheet(sourceSheet, newSheet);
        }
    }

    try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
        finalWorkbook.write(out);
        finalWorkbook.close();
        return out.toByteArray();
    }
}

	private static void copySheet(Sheet source, Sheet target) {
    Workbook targetWb = target.getWorkbook();
    Workbook sourceWb = source.getWorkbook();
    Map<CellStyle, CellStyle> styleMap = new HashMap<>();

    for (int i = 0; i <= source.getLastRowNum(); i++) {
        Row srcRow = source.getRow(i);
        if (srcRow == null) continue;

        Row tgtRow = target.createRow(i);
        tgtRow.setHeight(srcRow.getHeight());

        for (int j = 0; j < srcRow.getLastCellNum(); j++) {
            Cell srcCell = srcRow.getCell(j);
            if (srcCell == null) continue;

            Cell tgtCell = tgtRow.createCell(j);
            
            // Clone style
            if (srcCell.getCellStyle() != null) {
                CellStyle srcStyle = srcCell.getCellStyle();
                CellStyle tgtStyle = styleMap.computeIfAbsent(srcStyle, k -> {
                    CellStyle newStyle = targetWb.createCellStyle();
                    newStyle.cloneStyleFrom(srcStyle);
                    return newStyle;
                });
                tgtCell.setCellStyle(tgtStyle);
            }

            // Copy value
            switch (srcCell.getCellType()) {
                case STRING:
                    tgtCell.setCellValue(srcCell.getStringCellValue());
                    break;
                case NUMERIC:
                    tgtCell.setCellValue(srcCell.getNumericCellValue());
                    break;
                case BOOLEAN:
                    tgtCell.setCellValue(srcCell.getBooleanCellValue());
                    break;
                case FORMULA:
                    tgtCell.setCellFormula(srcCell.getCellFormula());
                    break;
                case BLANK:
                    tgtCell.setBlank();
                    break;
                default:
                    break;
            }
        }
    }

    // Copiar anchos de columna
    Row firstRow = source.getRow(0);
    if (firstRow != null) {
        for (int i = 0; i < firstRow.getLastCellNum(); i++) {
            target.setColumnWidth(i, source.getColumnWidth(i));
        }
    }
}

	/**
	 * Ajusta estilo y valor de la celda según el tipo de JObject.
	 */
	private CellStyle setCellStyleAndValue(JObject<?> obj, Cell cell, Workbook workbook, CreationHelper createHelper) throws Exception {

		if (obj == null || obj.isNull()) {
			CellStyle style = styleMap.computeIfAbsent("string", k -> {
				CellStyle cs = workbook.createCellStyle();
				return cs;
			});
			cell.setCellValue("");
			cell.setCellStyle(style);
			return style;

		}

		if (obj.isDate() || obj.isDateTime()) {
			CellStyle style = styleMap.computeIfAbsent("datetime", k -> {
				CellStyle cs = workbook.createCellStyle();
				try {
					cs.setDataFormat(createHelper.createDataFormat().getFormat(BizUsuario.getUsr().getObjCountry().getShortDateFormat() + " " + BizUsuario.getUsr().getObjCountry().getLongTimeFormat()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				return cs;
			});
			cell.setCellValue(((JDate) obj).getValue());
		} else if (obj.isLong()) {
			cell.setCellValue(((JLong) obj).getValue());
			cell.setCellStyle(numberCellStyle);
			cell.setCellType(CellType.NUMERIC);
			return numberCellStyle;
		} else if (obj.isFloat()) {
			cell.setCellValue(((JFloat) obj).getValue());
			cell.setCellStyle(floatCellStyle);
			cell.setCellType(CellType.NUMERIC);
			return floatCellStyle;

		}  else if (obj.isCurrency()) {
			cell.setCellValue(((JCurrency) obj).getValue());
			cell.setCellStyle(currencyCellStyle);
			cell.setCellType(CellType.NUMERIC);
			return currencyCellStyle;

		} 

		// Por defecto, tratamos como String
		if (stringCellStyle == null) {
			stringCellStyle = workbook.createCellStyle();
		}
		cell.setCellValue(obj.toString());
		cell.setCellType(CellType.STRING);
		cell.setCellStyle(stringCellStyle);
		return stringCellStyle;
	}

	/**
	 * Aplica estilo de color si el campo define foreground/background
	 */
	private void setStyleColor(JWin win, String campo, Workbook workbook, CellStyle style, Cell cell) throws Exception {

		String foreColor = win.getFieldForeground(campo); // color del texto (hex)
		String backColor = win.getFieldBackground(campo); // color de fondo (hex)

		// Creamos nueva fuente si hay que aplicar color
		if (foreColor != null || backColor != null) {
			Font font = workbook.createFont();
			font.setFontHeightInPoints((short) 10);

			// Aplicamos color de texto (solo en XLSX se puede RGB)
			if (foreColor != null) {
				if (isXlsx) {
					// Color RGB en .xlsx
					try {
						short index = IndexedColors.BLACK.getIndex(); // fallback
						font.setColor(index); // En SXSSF los colores RGB se manejan
																	// distinto
					} catch (Exception e) {
						font.setColor(IndexedColors.BLACK.getIndex());
					}
				} else {
					font.setColor(IndexedColors.BLACK.getIndex()); // .xls solo soporta
																													// paleta
				}
			}

			style.setFont(font);
		}

		// Color de fondo
		if (backColor != null) {
			try {
				style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				if (isXlsx) {
					// No se puede asignar color RGB directamente en CellStyle sin XSSF
					style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex()); // fallback
				} else {
					style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
				}
			} catch (Exception e) {
				style.setFillForegroundColor(IndexedColors.AUTOMATIC.getIndex());
			}
		}

		cell.setCellStyle(style);
	}

	private CellStyle getStyleCell(String styleName, Workbook workbook, CellStyle style) {
		CellStyle ret = styleMap.get(styleName);
		if (ret != null)
			return ret;

		ret = (CellStyle) workbook.createCellStyle();
		ret.setDataFormat(style.getDataFormat());
		styleMap.put(styleName, ret);
		return ret;
	}

	private Font createCellFont(Workbook workbook) {
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 10);
		return font;
	}

}
