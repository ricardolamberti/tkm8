package  pss.common.security;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormLoginTrace extends JBaseForm {
	boolean bOnlyOneDatabase = false;

	public FormLoginTrace() {
	}


	public GuiLoginTrace GetWin() throws Exception {
		return (GuiLoginTrace) getBaseWin();

	}

	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		setModal(true);
		SetExitOnOk(true);
		this.asignMessage();

		AddItemEdit("Login", CHAR, REQ, "login");
		AddItemPassword("Password", CHAR, OPT, "password");
		bOnlyOneDatabase = true;
	}

	private void asignMessage() throws Exception {
		if (BizUsuario.getUsr() == null)
			return;
		if (!BizUsuario.getUsr().wasRead())
			return;
//		this.SetConfirmarAplicar(true);
//		this.setConfirmationMessage(true);
		this.SetTextoConfirmacion("Se cerrarán todas las ventanas abiertas ¿Desea continuar?");
	}

}
