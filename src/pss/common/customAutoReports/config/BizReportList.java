package pss.common.customAutoReports.config;

import java.io.File;
import java.util.Date;

import pss.JPath;
import pss.common.customAutoReports.scheduler.BizAutoReportSchedule;
import pss.common.event.mail.BizMailCasilla;
import pss.common.event.mail.JMailSend;
import pss.common.security.BizUsuario;
import pss.core.reports.JBDReportes;
import pss.core.reports.JReport;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.BizVirtual;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JActFileGenerate;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormControles;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormCDatetimeResponsive;
import pss.core.winUI.responsiveControls.JFormComboResponsive;
import pss.core.winUI.responsiveControls.JFormWinLOVResponsive;

public class BizReportList extends JRecord {
	
	public static final String REP_BY_CARRIER="RBYCARRIER";
	public static final String REP_BY_MONTH="RBYMONTH";
	public static final String REP_SALES_BY_CONCEPT="RSBYCON";
	public static final String REP_CRM_BY_BUDGET="RCCBYBUDGET";
	public static final String REP_CASHDRAWER_NODO="RCDNODO";
	public static final String REP_CTACTE_DEUDA="RCTACTEDEUDA";
	public static final String PROC_EXP_FACTURAS="PROCEXPFACTURA";
	
	public static final String TYPE_REPORT="RE";
	public static final String TYPE_JWINACTION="JW";
	
	public static String INI_MES = "INI_MES";
	public static String FIN_MES = "FIN_MES";
	public static String INI_SEMANA = "INI_SEMANA";
	public static String FIN_SEMANA = "FIN_SEMANA";
	public static String DIA_ANT = "DIA_ANT";
	
