package pss.core.connectivity.messageManager.common.core.company;

import pss.common.regions.company.GuiNewCompany;
import pss.core.services.records.JRecord;

public class GuiMMNewCompany extends GuiNewCompany {

	public GuiMMNewCompany() throws Exception {
		super();
	}

	@Override
	public JRecord ObtenerDato() throws Exception {
		return new BizMMNewCompany();
	}
}
