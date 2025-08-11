package pss.bsp.consolid.model.repoDK.detailContrato;

import pss.common.regions.divitions.GuiPaisesLista;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiRepoDKDetailContratos extends JWins {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiRepoDKDetailContratos() throws Exception {
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiRepoDKDetailContrato.class;
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
		zLista.AddColumnaLista("contrato");
		zLista.AddColumnaLista("comision");

			}
	@Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		zFiltros.addEditResponsive("contrato", "contrato").setOperator("ilike");
	}

}
