package  pss.common.regions.propagation;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormPropagatesDetail extends JBaseForm {

	public FormPropagatesDetail() throws Exception {
  }

  public GuiPropagatesDetail GetWin() { return (GuiPropagatesDetail) getBaseWin(); }

  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "id_propagate", CHAR, REQ, "id_propagate" );
    AddItemCheck( "propagate", REQ, "propagate");
    AddItemCheck( "propagateToParent", REQ, "FromChildren");
    AddItemCheck( "propagateToChildren", REQ, "ToChildren");
    AddItemCheck( "propagateToMaster", REQ, "ToMaster");

  }
}


























































































