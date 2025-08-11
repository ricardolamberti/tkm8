package  pss.common.customList.config.customlist;

import javax.swing.JComboBox;

import pss.common.security.GuiUsuarios;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormCustomListFav extends JBaseForm {


private static final long serialVersionUID = 1226426806993L;

  JPssEdit winOwner = new JPssEdit  ();
//JPssEdit recordOwner = new JPssEdit  ();
JPssEdit company = new JPssEdit  ();
JPssEdit listId = new JPssEdit  ();  //  @jve:decl-index=0:visual-constraint="530,11"
private JPssLabel lrecord_set1 = null;

private JComboBox jModelo = null;

/**
   * Constructor de la Clase
   */
  public FormCustomListFav() throws Exception {
  }

  public GuiCustomListFav getWin() { return (GuiCustomListFav) getBaseWin(); }

 
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, REQ, "company" ).setHide(true);
    AddItemEdit( null, UINT, OPT, "list_id" ).setHide(true);
    AddItemCombo( "Usuario", CHAR, REQ, "usuario" , new GuiUsuarios());
 
  }
  

}  //  @jve:decl-index=0:visual-constraint="10,10" 
