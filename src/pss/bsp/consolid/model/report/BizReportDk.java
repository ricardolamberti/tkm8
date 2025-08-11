package pss.bsp.consolid.model.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import pss.bsp.consolid.model.liquidacion.detail.BizLiqDetail;
import pss.bsp.consolid.model.report.detail.BizReportDetailDk;
import pss.bsp.consolid.model.report.detail.GuiReportDetailDks;
import pss.bsp.dk.BizClienteDK;
import pss.bsp.dk.GuiClienteDKs;
import pss.common.event.mailing.type.GuiMailingPersonaTypes;
import pss.common.security.BizUsuario;
import pss.core.services.JExec;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JMultiple;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JPair;
import pss.core.tools.biblioteca.BizOldBiblioteca;
import pss.core.tools.collections.JIterator;
import pss.tourism.dks.BizDk;

public class BizReportDk extends JRecord {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	private JLong pId = new JLong();
	private JString pCompany = new JString();
	private JString pOriginalReportFile = new JString();
	private JLong pOriginalReport = new JLong();
	private JLong pGeneratedReport = new JLong();
	private JDate pDateFrom = new JDate();
	private JDate pDateTo = new JDate();
	private JLong pIdDK = new JLong();
	
	public void setId(long id) throws Exception {
		pId.setValue(id);
	}
	public long getId() throws Exception {
		return pId.getValue();
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public void setCompany(String company) throws Exception {
		pCompany.setValue(company);
	}

	public String getOriginalReportFile() throws Exception {
		return pOriginalReportFile.getValue();
	}

	public void setOriginalReportFile(String originalReportFile) throws Exception {
		pOriginalReportFile.setValue(originalReportFile);
	}

	public long getOriginalReport() throws Exception {
		return pOriginalReport.getValue();
	}

	public void setOriginalReport(long originalReport) throws Exception {
		pOriginalReport.setValue(originalReport);
	}

	public long getGeneratedReport() throws Exception {
		return pGeneratedReport.getValue();
	}

	public void setGeneratedReport(long generatedReport) throws Exception {
		pGeneratedReport.setValue(generatedReport);
	}

	public java.util.Date getDateFrom() throws Exception {
		return pDateFrom.getValue();
	}

	public void setDateFrom(java.util.Date dateFrom) throws Exception {
		pDateFrom.setValue(dateFrom);
	}

	public java.util.Date getDateTo() throws Exception {
		return pDateTo.getValue();
	}

	public void setDateTo(java.util.Date dateTo) throws Exception {
		pDateTo.setValue(dateTo);
	}

	public long getIdDK() throws Exception {
		return pIdDK.getValue();
	}

	public void setIdDK(long idDK) throws Exception {
		pIdDK.setValue(idDK);
	}

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizReportDk() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("id", pId);
		addItem("company", pCompany);
		addItem("original_report_file", pOriginalReportFile);
		addItem("original_report", pOriginalReport);
		addItem("generated_report", pGeneratedReport);
		addItem("date_from", pDateFrom);
		addItem("date_to", pDateTo);
		addItem("id_dk", pIdDK);
	}
	

	@Override
	public void createFixedProperties() throws Exception {

		addFixedItem(KEY, "id", "id", false, false, 64);
		addFixedItem(FIELD, "company", "Company", true, false, 100);
		addFixedItem(VIRTUAL, "original_report_file", "Archivo original file", true, false, 1000);
		addFixedItem(FIELD, "original_report", "Archivo original", true, false, 64);
		addFixedItem(FIELD, "generated_report", "Archivo generado", true, false, 64);
		addFixedItem(FIELD, "date_from", "Fecha desde", true, false, 25);
		addFixedItem(FIELD, "date_to", "Fecha hasta", true, false, 25);
		addFixedItem(FIELD, "id_dk", "dk", true, false, 64);

	}

	@Override
	public void createControlProperties() throws Exception {
		this.addControlsProperty("id_dk", createControlCombo(GuiClienteDKs.class,"id", new JPair<String, String>("company","company") ));
  		super.createControlProperties();
	}
	@Override
	public String GetTable() {
		return "TUR_DKS_REPORTS";
	}
  public String getFileElectronico() throws Exception {
  	if (this.pGeneratedReport.isNull()) return null;
  	BizOldBiblioteca biblio =this.getObjBiblioteca();
  	return biblio.getContentAsFilename(getCompany(),"reporte_"+JDateTools.DateToString(getDateFrom())+"_"+JDateTools.DateToString(getDateTo()));

  }

