package pss.common.event.manager;

import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizEventRead extends JRecord {



	protected JLong pIdevent = new JLong();
	protected JString pImei = new JString();


	public void setIdevent(long zValue) throws Exception {
		pIdevent.setValue(zValue);
	}


	public long getIdevent() throws Exception {
		return pIdevent.getValue();
	}

	public boolean isNullIdevent() throws Exception {
		return pIdevent.isNull();
	}

	public String getImei() throws Exception {
		return pImei.getValue();
	}

	public void setImei(String zValue) throws Exception {
		pImei.setValue(zValue);
	}

	public boolean isNullImei() throws Exception {
		return pImei.isNull();
	}

	public void setNullToIdevent() throws Exception {
		pIdevent.setNull();
	}


	/**
	 * Constructor de la Clase
	 */
	public BizEventRead() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("id_event", pIdevent);
		this.addItem("imei", pImei);
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {

		this.addFixedItem(KEY, "id_event", "Id", true, false, 18);
		this.addFixedItem(KEY, "imei", "imei", true, false, 18);
	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "EVT_EVENT_READ";
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
	
	/**
	 * Default read() method
	 */
	public boolean read(String imei, long id) throws Exception {
		addFilter("imei", imei);
		addFilter("id_event", id);
		return read();
	}


}
