package  pss.common.calendarTools.diasNoLaborales;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiDiasNoLaborables extends JWins {

	/**
	 * Constructor de la Clase
	 */
	public GuiDiasNoLaborables() throws Exception {
	}

	public int GetNroIcono() throws Exception {
		return 10001;
	}

	public String GetTitle() throws Exception {
		return "Dias No Laborables";
	}

	public Class<? extends JWin> GetClassWin() {
		return GuiDiaNoLaborable.class;
	}

	/**
	 * Mapeo las acciones con las operaciones
	 */
	public void createActionMap() throws Exception {
		addActionNew(1, "Nuevo Registro");
	}

	/**
	 * Configuro las columnas que quiero mostrar en la grilla
	 */
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		// super.ConfigurarColumnasLista(zLista);
		zLista.AddIcono("");
		zLista.AddColumnaLista("descripcion");
		zLista.AddColumnaLista("fecha_desde");
		zLista.AddColumnaLista("fecha_hasta");
		// zLista.AddColumnaLista("tipo_dia_no_laborable");
		zLista.AddColumnaLista("desc_tipo_dia_no_laborable");
		zLista.AddColumnaLista("movil");		
	}

}
