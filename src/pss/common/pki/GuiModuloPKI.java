package  pss.common.pki;

import pss.common.pki.signs.GuiSigns;
import pss.common.pki.users.GuiUsuarioFirmas;
import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;


public class GuiModuloPKI extends GuiModulo {

	public GuiModuloPKI() throws Exception {
		super();
		SetModuleName("PKI");
		SetNroIcono(10031);
	}

	@Override
	public BizAction createDynamicAction() throws Exception {
		return this.addAction(1, "Lex", null, 10031, true, true, true, "Group");
	}

	@Override
	public void createActionMap() throws Exception {
		this.addAction(70, "Firmas Sistema", null, 10031, true, true, true, "Group");
		this.addAction(80, "Firmas Usuarios", null, 10031, true, true, true, "Group");

		this.loadDynamicActions(null);
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 70) return new JActWins(new GuiSigns());
		if (a.getId() == 80) return new JActWins(new GuiUsuarioFirmas());
		return null;
	}

	
}
