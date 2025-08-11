package  pss.common.regions.nodes;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormRegionNodo extends JBaseForm {

  public FormRegionNodo() throws Exception {
  }

  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemCombo( "region", UINT, REQ, "region", new GuiRegiones() );
    AddItemCombo( "nodo", CHAR, REQ, "nodo", new GuiNodos() );

  }
}
