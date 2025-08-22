package pss.tourism.dks;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormDk extends JBaseForm {

	
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public FormDk() throws Exception {

	}

	public GuiDk GetWin() {
		return (GuiDk) GetBaseWin();
	}
	 public void InicializarPanel( JWin zWin ) throws Exception {
	    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
	    AddItemEdit( null, UINT, OPT, "id" ).setHide(true);
	    AddItemEdit( "GDS", CHAR, OPT, "gds" );
	    AddItemEdit( "Office id", CHAR, OPT, "office_id" );
	    AddItemEdit( "Ag.Emisión", CHAR, OPT, "ag_emision" );
	    AddItemEdit( "DK", CHAR, OPT, "dk_sinonimo" );
	    AddItemEdit( "Convertir a", CHAR, REQ, "dk" );

	 }


	// -------------------------------------------------------------------------//
	// Linkeo los campos con la variables del form
	// -------------------------------------------------------------------------//


} // @jve:decl-index=0:visual-constraint="10,10"
