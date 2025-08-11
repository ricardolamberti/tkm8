package pss.core.connectivity.messageManager.common.core.company;

import pss.common.regions.company.BizCompany;
import pss.common.regions.company.GuiCompany;
import pss.common.regions.company.GuiNewCompany;
import pss.common.regions.company.JCompanyBusiness;
import pss.common.security.BizUsuario;
import pss.core.connectivity.messageManager.common.core.usuario.BizMMUsuario;

public abstract class JMMCompanyBusiness extends JCompanyBusiness {

	@Override
	public BizCompany getInstance() throws Exception {
		return new BizMMCompany();
	}

	@Override
	public GuiNewCompany getNewWinInstance() throws Exception {
		return new GuiMMNewCompany();
	}


	@Override
	public BizUsuario getUserInstance() throws Exception {
		return new BizMMUsuario();
	}

	@Override
	public GuiCompany getWinInstance() throws Exception {
		return new GuiMMCompany();
	}

}
