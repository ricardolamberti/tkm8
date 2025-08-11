package  pss.common.regions.company;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiCompanyCountries extends JWins {

	public GuiCompanyCountries() throws Exception {
	}

  @Override
	public Class<? extends JWin> GetClassWin() { return GuiCompanyCountry.class; }

  
  @Override
	public String GetTitle() { return "Politicas Regionales"; }
  @Override
	public int GetNroIcono() { return 1; }

  @Override
	public void createActionMap() throws Exception {
    addActionNew ( 1, "Nuevo Pais" );
  }

  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
     zLista.AddIcono("");
     zLista.AddColumnaLista("descr_country");
  }
  
  
}
