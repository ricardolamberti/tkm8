package pss.core.reports;


import java.awt.Container;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.standard.MediaSize;

import net.sf.jasperreports.engine.JRDataSource;
import pss.JPss;
import pss.JPssVersion;
import pss.common.security.BizUsuario;
import pss.core.data.BizPssConfig;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.data.interfaces.connections.JBaseJDBC;
import pss.core.reports.jasper.JReportJasper;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JOrderedMap;
import pss.core.tools.formatters.JRegionalFormatterFactory;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

//import com.actuate.ereport.engine.ACJEngine;
//import com.actuate.ereport.engine.IDataSource;
//import com.actuate.ereport.engine.TemplateManager;
//import com.actuate.ereport.output.ACJOutputProcessor;

abstract public class JReport{

  public static final String  REPORTES_PDF = "PDF";
  public static final String  REPORTES_HTML = "HTML";
  public static final String  REPORTES_DHTML = "DHTML";
  public static final String  REPORTES_PRINT = "PRINT";
  public static final String  REPORTES_EXCEL = "EXCEL";

	protected String templateFile;
  protected String sPath;
  private String sTitulo = " Reporte ";
  private int dsType = DS_TYPE_JDBC;
  private boolean preview = true;
  private Object toolBarConfiguration;
  private boolean bConfiguracionGeneral = false;
  private boolean bPreviewLock = false;
  private OutputStream outputStream=null;
  private String type=REPORTES_PRINT;
  private String sQtyPattern = "#,##0.00";
  private String sDatePattern = "dd/MM/yyyy" ;
  private JOrderedMap<String, String> mCustomizedPattern=null;

  
  protected boolean isPrintingNow;
  private static String sPrintViaPDF;
  protected static String sAcrobatReader32Path;
  private boolean hasToRemove;
  private JWins oWins=null;
  private JWinList oWinList=null;
  
  //
  //   ERD objects
  //
//  private JFrame oFrame;
  //private ACJOutputProcessor oOutputProcessor;
  //private TemplateManager template;
  //private IDataSource ds;


  //////////////////////////////////////////////////////////////////////////////
  //
  //   STATIC VARIABLES
  //
  //////////////////////////////////////////////////////////////////////////////
  //
  //   constants
  //
  // datasource types
  public static final int DS_TYPE_JDBC = 0;
  public static final int DS_TYPE_APP = 1;
  // logo file
  private static final String LOGO_REPORTES = "core/ui/Images/logos/";
  // filter constants
  public static final String FILTRO_CHAR_MINIMO = "@@@@";
  public static final String FILTRO_CHAR_MAXIMO = "ZZZZ";
  // paper types
  public static final int LETTER = 1;
  public static final int A4 = 9;
  public static final int LEGAL = 5;
  //
  // global variables
  //
  private static boolean bUseDesigner = false;
  protected static String SUBDIR_REPORTES = "";
  //
  //  global variables to interact with ERD
  //
  protected static boolean ERD_GLOBALS_SET = false;
  protected static final String ERD_FONT_LOCATOR_FILE_LOCATION = "core/reports/Fonts/ACJFontLocator.properties";
  private static boolean ERD_FORMATS_FOR_CURRENT_USER; // by default, false

  //////////////////////////////////////////////////////////////////////////////
  //
  //   CONSTRUCTORS
  //
  //////////////////////////////////////////////////////////////////////////////
  public static JReport createReport(String template) throws Exception {
  	return createReport(template, DS_TYPE_JDBC);
  }
  public static JReport createReport(JWins wins) throws Exception {
  	return new JReportJasper(wins);
  }
  
  public static JReport createReport(String template, int zDsType) throws Exception {
  	if (template.contains(".jod")) {
  		return null;
//  		return new JReportActuate(template, zDsType);
  	}	else {
  		return new JReportJasper(template);
  	}
  }

  public static JReport createReport(String template, Object zDataSource) throws Exception {
  	if (template.contains(".jod")) {
  		return null;
//  		return new JReportActuate(template);
  	}	else {
  		return new JReportJasper(template, zDataSource);
  	}
  }

  //////////////////////////////////////////////////////////////////////////////
  //
  //   METHODS
  //
  //////////////////////////////////////////////////////////////////////////////

