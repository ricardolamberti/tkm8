package pss.bsp.contrato.evaluate;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormEvaluate extends JBaseForm {


private static final long serialVersionUID = 1477827540739L;

  /**
   * Propiedades de la Clase
   */



  /**
   * Constructor de la Clase
   */
  public FormEvaluate() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiEvaluate getWin() { return (GuiEvaluate) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
     }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit(null, CHAR, REQ,"id").setHide(true);
    
    JFormPanelResponsive panel = AddItemRow();
    panel.AddItemRow();
    panel.setLabelLeft();
    panel.AddItemEdit("Numero boleto", CHAR, REQ,"numero_boleto").setSizeColumns(9);
  
    panel.AddItemButton("Evaluar", 20, true).setSizeColumns(3);
    panel.AddItemEdit("Evaluación", CHAR, OPT,"diagnostico").setReadOnly(true);
    panel.AddItemList(10);
  }

}  //  @jve:decl-index=0:visual-constraint="14,3"
