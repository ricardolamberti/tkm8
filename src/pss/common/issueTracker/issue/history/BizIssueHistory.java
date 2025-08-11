package pss.common.issueTracker.issue.history;

import java.util.Date;

import pss.common.issueTracker.issue.BizIssue;
import pss.common.issueTracker.issueHandlerUsers.BizIssueHandlerUser;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

public class BizIssueHistory extends JRecord {

	public static String ACTION_CHANGE_STATUS = "CH";
	public static String ACTION_ADD_NOTE = "AN";
	public static String ACTION_CREATE = "AC";
	
	
	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//

	private JString pCompany = new JString();
	private JLong pHistoryId = new JLong();
	private JLong pIssueId = new JLong();
	private JString pDescription = new JString();
	private JDateTime pDateSubmitted = new JDateTime();
	
	private JString pUsuario = new JString();
	private JString pUsuarioDescr = new JString(){
		public void preset() throws Exception {
			pUsuarioDescr.setValue(getDescrUsuario());
		}
	};
	private JString pActionId = new JString();
	private JString pActionDescr = new JString(){
		public void preset() throws Exception {
			pActionDescr.setValue(getActionDescr());
		}
	};
	private JString pHandlerUser = new JString();
	
	private JString statusToApply = new JString();
	private JDateTime pDateSubmittedOfLastStatus = new JDateTime();
	private JLong pQtyHoursFromLastStatus = new JLong();
	
	
	public String getCompany() throws Exception { return pCompany.getValue();	}
	public long getIssueId() throws Exception{	return pIssueId.getValue();	}
	public long getHistoryId() throws Exception{	return pHistoryId.getValue();	}
	public String getDescription() throws Exception {	return pDescription.getValue();	}
	public Date getDateSubmitted() throws Exception{	return pDateSubmitted.getValue();	}
	public String getUsuario() throws Exception{	return pUsuario.getValue();}
	public String getActionId() throws Exception{	return pActionId.getValue();}
	public String getStatusToApply() throws Exception{	return statusToApply.getValue();}
	public String getHandlerUser() throws Exception{	return pHandlerUser.getValue();}
	public Date getDateSubmittedOfLastStatus() throws Exception{	return pDateSubmittedOfLastStatus.getValue();	}
	public long getQtyHoursFromLastStatus() throws Exception{	return pQtyHoursFromLastStatus.getValue();	}
	
	public void setCompany(String value) throws Exception { this.pCompany.setValue(value);	}
	public void setIssueId(long value) throws Exception{	this.pIssueId.setValue(value);}
	public void setHistoryId(long value) throws Exception{	this.pHistoryId.setValue(value);}
	public void setDescription(String value) throws Exception {pDescription.setValue(value);}
	public void setDateSubmitted(Date value) throws Exception{	this.pDateSubmitted.setValue(value);}
	public void setUsuario(String value) throws Exception{	this.pUsuario.setValue(value);}
	public void setActionId(String value) throws Exception{	this.pActionId.setValue(value);}
	public void setStatusToApply(String value) throws Exception{	this.statusToApply.setValue(value);}
	public void setHandlerUser(String value) throws Exception{	this.pHandlerUser.setValue(value);}	
	public void setDateSubmittedOfLastStatus(Date value) throws Exception{	this.pDateSubmittedOfLastStatus.setValue(value);}
	public void setQtyHoursFromLastStatus(long value) throws Exception{	this.pQtyHoursFromLastStatus.setValue(value);}
	
