package  pss.common.regions.company;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormCompanyLogo extends JBaseForm {
	
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public FormCompanyLogo() throws Exception {
	}

	public GuiCompany GetWin() {
		return (GuiCompany) getBaseWin();
	}

	// -------------------------------------------------------------------------//
	// Linkeo los campos con la variables del form
	// -------------------------------------------------------------------------//
	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		AddItemEdit("logo", CHAR, OPT, "logo");
		AddItemEdit("link", CHAR, OPT, "link");
	}
	
} 