  public String GetLogoFile() throws Exception { return LOGO_REPORTES; }
//  public void SetFrame(JFrame zFrame)     { this.oFrame = zFrame  ; }
  public void SetFile(String zFile)       { this.templateFile  = zFile   ; }
  public void SetPath(String zPath)       { this.sPath  = zPath   ; }
  public void setOutputStream(OutputStream value) { this.outputStream = value; }
  public void setType(String value) { this.type = value; }
  public void SetTitulo(String zTitulo)   { this.sTitulo = zTitulo; }
  public void SetConfiguracionGeneral(boolean zValor) { this.bConfiguracionGeneral = zValor;}
  public void SetPreviewLock(boolean zValor) { bPreviewLock = zValor;}
  //public TemplateManager GetTmplMgr()     { return this.template; }
  public static void SetDesigner( boolean zValue ) { bUseDesigner = zValue; }
  public void setDesigner( boolean zValue ) { bUseDesigner = zValue; if (zValue) createDesigner(); }
  public boolean ifConfiguracionGeneral() { return this.bConfiguracionGeneral; }
  public JRDataSource getDataSource() throws Exception { return null ; }
  
  public static boolean ifDesigner()               { return bUseDesigner  ; }

  public void setToolBarConfiguration(Object aToolBarConfiguration) {
    this.toolBarConfiguration = aToolBarConfiguration;
  }
  public void setWins(JWins zValue) {	this.oWins=zValue; }  
  
  public JWins getWins() throws Exception { return this.oWins; } 
  public void setWinList(JWinList zValue) {	this.oWinList=zValue; }  
  
  public JWinList getWinList() throws Exception { return this.oWinList; } 

  public Object getToolBarConfiguration() throws Exception {
  		return null;
  }
  public String getPath() throws Exception  { return this.sPath ; }

  public String getQtyPattern() throws Exception  { return this.sQtyPattern ; }
  public void setQtyPattern(String value) { this.sQtyPattern = value; }
  
  public String getDatePattern() throws Exception  { return this.sDatePattern ; }
  public void setDatePattern(String value) { this.sDatePattern = value; }
  
  private void createEngine(boolean createDataSource) throws Exception {

  }

 /* private TemplateManager getTemplate() {
  	return null;
  } */


  private void createDesigner() {

  }

  private void createDataSource() throws Exception {

  }

  private String getTitle() {
    if (this.sTitulo==null || (this.sTitulo=this.sTitulo.trim()).length() < 1) {
      this.sTitulo = "Reporte";
    }
    return this.sTitulo;
  }

  private PrinterJob createPrinterJob() throws Exception {
  		return null;
  }

  private synchronized void cleanUpJob(PrinterJob zJob, boolean onError) {

  }



  //
  // API Methods
  //
  public void setPreview(boolean zPreview) {
    if (bPreviewLock) return;
    this.preview = zPreview;
  }
  public boolean isPreview() {
    return this.preview;
  }
  public void SetParametersJDBC(String zDriver, String zDbURL, String zUser, String zPassword) throws Exception {

  }
  public void AddTablaJDBC(String zTable) throws Exception {

  }
  public void AddTabla(JRecords zBDs) throws Exception {


  }

  private void openPanelInDesktop(Container zContainer) throws Exception {

  }

  /**
   * Sends this report to an output device, passing it the default Pss reports
   * configuration if the <code>ConfiguracionGeneral</code> flag is on. <br>
   * If the designer flag is on, opens the designer to modify the template this
   * report is based on. Otherwise, opens the preview form or sends the report
   * directly to the printer, depending on whether the preview flag is on or
   * not.
   */
  public void DoReport() throws Exception {
    // aplica la configuración general
    if ( this.ifConfiguracionGeneral() ) ConfiguracionGeneral();
    // manda el reporte
    if ( ifDesigner() ) {
      this.DoReportDesign();
      return;
    } 
    if (type.equals(REPORTES_HTML)) {
    	this.DoReportHTML();
    	return;
    }
    if (type.equals(REPORTES_DHTML)) {
    	this.DoReportDHTML();
    	return;
    }
    if (type.equals(REPORTES_PDF)) {
    	this.DoReportPDF();
    	return;
    }

    if (type.equals(REPORTES_PRINT)) {
    	this.DoReportPrint();
    	return;
    }
  }


