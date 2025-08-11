package  pss.bsp.market.detalle;

import java.awt.Rectangle;

import pss.core.ui.components.JPssEdit;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormMarketDetail extends JBaseForm {


private static final long serialVersionUID = 1245253307166L;

/**
   * Constructor de la Clase
   */
  public FormMarketDetail() throws Exception {
    }

  public GuiMarketDetail getWin() { return (GuiMarketDetail) getBaseWin(); }


  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "id" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "id_market" ).setHide(true);
    AddItemArea( "Ruta", CHAR, REQ, "ruta").setKeepWidth(true);;
    AddItemEdit( "Prioridad", CHAR, REQ, "prioridad" );

  }

}  //  @jve:decl-index=0:visual-constraint="10,10" 
