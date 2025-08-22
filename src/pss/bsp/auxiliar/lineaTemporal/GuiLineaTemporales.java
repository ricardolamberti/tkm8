package  pss.bsp.auxiliar.lineaTemporal;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiLineaTemporales extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiLineaTemporales() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 2002; } 
  public String  GetTitle()    throws Exception  { return "Momento"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiLineaTemporal.class; }
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
    zLista.AddColumnaLista("fecha_serie");
   }

}