  //
  //  methods to manage the report from the preview form
  //
  public void generateReport() throws Exception {
  }
  public void refreshReport(boolean zRegenerate) throws Exception {
  
  }
  protected Container getPreviewDevice() throws Exception {
  		return null;
  }
 
  protected synchronized Object getOutputProcessor() {
    return null;
  }
  
  protected synchronized void recreateOutputProcessor() {
    //this.oOutputProcessor = null;
  }

   private synchronized void doRemoveIfNeeded() {
    if (this.hasToRemove) {
      this.hasToRemove = false;
      this.doRemove();
    }
  }
  private synchronized void doRemove() {

  }
  public synchronized void Remove() {
    if (this.isPrintingNow) {
      this.hasToRemove = true;
    } else {
      this.doRemove();
    }
  }


  private void finalizeEngine() {

  }

  public void setLabel(String zLabelId, String zValue) {
  }
  
  public void setFormat(String zControlId, String zFormat) {
   // this.getTemplate().setFormat(zControlId, zFormat);
  	

  }
  public void setImage(String zImageId, String zRelativePath) {
    URL resource = JPss.class.getResource(zRelativePath);
    if (resource != null) {
      String path = resource.getPath();
      //this.getTemplate().setLabel(zImageId, path);
    }
  }
  public void setVisible(String zControlId, boolean zVisible) {
  }
  
  public void setVisibleSeccion(String zControlId, boolean zVisible) {
    // Supone que las secciones y subsecciones empiezan con el predeterminado del Designer
  }
  public void setORDERBYClause(String zControlId, String zValue) {
  }

  public void setWHEREClause(String zControlId, String zValue) {
  }

  public String getWHEREClause(String zControlId) {
    return "" ;
  }

  public void setImportedSQLQueryWithoutSQLConvert(String zControlId, String zValue) throws Exception {
  }

  public void setImportedSQLQuery(String zControlId, String zValue) throws Exception {
  }
  public void setImportedSQLQueryVoid(String zControlId) {
  }
  public String getImportedSQLQuery(String zControlId) {
    return "";
  }
  public void setParameter(String zParameterId, Object zValue) {
    
  }
  public void setParameterNoFilterChar(String zParameterId) {
  }

  public void setDatasourceType(int zType) {
    this.dsType = zType;
  }



  //
  //   distintos outputs
  //
  public void DoReportHTML() throws Exception {
  }
  public void DoReportDHTML() throws Exception {
  }
  
  public void DoReportCSV() throws Exception {
  }
  
  public void DoReportExcel() throws Exception {
  }
  
  public void DoReportPDF() throws Exception {
  }
  
  public void setAlternativeColorRow() throws Exception {
  }


  public void refreshReport() throws Exception {

  }

  public void DoReportPrint() throws Exception {
    if (this.isPreview()) {
      this.DoReportPreview();
      return;
    } 

    this.DoReportPrint(false, false);
  }

  public synchronized void DoReportPrint(final boolean zShowDialog, boolean async) throws Exception {
    // check if it is printing now
    if (this.isPrintingNow) {
//      UITools.MostrarMensaje(this.getTitle(), "El reporte se está generando. Por favor, espere.");
      return;
    }
    this.isPrintingNow = true;
    PssLogger.logWait("Generando reporte... Por favor, espere...");
    if (async) {
      Thread oPrintThread = new Thread("Pss-Print Thread") { public void run() {
        try {
          processReport(zShowDialog);
        } catch (Exception ex) {
          PssLogger.logError(ex);
          isPrintingNow = false;
          doRemoveIfNeeded();
//          UITools.MostrarError(ex);
          //oOutputProcessor = null;
        }
      }};
      oPrintThread.setPriority(Thread.MIN_PRIORITY);
      oPrintThread.start();
    } else {
      try {
      	processReport(zShowDialog);
      } catch (Exception ex) {
        PssLogger.logInfo("");
        isPrintingNow = false;
        //oOutputProcessor = null;
      }
    }
  }
  
  private void processReport(boolean zShowDialog) throws Exception {
    if (isPrintViaPDF()) {
      this.printViaPDF(true);
    } else {
      this.printDirectly(zShowDialog);
    }
    }

  public void DoReportDesign() throws Exception {

  }

  public void DoReportPreview() throws Exception {

  }

