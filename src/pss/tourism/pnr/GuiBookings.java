package pss.tourism.pnr;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiBookings extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiBookings() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 15011; } 
  public String  GetTitle()    throws Exception  { return "Bookings"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiBooking.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	zLista.AddIcono("");
  	zLista.AddColumnaLista("Boleto", "numeroboleto");
  	zLista.AddColumnaLista("#", "CodigoSegmento");
  	zLista.AddColumnaLista("Pasajero", "nombrepasajero");
  	zLista.AddColumnaLista("Carrier_placa");
  	zLista.AddColumnaLista("Carrier");
   	zLista.AddColumnaLista("descr_carrier");
    zLista.AddColumnaLista("Vuelo", "NumeroVuelo");
   	zLista.AddColumnaLista("vendedor");
  	
  	zLista.AddColumnaLista("Despegue");
  	zLista.AddColumnaLista("descr_despegue");
  	zLista.AddColumnaLista("Fecha emisión", "fecha_emision");
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
   	zLista.AddColumnaLista("monto_round");
  	zLista.AddColumnaLista("currency");
  	zLista.AddColumnaLista("monto_orig");
   	zLista.AddColumnaLista("monto_round_usa");
   	
   	zLista.AddColumnaLista("monto_factura");
   	zLista.AddColumnaLista("monto_round_factura");
   	zLista.AddColumnaLista("monto_orig_factura");
   	zLista.AddColumnaLista("monto_round_usa_factura");
   	
   	zLista.AddColumnaLista("roundtrip");
  	zLista.AddColumnaLista("mercado");
  }

	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
//		JFormControl oControl;
//		if (BizUsuario.getUsr().isAdminUser())
//			zFiltros.NuevoCombo("Agencia", "company", new GuiCompanies());
//
//	
//		JFormCDate d= zFiltros.NuevoCDate("Fecha desde", "FechaDespegue");
//		d.setOperator(">=");
//		d.setIdControl("fdesde");
//		d.SetValorDefault(JDateTools.getFirstDayOfMonth(new Date()));
//		d=	zFiltros.NuevoCDate("Fecha hasta", "FechaDespegue");
//		d.setOperator("<=");
//		d.setIdControl("fhasta");
//		d.SetValorDefault(JDateTools.getLastDayOfMonth(new Date()));
//		zFiltros.NuevoCombo("LA",  "carrier_placa",new GuiCarriers().addOrderAdHoc("description", "asc"));
//		zFiltros.NuevoEdit("Boleto", JObject.JSTRING, "NumeroBoleto");
//		zFiltros.NuevoEdit("Pasajero", JObject.JSTRING, "nombrepasajero", "ilike");
//
//		zFiltros.NuevoEdit("PNR", JObject.JSTRING, "CodigoPNR").setOperator("ilike");
//		zFiltros.NuevoEdit("Ruta", JObject.JSTRING, "ruta").setOperator("ilike");
//		zFiltros.NuevoEdit("Origen", JObject.JSTRING, "Origen").setOperator("ilike");
//		zFiltros.NuevoEdit("Destino", JObject.JSTRING, "Destino").setOperator("ilike");

		
	}

}
