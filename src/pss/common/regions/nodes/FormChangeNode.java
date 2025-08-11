package  pss.common.regions.nodes;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormChangeNode extends JBaseForm {

  public FormChangeNode() throws Exception {
  }


  //-------------------------------------------------------------------------//
  // Linkeo los campos con la variables del form
  //-------------------------------------------------------------------------//
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
  	SetExitOnOk(true);
    AddItemEdit( "SourceCountry", CHAR, REQ, "source_country" );
    AddItemEdit( "SourceNode", CHAR, REQ, "source_node" );    
    AddItemEdit( "TargetNode", CHAR, REQ, "target_node" );    
  }

  public GuiChangeNode GetWin() throws Exception {
    return (GuiChangeNode) oWin;
  }
}
