package pss.common.layoutWysiwyg;

import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormColumnResponsive;
import pss.core.winUI.responsiveControls.JFormControlResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormPlantillaGeneral extends JBaseForm {


private static final long serialVersionUID = 1218223408500L;



/**
   * Constructor de la Clase
   */
  public FormPlantillaGeneral() throws Exception {
  }

  public GuiPlantilla GetWin() { return (GuiPlantilla) getBaseWin(); }

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
		AddItemEdit(null, CHAR, OPT, "pais").SetValorDefault(BizUsuario.getUsr().getBirthCountryId()).setHide(true);
		AddItemEdit(null, CHAR, OPT, "company").SetValorDefault(BizUsuario.getUsr().getCompany()).setHide(true);

		AddItemEdit( null, LONG, OPT, "id" ).setHide(true);
		AddItemEdit( null, CHAR, OPT, "id_tipo" ).setHide(true);
		AddItemEdit( "Descripcion", CHAR, REQ, "descripcion" ).setSizeColumns(12);
    AddItemCombo( "Tipo Página", CHAR, OPT, "tipo_pagina", JWins.createVirtualWinsFromMap(BizPlantilla.getTiposPagina()) ).setSizeColumns(2);
    AddItemCombo( "Origen", CHAR, OPT, "origen", JWins.createVirtualWinsFromMap(BizPlantilla.getOrigenDatos())  ).setSizeColumns(2);
    AddItemCombo( "Borde", CHAR, OPT, "borde", JWins.createVirtualWinsFromMap(BizPlantilla.getBordes()) ).setSizeColumns(2);
    AddItemEdit( "Sep.Borde", CHAR, OPT, "separacion" ).setSizeColumns(2);
    AddItemCombo( "Fondo", CHAR, OPT, "fondo" , new JControlCombo() {
    	public JWins getRecords(Object source, boolean one) throws Exception {
    		return getFondos(source, one);
    	}
    }).setSizeColumns(2);
    AddItemCheck( "Imprimier fondo", CHAR, OPT, "imprime_fondo", "S", "N" ).setSizeColumns(2);
     AddItemEdit( "Margen izq.", LONG, OPT, "margen_izq" ).setSizeColumns(3);
    AddItemEdit( "Margen der.", LONG, OPT, "margen_der" ).setSizeColumns(3);
    AddItemEdit( "Margen arriba", LONG, OPT, "margen_top" ).setSizeColumns(3);
    AddItemEdit( "Margen abajo", LONG, OPT, "margen_bottom" ).setSizeColumns(3);
     JFormPanelResponsive row=AddItemRow();
    JFormColumnResponsive col1 = row.AddItemColumn(3);
    JFormColumnResponsive col2 = row.AddItemColumn(9);
    JFormTabPanelResponsive tabs=col1.AddItemTabPanel();
    tabs.AddItemTab(401);
    JFormControlResponsive a = col2.AddItemHtml( "Plantilla", CHAR, OPT, "plantilla", false,GetWin().GetcDato().getAnchoEditor(),GetWin().GetcDato().getMargenIzqEditor());
    a.SetReadOnly(true);
  }

  private JWins getFondos(Object source, boolean one) throws Exception {
  	String company = this.valueControl("company");
  	return BizPlantilla.getFondos(company);
  }

}  //  @jve:decl-index=0:visual-constraint="80,14"
