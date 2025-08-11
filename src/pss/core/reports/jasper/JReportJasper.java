package pss.core.reports.jasper;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRRectangle;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JRTextField;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.base.JRBaseSubreport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.type.OrientationEnum;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import pss.JPath;
import pss.JPss;
import pss.JPssVersion;
import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.BizUsuario;
import pss.core.reports.JReport;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.formatters.JRegionalFormatterFactory;
import pss.core.win.JWins;


public class JReportJasper extends JReport implements JPrintConstants {


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
  private Map<String, Object> oParameters;
  private String sTitle;
  private int iPaperSize = PAPER_SIZE_LETTER;
  private JasperPrint oPrint=null;
  private Properties pFontMapProperties=null;
  private JasperReport oJReport=null;
  private JDynamicReport oJDReport=null;
  private boolean hasToRemove;
  
  //////////////////////////////////////////////////////////////////////////////
  //
  //   CONSTRUCTORS
  //
  //////////////////////////////////////////////////////////////////////////////
  public JReportJasper(String zFileName) {
    this.setFileName(zFileName);
    try {
   		this.oJReport = this.createReport();	
    } catch (Exception ex) {
      PssLogger.logDebug(ex, "Could not create Jasper instance " );
    }
  }
  public JReportJasper(String zFileName, JRResultSetDataSource zDataSource) {
    this.setFileName(zFileName);
    this.oDataSource = zDataSource;
    setERDGlobals();
  }

  public JReportJasper(String zFileName, Object zDataSource) {
    this.setFileName(zFileName);
    this.oDataSource = (JRDataSource) zDataSource;
    setERDGlobals();
    try {
   		this.oJReport = this.createReport();	
    } catch (Exception ex) {
      PssLogger.logDebug(ex, "Could not create Jasper instance " );
    }    
  }
  
