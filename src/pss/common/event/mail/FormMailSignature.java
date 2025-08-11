package pss.common.event.mail;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormMailSignature extends JBaseForm {

  public FormMailSignature() throws Exception {
  }

  public GuiMailCasilla GetWin() { return (GuiMailCasilla) getBaseWin(); }

  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "COMPANY", CHAR, OPT, "company" );
    AddItemEdit( "USERNAME", CHAR, OPT, "username" );
    AddItemEdit( "desc", CHAR, REQ, "user_desc" ).SetReadOnly(true);
    AddItemHtml( "mensaje", CHAR, REQ, "signature" );
   
  }
  

}
