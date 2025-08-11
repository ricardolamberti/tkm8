package pss.common.security.friends;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiUserFriends extends JWins {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiUserFriends() throws Exception {
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiUserFriend.class;
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 10074;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Usuarios Vinculados";
	}

	// -------------------------------------------------------------------------//
	// Mapeo las acciones con las operaciones
	// -------------------------------------------------------------------------//
	@Override
	public void createActionMap() throws Exception {
		addActionNew(1, "Nuevo Vinculo");
	}

	// -------------------------------------------------------------------------//
	// Configuro las columnas que quiero mostrar en la grilla
	// -------------------------------------------------------------------------//
	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
//		zLista.AddColumnaLista("descr_user");
		zLista.AddColumnaLista("descr_friend");
	}

}
