package pss.core.data.interfaces.connections;

import pss.core.services.records.JRecords;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.icons.GuiIcon;
import pss.core.winUI.lists.JWinList;

public class GuiBDatos extends JWins {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiBDatos() throws Exception {
	}

	@Override
	public int GetNroIcono() throws Exception {
		return GuiIcon.DATABASE_ICON;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Base de Datos";
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiBDato.class;
	}

	@Override
	public JRecords<JBDato> ObtenerDatos() throws Exception {
		return new JBDatos();
	}

	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		zLista.AddColumnaLista("nombre");
	}

}
