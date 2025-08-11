package pss.common.customLogin;


import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormCustomLoginLogo extends JBaseForm {

	public FormCustomLoginLogo() throws Exception {
	}

	public GuiCustomLoginLogo GetWin() {
		return (GuiCustomLoginLogo) getBaseWin();
	}


	// -------------------------------------------------------------------------//
	// Linkeo los campos con la variables del form
	// -------------------------------------------------------------------------//
	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		AddItemEdit(null , CHAR, OPT, "directory").setHide(true);
		AddItemFile("Archivo a subir" , CHAR, OPT, "file");
	}

	
}
