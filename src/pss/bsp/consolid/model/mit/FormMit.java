package pss.bsp.consolid.model.mit;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormMit extends JBaseForm {

	
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public FormMit() throws Exception {

	}

	public GuiMit GetWin() {
		return (GuiMit) GetBaseWin();
	}

	public void InicializarPanel(JWin zWin) throws Exception {
		AddItemEdit(null, CHAR, OPT, "company").setHide(true);
		AddItemEdit(null, UINT, OPT, "id").setHide(true);
		AddItemEdit(null, CHAR, OPT, "original_report").setHide(true);
		AddItemDateTime("Fecha desde", DATE, OPT, "date_from");
		AddItemDateTime("Fecha hasta", DATE, OPT, "date_to");

		JFormTabPanelResponsive panels = AddItemTabPanel();
		panels.AddItemTab(10);
		panels.AddItemTab(20);
	}

	// -------------------------------------------------------------------------//
	// Linkeo los campos con la variables del form
	// -------------------------------------------------------------------------//


} // @jve:decl-index=0:visual-constraint="10,10"
