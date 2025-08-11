package pss.common.customDashboard;

import pss.common.customDashboard.config.GuiDashBoardConfigs;
import pss.common.security.BizUsuario;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActWins;

public class GuiModuloCustomDashBoard extends GuiModulo {

	public GuiModuloCustomDashBoard() throws Exception {
		super();
		SetModuleName("DashBoard");
		SetNroIcono(26);
	}

	@Override
	public BizAction createDynamicAction() throws Exception {
		return addAction(1, "DashBoard", null, 26, true, true);
	}

	@Override
	public void createActionMap() throws Exception {
		this.addAction(10, "Configs", null, 43, true, false);
		this.addAction(20, "My DashBoard", null, 43, true, false);
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()== 1) return new JActQuery(this);
		if (a.getId() == 10) return new JActWins(this.getConfigs());
		if (a.getId() == 20) return new JActQuery(DashBoardModule.makeMyConfigDashBoard());
			
		return null;
	}


	private JWins getConfigs() throws Exception {
		GuiDashBoardConfigs wins = new GuiDashBoardConfigs();
		wins.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
		wins.getRecords().addFilter("pais", BizUsuario.getUsr().getCompany());
		return wins;
	}

}