  BizClienteDK objPersona;
  
  public BizClienteDK getObjClienteDK() throws Exception {
  	if (objPersona!=null) return objPersona;
  	BizClienteDK biz = new BizClienteDK();
  	biz.dontThrowException(true);
  	biz.addFilter("company", BizUsuario.getUsr().getCompany());
  	biz.addFilter("id", this.getIdDK());
  	if (biz.read())
  		return objPersona=biz;
  	return  null;
	}
  BizOldBiblioteca biblio;
  public BizOldBiblioteca getObjBiblioteca() throws Exception {
  	if (this.pGeneratedReport.isNull()) return null;
  	if (this.biblio!=null) return this.biblio;
  	BizOldBiblioteca b = new BizOldBiblioteca();
  	b.read(this.pGeneratedReport.getValue());
  	return (this.biblio=b);
  }
  BizOldBiblioteca biblioOriginal;
  public BizOldBiblioteca getObjBibliotecaOriginal() throws Exception {
  	if (this.pOriginalReport.isNull()) return null;
  	if (this.biblioOriginal!=null) return this.biblioOriginal;
  	BizOldBiblioteca b = new BizOldBiblioteca();
  	b.read(this.pOriginalReport.getValue());
  	return (this.biblioOriginal=b);
  }
	@Override
	public void processUpdate() throws Exception {
		super.processUpdate();
	}
	
	@Override
	public void processDelete() throws Exception {
		BizReportDetailDk det  = new BizReportDetailDk();
		det.addFilter("company", getCompany());
		det.addFilter("id_report", getId());
		det.deleteMultiple();
		getObjBiblioteca().processDelete();
		getObjBibliotecaOriginal().processDelete();
		super.processDelete();
	}

	@Override
	public void processInsert() throws Exception {
		String filename = getOriginalReportFile();
		if (filename.equals(""))
			throw new Exception("No se especifico archivo");
		filename = URLDecoder.decode(filename);
		String source = filename;
		int lastPunto = source.lastIndexOf('.') + 1;
		String type = source.substring(lastPunto);
		BizOldBiblioteca biblo = new BizOldBiblioteca();
		biblo.setContentByFilename(getCompany(), source, type);
		biblo.processInsert();
		
		this.setOriginalReport(biblo.getId());
		super.processInsert();
		this.setId(getIdentity("id"));
		fillIssues();
		fillRefunds();
		readExcel(biblo.getContentAsByteArray(getCompany()));
		
	}
	
	public void execProcessGenerate() throws Exception {
		JExec oExec = new JExec(this, "processGenerate") {

			@Override
			public void Do() throws Exception {
				processGenerate();
			}
		};
		oExec.execute();
	}
	public void processGenerate() throws Exception {
		BizOldBiblioteca biblo = getObjBibliotecaOriginal();
		biblo.getContentAsByteArray(getCompany());
		
		byte[] bytesOut = completeExcel(biblo.getContentAsByteArray(getCompany()));
	
		BizOldBiblioteca bibloOut = new BizOldBiblioteca();
		bibloOut.setContentFromByteArray( getCompany(), bytesOut,"xlsx");
		bibloOut.processInsert();
		this.setGeneratedReport(bibloOut.getId());
		super.processUpdate();

	}
 	public void fillIssues() throws Exception {
		JRecords<BizLiqDetail>  liqs = new JRecords<BizLiqDetail>(BizLiqDetail.class);
		liqs.addFilter("company", getCompany());
		liqs.addFilter("fecha_doc", getDateFrom(),">=");
		liqs.addFilter("fecha_doc", getDateTo(),"<=");
		liqs.addFilter("tipo_doc", "STE");
		
		liqs.addFilter("dk", getObjClienteDK().getDK());
		JIterator<BizLiqDetail> it = liqs.getStaticIterator();
		while (it.hasMoreElements()) {
			BizLiqDetail liq = it.nextElement();
      issues.put(liq.getNroBoleto(), liq);
		}
	}
	public void fillRefunds() throws Exception {
		JRecords<BizLiqDetail>  liqs = new JRecords<BizLiqDetail>(BizLiqDetail.class);
		liqs.addFilter("company", getCompany());
		liqs.addFilter("fecha_doc", getDateFrom(),">=");
		liqs.addFilter("fecha_doc", getDateTo(),"<=");
		liqs.addFilter("tipo_doc", "RFN");
		liqs.addFilter("dk", getObjClienteDK().getDK());
		JIterator<BizLiqDetail> it = liqs.getStaticIterator();
		while (it.hasMoreElements()) {
			BizLiqDetail liq = it.nextElement();
			
      refunds.put(liq.getNroBoleto(), liq);
		}
	}

