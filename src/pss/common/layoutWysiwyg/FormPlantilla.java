package pss.common.layoutWysiwyg;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;

public class FormPlantilla  extends JBaseForm {


	private static final long serialVersionUID = 1218223408500L;



	/**
	   * Constructor de la Clase
	   */
	  public FormPlantilla() throws Exception {
	     }

	  public GuiPlantilla GetWin() { return (GuiPlantilla) getBaseWin(); }

	  @Override
	  public boolean isFullSize() throws Exception {
	  	return true;
	  }

	  /**
	   * Linkeo los campos con la variables del form
	   */
	  public void InicializarPanel( JWin zWin ) throws Exception {
			AddItemEdit(null, CHAR, OPT, "pais").setHide(true);
			AddItemEdit(null, CHAR, REQ, "company").setHide(true);
			AddItemEdit( null, CHAR, OPT, "id" ).setHide(true);
			AddItemEdit( null, CHAR, OPT, "id_tipo" ).setHide(true);
			AddItemEdit( "Descripción", CHAR, REQ, "descripcion" ).SetReadOnly(true);
			AddItemEdit( "Margen Izquierdo", FLOAT, OPT, "margen_izq" ).SetReadOnly(true);
			AddItemEdit( "Margen Derecho", FLOAT, OPT, "margen_der" ).SetReadOnly(true);
	    AddItemCombo( "Tipo Página", CHAR, OPT, "tipo_pagina", JWins.createVirtualWinsFromMap(BizPlantilla.getTiposPagina()) ).SetReadOnly(true);
	    AddItemCombo( "Origen", CHAR, OPT, "origen", JWins.createVirtualWinsFromMap(BizPlantilla.getOrigenDatos())  ).SetReadOnly(true);
	    JFormControl c =AddItemCombo( "Nivel", CHAR, OPT, "nivel", JWins.createVirtualWinsFromMap(BizPlantilla.getNivelDatos(GetWin().GetcDato().getOrigen()))  );
	    c.setRefreshForm(true);
	    c.SetValorDefault(GetWin().GetcDato().getOrigen()); 
	    JFormControl a = AddItemHtml( "Plantilla", CHAR, OPT, "plantilla" , false, GetWin().GetcDato().getDatosPlantilla(),
	    		GetWin().GetcDato().getClaseOrigenDatos(), GetWin().GetcDato().getAnchoEditor(),
	    		GetWin().GetcDato().getMargenIzqEditor(),
	    		GetWin().GetcDato().getMargenImgSupEditor(),
	    		GetWin().GetcDato().getMargenImgLeftEditor(),
	    		GetWin().GetcDato().getMargenImgSizeEditor(),
	    		GetWin().GetcDato().getPathFondo(), 
	    		false);
		  a.setKeepHeight(true);
	    a.setKeepWidth(true);
	  }

		
	}  //  @jve:decl-index=0:visual-constraint="80,14"
