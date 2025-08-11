package pss.bsp.contrato.series;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiSeries extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiSeries() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 15010; } 
  public String  GetTitle()    throws Exception  { return "Series"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiSerie.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo Registro" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    super.ConfigurarColumnasLista(zLista);
  }

}
