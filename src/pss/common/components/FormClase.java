package  pss.common.components;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;


public class FormClase extends JBaseForm {

  public FormClase() throws Exception {
  }
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "Componente", CHAR, REQ, "componente" );
    AddItemEdit( "Archivo", CHAR, REQ, "archivo" );
    AddItemEdit( "TablaAsociada", CHAR, REQ, "tabla_asociada" );
  }
}
