package pss.core.reports;


import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.standard.MediaSize;

import com.actuate.ereport.datasrcmgrs.appdatasrc.AppDataHandler;
import com.actuate.ereport.datasrcmgrs.jdbcdatasrc.JDBCHandler;
import com.actuate.ereport.designer.ACJDesigner;
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.IDataSource;
import com.actuate.ereport.engine.TemplateManager;
import com.actuate.ereport.output.ACJOutputProcessor;

import pss.JPath;
import pss.JPss;
import pss.JPssVersion;
import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.BizUsuario;
import pss.core.data.BizPssConfig;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.data.interfaces.connections.JBaseJDBC;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JList;
import pss.core.tools.formatters.JRegionalFormatterFactory;


public class JReportActuate extends JReport {
	
	protected String templateFile;
  private String sTitulo = " Reporte ";
  private int dsType = DS_TYPE_JDBC;
  private boolean preview = true;
  private JPssPreviewToolBar toolBarConfiguration;
  private boolean bConfiguracionGeneral = false;
  private boolean bPreviewLock = false;
  private OutputStream outputStream=null;
  private String type=REPORTES_PRINT;

  
  private static String sPrintViaPDF;
  private boolean hasToRemove;

  //
  //   ERD objects
  //
//  private JFrame oFrame;
  private ACJEngine engine;
  private ACJOutputProcessor oOutputProcessor;
  private TemplateManager template;
  private IDataSource ds;
  private ACJDesigner designer;


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
  private static boolean ERD_FORMATS_FOR_CURRENT_USER; // by default, false

  //////////////////////////////////////////////////////////////////////////////
  //
  //   CONSTRUCTORS
  //
  //////////////////////////////////////////////////////////////////////////////
  public JReportActuate(String template) throws Exception {
    this(template, DS_TYPE_JDBC);
  }
  public JReportActuate(String template, int zDsType) throws Exception {
    setERDGlobals();

    URL resource = JPss.class.getResource(template);
    if (resource != null) {
    	templateFile = resource.getPath();
    } else {
    	templateFile = template;
    }
    this.setDatasourceType(zDsType);
    this.createEngine(true);
    if ( bUseDesigner ) {
      createDesigner();
    }
    else {
      oOutputProcessor = new ACJOutputProcessor();
    }
  }
  public String getFileName() {
    return this.templateFile;
  }

  //////////////////////////////////////////////////////////////////////////////
  //
  //   METHODS
  //
  //////////////////////////////////////////////////////////////////////////////

//  public void SetFrame(JFrame zFrame)     { this.oFrame = zFrame  ; }
  public void SetFile(String zFile)       { this.templateFile  = zFile   ; }
  public void SetPath(String zPath)       { this.sPath  = zPath   ; }
  public void setOutputStream(OutputStream value) { this.outputStream = value; }
  public void setType(String value) { this.type = value; }
  public void SetTitulo(String zTitulo)   { this.sTitulo = zTitulo; }
  public void SetConfiguracionGeneral(boolean zValor) { this.bConfiguracionGeneral = zValor;}
  public void SetPreviewLock(boolean zValor) { bPreviewLock = zValor;}
  public TemplateManager GetTmplMgr()     { return this.template; }
  public static void SetDesigner( boolean zValue ) { bUseDesigner = zValue; }
  public void setDesigner( boolean zValue ) { bUseDesigner = zValue; if (zValue) createDesigner(); }
  public boolean ifConfiguracionGeneral() { return this.bConfiguracionGeneral; }

  public static boolean ifDesigner()               { return bUseDesigner  ; }

  public void setToolBarConfiguration(JPssPreviewToolBar aToolBarConfiguration) {
    this.toolBarConfiguration = aToolBarConfiguration;
  }
  public JPssPreviewToolBar getToolBarConfiguration() throws Exception {
    if (this.toolBarConfiguration==null) {
      this.toolBarConfiguration = new JPssPreviewToolBar();
      this.toolBarConfiguration.removeControl(JPssPreviewToolBar.CONTROL_TOC);
      this.toolBarConfiguration.removeControl(JPssPreviewToolBar.CONTROL_ABOUT);
      JTools.MakeDirectory( JPath.PssPathReport() );
      this.toolBarConfiguration.setOutputDirectory(JPath.PssPathReport());
    }
    return this.toolBarConfiguration;
  }


