package  pss.bsp.familia.detalle;

import pss.core.win.JWin;
import pss.core.winUI.lists.JWinList;
import pss.core.win.JWins;

public class GuiFamiliaDetails extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiFamiliaDetails() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10032; } 
  public String  GetTitle()    throws Exception  { return "Familia-aerolineas"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiFamiliaDetail.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nueva Aerolínea" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	zLista.AddIcono("");
    zLista.AddColumnaLista("letra");
    zLista.AddColumnaLista("descripcion");
  }

}
