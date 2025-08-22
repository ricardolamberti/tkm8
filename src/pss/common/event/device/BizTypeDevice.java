package pss.common.event.device;

import pss.common.event.manager.BizEvent;
import pss.common.security.BizUsuario;
import pss.common.security.GuiUsuarios;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JWins;

public class BizTypeDevice  extends JRecord {

	protected JLong pIdTypeDevice = new JLong();
	protected JString pCompany = new JString();
	protected JString pDescription = new JString();
	protected JString pLogica = new JString();
	protected JString pSender = new JString();
	protected JString pTagId = new JString();
	protected JString pBotId = new JString();

	public void setIdTypeDevice(Long zValue) throws Exception {
		pIdTypeDevice.setValue(zValue);
	}

	public long getIdTypeDevice() throws Exception {
		return pIdTypeDevice.getValue();
	}


	public void setLogica(String zValue) throws Exception {
		pLogica.setValue(zValue);
	}

	public String getLogica() throws Exception {
		return pLogica.getValue();
	}
	public void setCompany(String zValue) throws Exception {
		pCompany.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}
	public void setSender(String zValue) throws Exception {
		pSender.setValue(zValue);
	}

	public String getSender() throws Exception {
		return pSender.getValue();
	}
	public void setTagId(String zValue) throws Exception {
		pTagId.setValue(zValue);
	}

	public String getTagId() throws Exception {
		return pTagId.getValue();
	}
	public void setBotId(String zValue) throws Exception {
		pBotId.setValue(zValue);
	}

	public String getBotId() throws Exception {
		return pBotId.getValue();
	}
	



	/**
	 * Constructor de la Clase
	 */
	public BizTypeDevice() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("id_typedevice", pIdTypeDevice);
		this.addItem("logica", pLogica);
		this.addItem("company", pCompany);
		this.addItem("description", pDescription);
		this.addItem("sender", pSender);
		this.addItem("tagid", pTagId);
		this.addItem("botid", pBotId);
		}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "id_typedevice", "Id", false, false, 64);
		this.addFixedItem(FIELD, "logica", "logica", true, true, 50);
		this.addFixedItem(FIELD, "company", "company", true, true, 50);
		this.addFixedItem(FIELD, "description", "description", true, true, 200);
		this.addFixedItem(FIELD, "sender", "sender", true, false, 20);
		this.addFixedItem(FIELD, "tagid", "tagid", true, false, 400);
		this.addFixedItem(FIELD, "botid", "botid", true, false, 400);
	}

  @Override
  public void createControlProperties() throws Exception {
  	this.addControlsProperty("sender", createControlCombo(GuiUsuarios.class,"usuario", null));
  	this.addControlsProperty("logica", createControlCombo(JWins.createVirtualWinsFromMap(getLogicas()),null, null) );
  	super.createControlProperties();
  }
	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "EVT_TYPEDEVICE";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static final String LOGICA_MAIL = "mail";
	public static final String LOGICA_TELEGRAM= "telegram";
	public static final String LOGICA_WHATSAPP= "whatsapp";
	public static final String LOGICA_PUSHNOTIFICATION= "push";
 
	static JMap<String,String> logicas;

  public static JMap<String,String> getLogicas() throws Exception {
  	if (logicas!=null) return logicas;
  	JMap<String,String> maps = JCollectionFactory.createOrderedMap();
  	maps.addElement(LOGICA_MAIL, "Enviar mail");
  	maps.addElement(LOGICA_TELEGRAM, "Enviar telegram mensaje");
   	maps.addElement(LOGICA_WHATSAPP, "Enviar whatsapp mensaje");
  	maps.addElement(LOGICA_PUSHNOTIFICATION, "Notificaciones celular");
  	return logicas=maps;
  }
	/**
	 * Default read() method
	 */
	public boolean read(long zIdTypeDevice) throws Exception {
		addFilter("id_typedevice", zIdTypeDevice);
		return read();
	}
	
	transient ILogicaDevice logicaDevice;
	ILogicaDevice getLogicaDevice() throws Exception {
		if (logicaDevice!=null) return logicaDevice;
		return logicaDevice=getRawLogicaDevice();
		
	}
	ILogicaDevice getRawLogicaDevice() throws Exception {
		if (getLogica().equalsIgnoreCase(LOGICA_TELEGRAM)) return logicaDevice=new JLogicaDeviceTelegram();
//		if (getType().equalsIgnoreCase("whatsapp")) return logicaDevice=new JLogicaDevicWhatsApp();
		if (getLogica().equalsIgnoreCase(LOGICA_MAIL)) return logicaDevice=new JLogicaDeviceMail();
		
		// web, android, iOs
		return new JLogicaDevicePushNotification();
		
	}
	
	transient BizUsuario objSender;
	public BizUsuario getObjUserSender() throws Exception {
		if (objSender!=null) return objSender;
		BizUsuario usr =new BizUsuario();
		usr.dontThrowException(true);
		if (!usr.Read(getSender())) return null;
		return objSender = usr;
	}

	
	 public void processSubscribe(BizDevice device, BizChannel channel) throws Exception {
	  	getLogicaDevice().subscribe(device, channel);
	  }
		

  public void processAddMessage(BizDevice device, String title, String info) throws Exception {
  	getLogicaDevice().sendMessage(device, title, info);
  }
	
  public void processAddMessage(BizDevice device,BizEvent e) throws Exception {
  	getLogicaDevice().sendMessage(device, e);
  }
	
  public String  getLinkSubscribe(BizChannel channel) throws Exception {
  	return getLogicaDevice().getLinkSubscribe(this,channel);
		
	}

}
