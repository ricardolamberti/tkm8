package  pss.bsp.regions;

import javax.swing.JTabbedPane;

import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormRegion extends JBaseForm {


private static final long serialVersionUID = 1245253307166L;

  /**
   * Propiedades de la Clase
   */

private JTabbedPane jTabbedPane = null;
/**
   * Constructor de la Clase
   */
  public FormRegion() throws Exception {
  }

  public GuiRegion getWin() { return (GuiRegion) getBaseWin(); }

 
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "Descripión", CHAR, REQ, "descripcion" );
    AddItemEdit( null, CHAR, OPT, "company" ).SetValorDefault(BizUsuario.getUsr().getCompany()).setHide(true);
    AddItemEdit( null, CHAR, OPT, "id" ).setHide(true);
    
    AddItemTabPanel().AddItemList(10);
  }


}  //  @jve:decl-index=0:visual-constraint="24,19"
