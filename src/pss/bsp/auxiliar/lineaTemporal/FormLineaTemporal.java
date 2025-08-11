package  pss.bsp.auxiliar.lineaTemporal;

import java.awt.Dimension;
import java.awt.Rectangle;

import pss.common.security.BizUsuario;
import pss.core.winUI.forms.JBaseForm;
import pss.core.ui.components.*;
import pss.core.win.JWin;
import pss.core.ui.components.JPssLabel;
import pss.core.ui.components.JPssCalendarEdit;

public class FormLineaTemporal extends JBaseForm {


private static final long serialVersionUID = 1245258187718L;

/**
   * Constructor de la Clase
   */
  public FormLineaTemporal() throws Exception {
   }

  public GuiLineaTemporal getWin() { return (GuiLineaTemporal) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
 /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "Company", CHAR, REQ, "company" ).SetValorDefault(BizUsuario.getUsr().getCompany()).setHide(true);
    AddItemEdit( "fecha", DATE, REQ, "fecha_serie" );
    // agregar todos los camos AddItemEdit para los edit, AddItemCombo para los combos, AddItemCheck para los checkbos, y asi
  }

	 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