  private void createEngine(boolean createDataSource) throws Exception {
    this.engine = new ACJEngine();
    ACJEngine.setDebugMode(true);
//    oFrame = new JFrame();
//    oFrame.getContentPane().setLayout(new BorderLayout(1,1));
//    oFrame.addWindowListener(new WindowAdapter() {
//      public void windowClosing(WindowEvent we) {
//        we.getWindow().removeWindowListener(this);
//        Remove();
//      }
//    });
    if (createDataSource) {
      this.createDataSource();
    }
    this.engine.setDataSource(this.ds);
    this.engine.readTemplate(templateFile);
    this.template = this.engine.getTemplateManager();
  }

  private TemplateManager getTemplate() {
    if (this.template==null || this.engine==null) {
      try {
        this.createEngine(false);
      } catch (Exception ex) {
        PssLogger.logError(ex);
      }
    }
    return this.template;
  }
  private ACJEngine getEngine() throws Exception {
    if (this.engine==null) {
      this.createEngine(false);
    }
    return this.engine;
  }


  private void createDesigner() {
//    designer = new ACJDesigner(oFrame, engine, true, true);
//    designer.updateDesigner();
//    designer.setTemplateFile(templateFile);
//    template.setUnitType("CENTIMETERS");  // esto es porque cada vez que se instancia el designer se cambia a INCHES
//                                          // sin razon aparente
//    oOutputProcessor = designer.getClient();
  }

  private void createDataSource() throws Exception {
    if (this.dsType == DS_TYPE_JDBC) {
      JDBCHandler jdbcDs = new JDBCHandler();
      jdbcDs.connect(JReportActuate.getConnectionReport(), false);
      jdbcDs.makeSelective();
      this.ds = jdbcDs;
    } else if (this.dsType == DS_TYPE_APP) {
      this.ds  = new AppDataHandler();
    }
  }

  private String getTitle() {
    if (this.sTitulo==null || (this.sTitulo=this.sTitulo.trim()).length() < 1) {
      this.sTitulo = "Reporte";
    }
    return this.sTitulo;
  }

  private PrinterJob createPrinterJob() throws Exception {
//    System.setProperty("sun.java2d.print.pipeline", "raster");
    PrinterJob pj = PrinterJob.getPrinterJob();
    pj.setJobName(JPssVersion.getPssTitle() + " - " + this.getTitle());

    String sPrinter = BizUsuario.getUsr().getObjNodo().GetImpresoraReportes();
//    PrintService oService = UITools.ObtenerImpresoraWindows( sPrinter );
//    if( oService != null ) {
//      pj.setPrintService( oService );
//    }

    return pj;
//    Win32PrinterJob oJob = new Win32PrinterJob();
//    oJob.setJobName(JPssVersion.getPssTitle() + " - " + this.getTitle());
//    return oJob;
  }

  private synchronized void cleanUpJob(PrinterJob zJob, boolean onError) {
    if (zJob != null) {
//      if (zJob instanceof WPrinterJob) {
//        this.cleanUpWPrinterJob(zJob, onError);
//      } else {
        zJob.cancel();
//      }
    }
    this.isPrintingNow = false;
  }


/*  private void cleanUpWPrinterJob(PrinterJob zJob, boolean onError) {
    WindowsPrinterJobAccessor accessor = new WindowsPrinterJobAccessor((WPrinterJob) zJob);
    accessor.invokeMethod("endDoc", null);
    if (onError) {
      accessor.invokeMethod("cancelDoc", null);
    }
    new WeakReference(zJob);
  }
*/


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
    JDBCHandler jdbcDs = (JDBCHandler) ds;
    jdbcDs.makeSelective();
    jdbcDs.Connect(zDriver, zDbURL, zUser, zPassword, false);
  }
  @SuppressWarnings("unchecked")
	public void AddTablaJDBC(String zTable) throws Exception {
    JDBCHandler dsbt = (JDBCHandler) ds;
    int tableTypes = -1;
    tableTypes += JDBCHandler.TABLE;
    Vector<String> tables = dsbt.getTableNames();
    if (tables==null) {
      tables = new Vector<String>(5);
    }
    tables.add(zTable);
    dsbt.setSelectiveProperties(tableTypes, tables);
    dsbt.useTables(tables);
  }
  public void AddTabla(JRecords zBDs) throws Exception {
     JRecord oBD = (JRecord) zBDs.getBasedClass().newInstance();
    JList aDefinition = oBD.GetDesignFields();
    Vector<Object> fields = new Vector<Object>(Arrays.asList(aDefinition.toArray()));
    String sTableName = oBD.getTableForReport();
    Collection oBDCollection = zBDs.getCollection();
    Class<? extends JRecord> oClass = oBD.getClass();
    try {
      oClass.getMethod("getReportProp", new Class[] {Object.class});
    } catch (Exception ex) {
      JExcepcion.SendError("Falta implementar método #getReportProp en la clase " + oClass.getName());
    }
    // creo el data handler
    AppDataHandler dsapp = (AppDataHandler) ds;
    dsapp.setTableDefinition(sTableName, oClass, fields);
    dsapp.passTableData(sTableName, oBDCollection);

  }

