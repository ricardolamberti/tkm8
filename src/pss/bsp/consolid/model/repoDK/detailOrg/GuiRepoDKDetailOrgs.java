package pss.bsp.consolid.model.repoDK.detailOrg;

import pss.common.regions.divitions.GuiPaisesLista;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiRepoDKDetailOrgs extends JWins {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiRepoDKDetailOrgs() throws Exception {
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiRepoDKDetailOrg.class;
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
		zLista.AddColumnaLista("org");
		zLista.AddColumnaLista("bruta_dom");
		zLista.AddColumnaLista("bruta_int");
		zLista.AddColumnaLista("nodev_dom");
		zLista.AddColumnaLista("nodev_int");
		zLista.AddColumnaLista("reemb");
			}
	@Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		zFiltros.addEditResponsive("org", "org").setOperator("ilike");
	}

}