	private BizIssue oIssue= null;
	private BizUsuario usuario;
	private static JMap<String,String> actions;

	
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizIssueHistory() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("company", pCompany);
		addItem("history_id", pHistoryId);
		addItem("issue_id", pIssueId);
		addItem("action_id", pActionId);
		addItem("description", pDescription);
		addItem("date_submitted", pDateSubmitted);
    addItem( "usuario", pUsuario);
    addItem( "status_to_apply", statusToApply);
    addItem( "handler_user", pHandlerUser);	
    addItem( "descr_usuario", pUsuarioDescr);
    addItem( "descr_action", pActionDescr);		
    addItem( "date_from_last_status", pDateSubmittedOfLastStatus);
    addItem( "qty_hours_from_last_status", pQtyHoursFromLastStatus);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "history_id", "Nro. History", false, false, 18);
		addFixedItem(FIELD, "company", "Empresa", true, true, 15);
		addFixedItem(FIELD, "issue_id", "Nro. Issue", true, true, 18);
		addFixedItem(FIELD, "action_id", "Acción", true, true, 2 );
		addFixedItem(FIELD, "description", "Descripción", true, false, 4000);
		addFixedItem(FIELD, "date_submitted", "Fecha Alta", true, false, 30);
		addFixedItem(FIELD, "usuario", "Usuario SITI", true, true, 15 );
		addFixedItem(FIELD, "handler_user", "Responsable PentaWare", true, false, 200 );
		addFixedItem(FIELD, "status_to_apply", "Estado", true, false, 20 );
		addFixedItem(FIELD, "date_from_last_status", "Fecha Estado Anterior", true, false, 30);
		addFixedItem(FIELD, "qty_hours_from_last_status", "Horas Desde Estado Anterior", true, true, 18);
		addFixedItem(VIRTUAL, "descr_usuario", "Usuario SITI", true, false, 200 );		
		addFixedItem(VIRTUAL, "descr_action", "Acción", true, false, 200 );
	}
	

	@Override
	public String GetTable() {
		return "ISSUE_TRACK_HISTORY";
	}

	public boolean Read(long history_id) throws Exception {
		addFilter("history_id", history_id);
		return this.read();
	}
	
	@Override
	public void processInsert()throws Exception{
		this.pUsuario.setValue(BizUsuario.getUsr().GetUsuario());
  	this.pDateSubmitted.setValue(BizUsuario.getUsr().todayGMT(true));
  	this.setQtyHoursFromLastStatus(0);
  	Date tmp = this.getDateSubmittedFromLastRecord();
  	if (tmp!=null) {
  		this.setDateSubmittedOfLastStatus(tmp);
  		this.setQtyHoursFromLastStatus(JDateTools.getHoursBetween(tmp, this.getDateSubmitted()));
  	}
		super.processInsert();
		pHistoryId.setValue(this.getIdentity("history_id"));
		
		
	}
	
	@Override
	public void processUpdate() throws Exception {
		super.processUpdate();
	}

	@Override
	public void processDelete() throws Exception {
		super.processDelete();
	}

	
	
	public BizIssue getObjIssue() throws Exception {
		if (this.oIssue!=null) return this.oIssue;
		BizIssue t = new BizIssue();
		t.Read(this.pIssueId.getValue());
		return (this.oIssue=t);
	}
	
	public String getDescrUsuario() throws Exception {
		if (BizIssueHandlerUser.isThisUserAHandlerUser(this.getUsuario()))
			return this.getHandlerUser();
		return this.getObjUsuario().GetDescrip();
	}
	
	
	public BizUsuario getObjUsuario() throws Exception {
		if (this.usuario!=null) return this.usuario;
		BizUsuario t = new BizUsuario();
		t.Read(this.pUsuario.getValue());
		return (this.usuario=t);
	}
	

  public static JMap<String, String> getActions() throws Exception {
		if (actions!=null) return actions;
		JMap<String,String> elements= JCollectionFactory.createOrderedMap();
		elements.addElement(ACTION_CREATE,"Creación");  	
		elements.addElement(ACTION_CHANGE_STATUS,"Cambio Estado");  	
		elements.addElement(ACTION_ADD_NOTE,"Agregar Nota");  	
		return actions=elements;
  }
  
  
	public String getActionDescr() throws Exception {
		return getActions().getElement(this.getActionId());
	}
	
	private Date getDateSubmittedFromLastRecord() throws Exception {
		JRecords<BizIssueHistory> recs = new JRecords<BizIssueHistory>(BizIssueHistory.class);
		recs.addFilter("company", this.getCompany());
		recs.addFilter("issue_id", this.getIssueId());
		recs.addOrderBy("history_id", "desc");
		recs.toStatic();
		recs.firstRecord();
		if (recs.nextRecord()) {
			return ((BizIssueHistory) recs.getRecord()).getDateSubmitted();
		} 
		return null;
	}
	

}
