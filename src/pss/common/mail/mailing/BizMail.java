package pss.common.mail.mailing;

import java.util.Date;

import pss.common.mail.JMail;
import pss.common.mail.message.BizMessage;
import pss.common.security.BizUsuario;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JHtml;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JMultiple;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;

public class BizMail extends JRecord {
	
	public static final String FOLDER_INBOX = "INBOX";
	public static final String FOLDER_SENDERS = "SENDERS";
	public static final String FOLDER_TRASH = "TRASH";

	private JString pCompany = new JString();
	private JString pUsuario = new JString();
	private JLong pIdmessage = new JLong();
	private JDate pDateReaded = new JDate();
	private JBoolean pReaded = new JBoolean();
	private JString pFolder = new JString();
	private JString pTypes = new JString();
	private JBoolean pAllCompanies = new JBoolean();
	private JMultiple pDestinatarios = new JMultiple();
	private JDateTime pDateCreation = new JDateTime(true);
	private JDateTime pValidTime = new JDateTime(true);
	private JString pTitle = new JString();
	private JInteger pPriority = new JInteger();
	private JHtml pMensaje = new JHtml();
	private JHtml pMensajeTrunc = new JHtml() { 
		public void preset() throws Exception {
			setValue(getMessageTrunc());;
		};
	};
	private JString pSender = new JString();
	private JString pDescrSender = new JString() {
		public void preset() throws Exception {
			setValue(getDescrSender());
		};
	};
	private JLong pSleepTime = new JLong();
	private JBoolean pUrgente = new JBoolean();

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public void setDateCreation(Date zValue) throws Exception {    pDateCreation.setValue(zValue);  }
  public Date getDateCreation() throws Exception {     return pDateCreation.getValue();  }
  public void setValidTime(Date zValue) throws Exception {    pValidTime.setValue(zValue);  }
  public Date getValidTime() throws Exception {     return pValidTime.getValue();  }
  public boolean isNullDateCreation() throws Exception { return  pDateCreation.isNull(); } 
  public void setNullToDateCreation() throws Exception {  pDateCreation.setNull(); }
  public long getSleepTime() throws Exception {     return pSleepTime.getValue();  }
  public void setSleepTime(long v) throws Exception {  pSleepTime.setValue(v);  }
  public void setSender(String v) throws Exception {    pSender.setValue(v);  }

