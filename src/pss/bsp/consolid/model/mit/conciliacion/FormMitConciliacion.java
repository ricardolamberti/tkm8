package pss.bsp.consolid.model.mit.conciliacion;

import pss.common.regions.divitions.GuiPaises;
import pss.common.regions.divitions.GuiPaisesLista;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssGoogleMap;
import pss.core.ui.components.JPssLabel;
import pss.core.ui.components.JPssLabelFormLov;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

import java.awt.Rectangle;
import java.awt.Dimension;

public class FormMitConciliacion extends JBaseForm {

	
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public FormMitConciliacion() throws Exception {

	}

	public GuiMitConciliacion GetWin() {
		return (GuiMitConciliacion) GetBaseWin();
	}

	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		AddItemEdit(null, CHAR, OPT, "company").setHide(true);
		AddItemEdit(null, UINT, OPT, "id_conciliacion").setHide(true);

		JFormPanelResponsive row = this.AddItemRow();
		row.AddItemEdit("Nro Op.", CHAR, OPT, "nor_op").setSizeColumns(4);
		row.AddItemEdit("Tipo Op.", CHAR, OPT, "tipo_op").setSizeColumns(4);

		row = this.AddItemRow();
		row.AddItemEdit("PNR", CHAR, OPT, "pnr").setSizeColumns(4);
		row.AddItemEdit("Fecha", DATE, OPT, "fecha").setSizeColumns(4);

		row = this.AddItemRow();
		row.AddItemEdit("Importe MIT", FLOAT, OPT, "importe_mit").setSizeColumns(4).setReadOnly(true);
		row.AddItemEdit("Importe PNR", FLOAT, OPT, "importe_pnr").setSizeColumns(4).setReadOnly(true);

		row = this.AddItemRow();
		row.AddItemEdit("Diferencia", FLOAT, OPT, "diferencia").setSizeColumns(4).setReadOnly(true);
		row.AddItemCombo("Estado", CHAR, OPT, "estado_conciliacion").setSizeColumns(4).setReadOnly(true);

		row = this.AddItemRow();
		row.AddItemEdit("Fecha Conciliación", DATE, OPT, "fecha_conciliacion").setSizeColumns(4).setReadOnly(true);

		row = this.AddItemRow();
		row.AddItemArea("Detalle", CHAR, OPT, "detalle").setSizeColumns(12).setHeight(200).setReadOnly(true);
	}




	// -------------------------------------------------------------------------//
	// Linkeo los campos con la variables del form
	// -------------------------------------------------------------------------//


} // @jve:decl-index=0:visual-constraint="10,10"
