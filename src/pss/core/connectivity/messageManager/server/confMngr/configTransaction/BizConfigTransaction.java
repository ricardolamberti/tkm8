package pss.core.connectivity.messageManager.server.confMngr.configTransaction;

import java.util.Date;

import pss.core.services.fields.JDate;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizConfigTransaction extends JRecord {

	private JString pCompany = new JString();
	private JString pCountry = new JString();
	private JString pStore = new JString();
	private JString pModuleId = new JString();
	private JLong pConfId = new JLong();
	private JDate pCreationDatetime = new JDate();
	private JDate pLastModifdatetime = new JDate();
	private JString pState = new JString();
	private JString pDescription = new JString();
	private JString pTransactionMsg = new JString();

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

	public void setCountry(String zValue) throws Exception {
		pCountry.setValue(zValue);
	}

	public String getCountry() throws Exception {
		return pCountry.getValue();
	}

	public boolean isNullCountry() throws Exception {
		return pCountry.isNull();
	}

	public void setNullToCountry() throws Exception {
		pCountry.setNull();
	}

	public void setStore(String zValue) throws Exception {
		pStore.setValue(zValue);
	}

	public String getStore() throws Exception {
		return pStore.getValue();
	}

	public boolean isNullStore() throws Exception {
		return pStore.isNull();
	}

	public void setNullToStore() throws Exception {
		pStore.setNull();
	}

	public void setModuleId(String zValue) throws Exception {
		pModuleId.setValue(zValue);
	}

	public String getModuleId() throws Exception {
		return pModuleId.getValue();
	}

	public boolean isNullModuleId() throws Exception {
		return pModuleId.isNull();
	}

	public void setNullToModuleId() throws Exception {
		pModuleId.setNull();
	}

	public void setConfId(long zValue) throws Exception {
		pConfId.setValue(zValue);
	}

	public long getConfId() throws Exception {
		return pConfId.getValue();
	}

	public boolean isNullConfId() throws Exception {
		return pConfId.isNull();
	}

	public void setNullToConfId() throws Exception {
		pConfId.setNull();
	}

	public void setCreationDatetime(Date zValue) throws Exception {
		pCreationDatetime.setValue(zValue);
	}

	public Date getCreationDatetime() throws Exception {
		return pCreationDatetime.getValue();
	}

	public boolean isNullCreationDatetime() throws Exception {
		return pCreationDatetime.isNull();
	}

	public void setNullToCreationDatetime() throws Exception {
		pCreationDatetime.setNull();
	}

	public void setLastModifdatetime(Date zValue) throws Exception {
		pLastModifdatetime.setValue(zValue);
	}

	public Date getLastModifdatetime() throws Exception {
		return pLastModifdatetime.getValue();
	}

	public boolean isNullLastModifdatetime() throws Exception {
		return pLastModifdatetime.isNull();
	}

	public void setNullToLastModifdatetime() throws Exception {
		pLastModifdatetime.setNull();
	}

	public void setState(String zValue) throws Exception {
		pState.setValue(zValue);
	}

	public String getState() throws Exception {
		return pState.getValue();
	}

	public boolean isNullState() throws Exception {
		return pState.isNull();
	}

	public void setNullToState() throws Exception {
		pState.setNull();
	}

	public void setDescription(String zValue) throws Exception {
		pDescription.setValue(zValue);
	}

	public String getDescription() throws Exception {
		return pDescription.getValue();
	}

	public boolean isNullDescription() throws Exception {
		return pDescription.isNull();
	}

	public void setNullToDescription() throws Exception {
		pDescription.setNull();
	}

	public void setTransactionMsg(String zValue) throws Exception {
		pTransactionMsg.setValue(zValue);
	}

	public String getTransactionMsg() throws Exception {
		return pTransactionMsg.getValue();
	}

	public boolean isNullTransactionMsg() throws Exception {
		return pTransactionMsg.isNull();
	}

	public void setNullToTransactionMsg() throws Exception {
		pTransactionMsg.setNull();
	}

	/**
	 * Constructor de la Clase
	 */
	public BizConfigTransaction() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("company", pCompany);
		this.addItem("country", pCountry);
		this.addItem("store", pStore);
		this.addItem("module_id", pModuleId);
		this.addItem("conf_id", pConfId);
		this.addItem("creation_datetime", pCreationDatetime);
		this.addItem("last_modif_datetime", pLastModifdatetime);
		this.addItem("state", pState);
		this.addItem("description", pDescription);
		this.addItem("transaction_msg", pTransactionMsg);
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "company", "Company", true, true, 15);
		this.addFixedItem(KEY, "country", "Country", true, true, 50);
		this.addFixedItem(KEY, "store", "Estación", true, true, 50);
		this.addFixedItem(KEY, "module_id", "Modulo", true, true, 50);
		this.addFixedItem(KEY, "conf_id", "Id Configuracion", true, true, 10);
		this.addFixedItem(FIELD, "creation_datetime", "Fecha Aplicacion", true, true, 10);
		this.addFixedItem(FIELD, "last_modif_datetime", "Fecha Ultimo Cambio Estado", true, true, 10);
		this.addFixedItem(FIELD, "state", "Estado", true, true, 5);
		this.addFixedItem(FIELD, "description", "Description", true, true, 50);
		this.addFixedItem(FIELD, "transaction_msg", "Transaccion", true, true, 800);
	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "ho_config_transaction";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Default read() method
	 */
	public boolean read(String zCompany, String zCountry, String zStore, String zModuleId, long zConfId) throws Exception {
		addFilter("company", zCompany);
		addFilter("country", zCountry);
		addFilter("store", zStore);
		addFilter("module_id", zModuleId);
		addFilter("conf_id", zConfId);
		return read();
	}
}