	public void setCompany(String zValue) throws Exception {
		pCompany.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public boolean isNullCompany() throws Exception {
		return pCompany.isNull();
	}

	public void setNullToCompany() throws Exception {
		pCompany.setNull();
	}

	public void setUsuario(String zValue) throws Exception {
		pUsuario.setValue(zValue);
	}

	public void setPriority(int zValue) throws Exception {
		pPriority.setValue(zValue);
	}

	public void addDestinatario(String zValue) throws Exception {
		pDestinatarios.addValue(zValue);
	}

	public void setTitle(String zValue) throws Exception {
		pTitle.setValue(zValue);
	}

	public void setMensaje(String zValue) throws Exception {
		pMensaje.setValue(zValue);
	}

	public String getUsuario() throws Exception {
		return pUsuario.getValue();
	}
	
	public String getType() throws Exception {
		return pTypes.getValue();
	}

	public String getSender() throws Exception {
		return pSender.getValue();
	}

	public boolean isNullUsuario() throws Exception {
		return pUsuario.isNull();
	}

	public void setNullToUsuario() throws Exception {
		pUsuario.setNull();
	}

	public void setIdmessage(long zValue) throws Exception {
		pIdmessage.setValue(zValue);
	}

	public long getIdmessage() throws Exception {
		return pIdmessage.getValue();
	}

	public boolean isNullIdmessage() throws Exception {
		return pIdmessage.isNull();
	}

	public void setNullToIdmessage() throws Exception {
		pIdmessage.setNull();
	}

	public void setDateReaded(Date zValue) throws Exception {
		pDateReaded.setValue(zValue);
	}

	public Date getDateReaded() throws Exception {
		return pDateReaded.getValue();
	}

	public boolean isNullDateReaded() throws Exception {
		return pDateReaded.isNull();
	}

	public void setNullToDateReaded() throws Exception {
		pDateReaded.setNull();
	}

	public void setReaded(boolean zValue) throws Exception {
		pReaded.setValue(zValue);
	}

	public boolean getReaded() throws Exception {
		return pReaded.getValue();
	}

	public boolean isReaded() throws Exception {
		return pReaded.getValue();
	}

	public boolean isNullReaded() throws Exception {
		return pReaded.isNull();
	}

	public void setNullToReaded() throws Exception {
		pReaded.setNull();
	}

	public void setFolder(String zValue) throws Exception {
		pFolder.setValue(zValue);
	}

	public void setDestinatarios(String zValue) throws Exception {
		pDestinatarios.setValue(zValue);
	}

	public String getFolder() throws Exception {
		return pFolder.getValue();
	}

	public boolean isNullFolder() throws Exception {
		return pFolder.isNull();
	}

	public void setNullToFolder() throws Exception {
		pFolder.setNull();
	}

	public void setUrgente(boolean v) throws Exception {
		pUrgente.setValue(v);
	}

	public int getPriority() throws Exception {
		return pPriority.getValue();
	}

	public String getMessage() throws Exception {
		return getObjMessage().getMessage();
	}

	public String getTitle() throws Exception {
		return getObjMessage().getTitle();
	}

	/**
	 * Constructor de la Clase
	 */
	public BizMail() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("company", pCompany);
		this.addItem("usuario", pUsuario);
		this.addItem("id_message", pIdmessage);
		this.addItem("date_readed", pDateReaded);
		this.addItem("valid_time", pValidTime);
		this.addItem("readed", pReaded);
		this.addItem("folder", pFolder);
		this.addItem("allcompanies", pAllCompanies);
		this.addItem("destinatarios", pDestinatarios);
		this.addItem("types", pTypes);
		this.addItem("sleep_time", pSleepTime);
		this.addItem("message_trunc", pMensajeTrunc);

		this.addItem("msg_date_creation", pDateCreation);
  	this.addItem("msg_sender", pSender);
  	this.addItem("msg_title", pTitle);
		this.addItem("msg_message", pMensaje);
		this.addItem("msg_priority", pPriority);
		this.addItem("msg_urgente", pUrgente);
		this.addItem("descr_sender", pDescrSender);
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "id_message", "Id message", true, true, 18);
		this.addFixedItem(KEY, "usuario", "Usuario", true, false, 15);
		this.addFixedItem(FIELD, "company", "Company", true, true, 15);
		this.addFixedItem(FIELD, "date_readed", "Date readed", true, false, 10);
		this.addFixedItem(FIELD, "valid_time", "Fecha Validez", true, false, 12);
		this.addFixedItem(FIELD, "readed", "Leído", true, false, 1);
		this.addFixedItem(FIELD, "folder", "Folder", true, false, 50);
		this.addFixedItem(FIELD, "sleep_time", "Sleep", true, false, 18);
		this.addFixedItem(VIRTUAL, "message_trunc", "Mensaje", true, false, 100);
		this.addFixedItem(FOREIGN, "msg_date_creation", "Fecha Envio", true, true, 10);
		this.addFixedItem(FOREIGN, "msg_title", "Titulo", true, false, 250);
		this.addFixedItem(FOREIGN, "msg_sender", "Remitente", true, false, 50);
		this.addFixedItem(FOREIGN, "msg_message", "Mensaje", true, false, 800);
		this.addFixedItem(FOREIGN, "msg_priority", "Prioridad", true, false, 18);
		this.addFixedItem(FOREIGN, "msg_urgente", "Urgente", true, false, 1);
		this.addFixedItem(VIRTUAL, "descr_sender", "Sender", true, false, 100);
		this.addFixedItem(VIRTUAL, "allcompanies", "A Toda la comapania", true, false, 1);
		this.addFixedItem(FIELD, "destinatarios", "Destinatarios", true, false, 800);
		this.addFixedItem(VIRTUAL, "types", "Tipo usuarios", true, false, 800);
	}
	
	@Override
	protected boolean loadForeignFields() throws Exception {
		return true;
	}
	
	@Override
	public String GetTableTemporal() throws Exception {
		return "(select mail.*, "
			+ "msg.title as msg_title, "
			+ "msg.date_creation as msg_date_creation, "
			+ "msg.sender as msg_sender, "
			+ "msg.priority as msg_priority, "
			+ "msg.urgent as msg_urgente, "
			+ "msg.message as msg_message "
			+ " from MSG_MAIL mail"
			+ " join MSG_MESSAGE msg on msg.id_message=mail.id_message"
			+ ") "
			+ " MSG_MAIL";
	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "MSG_MAIL";
	}

	BizMessage objMessage;

	BizMessage getObjMessage() throws Exception {
		if (objMessage != null)
			return objMessage;
		BizMessage o = new BizMessage();
		o.read(getIdmessage());
		return objMessage = o;
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Default read() method
	 */
	public boolean read(String zCompany, String zUsuario, long zIdmessage) throws Exception {
		this.addFilter("company", zCompany);
		this.addFilter("usuario", zUsuario);
		this.addFilter("id_message", zIdmessage);
		this.read();
		this.loadMessage();
		return true;
	}
	
	public void execProcessMarkReaded() throws Exception {
		JExec exec = new JExec(null,null) {
			public void Do() throws Exception {
				processMarkReaded();
			};
		};
		exec.execute();
	}


	public void processMarkReaded() throws Exception {
		if (this.isReaded())
			return;
		
		BizMail m = (BizMail) this.getPreInstance();
		m.setReaded(true);
		m.setDateReaded(new Date());
		m.update();
		this.setReaded(m.isReaded());
		this.setDateReaded(m.getDateReaded());
		
		JMail.getMailServer().clearCache(this.getUsuario());
	}

	public void processRecuperar() throws Exception {
		BizMail m = (BizMail) this.getPreInstance();
		m.setFolder(BizMail.FOLDER_INBOX);
		m.update();
		this.setFolder(m.getFolder());
	}

	public void processMarcarNoLeido() throws Exception {
		BizMail m = (BizMail) this.getPreInstance();
		m.setReaded(false);
		m.update();
		this.setReaded(m.isReaded());
	}

	public void processMoveToTrash() throws Exception {
		BizMail m = (BizMail) this.getPreInstance();
		m.setFolder(BizMail.FOLDER_TRASH);
		m.update();
		this.setFolder(m.getFolder());
	}
	
	public boolean isAllCompanies() throws Exception {
		return this.pAllCompanies.getValue();
	}
	
	public boolean isUrgente() throws Exception {
		return this.pUrgente.getValue();
	}

	@Override
	public void processInsert() throws Exception {
		if (this.pSender.isNull()) this.pSender.setValue(BizUsuario.getUsr().GetUsuario());
		// crear mensaje
		BizMessage mens = new BizMessage();
		mens.setCompany(this.getCompany());
		mens.setDateCreation(new Date());
		mens.setTitle(this.pTitle.getValue());
		mens.setMessage(this.pMensaje.getValue());
		mens.setSender(this.pSender.getValue());
		mens.setEmergency(this.isUrgente());
		mens.setUrgent(this.isUrgente());
		mens.setPriority(10);
		mens.processInsert();

		// entrada al mail
		this.setIdmessage(mens.getIdmessage());
		this.setFolder(BizMail.FOLDER_INBOX);
		this.setReaded(false);
		this.setDateCreation(mens.getDateCreation());

		if (this.isAllCompanies()) {
			this.processAllCompanies();
		} else {
			this.processDestinatarios();
		}

		if (this.isSoyDestinatario()) return;
		
		this.setUsuario(this.pSender.getValue());
		this.setFolder(BizMail.FOLDER_SENDERS);
		this.setDestinatarios(this.pDestinatarios.toString());
		this.setReaded(true);
		super.processInsert();
		
//		JMail.getMailServer().clearCache(BizUsuario.getUsr().GetUsuario());
		JMail.getMailServer().clearCache(this.pSender.getValue());
	}
	
	public boolean isSoyDestinatario() throws Exception {
		JIterator<String> iter = pDestinatarios.getIterator();
		while (iter.hasMoreElements()) {
			String destino = iter.nextElement();
			if (this.isSender(destino)) return true;
		}
		return false;
	}

	

	
	public boolean isSender(String usr) throws Exception {
		return usr.equalsIgnoreCase(this.pSender.getValue());
	}
	
	public void processDestinatarios() throws Exception {
		JIterator<String> iter = pDestinatarios.getIterator();
		while (iter.hasMoreElements()) {
			String destino = iter.nextElement();
			this.setReaded(false);
			this.setUsuario(destino);
			this.insert();
			JMail.getMailServer().clearCache(destino);
		}
	}

	public void processAllCompanies() throws Exception {
		JRecords<BizUsuario> usr = new JRecords<BizUsuario>(BizUsuario.class);
		if (pTypes.isNotNull()) 
			usr.addFilter("tipo_usuario", pTypes.getValue());
		if (!BizUsuario.getUsr().isAdminUser()) 
			usr.setFilterValue("company", this.getCompany(),null);
		usr.readAll();
		JIterator<BizUsuario> iter = usr.getStaticIterator();
		while (iter.hasMoreElements()) {
			BizUsuario user = iter.nextElement();
			if (user.GetUsuario().equalsIgnoreCase(BizUsuario.getUsr().GetUsuario()))
				continue;
			this.setCompany(user.getCompany());
			this.setUsuario(user.GetUsuario());
			super.processInsert();
			JMail.getMailServer().clearCache(user.GetUsuario());
		}
	}

	private void loadMessage() throws Exception {
		this.pUrgente.setValue(this.getObjMessage().isEmergency());
		this.pTitle.setValue(getObjMessage().getTitle());
		this.pPriority.setValue(getObjMessage().getPriority());
		this.pMensaje.setValue(getObjMessage().getMessage());
		this.pSender.setValue(getObjMessage().getSenderDesc());
	}
	
	public boolean isFolderInbox() throws Exception {
		return this.pFolder.getValue().equals(BizMail.FOLDER_INBOX);
	}
	
	public boolean isFolderSenders() throws Exception {
		return this.pFolder.getValue().equals(BizMail.FOLDER_SENDERS);
	}

	public boolean isFolderTrash() throws Exception {
		return this.pFolder.getValue().equals(BizMail.FOLDER_TRASH);
	}
	
	private BizUsuario sender;
	public BizUsuario getObjSender() throws Exception {
		if (this.sender!=null) return this.sender;
		BizUsuario u = new BizUsuario();
		u.Read(this.pSender.getValue());
		return (this.sender=u);
	}

	public String getDescrSender() throws Exception {
		return this.getObjSender().GetDescrip();
	}
	
	public String getMessageTrunc() throws Exception {
		return JTools.subStr(JTools.trimHTML(this.getMessage()), 0, 100);
	}	

}
