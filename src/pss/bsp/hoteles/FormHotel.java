package  pss.bsp.hoteles;

import java.awt.Dimension;
import java.awt.Rectangle;

import pss.common.security.BizUsuario;
import pss.core.winUI.forms.JBaseForm;
import pss.core.ui.components.*;
import pss.core.win.JWin;
import javax.swing.JTabbedPane;

public class FormHotel extends JBaseForm {


private static final long serialVersionUID = 1245253307166L;

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true).SetValorDefault(BizUsuario.getUsr().getCompany());
    AddItemEdit( null, CHAR, OPT, "id" ).setHide(true);
    AddItemEdit( "Descripción", CHAR, REQ, "descripcion" ).setSizeColumns(6);
    AddItemEdit( "Prioridad", CHAR, OPT, "orden" ).setSizeColumns(1).SetValorDefault(5);
    AddItemTabPanel().AddItemTab(10);
  }


}  //  @jve:decl-index=0:visual-constraint="24,19"