  private final Map<CellStyle, CellStyle> styleCache = new HashMap<>();
  private final Map<String, BizLiqDetail> issues  = new HashMap<>();
  private final Map<String, BizLiqDetail> refunds = new HashMap<>();

  public Map<String, BizLiqDetail> getIssues()  { return issues;  }
  public Map<String, BizLiqDetail> getRefunds() { return refunds; }
  CellStyle[] columnStyles = new CellStyle[12];
   
  public byte[] completeExcel(byte[] inputBytes) throws Exception {
    Row defaultStyleRow = null;
    CellStyle[] columnStyles = new CellStyle[12];
    styleCache.clear();
    try (Workbook srcWb = WorkbookFactory.create(new ByteArrayInputStream(inputBytes));
         Workbook trgWb = new XSSFWorkbook();
         ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

        Sheet src = srcWb.getSheetAt(0);
        Sheet trg = trgWb.createSheet(src.getSheetName());

        int maxCol = 0;
        for (int r = src.getFirstRowNum(); r <= src.getLastRowNum(); r++) {
            Row row = src.getRow(r);
            if (row != null) maxCol = Math.max(maxCol, row.getLastCellNum());
        }
        for (int c = 0; c < maxCol; c++) trg.setColumnWidth(c, src.getColumnWidth(c));

        for (int i = 0; i < src.getNumMergedRegions(); i++) trg.addMergedRegion(src.getMergedRegion(i));

    
        boolean inIssues = false;
        boolean inRefund = false;
        boolean nextRefund = false;
        boolean end = false;
        int rowOut = 0;
        int issueStart = -1, issueEnd = -1;
        int refundStart = -1, refundEnd = -1;
        int issueSumRow = -1, refundSumRow = -1;

        for (int r = src.getFirstRowNum(); r <= src.getLastRowNum(); r++) {
            Row sRow = src.getRow(r);
            if (sRow == null) continue;

            short first = sRow.getFirstCellNum();
            short last  = sRow.getLastCellNum();
            if (first < 0) continue;

            Cell cCell = sRow.getCell(2);
            String ticket = (cCell != null) ? cCell.toString().trim() : "";
            boolean valid = !ticket.isEmpty() && isValidTicketFormat(ticket);

            if (valid && !inIssues && !nextRefund) {
                inIssues = true;
                issueStart = rowOut;
                if (defaultStyleRow == null) {
                    defaultStyleRow = sRow;
                    for (int i = 0; i < columnStyles.length; i++) {
                        Cell cell = defaultStyleRow.getCell(i);
                        if (cell != null) {
                            columnStyles[i] = copyAndCacheStyle(cell.getCellStyle(), trgWb);
                        }
                    }
                }
            } else if (!valid && inIssues && !nextRefund) {
                inIssues = false;
                nextRefund = true;
                issueEnd = rowOut - 1;
                rowOut = appendMissing(trg, rowOut, true, false, trgWb, columnStyles);
                issueEnd = rowOut - 1;
            } else if (valid && !inRefund && nextRefund) {
                inRefund = true;
                refundStart = rowOut;
            } else if (!valid && inRefund && nextRefund && !end) {
                refundEnd = rowOut - 1;
                rowOut = appendMissing(trg, rowOut, false, false, trgWb, columnStyles);
                refundEnd = rowOut - 1;
                end = true;
            }

            Row tRow = trg.createRow(rowOut++);
            tRow.setHeight(sRow.getHeight());
            for (int c = first; c < last || c <= 11; c++) {
                Cell sCell = sRow.getCell(c);
                Cell tCell = tRow.createCell(c);

                if (c >= 7 && c <= 11) {
                    if (valid) {
                        BigDecimal v = calcValueForColumnFromDetail(c, ticket, inRefund);
                        tCell.setCellValue(v.doubleValue());
                    } else {
                        if (sCell != null) copyCellValue(sCell, tCell);
                    }
                } else {
                    if (sCell != null) copyCellValue(sCell, tCell);
                }
                if (sCell != null) copyCellStyle(sCell, tCell, trgWb);
            }
        }

        // Aplicar fórmulas SUM para totales
        for (int r = trg.getFirstRowNum(); r <= trg.getLastRowNum(); r++) {
            Row row = trg.getRow(r);
            if (row == null) continue;
            Cell fCell = row.getCell(5);
            Cell lCell = row.getCell(11);
            if (fCell == null || lCell == null) continue;

            String text = fCell.getStringCellValue().trim();
            String textNormalized = text.replaceAll("\\s", "");
            if (textNormalized.startsWith("总计") || textNormalized.endsWith("Total")) {
                if (issueSumRow == -1 && r > issueEnd) {
                    int from = issueStart + 1;
                    int to = issueEnd + 1;
                    lCell.setCellFormula("SUM(L" + from + ":L" + to + ")");
                    issueSumRow = r;
                } else if (refundSumRow == -1 && r > refundEnd) {
                    int from = refundStart + 1;
                    int to = refundEnd + 1;
                    lCell.setCellFormula("SUM(L" + from + ":L" + to + ")");
                    refundSumRow = r;
                }
            }
        }

        // Aplicar referencias en filas específicas
        for (int r = trg.getFirstRowNum(); r <= trg.getLastRowNum(); r++) {
            Row row = trg.getRow(r);
            if (row == null) continue;
            Cell fCell = row.getCell(5);
            Cell lCell = row.getCell(11);
            if (fCell == null || lCell == null) continue;

            String text = fCell.getStringCellValue().trim();
            if (text.contains("Total amount of Issuance") && issueSumRow != -1) {
                lCell.setCellFormula("L" + (issueSumRow + 1));
            } else if (text.contains("Total amount of refund") && refundSumRow != -1) {
                lCell.setCellFormula("L" + (refundSumRow + 1));
            } else if (text.contains("Total amount payable") && issueSumRow != -1 && refundSumRow != -1) {
              lCell.setCellFormula("L" + (issueSumRow + 1) + "+L" + (refundSumRow + 1));
          }
        }
        trgWb.setForceFormulaRecalculation(true);

        trgWb.write(bos);
        return bos.toByteArray();
    }
}
  CellStyle defaultCellStyle = null;
  public void readExcel(byte[] inputBytes) throws Exception {
    styleCache.clear();
    try (Workbook srcWb = WorkbookFactory.create(new ByteArrayInputStream(inputBytes));
         Workbook trgWb = new XSSFWorkbook();
         ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

        Sheet src = srcWb.getSheetAt(0);
       
        Set<String> foundIssues = new HashSet<>();
        Set<String> foundRefunds = new HashSet<>();

        boolean inIssues = false;
        boolean inRefund = false;
        boolean nextRefund = false;
        int rowOut = 0;

        for (int r = src.getFirstRowNum(); r <= src.getLastRowNum(); r++) {
            Row sRow = src.getRow(r);
            if (sRow == null) continue;

          
            short first = sRow.getFirstCellNum();
            short last  = sRow.getLastCellNum();
            if (first < 0) continue;

            Cell cCell = sRow.getCell(2);
            String ticket = (cCell != null) ? cCell.toString().trim() : "";
            boolean valid = !ticket.isEmpty() && isValidTicketFormat(ticket);

            if (valid && !inIssues && !nextRefund) {
               inIssues = true;
            }
            else if (!valid && inIssues && !nextRefund ) {
              inIssues = false;
              nextRefund=true;
              
            }
            else if (valid && !inRefund && nextRefund) {
            	inRefund = true;

            }
            else if (!valid && inRefund && nextRefund ) {
            	inRefund = false;
            }
            if (valid) {
                if (inIssues) foundIssues.add(ticket);
                else if (inRefund)  foundRefunds.add(ticket);
            }
            double total=0,fare=0,comm=0,fee=0,totap=0;
            for (int c = first; c < last || c <= 11; c++) {
               
                if (c >= 7 && c <= 11) {
                    if (valid) {
                        BigDecimal v = calcValueForColumn(c, ticket, inRefund);
                        if (c==7) total=v.doubleValue();
                        if (c==8) fare=v.doubleValue();
                        if (c==9) comm=v.doubleValue();
                        if (c==10) fee=v.doubleValue();
                        if (c==11) totap=v.doubleValue();
                    } 
                } 
            }
            if (valid) {
              BizReportDetailDk repo = new BizReportDetailDk();
              repo.setType(inIssues?"ISSUE":"REFUND");
              repo.setAdded(false);
              repo.setReportId(getId());
              repo.setCompany(getCompany());
              repo.setDateOfTick(sRow.getCell(0)==null?"":sRow.getCell(0).toString());
              repo.setCtrip(sRow.getCell(1)==null?"":sRow.getCell(1).toString());
              repo.setTktNumber(sRow.getCell(2)==null?"":sRow.getCell(2).toString());
              repo.setPassagerName(sRow.getCell(3)==null?"":sRow.getCell(3).toString());
              repo.setFlightNro(sRow.getCell(4)==null?"":sRow.getCell(4).toString());
              repo.setOD(sRow.getCell(5)==null?"":sRow.getCell(5).toString());
              repo.setCtripAmount(sRow.getCell(6)==null?0:sRow.getCell(6).getNumericCellValue());
              repo.setTotal(total);
              repo.setFare(fare);
              repo.setComision(comm);
              repo.setFee(fee);
              repo.setTotalPay(totap);
              repo.processInsert();
        			
            }
       }

        Set<String> missingIssues = new HashSet<>(issues.keySet());
        missingIssues.removeAll(foundIssues);

        Set<String> missingRefunds = new HashSet<>(refunds.keySet());
        missingRefunds.removeAll(foundRefunds);

        rowOut = appendMissing( rowOut, missingIssues, false, trgWb);
        appendMissing( rowOut, missingRefunds, true, trgWb);

      }
  }
  private CellStyle copyAndCacheStyle(CellStyle source, Workbook wb) {
    if (source == null) return null;

    return styleCache.computeIfAbsent(source, k -> {
        CellStyle newStyle = wb.createCellStyle();
        newStyle.cloneStyleFrom(k);
        return newStyle;
    });
  }
  private int appendMissing(Sheet trg, int startRow, boolean issues, boolean refund, Workbook wb, CellStyle[] referenceStyles) throws Exception {
    JRecords<BizReportDetailDk> details = new JRecords<BizReportDetailDk>(BizReportDetailDk.class);
    details.addFilter("company", getCompany());
    details.addFilter("id_report", getId());
    details.addFilter("type", issues ? "ISSUE" : "REFUND");
    details.addFilter("added", true);
    JIterator<BizReportDetailDk> it = details.getStaticIterator();

    while (it.hasMoreElements()) {
        BizReportDetailDk det = it.nextElement();
        if (det == null) continue;
        Row row = trg.createRow(startRow++);

        for (int i = 0; i <= 11; i++) {
            Cell c = row.createCell(i);
            switch (i) {
                case 0: c.setCellValue(det.getDateOfTick()); break;
                case 1: c.setCellValue(det.getCtrip()); break;
                case 2: c.setCellValue(det.getTktNumber()); break;
                case 3: c.setCellValue(det.getPassagerName()); break;
                case 4: c.setCellValue(det.getFlightNro()); break;
                case 5: c.setCellValue(det.getOD()); break;
                case 6: c.setCellValue(det.getCtripAmount()); break;
                case 7: c.setCellValue(det.getTotal()); break;
                case 8: c.setCellValue(det.getFare()); break;
                case 9: c.setCellValue(det.getComision()); break;
                case 10: c.setCellValue(det.getFee()); break;
                case 11: c.setCellValue(det.getTotalPay()); break;
            }
            if (referenceStyles != null && referenceStyles.length > i && referenceStyles[i] != null) {
                c.setCellStyle(referenceStyles[i]);
            }
        }
    }
    return startRow;
}
  
