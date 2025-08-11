package  pss.bsp.config.airportGroups;

import pss.core.services.records.JRecords;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.submits.JAct;
import pss.core.winUI.lists.JWinList;
import pss.core.win.JWins;
import pss.tourism.carrier.GuiCarrier;
import pss.tourism.carrier.GuiCarriers;

public class GuiAirportGroups extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiAirportGroups() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10032; } 
  public String  GetTitle()    throws Exception  { return "Grupo Aeropuertos"; }
  public Class<? extends JWin>  GetClassWin()    { return GuiAirportGroup.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo Grupo" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("descripcion");
  }

}
