package pss.common.customForm;

import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActWins;

public class GuiModuloCustomForm extends GuiModulo {

	public GuiModuloCustomForm() throws Exception {
		super();
		SetModuleName("Customización Forms");
		SetNroIcono(26);
	}

	@Override
	public BizAction createDynamicAction() throws Exception {
		return addAction(1, "Forms", null, 26, true, true, true, "Group");
	}

	@Override
	public void createActionMap() throws Exception {
		this.addAction(10, "Forms", null, 43, true, false, true, "Group");
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()== 1) return new JActQuery(this);
		if (a.getId() == 10) return new JActWins(this.getForms());
		return null;
	}


	private JWins getForms() throws Exception {
		GuiCustomForms records = new GuiCustomForms();
		return records;
	}

}
