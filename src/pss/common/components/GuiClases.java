package  pss.common.components;

import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiClases extends JWins {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiClases() throws Exception {
	}

	@Override
	public JRecords<? extends JRecord> ObtenerDatos() throws Exception {
		return new BizClases();
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiClase.class;
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 90;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Clases";
	}

	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		zLista.AddColumnaLista("archivo");
	}

	@Override
	public boolean canExpandTree() throws Exception {
		return false;
	}

}
