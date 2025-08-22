package  pss.bsp.interfaces.dqb.datos;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormDQB extends JBaseForm {


private static final long serialVersionUID = 1245253394589L;


  /**
   * Constructor de la Clase
   */
  public FormDQB() throws Exception {
   }

  public GuiDQB getWin() { return (GuiDQB) getBaseWin(); }

  
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, REQ, "company" ).setVisible(false);
    AddItemEdit( null, CHAR, REQ, "owner" ).setVisible(false);;
    AddItemEdit( null, CHAR, REQ, "idPDF" ).setVisible(false);;
    AddItemEdit( "Descripción", CHAR, REQ, "descripcion" );
    AddItemEdit( "Estado", CHAR, REQ, "estado" ).setSizeColumns(4);
    AddItemDateTime( "Fecha Desde", DATE, REQ, "fecha_desde" ).setSizeColumns(4);
    AddItemDateTime( "Fecha hasta", DATE, REQ, "fecha_hasta" ).setSizeColumns(4);
    JFormTabPanelResponsive tabs= AddItemTabPanel();
    tabs.AddItemList(20);
    tabs.AddItemForm( 10);
 
  }

}  //  @jve:decl-index=0:visual-constraint="10,10" 
