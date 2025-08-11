package pss.common.customLogin;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormCustomListComponent extends JBaseForm {


	public GuiCustomLoginComponent GetWin() {
		return (GuiCustomLoginComponent) getBaseWin();
	}


	/**
	 * Linkeo los campos con la variables del form
	 */
	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		AddItemEdit(null, UINT, OPT, "id").setHide(true);
		AddItemEdit(null, UINT, OPT, "secuencia").setHide(true);
		AddItemEdit("Orden", UINT, OPT, "orden");
		AddItemEdit("Texto", CHAR, REQ, "texto");
		

	}
}
