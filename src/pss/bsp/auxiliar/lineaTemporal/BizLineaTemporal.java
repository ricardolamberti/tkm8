package  pss.bsp.auxiliar.lineaTemporal;

import java.util.Date;

import pss.common.customList.config.relation.JRelation;
import pss.common.customList.config.relation.JRelations;
import pss.common.event.mailing.BizMailingPersona;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JDateTools;
import pss.tourism.pnr.BizPNRTicket;

public class BizLineaTemporal extends JRecord {

  private JDate pFecha =  new JDate();
  private JString pCompany =  new JString();
	private JString pCustomerId = new JString();
	private JString pOfficeId = new JString();
	private JString pNroIata = new JString();
	private JString pVendedor = new JString();
	private JString pCentrocosto = new JString();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setFecha(Date zValue) throws Exception {    pFecha.setValue(zValue);  }
  public Date getFecha() throws Exception {     return pFecha.getValue();  }


  /**
   * Constructor de la Clase
   */
  public BizLineaTemporal() throws Exception {
  }
	

  public void createProperties() throws Exception {

    this.addItem( "company", pCompany );
    this.addItem( "fecha_serie", pFecha );
    this.addItem( "customer_id", pCustomerId );
    this.addItem( "office_id", pOfficeId );
    this.addItem( "nro_iata", pNroIata );
    this.addItem( "vendedor", pVendedor );
    this.addItem( "centro_costos", pCentrocosto );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "company", true, false, 18 );
    this.addFixedItem( KEY, "fecha_serie", "Fecha Serie", true, false, 18 );
    this.addFixedItem( VIRTUAL, "customer_id", "customer_id", true, false, 200 );
    this.addFixedItem( VIRTUAL, "office_id", "office_id", true, false, 200 );
    this.addFixedItem( VIRTUAL, "nro_iata", "nro_iata", true, false, 200 );
    this.addFixedItem( VIRTUAL, "vendedor", "vendedor", true, false, 200 );
    this.addFixedItem( VIRTUAL, "centro_costos", "centro_costos", true, false, 200 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "serie_temporal"; }
  
