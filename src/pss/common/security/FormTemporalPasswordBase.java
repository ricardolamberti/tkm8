package pss.common.security;

import pss.core.winUI.forms.JBaseForm;
import pss.core.win.JWin;
import pss.core.winUI.responsiveControls.JFormEditResponsive;
import pss.core.winUI.responsiveControls.JFormCDatetimeResponsive;
public class FormTemporalPasswordBase extends JBaseForm {


	private static final long serialVersionUID = 1587312063166L;



	/*
	* Constructor de la Clase
	*/
	public FormTemporalPasswordBase() throws Exception {
	}

	public GuiTemporalPassword getWin() { return (GuiTemporalPassword) getBaseWin(); }

	/*
	* Linkeo los campos con la variables del form
	*/
	public void InicializarPanel( JWin zWin ) throws Exception {
		JFormEditResponsive edit;
		JFormCDatetimeResponsive date;
		edit = AddItemEdit( "Id",  UINT, OPT, "ID" );
		edit.setHide(true);
		edit = AddItemEdit( "PASSWORD",  CHAR, OPT, "PASSWORD" );
		edit.setSizeColumns(6);
		edit = AddItemEdit( "MAIL",  CHAR, OPT, "MAIL" );
		edit.setSizeColumns(6);
		date = AddItemDateTime( "START_DATE",  DATETIME, OPT, "START_DATE" );
		date.setSizeColumns(6);
		date = AddItemDateTime( "END_DATE",  DATETIME, OPT, "END_DATE" );
		date.setSizeColumns(6);
	}



}
