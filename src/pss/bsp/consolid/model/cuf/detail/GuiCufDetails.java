package pss.bsp.consolid.model.cuf.detail;

import pss.common.regions.divitions.GuiPaisesLista;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiCufDetails extends JWins {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiCufDetails() throws Exception {
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiCufDetail.class;
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
		zLista.AddColumnaLista("organizacion");
		zLista.AddColumnaLista("tacn");
		zLista.AddColumnaLista("efco");
		zLista.AddColumnaLista("agente");
		zLista.AddColumnaLista("dk");
		zLista.AddColumnaLista("al");
		zLista.AddColumnaLista("ruta");
		zLista.AddColumnaLista("correo");
		zLista.AddColumnaLista("uso_cfdi");
		zLista.AddColumnaLista("forma_pago");
		}
	@Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		zFiltros.addEditResponsive("DK", "dk").setOperator("ilike");
		zFiltros.addEditResponsive("Aerolínea", "al");
		zFiltros.addEditResponsive("Cod.Aerolinea", "tacn");
		zFiltros.addEditResponsive("Org.", "organizacion").setOperator("ilike");
	}

}
