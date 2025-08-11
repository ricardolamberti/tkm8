package pss.www.platform.users.stadistics;

import pss.core.win.JWin;
import pss.core.win.JWins;

public class GuiStadisitcss extends JWins {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiStadisitcss() throws Exception {
  }
  @Override
	public Class<? extends JWin> GetClassWin() { return GuiStadistics.class; }
  @Override
	public int GetNroIcono() throws Exception { return 745; }
  @Override
	public String GetTitle() throws Exception { return "Estadisticas Online"; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
  }

  @Override
  public boolean canConvertToURL() throws Exception {
  	return false;
  }


}
