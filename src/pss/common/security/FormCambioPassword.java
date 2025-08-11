package  pss.common.security;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormCambioPassword extends JBaseForm {

	public FormCambioPassword() {
	}
	
	public GuiCambioPassword GetWin() throws Exception {
		return (GuiCambioPassword) getBaseWin();
	}

	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		JFormPanelResponsive panel = this.AddItemColumn(4);
		panel.setLabelLeft(4);
		panel.AddItemEdit("Usuario", CHAR, REQ, "login").setReadOnly(true);
		panel.AddItemEdit("Descripción", CHAR, OPT, "DESCRIP").setReadOnly(true);
		panel.AddItemPassword("Clave Actual", CHAR, OPT, "passactual").setReadOnly(true);
		panel.AddItemPassword("Nueva Clave", CHAR, REQ, "passnueva");
		panel.AddItemPassword("Confirmar Clave", CHAR, REQ, "passconfir");
	}
	
//	@Override
//	public void OnPostShow() throws Exception {
//		checkControls();
//	}

//	public void checkControls() throws Exception {
//		String sUser = GetWin().GetcDato().pLogin.getValue();
//		String passActual = GetControles().findControl("passactual").getValue();
//		if (passActual.equals("")
//				|| sUser.equalsIgnoreCase(BizUsuario.C_ADMIN_USER)) {
//			GetControles().findControl("passactual").SetReadOnly(true);
//			GetControles().findControl("passactual").disable();
//		}
//	}

}
