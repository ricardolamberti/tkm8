package pss.bsp.consolid.model.repoDK.detail;

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

public class FormRepoDKDetail extends JBaseForm {

	
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public FormRepoDKDetail() throws Exception {

	}

	public GuiRepoDKDetail GetWin() {
		return (GuiRepoDKDetail) GetBaseWin();
	}



	// -------------------------------------------------------------------------//
	// Linkeo los campos con la variables del form
	// -------------------------------------------------------------------------//
	public void InicializarPanel(JWin zWin) throws Exception {
		AddItemEdit(null, CHAR, OPT, "company").setHide(true);
		AddItemEdit(null, UINT, OPT, "id").setHide(true);
		AddItemEdit(null, UINT, OPT, "repo_dk_id").setHide(true);
		
		
			JFormPanelResponsive row=AddItemRow();
		row.AddItemEdit("DK", CHAR, OPT, "dk").setSizeColumns(4);
		row.AddItemEdit("DK anterior", CHAR, OPT, "dk_anterior").setSizeColumns(4);
		row=AddItemRow();
		row.AddItemEdit("Tipo emision", CHAR, OPT, "tipo_emision").setSizeColumns(4);
		row.AddItemEdit("Razón social", CHAR, OPT, "razon_social").setSizeColumns(4);
		row.AddItemEdit("Nombre comercial", CHAR, OPT, "nombre_comercial").setSizeColumns(4);
		row=AddItemRow();
		row.AddItemEdit("Ubicación", CHAR, OPT, "ubicacion").setSizeColumns(4);
		row.AddItemEdit("GDS", CHAR, OPT, "tipo_gds").setSizeColumns(4);
		row.AddItemEdit("Linea negocio", CHAR, OPT, "linea_negocio").setSizeColumns(4);
		row=AddItemRow();
		row.AddItemEdit("Venta global neto", FLOAT, OPT, "venta_global_neto").setSizeColumns(4);
		row.AddItemEdit("Venta global total", FLOAT, OPT, "venta_global_total").setSizeColumns(4);
	  row=AddItemRow();
		row.AddItemEdit("Total", FLOAT, OPT, "total").setSizeColumns(4);
		row.AddItemEdit("Total ingresos", FLOAT, OPT, "ttl_ingresos").setSizeColumns(4);
		
		JFormTabPanelResponsive panels = AddItemTabPanel();
		panels.AddItemTab(10);
		panels.AddItemTab(20);
		panels.AddItemTab(30);
	}

} // @jve:decl-index=0:visual-constraint="10,10"
