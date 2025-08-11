package  pss.bsp.bspBusiness;

import pss.core.ui.components.JPssImage;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormImageResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormBSPCompany extends JBaseForm {
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public FormBSPCompany() throws Exception {
	}

	public GuiBSPCompany GetWin() {
		return (GuiBSPCompany) GetBaseWin();
	}


	// -------------------------------------------------------------------------//
	// Linkeo los campos con la variables del form
	// -------------------------------------------------------------------------//
	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		AddItemEdit("Sigle", CHAR, OPT, "company");
		AddItemEdit("Description", CHAR, REQ, "description");
		AddItemEdit("Negocio", CHAR, OPT, "business").SetValorDefault(GetWin().GetcDato().getBusiness());
		JFormImageResponsive m = AddItemImage("Logo", "logo_img");
		m.setSource(JPssImage.SOURCE_PSSDATA);
		JFormTabPanelResponsive tabs= AddItemTabPanel();
		tabs.AddItemList(10);
		tabs.AddItemList(20);
		tabs.AddItemList(60);
	}

} // @jve:decl-index=0:visual-constraint="10,10"
