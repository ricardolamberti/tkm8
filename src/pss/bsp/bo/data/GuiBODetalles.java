package  pss.bsp.bo.data;

import pss.core.win.JWin;
import pss.core.winUI.lists.JWinList;
import pss.core.win.JWins;

public class GuiBODetalles extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiBODetalles() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 2002; } 
  public String  GetTitle()    throws Exception  { return "Detalles Interfaz"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiBODetalle.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
   // addActionNew( 1, "Nuevo Registro" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddColumnaLista("tipo_ruta");
    zLista.AddColumnaLista("aerolinea");
    zLista.AddColumnaLista("operacion");
    zLista.AddColumnaLista("boleto" );
    zLista.AddColumnaLista("fecha" );
    zLista.AddColumnaLista("tarifa" );
    zLista.AddColumnaLista("contado" );
    zLista.AddColumnaLista("tarjeta" );
    zLista.AddColumnaLista("base_imponible");
    zLista.AddColumnaLista("impuesto1");
    zLista.AddColumnaLista("impuesto2" );
    zLista.AddColumnaLista("comision");
    zLista.AddColumnaLista("imp_sobre_com" );
    zLista.AddColumnaLista("comision_over" );
    zLista.AddColumnaLista("observaciones" );
    zLista.AddColumnaLista("numero_tarjeta");
    zLista.AddColumnaLista("tipo_tarjeta");
    zLista.AddColumnaLista("id_cliente" );
    zLista.AddColumnaLista("cliente" );
 
  }

}
