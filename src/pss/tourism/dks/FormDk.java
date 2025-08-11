package pss.tourism.dks;

import pss.common.regions.divitions.GuiPaises;
import pss.common.regions.divitions.GuiPaisesLista;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssGoogleMap;
import pss.core.ui.components.JPssLabel;
import pss.core.ui.components.JPssLabelFormLov;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;
import java.awt.Rectangle;
import java.awt.Dimension;

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
