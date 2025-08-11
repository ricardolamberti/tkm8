package  pss.common.scheduler;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiProcessHosts extends JWins {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiProcessHosts() throws Exception {
  }

  @Override
	public Class<? extends JWin> GetClassWin() { return GuiProcessHost.class; }
  @Override
	public int GetNroIcono() throws Exception { return 5005; }
  @Override
	public String GetTitle() throws Exception { return "Servidores de Ejecución"; }

  @Override
	public void createActionMap() throws Exception {
    addActionNew     ( 1, "Nuevo Proceso");
  }

  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("host");
    zLista.AddColumnaLista("params");
    zLista.AddColumnaLista("desc_status");
  }


}
