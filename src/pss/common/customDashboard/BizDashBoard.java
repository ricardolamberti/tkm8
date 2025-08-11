package pss.common.customDashboard;

import pss.common.customDashboard.config.BizDashBoardConfig;
import pss.core.services.records.JRecord;

public class BizDashBoard extends JRecord {

	public BizDashBoard() throws Exception {
	}

	private BizDashBoardConfig config;
	public void setObjConfig(BizDashBoardConfig cf) {
		this.config=cf;
	}
	
	public BizDashBoardConfig getConfig() throws Exception {
		return this.config;
	}


}
