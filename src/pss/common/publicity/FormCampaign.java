package  pss.common.publicity;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormCampaign extends JBaseForm {

  public FormCampaign() throws Exception {
  }

  public GuiCampaign GetWin() { return (GuiCampaign) getBaseWin(); }

  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "id", "UINT", "REQ", "id" );
    AddItemEdit( "description", "UINT", "REQ", "description" );
    AddItemTabPanel().AddItemTab(10 );

  } 
  

} 
