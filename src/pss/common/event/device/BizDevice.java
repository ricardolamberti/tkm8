package pss.common.event.device;

import pss.common.event.manager.BizEvent;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizDevice extends JRecord {

	protected JLong pIdDevice = new JLong();
	protected JString pUUID = new JString();
	protected JString pType = new JString();
	protected JLong   pIdType = new JLong();
	protected JString pCompany = new JString();
	protected JBoolean pActive = new JBoolean();

	public void setIdDevice(Long zValue) throws Exception {
		pIdDevice.setValue(zValue);
	}

	public long getIdDevice() throws Exception {
		return pIdDevice.getValue();
	}

	public void setUUID(String zValue) throws Exception {
		pUUID.setValue(zValue);
	}

	public String getUUID() throws Exception {
		return pUUID.getValue();
	}
	
	public boolean hasUUID() throws Exception {
		return pUUID.isNotNull();
	}
	public void setIdType(long zValue) throws Exception {
		pIdType.setValue(zValue);
	}

	public long getIdType() throws Exception {
		return pIdType.getValue();
	}
	public void setType(String zValue) throws Exception {
		pType.setValue(zValue);
	}

	public String getType() throws Exception {
		return pType.getValue();
	}
	public void setCompany(String zValue) throws Exception {
		pCompany.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}
	public void setActive(boolean zValue) throws Exception {
		pActive.setValue(zValue);
	}

	public boolean getActive() throws Exception {
		return pActive.getValue();
	}

	/**
	 * Constructor de la Clase
	 */
	public BizDevice() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("id_device", pIdDevice);
		this.addItem("UUID", pUUID);
		this.addItem("type", pType);
		this.addItem("id_type", pIdType);
		this.addItem("company", pCompany);
		this.addItem("active", pActive);
		}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "id_device", "Id", false, false, 64);
		this.addFixedItem(FIELD, "UUID", "UUID", true, false, 500);
		this.addFixedItem(FIELD, "type", "Type", true, true, 50);
		this.addFixedItem(FIELD, "id_type", "Id Type", true, false, 18);
		this.addFixedItem(FIELD, "company", "company", true, true, 50);
		this.addFixedItem(FIELD, "active", "active", true, false, 1);
	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "EVT_DEVICE";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Default read() method
	 */
	public boolean read(long zIdDevice) throws Exception {
		addFilter("id_device", zIdDevice);
		return read();
	}
	public boolean readByUUID(String zUUID) throws Exception {
		addFilter("UUID", zUUID);
		return read();
	}
	
	private transient BizTypeDevice objTypeDevice;
	public BizTypeDevice getObjTypeDevice() throws Exception {
		if (objTypeDevice!=null) return objTypeDevice;
		BizTypeDevice type = new BizTypeDevice();
		type.dontThrowException(true);
		if (pIdType.isNull()) {
			type.setLogica(BizTypeDevice.LOGICA_PUSHNOTIFICATION);
		} else {
			if (!type.read(pIdType.getValue())) return null;
		}
		return objTypeDevice=type;
	}
	
	 public void processSubscribe(BizChannel channel) throws Exception {
	  	getObjTypeDevice().processSubscribe(this, channel);
	  }
		
	
  public void processAddMessage(String title, String info) throws Exception {
  	getObjTypeDevice().processAddMessage(this, title, info);
  }
	
  public void processAddMessage(BizEvent e) throws Exception {
  	getObjTypeDevice().processAddMessage(this, e);
  }
  public String getLinkSubscribe(BizChannel channel) throws Exception {
  	return getObjTypeDevice().getLinkSubscribe(channel);
		
	}

}
