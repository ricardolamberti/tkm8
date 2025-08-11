package  pss.common.calendarTools.horarioLaboral;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiHorariosLaborales extends JWins {

	/**
	 * Constructor de la Clase
	 */
	public GuiHorariosLaborales() throws Exception {
	}

	public int GetNroIcono() throws Exception {
		return 10001;
	}

	public String GetTitle() throws Exception {
		return "Horarios Laborales";
	}

	public Class<? extends JWin> GetClassWin() {
		return GuiHorarioLaboral.class;
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
		zLista.AddColumnaLista("vigente_desde");
		zLista.AddColumnaLista("vigente_hasta");
		zLista.AddColumnaLista("horario_entrada");
		zLista.AddColumnaLista("horario_salida");
	}

}
