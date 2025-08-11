package  pss.common.security;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormPermiso extends JBaseForm {

  public FormPermiso() throws Exception {
  }

  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "COD_PERMISO", CHAR, REQ, "COD_PERMISO" );
    AddItemEdit( "TIPO_PERMISO", CHAR, REQ, "TIPO_PERMISO" );

  }
}
