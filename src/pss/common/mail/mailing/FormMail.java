package pss.common.mail.mailing;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormMail extends JBaseForm {

  public FormMail() throws Exception {
  }

  public GuiMail getWin() { return (GuiMail) getBaseWin(); }

  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "idMsg", UINT, OPT, "id_message" ).SetReadOnly(true);
    AddItemDateTime( "Fecha", DATE, OPT, "date_readed" );
    AddItemEdit( "Sender", CHAR, REQ, "descr_sender" );
    AddItemEdit( "titulo", CHAR, OPT, "msg_title" );
    AddItemArea( "mensaje", CHAR, OPT, "msg_message" ).setHeight(400);
  }
  
} 
