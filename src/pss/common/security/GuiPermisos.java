package  pss.common.security;

import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.win.JWin;
import pss.core.win.JWins;

public class GuiPermisos extends JWins {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiPermisos() throws Exception {
	}

	@Override
	public JRecords<? extends JRecord> ObtenerDatos() throws Exception {
		return new BizPermisos();
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiPermiso.class;
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 1;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Permisos de Logueo";
	}

	@Override
	public void createActionMap() throws Exception {
		addActionNew(1, "Nuevo Registro");
	}
}
