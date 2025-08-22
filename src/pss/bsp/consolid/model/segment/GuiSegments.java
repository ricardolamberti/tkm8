package pss.bsp.consolid.model.segment;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiSegments extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiSegments() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10003; } 
  public String  GetTitle()    throws Exception  { return "Segments Consolid"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiSegment.class; }
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
  	zLista.AddColumnaLista("id_segment");
  	zLista.AddColumnaLista("pnr_locator");
  	zLista.AddColumnaLista("product_code");
  	zLista.AddColumnaLista("departure_date");
  	zLista.AddColumnaLista("origin_city_code");
  	zLista.AddColumnaLista("origin_city_name");
  	zLista.AddColumnaLista("arrival_city_code");
  	zLista.AddColumnaLista("arrival_city_name");
  	zLista.AddColumnaLista("carrier_code");
  	zLista.AddColumnaLista("flight_number");
  	zLista.AddColumnaLista("class_of_service");
  	zLista.AddColumnaLista("departure_time");
  	zLista.AddColumnaLista("arrival_time");
  	zLista.AddColumnaLista("elapsed_flying_time");
  	zLista.AddColumnaLista("meal_service_indicator");
  	zLista.AddColumnaLista("supplemental_inf");
  	zLista.AddColumnaLista("flight_arr_date_chan_ind");
  	zLista.AddColumnaLista("stopover_city_codes");
  	zLista.AddColumnaLista("terminal_id_departure");
  	zLista.AddColumnaLista("gate_departure");
  	zLista.AddColumnaLista("terminal_id_arrival");
  	zLista.AddColumnaLista("arrival_gate");
  	zLista.AddColumnaLista("seat_number");
  	zLista.AddColumnaLista("secondary_product_code");
  	zLista.AddColumnaLista("confirmation_number");
  	zLista.AddColumnaLista("status");
  	zLista.AddColumnaLista("item_number");
  	zLista.AddColumnaLista("quantity");
  	zLista.AddColumnaLista("local_rate");
  	zLista.AddColumnaLista("local_currency");
  	zLista.AddColumnaLista("address");
  	zLista.AddColumnaLista("phone_information");
  	zLista.AddColumnaLista("v_a");
  	zLista.AddColumnaLista("v_b");
  	zLista.AddColumnaLista("v_c");
  	zLista.AddColumnaLista("v_d");
  	zLista.AddColumnaLista("v_e");
  	zLista.AddColumnaLista("v_f");
  	zLista.AddColumnaLista("v_g");
  	zLista.AddColumnaLista("v_h");
  	zLista.AddColumnaLista("v_i");
  	zLista.AddColumnaLista("v_j");
  	zLista.AddColumnaLista("v_k");
  	zLista.AddColumnaLista("v_l");
  	zLista.AddColumnaLista("v_m");
  	zLista.AddColumnaLista("v_n");
  	zLista.AddColumnaLista("v_o");
  	zLista.AddColumnaLista("v_p");
  	zLista.AddColumnaLista("v_q");
  	zLista.AddColumnaLista("v_r");
  	zLista.AddColumnaLista("v_s");
  	zLista.AddColumnaLista("v_t");
  	zLista.AddColumnaLista("v_u");
  	zLista.AddColumnaLista("v_v");
  	zLista.AddColumnaLista("v_w");
  	zLista.AddColumnaLista("v_x");
  	zLista.AddColumnaLista("v_y");
  	zLista.AddColumnaLista("v_z");
  	zLista.AddColumnaLista("baggage_allowance");
  	zLista.AddColumnaLista("fare_basis_code");
  	zLista.AddColumnaLista("fare_basis_code_exp");
  	zLista.AddColumnaLista("creation_date");
  	zLista.AddColumnaLista("last_update_date");
  	zLista.AddColumnaLista("arrival_date");
  	zLista.AddColumnaLista("miles");
  	zLista.AddColumnaLista("conexion");
  	zLista.AddColumnaLista("org_id");


  }
  
  @Override
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
   	zFiltros.addEditResponsive("Segment id", "transaccion_id").setOperator("=");
   	super.ConfigurarFiltros(zFiltros);
  }

}
