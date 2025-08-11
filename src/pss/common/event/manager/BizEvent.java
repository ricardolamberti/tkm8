package pss.common.event.manager;

import java.util.Date;

import pss.common.regions.company.BizCompany;
import pss.common.regions.nodes.BizNodo;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;
import pss.core.tools.collections.JIterator;

public class BizEvent extends JRecord {

	public static long EVENT_INFO = 0;
	public static long EVENT_WARN = 1;
	public static long EVENT_URG = 2;
	public static long EVENT_SUCCESS = 3;
	
	public static String ACTIVE = "S";
	public static String INACTIVE = "N";
	public static String NOSTATUS = "X";
			

	protected JLong pIdevent = new JLong();
	protected JString pModule = new JString();
	protected JString pCompany = new JString();
	protected JLong pLevel = new JLong();
	protected JString pEventCode = new JString();
	protected JString pKey = new JString();
	protected JString pSenderUser = new JString(); // sender
	protected JString pSenderNode = new JString(); // sender
	protected JString pTitle = new JString();
	protected JString pInfo = new JString();
	protected JDateTime pEventDatetime = new JDateTime(true);
	protected JBoolean pProcessed = new JBoolean();
	protected JString pActive = new JString();
	protected JString pStatus = new JString();
	protected JLong pOrigIdevent = new JLong();

	protected JString eventType = new JString();

	protected JString pDescrCompany = new JString() {
		public void preset() throws Exception {
			BizCompany.getCompanyDesc(pCompany);
		}
	};

	protected JString pDescrNode = new JString() {
		public void preset() throws Exception {
			if (pSenderNode.isNull()) return;
			pDescrNode.setValue(BizNodo.getStoreDesc(pCompany.getValue(), pSenderNode.getValue()));
		}
	};

	protected JString pDescrEventCode = new JString() {
		public void preset() throws Exception {
			setValue(getObjEventCode().getDescripcion());
		}
	};


	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setIdevent(long zValue) throws Exception {
		pIdevent.setValue(zValue);
	}

	protected String getNodeDesc(String value) {
		return null;
	}

	public long getIdevent() throws Exception {
		return pIdevent.getValue();
	}

	public long getOrigIdevent() throws Exception {
		return pOrigIdevent.getValue();
	}

	public void setOrigIdevent(long zValue) throws Exception {
		pOrigIdevent.setValue(zValue);
	}

	public void setModule(String zValue) throws Exception {
		pModule.setValue(zValue);
	}

	public String getModule() throws Exception {
		return pModule.getValue();
	}

