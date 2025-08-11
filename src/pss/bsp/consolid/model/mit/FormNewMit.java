package pss.bsp.consolid.model.mit;

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

public class FormNewMit extends JBaseForm {

	
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public FormNewMit() throws Exception {

	}

	public GuiMit GetWin() {
		return (GuiMit) GetBaseWin();
	}
	 public void InicializarPanel( JWin zWin ) throws Exception {
	    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
	    AddItemEdit( null, UINT, OPT, "id" ).setHide(true);
	    AddItemEdit( null, CHAR, OPT, "original_report" ).setHide(true);
	  	AddItemDateTime("Fecha desde", DATE, REQ, "date_from" ).setHide(true);
		  AddItemDateTime("Fecha hasta", DATE, REQ, "date_to" ).setHide(true);
		  AddItemFile( "Archivo", CHAR, REQ, "original_report_file" );
		
	 }


	// -------------------------------------------------------------------------//
	// Linkeo los campos con la variables del form
	// -------------------------------------------------------------------------//


} // @jve:decl-index=0:visual-constraint="10,10"