  private int appendMissing( int startRow, Set<String> missing, boolean refund, Workbook wb) throws Exception {
    for (String tk : missing) {
        BizLiqDetail det = refund ? refunds.get(tk) : issues.get(tk);
        if (det == null) continue;
        double total=0,fare=0,comm=0,fee=0,totap=0;
        for (int c = 7; c <= 11; c++) {
            BigDecimal v = calcValueForColumn(c, tk, refund);
             if (c==7) total=v.doubleValue();
            if (c==8) fare=v.doubleValue();
            if (c==9) comm=v.doubleValue();
            if (c==10) fee=v.doubleValue();
            if (c==11) totap=v.doubleValue();
          }
          BizReportDetailDk repo = new BizReportDetailDk();
          repo.setType(refund?"REFUND":"ISSUE");
          repo.setReportId(getId());
          repo.setCompany(getCompany());
          repo.setDateOfTick("");
          repo.setCtrip("");
          repo.setAdded(true);
          repo.setTktNumber(det.getNroBoleto());
          repo.setPassagerName(det.getPasajero());
          repo.setFlightNro("");
          repo.setOD(det.getRuta());
          repo.setCtripAmount(0);
          repo.setTotal(total);
          repo.setFare(fare);
          repo.setComision(comm);
          repo.setFee(fee);
          repo.setTotalPay(totap);
          repo.processInsert();
    			
        
    }
    return startRow;
}
  private void copyCellValue(Cell src, Cell trg) {
      switch (src.getCellType()) {
          case STRING:   trg.setCellValue(src.getStringCellValue()); break;
          case NUMERIC:  trg.setCellValue(src.getNumericCellValue()); break;
          case BOOLEAN:  trg.setCellValue(src.getBooleanCellValue()); break;
          case BLANK:    trg.setBlank(); break;
          case FORMULA:
              switch (src.getCachedFormulaResultType()) {
                  case STRING:  trg.setCellValue(src.getStringCellValue()); break;
                  case NUMERIC: trg.setCellValue(src.getNumericCellValue()); break;
                  case BOOLEAN: trg.setCellValue(src.getBooleanCellValue()); break;
                  default:      trg.setCellFormula(src.getCellFormula());
              }
              break;
          default: trg.setCellValue(src.toString());
      }
  }

