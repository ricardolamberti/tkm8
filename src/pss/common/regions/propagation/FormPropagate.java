package  pss.common.regions.propagation;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormPropagate extends JBaseForm {

	public FormPropagate() throws Exception {
  }

  public GuiPropagate GetWin() { return (GuiPropagate) getBaseWin(); }

  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "id_propagate", CHAR, REQ, "id_propagate" );
    AddItemEdit( "description", CHAR, REQ, "description" );
    AddItemCheck( "propagate", REQ, "propagate");
    AddItemCheck( "propagateToParent", REQ, "FromChildren");
    AddItemCheck( "propagateToChildren", REQ, "ToChildren");
    AddItemCheck( "propagateToMaster", REQ, "ToMaster");
    AddItemTabPanel().AddItemTab(5);

  }
}






















































