package pss.core.reports.jasper;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import pss.JPss;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;
import pss.core.tools.formatters.JRegionalFormatterFactory;


public class JReport implements JPrintConstants {


  //////////////////////////////////////////////////////////////////////////////
  //
  //   STATIC VARIABLES
  //
  //////////////////////////////////////////////////////////////////////////////
  @SuppressWarnings("unused")
	private static JRLoader MANAGER;

  //////////////////////////////////////////////////////////////////////////////
  //
  //   INSTANCE VARIABLES
  //
  //////////////////////////////////////////////////////////////////////////////
  private String sFileName;
  private JRDataSource oDataSource;
  private Map<String,Object> oParameters;
  private String sTitle;
  private int iPaperSize = PAPER_SIZE_LETTER;


  //////////////////////////////////////////////////////////////////////////////
  //
  //   CONSTRUCTORS
  //
  //////////////////////////////////////////////////////////////////////////////
  public JReport(String zFileName) {
    this.setFileName(zFileName);
  }
  public JReport(String zFileName, JRDataSource zDataSource) {
    this.setFileName(zFileName);
    this.oDataSource = zDataSource;
  }


  //////////////////////////////////////////////////////////////////////////////
  //
  //   INSTANCE METHODS
  //
  //////////////////////////////////////////////////////////////////////////////
  public JRDataSource getDataSource() {
    return this.oDataSource;
  }
  public void setDataSource(JRDataSource zDataSource) {
    this.oDataSource = zDataSource;
  }
  public String getFileName() {
    return this.sFileName;
  }
  public void setFileName(String zFileName) {
    URL resource = JPss.class.getResource(zFileName);
    if (resource != null) {
      this.sFileName = resource.getPath();
    } else {
      this.sFileName = zFileName;
    }
  }

  public int getPaperSize() {
    return this.iPaperSize;
  }
  public void setPaperSize(int zPaperSizeConstant) {
    this.iPaperSize = zPaperSizeConstant;
  }

  public void setDataSourceFromJBDs(JRecords zJBDs) throws Exception {
    this.setDataSource(JReportDataSourceProvider.getInstance().createDataSource(zJBDs, true));
  }

  public void setDataSourceFromSQL(String zSQL) throws Exception {
    this.setDataSource(JReportDataSourceProvider.getInstance().createDataSource(zSQL));
  }

  public String getTitle() {
    return this.sTitle;
  }
  public void setTitle(String zTitle) {
    this.sTitle = zTitle;
  }


  private Map<String,Object> getParametersMap() {
    if (this.oParameters==null) {
      this.oParameters = new HashMap<String,Object>();
    }
    return this.oParameters;
  }


  public void setParameter(String zParameterName, Object zParameterValue) {
    this.getParametersMap().put(zParameterName, zParameterValue);
  }
  public void setParameter(String zParameterName, int value) {
    this.getParametersMap().put(zParameterName, new Integer(value));
  }
  public void setParameter(String zParameterName, double value) {
    this.getParametersMap().put(zParameterName, new Double(value));
  }
  public void setParameter(String zParameterName, long value) {
    this.getParametersMap().put(zParameterName, new Long(value));
  }
  public void setParameter(String zParameterName, float value) {
    this.getParametersMap().put(zParameterName, new Float(value));
  }
  public void setParameter(String zParameterName, boolean value) {
    this.getParametersMap().put(zParameterName, new Boolean(value));
  }



  protected JasperReport createReport() throws Exception {
    String sFile = this.getFileName();
    if (sFile==null || (sFile = sFile.trim()).length() < 1) {
      JExcepcion.SendError("Report file not specified");
    }
    if (sFile.toLowerCase().endsWith(".jasper")) {
      return (JasperReport) JRLoader.loadObject(sFile);
    } else {
      return (JasperReport) JasperCompileManager.compileReport(sFile);
    }
  }

  protected JasperPrint createPrint() throws Exception {
    JasperPrint oPrint;
    if (this.oDataSource==null) {
      oPrint = JasperFillManager.fillReport(this.createReport(), this.getParametersMap(), JReportDataSourceProvider.getInstance().getReportConnection());
    } else {
      oPrint = JasperFillManager.fillReport(this.createReport(), this.getParametersMap(), this.oDataSource);
    }
    this.configurePaperType(oPrint);
    return oPrint;
  }


  private void configurePaperType(JasperPrint zPrint) {
    if (this.iPaperSize == PAPER_SIZE_LETTER) {
      zPrint.setPageHeight((int) (PAPER_SIZE_LETTER_HEIGHT * DOTS_PER_INCH));
      zPrint.setPageWidth((int) (PAPER_SIZE_LETTER_WIDTH * DOTS_PER_INCH));

    } else if (this.iPaperSize == PAPER_SIZE_A4) {
      zPrint.setPageHeight((int) (PAPER_SIZE_A4_HEIGHT * DOTS_PER_INCH));
      zPrint.setPageWidth((int) (PAPER_SIZE_A4_WIDTH * DOTS_PER_INCH));

    } else if (this.iPaperSize == PAPER_SIZE_LEGAL) {
      zPrint.setPageHeight((int) (PAPER_SIZE_LEGAL_HEIGHT * DOTS_PER_INCH));
      zPrint.setPageWidth((int) (PAPER_SIZE_LEGAL_WIDTH * DOTS_PER_INCH));
    }
  }

  public void cleanUp() {
    this.sFileName = null;
    this.oDataSource = null;
    this.sTitle = null;
    this.oParameters = null;
  }


  //
  //
  //   report controls configuration methods
  //
  //

  //
  //  set controls values:
  //

  public void setCurrencyParameter(String zControlId, double zValue) throws Exception {
    this.setParameter(zControlId, JRegionalFormatterFactory.getBusinessFormatter().formatCurrency(zValue));
  }
  public void setQuantityParameter(String zControlId, double zValue) throws Exception {
    this.setParameter(zControlId, JRegionalFormatterFactory.getRegionalFormatter().formatNumber(zValue,3));
  }

  //
  //  set controls formats:
  //

  public void setFormat(String zControlId, String zPattern) {
    // to implement
  }
  public void setDateFormat(String zControlId) throws Exception {
    this.setFormat(zControlId, BizUsuario.getUsr().getObjNodo().ObtenerPais().getShortDateFormat());
  }
  public void setTimeFormat(String zControlId) throws Exception {
    this.setFormat(zControlId, BizUsuario.getUsr().getObjNodo().ObtenerPais().getShortTimeFormat());
  }
  public void setQuantityFormat(String zControlId) {
    this.setFormat(zControlId, "#.###");
  }

  public void setCurrencyFormat(String zControlId) throws Exception {
    this.setFormat(zControlId, JRegionalFormatterFactory.getBusinessFormatter().getCurrencyPattern());
  }
  public void setCurrencyFormat(String zControlId, int iFractionDigits) throws Exception {
    DecimalFormat oFormat = new DecimalFormat(JRegionalFormatterFactory.getBusinessFormatter().getCurrencyPattern());
    oFormat.setMinimumFractionDigits(iFractionDigits);
    this.setFormat(zControlId, oFormat.toPattern());
  }
  public void setVariableDecimalsCurrencyFormat(String zControlId) throws Exception {
    this.setVariableDecimalsCurrencyFormat(zControlId, 4);
  }
  public void setVariableDecimalsCurrencyFormat(String zControlId, int zMaxDecimals) throws Exception {
    for (int i=0; i <= zMaxDecimals; i++) {
      this.setCurrencyFormat(zControlId + i, i);
    }
  }

}
