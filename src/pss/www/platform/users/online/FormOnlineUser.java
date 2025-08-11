package pss.www.platform.users.online;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;


public class FormOnlineUser extends JBaseForm {


  public FormOnlineUser() throws Exception {
  }

  public GuiOnlineUser GetWin() { return (GuiOnlineUser) getBaseWin(); }

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
	  JFormPanelResponsive col = this.AddItemColumn();
	  col.AddItemEdit("Usuario", CHAR, REQ, "user");
	  col.AddItemTabPanel().AddItemTab(10);
  }

}
