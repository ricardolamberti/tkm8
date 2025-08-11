package pss.bsp.consolid.model.report.detail;

import pss.common.regions.divitions.GuiPaisesLista;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiReportDetailDks extends JWins {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiReportDetailDks() throws Exception {
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiReportDetailDk.class;
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
		zLista.AddColumnaLista("added");
		zLista.AddColumnaLista("date_of_tick");
		zLista.AddColumnaLista("ctrip");
		zLista.AddColumnaLista("tkt_number");
		zLista.AddColumnaLista("passager_name");
		zLista.AddColumnaLista("flight_nro");
		zLista.AddColumnaLista("od");
		zLista.AddColumnaLista("ctrip_amount");
		zLista.AddColumnaLista("total");
		zLista.AddColumnaLista("fare");
		zLista.AddColumnaLista("comision");
		zLista.AddColumnaLista("fee");
		zLista.AddColumnaLista("total_pay");
	}

	@Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		zFiltros.addEditResponsive("Número doc.", "ctrip").setOperator("ilike");
		zFiltros.addEditResponsive("Número boleto", "tkt_number").setOperator("ilike");
		zFiltros.addEditResponsive("Pasajero", "passager_name").setOperator("ilike");
		zFiltros.addEditResponsive("Vuelo nro.", "flight_nro").setOperator("ilike");
		zFiltros.addCheckThreeResponsive("", "added", "Solo Agregados","","Desde excel");
	}

}
