package  pss.common.security;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;


public class FormRolVinculado extends JBaseForm {

  public FormRolVinculado() throws Exception {
  }

  public GuiRolVinculado GetWin() throws Exception {
    return (GuiRolVinculado) getBaseWin();
  }

  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
  	JFormPanelResponsive col = this.AddItemColumn(2);
    col.AddItemEdit( "rol", CHAR, REQ, "rol" ).hide();
    col.AddItemCombo( "Rol", CHAR, REQ, "rol_vinculado" , new JControlCombo() {
    	@Override
    	public JWins getRecords(boolean one) throws Exception {
    		return GetWin().getRolesPosibles();
    	}
    });
  }



}
