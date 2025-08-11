package pss.common.version.pack.detail;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormPackageDetail extends JBaseForm {

  public FormPackageDetail() throws Exception {
  }

  public GuiPackageDetail getWin() { return (GuiPackageDetail) getBaseWin(); }


  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "id_package", UINT, REQ, "id_package" );
    AddItemEdit( "id_sub_package", UINT, REQ, "id_sub_package" );
    AddItemEdit( "key_pack", CHAR, REQ, "key_pack" );
    AddItemEdit( "dpackage", CHAR, REQ, "pack" );

  } 
} 
