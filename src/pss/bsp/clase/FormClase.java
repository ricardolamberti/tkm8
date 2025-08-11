package  pss.bsp.clase;

import javax.swing.JTabbedPane;

import pss.common.security.BizUsuario;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormClase extends JBaseForm {


private static final long serialVersionUID = 1245253307166L;

  /**
   * Propiedades de la Clase
   */
JPssLabel lpais = new JPssLabel();
JPssEdit pais = new JPssEdit  ();
JPssEdit company = new JPssEdit  ();
JPssEdit id = new JPssEdit  ();

private JTabbedPane jTabbedPane = null;
/**
   * Constructor de la Clase
   */
  public FormClase() throws Exception {
  }

  public GuiClase getWin() { return (GuiClase) getBaseWin(); }

 
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "Clase", CHAR, REQ, "descripcion" );
    AddItemEdit( null, CHAR, OPT, "company" ).SetValorDefault(BizUsuario.getUsr().getCompany()).setHide(true);
    AddItemEdit( null, CHAR, OPT, "id" ).setHide(true);
 
    AddItemTabPanel().AddItemList(10);
  }
}  //  @jve:decl-index=0:visual-constraint="24,19"
