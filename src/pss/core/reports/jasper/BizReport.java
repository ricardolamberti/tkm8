package pss.core.reports.jasper;

import java.io.OutputStream;
import java.util.Locale;

import pss.common.security.BizUsuario;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.www.platform.JConstantes;

public abstract class BizReport extends JRecord implements JPssReportsConstants {


  //////////////////////////////////////////////////////////////////////////////
  //
  //   INSTANCE VARIABLES
  //
  //////////////////////////////////////////////////////////////////////////////
  // report configuration variables
  private String sReportTitle;
  private boolean bPreview;
  private boolean bApplyGeneralConfiguration;
  private boolean bAutoRemoveReport;
  // the report to be printed
  private JReportJasper oReport;


  //////////////////////////////////////////////////////////////////////////////
  //
  //   CONSTRUCTORS
  //
  //////////////////////////////////////////////////////////////////////////////

  public BizReport() throws Exception {
  }



  //////////////////////////////////////////////////////////////////////////////
  //
  //   METHODS
  //
  //////////////////////////////////////////////////////////////////////////////

  public void setReportTitle(String zTitle) {
    this.sReportTitle = zTitle;
  }
  public String getReportTitle() {
    return this.sReportTitle;
  }

  public void setPreview(boolean zPreview) {
    this.bPreview = zPreview;
  }
  public boolean isPreview() {
    return this.bPreview;
  }

  public void setApplyGeneralConfiguration(boolean zApplyGeneralConfiguration) {
    this.bApplyGeneralConfiguration = zApplyGeneralConfiguration;
  }
  public boolean isApplyGeneralConfiguration() {
    return this.bApplyGeneralConfiguration;
  }

  public boolean isAutoRemoveReport() {
    return this.bAutoRemoveReport;
  }
  public void setAutoRemoveReport(boolean zAutoRemoveReport) {
    this.bAutoRemoveReport = zAutoRemoveReport;
  }

  @Override
	public void processInsert() throws Exception {
      doprocessInsert();
  }
  private void doprocessInsert() throws Exception {
    try {
      this.showWaitMessage("Generando reporte");
      this.applyRestrictions();
      this.validateNullsConstraints();
      this.configureControls();
      this.configureFormats();
      this.configureDataSource();
      this.getReport().setTitle(this.getReportTitle());
      this.doReport();
      PssLogger.logInfo("");
      this.clearReport();
    } catch (Exception ex) {
      PssLogger.logInfo("");
      this.clearReport();
      throw ex;
    } catch (Throwable ex) {
      PssLogger.logInfo("");
      this.clearReport();
      throw new Exception(ex); // black solution
    }
  }

  private void showWaitMessage(String zCurrentTask) {
    String sTitle = this.getReportTitle();
    if (sTitle != null && sTitle.length() > 0) {
      PssLogger.logWait(zCurrentTask + ": " + sTitle + " . . .   Por favor espere . . .");
    } else {
      PssLogger.logWait(zCurrentTask + " . . .   Por favor espere . . .");
    }
  }

  public final void WebGenerarReporte( OutputStream out, String zsFormatoReporte ) throws Exception {
    this.getReport();
    this.applyRestrictions();
    this.configureControls();
    this.configureFormats();
    this.configureDataSource();

    // Fuerza los símbolos en los formateadores para cuando el JReport los pida para formatear
    Locale.setDefault( Locale.ITALY );
    this.doWebReport( out, zsFormatoReporte );
  }

  public JReportJasper getReport() throws Exception{
    if (this.oReport == null) {
      this.oReport = this.createReport();
    }
    return this.oReport;
  }

  public void clearReport() throws Exception{
    if (this.oReport != null) {
      if (this.isAutoRemoveReport()) {
        this.oReport.cleanUp();
      }
      this.oReport = null;
    }
  }



