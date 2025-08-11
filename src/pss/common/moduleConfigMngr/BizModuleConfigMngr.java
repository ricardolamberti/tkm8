package  pss.common.moduleConfigMngr;

import java.util.Date;

import pss.common.security.BizUsuario;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizModuleConfigMngr extends JRecord {

	private JString pCompany = new JString();
	private JString pNode = new JString();
	private JString pCountry = new JString();
	private JString pModuleId = new JString();
	private JString pConfigType = new JString();
	private JLong pConfigId = new JLong();
	private JDate pConfigDate = new JDate();

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

	public void setNode(String zValue) throws Exception {
		pNode.setValue(zValue);
	}

	public String getNode() throws Exception {
		return pNode.getValue();
	}

	public boolean isNullNode() throws Exception {
		return pNode.isNull();
	}

	public void setNullToNode() throws Exception {
		pNode.setNull();
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

	public void setConfigType(String zValue) throws Exception {
		pConfigType.setValue(zValue);
	}

	public String getConfigType() throws Exception {
		return pConfigType.getValue();
	}

	public boolean isNullConfigType() throws Exception {
		return pConfigType.isNull();
	}

	public void setNullToConfigType() throws Exception {
		pConfigType.setNull();
	}

	public void setConfigId(long zValue) throws Exception {
		pConfigId.setValue(zValue);
	}

	public long getConfigId() throws Exception {
		return pConfigId.getValue();
	}

	public boolean isNullConfigId() throws Exception {
		return pConfigId.isNull();
	}

	public void setNullToConfigId() throws Exception {
		pConfigId.setNull();
	}

	public void setConfigDate(Date zValue) throws Exception {
		pConfigDate.setValue(zValue);
	}

	public Date getConfigDate() throws Exception {
		return pConfigDate.getValue();
	}

	public boolean isNullConfigDate() throws Exception {
		return pConfigDate.isNull();
	}

	public void setNullToConfigDate() throws Exception {
		pConfigDate.setNull();
	}

	/**
	 * Constructor de la Clase
	 */
	public BizModuleConfigMngr() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("company", pCompany);
		this.addItem("node", pNode);
		this.addItem("country", pCountry);
		this.addItem("module_id", pModuleId);
		this.addItem("config_type", pConfigType);
		this.addItem("config_id", pConfigId);
		this.addItem("config_date", pConfigDate);
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "company", "Company", true, true, 15);
		this.addFixedItem(KEY, "node", "Nodo", true, true, 15);
		this.addFixedItem(KEY, "country", "Pais", true, true, 15);
		this.addFixedItem(KEY, "module_id", "Module id", true, true, 50);
		this.addFixedItem(KEY, "config_type", "Config type", true, true, 10);
		this.addFixedItem(FIELD, "config_id", "Config id", true, false, 10);
		this.addFixedItem(FIELD, "config_date", "Config date", true, true, 10);
	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "mdl_config_mngr";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Default read() method
	 */
	public boolean read(String zCompany, String zNode, String zCountry, String zModuleId, String zConfigType) throws Exception {
		addFilter("company", zCompany);
		addFilter("node", zNode);
		addFilter("country", zCountry);
		addFilter("module_id", zModuleId);
		addFilter("config_type", zConfigType);
		return read();
	}

	public static BizModuleConfigMngr getConfigInfo(String zCompany, String zNode, String zCountry, String zModuleId, String zConfigType) throws Exception {
		BizModuleConfigMngr bMCM = new BizModuleConfigMngr();
		bMCM.dontThrowException(true);
		bMCM.read(zCompany, zNode, zCountry, zModuleId, zConfigType);
		return bMCM;
	}

	public static BizModuleConfigMngr getConfigInfo(String zModuleId, String zConfigType) throws Exception {
		return getConfigInfo(BizUsuario.getUsr().getCompany(), BizUsuario.getUsr().getNodo(), BizUsuario.getUsr().getCountry(), zModuleId, zConfigType);
	}

}
