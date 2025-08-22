package pss.bsp.consolid.model.repoDK;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormNewRepoDK extends JBaseForm {

	
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public FormNewRepoDK() throws Exception {

	}

	public GuiRepoDK GetWin() {
		return (GuiRepoDK) GetBaseWin();
	}
	 public void InicializarPanel( JWin zWin ) throws Exception {
	    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
	    AddItemEdit( null, UINT, OPT, "id" ).setHide(true);
	  	AddItemDateTime("Fecha desde", DATE, REQ, "date_from" );
		  AddItemDateTime("Fecha hasta", DATE, REQ, "date_to" );
		  JFormTabPanelResponsive tabs= AddItemTabPanel();
		  tabs.AddItemTab(10);
	 }


	// -------------------------------------------------------------------------//
	// Linkeo los campos con la variables del form
	// -------------------------------------------------------------------------//


} // @jve:decl-index=0:visual-constraint="10,10"
