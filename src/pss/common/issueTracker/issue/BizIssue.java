package pss.common.issueTracker.issue;

import java.util.Date;

import pss.common.event.mail.BizMailCasilla;
import pss.common.event.mail.JMailSend;
import pss.common.issueTracker.issue.attachment.BizIssueAttachment;
import pss.common.issueTracker.issue.history.BizIssueHistory;
import pss.common.issueTracker.issue.note.BizIssueNote;
import pss.common.issueTracker.issueHandlerUsers.BizIssueHandlerUser;
import pss.common.issueTracker.issueHandlerUsers.GuiIssueHandlerUsers;
import pss.common.issueTracker.setting.BizIssueTrackerSetting;
import pss.common.mail.mailing.BizMail;
import pss.common.security.BizUsuario;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;

public class BizIssue extends JRecord {
	
	public static String MAIL_ACTION_NEW = "NEW";
	public static String MAIL_ACTION_NEW_NOTE = "NEWNOTE";
	public static String MAIL_ACTION_REOPEN = "REOPEN";
	
	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//

	protected JString pCompany = new JString();
	protected JLong pIssueId = new JLong();
	protected JString pSummary = new JString();
	protected JString pDescription = new JString();
	
	protected JDateTime pLastUpdate = new JDateTime();
	protected JString pCategory = new JString();
	protected JString pPriority = new JString();
	protected JString pSeverity = new JString();
	protected JString pStatus = new JString();
	protected JDateTime pDateSubmitted = new JDateTime();
	protected JDate pDateSubmittedPlain = new JDate();
	protected JString pLastNote = new JString(){
		public void preset() throws Exception {
			pLastNote.setValue(getLastNotesDescription());
		}
	};
	protected JString pUsuario = new JString();
	protected JString pUsuarioDescr = new JString(){
		public void preset() throws Exception {
			pUsuarioDescr.setValue(getDescrUsuario());
		}
	};
	protected JString pAssignedTo = new JString();
	protected JString pStatusDescr = new JString(){
		public void preset() throws Exception {
			pStatusDescr.setValue(getDescrStatus());
		}
	};
	protected JBoolean pHasNote = new JBoolean(){
		public void preset() throws Exception {
			pHasNote.setValue(hasNote());
		}
	};
	protected JBoolean pHasAttachment = new JBoolean(){
		public void preset() throws Exception {
			pHasAttachment.setValue(hasAttachment());
		}
	};
	protected JLong pTotalTimeUntilResolution = new JLong() {
		public void preset() throws Exception {
			pTotalTimeUntilResolution.setValue(getTotalTimeUntilResolution());
		}
	};
	protected JLong pReopensQty = new JLong() {
		public void preset() throws Exception {
			pReopensQty.setValue(getCantReopens());
		}
	};
	private JLong pRealTimeUntilResolution = new JLong() {
		public void preset() throws Exception {
			pRealTimeUntilResolution.setValue(getRealTimeUntilResolution());
		}
	};
	
	JBoolean pSoloPendientes = new JBoolean(); //solo Filtro
	JBoolean pVerCerrados = new JBoolean(); //solo Filtro
	
	public String getCompany() throws Exception { return pCompany.getValue();	}
	public String getSummary() throws Exception {return pSummary.getValue();}
	public String getDescription() throws Exception {	return pDescription.getValue();	}
	
	public Date getLastUpdate() throws Exception{	return pLastUpdate.getValue();	}
	public long getIssueId() throws Exception{	return pIssueId.getValue();	}
	public String getCategory() throws Exception{	return pCategory.getValue();	}
	public String getPriority() throws Exception{	return pPriority.getValue();	}
	public String getSeverity() throws Exception{	return pSeverity.getValue();	}
	public String getStatus() throws Exception{	return pStatus.getValue();	}
	public Date getDateSubmitted() throws Exception{	return pDateSubmitted.getValue();	}
	public String getUsuario() throws Exception{	return pUsuario.getValue();}
	public String getAssignedTo() throws Exception{	return pAssignedTo.getValue();}
	
	public void setCompany(String value) throws Exception { this.pCompany.setValue(value);	}
	public void setSummary(String value) throws Exception {	pSummary.setValue(value);}
	public void setDescription(String value) throws Exception {pDescription.setValue(value);}

