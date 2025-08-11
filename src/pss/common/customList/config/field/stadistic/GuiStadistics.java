package  pss.common.customList.config.field.stadistic;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiStadistics extends JWins {


  /**
   * Constructor de la Clase
   */
  public GuiStadistics() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10064; } 
  public String  GetTitle()    throws Exception  { return "Estadísticas"; }
  public Class<? extends JWin>  GetClassWin()    { return GuiSatdistic.class; }

  @Override
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	zLista.AddColumnaLista("campo");
  	super.ConfigurarColumnasLista(zLista);
  }
}
