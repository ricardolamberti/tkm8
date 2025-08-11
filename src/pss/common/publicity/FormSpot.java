package  pss.common.publicity;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormSpot extends JBaseForm {

	public FormSpot() throws Exception {
	}

	public GuiSpot GetWin() {
		return (GuiSpot) getBaseWin();
	}

	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		AddItemEdit("id", "UINT", REQ, "id");
		AddItemEdit("page", "CHAR", REQ, "page");
		AddItemEdit("action", "CHAR", REQ, "action");
		AddItemEdit("step", "UINT", REQ, "step");
		AddItemEdit("localization", "UINT", REQ, "localization");
		AddItemEdit("image", "CHAR", REQ, "image");
		AddItemEdit("link", "CHAR", REQ, "link");
		AddItemEdit("x", "UINT", REQ, "x");
		AddItemEdit("y", "UINT", REQ, "y");
		AddItemEdit("width", "UINT", REQ, "width");
		AddItemEdit("height", "UINT", REQ, "height");
		AddItemEdit("typePos", "CHAR", REQ, "typePos");
		AddItemEdit("idhtml", "CHAR", OPT, "idhtml");
		AddItemEdit("type", "CHAR", OPT, "type");

	}
}
