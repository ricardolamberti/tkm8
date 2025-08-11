package pss.common.personalData;

import pss.core.win.JWin;

public class FormDomicilio extends FormBaseDomicilio {

	@Override
	public void InicializarPanel(JWin zBase) throws Exception {
		this.AddItemEdit("Persona", CHAR, REQ, "persona").hide();
		this.AddItemEdit("Secuencia", CHAR, REQ, "secuencia").hide();
		this.createPanelDomicilio();
	}
}
