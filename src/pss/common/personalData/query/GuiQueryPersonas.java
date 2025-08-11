package pss.common.personalData.query;

import pss.common.regions.divitions.GuiProvincias;
import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.lists.JWinList;

public class GuiQueryPersonas extends JWins {
	
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiQueryPersonas() throws Exception {
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 5078;
	}
	
	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiQueryPersona.class;
	}

	@Override
	public void createActionMap() throws Exception {
	}

	
	@Override
	public boolean needAutocomplete(JWinList zLista) throws Exception {
		return false;
	}
	
	 @Override
	public boolean isForceHideActions() {
		return true;
	}

	public JWins getProvincias(JControlCombo combo) throws Exception {
		GuiProvincias p = new GuiProvincias();
		p.getRecords().addFilter("pais", BizUsuario.getUsr().getCountry());
		return p;
	}

	@Override
	public long selectSupraCount() throws Exception {
		return -1;
	}
	
}
