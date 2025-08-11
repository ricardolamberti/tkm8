package pss.common.issueTracker.issue.note;

import java.util.Date;

import pss.common.issueTracker.issue.BizIssue;
import pss.common.issueTracker.issue.history.BizIssueHistory;
import pss.common.issueTracker.issueHandlerUsers.BizIssueHandlerUser;
import pss.common.issueTracker.setting.BizIssueTrackerSetting;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizIssueNote extends JRecord {
	
	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//

	private JString pCompany = new JString();
	private JLong pIssueId = new JLong();
	private JLong pNoteId = new JLong();
	private JString pDescription = new JString();
	private JDateTime pDateSubmitted = new JDateTime();
	
	private JString pUsuario = new JString();
	private JString pUsuarioDescr = new JString(){
		public void preset() throws Exception {
			pUsuarioDescr.setValue(getDescrUsuario());
		}
	};
	private JString pHandlerUser = new JString();
	private JString statusToApply = new JString(); 
	private JBoolean pAvoidHistory = new JBoolean();

	public String getCompany() throws Exception { return pCompany.getValue();	}
	public long getIssueId() throws Exception{	return pIssueId.getValue();	}
	public long getNoteId() throws Exception{	return pNoteId.getValue();	}
	public String getDescription() throws Exception {	return pDescription.getValue();	}
	public Date getDateSubmitted() throws Exception{	return pDateSubmitted.getValue();	}
	public String getUsuario() throws Exception{	return pUsuario.getValue();}
	public String getHandlerUser() throws Exception{	return pHandlerUser.getValue();}
	public String getStatusToApply() throws Exception{	return statusToApply.getValue();}
	public boolean getAvoidHistory() throws Exception{	return pAvoidHistory.getValue();}
	
	public void setCompany(String value) throws Exception { this.pCompany.setValue(value);	}
	public void setIssueId(long value) throws Exception{	this.pIssueId.setValue(value);}
	public void setNoteId(long value) throws Exception{	this.pNoteId.setValue(value);}
	public void setDescription(String value) throws Exception {pDescription.setValue(value);}
	public void setDateSubmitted(Date value) throws Exception{	this.pDateSubmitted.setValue(value);}
	public void setUsuario(String value) throws Exception{	this.pUsuario.setValue(value);}
	public void setHandlerUser(String value) throws Exception{	this.pHandlerUser.setValue(value);}	
	public void setStatusToApply(String value) throws Exception{	this.statusToApply.setValue(value);}
	public void setAvoidHistory(boolean value) throws Exception{	this.pAvoidHistory.setValue(value);}
	
	private BizIssue oIssue= null;
	private BizUsuario usuario;
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizIssueNote() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("company", pCompany);
		addItem("issue_id", pIssueId);
		addItem("note_id", pNoteId);
		addItem("description", pDescription);
		addItem("date_submitted", pDateSubmitted);
    addItem( "usuario", pUsuario);
    addItem( "descr_usuario", pUsuarioDescr);
    addItem( "handler_user", pHandlerUser);		
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "note_id", "Nro. Nota", false, false, 18);
		addFixedItem(FIELD, "company", "Empresa", true, true, 15);
		addFixedItem(FIELD, "issue_id", "Nro. Issue", true, true, 18);
		addFixedItem(FIELD, "description", "Descripción", true, false, 4000);
		addFixedItem(FIELD, "date_submitted", "Fecha Alta", true, false, 30);
		addFixedItem(FIELD, "usuario", "Usuario SITI", true, true, 15 );
		addFixedItem(FIELD, "handler_user", "Responsable PentaWare", true, false, 200 );
		addFixedItem(VIRTUAL, "descr_usuario", "Usuario SITI", true, false, 200 );		
	}
	

	@Override
	public String GetTable() {
		return "ISSUE_TRACK_NOTE";
	}

	public boolean Read(long note_id) throws Exception {
		addFilter("note_id", note_id);
		return this.read();
	}
	
	@Override
	public void processInsert()throws Exception{
		this.pUsuario.setValue(BizUsuario.getUsr().GetUsuario());
  	this.pDateSubmitted.setValue(BizUsuario.getUsr().todayGMT(true));
		super.processInsert();
		pNoteId.setValue(this.getIdentity("note_id"));
		
		BizIssue is = this.getObjIssue();
		is.setLastUpdate(BizUsuario.getUsr().todayGMT(true));
		
		is.processUpdate();
		if (!this.getAvoidHistory())
			is.addHistory(BizIssueHistory.ACTION_ADD_NOTE,"");
		
		if (!BizIssueHandlerUser.isLoginedUserAHandlerUser() && !is.getStatus().equals(BizIssueTrackerSetting.STATUS_RESOLVED)) {
			is.sendMailNew(BizIssue.MAIL_ACTION_NEW_NOTE);
		}
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
		if (this.hasHandleUser() && !BizIssueHandlerUser.isLoginedUserAHandlerUser())
			return "";
		return this.getObjUsuario().GetDescrip();
	}
	
	
	public BizUsuario getObjUsuario() throws Exception {
		if (this.usuario!=null) return this.usuario;
		BizUsuario t = new BizUsuario();
		t.Read(this.pUsuario.getValue());
		return (this.usuario=t);
	}
	
	public boolean hasHandleUser() throws Exception {
		return !this.getHandlerUser().equals("");
	}


}