  public String TmpTableGetId() throws Exception {
    return JTools.GetHost();
  }

  public void TmpTableClear(Class<? extends JRecord> zClase) throws Exception {
    JRecords<JRecord> oTmps = new JRecords(zClase);
    oTmps.addFilter("host",TmpTableGetId());
    oTmps.delete();
  }

  public static String getDecimalPattern(int zDecimals) {
    int decimals = Math.max(zDecimals, 0);
    String pattern = "";
    for (int i = 1; i <= decimals; i++) {
      if (i==1) {
        pattern += '.';
      }
      pattern += '0';
    }
    pattern = "#,##0" + pattern;
    return pattern;
  }

  protected void ConfiguracionGeneral() throws Exception {

    this.setPreview(true);

    // poner imágenes
    String LogoFile  = GetLogoFile()  + BizUsuario.getUsr().getObjCompany().getLogo();
    
    URL resource = JPss.class.getResource(LogoFile);
    if (resource != null)
      this.setImage("LOGO", LogoFile);
    else
      this.setVisible("LOGO",false);

    try { 
	    // datos sucursal
    	
    	if ( BizUsuario.getUsr().getNodo().equalsIgnoreCase("") == false ) {
		    this.setLabel("CAB_VAL_NODE_NAME", "^"+BizUsuario.getUsr().getNodo()+"-"+BizUsuario.getUsr().getObjNodo().GetDescrip());
		    this.setLabel("CAB_VAL_NODE_ADDRESS", "^"+BizUsuario.getUsr().getObjNodo().getObjPerson().getObjDomicilioMain().getDomicilioCompleto());
		    this.setLabel("CAB_VAL_NODE_CITY", "Ciudad: ^" + BizUsuario.getUsr().getObjNodo().getObjPerson().getObjDomicilioMain().getCiudad() );
		    this.setLabel("CAB_VAL_NODE_ZIP", "C.P.: ^" + BizUsuario.getUsr().getObjNodo().getObjPerson().getObjDomicilioMain().getCodPostal() );
		    this.setLabel("CAB_LOGO","^"+BizUsuario.getUsr().getObjNodo().GetReporteCabecera());
		    this.setLabel("CAB_VAL_FECHAIMPRESION",  JDateTools.DateToString(BizUsuario.getUsr().getObjNodo().todayGMT()));
    	} else {
		    this.setLabel("CAB_VAL_NODE_NAME", "");
		    this.setLabel("CAB_VAL_NODE_ADDRESS", "");
		    this.setLabel("CAB_VAL_NODE_CITY", "" );
		    this.setLabel("CAB_VAL_NODE_ZIP", "" );
		    this.setLabel("CAB_LOGO","");
		    this.setLabel("CAB_VAL_FECHAIMPRESION",  JDateTools.CurrentDate(JRegionalFormatterFactory.getRegionalFormatter().getDateTimePattern()));
    	}
		    
	    this.setFormatDate("CAB_VAL_FECHAIMPRESION");
      this.setFormatTime("CAB_VAL_HORAIMPRESION");
      
      
    } catch ( Exception e ) {
    	PssLogger.logDebug("No store for the current user: " + BizUsuario.getUsr().GetUsuario());
    }
    this.setLabel("CAB_VAL_ARCHIVO", "^"+getFileName());
    this.setLabel("CAB_VAL_OPERADOR", "^"+BizUsuario.getCurrentUser()+"-"+BizUsuario.getCurrentUserName() );
//    try {this.setLabel("CAB_VAL_VERSION", "Versión: ^"+JPss.getPssVersion() );} catch (Exception ex) {}

    this.setLabel("PIE_OPERADOR", "Operador:");
    this.setLabel("CAB_LBL_FECHA_IMPRESION", "Fecha de Impresión:");
    this.setLabel("CAB_LBL_HORA_IMPRESION", "Hora de Impresión:");
    this.setLabel("CAB_TIT_FECIMPRESION", "Fecha de Impresión:");
    this.setLabel("CAB_TIT_HORAIMPRESION", "Hora de Impresión:");
    this.setLabel("LBL_PAGE", "Página");
    this.setLabel("LBL_OF", "de");
    this.setLabel("LBL000", "Cantidad Total de Registros");
    char user_dec_separator = JRegionalFormatterFactory.getRegionalFormatter().getDecimalSeparator();
    String lLanguage = "es" ;
    String lCountry = "ES" ;
    
    if  ( (user_dec_separator + "").equals(".")) { 
    	// entonces son numeraciones de sistema yankee
      lLanguage = "en" ;
      lCountry = "US" ;
    }
    Locale srLocale = new Locale(lLanguage,lCountry);
    this.setParameter("REPORT_LOCALE",srLocale);
    
    //seteo el formato de la fecha
  }
  
