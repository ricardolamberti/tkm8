package  pss.common.customMenu;

import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActWins;

public class GuiModuloCustomMenu extends GuiModulo {

	public GuiModuloCustomMenu() throws Exception {
		super();
		SetModuleName("Customización Menú");
		SetNroIcono(26);
	}

	@Override
	public BizAction createDynamicAction() throws Exception {
		return addAction(1, "Menús", null, 26, true, true, true, "Group");
	}

	@Override
	public void createActionMap() throws Exception {
		this.addAction(10, "Menús", null, 43, true, false, true, "Group");
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()== 1) return new JActQuery(this);
		if (a.getId() == 10) return new JActWins(this.getMenus());
		return null;
	}


	private JWins getMenus() throws Exception {
		GuiMenus records = new GuiMenus();
		return records;
	}

}
