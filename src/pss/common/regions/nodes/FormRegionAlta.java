package  pss.common.regions.nodes;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormRegionAlta extends JBaseForm {


  public FormRegionAlta() throws Exception {
  }


  //-------------------------------------------------------------------------//
  // Linkeo los campos con la variables del form
  //-------------------------------------------------------------------------//
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
  	SetExitOnOk(true);  	
    AddItemEdit( "region", UINT, OPT, "region" );
    AddItemEdit( "descripcion", CHAR, REQ, "descripcion" );
  }
}
