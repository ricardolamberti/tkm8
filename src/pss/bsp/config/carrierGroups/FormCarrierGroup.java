package  pss.bsp.config.carrierGroups;

import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormCarrierGroup extends JBaseForm {


private static final long serialVersionUID = 1245253307166L;

/**
   * Constructor de la Clase
   */
  public FormCarrierGroup() throws Exception {
  }

  public GuiCarrierGroup getWin() { return (GuiCarrierGroup) getBaseWin(); }

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, OPT, "company" ).SetValorDefault(BizUsuario.getUsr().getCompany()).setHide(true);
    AddItemEdit( "Grupo", CHAR, REQ, "descripcion" );
    AddItemEdit( null, CHAR, OPT, "id_group" ).setHide(true);
    AddItemTabPanel().AddItemList(10);
  }

}  //  @jve:decl-index=0:visual-constraint="24,19"