	@Override
	public String GetTableTemporal() throws Exception {
		return "(select '"+ BizUsuario.getUsr().getCompany()+"'::text as company, generate_series('"+(JDateTools.getAnioActual()-1)+"-01-01'::date  , '"+JDateTools.getAnioActual()+"-12-31'::date, '1 day'::interval) as fecha_serie) as "+GetTable();
//		return "generate_series(1, 365) as serie_temporal(fecha_serie)";
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void attachRelationMap(JRelations rels) throws Exception {
  	rels.setSourceWinsClass("pss.bsp.auxiliar.lineaTemporal.GuiLineaTemporales");
  	
    JRelation r=rels.addRelationParent(10, "PNRs", BizPNRTicket.class,"fecha_serie");
   	r.addJoin("serie_temporal.fecha_serie", "TUR_PNR_BOLETO.creation_date_date");
   	r.addJoin("serie_temporal.company", "TUR_PNR_BOLETO.company");
  	r.setTypeJoin(JRelations.JOIN_LEFT);

		
  	String s="7_10";
  	//rels.hideField("company");

  	rels.addFieldGroup("7", "fecha_serie", "*", "#Serie");

  	rels.addFieldGroup(s, "highfare", "*", "Remarks");
		rels.addFieldGroup(s, "lowfare", "*", "Remarks");
		rels.addFieldGroup(s, "corporativo", "*", "Remarks");
		rels.addFieldGroup(s, "proposito", "*", "Remarks");
		rels.addFieldGroup(s, "cuenta", "*", "Remarks");
		rels.addFieldGroup(s, "clase_tarifa", "*", "Remarks");
		rels.addFieldGroup(s, "autorizante", "*", "Remarks");
		rels.addFieldGroup(s, "fare_savings", "*", "Remarks");
		rels.addFieldGroup(s, "codigo_negocio", "*", "Remarks");
		rels.addFieldGroup(s, "region", "*", "Remarks");
		rels.addFieldGroup(s, "departamento", "*", "Remarks");
		rels.addFieldGroup(s, "solicitante", "*", "Remarks");
		rels.addFieldGroup(s, "reserva", "*", "Remarks");
		rels.addFieldGroup(s, "centro_costos", "*", "Remarks");
		
  	rels.addFieldGroup(s,"NumeroBoleto", "*", "Boleto");
  	rels.addFieldGroup(s,"CodigoPNR", "*", "Boleto");
  	rels.addFieldGroup(s,"nro_iata", "*", "Boleto");
  	rels.addFieldGroup(s,"dias_compra", "*", "Boleto");
  	rels.addFieldGroup(s,"dias_viajados", "*", "Boleto");
  	rels.addFieldGroup(s,"route", "*", "Boleto");
  	rels.addFieldGroup(s,"vendedor", "*", "Boleto");
  	rels.addFieldGroup(s,"centro_costos", "*", "Boleto");
  	rels.addFieldGroup(s,"observacion", "*", "Boleto");
  	rels.addFieldGroup(s,"gds", "*", "Boleto");
  	rels.addFieldGroup(s,"tipo_prestacion", "*", "Boleto");
		rels.addFieldGroup(s, "tipo_operacion", "*", "Boleto");
		rels.addFieldGroup(s, "boleto_original", "*", "Boleto");
		rels.addFieldGroup(s,"office_id", "*", "Boleto");
  	rels.addFieldGroup(s,"cliente", "*", "Boleto");
  	rels.addFieldGroup(s,"city_code", "*", "Boleto");
  	rels.addFieldGroup(s,"tipo_cambio", "*", "Boleto");
  	rels.addFieldGroup(s,"customer_id", "*", "Boleto");
  	rels.addFieldGroup(s,"TipoBoleto", "*", "Boleto");
  	rels.addFieldGroup(s,"codigo_aerolinea_intern", "*", "Boleto");
  	rels.addFieldGroup(s,"void", "*", "Boleto");
		rels.addFolderGroup("Tarifas", "Boleto");
		rels.addFolderGroup("Tarifa (Moneda PNR)", "Tarifas");
		rels.addFolderGroup("Tarifa (Moneda USD)", "Tarifas");
		rels.addFolderGroup("Tarifa (Moneda local)", "Tarifas");
		rels.addFolderGroup("Tarifa (Moneda Base)", "Tarifas");
		
		rels.addFieldGroup(s + "_90", "*", "*", "");
		rels.addFieldGroup(s + "_90", "descripcion", "*", "Boleto");
		rels.addFieldGroup(s + "_90", "mail", "*", "Boleto");

  	rels.addFieldGroup(s+"_21","*", "*", "Aerolínea");
  	rels.addFieldGroup(s+"_21","carrier", "*", "");
   	rels.addFieldGroup(s+"_22","*", "*", "");
    rels.addFieldGroup(s+"_70","*", "*", "");
  	rels.addFieldGroup(s+"_70_70","*", "*", "");
  	rels.addFieldGroup(s,"CodigoAerolinea", "*", "Aerolínea");
  	rels.addFieldGroup(s+"_70","id_group", "*", "Aerolínea");
  	rels.addFieldGroup(s+"_70_70","descripcion", "*", "Aerolínea");

  	rels.addFieldGroup(s,"codigo_moneda", "*", "Tarifa");
  	rels.addFieldGroup(s,"codigo_base_moneda", "*", "Tarifa");
		rels.addFieldGroup(s, "codigo_moneda", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "codigo_base_moneda", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "tarifa", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "pivatefareindicator", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "tarifa_yq", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "tarifa_facturada_yq", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "tarifa_usa", "*", "Tarifa (Moneda USD)");
		rels.addFieldGroup(s, "neto_usa", "*", "Tarifa (Moneda USD)");
		rels.addFieldGroup(s, "tarifa_facturada_yq_usa", "*", "Tarifa (Moneda USD)");

		rels.addFieldGroup(s, "tarifa_base_contax", "*", "Tarifa (Moneda Base)");
		rels.addFieldGroup(s, "tarifa_base", "*", "Tarifa (Moneda Base)");

		rels.addFieldGroup(s, "codigo_moneda_local", "*", "Tarifa (Moneda local)");
		rels.addFieldGroup(s, "tarifa_factura_total_local", "*", "Tarifa (Moneda local)");
		rels.addFieldGroup(s, "tarifa_factura_local", "*", "Tarifa (Moneda local)");
		rels.addFieldGroup(s, "neto_factura_local", "*", "Tarifa (Moneda local)");
		rels.addFieldGroup(s, "netoyq_local", "*", "Tarifa (Moneda local)");
		rels.addFieldGroup(s, "baseyq_local", "*", "Tarifa (Moneda local)");
		rels.addFieldGroup(s, "tarifa_local", "*", "Tarifa (Moneda local)");
		rels.addFieldGroup(s, "neto_local", "*", "Tarifa (Moneda local)");
		rels.addFieldGroup(s, "tarifa_facturada_yq_local", "*", "Tarifa (Moneda local)");

		rels.addFieldGroup(s, "neto", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "iva", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "tarifa_factura", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "neto_factura", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "iva_factura", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "tarifa_base_factura", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "tarifa_factura_total", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "tarifa_factura_total_usa", "*", "Tarifa (Moneda USD)");
		rels.addFieldGroup(s, "tarifa_factura_usa", "*", "Tarifa (Moneda USD)");
		rels.addFieldGroup(s, "neto_factura_usa", "*", "Tarifa (Moneda USD)");
		rels.addFieldGroup(s, "netoyq_usa", "*", "Tarifa (Moneda USD)");
		rels.addFieldGroup(s, "baseyq_usa", "*", "Tarifa (Moneda USD)");
		rels.addFieldGroup(s, "ahorro", "*", "Tarifa (Moneda PNR)");
  	rels.addFieldGroup(s,"nombre_pasajero", "*", "Pasajero");
  	rels.addFieldGroup(s,"NumeroPasajero", "*", "Pasajero");
  	rels.addFieldGroup(s,"tipo_pasajero", "*", "Pasajero");

  	rels.addFieldGroup(s,"comision_amount", "*", "Comisiones");
  	rels.addFieldGroup(s,"comision_perc", "*", "Comisiones");
  	rels.addFieldGroup(s,"importeover", "*", "Comisiones");
  	rels.addFieldGroup(s,"comision_factura", "*", "Comisiones");
  

  	rels.addFieldGroup(s+"_30","*", "*", "Aeropuerto Origen");
  	rels.addFieldGroup(s+"_30","geo_position", "*", "");
   	rels.addFieldGroup(s+"_30_80","*", "*", "");
  	rels.addFieldGroup(s+"_30_80_70","*", "*", "");
  	rels.addFieldGroup(s+"_32_80","*", "*", "");
  	rels.addFieldGroup(s+"_32_80_70","*", "*", "");
   	rels.addFieldGroup(s+"_30_60","*", "*", "");
  	rels.addFieldGroup(s+"_30_60","descripcion", "*", "Aeropuerto Origen");
  	rels.addFieldGroup(s+"_30_60","continente", "*", "Aeropuerto Origen");
  	rels.addFieldGroup(s+"_30_60","region", "*", "Aeropuerto Origen");
  	rels.addFieldGroup(s,"aeropuerto_origen", "*", "Aeropuerto Origen");

  	rels.addFieldGroup(s+"_32","*", "*", "Aeropuerto Destino");
  	rels.addFieldGroup(s+"_32","geo_position", "*", "");
  	rels.addFieldGroup(s+"_32_60","*", "*", "");
  	rels.addFieldGroup(s+"_32_60","descripcion", "*", "Aeropuerto Destino");
  	rels.addFieldGroup(s+"_32_60","continente", "*", "Aeropuerto Destino");
  	rels.addFieldGroup(s+"_32_60","region", "*", "Aeropuerto Destino");
  	rels.addFieldGroup(s,"aeropuerto_destino", "*", "Aeropuerto Destino");

  	rels.addFieldGroup(s,"monto_tarjeta", "*", "Tarjeta");
  	rels.addFieldGroup(s,"numero_tarjeta", "*", "Tarjeta");

  	rels.addFieldGroup(s+"_1","*", "*", "Impuestos");
  	rels.addFieldGroup(s+"_2","*", "*", "Seg.vuelos");
  	rels.addFieldGroup(s+"_4","*", "*", "Todos los Seg.vuelos");

  	rels.addFieldGroup(s+"_2","Despegue", "*", "Origen");
  	rels.addFieldGroup(s+"_2","FechaDespegue", "*", "Origen");
  	rels.addFieldGroup(s+"_2","HoraDespegue", "*", "Origen");
  	rels.addFieldGroup(s+"_2_51","*", "*", "Origen");
  	rels.addFieldGroup(s+"_2_51","geo_position", "*", "");
  	rels.addFieldGroup(s+"_2_51_60","*", "*", "");
  	rels.addFieldGroup(s+"_2_51_60","descripcion", "*", "Origen");
   	rels.addFieldGroup(s+"_2_51_60","continente", "*", "Origen");
   	rels.addFieldGroup(s+"_2_51_60","region", "*", "Origen");
   	
  	rels.addFieldGroup(s+"_2_70","*", "*", "");
  	rels.addFieldGroup(s+"_2_21","*", "*", "");
  	rels.addFieldGroup(s+"_2_70_70","*", "*", "");
  	rels.addFieldGroup(s+"_2","carrier", "*", "Seg.vuelos");
  	rels.addFieldGroup(s+"_2_70","id_group", "*", "Seg.vuelos");
  	rels.addFieldGroup(s+"_2_70_70","descripcion", "*", "Seg.vuelos");
  	
  	rels.addFieldGroup(s+"_2_51_80","*", "*", "");
  	rels.addFieldGroup(s+"_2_51_80_70","*", "*", "");
  	rels.addFieldGroup(s+"_2_51_80","id_group", "*", "Origen");
  	rels.addFieldGroup(s+"_2_51_80_70","descripcion", "*", "Origen");

  	rels.addFieldGroup(s+"_2_52_80","*", "*", "");
  	rels.addFieldGroup(s+"_2_52_80_70","*", "*", "");
  	rels.addFieldGroup(s+"_2_52_80","id_group", "*", "Destino");
  	rels.addFieldGroup(s+"_2_52_80_70","descripcion", "*", "Destino");

  	rels.addFieldGroup(s+"_2","Arrivo", "*", "Destino");
  	rels.addFieldGroup(s+"_2","FechaArrivo", "*", "Destino");
  	rels.addFieldGroup(s+"_2","HoraArrivo", "*", "Destino");
  	rels.addFieldGroup(s+"_2_52","*", "*", "Destino");
   	rels.addFieldGroup(s+"_2_52","geo_position", "*", "");
  	rels.addFieldGroup(s+"_2_52_60","*", "*", "");
  	rels.addFieldGroup(s+"_2_52_60","descripcion", "*", "Destino");
   	rels.addFieldGroup(s+"_2_52_60","continente", "*", "Destino");
   	rels.addFieldGroup(s+"_2_52_60","region", "*", "Destino");

  	rels.addFieldGroup(s+"_3","*", "*", "Seg.precios");
  	rels.addFieldGroup(s+"_3_70","*", "*", "");
  	rels.addFieldGroup(s+"_3_70_70","*", "*", "");
  	rels.addFieldGroup(s+"_3_70","id_group", "*", "Seg.precios");
  	rels.addFieldGroup(s+"_3_70_70","descripcion", "*", "Seg.precios");


  	rels.addFieldGroup(s+"_3_51_60","*", "*", "");
   	rels.addFieldGroup(s+"_3_51_80","*", "*", "");
  	rels.addFieldGroup(s+"_3_51_80_70","*", "*", "");
  	rels.addFieldGroup(s+"_3_52_60","*", "*", "");
  	rels.addFieldGroup(s+"_3_52_80","*", "*", "");
  	rels.addFieldGroup(s+"_3_52_80_70","*", "*", "");
   	rels.addFieldGroup(s+"_3_52","geo_position", "*", "");
   	
  	rels.addFieldGroup(s+"_3","Despegue", "*", "Origen Precio");
  	rels.addFieldGroup(s+"_3","FechaDespegue", "*", "Origen Precio");
  	rels.addFieldGroup(s+"_3","HoraDespegue", "*", "Origen Precio");
  	rels.addFieldGroup(s+"_3_51","*", "*", "Origen Precio");
  	rels.addFieldGroup(s+"_3_51","geo_position", "*", "");
  	rels.addFieldGroup(s+"_3_51_60","descripcion", "*", "Origen Precio");
   	rels.addFieldGroup(s+"_3_51_60","continente", "*", "Origen Precio");
   	rels.addFieldGroup(s+"_3_51_60","region", "*", "Origen Precio");
  	rels.addFieldGroup(s+"_3_51_80","id_group", "*", "Origen Precio");
  	rels.addFieldGroup(s+"_3_51_80_70","descripcion", "*", "Origen Precio");

  	rels.addFieldGroup(s+"_3","Arrivo", "*", "Destino Precio");
  	rels.addFieldGroup(s+"_3","FechaArrivo", "*", "Destino Precio");
  	rels.addFieldGroup(s+"_3","HoraArrivo", "*", "Destino Precio");
  	rels.addFieldGroup(s+"_3","carrier", "*", "Destino Precio");
  	rels.addFieldGroup(s+"_3_52","*", "*", "Destino Precio");
  	rels.addFieldGroup(s+"_3_52_60","descripcion", "*", "Destino Precio");
   	rels.addFieldGroup(s+"_3_52_60","continente", "*", "Destino Precio");
   	rels.addFieldGroup(s+"_3_52_60","region", "*", "Destino Precio");
  	rels.addFieldGroup(s+"_3_52_80","id_group", "*", "Destino Precio");
  	rels.addFieldGroup(s+"_3_52_80_70","descripcion", "*", "Destino Precio");
 
  	rels.addFieldGroup(s+"_38","*", "*", "");
  	rels.addFieldGroup(s+"_38_49","*", "*", "");
  	rels.addFieldGroup(s+"_38_49","descripcion", "*", "Aeropuerto Destino");//region
  	rels.addFieldGroup(s+"_2_54_49","*", "*", "");
  	rels.addFieldGroup(s+"_2_54","*", "*", "");
  	rels.addFieldGroup(s+"_2_54_49","descripcion", "*", "Destino");//region
  	rels.addFieldGroup(s+"_3_54_49","*", "*", "");
  	rels.addFieldGroup(s+"_3_54","*", "*", "");
  	rels.addFieldGroup(s+"_3_54_49","descripcion", "*", "Destino Precio");//region

  	rels.addFieldGroup(s+"_4","Despegue", "*", "Seg.Origen");
  	rels.addFieldGroup(s+"_4","FechaDespegue", "*", "Seg.Origen");
  	rels.addFieldGroup(s+"_4","HoraDespegue", "*", "Seg.Origen");
  	rels.addFieldGroup(s+"_4_51","*", "*", "Seg.Origen");
  	rels.addFieldGroup(s+"_4_51","geo_position", "*", "");
  	rels.addFieldGroup(s+"_4_51_60","*", "*", "");
  	rels.addFieldGroup(s+"_4_51_60","descripcion", "*", "Seg.Origen");
   	rels.addFieldGroup(s+"_4_51_60","continente", "*", "Seg.Origen");
   	rels.addFieldGroup(s+"_4_51_60","region", "*", "Seg.Origen");
   	
  	rels.addFieldGroup(s+"_4_70","*", "*", "");
  	rels.addFieldGroup(s+"_4_21","*", "*", "");
  	rels.addFieldGroup(s+"_4_70_70","*", "*", "");
  	rels.addFieldGroup(s+"_4","carrier", "*", "Todos los Seg.vuelos");
  	rels.addFieldGroup(s+"_4_70","id_group", "*", "Todos los Seg.vuelos");
  	rels.addFieldGroup(s+"_4_70_70","descripcion", "*", "Todos los Seg.vuelos");
 
  	rels.addFieldGroup(s+"_4_51_80","*", "*", "");
  	rels.addFieldGroup(s+"_4_51_80_70","*", "*", "");
  	rels.addFieldGroup(s+"_4_51_80","id_group", "*", "Seg.Origen");
  	rels.addFieldGroup(s+"_4_51_80_70","descripcion", "*", "Seg.Origen");

  	rels.addFieldGroup(s+"_4_52_80","*", "*", "");
  	rels.addFieldGroup(s+"_4_52_80_70","*", "*", "");
  	rels.addFieldGroup(s+"_4_52_80","id_group", "*", "Seg.Destino");
  	rels.addFieldGroup(s+"_4_52_80_70","descripcion", "*", "Seg.Destino");

  	rels.addFieldGroup(s+"_4","Arrivo", "*", "Seg.Destino");
  	rels.addFieldGroup(s+"_4","FechaArrivo", "*", "Seg.Destino");
  	rels.addFieldGroup(s+"_4","HoraArrivo", "*", "Seg.Destino");
  	rels.addFieldGroup(s+"_4_52","*", "*", "Seg.Destino");
   	rels.addFieldGroup(s+"_4_52","geo_position", "*", "");
  	rels.addFieldGroup(s+"_4_52_60","*", "*", "");
  	rels.addFieldGroup(s+"_4_52_60","descripcion", "*", "Seg.Destino");
   	rels.addFieldGroup(s+"_4_52_60","continente", "*", "Seg.Destino");
   	rels.addFieldGroup(s+"_4_52_60","region", "*", "Seg.Destino");

		rels.addCamposMailing(BizMailingPersona.CLIENTERMK, "codigo_cliente",BizPNRTicket.class.getName(),"0","=");
		rels.addCamposMailing(BizMailingPersona.CLIENTE, "customer_id",BizPNRTicket.class.getName(),"0","=");
		rels.addCamposMailing(BizMailingPersona.SUCURSAL, "office_id",BizPNRTicket.class.getName(),"0","=");
		rels.addCamposMailing(BizMailingPersona.IATA, "nro_iata",BizPNRTicket.class.getName(),"0","=");
		rels.addCamposMailing(BizMailingPersona.VENDEDOR, "vendedor",BizPNRTicket.class.getName(),"0","=");
		rels.addCamposMailing(BizMailingPersona.CCOSTO, "centro_costos",BizPNRTicket.class.getName(),"0","=");

//   	 r=rels.addRelationParent(20, "Publicidad", BizPublicity.class,"fecha_serie");
//   	r.addJoin("serie_temporal.fecha_serie", "BSP_PUBLICITY_DETAIL.fecha");
//   	r.addJoin("serie_temporal.company", "BSP_PUBLICITY_DETAIL.company");
//  	r.setTypeJoin(JRelations.JOIN_LEFT);

	}


	
}
