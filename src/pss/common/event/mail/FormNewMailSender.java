package pss.common.event.mail;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormNewMailSender extends JBaseForm {


  public FormNewMailSender() throws Exception {
  }

  public GuiMailCasilla GetWin() { return (GuiMailCasilla) getBaseWin(); }


  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "COMPANY", CHAR, OPT, "company" );
    AddItemEdit( "desc", CHAR, REQ, "Mail_subject" );
    AddItemEdit( "Mail_To", CHAR, REQ, "Mail_To" );
    AddItemEdit( "mensaje", CHAR, REQ, "Mail_body" );
   
  }


}