  protected String getFileName() throws Exception {
  	return "";
  }
  public void setFormatDate(String zControlId) throws Exception {
    this.setFormat(zControlId, BizUsuario.getUsr().getObjNodo().ObtenerPais().getShortDateFormat());
  }
  public void setFormatTime(String zControlId) throws Exception {
    this.setFormat(zControlId, BizUsuario.getUsr().getObjNodo().ObtenerPais().getShortTimeFormat());
  }
  public void setFormatCurrency(String zControlId) throws Exception {
    this.setFormat(zControlId, JRegionalFormatterFactory.getBusinessFormatter().getCurrencyPattern());
  }
  public void setFormatCurrencyMaximumDigits(String zControlId) throws Exception {
    DecimalFormat oFormat = new DecimalFormat(JRegionalFormatterFactory.getBusinessFormatter().getCurrencyPattern());
    oFormat.setMaximumFractionDigits(JRegionalFormatterFactory.getAbsoluteBusinessFormatter().getTotalFractionDigits());
    this.setFormat(zControlId, oFormat.toPattern());
  }
  public void setFormatCurrency(String zControlId, int iFractionDigits) throws Exception {
    DecimalFormat oFormat = new DecimalFormat(JRegionalFormatterFactory.getBusinessFormatter().getCurrencyPattern());
    oFormat.setMinimumFractionDigits(iFractionDigits);
    this.setFormat(zControlId, oFormat.toPattern());
  }
  public void setFormatCurrencyMultiFormato(String zControlId) throws Exception {
    setFormatCurrencyMultiFormato(zControlId,4);
  }
  public void setFormatCurrencyMultiFormato(String zControlId, int zMaxDecimals) throws Exception {
    for (int i=0;i<=zMaxDecimals;i++)
      setFormatCurrency(zControlId+i,i);
  }
  public void setLabelCantidad(String zControlId, double zValue) throws Exception {
    setLabelCantidad(zControlId, zValue, 3);
  }
  public void setLabelCantidad(String zControlId, double zValue, int zCantidadDecimales) throws Exception {
    this.setLabel(zControlId,JRegionalFormatterFactory.getRegionalFormatter().formatNumber(zValue,zCantidadDecimales));
  }
  public void setLabelCurrency(String zControlId, double zValue) throws Exception {
    this.setLabel(zControlId,JRegionalFormatterFactory.getBusinessFormatter().formatCurrency(zValue));
  }
  public void setLabelCurrencyMaximumDigits(String zControlId, double zValue) throws Exception {
    this.setLabel(zControlId,JRegionalFormatterFactory.getBusinessFormatter().formatCurrency(zValue,JRegionalFormatterFactory.getAbsoluteBusinessFormatter().getTotalFractionDigits()));
  }

  public void setLabelCurrency(String zControlId, String zValue) throws Exception {
    this.setLabelCurrency(zControlId,Float.parseFloat(zValue));
  }
  public void setFormatCantidad(String zControlId) throws Exception {
    this.setFormat(zControlId, "#.###");
  }

  public void setPageHeight(double zHeight) throws Exception{
  }

  public void setPageWidth(double zWidth) throws Exception{
  }

