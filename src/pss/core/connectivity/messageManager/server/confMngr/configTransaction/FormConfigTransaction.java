package pss.core.connectivity.messageManager.server.confMngr.configTransaction;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormConfigTransaction extends JBaseForm {

  public FormConfigTransaction() throws Exception {
  }

  public GuiConfigTransaction getWin() { return (GuiConfigTransaction) getBaseWin(); }

  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "company", CHAR, REQ, "company" );
    AddItemEdit( "country", CHAR, REQ, "country" );
    AddItemEdit( "store", CHAR, REQ, "store" );
    AddItemEdit( "module_id", CHAR, REQ, "module_id" );
    AddItemEdit( "conf_id", UINT, REQ, "conf_id" );
    AddItemEdit( "creation_datetime", DATE, REQ, "creation_datetime" );
    AddItemEdit( "last_modif_datetime", DATE, REQ, "last_modif_datetime" );
    AddItemEdit( "state", CHAR, REQ, "state" );
    AddItemEdit( "description", CHAR, REQ, "description" );
    AddItemEdit( "transaction_msg", CHAR, REQ, "transaction_msg" );

  } 
} 
