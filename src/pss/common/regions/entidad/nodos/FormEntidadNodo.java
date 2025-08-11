package  pss.common.regions.entidad.nodos;

import pss.common.regions.nodes.GuiNodos;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormEntidadNodo extends JBaseForm {

	public FormEntidadNodo() throws Exception {
	}

	public GuiEntidadNodo GetWin() {
		return (GuiEntidadNodo) getBaseWin();
	}

	/**
	 * Linkeo los campos con la variables del form
	 */
	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		JFormPanelResponsive col = this.AddItemColumn(4);
		col.setLabelLeft(3);
		col.AddItemEdit("id_entidad", CHAR, OPT, "id_entidad").hide();
		col.AddItemEdit("company", CHAR, OPT, "company").hide();
    col.AddItemCombo("Sucursal", CHAR, OPT, "nodo",  new JControlCombo() { 
			public JWins getRecords(boolean one) throws Exception {
				return getNodos(one);
			};
		});
	}
	
	private JWins getNodos(boolean onerow) throws Exception {
		GuiNodos wins = new GuiNodos();
		wins.getRecords().addFilter("company", this.valueControl("company"));
		return wins;
	}


}
