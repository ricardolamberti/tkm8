package pss.bsp.bspBusiness;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiBSPChildCompanies extends JWins {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiBSPChildCompanies() throws Exception {
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiBSPChildCompany.class;
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 1105;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Concentrador";
	}

	// -------------------------------------------------------------------------//
	// Mapeo las acciones con las operaciones
	// -------------------------------------------------------------------------//
	@Override
	public void createActionMap() throws Exception {
		addActionNew(1, "Nuevo Registro");
	}

	// -------------------------------------------------------------------------//
	// Configuro las columnas que quiero mostrar en la grilla
	// -------------------------------------------------------------------------//
	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
//		zLista.AddColumnaLista("company");
		zLista.AddColumnaLista("child_company");
	}
}

	
