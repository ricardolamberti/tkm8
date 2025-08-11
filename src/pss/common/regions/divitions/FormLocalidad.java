package  pss.common.regions.divitions;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormLocalidad extends JBaseForm {

	public FormLocalidad() throws Exception {
  }

  public GuiLocalidad GetWin() { return (GuiLocalidad) getBaseWin(); }

  //-------------------------------------------------------------------------//
  // Inicializacion Grafica
  //-------------------------------------------------------------------------//
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "pais", CHAR, REQ, "pais" );
    AddItemEdit( "provincia", CHAR, REQ, "provincia" );
    AddItemEdit( "ciudad", CHAR, OPT, "ciudad_id" );
    AddItemEdit( "localidad_id", CHAR, OPT, "localidad_id");
    AddItemEdit( "localidad", CHAR, REQ, "localidad" );
  }
  
}
