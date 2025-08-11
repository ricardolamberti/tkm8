package  pss.common.customMenu;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiMenuItems extends JWins {

	public GuiMenuItems() throws Exception {
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 10071;
	}
	@Override
	public String GetTitle() throws Exception {
		return "Items";
	}
	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiMenuItem.class;
	}

	@Override
	public void createActionMap() throws Exception {
		this.addActionNew(1, "Nuevo Registro");
	}

	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		zLista.AddColumnaLista("orden");
		zLista.AddColumnaLista("final_descr");
	}

}
