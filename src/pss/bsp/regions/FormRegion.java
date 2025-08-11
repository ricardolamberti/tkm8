package  pss.bsp.regions;

import java.awt.Dimension;
import java.awt.Rectangle;

import pss.common.security.BizUsuario;
import pss.core.winUI.forms.JBaseForm;
import pss.core.ui.components.*;
import pss.core.win.JWin;
import javax.swing.JTabbedPane;

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