	public void setLastUpdate(Date value) throws Exception{	this.pLastUpdate.setValue(value);}
	public void setIssueId(long value) throws Exception{	this.pIssueId.setValue(value);}
	public void setCategory(String value) throws Exception{	this.pCategory.setValue(value);}
	public void setPriority(String value) throws Exception{	this.pPriority.setValue(value);}
	public void setSeverity(String value) throws Exception{	this.pSeverity.setValue(value);}
	public void setStatus(String value) throws Exception{	this.pStatus.setValue(value);}
	public void setDateSubmitted(Date value) throws Exception{	this.pDateSubmitted.setValue(value);}
	public void setUsuario(String value) throws Exception{	this.pUsuario.setValue(value);}
	public void setAssignedTo(String value) throws Exception{	this.pAssignedTo.setValue(value);}
	

	protected BizUsuario usuario;
	protected BizIssueTrackerSetting IssueTrackerSetting;
	protected BizIssueHandlerUser handle_user;
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizIssue() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("company", pCompany);
		addItem("summary", pSummary);
		addItem("description", pDescription);
		addItem("last_update", pLastUpdate);
		addItem("issue_id", pIssueId);
		addItem("category", pCategory);
		addItem("priority", pPriority);
		addItem("severity", pSeverity);
		addItem("status", pStatus);
		addItem("date_submitted", pDateSubmitted);
		addItem("date_submitted_plain", pDateSubmittedPlain);
		addItem("last_note", pLastNote);
    addItem( "usuario", pUsuario);
    addItem( "descr_usuario", pUsuarioDescr);
    addItem( "assigned_to", pAssignedTo);
    addItem( "descr_status", pStatusDescr);    
    addItem( "has_note", pHasNote);
    addItem( "has_attachment", pHasAttachment);
    addItem( "total_time_for_resolution", pTotalTimeUntilResolution);
    addItem( "real_time_for_resolution", pRealTimeUntilResolution);
    addItem( "reopens_qty", pReopensQty);
    addItem("solo_pendientes", pSoloPendientes);
    addItem("ver_cerrados", pVerCerrados);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "issue_id", "# Incidencia", false, false, 18);
		addFixedItem(FIELD, "company", "Empresa", true, true, 15);
		addFixedItem(FIELD, "summary", "Titulo", true, false, 1000);
		addFixedItem(FIELD, "description", "Descripción", true, false, 4000);
		addFixedItem(FIELD, "last_update", "Ult. Actualización", true, false, 30);
		addFixedItem(FIELD, "category", "Categoría", true, false, 50);
		addFixedItem(FIELD, "priority", "Prioridad", true, false, 50);
		addFixedItem(FIELD, "severity", "Severidad", true, false, 50);
		addFixedItem(FIELD, "status", "Estado", true, false, 50);
		addFixedItem(FIELD, "date_submitted", "Fecha Alta", true, false, 30);
		addFixedItem(FIELD, "date_submitted_plain", "Fecha Alta", true, false, 30);
		addFixedItem(FIELD, "usuario", "Usuario", true, true, 15 );
		addFixedItem(FIELD, "assigned_to", "Asignado A", true, false, 200 );
		addFixedItem(VIRTUAL, "last_note", "Comentario Final", true, false, 4000);
		addFixedItem(VIRTUAL, "descr_usuario", "Usuario", true, false, 200 );
		addFixedItem(VIRTUAL, "descr_status", "Estado", true, false, 50 );		
		addFixedItem(VIRTUAL, "has_note", "Notas", true, false, 1 );
		addFixedItem(VIRTUAL, "has_attachment", "Adjuntos", true, false, 1 );
		addFixedItem(VIRTUAL, "total_time_for_resolution", "Horas Totales para Resolución", true, false, 18 );
		addFixedItem(VIRTUAL, "real_time_for_resolution", "Horas Reales para Resolución", true, false, 18 );
		addFixedItem(VIRTUAL, "reopens_qty", "# Reopens", true, false, 18 );
		addFixedItem(VIRTUAL, "solo_pendientes", "solo_pendientes", true, false, 1 );
		addFixedItem(VIRTUAL, "ver_cerrados", "ver_cerrados", true, false, 1 );
	}

	@Override
	public String GetTable() {
		return "ISSUE_TRACK";
	}

	public boolean Read(long issue_id) throws Exception {
		addFilter("issue_id", issue_id);
		return this.read();
	}
	
	@Override
	public void processInsert()throws Exception{
  	this.pUsuario.setValue(BizUsuario.getUsr().GetUsuario());
  	this.pDateSubmitted.setValue(BizUsuario.getUsr().todayGMT(true));
  	this.pDateSubmittedPlain.setValue(BizUsuario.getUsr().todayGMT(false));
  	this.pStatus.setValue(BizIssueTrackerSetting.STATUS_NEW);
  	this.pAssignedTo.setValue(this.getDefaultHandlerUserForCompany());
		super.processInsert();
		pIssueId.setValue(this.getIdentity("issue_id"));
		
		this.sendMailNew(MAIL_ACTION_NEW);
		this.addHistory(BizIssueHistory.ACTION_CREATE,"");
	}
	
	@Override
	public void processUpdate() throws Exception {
		super.processUpdate();
	}

	@Override
	public void processDelete() throws Exception {
		super.processDelete();
	}
	public boolean isResolved() throws Exception {
		return this.getStatus().equals(BizIssueTrackerSetting.STATUS_RESOLVED);
	}
	public boolean isClosed() throws Exception {
		return this.getStatus().equals(BizIssueTrackerSetting.STATUS_CLOSED);
	}
			
	
	public String getLastNotesDescription() throws Exception {
		JRecords<BizIssueNote> recs = new JRecords<BizIssueNote>(BizIssueNote.class);
		recs.addFilter("company", this.getCompany());
		recs.addFilter("issue_id", this.getIssueId());
		recs.addOrderBy("note_id", "desc");
		recs.toStatic();
		recs.firstRecord();
		if (recs.sizeStaticElements()>0) {
			recs.nextRecord();
			return ((BizIssueNote) recs.getRecord()).getDescription();
		}
		return "";
	}
	

	
	public String getDescrUsuario() throws Exception {
		return this.getObjUsuario().GetDescrip();
	}
	
	public String getDescrStatus() throws Exception {
		return BizIssueTrackerSetting.getStatusDescr(this.getStatus());
	}

	
	public BizUsuario getObjUsuario() throws Exception {
		if (this.usuario!=null) return this.usuario;
		BizUsuario t = new BizUsuario();
		t.Read(this.pUsuario.getValue());
		return (this.usuario=t);
	}
	
	public void execAsignarUsuario(final BizIssueHandlerUser usuario) throws Exception {
		JExec oExec = new JExec(this, null) {
			public void Do() throws Exception {
				asignarUsuario(usuario);
			}
		};
		oExec.execute();
	}
	
	public void asignarUsuario(BizIssueHandlerUser usuario) throws Exception {
		setAssignedTo(usuario.getUsuario());
		update();
		//this.addHito(BizTramiteHito.ASIGNA_RESPONSABLE, usuario.getIdusuario(),false);
	}

	public String getDefaultHandlerUserForCompany() throws Exception {
		if (this.getObjIssueTrackerSetting()!=null) 
			return this.getObjIssueTrackerSetting().getHandlerUser();
		else
			return BizIssueTrackerSetting.getDefaultSetting().getHandlerUser();
	}
	
	public BizIssueTrackerSetting getObjIssueTrackerSetting() throws Exception {
		if (this.IssueTrackerSetting!=null) return this.IssueTrackerSetting;
		BizIssueTrackerSetting t = new BizIssueTrackerSetting();
		t.dontThrowException(true);
		if (t.Read(this.pCompany.getValue()))
			return (this.IssueTrackerSetting=t);
		return this.IssueTrackerSetting=null;
	}
	
	public void execChangeStatus(final String status, final String handle_user) throws Exception {
		JExec oExec = new JExec(this, null) {
			public void Do() throws Exception {
				changeStatus(status, handle_user);
			}
		};
		oExec.execute();
	}
	
	public void changeStatus(String status, String handle_user) throws Exception {
  	if (handle_user==null && BizIssueHandlerUser.isLoginedUserAHandlerUser()) 
  		handle_user = this.getFirsHandleUser();
  	
		this.setStatus(status);
		update();
		String title = "Cambio de estado en Incidencia: " + this.getIssueId();
		String msg ="";
		if (status.equals(BizIssueTrackerSetting.STATUS_RESOLVED)) {
			msg = "Se ha resuelto la incidencia " + this.getIssueId() + "\n";
			msg += " (" + this.getSummary() + ")";
		}
		if (status.equals(BizIssueTrackerSetting.STATUS_MOREDATA)) {
			msg = "Se necesita más información de la incidencia " + this.getIssueId() + "\n";
			msg += " (" + this.getSummary() + ")";
		}
		if (status.equals(BizIssueTrackerSetting.STATUS_ARRIVED)) {
			msg = "La incidencia " + this.getIssueId() + " ha sido recibida y la analizaremos \n";
			msg += " (" + this.getSummary() + ")";
		}
		if (status.equals(BizIssueTrackerSetting.STATUS_REOPENED)) {
			this.sendMailNew(MAIL_ACTION_REOPEN);
		}
		if (!msg.equals(""))
			this.sendMessage(title, msg);
		//this.addHito(BizTramiteHito.ASIGNA_RESPONSABLE, usuario.getIdusuario(),false);
		
		this.addHistory(BizIssueHistory.ACTION_CHANGE_STATUS, handle_user);
	}
	
	public BizIssueHandlerUser getObjHandleUser() throws Exception {
		if (this.handle_user!=null) return this.handle_user;
		BizIssueHandlerUser t = new BizIssueHandlerUser();
		t.Read(this.pAssignedTo.getValue());
		return (this.handle_user=t);
	}
	
	public void sendMailNew(String mail_action) throws Exception {
		BizIssueTrackerSetting issueTrackCompanyOrDefault=this.getObjIssueTrackerSetting();
		if (issueTrackCompanyOrDefault==null) {
			issueTrackCompanyOrDefault = BizIssueTrackerSetting.getDefaultSetting();
		}
		//Obtengo la casilla desde donde se lo voy a mandar
		BizMailCasilla casillaFrom = issueTrackCompanyOrDefault.getObjMailCasilla();
		if (casillaFrom==null) return;
		
		// Obtengo la casilla hacia donde lo voy a mandar. Si no está asignado a nadie, entonces voy al default 
		//de la empresa, y si no, al default del SITI.
		String mailTo="";
		if (this.getObjHandleUser()!=null) {
			mailTo = this.getObjHandleUser().getEmail();
		}
		if (mailTo.equals("")) {
			mailTo = issueTrackCompanyOrDefault.getObjHandleUser().getEmail();
		}
		
		if (mailTo.equals("")) return;
		
		JMailSend send = new JMailSend();
		send.setObjServer(casillaFrom.getObjMailConf());
		send.setMailFrom(casillaFrom.getMailFrom());
		send.setMailFromName(casillaFrom.getNombre());
		send.setAuthUser(casillaFrom.getInternalUser());
		send.setPassword(casillaFrom.getMailPassword());
		send.setMailTo(mailTo);
		String title="Nueva Incidencia #" + this.getIssueId() +" - " + this.getCompany()  ;
		
		boolean newNote = false;
		if (mail_action.equals(MAIL_ACTION_NEW_NOTE)) {
			title = " Nueva Nota en la incidencia #" + this.getIssueId() +" - " + this.getCompany()  ;
			newNote = true;
		}
		if (mail_action.equals(MAIL_ACTION_REOPEN)){
			title = " Se REABRIO la Incidencia #" + this.getIssueId() +" - " + this.getCompany()  ;
			newNote=true;
		}
		send.setTitle(title);
		send.setBody(this.prepareIncidenciaBody(newNote));
		//send.setFilesAttach(this.getFilesAttached());
		send.send();
	}
	
	
	private String prepareIncidenciaBody(boolean newnote) throws Exception {
		
		String body = "----------- ENCABEZADO --------------<br>"; 
		body += "Empresa: " + this.getCompany() + "<br>";
		body += "Nro. Incidencia: " + this.getIssueId() + "<br>";
		body += "Titulo: " + this.getSummary() + "<br>";
		body += "Estado: " + this.getDescrStatus() + "<br>";
		body += "Usuario Creador: " + this.getUsuario() + " (" + this.getDescrUsuario() + ")" + "<br>";
		body += "Asignado A: " + this.getAssignedTo() + "<br>";
		body += "<br>";
		body += "----------- DESCRIPCION --------------<br>";
		if (newnote)
			body += this.getLastNotesDescription() +  "<br>";
		else
			body += this.getDescription() +  "<br>";
		
		
		return body;
		
	}
	
	private void sendMessage(String titulo, String msg) throws Exception {
		BizMail mail = new BizMail();
		mail.setCompany(this.getCompany());
//		mail.setFolder("INBOX");
		mail.setUrgente(true);
		mail.setUsuario("ADMIN");
		mail.addDestinatario(this.getUsuario());
		mail.setTitle(titulo);
		mail.setMensaje(JTools.encodeIso(JTools.encodeString2(msg)));
		//mail.setSleepTime(15000L);
		mail.setDateCreation(new Date());
		//mail.setValidTime(JDateTools.addMinutes(new Date(), cfg.getMessageLapse()+1));
		mail.processInsert();
	}
	
	
	public boolean hasNote() throws Exception {
		return this.getNotes().sizeStaticElements()>0;
	}
	public boolean hasAttachment() throws Exception {
		return this.getAttachments().sizeStaticElements()>0;
	}
	
	
	public JRecords<BizIssueNote> getNotes() throws Exception {
		JRecords<BizIssueNote> recs = new JRecords<BizIssueNote>(BizIssueNote.class);
		recs.addFilter("company", this.getCompany());
		recs.addFilter("issue_id", this.getIssueId());
		recs.addOrderBy("note_id", "desc");
		recs.toStatic();
		return recs;
	}
	
	public JRecords<BizIssueAttachment> getAttachments() throws Exception {
		JRecords<BizIssueAttachment> recs = new JRecords<BizIssueAttachment>(BizIssueAttachment.class);
		recs.addFilter("company", this.getCompany());
		recs.addFilter("issue_id", this.getIssueId());
		recs.addOrderBy("id_doc");
		recs.toStatic();
		return recs;
	}
	
	public JRecords<BizIssueHistory> getHistorys() throws Exception {
		JRecords<BizIssueHistory> recs = new JRecords<BizIssueHistory>(BizIssueHistory.class);
		recs.addFilter("company", this.getCompany());
		recs.addFilter("issue_id", this.getIssueId());
		recs.addOrderBy("history_id");
		recs.toStatic();
		return recs;
	}
	
	public void addHistory(String actionId, String handle_user) throws Exception {
		BizIssueHistory rec = new BizIssueHistory();
		rec.setCompany(this.getCompany());
		rec.setIssueId(this.getIssueId());
		rec.setActionId(actionId);
		rec.setStatusToApply(this.getStatus());
		rec.setHandlerUser(handle_user);
		String descr = "";
		if (actionId.equals(BizIssueHistory.ACTION_CREATE))
			descr = "Creación del issue #" + this.getIssueId();
		if (actionId.equals(BizIssueHistory.ACTION_CHANGE_STATUS))
			descr = "Se cambió el estado a " + this.getDescrStatus();
		if (actionId.equals(BizIssueHistory.ACTION_ADD_NOTE))
			descr = "Se agregó una nota";
		rec.setDescription(descr);
		rec.processInsert();
	}
	
	protected long getTotalTimeUntilResolution() throws Exception {
		//Son las horas totales desde que el issue se creó hasta que se resolvió
		if (this.getStatus().equals(BizIssueTrackerSetting.STATUS_RESOLVED) || this.getStatus().equals(BizIssueTrackerSetting.STATUS_CLOSED)) {
			return JDateTools.getHoursBetween(this.getDateSubmitted(), this.getLastUpdate()) ;
		}
		return 0;
	}
	
	protected long getRealTimeUntilResolution() throws Exception {
		//Son las horas que el issue estuvo del lado Nuestro
		if (!this.getStatus().equals(BizIssueTrackerSetting.STATUS_RESOLVED) && !this.getStatus().equals(BizIssueTrackerSetting.STATUS_CLOSED)) {
			return 0;
		}
		JRecords<BizIssueHistory> recs = new JRecords<BizIssueHistory>(BizIssueHistory.class);
		recs.addFilter("company", this.getCompany());
		recs.addFilter("issue_id", this.getIssueId());
		recs.addFixedFilter("WHERE handler_user is not null");
		recs.toStatic();
		recs.firstRecord();
		long sumHours = 0;
		while (recs.nextRecord()) {
			BizIssueHistory rec = recs.getRecord();
			sumHours += rec.getQtyHoursFromLastStatus();
		}
		return sumHours;	
	}
	
	protected long getCantReopens() throws Exception {
		JRecords<BizIssueHistory> recs = new JRecords<BizIssueHistory>(BizIssueHistory.class);
		recs.addFilter("company", this.getCompany());
		recs.addFilter("issue_id", this.getIssueId());
		recs.addFilter("action_id", BizIssueHistory.ACTION_CHANGE_STATUS);
		recs.addFilter("status_to_apply", BizIssueTrackerSetting.STATUS_REOPENED);
		recs.toStatic();
		return recs.sizeStaticElements();	
	}

	private String getFirsHandleUser() throws Exception {
		GuiIssueHandlerUsers wins=new GuiIssueHandlerUsers();
		wins.getRecords().addFilter("usuario_map_sistema", BizUsuario.getUsr().GetUsuario());			
		wins.getRecords().toStatic();
		wins.getRecords().firstRecord();
		if (wins.getRecords().nextRecord())
			return  ((BizIssueHandlerUser)wins.getRecords().getRecord()).getUsuario();
		return "";
	}

}

