package pss.common.issueTracker.setting;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiIssueTrackerSettings extends JWins {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiIssueTrackerSettings() throws Exception {
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiIssueTrackerSetting.class;
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 10055;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Configuración Mantis";
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
		zLista.AddColumnaLista("company");
		zLista.AddColumnaLista("handler_user");
		zLista.AddColumnaLista("casilla_descr");
		zLista.AddColumnaLista("default_setting");
	}

	

}