  private JString pCompany = new JString();
  private JLong pReportId = new JLong();
  private JString pReportName = new JString();
  private JString pReportFantasyName = new JString();
  private JString pReportSource = new JString();
  private JString pReportForm = new JString();
  private JString pReportWin = new JString();
  private JString pReportDesc = new JString(){
		public void preset() throws Exception {
			pReportDesc.setValue(ObtenerReportDescription());
		}
	};
	private JDate pfechaEjecucion = new JDate();
	private JString pMailList = new JString();
	private JString pMailListBCC = new JString();
	private JString pUser = new JString();
	private JString pType = new JString();
  private JString pReportTypeDesc = new JString(){
		public void preset() throws Exception {
			pReportTypeDesc.setValue(getTipoReportesDescr());
		}
	};
	private JInteger pWinActionId = new JInteger();
	private String file_name_to_send = "";
	
//  private Object wRecordSet;
  private JRecord recordSource;
  private JBaseForm formSource;
  private JWin winSource;
  private JRecords<BizReportField> campos;
  private JRecords<BizAutoReportSchedule> schedule;
	private JFormControles oControles = null;
	private static JRecords<BizVirtual> tiposFechas = null;
	private static JMap<String,String> tipoReportes;
	private BizUsuario oUser = null;
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public void setReportSource(String zValue) throws Exception {    pReportSource.setValue(zValue);  }
  public String getReportSource() throws Exception {     return pReportSource.getValue();  }
  public void setIdReport(long zValue) throws Exception {    pReportId.setValue(zValue);  }
  public long getIdReport() throws Exception {     return pReportId.getValue();  }
  public void setReportForm(String zValue) throws Exception {    pReportForm.setValue(zValue);  }
  public String getReportForm() throws Exception {     return pReportForm.getValue();  }
  public void setReportWin(String zValue) throws Exception {    pReportWin.setValue(zValue);  }
  public String getReportWin() throws Exception {     return pReportWin.getValue();  }
  public void setReportName(String zValue) throws Exception {    pReportName.setValue(zValue);  }
  public String getReportName() throws Exception {     return pReportName.getValue();  }
  public void setFechaEjecucion( Date zValue) throws Exception { pfechaEjecucion.setValue(zValue); }
  public Date getFechaEjecucion() throws Exception { return pfechaEjecucion.getValue();}
  public void setReportFantasyName(String zValue) throws Exception {    pReportFantasyName.setValue(zValue);  }
  public String getReportFantasyName() throws Exception {     return pReportFantasyName.getValue();  }
  public void setMailList(String zValue) throws Exception {    pMailList.setValue(zValue);  }
  public String getMailList() throws Exception {     return pMailList.getValue();  }
  public void setUser(String zValue) throws Exception {    pUser.setValue(zValue);  }
  public String getUser() throws Exception {     return pUser.getValue();  }
  public void setMailListBCC(String zValue) throws Exception {    pMailListBCC.setValue(zValue);  }
  public String getMailListBCC() throws Exception {     return pMailListBCC.getValue();  }
  public void setReportType(String zValue) throws Exception {    pType.setValue(zValue);  }
  public String getReportType() throws Exception {     return pType.getValue();  }
  public void setWinActionId(int zValue) throws Exception {    pWinActionId.setValue(zValue);  }
  public int getWinActionId() throws Exception {     return pWinActionId.getValue();  }
  
  
  /**
   * Constructor de la Clase
   */
  public BizReportList() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "report_id", pReportId );
    this.addItem( "report_name", pReportName );
    this.addItem( "report_fantasy_name", pReportFantasyName );
    this.addItem( "report_source", pReportSource );
    this.addItem( "report_form", pReportForm );
    this.addItem( "report_win", pReportWin );
    this.addItem( "report_mail_list", pMailList );
    this.addItem( "report_mail_list_bcc", pMailListBCC );
    this.addItem( "report_user", pUser );
    this.addItem( "report_type", pType );
    this.addItem( "report_win_action_id", pWinActionId );
    this.addItem( "report_type_desc", pReportTypeDesc );
    this.addItem( "report_desc", pReportDesc );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 15 );
    this.addFixedItem( KEY, "report_id", "Reporte", true, true, 18);
    this.addFixedItem( FIELD, "report_name", "Reporte", true, true, 20 );
    this.addFixedItem( FIELD, "report_user", "Usuario", true, true, 50 );
    this.addFixedItem( FIELD, "report_fantasy_name", "Reporte", true, false, 100 );
    this.addFixedItem( FIELD, "report_source", "Reporte", true, true, 255 );
    this.addFixedItem( FIELD, "report_form", "Form", true, true, 255 );
    this.addFixedItem( FIELD, "report_win", "Win", true, true, 255 );
    this.addFixedItem( FIELD, "report_mail_list", "Mails", true, false, 2000 );
    this.addFixedItem( FIELD, "report_mail_list_bcc", "Copia Oculta", true, false, 2000 );
    this.addFixedItem( FIELD, "report_type", "Tipo", true, true, 2 );
    this.addFixedItem( FIELD, "report_win_action_id", "Accion Win", true, false, 10 );
    this.addFixedItem( VIRTUAL, "report_type_desc", "Tipo", true, true, 255 );
    this.addFixedItem( VIRTUAL, "report_desc", "Reporte", true, true, 255 );
  }
  
  /**
   * Returns the table name
   */
  public String GetTable() { return "REPORT_AUTO_LIST"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( String zCompany, long zIdReporte ) throws Exception { 
    addFilter( "company",  zCompany ); 
    addFilter( "report_id",  zIdReporte ); 
    return read(); 
  } 
  
  public JRecords<BizReportField> getObjReportFields() throws Exception {
  	if (this.campos!=null) return this.campos;
  	JRecords<BizReportField> r = this.getReportFields();
  	r.toStatic();
  	return (this.campos=r);
  }

  public JRecords<BizAutoReportSchedule> getObjReportSchedule() throws Exception {
  	if (this.schedule!=null) return this.schedule;
  	JRecords<BizAutoReportSchedule> r = this.getReportSchedules();
  	r.toStatic();
  	return (this.schedule=r);
  }

  public BizUsuario getObjUsuario() throws Exception {
  	if (this.oUser!=null) return this.oUser;
  	BizUsuario r = new BizUsuario();
  	r.dontThrowException(true);
  	if (r.Read(this.getUser())) {
  			this.oUser = r ;
  			return this.oUser;
  	}
  	return null;
  }
  
  private JRecords<BizReportField> getReportFields() throws Exception {
  	JRecords<BizReportField> r = new JRecords<BizReportField>(BizReportField.class);
  	r.addFilter("company", this.getCompany());
  	r.addFilter("report_id", this.getIdReport());
  	r.addOrderBy("orden");
  	r.readAll();
  	return r;
  }
  
  private JRecords<BizAutoReportSchedule> getReportSchedules() throws Exception {
  	JRecords<BizAutoReportSchedule> r = new JRecords<BizAutoReportSchedule>(BizAutoReportSchedule.class);
  	r.addFilter("company", this.getCompany());
  	r.addFilter("report_id", this.getIdReport());
  	r.readAll();
  	return r;
  }

  public void processDelete() throws Exception {
  	this.getObjReportFields().processDeleteAll();
  	this.getObjReportSchedule().processDeleteAll();
  	super.processDelete();
  }
  
  public void processInsert() throws Exception {
  	if (this.pReportId.isNull()) {
  		BizReportList max = new BizReportList();
  		max.addFilter("company", pCompany.getValue());
  		this.pReportId.setValue(max.SelectMaxLong("report_id")+1);
  	}
  	this.pReportForm.setValue(this.ObtenerReportForm());
  	this.pReportSource.setValue(this.ObtenerReportSource());
  	this.pReportWin.setValue(this.ObtenerReportWin());
  	super.processInsert();
  }
  
  @Override
  public void processUpdate() throws Exception {
  	this.pReportForm.setValue(this.ObtenerReportForm());
  	this.pReportSource.setValue(this.ObtenerReportSource());
  	this.pReportWin.setValue(this.ObtenerReportWin());
  	super.processUpdate();
  }
  
	private String ObtenerReportForm() throws Exception {
		return this.getFormClass().findVirtualKey(String.valueOf(pReportName.getValue())).getDescrip();
	}
	private String ObtenerReportSource() throws Exception {
		return this.getBizClass().findVirtualKey(String.valueOf(pReportName.getValue())).getDescrip();
	}
	private String ObtenerReportWin() throws Exception {
		return this.getWinClass().findVirtualKey(String.valueOf(pReportName.getValue())).getDescrip();
	}
	private String ObtenerReportDescription() throws Exception {
		return this.getReportList().findVirtualKey(String.valueOf(pReportName.getValue())).getDescrip();
	}

	public JRecords<BizVirtual> getReportList() throws Exception {
		JRecords<BizVirtual> maps = JRecords.createVirtualBDs();
		maps.addItem(JRecord.virtualBD(String.valueOf(REP_BY_CARRIER), "Reporte por Aerolínea", 1));
		maps.addItem(JRecord.virtualBD(String.valueOf(REP_BY_MONTH), "Reporte por Mes", 1));
		maps.addItem(JRecord.virtualBD(String.valueOf(REP_SALES_BY_CONCEPT), "Reporte de Ventas", 1));
		maps.addItem(JRecord.virtualBD(String.valueOf(REP_CRM_BY_BUDGET), "Reporte de Presupuestos", 1));
		maps.addItem(JRecord.virtualBD(String.valueOf(REP_CASHDRAWER_NODO), "Reporte de Caja", 1));
		maps.addItem(JRecord.virtualBD(String.valueOf(REP_CTACTE_DEUDA), "Reporte de Deuda", 1));
		maps.addItem(JRecord.virtualBD(String.valueOf(REP_CTACTE_DEUDA), "Reporte de Deuda", 1));
		maps.addItem(JRecord.virtualBD(String.valueOf(PROC_EXP_FACTURAS), "Exportación Facturas", 1));
		return maps;
	}
	
	public JRecords<BizVirtual> getBizClass() throws Exception {
		JRecords<BizVirtual> maps = JRecords.createVirtualBDs();
		maps.addItem(JRecord.virtualBD(String.valueOf(REP_BY_CARRIER), "pss.tourism.products.air.reports.BizReporteTicketsByCarrier", 1));
		maps.addItem(JRecord.virtualBD(String.valueOf(REP_BY_MONTH), "pss.tourism.products.air.reports.BizReporteTicketsByMonth", 1));
		maps.addItem(JRecord.virtualBD(String.valueOf(REP_SALES_BY_CONCEPT), "pss.tourism.products.air.reports.concepts.BizReporteSalesByConcept", 1));
		maps.addItem(JRecord.virtualBD(String.valueOf(REP_CRM_BY_BUDGET), "pss.erp.crm.reports.BizReporteCrmByCotizacion", 1));
		maps.addItem(JRecord.virtualBD(String.valueOf(REP_CASHDRAWER_NODO), "pss.erp.cashDrawer.reports.BizReporteCashDrawerAgrup", 1));
		maps.addItem(JRecord.virtualBD(String.valueOf(REP_CTACTE_DEUDA), "pss.erp.ctacte.reportes.BizReporteCtaCteDeuda", 1));
		maps.addItem(JRecord.virtualBD(String.valueOf(PROC_EXP_FACTURAS), "pss.erp.Interfaces.globers.BizTransGlobers", 1));
		return maps;
	}
	public JRecords<BizVirtual> getFormClass() throws Exception {
		JRecords<BizVirtual> maps = JRecords.createVirtualBDs();
		maps.addItem(JRecord.virtualBD(String.valueOf(REP_BY_CARRIER), "pss.tourism.products.air.reports.FormReporteTicketsByCarrier", 1));
		maps.addItem(JRecord.virtualBD(String.valueOf(REP_BY_MONTH), "pss.tourism.products.air.reports.FormReporteTicketsByMonth", 1));
		maps.addItem(JRecord.virtualBD(String.valueOf(REP_SALES_BY_CONCEPT), "pss.tourism.products.air.reports.concepts.FormReporteSalesByConcept", 1));
		maps.addItem(JRecord.virtualBD(String.valueOf(REP_CRM_BY_BUDGET), "pss.erp.crm.reports.FormReporteCrmByCotizacion", 1));		
		maps.addItem(JRecord.virtualBD(String.valueOf(REP_CASHDRAWER_NODO), "pss.erp.cashDrawer.reports.FormReporteCashDrawerAgrup", 1));		
		maps.addItem(JRecord.virtualBD(String.valueOf(REP_CTACTE_DEUDA), "pss.erp.ctacte.reportes.FormReporteCtaCteDeuda", 1));
		maps.addItem(JRecord.virtualBD(String.valueOf(PROC_EXP_FACTURAS), "pss.erp.Interfaces.globers.FormTransGlobers", 1));
		return maps;
	}
	public JRecords<BizVirtual> getWinClass() throws Exception {
		JRecords<BizVirtual> maps = JRecords.createVirtualBDs();
		maps.addItem(JRecord.virtualBD(String.valueOf(REP_BY_CARRIER), "pss.tourism.products.air.reports.GuiReporteTicketsByCarrier", 1));
		maps.addItem(JRecord.virtualBD(String.valueOf(REP_BY_MONTH), "pss.tourism.products.air.reports.GuiReporteTicketsByMonth", 1));
		maps.addItem(JRecord.virtualBD(String.valueOf(REP_SALES_BY_CONCEPT), "pss.tourism.products.air.reports.concepts.GuiReporteSalesByConcept", 1));
		maps.addItem(JRecord.virtualBD(String.valueOf(REP_CRM_BY_BUDGET), "pss.erp.crm.reports.GuiReporteCrmByCotizacion", 1));
		maps.addItem(JRecord.virtualBD(String.valueOf(REP_CASHDRAWER_NODO), "pss.erp.cashDrawer.reports.GuiReporteCashDrawerAgrup", 1));		
		maps.addItem(JRecord.virtualBD(String.valueOf(REP_CTACTE_DEUDA), "pss.erp.ctacte.reportes.GuiReporteCtaCteDeuda", 1));
		maps.addItem(JRecord.virtualBD(String.valueOf(PROC_EXP_FACTURAS), "pss.erp.Interfaces.globers.GuiTransGlobers", 1));
		return maps;
	}
  
  public void setObjRecordSource(JRecord value) throws Exception {
  	this.recordSource=value;
  }
  
	public JRecord getObjRecordSource() throws Exception {
		if (this.recordSource!=null) return this.recordSource;
		JRecord r = (JRecord)Class.forName(this.getReportSource()).newInstance();
		return (this.recordSource=r);
	}

	public JBaseForm getObjFormSource() throws Exception {
		if (this.formSource!=null) return this.formSource;
		PssLogger.logInfo("llego a getObjFormSource y this.getReportForm() es " + this.getReportForm());
		JBaseForm r = (JBaseForm)Class.forName(this.getReportForm()).newInstance();
		return (this.formSource=r);
	}
	public JWin getObjWinSource() throws Exception {
		if (this.winSource!=null) return this.winSource;
		JWin r = (JWin)Class.forName(this.getReportWin()).newInstance();
		return (this.winSource=r);
	}
  
	public JWin getFilledWinSource() throws Exception {
  	JWin source = this.getObjWinSource();
  	source.setRecord(this.getObjRecordSource());
  	this.fillRecordProperties(source.getRecord());
  	PssLogger.logInfo("Paso 3");
  	return source;
	}

	private void fillRecordProperties(JRecord record) throws Exception {
		PssLogger.logInfo("llego a fillRecordProperties");
	 	JIterator<JFormControl> iter = this.getControles().GetItems();
	 	PssLogger.logInfo("paso el iter");
	 	this.getObjReportFields().convertToHash("campo");
	 	PssLogger.logInfo("Voy a entrar al while");
  	while (iter.hasMoreElements()) {
  		JFormControl c = iter.nextElement();
  		PssLogger.logInfo("campo " + c.getName());
  		if (c.getName().equals("company")) {
  			if (this.getObjUsuario()==null) return;
  			record.getProp(c.getName()).setValue(this.getObjUsuario().getCompany());
  		} else {
  			BizReportField campo = (BizReportField) this.getObjReportFields().findInHash(c.getName());
  			if (campo!=null) {
  				if (c.GetTipoDato().equals(JBaseForm.DATE))
  					record.getProp(c.getName()).setValue(this.getFechaResolved(this.getFechaEjecucion(),campo.getValue()));
  				else
  					record.getProp(c.getName()).setValue(campo.getValue());
  			} else {
  				if (c.getValorDefault()!=null)
  					record.getProp(c.getName()).setValue(c.getValorDefault().asRawObject());
  			}
  		}
  	}		
		record.setStatic(true);
	}
	
  public JFormControles getControles() throws Exception {
  	if (oControles!=null) return oControles ;
		JRecord r = this.getObjRecordSource();
		JWin w =  this.getObjWinSource();
		JBaseForm f = this.getObjFormSource();
		f.SetWin(w);
		w.setRecord(r);
		f.InicializarPanel(w);
		oControles = new JFormControles(f,null);
		oControles = f.getInternalPanel().getControles();
  	return oControles ; 
  }
 
  public JMap<String, String> getCamposGallery() throws Exception {
  	JMap<String, String> map = JCollectionFactory.createOrderedMap();
  	JIterator<JFormControl> iter = this.getControles().GetItems();
  	while (iter.hasMoreElements()) {
  		JFormControl c = iter.nextElement();
  		if (c.isVisible())
  			map.addElement(c.getName(), c.GetDisplayName());
  	}
  	return map;
  }
  	
  public JWins getWinsFromConrol(String zControlId) throws Exception {
  	JWins wins = null ;
  	JFormControl oControl = this.getControles().findControl(zControlId);
  	if (oControl instanceof JFormWinLOVResponsive ) {
  		JFormWinLOVResponsive a = (JFormWinLOVResponsive) oControl;
  		wins =  a.getControlWins().getRecords();
  		if (wins == null)
  			wins =  a.getControlWins().getRecords(true);
  	}
  	if (oControl instanceof JFormComboResponsive) {
  		JFormComboResponsive a = (JFormComboResponsive) oControl;
//  		wins =  a.GetsAsociado();
//  		if (wins == null)
  			wins = a.getControlCombo().getRecords(true);
  	}
  	if (oControl instanceof JFormCDatetimeResponsive) {
  		wins =  JWins.CreateVirtualWins(getTiposFechas());
  	}
  	return wins;
  }
	
	public synchronized static JRecords<BizVirtual> getTiposFechas() throws Exception {
		if (tiposFechas != null)
			return tiposFechas;
		tiposFechas = JRecords.createVirtualBDs();
		tiposFechas.addItem(JRecord.virtualBD(INI_MES, "Inicio Mes Pasado", 1));
		tiposFechas.addItem(JRecord.virtualBD(FIN_MES, "Fin Mes Pasado", 1));
		tiposFechas.addItem(JRecord.virtualBD(INI_SEMANA, "Inicio Semana", 1));
		tiposFechas.addItem(JRecord.virtualBD(FIN_SEMANA, "Fin Semana", 1));
		tiposFechas.addItem(JRecord.virtualBD(DIA_ANT, "Día Anterior", 1));
		return tiposFechas;
	}
	
	private String getFechaResolved(Date fecha, String tipoFecha) throws Exception {
		if (fecha==null || fecha.equals(JDateTools.MinDate()))
			fecha = new Date();
		String ret = JDateTools.DateToString(fecha);
		if (tipoFecha.equals("INI_MES")) 
			ret = JDateTools.DateToString(JDateTools.getFirstDayOfMonth(JDateTools.AddMonths(fecha, -1)));
		if (tipoFecha.equals("FIN_MES")) 
			ret = JDateTools.DateToString(JDateTools.getLastDayOfMonth(JDateTools.AddMonths(fecha, -1)));
		if (tipoFecha.equals("INI_SEMANA")) 
			ret = JDateTools.DateToString(JDateTools.addDays(fecha, -7));
		if (tipoFecha.equals("FIN_SEMANA")) 
			ret = JDateTools.DateToString(JDateTools.addDays(fecha, -1));
		if (tipoFecha.equals("DIA_ANT")) 
			ret = JDateTools.DateToString(JDateTools.addDays(fecha, -1));
		return ret;
	}
	
	public void ejecutarReporteyEnviarMails() throws Exception {
		PssLogger.logInfo("chequeo usuario mail"); 
		if (this.getObjUsuario()==null) return ;
		if (BizUsuario.getUsr().GetUsuario().equals("")) {
			BizUsuario.getUsr().setBirthCountryId("AR");
			//BizUsuario.getUsr().SetGlobal(this.getObjUsuario());
		}
		PssLogger.logInfo("trato de hacer el reporte"); 
		String fileName = "";
		if (this.getReportType().equals(TYPE_REPORT)) 
				fileName = this.doReport();
		else
			fileName = this.doWinsAction();
		PssLogger.logInfo("sale del reporte");
		if (fileName.equals("")) {
			PssLogger.logInfo("El fileName vino en blanco. Quiere decir que no hay datos. Sale del proceso");
			return;
		}
		if (this.getMailList().equals("")) return ;
		BizMailCasilla c = new BizMailCasilla();
		c.read(this.getObjUsuario().getMail());
		
		JMailSend mail = new JMailSend(); 
//		mail.setCompany(this.getObjUsuario().getCompany());
//		mail.setUsuario(this.getObjUsuario().GetUsuario());
		mail.setMailFrom(c.getMailFrom());
		mail.setMailTo(this.getMailList());
		mail.setMailToBCC(this.getMailListBCC());
		mail.setPassword(c.getMailPassword());
		mail.setTitle(this.getReportFantasyName());
		mail.setObjServer(c.getObjMailConf());
		mail.addFileAttach(this.getFileNameFromPath(fileName), fileName);
		mail.send();
//		snd.send(this.getReportFantasyName(), "", fileName); 
		return ;
	}
	
	private String doReport() throws Exception {
		PssLogger.logInfo("voy a llamar al fillwinsource");
		JBDReportes report = (JBDReportes) this.getFilledWinSource().getRecord();
		PssLogger.logInfo("tengo el objeto reporte");
  	String fileName = "" ;
		if (report.getTtpe().equals(JReport.REPORTES_EXCEL)) {
			fileName = this.getObjUsuario().getCompany()+"/"+report.toString()+".xls";
		} else {	
			report.setType(JReport.REPORTES_PDF);
			fileName =this.getObjUsuario().getCompany()+"/"+this.getReportFantasyName()+".pdf";
		}
		PssLogger.logInfo("obtengo el nombre " + fileName);
  	JTools.createDirectories(JPath.PssPathTempFiles(), fileName);
		report.setOutputFile(JPath.PssPathTempFiles()+"/"+fileName);
		BizUsuario.SetGlobal(this.getObjUsuario());
		PssLogger.logInfo("voy a hacer el insert");
  	report.processInsert();
  	PssLogger.logInfo("salgo del insert");
		return JPath.PssPathTempFiles()+"/"+fileName;
	}
	
	private String doWinsAction() throws Exception {
		JRecord report = this.getFilledWinSource().getRecord();
		BizUsuario.SetGlobal(this.getObjUsuario());
		JWin win = this.getObjWinSource();
		win.setRecord(report);

		BizAction a = win.findAction(this.getWinActionId());
		JActFileGenerate b = (JActFileGenerate) a.getObjSubmit();
		b.submit();
		String fileName = b.getFileName();
		if (fileName.equals("")) return "";
		return  b.getPath()+"/" + fileName;
	}
	
	
	public String getTipoReportesDescr() throws Exception {
		return getTipoReportes().getElement(this.getReportType());
	}
	
	public static JMap<String,String> getTipoReportes() throws Exception {
		if (tipoReportes!=null) return tipoReportes;
		JMap<String,String> temps= JCollectionFactory.createOrderedMap();
		temps.addElement(TYPE_REPORT, "Reporte");
		temps.addElement(TYPE_JWINACTION, "Accion JWin");
		return tipoReportes=temps;
	}

	public String getFileNameFromPath(String filePath) throws Exception {
		File oFile = new File(filePath);
		if (oFile!=null)
			return oFile.getName();
		return filePath;
	}
	
	
}
