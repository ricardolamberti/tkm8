package  pss.common.scheduler;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiProcesses extends JWins {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiProcesses() throws Exception {
  }

  @Override
	public Class<? extends JWin> GetClassWin() { return GuiProcess.class; }
  @Override
	public String GetTitle() throws Exception { return "Procesos"; }

  @Override
	public int GetNroIcono() throws Exception { return 5007; }

  @Override
	public void createActionMap() throws Exception {
    addActionNew     ( 1, "Nuevo Proceso" );
  }


  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("pid");
    zLista.AddColumnaLista("description");
  }

  @Override
	public String getDescripField() {
      return "Procesos";
  }



}
