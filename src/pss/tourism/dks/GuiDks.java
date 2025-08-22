package pss.tourism.dks;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiDks extends JWins {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiDks() throws Exception {
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiDk.class;
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 1105;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Extra DK";
	}

	// -------------------------------------------------------------------------//
	// Mapeo las acciones con las operaciones
	// -------------------------------------------------------------------------//
	@Override
	public void createActionMap() throws Exception {
		addActionNew(1, "Nuevo Registro");
	}

	// -------------------------------------------------------------------------//
	// Configuro las columnas que quiero mostrar en la grilla
	// -------------------------------------------------------------------------//
	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		zLista.AddColumnaLista("dk");
		zLista.AddColumnaLista("office_id");
		zLista.AddColumnaLista("ag_emision");
		zLista.AddColumnaLista("gds");
		zLista.AddColumnaLista("dk_sinonimo");
	}

	@Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		// zFiltros.NuevoCatalog("Busqueda", "CHAR", "Busqueda", true);
		zFiltros.addEditResponsive("DK", "CHAR", "dk").setOperator("ilike");
		zFiltros.addEditResponsive("Office id", "CHAR", "office_id").setOperator("ilike");
		zFiltros.addEditResponsive("Ag.Emision", "CHAR", "ag_emision").setOperator("ilike");
		zFiltros.addEditResponsive("GDS", "CHAR", "gds").setOperator("ilike");
		zFiltros.addEditResponsive("DK sinónimo", "CHAR", "dk_sinonimo").setOperator("ilike");
		
	}

}
