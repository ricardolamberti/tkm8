package  pss.bsp.publicity;

import pss.core.win.JWin;
import pss.core.winUI.lists.JWinList;
import pss.core.win.JWins;

public class GuiPublicities extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiPublicities() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 2002; } 
  public String  GetTitle()    throws Exception  { return "Detalles publicidad"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiPublicity.class; }
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
    zLista.AddColumnaLista("fecha");
    zLista.AddColumnaLista("campana");
    zLista.AddColumnaLista("segmento" );
    zLista.AddColumnaLista("clicks" );
      }

}
