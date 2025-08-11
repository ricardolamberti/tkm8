package pss.tourism.pnr;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiPNRFares  extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiPNRFares() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 15006; } 
  public String  GetTitle()    throws Exception  { return "Analisis de tarifa"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiPNRFare.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
 //   addActionNew( 1, "Nuevo Registro" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	zLista.AddIcono("");
  	zLista.AddColumnaLista("Cod.", "fare");
  	zLista.AddColumnaLista("airport_from");  	
  	zLista.AddColumnaLista("airport_to");  	
  	zLista.AddColumnaLista("ruta");  	
  	zLista.AddColumnaLista("impuesto");  	
  	zLista.AddColumnaLista("importe");  	
   	zLista.AddColumnaLista("importe_iv");  	
   	zLista.AddColumnaLista("importe_base");  	
    zLista.AddColumnaLista("emitido");  	
  }
  
 
}
