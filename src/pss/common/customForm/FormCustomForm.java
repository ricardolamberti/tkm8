package pss.common.customForm;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormCustomForm extends JBaseForm {

	public FormCustomForm() throws Exception {
	}

	public GuiCustomForm GetWin() {
		return (GuiCustomForm) getBaseWin();
	}
	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		AddItemEdit("company", CHAR, REQ, "company").hide();
		AddItemEdit("secuencia", INT, OPT, "secuencia").hide();
		AddItemEdit("Form", DATE, REQ, "formulario");
		AddItemEdit("ClaseWin", DATE, REQ, "clase_win");
		AddItemTabPanel().AddItemTab(10);
//		AddItem(jTabbedPane, 20);
	}
}
