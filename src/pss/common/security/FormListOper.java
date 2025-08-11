package  pss.common.security;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;


public class FormListOper extends JBaseForm {

	public FormListOper() throws Exception {
  }


  public GuiListOper GetWin() {
    return (GuiListOper) getBaseWin();
  }

  //-------------------------------------------------------------------------//
  // Linkeo los campos con la variables del form
  //-------------------------------------------------------------------------//
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
  	AddItemEdit( "company", CHAR, REQ, "company" );
    AddItemEdit( "Operac" , CHAR, REQ, "OPERACION" );
    AddItemEdit( "Descrip", CHAR, REQ, "DESCRIPCION" );
    AddItemTabPanel().AddItemTab(10);
  }
  
}
