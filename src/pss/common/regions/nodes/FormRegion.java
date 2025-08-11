package  pss.common.regions.nodes;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormRegion extends JBaseForm {

  public FormRegion() throws Exception {
  }



  //-------------------------------------------------------------------------//
  // Linkeo los campos con la variables del form
  //-------------------------------------------------------------------------//
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "region", UINT, OPT, "region" );
    AddItemEdit( "descripcion", CHAR, REQ, "descripcion" );
    //AddItem( Componentes, GetWin().ObtenerRegiones() );
  }

  public GuiRegion GetWin() throws Exception {
    return (GuiRegion) oWin;
  }
}
