package  pss.bsp.config.airportGroups;

import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormEditResponsive;

public class FormAirportGroup extends JBaseForm {


private static final long serialVersionUID = 1245253307166L;

  
/**
   * Constructor de la Clase
   */
  public FormAirportGroup() throws Exception {
   }

  public GuiAirportGroup getWin() { return (GuiAirportGroup) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */

  /**
   * 
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    JFormEditResponsive edit =AddItemEdit(null, CHAR, OPT, "company" );
    edit.SetValorDefault(BizUsuario.getUsr().getCompany());
    edit.setHide(true);
    AddItemEdit( null, CHAR, OPT, "id_group" ).setHide(true);
    AddItemEdit( "Descripción", CHAR, REQ, "descripcion" );
    AddItemList(10);
  }


}  //  @jve:decl-index=0:visual-constraint="24,19"
