package  pss.common.regions.nodes;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormNodoDatabase extends JBaseForm {

  public FormNodoDatabase() throws Exception {
  }

  public GuiNodoDatabase GetWin() { return (GuiNodoDatabase) getBaseWin(); }

  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemCombo( "Nodo", CHAR, REQ, "nodo", new GuiNodos() );
    AddItemEdit( "host", CHAR, REQ, "host" );
    AddItemEdit( "port", UINT, REQ, "port" );
    AddItemEdit( "host_db", CHAR, REQ, "host_db" );
    AddItemEdit( "seccion_ini", CHAR, REQ, "seccion_ini" );
    AddItemCheck( "Master", REQ, "is_master");
  }

}
