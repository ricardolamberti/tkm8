package pss.bsp.consolid.model.repoCarrier.detail;

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
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

import java.awt.Rectangle;
import java.awt.Dimension;

public class FormRepoCarrierDetail extends JBaseForm {

	
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public FormRepoCarrierDetail() throws Exception {

	}

	public GuiRepoCarrierDetail GetWin() {
		return (GuiRepoCarrierDetail) GetBaseWin();
	}



	// -------------------------------------------------------------------------//
	// Linkeo los campos con la variables del form
	// -------------------------------------------------------------------------//
	public void InicializarPanel(JWin zWin) throws Exception {
		AddItemEdit(null, CHAR, OPT, "company").setHide(true);
		AddItemEdit(null, UINT, OPT, "id").setHide(true);
		AddItemEdit(null, UINT, OPT, "repo_dk_id").setHide(true);
		
		
			JFormPanelResponsive row=AddItemRow();
		row.AddItemEdit("Aerolinea", CHAR, OPT, "carrier").setSizeColumns(4);
		row=AddItemRow();
		row.AddItemEdit("Cod.aerolinea", CHAR, OPT, "cod_aerolinea").setSizeColumns(4);
		row.AddItemEdit("Aerolinea", CHAR, OPT, "Aerolinea").setSizeColumns(6);
		row=AddItemRow();
		row.AddItemEdit("Venta global neto", FLOAT, OPT, "venta_global_neto").setSizeColumns(4);
		row.AddItemEdit("Venta global total", FLOAT, OPT, "venta_global_total").setSizeColumns(4);
		row.AddItemEdit("Reembolso", FLOAT, OPT, "reembolsos").setSizeColumns(4);
	  row=AddItemRow();
	  row.AddItemEdit("Comisión", FLOAT, OPT, "comision").setSizeColumns(4);
	 // row.AddItemEdit("Total", FLOAT, OPT, "total").setSizeColumns(4);
		row.AddItemEdit("Total ingresos", FLOAT, OPT, "ttl_ingresos").setSizeColumns(4);
		
		JFormTabPanelResponsive panels = AddItemTabPanel();
		panels.AddItemTab(10);
	}

} // @jve:decl-index=0:visual-constraint="10,10"
