package  pss.bsp.hoteles.detalle;

import pss.core.win.JWin;
import pss.core.winUI.lists.JWinList;
import pss.core.win.JWins;

public class GuiHotelDetails extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiHotelDetails() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10032; } 
  public String  GetTitle()    throws Exception  { return "Detalle grupo hotel"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiHotelDetail.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo detalle" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	zLista.AddIcono("");
    zLista.AddColumnaLista("descripcion");
  }

}
