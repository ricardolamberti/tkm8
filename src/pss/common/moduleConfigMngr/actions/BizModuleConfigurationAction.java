package  pss.common.moduleConfigMngr.actions;

import java.util.Date;

import pss.core.services.fields.JDate;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizModuleConfigurationAction extends JRecord {
	public static final String ACT_STATE_NEW = "NEW";
	public static final String ACT_STATE_REDO = "REDO";
	public static final String ACT_STATE_PROCESSING = "PRC";
	public static final String ACT_STATE_DONE = "DONE";
	public static final String ACT_STATE_CANCELED = "CANCEL";

	public static final String ACT_RESULT_OK = "OK";
	public static final String ACT_RESULT_ERROR = "ERROR";

	private JString pCompany = new JString();
	private JString pCountry = new JString();
	private JString pNode = new JString();
	private JString pConfigType = new JString();
	private JString pModuleId = new JString();
	private JLong pActionSequence = new JLong();
	private JString pActionId = new JString();
	private JString pActionData = new JString();
	private JDate pActionCreationdatetime = new JDate();
	private JString pActionState = new JString();
	private JDate pActionStatedatetime = new JDate();
	private JString pActionResult = new JString();
	private JDate pActionResultdatetime = new JDate();

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

	public void setActionSequence(long zValue) throws Exception {
		pActionSequence.setValue(zValue);
	}

	public long getActionSequence() throws Exception {
		return pActionSequence.getValue();
	}

	public boolean isNullActionSequence() throws Exception {
		return pActionSequence.isNull();
	}

	public void setNullToActionSequence() throws Exception {
		pActionSequence.setNull();
	}

	public void setActionId(String zValue) throws Exception {
		pActionId.setValue(zValue);
	}

	public String getActionId() throws Exception {
		return pActionId.getValue();
	}

	public boolean isNullActionId() throws Exception {
		return pActionId.isNull();
	}

	public void setNullToActionId() throws Exception {
		pActionId.setNull();
	}

	public void setActionData(String zValue) throws Exception {
		pActionData.setValue(zValue);
	}

	public String getActionData() throws Exception {
		return pActionData.getValue();
	}

	public boolean isNullActionData() throws Exception {
		return pActionData.isNull();
	}

	public void setNullToActionData() throws Exception {
		pActionData.setNull();
	}

	public void setActionCreationdatetime(Date zValue) throws Exception {
		pActionCreationdatetime.setValue(zValue);
	}

	public Date getActionCreationdatetime() throws Exception {
		return pActionCreationdatetime.getValue();
	}

	public boolean isNullActionCreationdatetime() throws Exception {
		return pActionCreationdatetime.isNull();
	}

	public void setNullToActionCreationdatetime() throws Exception {
		pActionCreationdatetime.setNull();
	}

	public void setActionState(String zValue) throws Exception {
		pActionState.setValue(zValue);
	}

	public String getActionState() throws Exception {
		return pActionState.getValue();
	}

	public boolean isNullActionState() throws Exception {
		return pActionState.isNull();
	}

	public void setNullToActionState() throws Exception {
		pActionState.setNull();
	}

	public void setActionStatedatetime(Date zValue) throws Exception {
		pActionStatedatetime.setValue(zValue);
	}

	public Date getActionStatedatetime() throws Exception {
		return pActionStatedatetime.getValue();
	}

	public boolean isNullActionStatedatetime() throws Exception {
		return pActionStatedatetime.isNull();
	}

	public void setNullToActionStatedatetime() throws Exception {
		pActionStatedatetime.setNull();
	}

	public void setActionResult(String zValue) throws Exception {
		pActionResult.setValue(zValue);
	}

	public String getActionResult() throws Exception {
		return pActionResult.getValue();
	}

	public boolean isNullActionResult() throws Exception {
		return pActionResult.isNull();
	}

	public void setNullToActionResult() throws Exception {
		pActionResult.setNull();
	}

	public void setActionResultdatetime(Date zValue) throws Exception {
		pActionResultdatetime.setValue(zValue);
	}

	public Date getActionResultdatetime() throws Exception {
		return pActionResultdatetime.getValue();
	}

	public boolean isNullActionResultdatetime() throws Exception {
		return pActionResultdatetime.isNull();
	}

	public void setNullToActionResultdatetime() throws Exception {
		pActionResultdatetime.setNull();
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

	/**
	 * Constructor de la Clase
	 */
	public BizModuleConfigurationAction() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("company", pCompany);
		this.addItem("country", pCountry);
		this.addItem("node", pNode);
		this.addItem("module_id", pModuleId);
		this.addItem("config_type", pConfigType);
		this.addItem("action_sequence", pActionSequence);
		this.addItem("action_id", pActionId);
		this.addItem("action_data", pActionData);
		this.addItem("action_creation_datetime", pActionCreationdatetime);
		this.addItem("action_state", pActionState);
		this.addItem("action_state_datetime", pActionStatedatetime);
		this.addItem("action_result", pActionResult);
		this.addItem("action_result_datetime", pActionResultdatetime);
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "company", "Company", true, true, 15);
		this.addFixedItem(KEY, "country", "Country", true, true, 50);
		this.addFixedItem(KEY, "node", "Nodo", true, true, 50);
		this.addFixedItem(KEY, "module_id", "Module id", true, true, 50);
		this.addFixedItem(KEY, "config_type", "Config type", true, true, 10);
		this.addFixedItem(KEY, "action_sequence", "Action sequence", false, false, 10);
		this.addFixedItem(FIELD, "action_id", "Action id", true, true, 5);
		this.addFixedItem(FIELD, "action_data", "Action data", true, false, 8000);
		this.addFixedItem(FIELD, "action_creation_datetime", "Action creation datetime", true, true, 10);
		this.addFixedItem(FIELD, "action_state", "Action state", true, true, 5);
		this.addFixedItem(FIELD, "action_state_datetime", "Action state datetime", true, true, 10);
		this.addFixedItem(FIELD, "action_result", "Action result", true, false, 5);
		this.addFixedItem(FIELD, "action_result_datetime", "Action result datetime", true, false, 10);
	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "mdl_config_actions";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Default read() method
	 */
	public boolean read(String zCompany, String zCountry, String zNode, String zModuleId, String zConfigType, long zActionSequence) throws Exception {
		addFilter("company", zCompany);
		addFilter("country", zCountry);
		addFilter("node", zNode);
		addFilter("module_id", zModuleId);
		addFilter("config_type", zConfigType);
		addFilter("action_sequence", zActionSequence);
		return read();
	}
}
