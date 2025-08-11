package pss.common.customDashboard2;

import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiDashBoardConfigs extends JWins {

	/**
	 * Constructor de la Clase
	 */
	public GuiDashBoardConfigs() throws Exception {
	}

	public int GetNroIcono() throws Exception {
		return 92;
	}

	public String GetTitle() throws Exception {
		return "Configuracion Dashboard";
	}

	public Class<? extends JWin> GetClassWin() {
		return GuiDashBoardConfig.class;
	}

	/**
	 * Mapeo las acciones con las operaciones
	 */
	public void createActionMap() throws Exception {
		// addActionNew( 1, "Nuevo Registro" );
	}

	/**
	 * Configuro las columnas que quiero mostrar en la grilla
	 */
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		zLista.AddColumnaLista("dash_order");
		zLista.AddColumnaLista("DASH_DESCR");
		
	
		if (BizUsuario.getUsr().isAnyAdminUser())
			zLista.AddColumnaLista("ONLY_ADMIN");
	}



}
