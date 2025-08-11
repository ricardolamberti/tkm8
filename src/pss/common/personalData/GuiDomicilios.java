package  pss.common.personalData;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.winUI.lists.JWinList;

public class GuiDomicilios extends JWins {
	public static String VISION_INTERNATIONAL="VI";

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiDomicilios() throws Exception {
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 763;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Domicilios";
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiDomicilio.class;
	}

	@Override
	public void createActionMap() throws Exception {
		addActionNew(1, "Nuevo Domicilio");
	}

	@Override
	protected boolean checkActionOnDrop(BizAction a) throws Exception {
		return true;
	}
	
	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		if (zLista.isForExport()) {
			zLista.AddIcono("");
			zLista.AddColumnaLista("descr_domicilio");
			zLista.AddColumnaLista("ciudad");
			zLista.AddColumnaLista("descr_prov");
			zLista.AddColumnaLista("descr_pais");
		} else {
			zLista.addColumnWinAction("Flat", 1);
			zLista.setWithFooter(false);
			zLista.setWithHeader(false);
		}
//		zLista.AddColumnaLista("Legal", "is_legal");
//		zLista.AddColumnaLista("Comercial", "is_comercial");
//		zLista.AddColumnaLista("Particular", "is_particular");
//		zLista.AddColumnaLista("Listados", "is_listados");
	}
	
	@Override
	protected void configureList(JWinList list) throws Exception {
		list.setHideActions(true);
	}

//	@Override
//	public boolean ifAcceptRefreshAll(JWinEvent e) throws Exception {
//		return true;
//	}
}
