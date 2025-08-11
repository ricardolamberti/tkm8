package  pss.common.regions.company;

import pss.core.connectivity.messageManager.common.core.JMMBaseForm;
import pss.core.win.JWin;
import pss.core.winUI.responsiveControls.JFormComboResponsive;

public class FormCompanyAlta extends JMMBaseForm {
	
  public FormCompanyAlta() {
  }


  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
//    SetConfirmarAplicar( false );
  	
    SetExitOnOk( true );
    JFormComboResponsive oCombo = AddItemCombo( "business"   , CHAR, REQ, "business" , JCompanyBusinessModules.getWins());
//    oCombo.SetNullItem(false);
    oCombo.SetValorDefault(JCompanyBusinessModules.DEFAULT);
  }

}