	public void setCompany(String zValue) throws Exception {
		pCompany.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public void setKey(String zValue) throws Exception {
		pKey.setValue(zValue);
	}

	public String getKey() throws Exception {
		return pKey.getValue();
	}

	public boolean hasKey() throws Exception {
		return !pKey.isEmpty();
	}

	public void setEventCode(String zValue) throws Exception {
		pEventCode.setValue(zValue);
	}

	public String getEventCode() throws Exception {
		return pEventCode.getValue();
	}

	public void setEventLevel(long zValue) throws Exception {
		pLevel.setValue(zValue);
	}

	public long getEventLevel() throws Exception {
		return pLevel.getValue();
	}

	public void setSenderUser(String zValue) throws Exception {
		pSenderUser.setValue(zValue);
	}

	public String getSenderUser() throws Exception {
		return pSenderUser.getValue();
	}

	public void setSenderNode(String zValue) throws Exception {
		pSenderNode.setValue(zValue);
	}

	public String getSenderNode() throws Exception {
		return pSenderNode.getValue();
	}

	public void setStatus(String zValue) throws Exception {
		pStatus.setValue(zValue);
	}

	public String getStatus() throws Exception {
		return pStatus.getValue();
	}

	public void setInfo(String zValue) throws Exception {
		pInfo.setValue(zValue);
	}

	public String getInfo() throws Exception {
		return pInfo.getValue();
	}

	public boolean isNullInfo() throws Exception {
		return pInfo.isNull();
	}

	public void setNullToInfo() throws Exception {
		pInfo.setNull();
	}

	public void setEventDatetime(Date zValue) throws Exception {
		pEventDatetime.setValue(zValue);
	}

	public Date getEventDatetime() throws Exception {
		return pEventDatetime.getValue();
	}

	public boolean isNullEventDatetime() throws Exception {
		return pEventDatetime.isNull();
	}

	public void setNullToEventDatetime() throws Exception {
		pEventDatetime.setNull();
	}

	public void setProcessed(boolean zValue) throws Exception {
		pProcessed.setValue(zValue);
	}

	public boolean getProcessed() throws Exception {
		return pProcessed.getValue();
	}

	public void setActive(String zValue) throws Exception {
		pActive.setValue(zValue);
	}

	public String getActive() throws Exception {
		return pActive.getValue();
	}

	public boolean isNullProcessed() throws Exception {
		return pProcessed.isNull();
	}

	public void setNullToProcessed() throws Exception {
		pProcessed.setNull();
	}

//	public void setEventActionId(long zValue) throws Exception {
//		pEventActionId.setValue(zValue);
//	}
//
//	public long getEventActionId() throws Exception {
//		return pEventActionId.getValue();
//	}

	public void setTitle(String zValue) throws Exception {
		pTitle.setValue(zValue);
	}

	public String getTitle() throws Exception {
		return pTitle.getValue();
	}

	/**
	 * Constructor de la Clase
	 */
	public BizEvent() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("id_event", pIdevent);
		this.addItem("module", pModule);
		this.addItem("company", pCompany);
		this.addItem("descr_company", this.pDescrCompany);
		this.addItem("event_code", pEventCode);
		this.addItem("event_type", eventType);
		this.addItem("descr_event_code", pDescrEventCode);
		this.addItem("uniqueid", pKey);
		this.addItem("sender_user", pSenderUser);
		this.addItem("sender_node", pSenderNode);
		this.addItem("descr_node", pDescrNode);
		this.addItem("title", pTitle);
		this.addItem("info", pInfo);
		this.addItem("active", pActive);
		this.addItem("event_status", pStatus);
		this.addItem("event_level", pLevel);
		this.addItem("orig_event_id", pOrigIdevent);
		this.addItem("event_datetime", pEventDatetime);
		this.addItem("processed", pProcessed);
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "id_event", "Id", false, false, 18);
		this.addFixedItem(FIELD, "module", "Modulo", true, true, 50);
		this.addFixedItem(FIELD, "company", "Company", true, true, 50);
		this.addFixedItem(FIELD, "event_code", "Código", true, true, 5);
		this.addFixedItem(FIELD, "uniqueid", "Clave", true, false, 50);
		this.addFixedItem(FIELD, "sender_user", "Sender Usuario", true, false, 50);
		this.addFixedItem(FIELD, "sender_node", "Sender Nodo", true, false, 50);
		this.addFixedItem(FIELD, "title", "Título", true, false, 250);
		this.addFixedItem(FIELD, "info", "Info", true, false, 4000);
		this.addFixedItem(FIELD, "event_datetime", "Fecha/Hora", true, true, 10);

		this.addFixedItem(FIELD, "event_level", "Nivel", true, false, 5);
		this.addFixedItem(FIELD, "event_status", "Estado", true, false, 50);
		this.addFixedItem(FIELD, "active", "Activo?", true, false, 1);
		this.addFixedItem(FIELD, "orig_event_id", "Id", true, false, 18);
		
		this.addFixedItem(FIELD, "processed", "Procesado?", true, true, 1);
		this.addFixedItem(VIRTUAL, "event_type", "Evento", true, true, 50);
		this.addFixedItem(VIRTUAL, "descr_node", "Nodo", true, true, 200);
		this.addFixedItem(VIRTUAL, "descr_company", "Empresa", true, true, 50);
		this.addFixedItem(VIRTUAL, "descr_action", "Acción", true, true, 50);
		this.addFixedItem(VIRTUAL, "descr_event_code", "Evento", true, true, 50);
	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "EVT_EVENT";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Default read() method
	 */
	public boolean read(long zIdevent) throws Exception {
		addFilter("id_event", zIdevent);
		return read();
	}


//	protected static BizRegister getEventRegistered(String module, String company, long event) throws Exception {
//		BizRegister reg = new BizRegister();
//		reg.dontThrowException(true);
//		if (reg.read(module, company, event))
//			return reg;
//		return null;
//	}


//	public static void registerEvent(String module, String company,String store, long eventCode, String key, String title,String eventInfo) throws Exception {
//		BizEvent.registerEvent(module, company, store, 0, eventCode, key,title, eventInfo, null, -1, BizEvent.NOSTATUS, null);
//	}
	
	private boolean trace=false;
	public void setTrace(boolean v) {
		this.trace=v;
	}
	
