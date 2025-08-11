package pss.common.terminals.config.shadows;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiTerminalShadows extends JWins {

	/**
	 * Constructor de la Clase
	 */
	public GuiTerminalShadows() throws Exception {
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 890;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Shadows";
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiTerminalShadow.class;
	}


	@Override
	public boolean canConvertToURL() throws Exception {
		return false;
	}
	
	/**
	 * Configuro las columnas que quiero mostrar en la grilla
	 */
	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		zLista.AddColumnaLista("mac_address");
	}

}
