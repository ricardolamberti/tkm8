package pss.common.moduleConfigMngr;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormModuleConfigMngr extends JBaseForm {

  public FormModuleConfigMngr() throws Exception {
  }

  public GuiModuleConfigMngr getWin() { return (GuiModuleConfigMngr) getBaseWin(); }


  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "company", CHAR, REQ, "company" );
    AddItemEdit( "node", CHAR, REQ, "node" );
    AddItemEdit( "country", CHAR, REQ, "country" );
    AddItemEdit( "module_id", CHAR, REQ, "module_id" );
    AddItemEdit( "config_type", CHAR, REQ, "config_type" );
    AddItemEdit( "config_id", CHAR, REQ, "config_id" );
    AddItemEdit( "config_date", DATE, REQ, "config_date" );

  } 
} 
