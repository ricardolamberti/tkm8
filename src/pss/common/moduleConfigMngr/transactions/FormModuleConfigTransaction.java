package  pss.common.moduleConfigMngr.transactions;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormModuleConfigTransaction extends JBaseForm {

  public FormModuleConfigTransaction() throws Exception {
  }

  public GuiModuleConfigTransaction getWin() { return (GuiModuleConfigTransaction) getBaseWin(); }


  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "company", CHAR, REQ, "company" );
    AddItemEdit( "country", CHAR, REQ, "country" );
    AddItemEdit( "node", CHAR, REQ, "node" );
    AddItemEdit( "module_id", CHAR, REQ, "module_id" );
    AddItemEdit( "config_type", CHAR, REQ, "config_type" );
    AddItemEdit( "conf_id", UINT, REQ, "conf_id" );
    AddItemEdit( "creation_datetime", DATE, REQ, "creation_datetime" );
    AddItemEdit( "description", CHAR, REQ, "description" );
    AddItemEdit( "transaction_msg", CHAR, REQ, "transaction_msg" );
  } 
} 
