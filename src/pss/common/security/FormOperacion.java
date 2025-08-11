package  pss.common.security;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;


public class FormOperacion extends JBaseForm {

	public FormOperacion() throws Exception {
  }

  public GuiOperacion GetWin() {
    return (GuiOperacion) getBaseWin();
  }

  //-------------------------------------------------------------------------//
  // Linkeo los campos con la variables del form
  //-------------------------------------------------------------------------//
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
  	JFormPanelResponsive col = this.AddItemColumn(6);
  	col.AddItemEdit( "company", CHAR, REQ, "company" ).hide();
  	col.AddItemEdit( "Operación" , CHAR, REQ, "operacion", 6 );
  	col.AddItemEdit( "Descripción", CHAR, REQ, "descripcion", 6 );
    col.AddItemTabPanel().AddItemTab(10);
  }

}
