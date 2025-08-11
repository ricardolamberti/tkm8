package  pss.common.regions.nodes;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormNodoPC extends JBaseForm {

  public FormNodoPC() throws Exception {
  }

  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemCombo( "Nodo", CHAR, REQ, "nodo", new GuiNodos() );
    AddItemEdit( "PssPath", CHAR, REQ, "Pss_path" );
    AddItemEdit( "Host", CHAR, REQ, "host" );
    AddItemCheck( "Master", REQ, "is_master");
  }

}
