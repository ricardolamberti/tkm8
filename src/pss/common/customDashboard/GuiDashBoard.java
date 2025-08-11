package pss.common.customDashboard;

import pss.common.customDashboard.config.BizDashBoardConfig;
import pss.core.win.JWin;

public class GuiDashBoard extends JWin {

	public int GetNroIcono() throws Exception {
		return 5053;
	}

	public String GetTitle() throws Exception {
		return "Dashboard";
	}

	public String getKeyField() throws Exception {
		return "usuario";
	}

	public String getDescripField() {
		return "usuario";
	}

	public GuiDashBoard() throws Exception {
	}
	
	public BizDashBoard GetcDato() throws Exception {
		return (BizDashBoard) this.getRecord();
	}

	public BizDashBoardConfig getConfig() throws Exception {
		return this.GetcDato().getConfig();
	}

	
}