	public void processEvent() throws Exception {
		if (this.pCompany.isEmpty()) this.pCompany.setValue(BizUsuario.getUsr().getCompany());
		if (this.pSenderUser.isEmpty()) this.pSenderUser.setValue(BizUsuario.getUsr().GetUsuario());
		if (this.pSenderNode.isEmpty()) this.pSenderNode.setValue(BizUsuario.getUsr().getNodo());
		if (this.pEventCode.isEmpty())
			JExcepcion.SendError("Debe especificar un codigo de evento");
				
		if (trace) {
			this.setEventDatetime(BizUsuario.getUsr().todayGMT());
			this.insert();
		}
		this.checkListeners();
	}
	
	
	public void checkListeners() throws Exception {
		JIterator<BizRegister> iter = this.getListeners().getStaticIterator();
		while (iter.hasMoreElements()) { 
			BizRegister r = iter.nextElement();
			if (!r.isOkSenderNode(this.pSenderNode.getValue())) continue;
			if (!r.isOkSenderUser(this.pSenderUser.getValue())) continue;
//			if (!r.isOkFilters(this)) continue;
			r.processEvent(this);
		}
	}
	
	public JRecords<BizRegister> getListeners() throws Exception {
		JRecords<BizRegister> recs = new JRecords<BizRegister>(BizRegister.class);
		recs.addFilter("company", this.getCompany());
		recs.addFilter("event_code", this.getEventCode());
		recs.readAll();
		recs.toStatic();
		return recs;
	}
	

	/*
	 * Register an event
	 */
//	public static void registerEvent(String module, String company,String store, long level, long eventCode,  String key, String title,String eventInfo, String status, long eventOrig, String active, Date datetime)throws Exception {
//
//		if (company == null) company = "DEFAULT";
//		BizCompany c = new BizCompany();
//		c.Read(company);
////		BizRegister r = BizEvent.getEventRegistered(module, company, eventCode);
//		BizEvent evt = new BizEvent();
//		if (key != null) {
//			evt.addFilter("company", company);
//			evt.addFilter("module", module);
//			evt.addFilter("event_code", eventCode);
//			evt.addFilter("uniqueid", key);
//			evt.dontThrowException(true);
//			if (evt.read())
//				return;
//		}
//		if (eventOrig > 0)
//			evt.setOrigIdevent(eventOrig);
//
//		evt.setActive(active);
//		evt.setStatus(status);
//		evt.setEventLevel(level);
//
//		evt.setCompany(company);
//		evt.setEventCode(eventCode);
//		evt.setProcessed(true);
//		if (r != null) {
//			if (r.getEventAction() > 0) {
//				evt.setEventAction(r.getEventAction());
//				evt.setEventActionId(r.getEventActionId());
//				evt.setProcessed(false);
//			}
//		}
//		evt.setModule(module);
//		evt.setKey(key);
//		evt.setTitle(title);
//		evt.setInfo(eventInfo);
//		evt.setNode(store);
//		if (datetime==null)
//			datetime=new Date();
//		evt.setEventDatetime(datetime);
//		evt.insert();
//
//	}

//	public static String getEventActionIdDescription(long code, long id)
//			throws Exception {
//		switch ((int) code) {
//		case EV_ACTION_MAIL:
//			return getMailDescription((int) id);
//		}
//		return "";
//	}

	protected static String getMailDescription(int id) throws Exception {
//		BizMailCasilla ms = new BizMailCasilla();
//		ms.dontThrowException(true);
//		if (ms.read(id))
//			return ms.getMailTo();
		return "";
	}
	
	private BizEventCode eventCode;
	public BizEventCode getObjEventCode() throws Exception {
		if (this.eventCode!=null) return this.eventCode;
		BizEventCode e = BizEventCode.findEventCode(this.pEventCode.getValue());
		return (this.eventCode=e);
	}

	private BizUsuario senderUser;
	public BizUsuario getObjSenderUser() throws Exception {
		if (this.senderUser!=null) return this.senderUser;
		BizUsuario u = new BizUsuario();
		u.Read(this.pSenderUser.getValue());
		return (this.senderUser=u);
	}

	private BizNodo senderNode;
	public BizNodo getObjSenderNode() throws Exception {
		if (this.senderNode!=null) return this.senderNode;
		BizNodo n = new BizNodo();
		n.Read(this.getCompany(), this.pSenderNode.getValue());
		return (this.senderNode=n);
	}
	
	private Object source;
	public void setSource(Object obj) {
		this.source=obj;
	}

	public Object getSource() {
		return this.source;
	}

	public boolean hasSource() {
		return this.source!=null;
	}

}
