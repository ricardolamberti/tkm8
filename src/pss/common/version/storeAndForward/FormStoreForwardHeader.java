package pss.common.version.storeAndForward;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormStoreForwardHeader extends JBaseForm {

  public FormStoreForwardHeader() throws Exception {
  } 

  public GuiStoreForwardHeader getWin() { return (GuiStoreForwardHeader) getBaseWin(); }


  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "id", UINT, REQ, "id_pack" );
    AddItemEdit( "message", CHAR, REQ, "description" );
 
  } 
} 
