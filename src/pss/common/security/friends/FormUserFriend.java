package pss.common.security.friends;

import pss.common.security.GuiUsuarios;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormUserFriend extends JBaseForm {

	public FormUserFriend() throws Exception {
	}

	public GuiUserFriend GetWin() {
		return (GuiUserFriend) getBaseWin();
	}

	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		JFormPanelResponsive col = this.AddItemColumn();
		col.AddItemEdit("Company", CHAR, REQ, "company").hide();
		col.AddItemEdit("User", CHAR, REQ, "usuario").hide();
		col.AddItemCombo("Usuario Vinculado", CHAR, REQ, "usuario_friend", new JControlCombo() {
			public JWins getRecords(boolean one) throws Exception {
				return getUsuarios(one);
			}
		});
	}

	public JWins getUsuarios(boolean one) throws Exception {
		GuiUsuarios wins=new GuiUsuarios();
		if (one) {
			wins.getRecords().addFilter("usuario", GetWin().GetcDato().getFriend());
		} else {
			wins.getRecords().addFilter("company", this.valueControl("company"));
		}
		return wins;
	}


} // @jve:decl-index=0:visual-constraint="10,10"
