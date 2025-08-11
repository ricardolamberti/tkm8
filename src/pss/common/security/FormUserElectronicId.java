package  pss.common.security;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormUserElectronicId extends JBaseForm {

	public FormUserElectronicId() throws Exception {
	}

	public GuiUserElectronicId GetWin() {
		return (GuiUserElectronicId)getBaseWin();
	}


	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		SetExitOnOk(true);
		AddItemCombo("User", CHAR, REQ, "usuario", new GuiUsuarios());
		AddItemEdit("electronic_id", CHAR, REQ, "electronic_id");
	}
}
