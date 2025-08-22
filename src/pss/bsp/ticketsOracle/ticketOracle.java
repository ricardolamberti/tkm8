package  pss.bsp.ticketsOracle;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class ticketOracle extends JBaseForm {


private static final long serialVersionUID = 1245253394589L;

  

  /**
   * Constructor de la Clase
   */
  public ticketOracle() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiTicketOracle getWin() { return (GuiTicketOracle) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
   
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, REQ, "company" ).setHide(true);
    AddItemEdit( null, CHAR, REQ, "owner" ).setHide(true);;
    AddItemEdit( "Descripción", CHAR, REQ, "descripcion" ).setSizeColumns(8);
    AddItemEdit( "Estado", CHAR, REQ, "estado" ).setSizeColumns(4);
    AddItemDateTime("Fecha desde", DATE, REQ, "fecha_desde" ).setSizeColumns(6);
    AddItemDateTime("Fecha hasta", DATE, REQ, "fecha_hasta" ).setSizeColumns(6);
    AddItemTabPanel().AddItemTab( 20);
  
  }

  

}  //  @jve:decl-index=0:visual-constraint="10,10" 
