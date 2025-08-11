package pss.bsp.consolid.model.pnr;

import pss.core.win.JWin;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;
import pss.core.win.JWins;

public class GuiContentPnrs extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiContentPnrs() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10003; } 
  public String  GetTitle()    throws Exception  { return "Trxs Consolid"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiContentPnr.class; }
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
  	zLista.AddColumnaLista("id_ctscontent_pnr");
  	zLista.AddColumnaLista("name_rec");
  	zLista.AddColumnaLista("date_emision");
  	zLista.AddColumnaLista("time_emision");
  	zLista.AddColumnaLista("customer_number");
  	zLista.AddColumnaLista("customer_name");
  	zLista.AddColumnaLista("iata");
  	zLista.AddColumnaLista("pnr_locator");
  	zLista.AddColumnaLista("customer_branch");
  	zLista.AddColumnaLista("date_rec");
  	zLista.AddColumnaLista("time_rec");
  	zLista.AddColumnaLista("invoicing_agency_city_code");
  	zLista.AddColumnaLista("invoicing_agent_duty_code");
  	zLista.AddColumnaLista("agency_policy");
  	zLista.AddColumnaLista("telephone_number");
  	zLista.AddColumnaLista("telephone_number2");
  	zLista.AddColumnaLista("telephone_number3");
  	zLista.AddColumnaLista("agente_reserva_pnr");
  	zLista.AddColumnaLista("agente_emite_pnr");
  	zLista.AddColumnaLista("passenger_man_number");
  	zLista.AddColumnaLista("frequent_traveler_number");
  	zLista.AddColumnaLista("fare_currency_code");
  	zLista.AddColumnaLista("fare_amount");
  	zLista.AddColumnaLista("tax_1_id");
  	zLista.AddColumnaLista("tax_2_id");
  	zLista.AddColumnaLista("tax_3_id");
  	zLista.AddColumnaLista("total_fae_sign");
  	zLista.AddColumnaLista("commission_percentage");
  	zLista.AddColumnaLista("commission_sign");
  	zLista.AddColumnaLista("commission_amount");
  	zLista.AddColumnaLista("net_amount");
  	zLista.AddColumnaLista("type_of_payment");
  	zLista.AddColumnaLista("original_issue");
  	zLista.AddColumnaLista("credit_card_number");
  	zLista.AddColumnaLista("code_aut");
  	zLista.AddColumnaLista("canceled_ticket");
  	zLista.AddColumnaLista("carrier_code_m5");
  	zLista.AddColumnaLista("ticket_number_m5");
  	zLista.AddColumnaLista("commission_amount_m5");
  	zLista.AddColumnaLista("base_fare_amount_m5");
  	zLista.AddColumnaLista("tax_1_amount_m5");
  	zLista.AddColumnaLista("tax_2_amount_m5");
  	zLista.AddColumnaLista("tax_3_amount_m5");
  	zLista.AddColumnaLista("form_payment_m5");
  	zLista.AddColumnaLista("passenger_name_m5");
  	zLista.AddColumnaLista("conjunction_documents");
  	zLista.AddColumnaLista("rout_indicator");
  	zLista.AddColumnaLista("ticket_number_ex");
  	zLista.AddColumnaLista("exchanged_coupons");
  	zLista.AddColumnaLista("type_invoice");
  	zLista.AddColumnaLista("creation_date");
  	zLista.AddColumnaLista("last_update_date");
  	zLista.AddColumnaLista("error");
  	zLista.AddColumnaLista("customer_trx_id");
  	zLista.AddColumnaLista("origin_gds");
  	zLista.AddColumnaLista("status_flag");
  	zLista.AddColumnaLista("item_number");
  	zLista.AddColumnaLista("exchange_rate");
  	zLista.AddColumnaLista("hist");
  	zLista.AddColumnaLista("service_charge");
  	zLista.AddColumnaLista("service_charge_name");
  	zLista.AddColumnaLista("customer_trx_id_cs");
  	zLista.AddColumnaLista("trx_number_cs");
  	zLista.AddColumnaLista("trx_number");
  	zLista.AddColumnaLista("customer_address");
  	zLista.AddColumnaLista("customer_rfc");
  	zLista.AddColumnaLista("trx_number_npd");
  	zLista.AddColumnaLista("customer_trx_id_npd");
  	zLista.AddColumnaLista("trx_number_nc");
  	zLista.AddColumnaLista("customer_trx_id_nc");
  	zLista.AddColumnaLista("item_pax");
  	zLista.AddColumnaLista("customer_trx_id_npd_cs");
  	zLista.AddColumnaLista("trx_number_npd_cs");
  	zLista.AddColumnaLista("flag_npd");
  	zLista.AddColumnaLista("control");
  	zLista.AddColumnaLista("trx_number_nd");
  	zLista.AddColumnaLista("customer_trx_id_nd");
  	zLista.AddColumnaLista("credit_card_number_cs");
  	zLista.AddColumnaLista("type_of_payment_cs");
  	zLista.AddColumnaLista("form_payment_m5_cs");
  	zLista.AddColumnaLista("comments");
  	zLista.AddColumnaLista("org_id");
  	zLista.AddColumnaLista("invoice_number");
  	zLista.AddColumnaLista("name_rec_ext");
  	zLista.AddColumnaLista("pseudo_reserva");

  }
  
  @Override
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
   	zFiltros.addEditResponsive("trx idr", "transaccion_id").setOperator("=");
   	super.ConfigurarFiltros(zFiltros);
  }

}
