package  pss.bsp.familia;

import javax.swing.JTabbedPane;

import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormFamilia extends JBaseForm {


private static final long serialVersionUID = 1245253307166L;


private JTabbedPane jTabbedPane = null;
/**
   * Constructor de la Clase
   */
  public FormFamilia() throws Exception {
  }

  public GuiFamilia getWin() { return (GuiFamilia) getBaseWin(); }

  
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "Fam.Tarif.", CHAR, REQ, "descripcion" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "company" ).SetValorDefault(BizUsuario.getUsr().getCompany()).setHide(true);
    AddItemEdit( null, CHAR, OPT, "id" ).setHide(true);
    AddItemTabPanel().AddItemList(10);
  }


}  //  @jve:decl-index=0:visual-constraint="24,19"
