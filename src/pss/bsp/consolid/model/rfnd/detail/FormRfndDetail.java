package pss.bsp.consolid.model.rfnd.detail;

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

public class FormRfndDetail extends JBaseForm {

	
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public FormRfndDetail() throws Exception {

	}

	public GuiRfndDetail GetWin() {
		return (GuiRfndDetail) GetBaseWin();
	}
	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		AddItemEdit(null, CHAR, OPT, "company").setHide(true);
		AddItemEdit(null, UINT, OPT, "id").setHide(true);
		AddItemEdit(null, UINT, OPT, "id_rfnd").setHide(true);

		JFormPanelResponsive row = this.AddItemRow();
		row.AddItemEdit("Tipo", CHAR, OPT, "tipo").setSizeColumns(3);
		row.AddItemEdit("IATA", CHAR, OPT, "iata").setSizeColumns(3);
		row.AddItemEdit("DK", CHAR, OPT, "dk").setSizeColumns(3);
		row.AddItemEdit("Solicita", CHAR, OPT, "solicita").setSizeColumns(3);

		row = this.AddItemRow();
		row.AddItemEdit("Número", CHAR, OPT, "numero").setSizeColumns(3);
		row.AddItemEdit("FCE", CHAR, OPT, "fce").setSizeColumns(3);
		row.AddItemEdit("Pax/Hotel", CHAR, OPT, "pax_name_hot").setSizeColumns(6);

		row = this.AddItemRow();
		row.AddItemEdit("Concepto", CHAR, OPT, "concepto").setSizeColumns(4);
		row.AddItemEdit("Servicio", CHAR, OPT, "servicio").setSizeColumns(4);
		row.AddItemEdit("LA", CHAR, OPT, "la").setSizeColumns(4);

		row = this.AddItemRow();
		row.AddItemEdit("Folio BSP", CHAR, OPT, "folio_bsp").setSizeColumns(4);
		row.AddItemEdit("Boleto", CHAR, OPT, "boleto").setSizeColumns(4);
		row.AddItemEdit("Forma de Pago", CHAR, OPT, "fpag").setSizeColumns(4);

		row = this.AddItemRow();
		row.AddItemEdit("Tarifa", FLOAT, OPT, "tarifa").setSizeColumns(3);
		row.AddItemEdit("IVA", FLOAT, OPT, "iva").setSizeColumns(3);
		row.AddItemEdit("TUA", FLOAT, OPT, "tua").setSizeColumns(3);
		row.AddItemEdit("Total", FLOAT, OPT, "total").setSizeColumns(3);

		row = this.AddItemRow();
		row.AddItemEdit("Observación", CHAR, OPT, "observacion").setSizeColumns(12);

		row = this.AddItemRow();
		row.AddItemEdit("PNR", CHAR, OPT, "pnr").setSizeColumns(4);
		row.AddItemEdit("Ruta", CHAR, OPT, "ruta").setSizeColumns(4);
		row.AddItemEdit("Fecha Hot", CHAR, OPT, "fecha_hot").setSizeColumns(4);

		row = this.AddItemRow();
		row.AddItemEdit("% Comisión", FLOAT, OPT, "pct_com").setSizeColumns(4);
		row.AddItemEdit("Comisión", FLOAT, OPT, "comision").setSizeColumns(4);
		row.AddItemEdit("Fecha Periodo", CHAR, OPT, "fecha_periodo").setSizeColumns(4);
	}


	// -------------------------------------------------------------------------//
	// Linkeo los campos con la variables del form
	// -------------------------------------------------------------------------//


} // @jve:decl-index=0:visual-constraint="10,10"
