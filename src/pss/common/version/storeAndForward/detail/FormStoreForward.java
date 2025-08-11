package pss.common.version.storeAndForward.detail;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormStoreForward extends JBaseForm {

  public FormStoreForward() throws Exception {
  }

  public GuiStoreForward getWin() { return (GuiStoreForward) getBaseWin(); }
 

  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "id", UINT, REQ, "id" );
    AddItemEdit( "message", CHAR, REQ, "message" );
 
  } 
} 
