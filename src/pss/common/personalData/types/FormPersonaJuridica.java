package  pss.common.personalData.types;

import pss.common.personalData.GuiPersona;
import pss.core.win.JWin;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormPersonaJuridica extends FormPersona {

	public FormPersonaJuridica() throws Exception {
	}

	@Override
	public GuiPersona GetWin() {
		return (GuiPersona) getBaseWin();
	}

	// -------------------------------------------------------------------------//
	// Linkeo los campos con la variables del form
	// -------------------------------------------------------------------------//
	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		AddItemEdit("persona", UINT, OPT, "persona");
		AddItemEdit("apellido", CHAR, REQ, "apellido");
		AddItemEdit("nombre", CHAR, OPT, "nombre");
		AddItemDateTime("fecha Nacimiento", DATE, OPT, "fecha_nacimiento");
		AddItemArea("Observacion", CHAR, OPT, "observaciones");
		AddItemEdit("email", CHAR, OPT, "e_mail");
		AddItemEdit("web", CHAR, OPT, "web");
//		AddItem(getIdSistemaExterno(), CHAR, OPT, "id_sistema_externo");
		AddItem("Contacto", CHAR, OPT, "contacto");
    if (isAlta()) return;

    JFormTabPanelResponsive tab = this.AddItemTabPanel();
    tab.AddItemTab(10);
    tab.AddItemTab(12);
	}

}