  public JReportJasper(JWins wins) {
  	try {
  		this.oJDReport = new JDynamicReport(wins);
  		this.oJReport = this.oJDReport.CreateJasper();
  		this.oParameters = oJDReport.getParametersMap();
    } catch (Exception ex) {
      PssLogger.logDebug(ex, "Could not create Jasper instance " );
    }
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
      this.sPath = this.getAbsolutePath(this.sFileName);
    } else {
      this.sFileName = zFileName;
    }
  }
  
 private String getAbsolutePath(String Path) {
	 String tPath = "";
	 tPath = Path.substring(0, Path.lastIndexOf("/") + 1) ;
	 return tPath ;
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


  private Map<String, Object> getParametersMap() {
    if (this.oParameters==null) {
      this.oParameters = new HashMap<String, Object>();
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
  	if (this.oJReport == null) {
	    String sFile = this.getFileName();
	    if (sFile==null || (sFile = sFile.trim()).length() < 1) {
	      JExcepcion.SendError("Report file not specified");
	    }
	    if (sFile.toLowerCase().endsWith(".jasper")) {
	      this.oJReport = (JasperReport) JRLoader.loadObject(sFile);
	    } else {
	    	this.oJReport = (JasperReport) JasperCompileManager.compileReport(sFile);
	    }
  	}
    return this.oJReport;
  }

  protected JasperPrint createPrint() throws Exception {
  	if (this.oPrint == null) {
    	if (this.getWins() != null)
   		 this.oDataSource = this.oJDReport.getDataSourceFromWins();
  		
	    if (this.oDataSource==null) {
	      this.oPrint = JasperFillManager.fillReport(this.createReport(), this.getParametersMap(), JReportDataSourceProvider.getInstance().getReportConnection());
	    } else {
	      this.oPrint = JasperFillManager.fillReport(this.createReport(), this.getParametersMap(), this.oDataSource);
	    }
	    this.configurePaperType(oPrint);
	    
    }
    return this.oPrint;
  }

  public void setAlternativeColorRow() throws Exception {
  	if (this.oJReport == null ) return;
  	JRRectangle rectangle = null; 
		if ( this.oJReport.getDetailSection().getBands()[0] != null ) {
			rectangle = (JRRectangle)this.oJReport.getDetailSection().getBands()[0].getElementByKey("rectangle_alter");
		}	
		if (rectangle != null)
			rectangle.setBackcolor(new Color(255,255,204));
  }
  
  @Override
  protected void ConfiguracionGeneral() throws Exception {
  	super.ConfiguracionGeneral();
  	this.setFormatFieldsPattern(this.oJReport);
  }

  
  public void setFormatFieldsPattern(JasperReport jReport) throws Exception {
  	if (jReport == null ) return;
  	
  	if (jReport.getDetailSection() != null) {
  		if (jReport.getDetailSection().getBands() != null) {
		  	for (int i=0, n=jReport.getDetailSection().getBands().length; i<n; i++) {
		  		if ( jReport.getDetailSection().getBands()[i] != null ) {
		  			JRElement[] elements = jReport.getDetailSection().getBands()[i].getElements() ;
		  			this.processElements(elements);
		  		}
		    }
  		}	
  	}	
  	
  	if (jReport.getGroups() != null) {
	  	for (int i=0, n=jReport.getGroups().length; i<n; i++) {
	  		if (jReport.getGroups()[i].getGroupHeaderSection() != null) {
		  		if (jReport.getGroups()[i].getGroupHeaderSection().getBands() != null) {
			  		for (int h=0, x=jReport.getGroups()[i].getGroupHeaderSection().getBands().length; h<x; h++) {
			  			if ( jReport.getGroups()[i].getGroupHeaderSection().getBands()[h] != null ) {
			  				JRElement[] elements_H = jReport.getGroups()[i].getGroupHeaderSection().getBands()[h].getElements() ;
			  				this.processElements(elements_H);
			  			}
			  		}
		  		}
	  		}
	  		if (jReport.getGroups()[i].getGroupFooterSection() != null) {
		  		if (jReport.getGroups()[i].getGroupFooterSection().getBands() != null) {
			  		for (int h=0, x=jReport.getGroups()[i].getGroupFooterSection().getBands().length; h<x; h++) {
			  			if ( jReport.getGroups()[i].getGroupFooterSection().getBands()[h] != null ) {
			  				JRElement[] elements_F = jReport.getGroups()[i].getGroupFooterSection().getBands()[h].getElements() ;
			  				this.processElements(elements_F);
			  			}
			  		}
		  		}
	  		}
	  	}
  	}
  }

  private void processElements (JRElement[] elems) throws Exception {
  	JRTextField textField = null; 
		for (int h=0, x=elems.length; h<x; h++) {
			if( elems[h] instanceof JRTextField) {
				textField = (JRTextField) elems[h];
				String sClassName = textField.getExpression().getValueClassName();
				String sFieldName = "";
				sFieldName =  this.getFieldNameFromExpression(textField.getExpression().getText()) ;
				textField.setPattern(this.getPatternResolver(sClassName,sFieldName)) ;
			}
			if (elems[h] instanceof JRBaseSubreport) {
				JRBaseSubreport subrep = (JRBaseSubreport) elems[h];
				JRExpression exp = subrep.getExpression();
				JasperReport repObj = getJasperReport(exp.getChunks()[1].getText());
				if (repObj != null) {
					this.setFormatFieldsPattern(repObj) ;
				}
			}
		}
  }
   
  private String getFieldNameFromExpression(String sExpression){
  	String fieldName  = "";
  	int pos1 = JTools.FindInStr(sExpression, "{");
  	int pos2 = JTools.FindInStr(sExpression, "}");
  	fieldName = sExpression.substring(pos1 + 1, pos2);
  	
  	return fieldName;
  }
  
  
  
  private JasperReport getJasperReport(String fileName) throws Exception { 
  	JasperReport subReport = null;
  	if (fileName != null){ 
  		fileName = fileName.replaceAll("\"","" );
  		fileName = fileName.replaceAll("\\+","" ).trim();
  		fileName = this.getAbsolutePath(this.getFileName()) + fileName;
  		subReport = (JasperReport) JRLoader.loadObject(fileName);
  	} 
  	return subReport;
  } 

  
  private String getPatternResolver(String className, String fieldName) throws Exception {
  	String pattern = "";

//  	if (className==null) { 
//  		className = "java.lang.String";
//  	}

  	
  	if (this.getCustomControlPattern() != null) {
	  	if (this.getCustomControlPattern().containsKey(fieldName.toLowerCase())) {
	  		pattern = this.getCustomControlPattern().getElement(fieldName.toLowerCase()) ;
	  		return pattern;
	  	}
  	}
  	
  	
  	if (className.toLowerCase().contains("timestamp")
  		|| className.toLowerCase().contains("date"))
  	{
  		pattern = this.getDatePattern() ;
  	}
  	if (className.toLowerCase().contains("bigdecimal")
  	 || className.toLowerCase().contains("double") 
  	 || className.toLowerCase().contains("float")){
  		pattern = this.getQtyPattern();
  	}
  	if (className.toLowerCase().contains("long")
  	  	 || className.toLowerCase().contains("integer")){
  	  		pattern = "######" ;
  	  	}
  	return pattern;
  }
  
  private void configurePaperType(JasperPrint zPrint) {
  	OrientationEnum orientation = zPrint.getOrientationValue(); //.getOrientation();
  	int pageHeight = zPrint.getPageHeight();
  	int pageWidth = zPrint.getPageWidth();
  	zPrint.setPageHeight(pageHeight);
  	zPrint.setOrientation(orientation);
  	zPrint.setPageWidth(pageWidth);
     }

  
  public  JasperPrint getPrint() throws Exception {
  	return this.createPrint();
  }
  
  public void cleanUp() {
    this.sFileName = null;
    this.oDataSource = null;
    this.sTitle = null;
    this.oParameters = null;
  }

  public void DoReportPDF() throws Exception {
  	JRPdfExporter exporter = new JRPdfExporter();
    
  	exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
  			sPath);
  	exporter.setParameter(JRExporterParameter.FONT_MAP, this.pFontMapProperties);
  	exporter.setParameter(JRExporterParameter.JASPER_PRINT, this.getPrint());
  	exporter.exportReport();
  	
   // JasperExportManager.exportReportToPdfFile(this.getPrint(), sPath);
  }
  
  private void printViaPDF(boolean isAsync) throws Exception {

    // create temp PDF file
    String sTempDir = System.getProperty("java.io.tmpdir", JPath.PssPathReport());
    String sFullPDFFileName = getReportToPDFFileName(sTempDir, getReportTitleAsFileName());
    JasperExportManager.exportReportToPdfFile(this.getPrint(), sFullPDFFileName);
    // invoke acrord32 to print it
    String sAcrord32Path = getAcrobatReader32Path();
    String sPrinter = BizUsuario.getUsr().getObjNodo().GetImpresoraReportes();
    String sCMD;
//    if( JPrinter.ObtenerImpresoraWindows(sPrinter) != null ) {
//      sCMD = "\"" + sAcrord32Path + "\" /p /h /t \"" + sFullPDFFileName + "\" \"" + sPrinter + "\"";
//    } else {
      sCMD = "\"" + sAcrord32Path + "\" /p /h \""+ sFullPDFFileName + "\"";
//    }
    PssLogger.logWait("Imprimiendo reporte...");
    final Process oProcess = Runtime.getRuntime().exec(sCMD);
    this.isPrintingNow = false;
    //this.doRemoveIfNeeded();

    final File oTempPDFFile = new File(sFullPDFFileName);
    final long toWait = Math.max(20000, oTempPDFFile.length() / 20);

    if (isAsync) {
      Thread.sleep(toWait);
      oProcess.destroy();
      PssLogger.logDebug("Close Acrobat window - oProcess.destroy");
      oTempPDFFile.delete();
    } else {
      new Thread() {public void run() {
        try {
          Thread.sleep(toWait);
          oProcess.destroy();
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
  
  
  public void setImage(String zImageId, String zRelativePath) {
    URL resource = JPss.class.getResource(zRelativePath);
    if (resource != null) {
      String path = resource.getPath();
      this.setLabel(zImageId, path);
    }
  }
  
  
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
    this.setLabel(zControlId, zPattern);
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

  public void setFormatCurrency(String zControlId) throws Exception {
    this.setFormat(zControlId,JRegionalFormatterFactory.getBusinessFormatter().getCurrencySymbol() + " " + JRegionalFormatterFactory.getBusinessFormatter().getCurrencyPattern());
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
  
  // Reveer. Cada número, repreenta dpi, así que 72 representa 1 pulgada
  // o sea, que para un tamaño carta de 8,5" x 11", hay que darle una 
  // altura de 11 x 72, y 8.5 x 72
  public void setPageHeight(double zHeight) throws Exception{
    //this.getPrint().setPageHeight((int) zHeight);
  }

  public void setPageWidth(double zWidth) throws Exception{
    //this.getPrint().setPageWidth((int) zWidth);
  }  

  public void setVisible(String zControlId, boolean zVisible) {
  	
  }
  
  public void setLabel(String zLabelId, String zValue) {
    this.setParameter(zLabelId, JLanguage.translate(zValue));
  }
  
  public void DoReportPreview() throws Exception {
    //JasperViewer jviewer = new JasperViewer(this.getPrint(),false); 
    JasperViewer.viewReport(this.getPrint(),false);  	
  }
  
  private void setERDGlobals() {
    if (!ERD_GLOBALS_SET) {
      this.setFontMapLocation();
      ERD_GLOBALS_SET = true;
    }
  }
  private void setFontMapLocation() {
    try {
      URL resource = JPss.class.getResource( ERD_FONT_LOCATOR_FILE_LOCATION );
      if( resource != null ) {
        Properties oDefaultProp = new Properties();
        Properties oNewProp = new Properties();
        String sFontPath = "c:\\WINDOWS" + "\\Fonts\\";        
        oDefaultProp.load( resource.openStream() );
        Enumeration oEn = oDefaultProp.propertyNames();
        while( oEn.hasMoreElements() ) {
          String sProp = (String)oEn.nextElement();
          oNewProp.setProperty( sProp, sFontPath+oDefaultProp.getProperty(sProp) );
        }
        this.pFontMapProperties = oNewProp ;
        
      }
    } catch( Exception ign ) {
      PssLogger.logDebug( ign );
    }
  }  
  
  public void setImportedSQLQueryWithoutSQLConvert(String zControlId, String zValue) throws Exception {
  	this.setParameter("sqlexp", zValue);
  }
  
  public void setImportedSQLQuery(String zControlId, String zValue) throws Exception {
  	this.setParameter("sqlexp", zValue);
  }
  public void setVisibleSeccion(String zControlId, boolean zVisible) {
    this.setParameter(zControlId, (zVisible ? "Y" : "N")  ) ;
  }  

  private synchronized void doRemoveIfNeeded() {
    if (this.hasToRemove) {
      this.hasToRemove = false;
      this.doRemove();
    }
  }
  
  private synchronized void doRemove() {
    this.oPrint = null;
    this.oJDReport = null;
    this.oDataSource = null;
    this.oJReport = null;
  }  
  public synchronized void Remove() {
    if (this.isPrintingNow) {
      this.hasToRemove = true;
    } else {
      this.doRemove();
    }
  }  
  
}
