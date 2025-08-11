package  pss.common.regions.entidad.nodos;

import pss.common.regions.divitions.GuiPaisesLista;
import pss.common.security.BizUsuario;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiEntidadeNodos extends JWins {

	/**
	 * Constructor de la Clase
	 */
	public GuiEntidadeNodos() throws Exception {
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 5107;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Sucursales";
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiEntidadNodo.class;
	}

	/**
	 * Mapeo las acciones con las operaciones
	 */
	@Override
	public void createActionMap() throws Exception {
		addActionNew(1, "Nuevo Canal");
	}

	/**
	 * Configuro las columnas que quiero mostrar en la grilla
	 */
	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		zLista.AddColumnaLista("descr_nodo");
	}


}
