package  pss.common.security;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormLogTrace extends JBaseForm {


  public FormLogTrace() throws Exception {
  }



  //-------------------------------------------------------------------------//
  // Linkeo los campos con la variables del form
  //-------------------------------------------------------------------------//
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "NROTRACE", UINT, REQ, "NROTRACE" );
    AddItemEdit( "USUARIO", CHAR, REQ, "USUARIO" );
    AddItemEdit( "FECHA", DATE, REQ, "FECHA" );
    AddItemEdit( "HORA", CHAR, REQ, "HORA" );
    AddItemEdit( "OPERACION", CHAR, REQ, "OPERACION" );
    AddItemEdit( "DATOS", CHAR, REQ, "DATOS" );

  }
}
