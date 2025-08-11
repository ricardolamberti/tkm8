package  pss.common.customDashboard.dynamic;

import pss.common.customDashboard.config.BizDashBoardConfig;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;

public class BizDynamicDash extends JRecord {

	private JString pCompany = new JString();
	private JString pPais = new JString();
	private JString pUserId = new JString();
	
	public void setCompany(String v) {
		this.pCompany.setValue(v);
	}
	
	public void setPais(String v) {
		this.pPais.setValue(v);
	}

	public void setUserId(String v) {
		this.pUserId.setValue(v);
	}
	
	public String getCompany() throws Exception {
		return this.pCompany.getValue();
	}
	
	public String getPais() throws Exception {
		return this.pPais.getValue();
	}

	public String getUserId() throws Exception {
		return this.pUserId.getValue();
	}
	
	public BizDynamicDash() throws Exception {
	}

	public void createProperties() throws Exception {
		
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {

	}
	@Override
	protected boolean loadForeignFields() throws Exception {
		return true;
	}

	
	public String GetTable() {
		return "";
	}

	public JRecords<BizDashBoardConfig> getConfigs() throws Exception {
		JRecords<BizDashBoardConfig> recs = new JRecords<BizDashBoardConfig>(BizDashBoardConfig.class);
		recs.addFilter("company", this.getCompany());
		recs.addFilter("pais", this.getPais());
		recs.addFilter("userid", this.getUserId());
		recs.addOrderBy("dash_order");
		return recs;
	}




}
