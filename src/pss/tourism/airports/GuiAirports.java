package pss.tourism.airports;

import pss.common.regions.divitions.GuiPaisesLista;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiAirports extends JWins {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiAirports() throws Exception {
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiAirport.class;
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 1105;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Aeropuertos";
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
		zLista.AddColumnaLista("code");
		zLista.AddColumnaLista("description");
		zLista.AddColumnaLista("descr_country");
		zLista.AddColumnaLista("city");
	}

	@Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		// zFiltros.NuevoCatalog("Busqueda", "CHAR", "Busqueda", true);
		zFiltros.addEditResponsive("Código", "CHAR", "code").setOperator("ilike");;
		zFiltros.addEditResponsive("Descripción", "CHAR", "description").setOperator("ilike");
		zFiltros.addWinLovResponsive("País", "country", new JControlWin() {

			@Override
			public JWins getRecords(boolean zOneRow) throws Exception {
				return new GuiPaisesLista();
			}
		});
		zFiltros.addEditResponsive("Ciudad", "CHAR", "city");
		zFiltros.addEditResponsive("IATA area", "CHAR", "iata_area");
	}

}
