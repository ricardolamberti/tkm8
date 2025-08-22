package  pss.bsp.ticketsOracle;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormNewTicketOracle extends JBaseForm {


private static final long serialVersionUID = 1245253394589L;


  /**
   * Constructor de la Clase
   */
  public FormNewTicketOracle() throws Exception {
  }
 
  public GuiTicketOracle getWin() { return (GuiTicketOracle) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "id" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "owner" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "descripcion" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "estado" ).setHide(true);
    AddItemDateTime( "Fecha desde", DATE, REQ, "fecha_desde" );
    AddItemDateTime( "Fecha hasta", DATE, REQ, "fecha_hasta" );

  } 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
