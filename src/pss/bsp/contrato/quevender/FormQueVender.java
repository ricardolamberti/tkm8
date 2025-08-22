package pss.bsp.contrato.quevender;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormQueVender extends JBaseForm {


private static final long serialVersionUID = 1446860154249L;

  /**
   * Constructor de la Clase
   */
  public FormQueVender() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiQueVender getWin() { return (GuiQueVender) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
  	AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "id_contrato" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "id_detalle" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "id" ).setHide(true);
    AddItemEdit("Aerolineas", CHAR, OPT, "obj_detalle.info_aerolineas").setReadOnly(true);
    AddItemEdit("período", CHAR, OPT, "obj_detalle.periodo_detalle").setReadOnly(true);
    AddItemList(30).setResponsiveClass("col-lg-6 col-md-6 col-sm-12");
    AddItemImage("", "imagen1").setResponsiveClass("col-lg-6 col-md-6 col-sm-12").setHeight(500);


  } 
} 
