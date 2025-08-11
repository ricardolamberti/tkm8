package  pss.common.customDashboard.component;

import pss.common.customDashboard.config.BizDashBoardConfig;
import pss.core.services.records.JRecord;

public class BizDashComponent extends JRecord {

	private BizDashBoardConfig config;
	
	public void setConfig(BizDashBoardConfig v) {
		this.config = v;
	}
	
	public BizDashBoardConfig getConfig() {
		return this.config;
	}
	
	public BizDashComponent() throws Exception {
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





}
