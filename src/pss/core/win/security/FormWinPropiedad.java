package pss.core.win.security;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormWinPropiedad extends JBaseForm {


  public FormWinPropiedad() throws Exception {
  }



  public GuiWinPropiedad GetWin() {
    return (GuiWinPropiedad) getBaseWin();
  }

  //-------------------------------------------------------------------------//
  // Linkeo los campos con la variables del form
  //-------------------------------------------------------------------------//
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
  	JFormPanelResponsive col = this.AddItemColumn(6);
//  	col.setLabelLeft(4);
    col.AddItemEdit( "Descripción", CHAR, REQ, "nombre", 6 );
    col.AddItemEdit( "Clase"  , CHAR, REQ, "clase", 6 );
//    AddItem( Version, CHAR, REQ, "version" );
//    AddItem( Icono  , CHAR, REQ, "nro_icono" );
    this.AddItemTabPanel().AddItemTab(10);
//    bIcono.setIcon(GuiIconos.GetGlobal().buscarIcono(((GuiWinPropiedad)zWin).GetcDato().pNroIcono.getValue()).GetIconImage());
  }

}  