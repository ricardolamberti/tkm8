package  pss.bsp.hoteles.detalle;

import java.awt.Dimension;
import java.awt.Rectangle;

import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.ui.components.JPssLabelWinLov;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;
import pss.tourism.carrier.GuiCarriers;

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