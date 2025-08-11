package pss.core.reports;

import java.io.OutputStream;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.data.interfaces.connections.JBaseJDBC;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

abstract public class JBDReportes extends JRecord {

  private JReport oReport;
  private String sTitle;
  private String sType=JReport.REPORTES_PRINT;
  private OutputStream outputStream;
  private String outputFile;
  private boolean bAutoRemoveReport;
  private JWins oWins=null;
  private JWinList oWinList=null;


  public JBDReportes() throws Exception {
  }

  public void setOutputStream(OutputStream value) {
    this.outputStream = value;
  }
  public void setOutputFile(String value) {
    this.outputFile = value;
  }
  public void setType(String value) {
    this.sType = value;
  }
  public String getTtpe() {
  	return this.sType;
  }

  public void setReportTitle(String zTitle) {
    this.sTitle = zTitle;
  }
  public String getReportTitle() {
    return this.sTitle;
  }
  
  public boolean isAutoRemoveReport() {
    return this.bAutoRemoveReport;
  }
  public void setAutoRemoveReport(boolean zAutoRemoveReport) {
    this.bAutoRemoveReport = zAutoRemoveReport;
  }

  public boolean needsConfirmationOnPreview() throws Exception {
    return !this.getReport().ifConfiguracionGeneral();
  }

  public void setWins(JWins zValue) {
    this.oWins = zValue;
  }
  public JWins getWins() {return this.oWins; }
  
  public void setWinList(JWinList zValue) {
    this.oWinList = zValue;
  }
  public JWinList getWinList() {return this.oWinList; }
  
  @Override
	public void processInsert() throws Exception {
    if (!JBDatos.GetBases().getPrivateCurrentDatabase().isTransactionInProgress()) {
      execProcessInsert();
      return;
    }
    imprimir();
  }
  
  public void imprimir() throws Exception {
    try {
    	this.getReport();
      verificarRestricciones();
      validateNullsConstraints();
      configurarControles();
      configurarFormatos();
      configurarSQL();
      //configPaper(); comentado por MRO 16/10/2008. No le encuentro sentido configurar el papel.
      oReport.SetTitulo(this.getReportTitle());
      oReport.setOutputStream(this.outputStream);
      oReport.SetPath(this.outputFile);
      oReport.setType(this.sType);
      oReport.setWins(this.oWins);
      oReport.setWinList(this.oWinList);
      oReport.DoReport();
      this.setAutoRemoveReport(true);
    } catch (Exception ex) {
      PssLogger.logError(ex);
      throw ex;
    } catch (Throwable ex) {
      throw new Exception(ex); // black solution
    } finally {
    	this.clearReport();
    }
  }

/*  public final void WebGenerarReporte( OutputStream out, String zsFormatoReporte ) throws Exception {
    getReport();
    verificarRestricciones();
    validateNullsConstraints();
    configurarControles();
    configurarFormatos();
    configurarSQL();
    configPaper();

    // Fuerza los símbolos en los formateadores para cuando el JReport los pida para formatear
//    Locale.setDefault( Locale.ITALY );
    oReport.generateWebReport( out, zsFormatoReporte );
  }
*/
  private void configPaper() throws Exception{
    if(oReport.getDefaultPaperSize() == JReport.LETTER){
      oReport.setPageHeight(27.94);
      oReport.setPageWidth(21.59);
    } else if(oReport.getDefaultPaperSize() == JReport.LEGAL){
      oReport.setPageHeight(35.56);
      oReport.setPageWidth(21.59);
    } else {//if(oReport.getDefaultPaperSize() == oReport.A4)
      oReport.setPageHeight(29.7);
      oReport.setPageWidth(21.0);
    }
  }

  public JReport getReport() throws Exception{
    if (this.oReport == null) {
      this.oReport = this.obtenerReporte();
    }
    return this.oReport;
  }
  
  public void clearReport() throws Exception{
    if (this.oReport != null) {
      if (this.isAutoRemoveReport()) {
        this.oReport.Remove();
      }
      this.oReport = null;
    }
  }

  abstract protected JReport obtenerReporte() throws Exception;
  abstract protected void verificarRestricciones() throws Exception;
  abstract protected void configurarFormatos() throws Exception;
  abstract protected void configurarControles() throws Exception;
  abstract protected void configurarSQL() throws Exception;

  protected boolean isSelected(JString zCampo) throws Exception {
    return !(zCampo.isEmpty() || zCampo.getValue().equals("0"));
  }
  protected void setCalculatedLabel(String sQuery, String sControl, String sCampo) throws Exception {
    JList<String> vFields = JCollectionFactory.createList();
    vFields.addElement(sCampo);
    JMap<String,String> oFields = getCalculatedValue(sQuery,vFields);
    oReport.setLabelCurrency(sControl,oFields.getElement(sCampo));
  }

  protected void setCalculatedLabelCantidad(String sQuery, String sControl, String sCampo) throws Exception {
    setCalculatedLabelCantidad(sQuery, sControl, sCampo, 3);
  }
  protected void setCalculatedLabelCantidad(String sQuery, String sControl, String sCampo, int zCantidadDecimales) throws Exception {
    JList<String> vFields = JCollectionFactory.createList();
    vFields.addElement(sCampo);
    JMap<String,String> oFields = getCalculatedValue(sQuery,vFields);
    double dCantidad = Double.parseDouble(oFields.getElement(sCampo));
    getReport().setLabelCantidad(sControl,dCantidad,zCantidadDecimales);
  }
  protected void setCalculatedLabel(String sQuery, String sControl1, String sCampo1, String sControl2, String sCampo2) throws Exception {
    JList<String> vFields = JCollectionFactory.createList();
    vFields.addElement(sCampo1);
    vFields.addElement(sCampo2);
    JMap<String,String> oFields = getCalculatedValue(sQuery,vFields);
    oReport.setLabelCurrency(sControl1,oFields.getElement(sCampo1));
    oReport.setLabelCurrency(sControl2,oFields.getElement(sCampo2));
  }
  private JMap<String,String> getCalculatedValue(String sQuery, JList<String> oFields) throws Exception {
    return JBaseRegistro.VirtualCreate(this).ExecuteQueryOneRow(oFields,sQuery);
  }
  public boolean isSQL92() throws Exception {
    return ((JBaseJDBC)JBDatos.GetBases().getPrivateCurrentDatabase()).isSQL92();
  }
  
	public JRecords<?> getRecords() throws Exception {
		return null;
	}
	
  
	public boolean next() throws  JRException
	{
		boolean value = false;
		try {
			value = this.nextRecord();
		} catch (Exception ex) {
			return false;
		}
		return value ;
	}

	 private boolean nextRecord() throws Exception {
			return this.getRecords().nextRecord();
		 
	 }
	 public Object getFieldValue(JRField field) throws JRException
	 {
	 	Object value = null;
	 	String fieldName = field.getName();
	 	@SuppressWarnings("unused")
		String fieldValue = "";
	 	try {
	 		JRecord thisRecord = this.getRecords().getRecord();
	 		if (thisRecord.existProperty(fieldName)) {
	 			fieldValue = thisRecord.getProp(fieldName).toString(); 
	 			value = thisRecord.getProp(fieldName).asRawObject() ;
	 		}
	 	} catch (Exception ex) {
	 		return null;
	 	}
	 return value;
	 } 

}
