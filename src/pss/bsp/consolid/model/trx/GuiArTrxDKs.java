package pss.bsp.consolid.model.trx;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiArTrxDKs extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiArTrxDKs() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10003; } 
  public String  GetTitle()    throws Exception  { return "Trxs Consolid"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiArTrxDK.class; }
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
  	zLista.AddColumnaLista("transaccion_id");
  	zLista.AddColumnaLista("trx_line_id");
  	zLista.AddColumnaLista("trx_link_id");
  	zLista.AddColumnaLista("fecha_creacion");
  	zLista.AddColumnaLista("transaccion_num");
  	zLista.AddColumnaLista("imprimir");
  	zLista.AddColumnaLista("moneda");
  	zLista.AddColumnaLista("solicitante");
  	zLista.AddColumnaLista("forma_pago");
  	zLista.AddColumnaLista("tc_num");
  	zLista.AddColumnaLista("correo");
  	zLista.AddColumnaLista("tipo_cambio");
  	zLista.AddColumnaLista("unidad_operativa");
  	zLista.AddColumnaLista("fecha_cupon");
  	zLista.AddColumnaLista("finalizar");
  	zLista.AddColumnaLista("reserva_id");
  	zLista.AddColumnaLista("iata");
  	zLista.AddColumnaLista("gds");
  	zLista.AddColumnaLista("pcc_emite");
  	zLista.AddColumnaLista("agente_emite");
  	zLista.AddColumnaLista("archivo");
  	zLista.AddColumnaLista("organizacion");
  	zLista.AddColumnaLista("tipo_servicio");
  	zLista.AddColumnaLista("cantidad");
  	zLista.AddColumnaLista("precio_unitario");
  	zLista.AddColumnaLista("pasajero");
  	zLista.AddColumnaLista("ruta");
  	zLista.AddColumnaLista("aerolinea");
  	zLista.AddColumnaLista("boleto");
  	zLista.AddColumnaLista("fecha_hora_salida");
  	zLista.AddColumnaLista("clase");
  	zLista.AddColumnaLista("tarifa_base");
  	zLista.AddColumnaLista("iva");
  	zLista.AddColumnaLista("tua");
  	zLista.AddColumnaLista("monto");
  	zLista.AddColumnaLista("boleto_revisado");
  	zLista.AddColumnaLista("documento_tipo");
  	zLista.AddColumnaLista("fecha_hora_regreso");
  	zLista.AddColumnaLista("cd_origen");
  	zLista.AddColumnaLista("cd_destino");
  	zLista.AddColumnaLista("boleto_conjunto");
  	zLista.AddColumnaLista("tasa_iva");
  	zLista.AddColumnaLista("clas_iva");
  	zLista.AddColumnaLista("forma_pago_linea");
  	zLista.AddColumnaLista("tc_linea");
  	zLista.AddColumnaLista("dk");
  	zLista.AddColumnaLista("nombre_cliente");
  	zLista.AddColumnaLista("pcc_reserva");
  	zLista.AddColumnaLista("id_branch");
  	zLista.AddColumnaLista("comentarios");
  	zLista.AddColumnaLista("ultimaste");
  	zLista.AddColumnaLista("dk_agente");


  }
  
  @Override
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
   	zFiltros.addEditResponsive("trx idr", "transaccion_id").setOperator("=");
   	super.ConfigurarFiltros(zFiltros);
  }

}
