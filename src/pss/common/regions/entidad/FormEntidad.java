package  pss.common.regions.entidad;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormEntidad extends JBaseForm {

	public FormEntidad() throws Exception {
	}

	public GuiEntidad GetWin() {
		return (GuiEntidad) getBaseWin();
	}

	/**
	 * Linkeo los campos con la variables del form
	 */
	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		JFormPanelResponsive col = this.AddItemColumn(8);
		col.AddItemEdit("id", CHAR, OPT, "id").hide();
		col.AddItemEdit("company", CHAR, REQ, "company").hide();
		col.AddItemEdit("Entidad", CHAR, REQ, "descripcion", 2);
		col.AddItemArea("URL", CHAR, OPT, "url");
		
		JFormTabPanelResponsive tab = col.AddItemTabPanel();
		tab.AddItemTab(10);
	}

}
