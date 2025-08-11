package pss.common.mail.message;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormMessage extends JBaseForm {

  public FormMessage() throws Exception {
  }

  public GuiMessage getWin() { return (GuiMessage) getBaseWin(); }


  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "id_message", UINT, REQ, "id_message" );
    AddItemEdit( "sender", CHAR, REQ, "sender" );
    AddItemDateTime( "date_creation", DATE, REQ, "date_creation" );
    AddItemEdit( "title", CHAR, REQ, "title" );
    AddItemEdit( "message", CHAR, REQ, "message" );

  } 
} 
