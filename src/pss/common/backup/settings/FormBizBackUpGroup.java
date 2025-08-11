package  pss.common.backup.settings;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormBizBackUpGroup extends JBaseForm {

  public FormBizBackUpGroup() throws Exception {
  }

  public GuiBackUpGroup getWin() { return (GuiBackUpGroup) getBaseWin(); }

  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "company", CHAR, REQ, "company" );
    AddItemEdit( "grupo", CHAR, REQ, "grupo" );
    this.AddItemTabPanel().AddItemTab(10);

  } 
  
}
