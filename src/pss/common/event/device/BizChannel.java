package pss.common.event.device;

import pss.common.event.manager.BizEvent;
import pss.common.security.BizUsuario;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.services.JExec;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;

public class BizChannel  extends JRecord {
	
	protected JLong pIdChannel = new JLong();
	protected JLong pIdDevice= new JLong();
	protected JString pUsuario = new JString();
	protected JString pCompany = new JString();
	protected JString pUUIDChannel = new JString();

	public void setIdChannel(Long zValue) throws Exception {
		pIdChannel.setValue(zValue);
	}

	public long getIdChannel() throws Exception {
		return pIdChannel.getValue();
	}

	public void setIdDevice(long zValue) throws Exception {
		pIdDevice.setValue(zValue);
	}

	public long getIdDevice() throws Exception {
		return pIdDevice.getValue();
	}
	public void setUsuario(String zValue) throws Exception {
		pUsuario.setValue(zValue);
	}

	public String getUsuario() throws Exception {
		return pUsuario.getValue();
	}
	public void setCompany(String zValue) throws Exception {
		pCompany.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}
	public void setUUIDChannel(String zValue) throws Exception {
		pUUIDChannel.setValue(zValue);
	}

	public String getUUIDChannel() throws Exception {
		return pUUIDChannel.getValue();
	}

	/**
	 * Constructor de la Clase
	 */
	public BizChannel() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("id_channel", pIdChannel);
		this.addItem("id_device", pIdDevice);
		this.addItem("usuario", pUsuario);
		this.addItem("company", this.pCompany);
		this.addItem("uuid_channel", this.pUUIDChannel);
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "id_channel", "channel", false, false, 64);
		this.addFixedItem(FIELD, "id_device", "Id device", true, true, 64);
		this.addFixedItem(FIELD, "company", "Company", true, true, 50);
		this.addFixedItem(FIELD, "usuario", "usuario", true, true, 15);
		this.addFixedItem(FIELD, "uuid_channel", "uuid channel", true, false, 1000);
	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "EVT_CHANNEL";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Default read() method
	 */
	public boolean read(long zIdChannel) throws Exception {
		addFilter("id_channel", zIdChannel);
		return read();
	}
	public boolean readByUser(String user,long deviceType) throws Exception {
		addFilter("usuario", user);
		addJoin("JOIN", "EVT_DEVICE", "D", "D.id_device=EVT_CHANNEL.id_device");
		addFilter("D","id_type", deviceType,"=");
		return read();
	}
	
	public void execProcessSendMessage(String title,String info) throws Exception {
		JExec oExec = new JExec(this, "processSendMessage") {

			@Override
			public void Do() throws Exception {
				processSendMessage(title,info);
			}
		};
		oExec.execute();
	}
  
  
	public void execProcessAddChannel(String zUUID,String zType) throws Exception {
		JExec oExec = new JExec(this, "processAddChannel") {

			@Override
			public void Do() throws Exception {
			 processAddChannel(zUUID,zType,-1);
			}
		};
		oExec.execute();
	}
	public void execProcessAddChannel(String zUUID,long zType) throws Exception {
		JExec oExec = new JExec(this, "processAddChannel") {

			@Override
			public void Do() throws Exception {
			 processAddChannel(zUUID,"others",zType);
			}
		};
		oExec.execute();
	}

	public static String getIdDeviceWeb() throws Exception {
		return "WEB_"+BizUsuario.getUsr().GetUsuario()+"_"+BizUsuario.getUsr().getCompany();
	}
	
	

	public void createChannelWeb() throws Exception {
		if (JBDatos.GetBases().getPrivateCurrentDatabase().isTransactionInProgress())
			processAddChannel(getIdDeviceWeb(),"Web",-1);
	
		else
			execProcessAddChannel(getIdDeviceWeb(),"Web");	}
	
	public void processAddChannel(String zUUID,String zType, long idType) throws Exception {
		BizUsuario usuario ;
		if (!pUsuario.hasValue()) {
			usuario = BizUsuario.getUsr();
			if (usuario==null) throw new Exception("no user logged in");
		} else {
			usuario=getObjUsuario();
			if (usuario==null) throw new Exception("user unknown");
		}
		BizDevice device = new BizDevice();
		device.dontThrowException(true);
		if (zUUID==null || !device.readByUUID(zUUID)) {
			if (zUUID!=null) device.setUUID(zUUID);
			device.setType(zType);
			if (idType!=-1) device.setIdType(idType);
			device.setCompany(usuario.getCompany());
			device.setActive(true);
			device.processInsert();
		}
		
		clearFilters();
		addFilter("company", usuario.getCompany());
		addFilter("usuario", usuario.GetUsuario());
		addFilter("id_device", device.getIdDevice());
		dontThrowException(true);
		if (read()) 
			return ;
		
		filtersToProp();
		processInsert();
		String uuidChannel = getUsuario()+"_|_"+getCompany()+"_|_"+zUUID+"_|_"+device.getIdDevice()+"_|_"+zType+"_|_"+ getIdChannel()+"_|_"+JDateTools.CurrentDateTime();
		setUUIDChannel(JTools.encryptMessage(uuidChannel));
		processUpdate();
	}
	
	transient BizDevice objDevice;
	public BizDevice getObjDevice() throws Exception {
		if (objDevice!=null) return objDevice;
		BizDevice dev= new BizDevice();
		dev.dontThrowException(true);
		if (!dev.read(getIdDevice()))
			return null;
		return objDevice=dev;
	}
	transient BizUsuario objUsuario;
	public BizUsuario getObjUsuario() throws Exception {
		if (objUsuario!=null) return objUsuario;
		BizUsuario dev= new BizUsuario();
		dev.dontThrowException(true);
		if (!dev.Read(pUsuario.getValue()))
			return null;
		return objUsuario=dev;
	}
	
	public boolean checkChannel(String uuidChannel,String usuario,String company,String uuid,long deviceID,String type ) throws Exception {
		if (!uuidChannel.equals(getUUIDChannel())) return false; 
		if (!usuario.equals(getUsuario())) return false; 
		if (!company.equals(getCompany())) return false; 
		if (deviceID!=getIdDevice()) return false; 
		BizDevice device = getObjDevice();
		if (device==null) return false;
		if (!uuid.equals(device.getUUID())) return false; 
		if (!type.equals(device.getType())) return false; 
		return true;
		
	}
	public long processFindDevice(String uuid) throws Exception {
		BizDevice device = new BizDevice();
		device.dontThrowException(true);
		if (!device.readByUUID(uuid)) return -1;
		return device.getIdDevice();
	}
	
	public boolean isSubscribe() throws Exception {
		return getObjDevice().hasUUID();
	}
	public void processSubscribe() throws Exception {
		getObjDevice().processSubscribe(this);
		objDevice=null;
		
	}

	public void processSendEvent(BizEvent e) throws Exception {
		if (!getObjDevice().getActive()) return;
		if (!isSubscribe())
			processSubscribe();
		if (isSubscribe())
			getObjDevice().processAddMessage(e);
		
	}
	public void processSendMessage(String title, String info) throws Exception {
		if (!getObjDevice().getActive()) return;
		if (!isSubscribe())
			processSubscribe();
		if (isSubscribe())
			getObjDevice().processAddMessage(title, info);
		
	}

	public String getLinkSubscribe() throws Exception {
		return getObjDevice().getLinkSubscribe(this);
		
	}

}
