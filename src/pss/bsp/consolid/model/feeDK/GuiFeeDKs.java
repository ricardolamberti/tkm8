package  pss.bsp.consolid.model.feeDK;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiFeeDKs extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiFeeDKs() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10036; } 
  public String  GetTitle()    throws Exception  { return "Fee"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiFeeDK.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nueva Fee DK" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("fecha_desde");
    zLista.AddColumnaLista("fecha_hasta");
    zLista.AddColumnaLista("priority");
    zLista.AddColumnaLista("aerolineas");
    zLista.AddColumnaLista("dom_int");
    zLista.AddColumnaLista("origen_pais");
    zLista.AddColumnaLista("origen_airport");
    zLista.AddColumnaLista("destino_pais");
    zLista.AddColumnaLista("destino_airport");
    zLista.AddColumnaLista("tipo_fee");
    zLista.AddColumnaLista("fee");

  }

}
