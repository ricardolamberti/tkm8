package pss.bsp.consolid.model.rfnd;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormRfnd extends JBaseForm {

	
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public FormRfnd() throws Exception {

	}

	public GuiRfnd GetWin() {
		return (GuiRfnd) GetBaseWin();
	}

	public void InicializarPanel(JWin zWin) throws Exception {
		AddItemEdit(null, CHAR, OPT, "company").setHide(true);
		AddItemEdit(null, UINT, OPT, "id").setHide(true);
		AddItemDateTime("Fecha desde", DATE, OPT, "date_from");
		AddItemDateTime("Fecha hasta", DATE, OPT, "date_to");

		JFormTabPanelResponsive panels = AddItemTabPanel();
		panels.AddItemTab(10);
	}

	// -------------------------------------------------------------------------//
	// Linkeo los campos con la variables del form
	// -------------------------------------------------------------------------//


} // @jve:decl-index=0:visual-constraint="10,10"
