package pss.core.connectivity.messageManager.common.core.company;

import pss.common.regions.company.GuiCompany;
import pss.core.services.records.JRecord;

public class GuiMMCompany extends GuiCompany {

	public GuiMMCompany() throws Exception {
		super();
	}

	@Override
	public JRecord ObtenerDato() throws Exception {
		return new BizMMCompany();
	}
	
}
