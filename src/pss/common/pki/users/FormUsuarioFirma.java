package  pss.common.pki.users;

import pss.common.pki.signs.GuiSign;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormUsuarioFirma  extends JBaseForm {

  public FormUsuarioFirma() throws Exception {
  }

  public GuiSign getWin() { return (GuiSign) getBaseWin(); }

  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "usuario", CHAR, OPT, "usuario" ).SetReadOnly(true);
    AddItemEdit( "id", ULONG, OPT, "id" ).SetReadOnly(true);
    AddItemEdit( "sign_description", CHAR, REQ, "filename" );
  } 
  
} 
