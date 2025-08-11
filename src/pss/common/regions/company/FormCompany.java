package  pss.common.regions.company;

import pss.core.connectivity.messageManager.common.core.JMMBaseForm;
import pss.core.win.JWin;

public class FormCompany extends JMMBaseForm {

	public FormCompany() throws Exception {
	}

	public GuiCompany GetWin() {
		return (GuiCompany) getBaseWin();
	}

	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		AddItemEdit("company", CHAR, REQ, "company");
		AddItemEdit("description", CHAR, REQ, "description");
		AddItemEdit("negocio", CHAR, REQ, "business");
//		AddItem(jTabbedPane, 10);
		//AddItem(jTabbedPane, 12);
	}
} 
