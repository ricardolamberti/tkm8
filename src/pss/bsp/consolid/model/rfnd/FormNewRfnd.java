package pss.bsp.consolid.model.rfnd;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormNewRfnd extends JBaseForm {

	
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public FormNewRfnd() throws Exception {

	}

	public GuiRfnd GetWin() {
		return (GuiRfnd) GetBaseWin();
	}
	 public void InicializarPanel( JWin zWin ) throws Exception {
	    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
	    AddItemEdit( null, UINT, OPT, "id" ).setHide(true);
	  	AddItemDateTime("Fecha desde", DATE, REQ, "date_from" );
		  AddItemDateTime("Fecha hasta", DATE, REQ, "date_to" );
		
	 }


	// -------------------------------------------------------------------------//
	// Linkeo los campos con la variables del form
	// -------------------------------------------------------------------------//


} // @jve:decl-index=0:visual-constraint="10,10"
