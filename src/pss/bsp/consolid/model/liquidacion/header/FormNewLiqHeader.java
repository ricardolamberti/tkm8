package pss.bsp.consolid.model.liquidacion.header;

import pss.bsp.ticketsOracle.GuiTicketOracle;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormNewLiqHeader extends JBaseForm {


private static final long serialVersionUID = 1241253394589L;


  /**
   * Constructor de la Clase
   */
  public FormNewLiqHeader() throws Exception {
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
    AddItemEdit( null, CHAR, OPT, "liquidacion_id" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "estado" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "descripcion" ).setHide(true);
    AddItemDateTime( "Fecha desde", DATE, REQ, "fecha_desde" ).setSizeColumns(6);
    AddItemDateTime( "Fecha hasta", DATE, REQ, "fecha_hasta" ).setSizeColumns(6);
    AddItemCombo( "Grupo clientes", LONG, OPT, "grupo_cliente" ).setSizeColumns(4);
    
  } 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
