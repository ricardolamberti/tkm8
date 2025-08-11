package pss.bsp.consolid.model.cuf;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormNewCuf extends JBaseForm {

	
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public FormNewCuf() throws Exception {

	}

	public GuiCuf GetWin() {
		return (GuiCuf) GetBaseWin();
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
