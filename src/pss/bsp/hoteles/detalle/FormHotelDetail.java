package  pss.bsp.hoteles.detalle;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormHotelDetail extends JBaseForm {


private static final long serialVersionUID = 12452533017166L;


  public GuiHotelDetail getWin() { return (GuiHotelDetail) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {

    AddItemEdit( null, CHAR, OPT, "id" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "id_hotel" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
    AddItemEdit( "Descripción", CHAR, REQ, "descripcion" ).setSizeColumns(5);
    AddItemCheck( "Exacto", CHAR, "exacto" ).setSizeColumns(2);

  }

}