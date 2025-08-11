package  pss.bsp.bo;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormInterfazBO extends JBaseForm {


private static final long serialVersionUID = 1245254423061L;




  /**
   * Constructor de la Clase
   */
  public FormInterfazBO() throws Exception {
  }

  public GuiInterfazBO getWin() { return (GuiInterfazBO) getBaseWin(); }

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, REQ, "company" ).setHide(true);
    AddItemEdit( null, CHAR, REQ, "owner" ).setHide(true);
    AddItemEdit( null, UINT, REQ, "idInterfaz" ).setHide(true);
    AddItemEdit( "Descripcion", CHAR, REQ, "descripcion" );
    AddItemEdit( "Estado", CHAR, REQ, "estado" );
    AddItemDateTime( "Fecha desde", DATE, REQ, "fecha_desde" );
    AddItemDateTime( "Fecha hasta", DATE, REQ, "fecha_hasta" );
    AddItemEdit( "tipo interfaz", CHAR, REQ, "tipo_interfaz" );
    AddItemEdit( "Iata", CHAR, REQ, "iata" );
    JFormTabPanelResponsive tab =AddItemTabPanel();
    tab.AddItemList( 20);
    tab.AddItemForm( 10);
    tab.AddItemList( 25);
   }

}  //  @jve:decl-index=0:visual-constraint="8,18"
