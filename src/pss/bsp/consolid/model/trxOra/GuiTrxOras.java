package pss.bsp.consolid.model.trxOra;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiTrxOras extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiTrxOras() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10003; } 
  public String  GetTitle()    throws Exception  { return "Trxs Oracle"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiTrxOra.class; }
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
  	zLista.AddColumnaLista("periodo_emi");
  	zLista.AddColumnaLista("periodo_fac");
  	zLista.AddColumnaLista("fecha_factura");
  	zLista.AddColumnaLista("org_id");
  	zLista.AddColumnaLista("org");
  	zLista.AddColumnaLista("tipo_servicio");
  	zLista.AddColumnaLista("tipo_documento");
  	zLista.AddColumnaLista("factura");
  	zLista.AddColumnaLista("moneda");
  	zLista.AddColumnaLista("solicito");
  	zLista.AddColumnaLista("forma_pago");
  	zLista.AddColumnaLista("tipo_cambio");
  	zLista.AddColumnaLista("unidad_operativa");
  	zLista.AddColumnaLista("fecha_creacion_pnr");
  	zLista.AddColumnaLista("pnr");
  	zLista.AddColumnaLista("iata");
  	zLista.AddColumnaLista("gds");
  	zLista.AddColumnaLista("pcc_emite");
  	zLista.AddColumnaLista("agente_emite");
  	zLista.AddColumnaLista("servicio");
  	zLista.AddColumnaLista("precio_unitario");
  	zLista.AddColumnaLista("importe");
  	zLista.AddColumnaLista("total_factura");
  	zLista.AddColumnaLista("pasajero");
  	zLista.AddColumnaLista("ruta");
  	zLista.AddColumnaLista("linea_aerea");
  	zLista.AddColumnaLista("boleto");
  	zLista.AddColumnaLista("fecha_salida");
  	zLista.AddColumnaLista("fecha_regreso");
  	zLista.AddColumnaLista("clase");
  	zLista.AddColumnaLista("tarifa");
  	zLista.AddColumnaLista("iva");
  	zLista.AddColumnaLista("tua");
  	zLista.AddColumnaLista("total");
  	zLista.AddColumnaLista("boleto_revisado");
  	zLista.AddColumnaLista("boleto_conjunto");
  	zLista.AddColumnaLista("ciudad_origen");
  	zLista.AddColumnaLista("ciudad_destino");
  	zLista.AddColumnaLista("proveedor");
  	zLista.AddColumnaLista("cliente");
  	zLista.AddColumnaLista("dk");
  	zLista.AddColumnaLista("id_cliente");
  	zLista.AddColumnaLista("termino_pago");
  	zLista.AddColumnaLista("cci");
  	zLista.AddColumnaLista("elaborado_por");
  	zLista.AddColumnaLista("descripcion");
  	zLista.AddColumnaLista("fecha_emision");
  	zLista.AddColumnaLista("creacion_factura");
  	zLista.AddColumnaLista("archivo_origen");
  	zLista.AddColumnaLista("referencia");
  	zLista.AddColumnaLista("global_attribute19");
  	zLista.AddColumnaLista("global_attribute6");

  	


  }
  
  @Override
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
   	zFiltros.addEditResponsive("Factura", "factura").setOperator("=");
   	super.ConfigurarFiltros(zFiltros);
  }
  @Override
  public long selectSupraCount() throws Exception {
   	return -1;
  }
}
