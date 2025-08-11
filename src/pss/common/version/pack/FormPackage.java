 package pss.common.version.pack;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormPackage  extends JBaseForm {

  public FormPackage() throws Exception {
  }

  public GuiPackage  getWin() { return (GuiPackage) getBaseWin(); }


  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "id_package", UINT, REQ, "id_package" );
    AddItemEdit( "date_creation", DATE, REQ, "date_creation" );
    AddItemEdit( "status", CHAR, REQ, "status" );
    AddItemDateTime( "date_installation", DATE, REQ, "date_installation" );
    AddItemEdit( "description", CHAR, REQ, "description" );

  } 
} 
