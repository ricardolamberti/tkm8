package  pss.bsp.market;

import javax.swing.JTabbedPane;

import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormMarket extends JBaseForm {


private static final long serialVersionUID = 1245253307166L;


private JTabbedPane jTabbedPane = null;
/**
   * Constructor de la Clase
   */
  public FormMarket() throws Exception {
 }

  public GuiMarket getWin() { return (GuiMarket) getBaseWin(); }

 
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "Marca mercado", CHAR, REQ, "descripcion" );
    AddItemEdit( null, CHAR, OPT, "company" ).SetValorDefault(BizUsuario.getUsr().getCompany()).setHide(true);
    AddItemEdit( null, CHAR, OPT, "id" ).setHide(true);
    AddItemTabPanel().AddItemList(10);
   }


}  //  @jve:decl-index=0:visual-constraint="24,19"
