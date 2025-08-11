package pss.common.terminals.config;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiTerminals extends JWins {

	/**
	 * Constructor de la Clase
	 */
	public GuiTerminals() throws Exception {
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 700;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Terminales";
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiTerminal.class;
	}

	/**
	 * Mapeo las acciones con las operaciones
	 */
	@Override
	public void createActionMap() throws Exception {
		addActionNew(1, "Nuevo Registro");
	}

	/**
	 * Configuro las columnas que quiero mostrar en la grilla
	 */
	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		zLista.AddColumnaLista("descr_terminal_type");
		zLista.AddColumnaLista("connection_string");
	}

}