  private void copyCellStyle(Cell src, Cell trg, Workbook wb) {
      CellStyle orig = src.getCellStyle();
      if (orig == null) return;
      CellStyle cached = styleCache.computeIfAbsent(orig, k -> {
          CellStyle cs = wb.createCellStyle();
          cs.cloneStyleFrom(k);
          return cs;
      });
      trg.setCellStyle(cached);
  }

  private BigDecimal calcValueForColumn(int col, String tk, boolean refund) throws Exception {
      switch (col) {
          case 7:  return getTotal(tk, refund);
          case 8:  return getFare(tk, refund);
          case 9:  return getCommission(tk, refund);
          case 10: return getFee(tk, refund);
          case 11: return getTktCash(tk, refund);
          default: return BigDecimal.ZERO;
      }
  }
  private BigDecimal calcValueForColumnFromDetail(int col, String tk, boolean refund) throws Exception {
    switch (col) {
        case 7:  return getTotalFromDetail(tk, refund);
        case 8:  return getFareFromDetail(tk, refund);
        case 9:  return getCommissionFromDetail(tk, refund);
        case 10: return getFeeFromDetail(tk, refund);
        case 11: return getTktCashFromDetail(tk, refund);
        default: return BigDecimal.ZERO;
    }
}

  private BizLiqDetail cacheDetail; private String lastTicket;
  private BizLiqDetail fetch(String tk) throws Exception {
      String pure = tk.contains("-") ? tk.substring(tk.indexOf('-')+1) : tk;
      if (lastTicket!=null && pure.equals(lastTicket) ) return cacheDetail;
      BizLiqDetail liq = new BizLiqDetail();
      liq.dontThrowException(true);
      lastTicket = pure;
      if (!liq.readByBoleto(getCompany(), pure)) return cacheDetail = null;
      return cacheDetail = liq;
  }
  private BizReportDetailDk cacheDetail2; private String lastTicket2;
  private BizReportDetailDk fetchDetail(String tk) throws Exception {
      String pure = tk;
      if (lastTicket2!=null && pure.equals(lastTicket2) ) return cacheDetail2;
      BizReportDetailDk liq = new BizReportDetailDk();
      liq.dontThrowException(true);
      lastTicket2 = pure;
      if (!liq.readByBoleto(getCompany(), pure)) return cacheDetail2 = null;
      return cacheDetail2 = liq;
  }

