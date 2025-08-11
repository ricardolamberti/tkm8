package pss.bsp.regions;

import pss.core.win.JWin;
import pss.core.winUI.lists.JWinList;
import pss.core.win.JWins;

public class GuiRegiones extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiRegiones() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10032; } 
  public String  GetTitle()    throws Exception  { return "Region "; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiRegion.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nueva Region" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	zLista.AddIcono("");
    zLista.AddColumnaLista("descripcion");
  }

}
