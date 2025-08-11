package  pss.common.security;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiUserElectronicIds extends JWins {

	/**
	 * Constructor de la Clase
	 */
	public GuiUserElectronicIds() throws Exception {
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 351;
	}
	@Override
	public String GetTitle() throws Exception {
		return "Identificaciónes electrónicas";
	}
	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiUserElectronicId.class;
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
		if ( this.GetVision().equals("ALL") ) {
			zLista.AddColumnaLista("electronic_id");
			zLista.AddColumnaLista("usuario");
		} else {
			zLista.AddColumnaLista("electronic_id");
		}
  }
}
