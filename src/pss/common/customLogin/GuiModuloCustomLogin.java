package pss.common.customLogin;

import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;

public class GuiModuloCustomLogin extends GuiModulo {

	public GuiModuloCustomLogin() throws Exception {
		super();
		SetModuleName("Custom login");
		SetNroIcono(755);
	}

	@Override
	public BizAction createDynamicAction() throws Exception {
		return addAction(1, "Custom Login", null, 755, true, true, true, "Group");
	}

	@Override
	public void createActionMap() throws Exception {
		this.addAction(10, "Custom Login", null, 755, true, false, true, "Group");
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)
			return new JActWins(this.getCustomList());
		return null;
	}

	private JWins getCustomList() throws Exception {
		GuiCustomLogins records = new GuiCustomLogins();
		return records;
	}

}
