package  pss.common.help;

import pss.common.security.BizUsuario;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiUserManuals extends JWins {


	static GuiUserManuals oUserManuals = null ; 
	/**
	 * Constructor de la Clase
	 */
	public GuiUserManuals() throws Exception {
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 10000;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Manuales de Usuario";
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiUserManual.class;
	}


	/**
	 * Configuro las columnas que quiero mostrar en la grilla
	 */
	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		zLista.AddColumnaLista("id");
	}
	

	public static  GuiUserManuals getManuales() throws Exception {
		if (oUserManuals != null) return oUserManuals;
		oUserManuals = new GuiUserManuals();
		JIterator<String> iter = BizUsuario.getUsr().getObjBusiness().getUserManuals().getKeyIterator();
		JMap<String, String>  userManuals = BizUsuario.getUsr().getObjBusiness().getUserManuals();
		while (iter.hasMoreElements()) {
			BizUserManual userManual = new BizUserManual();
			userManual.setId(iter.nextElement());
			userManual.setFile(userManuals.getElement(userManual.getId()));
			oUserManuals.getRecords().addItem(userManual);
		}
		oUserManuals.getRecords().setStatic(true);
		return oUserManuals;
	}
	
	
}
