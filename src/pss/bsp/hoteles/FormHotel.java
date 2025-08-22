package  pss.bsp.hoteles;

import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

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
