package pss.common.version;

import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;

public class GuiModuloVersion extends GuiModulo {

	public GuiModuloVersion() throws Exception {
		super();
		SetModuleName("Version");
		SetNroIcono(1111);
	}

	@Override
	public BizAction createDynamicAction() throws Exception {
		return this.addAction(1, "Version", null, 1111, true, true, true, "Group");
	}

	@Override
	public void createActionMap() throws Exception {
//		addAction(12, "Paises", null, 1, true, false, true, "Group");
		this.loadDynamicActions(null);
	} 

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
//		if (a.getId() == 10)		return new JActWins(new GuiCompanies());
		return null;
	}


}
