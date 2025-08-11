package  pss.bsp.bo.arg.detalle;

import pss.core.win.JWin;
import pss.core.winUI.lists.JWinList;
import pss.core.win.JWins;

public class GuiArgDetalles extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiArgDetalles() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 2002; } 
  public String  GetTitle()    throws Exception  { return "Detalles Interfaz"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiArgDetalle.class; }
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
