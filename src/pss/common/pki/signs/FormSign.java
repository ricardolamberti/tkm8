package  pss.common.pki.signs;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormSign extends JBaseForm {

  public FormSign() throws Exception {
  }

  public GuiSign getWin() { return (GuiSign) getBaseWin(); }

  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "sign_id", CHAR, REQ, "sign_id" );
    AddItemEdit( "sign_description", CHAR, REQ, "sign_description" );
    AddItemEdit( "sign_file", CHAR, REQ, "sign_file" );
    AddItemEdit( "sign_password", CHAR, REQ, "sign_password" );
    AddItemEdit( "sign_format", CHAR, REQ, "sign_format" );
  } 
  
} 