//  private void openPanelInDesktop(Container zContainer) throws Exception {
//    int iconNumber = 5;
//    ImageIcon icon = GuiIconGalerys.GetGlobal().getIcon(GuiIconos.GetGlobal().buscarIcono(iconNumber).GetFile());
////    UITools.desktop().getCurrentDesktop().openForm(
////      zContainer, null, "Vista previa", icon,
////      new JAct() {public void Do() {Remove();}}
////      );
//  }

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
    this.getOutputProcessor().setReportData(this.getEngine().generateReport(), null);
  }
  public void refreshReport(boolean zRegenerate) throws Exception {
    this.getOutputProcessor().refreshReport(zRegenerate);
  }

  protected synchronized ACJOutputProcessor getOutputProcessor() {
    if (this.oOutputProcessor==null) {
      this.oOutputProcessor = new ACJOutputProcessor();
    }
    return this.oOutputProcessor;
  }
  protected synchronized void recreateOutputProcessor() {
    this.oOutputProcessor = null;
  }




  // Manda un PDF con el reporte a través del stream
/*  public void generateWebReport( OutputStream out, String zsFormatoReporte ) throws Exception {

    setVisible( "LOGO", false );
    setVisible( "PAGINOTA", false );
    setVisible( "CAB_VAL_ARCHIVO", false );
    setLabel("CAB_VAL_OPERADOR", BizUsuario.getCurrentUser()+"-"+BizUsuario.getCurrentUserName() );
    if ( this.ifConfiguracionGeneral() ) ConfiguracionGeneral();
    getOutputProcessor().setReportData( this.getEngine().generateReport(), null );

    if( zsFormatoReporte.equals( JConstantes.REPORTES_HTML ) ) {
      //getOutputProcessor().setDHTMLProperty( "OutputStream", out );
    	getOutputProcessor().setDHTMLProperty( "FileName", "c:\\pepe.html" );
      getOutputProcessor().setDHTMLProperty("TableOfContents", Boolean.FALSE );
      getOutputProcessor().setDHTMLProperty("PreserveColors", Boolean.TRUE);
      getOutputProcessor().setDHTMLProperty("PreserveFonts", Boolean.TRUE);
      getOutputProcessor().setDHTMLProperty("PreservePageBreaks", Boolean.TRUE);
      getOutputProcessor().generateDHTML();
    } else if( zsFormatoReporte.equals( JConstantes.REPORTES_PDF ) ) {
//      getOutputProcessor().refreshReport( true );
      getOutputProcessor().setPDFProperty( "OutputStream", out );
      getOutputProcessor().generatePDF();
    } else {
      JExcepcion.SendError( "Formato de reportes '" + zsFormatoReporte + "' desconocido." );
    }
  }
*/

  private synchronized void doRemoveIfNeeded() {
    if (this.hasToRemove) {
      this.hasToRemove = false;
      this.doRemove();
    }
  }
  private synchronized void doRemove() {
//    this.oFrame = null;
    if (this.engine != null) {
      this.finalizeEngine();
      this.engine = null;
    }
    this.recreateOutputProcessor();
    this.template = null;
    this.ds = null;
    this.designer = null;
  }
  public synchronized void Remove() {
    if (this.isPrintingNow) {
      this.hasToRemove = true;
    } else {
      this.doRemove();
    }
  }


  private void finalizeEngine() {
    try {
      this.getEngine().finalizeEngine();
    } catch (Throwable ex) {
      PssLogger.logDebug(ex, "Engine could not be finalized");
    }

  }

  public void setLabel(String zLabelId, String zValue) {
    this.getTemplate().setLabel(zLabelId, JLanguage.translate(zValue));
  }
  public void setFormat(String zControlId, String zFormat) {
    this.getTemplate().setFormat(zControlId, zFormat);

  }
  public void setImage(String zImageId, String zRelativePath) {
    URL resource = JPss.class.getResource(zRelativePath);
    if (resource != null) {
      String path = resource.getPath();
      this.getTemplate().setLabel(zImageId, path);
    }
  }
  public void setVisible(String zControlId, boolean zVisible) {
    this.getTemplate().setVisible(zControlId, zVisible);
  }
  public void setVisibleSeccion(String zControlId, boolean zVisible) {
    // Supone que las secciones y subsecciones empiezan con el predeterminado del Designer
    this.getTemplate().setVisible("SEC_"+zControlId, zVisible);
    this.getTemplate().setVisible("GH_"+zControlId, zVisible);
    this.getTemplate().setVisible("DTL_"+zControlId, zVisible);
    this.getTemplate().setVisible("GF_"+zControlId, zVisible);
  }
  public void setORDERBYClause(String zControlId, String zValue) {
    this.getTemplate().setORDERBYClause(zControlId, zValue);
  }

  public void setWHEREClause(String zControlId, String zValue) {
    this.getTemplate().setWHEREClause(zControlId, zValue);
  }

  public String getWHEREClause(String zControlId) {
    return this.getTemplate().getWHEREClause(zControlId);
  }

  public void setImportedSQLQueryWithoutSQLConvert(String zControlId, String zValue) throws Exception {
    this.getTemplate().setImportedSQLQuery(zControlId, zValue);
  }

  public void setImportedSQLQuery(String zControlId, String zValue) throws Exception {
    //JDebugPrint.logDebugSQL("Report SQL = "+zValue);
    JBaseJDBC oBaseDato=(JBaseJDBC)JBDatos.GetBases().getPrivateCurrentDatabase();
    PssLogger.logDebugSQL("Report SQL = "+oBaseDato.convertSqlToANSISql86(zValue));
    this.getTemplate().setImportedSQLQuery(zControlId, oBaseDato.convertSqlToANSISql86(zValue));
  }
  public void setImportedSQLQueryVoid(String zControlId) {
    this.getTemplate().setImportedSQLQuery(zControlId, "select * from NOD_NODO where 1=2");
  }
  public String getImportedSQLQuery(String zControlId) {
    return this.getTemplate().getImportedSQLQuery(zControlId);
  }
  public void setParameter(String zParameterId, Object zValue) {
    this.getTemplate().setParamValue(zParameterId, zValue);
  }
  public void setParameterNoFilterChar(String zParameterId) {
    setParameter(zParameterId+"a", FILTRO_CHAR_MINIMO);
    setParameter(zParameterId+"b", FILTRO_CHAR_MAXIMO);
  }
