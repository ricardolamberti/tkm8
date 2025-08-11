package  pss.common.security;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;


public class FormRol extends JBaseForm {

  public FormRol() {
    super();

   
  }


  public GuiRol GetWin() throws Exception {
    return (GuiRol) GetBaseWin();
  }

  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
  	JFormPanelResponsive col = this.AddItemColumn(4);
  	col.AddItemEdit( null , CHAR, OPT,  "company").setHide(true);
  	col.AddItemEdit( null , CHAR, OPT,  "tipo").setHide(true);
  	col.AddItemEdit( null , CHAR, OPT,  "rol").setHide(true);
  	col.AddItemEdit( "Descripción"  , CHAR, REQ,  "descripcion"   );


  	JFormTabPanelResponsive tabs=col.AddItemTabPanel();
  	tabs.AddItemListOnDemand( 11 ); // Operaciones
  	tabs.AddItemListOnDemand( 20 ); // Usuarios
//  	tabs.AddItemListOnDemand( 10 ); // Roles



  }

}  //  @jve:decl-index=0:visual-constraint="10,10"

