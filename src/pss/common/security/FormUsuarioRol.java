package  pss.common.security;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;


public class FormUsuarioRol extends JBaseForm {


  public JWin getWin() {
  	return (GuiUsuarioRol) this.getBaseWin();
  }

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public FormUsuarioRol() throws Exception {
  }

  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
  	JFormPanelResponsive col  = this.AddItemColumn(2);
  	col.AddItemEdit( "company", CHAR, REQ, "company" ).hide();
  	col.AddItemEdit( "Usuario", CHAR, REQ, "USUARIO" ).hide();
  	col.AddItemCombo( "Perfil", CHAR, REQ, "ROL", new JControlCombo() {
    	public JWins getRecords(boolean one) throws Exception {
    		return getRoles();
    	}
    });
  }
  
	public JWins getRoles() throws Exception {
		GuiRoles roles=new GuiRoles();
		if (!BizUsuario.getUsr().hasCompany()) return roles;
		roles.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
		roles.getRecords().addFilter("tipo", BizRoles.JERARQUIA);
		roles.SetVision(BizRoles.JERARQUIA);
		return roles;
	}

  
}