/*
  public void setParameterNoFilterNum(String zParameterId) {
    this.setParameter(zParameterId, oReport.FILTRO_NUM_MINIMO);
    this.setParameter(zParameterId, oReport.FILTRO_NUM_MAXIMO);
  }
*/
  public void setDatasourceType(int zType) {
    this.dsType = zType;
  }



  //
  //   distintos outputs
  //
  public void DoReportHTML() throws Exception {
    getOutputProcessor().setReportData(this.getEngine().generateReport(), null);
    if (outputStream!=null)
    	getOutputProcessor().setHTMLProperty( "OutputStream", outputStream );
    else
    	getOutputProcessor().setHTMLProperty("FileName", this.sPath);
    
    getOutputProcessor().setHTMLProperty("TableOfContents", Boolean.FALSE );
    getOutputProcessor().setHTMLProperty("PreserveColors", Boolean.TRUE);
    getOutputProcessor().setHTMLProperty("PreserveFonts", Boolean.TRUE);
    getOutputProcessor().setHTMLProperty("PreservePageBreaks", Boolean.TRUE);
    getOutputProcessor().setHTMLProperty("HorizontalSpacing", Boolean.TRUE);
    getOutputProcessor().generateHTML();
  }
  public void DoReportDHTML() throws Exception {
    getOutputProcessor().setReportData(this.getEngine().generateReport(), null);
    if (outputStream!=null)
    	getOutputProcessor().setDHTMLProperty( "OutputStream", outputStream );
    else
    	getOutputProcessor().setDHTMLProperty("FileName", this.sPath);

    getOutputProcessor().setDHTMLProperty("TableOfContents", Boolean.FALSE );
    getOutputProcessor().setDHTMLProperty("PreserveColors", Boolean.TRUE);
    getOutputProcessor().setDHTMLProperty("PreserveFonts", Boolean.TRUE);
    getOutputProcessor().setDHTMLProperty("PreservePageBreaks", Boolean.FALSE);
    getOutputProcessor().setDHTMLProperty("HorizontalSpacing", Boolean.FALSE);
    getOutputProcessor().generateDHTML();
  }
  public void DoReportCSV() throws Exception {
    getOutputProcessor().setReportData(this.getEngine().generateReport(), null);
    getOutputProcessor().setCSVProperty("FileName", this.sPath);
    getOutputProcessor().generateCSV();
  }
  public void DoReportExcel() throws Exception {
    getOutputProcessor().setReportData(this.getEngine().generateReport(), null);
    getOutputProcessor().setExcelProperty("FileName", this.sPath);
    getOutputProcessor().generateExcel();
  }
  public void DoReportPDF() throws Exception {
    getOutputProcessor().setReportData(this.getEngine().generateReport(), null);
    if (outputStream!=null)
    	getOutputProcessor().setPDFProperty( "OutputStream", outputStream );
    else
    	getOutputProcessor().setPDFProperty("FileName", this.sPath);    
    getOutputProcessor().generatePDF();
  }


  public void refreshReport() throws Exception {
    this.getOutputProcessor().setReportData(this.getEngine().generateReport());
    this.getOutputProcessor().refreshReport(true);
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
          oOutputProcessor = null;
        }
      }};
      oPrintThread.setPriority(Thread.MIN_PRIORITY);
      oPrintThread.start();
    } else {
      try {
      	processReport(zShowDialog);
      } catch (Exception ex) {
        PssLogger.logError(ex);
        isPrintingNow = false;
        oOutputProcessor = null;
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
    designer.getRelations().clear();
    ACJDesigner.setDebugMode(true);
    designer.addDataSource(ds);
//    if (UITools.desktop().isDesktopActive()) {
//      oFrame.setTitle("Designer");
//      this.openPanelInDesktop(designer);
//    } else {
//      oFrame.getContentPane().add(designer, BorderLayout.CENTER);
//      oFrame.pack();
//      oFrame.setVisible(true);
//    }
  }

  public void DoReportPreview() throws Exception {
    this.recreateOutputProcessor();
    GuiReportPreview oPreview = new GuiReportPreview();
    oPreview.GetcDato().setReport(this);
    oPreview.SetTitle(this.getTitle() + "^ -"+ JLanguage.translate("Vista previa"));
    oPreview.showQueryForm();
  }

  public String TmpTableGetId() throws Exception {
    return JTools.GetHost();
  }

  @SuppressWarnings("unchecked")
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
    URL resource = JPss.class.getResource(GetLogoFile());
    if (resource != null)
      this.setImage("LOGO", GetLogoFile());
    else
      this.setVisible("LOGO",false);

    // datos sucursal
    this.setLabel("CAB_VAL_NODE_NAME", "^"+BizUsuario.getUsr().getNodo()+"-"+BizUsuario.getUsr().getObjNodo().GetDescrip());
    this.setLabel("CAB_VAL_NODE_ADDRESS", "^"+BizUsuario.getUsr().getObjNodo().getObjPerson().getObjDomicilioMain().getDomicilioCompleto());
    this.setLabel("CAB_VAL_NODE_CITY", "Ciudad: ^" + BizUsuario.getUsr().getObjNodo().getObjPerson().getObjDomicilioMain().getCiudad() );
    this.setLabel("CAB_VAL_NODE_ZIP", "C.P.: ^" + BizUsuario.getUsr().getObjNodo().getObjPerson().getObjDomicilioMain().getCodPostal() );
    this.setLabel("CAB_VAL_ARCHIVO", "^"+templateFile);
    this.setLabel("CAB_VAL_OPERADOR", "^"+BizUsuario.getCurrentUser()+"-"+BizUsuario.getCurrentUserName() );
    try {this.setLabel("CAB_VAL_VERSION", "Versión: ^"+JPss.getPssVersion() );} catch (Exception ex) {}
    this.setLabel("CAB_LOGO","^"+BizUsuario.getUsr().getObjNodo().GetReporteCabecera());

    this.setLabel("PIE_OPERADOR", "Operador:");
    this.setLabel("CAB_LBL_FECHA_IMPRESION", "Fecha de Impresión:");
    this.setLabel("CAB_LBL_HORA_IMPRESION", "Hora de Impresión:");
    this.setLabel("CAB_TIT_FECIMPRESION", "Fecha de Impresión:");
    this.setLabel("CAB_TIT_HORAIMPRESION", "Hora de Impresión:");

    //seteo el formato de la fecha
    this.setFormatDate("CAB_VAL_FECHAIMPRESION");
    this.setFormatTime("CAB_VAL_HORAIMPRESION");

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
    this.getTemplate().setPageHeight(zHeight);
  }

  public void setPageWidth(double zWidth) throws Exception{
    this.getTemplate().setPageWidth(zWidth);
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

  @SuppressWarnings("unchecked")
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
    if (JReportActuate.sPrintViaPDF == null) {
    	JReportActuate.sPrintViaPDF = "N";
      JReportActuate.sPrintViaPDF = BizPssConfig.getPssConfig().getCachedValue("GENERAL", "PRINT_VIA_PDF", "N");
    }
    return JReportActuate.sPrintViaPDF.equalsIgnoreCase("Y") || JReportActuate.sPrintViaPDF.equalsIgnoreCase("S");
  }
  private void printDirectly(boolean zShowDialog) throws Exception {
    PrinterJob pj = this.createPrinterJob();
    try {
      this.getOutputProcessor().promptParams(false);
      this.getOutputProcessor().setReportData(this.getEngine().generateReport());
      this.getOutputProcessor().setPRINTERProperty("SilentPrint", zShowDialog ? Boolean.FALSE : Boolean.TRUE);
      this.getOutputProcessor().setPRINTERProperty("PrinterJob", pj);
      PrinterJob usedPrintJob = this.getOutputProcessor().generatePRINTOUT();
      // limpio la info
      this.getEngine().printingDone();
      // cancelo el printer job
      this.cleanUpJob(pj, false);
      if (pj != usedPrintJob) {
        // en realidad pj y usedPrintJob debería ser el mismo objeto...
        this.cleanUpJob(usedPrintJob, false);
      }
    } catch(Throwable ex) {
      // cancelo el printer job
      this.cleanUpJob(pj, true);
      PssLogger.logError(ex);
      if (ex instanceof Exception) {
        throw (Exception) ex;
      }
    }
  }

  private void printViaPDF(boolean isAsync) throws Exception {

    // create temp PDF file
    String sTempDir = System.getProperty("java.io.tmpdir", JPath.PssPathReport());
    String sFullPDFFileName = getReportToPDFFileName(sTempDir, getReportTitleAsFileName());
    getOutputProcessor().setReportData(this.getEngine().generateReport(), null);
    getOutputProcessor().setPDFProperty("FileName", sFullPDFFileName);
    getOutputProcessor().generatePDF();
    // invoke acrord32 to print it
    String sAcrord32Path = getAcrobatReader32Path();
    String sPrinter = BizUsuario.getUsr().getObjNodo().GetImpresoraReportes();
    String sCMD;
//    if( UITools.ObtenerImpresoraWindows(sPrinter) != null ) {
//      sCMD = "\"" + sAcrord32Path + "\" /p /h /t \"" + sFullPDFFileName + "\" \"" + sPrinter + "\"";
//    } else {
//      sCMD = "\"" + sAcrord32Path + "\" /p /h \""+ sFullPDFFileName + "\"";
//    }
    PssLogger.logWait("Imprimiendo reporte...");
//    final Process oProcess = Runtime.getRuntime().exec(sCMD);
    isPrintingNow = false;
    this.doRemoveIfNeeded();
    oOutputProcessor = null;

    final File oTempPDFFile = new File(sFullPDFFileName);
    final long toWait = Math.max(20000, oTempPDFFile.length() / 20);

    if (isAsync) {
      Thread.sleep(toWait);
//      oProcess.destroy();
      PssLogger.logDebug("Close Acrobat window - oProcess.destroy");
      oTempPDFFile.delete();
    } else {
      new Thread() {public void run() {
        try {
          Thread.sleep(toWait);
//          oProcess.destroy();
          PssLogger.logDebug("Close Acrobat window - oProcess.destroy");
          oTempPDFFile.delete();
        } catch (Exception ex) {
          PssLogger.logDebug(ex, "Could not delete temp file: " + oTempPDFFile.getAbsolutePath());
        }
      }}.start();
    }

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
        ACJEngine.setFontMap( oNewProp );
      }
    } catch( Exception ign ) {
      PssLogger.logDebug( ign );
    }
  }



