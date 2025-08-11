package pss.bsp.consolid.model.repoDK.detailMonth;

import pss.common.regions.divitions.GuiPaisesLista;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiRepoDKDetailMonths extends JWins {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiRepoDKDetailMonths() throws Exception {
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiRepoDKDetailMonth.class;
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
		zLista.AddColumnaLista("period_desde");
		zLista.AddColumnaLista("period_hasta");
		zLista.AddColumnaLista("venta_bsp_neto");
		zLista.AddColumnaLista("venta_bsp_total");
		zLista.AddColumnaLista("bajo_costo_neto");
		zLista.AddColumnaLista("bajo_costo_total");
		zLista.AddColumnaLista("boletos_emds");
		zLista.AddColumnaLista("boletos_tkts");
		zLista.AddColumnaLista("boletos_bajo_costo");
		zLista.AddColumnaLista("boletos_total");
		}

	@Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
//		zFiltros.addEditResponsive("DK", "dk").setOperator("ilike");
//		zFiltros.addEditResponsive("Aerolínea", "al");
//		zFiltros.addEditResponsive("Cod.Aerolinea", "tacn");
//		zFiltros.addEditResponsive("Org.", "organizacion").setOperator("ilike");
	}

}
