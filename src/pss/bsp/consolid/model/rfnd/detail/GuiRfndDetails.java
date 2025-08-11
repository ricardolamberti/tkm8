package pss.bsp.consolid.model.rfnd.detail;

import pss.common.regions.divitions.GuiPaisesLista;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiRfndDetails extends JWins {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiRfndDetails() throws Exception {
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiRfndDetail.class;
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 5052;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Reporte";
	}

	// -------------------------------------------------------------------------//
	// Mapeo las acciones con las operaciones
	// -------------------------------------------------------------------------//
	@Override
	public void createActionMap() throws Exception {
	//	addActionNew(1, "Nuevo Registro");
	}

	// -------------------------------------------------------------------------//
	// Configuro las columnas que quiero mostrar en la grilla
	// -------------------------------------------------------------------------//
	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");

		zLista.AddColumnaLista("tipo");
		zLista.AddColumnaLista("iata");
		zLista.AddColumnaLista("dk");
		zLista.AddColumnaLista("solicita");
		zLista.AddColumnaLista("numero");
		zLista.AddColumnaLista("fce");
		zLista.AddColumnaLista("pax_name_hot");
		zLista.AddColumnaLista("concepto");
		zLista.AddColumnaLista("servicio");
		zLista.AddColumnaLista("la");
		zLista.AddColumnaLista("folio_bsp");
		zLista.AddColumnaLista("boleto");
		zLista.AddColumnaLista("fpag");
		zLista.AddColumnaLista("tarifa");
		zLista.AddColumnaLista("iva");
		zLista.AddColumnaLista("tua");
		zLista.AddColumnaLista("total");
		zLista.AddColumnaLista("observacion");
		zLista.AddColumnaLista("pnr");
		zLista.AddColumnaLista("ruta");
		zLista.AddColumnaLista("fecha_hot");
		zLista.AddColumnaLista("pct_com");
		zLista.AddColumnaLista("comision");
		zLista.AddColumnaLista("fecha_periodo");
	}
	@Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		zFiltros.addEditResponsive("DK", "dk").setOperator("ilike");
		zFiltros.addEditResponsive("PNR", "pnr").setOperator("ilike");
		zFiltros.addEditResponsive("Boleto", "boleto").setOperator("ilike");
		zFiltros.addEditResponsive("Concepto", "concepto").setOperator("ilike");
		zFiltros.addEditResponsive("Servicio", "servicio").setOperator("ilike");
		zFiltros.addEditResponsive("Pax", "pax_name_hot").setOperator("ilike");
	}

}
