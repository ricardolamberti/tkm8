package pss.tourism.pnr;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiPNRSegmentoAereos extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiPNRSegmentoAereos() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 748; } 
  public String  GetTitle()    throws Exception  { return "Segmentos aereos"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiPNRSegmentoAereo.class; }
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
  	zLista.AddColumnaLista("#", "CodigoSegmento");
  	zLista.AddColumnaLista("emitido");
  	zLista.AddColumnaLista("Carrier");
   	zLista.AddColumnaLista("descr_carrier");
    zLista.AddColumnaLista("Vuelo", "NumeroVuelo");
  	
  	zLista.AddColumnaLista("Despegue");
  	zLista.AddColumnaLista("descr_despegue");
  	zLista.AddColumnaLista("Fecha Despegue", "FechaDespegue");
  	zLista.AddColumnaLista("Hora Despegue", "HoraDespegue");

  	zLista.AddColumnaLista("Arrivo");
  	zLista.AddColumnaLista("descr_arrivo");

  	zLista.AddColumnaLista("Fecha Arrivo", "FechaArrivo");
  	zLista.AddColumnaLista("Hora Arrivo", "HoraArrivo");
  	zLista.AddColumnaLista("Estado");
  	zLista.AddColumnaLista("Clase");
   	zLista.AddColumnaLista("tipo_clase");
    zLista.AddColumnaLista("CodigoComida");
  	zLista.AddColumnaLista("duracionvuelo");
  	zLista.AddColumnaLista("codigoentretenimiento");
  	zLista.AddColumnaLista("weight");
  	zLista.AddColumnaLista("fare_basic");
  	zLista.AddColumnaLista("fare_basic_ext");
  	zLista.AddColumnaLista("fare_family");
  	zLista.AddColumnaLista("weight");
  	zLista.AddColumnaLista("segmento_ini");
  	zLista.AddColumnaLista("monto");
  	zLista.AddColumnaLista("currency");
  	zLista.AddColumnaLista("monto_orig");

  	
  }

}
