package pss.common.personalData.aditionals.types;

import pss.common.personalData.aditionals.GuiPersonaAdicional;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormTipoAdicional extends JBaseForm {

	
	private GuiPersonaAdicional getWin() { 
		return (GuiPersonaAdicional) this.getBaseWin(); 
	}
  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public FormTipoAdicional() throws Exception {
  }


  //-------------------------------------------------------------------------//
  // Linkeo los campos con la variables del form
  //-------------------------------------------------------------------------//
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    JFormPanelResponsive col = this.AddItemColumn(3);
  	col.AddItemEdit("company", CHAR, REQ, "company" ).hide();
  	col.AddItemEdit( "Tipo", CHAR, OPT, "tipo");
  	col.AddItemEdit( "Descripción", CHAR, OPT, "descripcion");
  }
  
}