/*  private static class WindowsPrinterJobAccessor {
    private static JMap methods = JCollectionFactory.createMap();
    private static JMap fields = JCollectionFactory.createMap();

    private WPrinterJob oPrinterJob;

    public WindowsPrinterJobAccessor(WPrinterJob zWindowsPrinterJob) {
      this.oPrinterJob = zWindowsPrinterJob;
    }

    public void setField(String zFieldName, Object zValue) {
      try {
        this.getField(zFieldName).set(this.oPrinterJob, zValue);
      } catch (Exception ex) {
        JDebugPrint.logDebug(ex, "Error setting field '" + zFieldName + "' in a WPrinterJob");
      }
    }
    public Object invokeMethod(String zMethodName, Object param1) {
      return this.invokeMethod(zMethodName, param1, null, null);
    }
    public Object invokeMethod(String zMethodName, Object param1, Object param2) {
      return this.invokeMethod(zMethodName, param1, param2, null);
    }
    public Object invokeMethod(String zMethodName, Object param1, Object param2, Object param3) {
      try {
        if (param1==null && param2==null && param3==null) {
          return this.getMethod(zMethodName).invoke(this.oPrinterJob, null);
        } else {
          return this.getMethod(zMethodName).invoke(this.oPrinterJob, new Object[] {param1, param2, param3});
        }
      } catch (Exception ex) {
        JDebugPrint.logDebug(ex, "Error invoking method '" + zMethodName + "' in a WPrinterJob");
        return null;
      }
    }

    private static Field getField(String fName) {
      Field requestedField = (Field) fields.getElement(fName);
      if (requestedField==null) {
        Field[] fieldsArr;
        Field field = null;
        ArrayList fieldList = new ArrayList();
        fieldList.addAll(Arrays.asList(sun.awt.windows.WPrinterJob.class.getDeclaredFields()));
        fieldList.addAll(Arrays.asList(sun.awt.windows.WPrinterJob.class.getFields()));
        fieldList.addAll(Arrays.asList(sun.print.RasterPrinterJob.class.getDeclaredFields()));
        fieldList.addAll(Arrays.asList(sun.print.RasterPrinterJob.class.getFields()));
        fieldsArr = new Field[fieldList.size()];
        fieldList.toArray(fieldsArr);
        for (int i = 0; i < fieldsArr.length; i++) {
          field = fieldsArr[i];
          if (field.getName().equals(fName)) {
            field.setAccessible(true);
            requestedField = field;
            break;
          }
        }
        fields.addElement(fName, requestedField);
      }
      return requestedField;
    }

    private static Method getMethod(String mName) {
      Method requestedMethod = (Method) methods.getElement(mName);
      if (requestedMethod==null) {
        Method[] methodsArr;
        Method method = null;
        ArrayList methodList = new ArrayList();
        methodList.addAll(Arrays.asList(sun.awt.windows.WPrinterJob.class.getDeclaredMethods()));
        methodList.addAll(Arrays.asList(sun.awt.windows.WPrinterJob.class.getMethods()));
        methodList.addAll(Arrays.asList(sun.print.RasterPrinterJob.class.getDeclaredMethods()));
        methodList.addAll(Arrays.asList(sun.print.RasterPrinterJob.class.getMethods()));
        methodsArr = new Method[methodList.size()];
        methodList.toArray(methodsArr);
        for (int i = 0; i < methodsArr.length; i++) {
          method = methodsArr[i];
          if (method.getName().equals(mName)) {
            method.setAccessible(true);
            requestedMethod = method;
            break;
          }
        }
        methods.addElement(mName, requestedMethod);
      }
      return requestedMethod;
    }

  }
*/


}
