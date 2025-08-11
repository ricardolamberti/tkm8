package  pss.common.customMenu;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormMenu extends JBaseForm {

	public FormMenu() throws Exception {
	}
	public GuiMenu GetWin() {
		return (GuiMenu)getBaseWin();
	}
	public void InicializarPanel(JWin zWin) throws Exception {

		AddItemEdit("id", CHAR, REQ, "id_menu");
		AddItemEdit("descripcion", CHAR, REQ, "description");
		AddItemTabPanel().AddItemTab(10);
	}
}
