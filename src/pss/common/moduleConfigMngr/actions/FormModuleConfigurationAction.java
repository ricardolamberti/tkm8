package  pss.common.moduleConfigMngr.actions;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormModuleConfigurationAction extends JBaseForm {

  public FormModuleConfigurationAction() throws Exception {
  }

  public GuiModuleConfigurationAction getWin() { return (GuiModuleConfigurationAction) getBaseWin(); }


  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "company", CHAR, REQ, "company" );
    AddItemEdit( "country", CHAR, REQ, "country" );
    AddItemEdit( "node", CHAR, REQ, "node" );
    AddItemEdit( "module_id", CHAR, REQ, "module_id" );
    AddItemEdit( "action_sequence", UINT, REQ, "action_sequence" );
    AddItemEdit( "action_id", CHAR, REQ, "action_id" );
    AddItemEdit( "action_data", CHAR, REQ, "action_data" );
    AddItemDateTime( "action_creation_datetime", DATE, REQ, "action_creation_datetime" );
    AddItemEdit( "action_state", CHAR, REQ, "action_state" );
    AddItemDateTime( "action_state_datetime", DATE, REQ, "action_state_datetime" );
    AddItemEdit( "action_result", CHAR, REQ, "action_result" );
    AddItemDateTime( "action_result_datetime", DATE, REQ, "action_result_datetime" );

  } 
} 