  private BigDecimal getTotal(String t, boolean ref) throws Exception { BizLiqDetail d = fetch(t); return new BigDecimal(d==null?0:(ref?-d.getImporte():d.getImporte())); }
  private BigDecimal getFare(String t, boolean ref)  throws Exception { BizLiqDetail d = fetch(t); return new BigDecimal(d==null?0:(ref?-d.getTarifa():d.getTarifa())); }
  private BigDecimal getCommission(String t, boolean ref) throws Exception{BizLiqDetail d = fetch(t); return new BigDecimal(d==null?0:(ref?-d.getComision():d.getComision())); }
  private BigDecimal getFee(String t, boolean ref)   throws Exception { BizLiqDetail d = fetch(t); return new BigDecimal(d==null?0:(ref?-d.getFee():d.getFee())); }
  private BigDecimal getTktCash(String t, boolean ref) throws Exception {
      BizLiqDetail d = fetch(t);
      if (d == null) return BigDecimal.ZERO;
      if (ref) return new BigDecimal(-1*(d.getSaldo() - d.getComision() - d.getFee()));
      return new BigDecimal(d.getSaldo() - d.getComision() - d.getFee());
  }

  private BigDecimal getTotalFromDetail(String t, boolean ref) throws Exception { BizReportDetailDk d = fetchDetail(t); return new BigDecimal(d==null?0:d.getTotal()); }
  private BigDecimal getFareFromDetail(String t, boolean ref)  throws Exception { BizReportDetailDk d = fetchDetail(t); return new BigDecimal(d==null?0:d.getFare()); }
  private BigDecimal getCommissionFromDetail(String t, boolean ref) throws Exception{BizReportDetailDk d = fetchDetail(t); return new BigDecimal(d==null?0:d.getComision()); }
  private BigDecimal getFeeFromDetail(String t, boolean ref)   throws Exception { BizReportDetailDk d = fetchDetail(t); return new BigDecimal(d==null?0:d.getFee()); }
  private BigDecimal getTktCashFromDetail(String t, boolean ref) throws Exception { BizReportDetailDk d = fetchDetail(t); return new BigDecimal(d==null?0:d.getTotalPay()); }
  