  protected boolean isSelected(JString zCampo) throws Exception {
    return !(zCampo.isEmpty() || zCampo.getValue().equals("0"));
  }
  protected void setCalculatedLabel(String sQuery, String sControl, String sCampo) throws Exception {
    JList<String> vFields = JCollectionFactory.createList();
    vFields.addElement(sCampo);
    JMap<String,String> oFields = getCalculatedValue(sQuery,vFields);
    oReport.setCurrencyParameter(sControl, Double.parseDouble(oFields.getElement(sCampo)));
  }
  protected void setCalculatedLabel(String sQuery, String sControl1, String sCampo1, String sControl2, String sCampo2) throws Exception {
    JList<String> vFields = JCollectionFactory.createList();
    vFields.addElement(sCampo1);
    vFields.addElement(sCampo2);
    JMap<String,String> oFields = getCalculatedValue(sQuery,vFields);
    oReport.setCurrencyParameter(sControl1, Double.parseDouble(oFields.getElement(sCampo1)));
    oReport.setCurrencyParameter(sControl2, Double.parseDouble(oFields.getElement(sCampo2)));
  }
  private JMap<String,String> getCalculatedValue(String sQuery, JList<String> oFields) throws Exception {
    return JBaseRegistro.VirtualCreate(this).ExecuteQueryOneRow(oFields,sQuery);
  }




  protected void applyGeneralConfiguration() throws Exception {

    this.setPreview(true);

    // poner imágenes
    this.getReport().setParameter("LOGO", Pss_REPORTS_LOGO_PATH);

    // datos sucursal
    this.getReport().setParameter("CAB_VAL_NODE_NAME", BizUsuario.getUsr().getNodo()+"-"+BizUsuario.getUsr().getObjNodo().GetDescrip());
    this.getReport().setParameter("CAB_VAL_NODE_ADDRESS", BizUsuario.getUsr().getObjNodo().getObjPerson().getObjDomicilioMain().getDomicilioCompleto());
    this.getReport().setParameter("CAB_VAL_NODE_CITY", "Ciudad: " + BizUsuario.getUsr().getObjNodo().getObjPerson().getObjDomicilioMain().getCiudad() );
    this.getReport().setParameter("CAB_VAL_NODE_ZIP", "C.P.: " + BizUsuario.getUsr().getObjNodo().getObjPerson().getObjDomicilioMain().getCodPostal() );
    this.getReport().setParameter("CAB_VAL_ARCHIVO", this.getReport().getFileName());
    this.getReport().setParameter("CAB_VAL_OPERADOR", BizUsuario.getCurrentUser()+"-"+BizUsuario.getCurrentUserName() );
//    try {
//      this.getReport().setParameter("CAB_VAL_VERSION", "Versión: "+JPss.getPssVersion() );
//    } catch (Exception ex) {}

    //seteo el formato de la fecha
    this.getReport().setDateFormat("CAB_VAL_FECHAIMPRESION");
    this.getReport().setTimeFormat("CAB_VAL_HORAIMPRESION");

  }


  protected void doReport() throws Exception {
    // report configuration
    if ( this.isApplyGeneralConfiguration() ) {
      this.applyGeneralConfiguration();
    }

    // output
    if (this.isPreview()) {
      JReportOutputProcessor.getInstance().preview(this.getReport());
    } else {
      JReportOutputProcessor.getInstance().print(this.getReport());
    }
  }

  protected void doWebReport(OutputStream out, String zsFormatoReporte) throws Exception {
    // report configuration:
    this.getReport().setParameter( "LOGO_VISIBLE", false );
    this.getReport().setParameter( "PAGINOTA_VISIBLE", false );
    this.getReport().setParameter( "CAB_VAL_ARCHIVO_VISIBLE", false );
    this.getReport().setParameter("CAB_VAL_OPERADOR", BizUsuario.getCurrentUser()+"-"+BizUsuario.getCurrentUserName() );
    if ( this.isApplyGeneralConfiguration() ) {
      this.applyGeneralConfiguration();
    }

    // output:

    if( zsFormatoReporte.equals( JConstantes.REPORTES_HTML ) ) {
      JReportOutputProcessor.getInstance().exportToHTML(this.getReport(), out);
    } else if( zsFormatoReporte.equals( JConstantes.REPORTES_PDF ) ) {
      JReportOutputProcessor.getInstance().exportToPDF(this.getReport(), out);
    } else {
      JExcepcion.SendError( "Formato de reportes '" + zsFormatoReporte + "' desconocido." );
    }

  }



  //////////////////////////////////////////////////////////////////////////////
  //
  //   METHODS TO OVERRIDE IN SUBCLASSES
  //
  //////////////////////////////////////////////////////////////////////////////

  protected abstract JReportJasper createReport() throws Exception;
  protected abstract void applyRestrictions() throws Exception;
  protected abstract void configureFormats() throws Exception;
  protected abstract void configureControls() throws Exception;
  protected abstract void configureDataSource() throws Exception;



}
