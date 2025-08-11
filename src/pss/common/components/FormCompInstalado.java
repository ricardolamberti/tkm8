package  pss.common.components;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormCompInstalado extends JBaseForm {

  public FormCompInstalado() throws Exception {
  }

  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "Componente", CHAR, REQ, "componente" );
    AddItemEdit( "Descrip", CHAR, REQ, "descripcion" );
    AddItemTabPanel().AddItemTab(25 );
  }
  
}