  public void setSQLQueryWhere(String zControlId, String zValue) throws Exception {
    // Si zValue comienza con "Where", será un reemplazo.
    // Si zValue comienza con "And", será un append al Where existente
    this.setSQLQuery(zControlId,"","",zValue,"","");
  }
  public void setSQLQueryOrder(String zControlId, String zValue) throws Exception {
    this.setSQLQuery(zControlId,"","","","",zValue);
  }
  private void setSQLQuery(String zControlId, String zSelect, String From, String zWhere, String zGroupBy, String zOrder) throws Exception {
    String sSql = getImportedSQLQuery(zControlId).toUpperCase();
    sSql = insertOrder(sSql, zOrder);
    sSql = insertWhere(sSql, zWhere);
    setImportedSQLQuery(zControlId,sSql);
  }
  private String insertOrder(String zSql, String newOrder){
    if (newOrder.equals("")) return zSql;
    int comienzo = zSql.indexOf("ORDER");
    int total = zSql.length();
    if (comienzo == -1) comienzo = total;
    return zSql.substring(0,comienzo)+ newOrder;
  }
  private String insertWhere(String zSql, String newWhere){
    if (newWhere.equals("")) return zSql;

    String sSqlResultado="", sPostWhere;
    int comienzo, agrupar, ordenar, total, finWhere;

    comienzo =  zSql.indexOf("WHERE");
    agrupar =  zSql.indexOf("GROUP");
    ordenar = zSql.indexOf("ORDER");
    total = zSql.length();
    finWhere = agrupar;
    if(finWhere == -1) finWhere= ordenar;
    if(finWhere == -1) finWhere = total;

    sPostWhere = zSql.substring(finWhere,total);
    if (newWhere.trim().toUpperCase().substring(0,5).equals("WHERE"))
      sSqlResultado = zSql.substring(0,comienzo) + newWhere + sPostWhere;
    else
      sSqlResultado = zSql.substring(0,finWhere-1) + " " + newWhere + " " + sPostWhere;

    return sSqlResultado;
  }

  public int getDefaultPaperSize() throws Exception {
    PrintService ps = PrintServiceLookup.lookupDefaultPrintService();
    if( ps == null )  return A4;
    if (! ps.isAttributeCategorySupported(MediaSize.class) ) return A4;
    return Integer.parseInt(ps.getAttribute((Class)MediaSize.class).toString());
//    Field field = ps.getClass().getDeclaredField("defPaper");
//    field.setAccessible(true);
//    return field.getInt(ps);
  }

  /**
   * Retorna la única conexión que se utiliza para las consultas del JReport.
   * Cuando el driver utilizado es JDataConnect, se crea una conexión especial.
   * En otro caso, utiliza la misma conexión que Pss
   * @return la conexión a ser usada por el JReport
   */
  public static Connection getConnectionReport() throws Exception {
    JBaseJDBC oBaseDefault = (JBaseJDBC)JBDatos.GetBases().getPrivateCurrentDatabase();
    return oBaseDefault.getDriver().getConnectionForReports();
  }
//    JBDato oBaseDefault = JBDatos.GetBases().GetBaseDefault();
//    JBDato oJBDCRep = null;
//    String nameReport = oBaseDefault.GetName() + "_REPORT";
//
//    try {
//      oJBDCRep = JBDatos.GetBases().GetBaseByName(nameReport);
//    } catch (JDatabaseNotFound e) {
//      oJBDCRep = JBDatos.getBaseReport();
//      JBDatos.GetBases().AddItem(oJBDCRep);
//      oJBDCRep.Abrir();
//      oJBDCRep.BeginTransaction();
//    }
//    return oJBDCRep.GetConnection();
//  }




  //
  //  PRNIT DIRECTLY/VIA PDF IMPLEMENTATION
  //

  public static boolean isPrintViaPDF() throws Exception {
    if (JReport.sPrintViaPDF == null) {
    	JReport.sPrintViaPDF = "N";
    	JReport.sPrintViaPDF = BizPssConfig.getPssConfig().getCachedValue("GENERAL", "PRINT_VIA_PDF", "N");
    }
    return JReport.sPrintViaPDF.equalsIgnoreCase("Y") || JReport.sPrintViaPDF.equalsIgnoreCase("S");
  }
  private void printDirectly(boolean zShowDialog) throws Exception {

  }

  private void printViaPDF(boolean isAsync) throws Exception {

  }

