package pss.common.issueTracker.issue.history;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiIssueHistorys extends JWins {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiIssueHistorys() throws Exception {
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiIssueHistory.class;
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 10082;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Historia";
	}



	
	// -------------------------------------------------------------------------//
	// Configuro las columnas que quiero mostrar en la grilla
	// -------------------------------------------------------------------------//
	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		zLista.AddColumnaLista("date_submitted");
		zLista.AddColumnaLista("descr_usuario");
		zLista.AddColumnaLista("description");
		zLista.AddColumnaLista("qty_hours_from_last_status");
	}


	
}