  private boolean isValidTicketFormat(String s) {
    if (s == null || s.trim().isEmpty()) return false;
    s = s.trim();

    // Si es un único ticket con guion negativo
    if (s.matches("^-[0-9]{5,15}$")) return true;

    // Si es un único ticket con prefijo
    if (s.matches("^\\d{1,4}-\\d{5,15}$")) return true;

    // Si es un único ticket con doble guion
    if (s.matches("^\\d{1,4}-\\d{5,15}-\\d{5,15}$")) return true;

    // Si contiene múltiples tickets separados por ;
    String[] partes = s.split(";");
    for (String parte : partes) {
        parte = parte.trim();
        if (parte.isEmpty()) return false;

        // Ticket simple: 205-xxxxxxxxxx
        if (parte.matches("^\\d{1,4}-\\d{5,15}$")) continue;

        // Ticket doble sin prefijo repetido: xxxxxxxx-xxxxxxxx
        if (parte.matches("^\\d{5,15}-\\d{5,15}$")) continue;

        // Ticket con prefijo + dos números: 205-xxxxxxxx-xxxxxxxx
        if (parte.matches("^\\d{1,4}-\\d{5,15}-\\d{5,15}$")) continue;

        // Si nada coincide, es inválido
        return false;
    }

    return true;
}


}
