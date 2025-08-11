package  pss.common.customMenu;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiMenus extends JWins {

	public GuiMenus() throws Exception {
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 10070;
	}
	@Override
	public String GetTitle() throws Exception {
		return "Menús";
	}
	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiMenu.class;
	}

	@Override
	public void createActionMap() throws Exception {
		this.addActionNew(1, "Nuevo Registro");
	}

	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		zLista.AddColumnaLista("business");
		zLista.AddColumnaLista("id_menu");
		zLista.AddColumnaLista("description");
	}

	public static GuiMenus getAvailablesMenus() throws Exception {
		GuiMenus oMenus = new GuiMenus();
//    oMenus.getRecords().addFilter("id_menu", zValue, "<>");
    oMenus.readAll();
    return oMenus;
	}

}
