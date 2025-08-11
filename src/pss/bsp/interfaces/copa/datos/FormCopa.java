package  pss.bsp.interfaces.copa.datos;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormCopa extends JBaseForm {


private static final long serialVersionUID = 1245253394589L;



  /**
   * Constructor de la Clase
   */
  public FormCopa() throws Exception {
  }

  public GuiCopa getWin() { return (GuiCopa) getBaseWin(); }

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, REQ, "company" ).setVisible(false);
    AddItemEdit( null, CHAR, REQ, "owner" ).setVisible(false);;
    AddItemEdit( null, CHAR, REQ, "idPDF" );
    AddItemEdit( "Descripcion", CHAR, REQ, "descripcion" );
    AddItemEdit( "Estado", CHAR, REQ, "estado" );
    AddItemDateTime(  "Fecha desde", UINT, REQ, "fecha_desde" );
    AddItemDateTime( "Fecha hasta", UINT, REQ, "fecha_hasta" );
    
    JFormTabPanelResponsive tabs = AddItemTabPanel();
    tabs.AddItemList(20);
    tabs.AddItemForm(10);
    
 
 
  }

}  //  @jve:decl-index=0:visual-constraint="10,10" 
