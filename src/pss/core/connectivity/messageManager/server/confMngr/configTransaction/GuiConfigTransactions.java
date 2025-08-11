package pss.core.connectivity.messageManager.server.confMngr.configTransaction;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiConfigTransactions extends JWins {

	/**
	 * Constructor de la Clase
	 */
	public GuiConfigTransactions() throws Exception {
	}

	public int GetNroIcono() throws Exception {
		return 909;
	}

	public String GetTitle() throws Exception {
		return "Transacciones de Configuracion";
	}

	public Class<? extends JWin> GetClassWin() {
		return GuiConfigTransaction.class;
	}

	/**
	 * Mapeo las acciones con las operaciones
	 */
	public void createActionMap() throws Exception {
		// addActionNew(1, "Nuevo Registro");
	}

	/**
	 * Configuro las columnas que quiero mostrar en la grilla
	 */
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		// super.ConfigurarColumnasLista(zLista);
		zLista.AddIcono("");
		zLista.AddColumnaLista("store");
		zLista.AddColumnaLista("state");
		zLista.AddColumnaLista("module_id");
		zLista.AddColumnaLista("conf_id");
		zLista.AddColumnaLista("creation_datetime");
		zLista.AddColumnaLista("last_modif_datetime");
		zLista.AddColumnaLista("description");
	}

}
