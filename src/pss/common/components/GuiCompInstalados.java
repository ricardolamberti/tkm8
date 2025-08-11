package  pss.common.components;

import pss.JPssVersion;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiCompInstalados extends JWins {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiCompInstalados() throws Exception {
	}

	@Override
	public JRecords<? extends JRecord> ObtenerDatos() throws Exception {
		return new BizCompInstalados();
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiCompInstalado.class;
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 78;
	}

	@Override
	public String GetTitle() throws Exception {
		return JPssVersion.getPssTitle();
	}

	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		zLista.AddColumnaLista("descripcion");
	}

}