  private String getReportTitleAsFileName() {
    String sFileName = this.getTitle();
    String sNewFileName = "";
    for (int i = 0; i < sFileName.length(); i++) {
      char c = sFileName.charAt(i);
      if (Character.isLetterOrDigit(c) || Character.isSpaceChar(c)) {
        sNewFileName += String.valueOf(c);
      } else {
        sNewFileName += "_";
      }
    }
    return JPssVersion.getPssTitle() + " - " + sNewFileName;
  }
  private static String getAcrobatReader32Path() {
    if (sAcrobatReader32Path==null) {
      String[] possibleDrives = {"C:/", "D:/", "E:/"};
      String homeDrive = System.getProperty("os.home");
      String exeName = "AcroRd32.exe";
      String[] possiblePaths = {
        "Archivos de programa/Adobe/Acrobat 6.0/Reader/",
        "Program Files/Adobe/Acrobat 6.0/Reader/",
        "Archivos de programa/Adobe/Acrobat 5.0/Reader/",
        "Program Files/Adobe/Acrobat 5.0/Reader/",
        "Archivos de programa/Adobe/Acrobat 4.0/Reader/",
        "Program Files/Adobe/Acrobat 4.0/Reader/",
        "Archivos de programa/Adobe/Acrobat 3.0/Reader/",
        "Program Files/Adobe/Acrobat 3.0/Reader/"
      };
      for (int i = 0; i < possiblePaths.length && sAcrobatReader32Path==null; i++) {
        String sDir = possiblePaths[i].trim().replace('\\', '/');
        if (!sDir.endsWith("/")) {
          sDir += "/";
        }
        for (int j = 0; j < possibleDrives.length && sAcrobatReader32Path==null; j++) {
          String sDrive = possibleDrives[j].trim().replace('\\', '/');
          if (!sDrive.endsWith("/")) {
            sDrive += "/";
          }
          String sPossibleFileName = sDrive + sDir + exeName;
          if (new File(sPossibleFileName).exists()) {
            sAcrobatReader32Path = sPossibleFileName;
          }
        }
      }
      if (sAcrobatReader32Path==null) {
        sAcrobatReader32Path = exeName;
      }
    }
    return sAcrobatReader32Path;
  }
  private static synchronized String getReportToPDFFileName(String zDirectoryPath, String zRequestedFileName) throws Exception {
    // normalize the path
    String sPath = zDirectoryPath.trim().replace('\\', '/');
    if (!sPath.endsWith("/")) {
      sPath += "/";
    }
    // determine a suitable file name
    String sFileName = zRequestedFileName + ".PDF";
    for (int i = 1; new File(sPath + sFileName).exists(); i++) {
      sFileName = zRequestedFileName + " (" + String.valueOf(i) + ").PDF";
    }
    new FileOutputStream(sPath + sFileName).close();
    return sPath + sFileName;
  }

  //
  //  ERD globals managing methods
  //
  public static boolean hasToFormatForCurrentUser() {
    return ERD_FORMATS_FOR_CURRENT_USER;
  }
  public static void setHasFormatForCurrentUser(boolean zFormatsForCurrentUser) {
    ERD_FORMATS_FOR_CURRENT_USER = zFormatsForCurrentUser;
  }
  private static void setERDGlobals() {
    if (!ERD_GLOBALS_SET) {
      setFontMapLocation();
      ERD_GLOBALS_SET = true;
    }
  }
  private static void setFontMapLocation() {
    try {
      URL resource = JPss.class.getResource( ERD_FONT_LOCATOR_FILE_LOCATION );
      if( resource != null ) {
        Properties oDefaultProp = new Properties();
        Properties oNewProp = new Properties();
//        String sFontPath = JNativeTools.getEnv("SystemRoot") + "\\Fonts\\";
        String sFontPath = "c:\\WINDOWS" + "\\Fonts\\";        
        oDefaultProp.load( resource.openStream() );
//        Properties oDefaultProp = System.getProperties();
        Enumeration oEn = oDefaultProp.propertyNames();
        while( oEn.hasMoreElements() ) {
          String sProp = (String)oEn.nextElement();
          oNewProp.setProperty( sProp, sFontPath+oDefaultProp.getProperty(sProp) );
        }
        //ACJEngine.setFontMap( oNewProp );
      }
    } catch( Exception ign ) {
      PssLogger.logDebug( ign );
    }
  }
  
	public void AddCustomControlPattern(String sControl, String sPattern) throws Exception {
		if (this.mCustomizedPattern == null)
			this.mCustomizedPattern =JCollectionFactory.createOrderedMap();
		
			this.mCustomizedPattern.addElement(sControl.toLowerCase(), sPattern);
	}
	
	public JOrderedMap<String,String>  getCustomControlPattern() throws Exception {
		return this.mCustomizedPattern ;
	}


}
