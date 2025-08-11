package pss.tourism.interfaceGDS.log;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiInterfaceLogs extends JWins {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiInterfaceLogs() throws Exception {
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiInterfaceLog.class;
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 433;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Estado de Interfaz";
	}



	// -------------------------------------------------------------------------//
	// Mapeo las acciones con las operaciones
	// -------------------------------------------------------------------------//
	@Override
	public void createActionMap() throws Exception {
		// addActionNew ( 1, "Nuevo Registro" );
	}

	// -------------------------------------------------------------------------//
	// Configuro las columnas que quiero mostrar en la grilla
	// -------------------------------------------------------------------------//
	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		zLista.AddColumnaLista("company");
		zLista.AddColumnaLista("id");
		zLista.AddColumnaLista("version");
		zLista.AddColumnaLista("lastecho");
		zLista.AddColumnaLista("last_server");
		zLista.AddColumnaLista("last_ip");
		zLista.AddColumnaLista("last_directory");
		zLista.AddColumnaLista("lastfile");
		zLista.AddColumnaLista("lasttransfer");

	}


}
