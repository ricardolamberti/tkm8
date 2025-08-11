package  pss.common.regions.company;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiCompanyBusinessModules extends JWins {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiCompanyBusinessModules() throws Exception {
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 5000;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Módulos de Negocio";
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiCompanyBusinessModule.class;
	}

	// -------------------------------------------------------------------------//
	// Mapeo las acciones con las operaciones
	// -------------------------------------------------------------------------//
	@Override
	public void createActionMap() throws Exception {
		this.addActionNew(1, "Nuevo Módulo");
	}


	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		zLista.AddColumnaLista("business");
		zLista.AddColumnaLista("module");
	}

}
