package pss.tourism.pnr;

import java.util.Date;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.bspBusiness.GuiBSPCompany;
import pss.bsp.contrato.detalle.GuiDetalles;
import pss.bsp.monitor.log.BizBspLog;
import pss.common.regions.company.GuiCompanies;
import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.BizUsuario;
import pss.core.JAplicacion;
import pss.core.services.fields.JObject;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.win.totalizer.JTotalizer;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JGrupoColumnaLista;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.responsiveControls.JFormIntervalCDatetimeResponsive;
import pss.tourism.carrier.GuiCarriers;

public class GuiPNRTickets extends JWins {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiPNRTickets() throws Exception {
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiPNRTicket.class;
	}

  @Override
  public int GetNroIcono() throws Exception {
  	return 5129;
  }


	@Override
	public String GetTitle() throws Exception {
		return "Boletos";
	}


	// -------------------------------------------------------------------------//
	// Mapeo las acciones con las operaciones
	// -------------------------------------------------------------------------//
	@Override
	public void createActionMap() throws Exception {
		// addActionNew ( 1, "Nuevo Registro" );
		addAction(12, "Reprocesar todo", null, 17, true, true);
		addAction(14, "Reprocesar año", null, 17, true, true);
		this.addAction(250,"Calcule Over", null, 15013, true, true);
 }

  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==12) return new JActSubmit(this, a.getId()) {
  		public void submit() throws Exception {
  			execReprocesarTodo(false);
  		}
  	};
  	if (a.getId()==14) return new JActSubmit(this, a.getId()) {
  		public void submit() throws Exception {
  			execReprocesarTodo(true);
  		}
  	};
   	if (a.getId()==250 ) return new JActSubmit(this) {
  		@Override
  		public void submit() throws Exception {
  			new BizPNRTicket().execCalculeOver(BizUsuario.getUsr().getCompany(), JDateTools.getFirstDayOfYear(new Date()), JDateTools.getLastDayOfYear(new Date()));
  		}
  	};		
  	return super.getSubmitFor(a);
  }
  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getId()==12) return BizUsuario.IsAdminCompanyUser();
  	if (a.getId()==14) return BizUsuario.IsAdminCompanyUser();
		if (a.getId()==250) return GetVision().equals("OVER");
  	return super.OkAction(a);
  }
	// -------------------------------------------------------------------------//
	// Configuro las columnas que quiero mostrar en la grilla
	// -------------------------------------------------------------------------//
	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		if (GetVision().equals("UPFRONT"))
			ConfigurarColumnasListaUpfront(zLista);
		else if (GetVision().equals("OVER"))
			ConfigurarColumnasListaOver(zLista);
		else
			ConfigurarColumnasListaDefault(zLista);
	}
	
	public void ConfigurarColumnasListaDefault(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		JGrupoColumnaLista datos=zLista.AddGrupoColumnaLista("Datos boleto");
		JGrupoColumnaLista factura=zLista.AddGrupoColumnaLista("Facturacion");
		JGrupoColumnaLista totales=zLista.AddGrupoColumnaLista("Acumulado con reemisiones");
		JGrupoColumnaLista dolares=zLista.AddGrupoColumnaLista("Dolares");
		JGrupoColumnaLista comision=zLista.AddGrupoColumnaLista("Comisiones");
		JGrupoColumnaLista tarjeta=zLista.AddGrupoColumnaLista("Tarjetas");
		JGrupoColumnaLista otros=zLista.AddGrupoColumnaLista("Otros");
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()|| (BizUsuario.getUsr().isAdminUser()))
			zLista.AddColumnaLista("company").setGrupo(datos);
		zLista.AddColumnaLista("CodigoPNR").setGrupo(datos);
		zLista.AddColumnaLista("tipo_operacion").setGrupo(datos);
		zLista.AddColumnaLista("NumeroBoleto").setGrupo(datos);
		zLista.AddColumnaLista("LA", "CodigoAerolinea").setGrupo(datos);
		zLista.AddColumnaLista("office_id").setGrupo(datos);
		zLista.AddColumnaLista("customer_id_reducido").setGrupo(datos);
		zLista.AddColumnaLista("nombre_pasajero").setGrupo(datos);
		
		zLista.AddColumnaLista("creation_date").setGrupo(datos);
		zLista.AddColumnaLista("creation_date_date").setGrupo(datos);
		
		zLista.AddColumnaLista("departure_date").setGrupo(datos);
		if (BizUsuario.getUsr().IsAdminCompanyUser())
			zLista.AddColumnaLista("fecha_proc").setGrupo(datos);
		
		zLista.AddColumnaLista("neto_factura_local").setGrupo(factura);
		if (GetVision().toUpperCase().indexOf("YQ")==-1)
			zLista.AddColumnaLista("tarifa_factura_local").setGrupo(factura);
		else
		 zLista.AddColumnaLista("tarifa_facturada_yq_local").setGrupo(factura);
		
		zLista.AddColumnaLista("neto_factura_local").setGrupo(factura);
		if (GetVision().toUpperCase().indexOf("YQ")==-1)
			zLista.AddColumnaLista("tarifa_factura_local").setGrupo(factura);
		else
		 zLista.AddColumnaLista("tarifa_facturada_yq").setGrupo(factura);
		zLista.AddColumnaLista("impuestos_total_factura").setGrupo(factura);
//		zLista.AddColumnaLista("iva_factura").setGrupo(factura);
		zLista.AddColumnaLista("tarifa_base_factura").setGrupo(factura);
		zLista.AddColumnaLista("comision_factura").setGrupo(factura);
		zLista.AddColumnaLista("tarifa_factura_total").setGrupo(factura);

		zLista.AddColumnaLista("neto_local").setGrupo(totales);
		zLista.AddColumnaLista("tarifa_local").setGrupo(totales);
		zLista.AddColumnaLista("impuestos_total").setGrupo(totales);
//		zLista.AddColumnaLista("iva").setGrupo(totales);
		zLista.AddColumnaLista("tarifa_base_contax").setGrupo(totales);
		if (GetVision().toUpperCase().indexOf("YQ")==-1)
			zLista.AddColumnaLista("tarifa_base").setGrupo(totales);
		else
			zLista.AddColumnaLista("tarifa_yq").setGrupo(totales);
		
		zLista.AddColumnaLista("tarifa_usa").setGrupo(dolares);
		zLista.AddColumnaLista("neto_usa").setGrupo(dolares);
		zLista.AddColumnaLista("neto_factura_usa").setGrupo(dolares);
		zLista.AddColumnaLista("tarifa_factura_usa").setGrupo(dolares);
		zLista.AddColumnaLista("tarifa_factura_total_usa").setGrupo(dolares);
		zLista.AddColumnaLista("baseyq_usa").setGrupo(dolares);
				
		
		zLista.AddColumnaLista("nombre_tarjeta").setGrupo(tarjeta);
		zLista.AddColumnaLista("numero_tarjeta_mask").setGrupo(tarjeta);
		zLista.AddColumnaLista("monto_tarjeta").setGrupo(tarjeta);
//		zLista.AddColumnaLista("I", "is_internacional");
		zLista.AddColumnaLista("Ruta", "route").setGrupo(otros);
		zLista.AddColumnaLista("Tour code", "tour_code").setGrupo(otros);
		zLista.AddColumnaLista("Fam.Tarif", "fare_intinerary").setGrupo(otros);
		
		zLista.AddColumnaLista("Clase", "clase").setGrupo(otros);
	 	zLista.AddColumnaLista("Origen", "aeropuerto_origen").setGrupo(otros);	
		zLista.AddColumnaLista("Destino", "aeropuerto_destino").setGrupo(otros);		
		zLista.AddColumnaLista("Vendedor", "vendedor").setGrupo(otros);
		zLista.AddColumnaLista("Cliente RMK", "codigo_cliente").setGrupo(otros);
		zLista.AddColumnaLista("Cliente", "customer_id").setGrupo(otros);
		zLista.AddColumnaLista("DK", "customer_id_reducido").setGrupo(otros);
		zLista.AddColumnaLista("GDS", "gds").setGrupo(otros);
		zLista.AddColumnaLista("void").setGrupo(otros);
		zLista.AddColumnaLista("reemitted").setGrupo(otros);
		zLista.AddColumnaLista("endoso").setGrupo(otros);
		zLista.AddColumnaLista("comision_perc").setGrupo(comision);
		zLista.AddColumnaLista("comision_amount").setGrupo(comision);
		zLista.AddColumnaLista("oversobre").setGrupo(comision);
		zLista.AddColumnaLista("porcentajeover").setGrupo(comision);
		zLista.AddColumnaLista("importeover").setGrupo(comision);
		zLista.AddColumnaLista("ivaover").setGrupo(comision);
		zLista.AddColumnaLista("porcentaje_prorr_over").setGrupo(comision);
		zLista.AddColumnaLista("importe_prorr_over").setGrupo(comision);
		zLista.AddColumnaLista("upfront_descripcion").setGrupo(comision);
		zLista.AddColumnaLista("comision_perc_back").setGrupo(comision);
		zLista.AddColumnaLista("comision_over_back").setGrupo(comision);
		zLista.AddColumnaLista("backend_descripcion").setGrupo(comision);
	}
	
	public void ConfigurarColumnasListaOver(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		JGrupoColumnaLista datos=zLista.AddGrupoColumnaLista("Datos boleto");
		JGrupoColumnaLista factura=zLista.AddGrupoColumnaLista("Facturacion");
		JGrupoColumnaLista comision=zLista.AddGrupoColumnaLista("Comisiones");
		JGrupoColumnaLista otros=zLista.AddGrupoColumnaLista("Otros");
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()|| (BizUsuario.getUsr().isAdminUser()))
			zLista.AddColumnaLista("company").setGrupo(datos);
		zLista.AddColumnaLista("CodigoPNR").setGrupo(datos);
		zLista.AddColumnaLista("tipo_operacion").setGrupo(datos);
		zLista.AddColumnaLista("NumeroBoleto").setGrupo(datos);
		zLista.AddColumnaLista("LA", "CodigoAerolinea").setGrupo(datos);
		zLista.AddColumnaLista("nombre_pasajero").setGrupo(datos);
		
		zLista.AddColumnaLista("creation_date").setGrupo(datos);
		zLista.AddColumnaLista("departure_date").setGrupo(datos);
		if (BizUsuario.getUsr().IsAdminCompanyUser())
			zLista.AddColumnaLista("fecha_proc").setGrupo(datos);
		
		zLista.AddColumnaLista("neto_factura_local").setGrupo(factura);
		if (GetVision().toUpperCase().indexOf("YQ")==-1)
			zLista.AddColumnaLista("tarifa_factura_local").setGrupo(factura);
		else
		 zLista.AddColumnaLista("tarifa_facturada_yq_local").setGrupo(factura);
		
		zLista.AddColumnaLista("neto_factura").setGrupo(factura);
		if (GetVision().toUpperCase().indexOf("YQ")==-1)
			zLista.AddColumnaLista("tarifa_factura").setGrupo(factura);
		else
		 zLista.AddColumnaLista("tarifa_facturada_yq").setGrupo(factura);
		zLista.AddColumnaLista("impuestos_total_factura").setGrupo(factura);
		zLista.AddColumnaLista("tarifa_base_factura").setGrupo(factura);
		zLista.AddColumnaLista("tarifa_factura_total").setGrupo(factura);

		zLista.AddColumnaLista("DK", "customer_id_reducido").setGrupo(comision);
		zLista.AddColumnaLista("comision_perc").setGrupo(comision);
		zLista.AddColumnaLista("comision_amount").setGrupo(comision);
		zLista.AddColumnaLista("oversobre").setGrupo(comision);
		zLista.AddColumnaLista("porcentajeover").setGrupo(comision);
		zLista.AddColumnaLista("importeover").setGrupo(comision);
		zLista.AddColumnaLista("ivaover").setGrupo(comision);
		zLista.AddColumnaLista("porcentaje_prorr_over").setGrupo(comision);
		zLista.AddColumnaLista("importe_prorr_over").setGrupo(comision);
		zLista.AddColumnaLista("upfront_descripcion").setGrupo(comision);
		zLista.AddColumnaLista("comision_perc_back").setGrupo(comision);
		zLista.AddColumnaLista("comision_over_back").setGrupo(comision);
		zLista.AddColumnaLista("backend_descripcion").setGrupo(comision);

//		zLista.AddColumnaLista("I", "is_internacional");
		zLista.AddColumnaLista("Ruta", "route").setGrupo(otros);
		zLista.AddColumnaLista("Tour code", "tour_code").setGrupo(otros);
		zLista.AddColumnaLista("Origen", "aeropuerto_origen").setGrupo(otros);	
		zLista.AddColumnaLista("Destino", "aeropuerto_destino").setGrupo(otros);		
		zLista.AddColumnaLista("GDS", "gds").setGrupo(otros);
		zLista.AddColumnaLista("void").setGrupo(otros);
		zLista.AddColumnaLista("reemitted").setGrupo(otros);
		zLista.AddColumnaLista("endoso").setGrupo(otros);

	}
	public void ConfigurarColumnasListaUpfront(JWinList zLista) throws Exception {
		zLista.AddIcono("");
	 	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()|| (BizUsuario.getUsr().isAdminUser()))
			zLista.AddColumnaLista("company");
  	zLista.AddColumnaLista("MES","month");
  	zLista.AddColumnaLista("DI","di");
  	zLista.AddColumnaLista("NO_BOLETO","NumeroBoleto");
  	zLista.AddColumnaLista("PNR","CodigoPNR");
  	zLista.AddColumnaLista("NUMERO","oracle_numero");
  	zLista.AddColumnaLista("SERIE","oracle_serie");
  	zLista.AddColumnaLista("FECHAHOT","creation_date");
		zLista.AddColumnaLista("SALIDA","departure_date");
		zLista.AddColumnaLista("REGRESO","arrive_date");
		zLista.AddColumnaLista("DK", "customer_id_reducido");
		zLista.AddColumnaLista("AIR", "CodigoAerolinea");
		zLista.AddColumnaLista("FAREBASIS","fare_intinerary");
		zLista.AddColumnaLista("T_BASE","tarifa_factura_local");
		zLista.AddColumnaLista("IMPTOS","impuestos_total_factura");
		zLista.AddColumnaLista("TOTAL","tarifa_factura_total");
		zLista.AddColumnaLista("IATA","nro_iata");
		zLista.AddColumnaLista("PCT","comision_perc");
		zLista.AddColumnaLista("COMISION","comision_amount");
		zLista.AddColumnaLista("A_CLASE","class_intinerary");
		zLista.AddColumnaLista("LINEAS","air_intinerary");
		zLista.AddColumnaLista("RUTA", "route");
		zLista.AddColumnaLista("ORIGEN", "aeropuerto_origen");	
		zLista.AddColumnaLista("DESTINO", "aeropuerto_destino");		
		zLista.AddColumnaLista("TIPOHOT","bsp_tipo_operacion");
		zLista.AddColumnaLista("ANO","year");
		zLista.AddColumnaLista("TOURCODE","tour_code");
		zLista.AddColumnaLista("A.NOVUELO","nro_flight_intinerary");
		zLista.AddColumnaLista("Nº AL BSP","bsp_codecarrier");
		zLista.AddColumnaLista("AL BSP","bsp_carrier");
		zLista.AddColumnaLista("IATA BSP","bsp_iata");
		zLista.AddColumnaLista("TTL TKT","ttl");
		zLista.AddColumnaLista("MERCADO ORIGEN","mercado_origen");
		zLista.AddColumnaLista("MERCADO DESTINO","mercado_destino");
		zLista.AddColumnaLista("MERCADO","market_intinerary");
		zLista.AddColumnaLista("LA BSP-2","bsp_carrier2");
		zLista.AddColumnaLista("TIPO DE DOCUMENTO","tipo_operacion");
		zLista.AddColumnaLista("% COM STD","comision_perc");
		zLista.AddColumnaLista("$ COM STD","comision_amount");
		zLista.AddColumnaLista("% COM BSP","bsp_porc");
		zLista.AddColumnaLista("$ COM BSP","bsp_comision");
		zLista.AddColumnaLista("% COM CONTRATO ","porcentajeover");
		zLista.AddColumnaLista("$ COM CONTRATO","importeover");
		zLista.AddColumnaLista("% DIF","dif_porcentajeover");
		zLista.AddColumnaLista("$ DIF","dif_importeover");
		zLista.AddColumnaLista("REGLA CONTRATO","upfront_descripcion");
		
	
	}
	public void createTotalizer(JWinList oLista) throws Exception {
		if (GetVision().equals("SINTOT")) return;
		if (GetVision().equals("OVER")) return;
		
		if (GetVision().equals("UPFRONT")) {
			oLista.addTotalizer("tarifa_factura_local", 2, JTotalizer.OPER_SUM); // la suma del
			oLista.addTotalizer("impuestos_total_factura", 2, JTotalizer.OPER_SUM); // la suma del
			oLista.addTotalizer("tarifa_factura_total", 2, JTotalizer.OPER_SUM); // la suma del
			return;
		}
		
		oLista.addTotalizerText("CodigoPNR", "Totales"); // un texto
		oLista.addTotalizer("neto_factura", 2, JTotalizer.OPER_SUM); // la suma del
		oLista.addTotalizer("tarifa_factura", 2, JTotalizer.OPER_SUM); // la suma del
		oLista.addTotalizer("tarifa_facturada_yq", 2, JTotalizer.OPER_SUM); // la suma del
		
		oLista.addTotalizer("neto_factura_usa", 2, JTotalizer.OPER_SUM); // la suma del
		oLista.addTotalizer("tarifa_factura_usa", 2, JTotalizer.OPER_SUM); // la suma del

		if (GetVision().endsWith("baseyq_usa")||GetVision().endsWith("tarifa_yq")||GetVision().endsWith("tarifa_usa")||GetVision().endsWith("neto_usa")||GetVision().endsWith("neto")||GetVision().endsWith("tarifa")||GetVision().endsWith("local")||GetVision().endsWith("impuestos")){
			oLista.addTotalizer("neto_local", 2, JTotalizer.OPER_SUM); // la suma del
			oLista.addTotalizer("tarifa_local", 2, JTotalizer.OPER_SUM); // la suma del
			oLista.addTotalizer("tarifa_usa", 2, JTotalizer.OPER_SUM); // la suma del
			oLista.addTotalizer("neto_usa", 2, JTotalizer.OPER_SUM); // la suma del
			 oLista.addTotalizer("tarifa_yq", 2, JTotalizer.OPER_SUM); // la suma del
			 oLista.addTotalizer("baseyq_usa", 2, JTotalizer.OPER_SUM); // la suma del
			
						
		} else {
			oLista.addTotalizer("neto_factura_local", 2, JTotalizer.OPER_SUM); // la suma del
			oLista.addTotalizer("tarifa_factura_local", 2, JTotalizer.OPER_SUM); // la suma del
			oLista.addTotalizer("neto_factura_usa", 2, JTotalizer.OPER_SUM); // la suma del
			oLista.addTotalizer("tarifa_factura_usa", 2, JTotalizer.OPER_SUM); // la suma del
			
		}
		
	}



	@Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		JFormControl oControl;
	//	zFiltros.addGenericResponsive("Busqueda","").setFilterNeverHide(true);
		if (BizUsuario.getUsr().isAdminUser() )
			zFiltros.addComboResponsive("Agencia", "company", new GuiCompanies());
		else if ( BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
			GuiBSPCompany c= new GuiBSPCompany();
			c.setRecord(BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()));
			zFiltros.addComboResponsive("Agencia", "company", c.getHijos());
		}
		if (BizUsuario.IsAdminCompanyUser())
			zFiltros.addCDateResponsive("Proc.antes de", "fecha_proc").setOperator("<=");

		zFiltros.addComboResponsive("Tipo documento", "tipo_operacion", new JControlCombo() {
			@Override
			public JWins getRecords(Object zSource, boolean zOneRow) throws Exception {
				return getTiposOperacion();
			}
		}, false).SetValorDefault("ETR");

		JFormIntervalCDatetimeResponsive d= zFiltros.addIntervalCDateResponsive("F.emisión", "creation_date");
		d.SetValorDefault((Date)null);
		
		JFormIntervalCDatetimeResponsive d2= zFiltros.addIntervalCDateResponsive("F.emisión Doc", "creation_date_date");
		d2.SetValorDefault(JDateTools.getFirstDayOfMonth(new Date()),JDateTools.getLastDayOfMonth(new Date()));
		
		JFormIntervalCDatetimeResponsive dv= zFiltros.addIntervalCDateResponsive("F.despegue", "departure_date");
		zFiltros.addWinLovResponsive("LA",  "CodigoAerolinea",new GuiCarriers().addOrderAdHoc("description", "asc")).setShowKey(true);
		zFiltros.addEditResponsive("Boleto", JObject.JSTRING, "NumeroBoleto");
		zFiltros.addEditResponsive("Pasajero", JObject.JSTRING, "nombre_pasajero", "ilike");
		zFiltros.addEditResponsive("PNR", JObject.JSTRING, "codigopnr").setOperator("=");
		zFiltros.addComboResponsive("GDS ", "gds", new JControlCombo() {
			@Override
			public JWins getRecords(Object zSource, boolean zOneRow) throws Exception {
				return getGDS();
			}
		}, true);
		zFiltros.addEditResponsive("Ruta", JObject.JSTRING, "route").setOperator("ilike");
		zFiltros.addEditResponsive("Origen", JObject.JSTRING, "aeropuerto_origen").setOperator("ilike");
		zFiltros.addEditResponsive("Destino", JObject.JSTRING, "aeropuerto_destino").setOperator("ilike");
		zFiltros.addEditResponsive("Vendedor", JObject.JSTRING, "vendedor").setOperator("ilike");
		zFiltros.addEditResponsive("Cliente rmk", JObject.JSTRING, "codigo_cliente").setOperator("ilike");
		zFiltros.addEditResponsive("DK", JObject.JSTRING, "customer_id_reducido");
		zFiltros.addCheckThreeResponsive(null, "Internacional","Internacional","Todos","Domestico");
		zFiltros.addCheckThreeResponsive("Anulados", "void","Si","-","No");
		zFiltros.addCheckThreeResponsive("Revisados", "reemitted","Si","-","No");
		zFiltros.addCheckThreeResponsive("Exchanges", "exchanged","Si","-","No");
		zFiltros.addCheckThreeResponsive("Abierto", "open","Si","-","No");
		zFiltros.addEditResponsive("Fare Basis", JObject.JSTRING, "fare_savings").setOperator("ilike");
		zFiltros.addCheckThreeResponsive("Contrato Upfront", "oversobre","Si","-","No");
		zFiltros.addEditResponsive("Tour code", JObject.JSTRING, "tour_code").setOperator("ilike");
		zFiltros.addEditResponsive("Office Id", JObject.JSTRING, "office_id").setOperator("ilike");
		if (GetVision().equals("OVER")) {
			zFiltros.addComboResponsive("Contratos ", "upfront_ref", new JControlCombo() {
				@Override
				public JWins getRecords(Object zSource, boolean zOneRow) throws Exception {
					return new GuiDetalles().addFilterAdHoc("company", BizUsuario.getUsr().getCompany());
				}
			}, true).setSearchFields("linea", "descripcion").setDefaultValueProp(null);
						
		}
		zFiltros.addComboResponsive("Ordenar x ", "order_str", new JControlCombo() {
			@Override
			public JWins getRecords(Object zSource, boolean zOneRow) throws Exception {
				return getOrderOptions();
			}
		}, false).SetValorDefault("Fecha");
		
	}
	protected JWins getGDS() throws Exception {
		JMap<String, String> map = JCollectionFactory.createOrderedMap();
		map.addElement("AMADEUS", JLanguage.translate("Amadeus"));
		map.addElement("SABRE", JLanguage.translate("Sabre"));
		map.addElement("NDC", JLanguage.translate("Ndc"));
		map.addElement("TRAVELPORT", JLanguage.translate("Worldspan"));
		map.addElement("GALILEO", JLanguage.translate("Galileo"));
		map.addElement("ORACLE", JLanguage.translate("Oracle"));
			return JWins.createVirtualWinsFromMap(map);
	}
	protected JWins getOrderOptions() throws Exception {
		JMap<String, String> map = JCollectionFactory.createOrderedMap();
		map.addElement("Fecha", JLanguage.translate("Fecha(Descendente)"));
		map.addElement("Fecha_asc", JLanguage.translate("Fecha(Ascendente)"));
		map.addElement("Boleto", JLanguage.translate("Boleto"));
		map.addElement("Pasajero", JLanguage.translate("Pasajero"));
		return JWins.createVirtualWinsFromMap(map);
	}
	
	protected JWins getTiposOperacion() throws Exception {
		JMap<String, String> map = JCollectionFactory.createOrderedMap();
		map.addElement("ETR", JLanguage.translate("Ticket electronico"));
		map.addElement("EMD", JLanguage.translate("Documento electronico"));
		return JWins.createVirtualWinsFromMap(map);
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		GuiPNRTickets.process();
	}



	public static void process() {
			try {

				// String aa = new String(hash);
				JAplicacion.openSession();
				JAplicacion.GetApp().openApp("process.all", "process", true);
				if (BizUsuario.getUsr().GetUsuario().equals("")) {
					BizUsuario usuario = new BizUsuario();
					usuario.Read(BizUsuario.C_ADMIN_USER);
					BizUsuario.SetGlobal(usuario);
				}

				GuiPNRTickets mgr = new GuiPNRTickets();
			
				mgr.execReprocesarTodo(false, "CTS");

				JAplicacion.GetApp().closeApp();
				JAplicacion.closeSession();
			
			} catch (InterruptedException e) {
				// falta rollback?
				PssLogger.logDebug("Interruption received");
			
			} catch (Exception e) {
				// falta rollback?
				PssLogger.logError(e);
			}
	
	}
	
	private void execReprocesarTodo(boolean soloanioactual) throws Exception {
		this.execReprocesarTodo(soloanioactual,null);
	}

	private void execReprocesarTodo(boolean soloanioactual,String company) throws Exception {
		JRecords<BizPNRTicket> recs = this.getRecords();
		if (soloanioactual)
			recs.addFilter("creation_date", JDateTools.getFirstDayOfYear(new Date()),">=");

//		recs.addFixedFilter("where 1 in (select interface_id "
//				+ "from tur_pnr_file where tur_pnr_file.company=tur_pnr_boleto.company and "
//				+ "tur_pnr_file.pnr = tur_pnr_boleto.CodigoPNR)");
//		recs.addFixedFilter("where numeroboleto in ('1061959409','1061959410','1061959411','1061983802','1061983803','1062062041','1062062042','1062062043','1062107801','1062107802','1062107803','1062107804','1062107805','1062107806','1062107807','1062229198','1062229199','1062230800','1062230801','1062230802','1062230803','1062230804','1062230820','1062230821','1062230822','1062230825','1062355432','1062355434','1062355435','1165411568','1165411569','1165411570','1165511914','1165511915','1165543411','1165543412','1165543413','1165543414','1165551697','1165551698','1165551699','1165560736','1165576357','1165576358','1165589619','1165589620','1165599717','1165599718','1165599723','1165599724','1165622012','1165622013','1165622014','1165622015','1165622016','1165692040','1165692042','1165692043','1165692044','1165692045','1165702732','1165702734','1165744562','1165744563','1165761273','1165761275','1165761277','1165761279','1165785595','1165785596','1165785597','1165806425','1165806426','1165806427','1165806428','1165806429','1165806430','1165807206','1165807207','1165826906','1165826907','1165826908','1165826909','1165826915','1165878817','1165878818','1165878819','1165890859','1165890860','1165890861','1165906556','1165906557','1165926004','1165935960','1165935961','1165935962','1165935963','1165952101','1165952102','1165952157','1165952158','1165959391','1165959392','1165959393','1165982148','1165982149','1165996759','1165996761','1166006940','1166006948','1166006950','1166006957','1166006959','1166006961','1166006964','1166006966','1166009514','1166009520','1166009522','1166009524','1166009526','1166009528','1166009530','1166009532','1166009535','1166009537','1166009539','1166009541','1166009543','1166009545','1166009547','1166009549','1166009551','1166009553','1166009555','1166009557','1166016223','1166016224','1166022332','1166022334','1166022336','1166022338','1166022340','1166022351','1166027806','1166027807','1166027808','1166027809','1166050394','1166050395','1166053685','1166053686','1166053687','1166053688','1166072103','1166072104','1166072105','1166072106','1166072107','1166072108','1166072109','1166072110','1166072111','1166072112','1166072113','1166072114','1166072115','1166072116','1166072117','1166073586','1166073587','1166073588','1307242828','1307242829','1307242830','1307242831','1307264013','1307264014','1307265079','1307265081','1307270794','1307270795','1307270797','1307270798','1307270799','1307270906','1307270907','1307270915','1307270916','1307270949','1307270950','1307274460','1307274461','1307274462','1307296275','1307296276','1307296277','1307296278','1307296280','1307296281','1307306923','1307306924','1307306925','1307306926','1307306927','1307306928','1307306929','1307306930','1307306931','1307306932','1307317756','1307317757','1307322837','1307322838','1307322839','1307331649','1307331650','1307331651','1307331652','1307331653','1307331654','1307331655','1307335297','1307335298','1307335299','1307340500','1307340501','1307340502','1307363055','1307363056','1307363057','1307363058','1307364491','1307364492','1307364493','1307369726','1307369727','1307371145','1307371146','1307397084','1307397085','1307403527','1307403528','1307403529','1307403530','1307403531','1307403532','1307403533','1307403534','1307403535','1307424249','1307424250','1307426618','1307426619','1307426620','1307426621','1307426622','1307426623','1307437400','1307437401','1307443060','1307443061','1307467179','1307467180','1307467183','1307467184','1307467185','1307468089','1307468091','1307468093','1307472491','1307472493','1307476133','1307476134','1307476135','1307476136','1307477049','1307477050','1307477051','1307494006','1307494007','1307494012','1307498840','1307498841','1307504331','1307504333','1307537567','1307537568','1307537569','1307537570','1307537571','1307537572','1307542474','1307542475','1307562429','1307562430','1307562431','1307562432','1307562433','1307562434','1307567717','1307567718','1307573630','1307573631','1307573632','1307573633','1307592836','1307592837','1307592891','1307592892','1307592893','1307592894','1307610579','1307610580','1307631482','1307631484','1307633209','1307633210','1307633211','1307633212','1307633213','1307633214','1307633215','1307633216','1307633217','1307633218','1307633219','1307633220','1307633221','1307633222','1307633223','1307633224','1307633225','1307633226','1307633227','1307633228','1307633229','1307647243','1307665700','1307665701','1307665702','1307666007','1307666008','1307687519','1307687520','1307687521','1307687522','1307687523','1307690771','1307690772','1307690773','1307690774','1307690775','1307690776','1307690777','1307699532','1307699533','1307699534','1307699535','1307699536','1307699538','1307699540','1307699541','1307699542','1307700799','1307737805','1307737809','1307737811','1307737844','1307737847','1307737861','1307737862','1307737879','1307737880','1307765300','1307773204','1307773209','1307815622','1307815623','1307816260','1307816262','1307816264','1307816266','1307816268','1307816282','1307816283','1307816284','1307816285','1307816286','1307825413','1307825415','1307832053','1307832054','1307832055','1307832056','1307834144','1307834145','1307837911','1307837912','1307837913','1307837914','1307850730','1307850731','1307850732','1307850733','1307850734','1307850735','1307905286','1307905287','1307905288','1307905289','1307924798','1307924799','1820094101','1820094102','1820097261','1820097262','1820101848','1820101849','2220787805','2220787807','2220787810','2719831560','2719831561','2719831562','2719831563','2719831564','2719831565','4944390607','4944390608','4944390609','4944390610','4944390611','4944390612','4944406590','4944406591','4944406592','4944406593','4944406594','4944408903','4944408904','4944408905','4944408906','4944408907','4944408908','4944408909','4944439557','4944439558','4944439559','4944477227','4944477228','4944495555','4944495556','4944497300','4944497301','4944497302','4944514686','4944514687','4944527475','4944527476','4944544256','4944544257','4944544258','4944544259','4944550649','4944550650','4944550653','4944550654','4944572068','4944572069','4944572085','4944572086','4944572087','4944573859','4944573860','4944583300','4944583301','4944583302','4944583303','4944583304','4944583307','4944583310','4944585768','4944585770','4944623849','4944623850','4944623851','4944656262','4944656263','4944678457','4944678458','4944678459','4944689846','4944689847','4944689848','4944689849','4944695741','4944695742','4944695743','4944695744','4944707885','4944707886','4944707887','4944707888','4944707889','4944709319','4944709320','4944709321','4944709322','4944709323','4944752246','4944752247','4944757116','4944757117','4944761072','4944761073','4944761074','4944799127','4944799128','4944868910','4944868911','4944868912','4944868913','4944868914','4944868915','4944868916','4944868917','4944868918','4944868919','4944868969','4944868970','4944871822','4944871823','4944879138','4944879140','4944879142','4944879144','4944880797','4944880798','4944916012','4944916013','4944916014','4944916015','4944916017','4944916018','4944916019','4944928327','4944928328','4944928329','4944932368','4944932369','4944932370','4944943872','4944943874','4944948327','4944948328','4944948329','4944948330','4944948331','4944948332','4944948333','4944948334','4944948335','4944948336','4944948337','4944948338','4944948339','4944948340','4944948341','4944953452','4944953454','4944953456','4944953458','4944953460','4944953462','4944953464','4944953466','4944958539','4944958540','4944958541','4944958555','4944958556','4944958557','4944968675','4944968676','4944970502','4944970503','4944970504','4944970565','4944970566','4944991045','4944991047','4944991049','4944991051','4945009838','4945009839','4945043667','4945043668','4945043669','4945043670','4945043671','4945045093','4945045094','4945045095','4945045096','4945045097','4945045098','4945045099','4945059652','4945059653','4945059656','4945088981','4945088982','4945088983','5697434145','5697434146','5697434162','5697434163','5697434164','5697434165','5697440790','5697440791','5697516621','5697516622','5697542020','5697542021','5697547148','5697547149','5697547150','5697547151','5697547152','5697547305','5697547306','5697547307','5697547352','5697547355','5697547357','5697550706','5697550707','5697550708','5697550709','5697550710','5697550711','5697550712','5697550713','5697550714','5697550715','5697550716','5697550717','5697550718','5697550719','5697550720','5697550721','5697550722','5697550723','5697550724','5697550725','5697550726','5697550727','5697550728','5697550729','5697550730','5697550731','5697550732','5697550733','5697550734','5697550735','5697550736','5697550737','5697550738','5697550739','5697550740','5697550741','5697550742','5697550743','5697550744','5697550745','5697550746','5697550747','5697550748','5697550749','5697550750','5697550751','5697550752','5697550753','5697550754','5697550755','5697550756','5697550757','5697550758','5697550759','5697550760','5697550761','5697550762','5697550763','5697550764','5697550765','5697550766','5697550767','5697550768','5697550769','5697550770','5697550771','5697550772','5697550773','5697550774','5697550775','5697550776','5697550777','5697550778','5697550779','5697578311','5697578312','5697586228','5697586229','5697599673','5697599674','5697601345','5697601347','5697601349','5697601389','5697619017','5697635687','5697635688','5697649545','5697649546','5697649547','5697670459','5697670460','5697670461','5697670462','5697670481','5697670482','5697670483','5697719946','5697719947','5697727626','5697731206','5697731207','5697768083','5697770000','5697770013','5697770014','5697801171','5697801172','5697801173','5697801177','5697801178','5697834055','5697869878','5697889992','9124800357','9124800358','9124800359','9124800360','9125054120','9125054121','9125147544','9125147545','9125147546','9125147547','9125147548','9125147549','9125147550','9125147551','9125168782','9125168784','1002381074','1002381076','1002381078','1002381080','1002382744','1002382745','1002382746','1002382747','1002383480','1002387255','1002387256','1002388496','1002388497','1002388919','1002388937','1002388964','1002388982','1002388985','1002389403','1002389404','1002389700','1002389701','1002389702','1002389705','1002389706','1002389731','1002389736','1002389739','1002389743','1002391682','1002391684','1002391685','1002396458','1002398182','1002398183','1002398184','1002398185','1002398623','1002398625','1002398648','1002398650','1002398652','1002398654','1002398657','1002399440','1002399443','1002399444','1002400781','1002400782','1002400783','1002400784','1002404508','1002404510','1002405075','1002405077','1002405079','1002405081','1002405385','1002412355','1002412356','1002412357','1002412358','1002412908','1002412909','1002412910','1002425648','1002425649','1002425650','1002425651','1002425739','1002425740','1002428041','1002428042','1002429733','1002429734','1002429735','1002429736','1002431439','1002431440','1002431441','1002431442','1002432644','1002432645','1002435800','1002435802','1002435804','1002435806','1002435808','1157298104','1157298105','1157300871','1157300872','1157302064','1157302065','1157305923','1157305924','1157305925','1157313591','1157313592','1157313593','1157313594','1157313595','1157313596','1157313597','1157315897','1157315898','1157323338','1157323339','1157329208','1157329209','1356692026','1356692027','1356696217','1356696218','1356705087','1356705088','1356712998','1356715485','1356715486','1356715487','1356715488','1356715489','1356715490','1356719413','1356719980','1356719981','1356734157','1356734158','1356734159','1356734160','1356741338','1356741339','1356741340','1356743000','1356743002','1356743004','1356743005','1356743006','1356746337','1356746338','1356746339','1356746340','1356746341','1356746342','1356751867','1356751868','1356751869','2244315154','2244315155','2244315156','2244315157','2244315162','5695662277','5695662278','5695662279','5695662280','5695662281','5695665308','5695665309','5695665799','5695666101','5695666102','5695666103','5695666104','5695666105','5695666106','5695666107','5695666108','5695666109','5695666110','5695667528','5695667529','5695668123','5695668124','5695668126','5695672500','5695672501','5695673809','5695673810','5695674873','5695674874','5695674875','5695677881','5695677882','5695683646','5695683647','5695683648','5695683697','5695683698','5695684200','1062366712','1165681330','1165681332','1165681334','1165681336','1165888393','1165916386','1165946213','1165946250','1307367605','1307611281','1307635069','1307658757','1307684159','1307684161','1307833128','1307902913','1307902942','4944416980','4945019590','4945077001','4945077059','4945077080','5697433108','5697564114','5697564159','5697564160','5697667705','5697667706','5697677617','5697783423','5697835724','','1163810572','1163810573','1163855364','1163855365','1163855366','1163855367','1163855368','1610655972','1610655974','2296985447','2296985448','2296985449','2296991450','2296991451','2296991452','2296991453','2296991454','2296991455','2296991456','2296991457','2296991458','2296991459','2296991460','2297002913','2297002914','2297002915','2297002916','2297032445','2297032447','2297036150','2297036152','2297039571','2297039572','2717606473','2717606474','1163799481','1163799482','1163839338','1163839339','1610601054','1610601055','1610609349','1610609350','1610640493','1610640495','1610642600','1610642602','1610655972','1610655974','2296985447','2296985448','2296985449','2296991450','2296991451','2296991452','2296991453','2296991454','2296991455','2296991456','2296991457','2296991458','2296991459','2296991460','2296997152','2296997153','2297002913','2297002914','2297002915','2297002916','2297032445','2297032447','2297036150','2297036152','2717606473','2717606474','1334480821','1334480822','1334480823','1334480824','1334480825','1334480826','1334480827','1334529706','1334529707','1825602729','1825602730','4988527711','4988527712','4988527713','4988527714','4988542864','4988542865','4988542866','4988542867','4988542868','4988710535','4988710536','4988710537','4988710538','4988710539','4988710540','4988710541','4988710542','4988753269','4988753270','4988918812','4988918813','4988918814','4988918815','4988918816','4988918817','4988918818','4988918819','4988918820','5739286301','5739286302','5739286303','5739301414','5739387961','5739387962','5739387963','5739387964','5739403621','5739403622','5739403637','5739403638','5739409226','5739409227','5739409228','5739409229','5739409230','1610626187','1610626188','1610626189','1610626190','1610626191','1610626192','1610638432','1610638433','1610638434','1610638435','1610638436','1610645925','1610645926','1610645927','1610645928','1610645929','2296965015','2296965016','4974449965','4974449967','4974449968','4974449970','4974449978','4974449980','4974449982','4974449983','4974454400','1061914504','1061914505','1062064735','1062064736','1062064737','1062064738','1062064740','1062064745','1062064750','1062064751','1062064761','1062064763','1062064764','1062064765','1062064766','1062064767','1062064769','1062064770','1062064771','1062064772','1062064773','1062064774','1062064775','1062064776','1062064777','1062064778','1062064779','1062064780','1062064781','1062064782','1062064783','1062064784','1062064799','1062102144','1062102146','1062126571','1062126573','1062126575','1062126577','1062126579','1062126582','1062155878','1062155880','1062227193','1062227194','1062227195','1062227196','1062227197','1062338220','1062338222','1165431262','1165431264','1165479675','1165479676','1165479677','1165499649','1165499650','1165499651','1165499652','1165499655','1165522581','1165522582','1165601632','1165601634','1165787189','1165787190','1165787191','1166025634','1166025635','1166025684','1166025685','1166025686','1166025687','1166069902','1166069903','1166069904','1307365232','1307365233','1307365234','1307365235','1307365236','1307365237','1307365254','1307365255','1307365256','1307365257','1307382607','1307382608','1307382609','1307408287','1307408288','1307408289','1307408290','1307421232','1307635369','1307635370','1307635371','1307635372','1307635375','1307635376','1307635377','1307635378','1307711435','1307711436','1307711491','1307711492','1307741992','1307741993','1307741994','1307743987','1307743988','1307743999','1307760647','1307760648','1307760649','1307823800','1307913860','1307913876','1307913877','1307913878','4944482022','4944482023','4944482046','4944482047','4944695876','4944695878','4944728040','4944728041','4944728042','4944728043','4944728044','4944728065','4944728066','4944728067','4944741843','4944741844','4944741845','4944741846','4944832716','4944832717','4944832718','4944832719','4944873072','4944873073','4944933878','4944933879','4944983049','4944983050','4944983051','4944983052','4945041867','4945041868','5697448333','5697448334','5697448335','5697448336','5697488559','5697639929','5697639930','5697639938','5697704452','5697704453','9124881619','9124881620','9124913379','','1075307768','1075307769','1075307770','1075337764','1075503198','1075503199','1075686998','1075686999','1075702400','1179387456','1179387457','1179387458','1179387459','1179439379','1179439380','1334106265','1334106266','1334106267','1334106268','1334193204','1334193205','1334480042','1334480043','1334480044','1334480045','1334544819','1334544820','1334622013','1334622015','1334622017','1334622019','1334776769','1750320213','1750320214','1750320215','1750320216','1750320217','1750343259','1750343260','2297699410','2297699411','2297699412','2297699413','2297699414','2297699415','4988590320','4988590321','4988590322','4988590323','4988838430','4988838431','4988847730','4988847731','4988886493','4988886494','4988978416','4988978417','4988978418','4988978419','5739333627','5739333628','5739333629','1225149699','1225149800','1225150949','1225150950','1225163450','1225163451','1225172721','1225172722','4944798165','4944798166','4944798167','4944798168','4945029612','4945029613','4945029614','4945029615','4945029616','4945029617','4945029618','4945029634','4945029637','5697820057','5697820058','5697820059','5697820060','9195718542','9195718543','1075447874','1075447894','1075447895','1075447896','1075447897','1075447898','1075447899','1075471400','1075471401','1075471402','4989200853','1065238768','1392794444','1392798532','1392798552','1392821892','1392821893','1392889517','1392892364','1392893512','1392893513','1392893562','1392898407','1392898408','1392898409','1392908754','1392915065','1392920472','1392931807','1392950380','1392950381','1392950382','1392950383','1392950384','1392950385','1392958727','1393000039','1393015590','1393026339','1393026340','1393026341','1393041762','1393041763','1393041764','1393041792','1393041793','1393041794','1820322184','1825116173','1825116174','1825116750','5622845476','5622845477','5622845478','5622864258','5622864259','5622864260','5622864261','5622864262','5622897242','5622897243','5622909024','5622963748','5622963760','5622967568','5622972356','5623000875','5623003768','5623017925','5623026590','5623026591','5623029654','5623066629','5623066630','5623107403','5623118616','5623118617','5623118618','5623118619','5623145829','5623189295','1065231741','1227504913','1227591154','4904964249','4904964250','4904964251','4904964252','4904964253','4904973305','4904973313','4904994498','4904996226','4904996227','4904996228','4904996229','4904996230','5697143915','5697143916','5697171495','5697171496','5697198478','5697198479','5697202096','4919489173','4919489174','4919489175','4919489176','4919489177','4919708935','4919708936','4919708937','4920352512','4920352513','4920352514','4920352515','1075662074','1075662075','5739349366','5739349367','5739349368','5739349369','5739349370','5739349371','5739349372','4944484761','5697742086','5697742087','5697742088','5697742089','5697762291','5697762292','5697762293','5697762294','5697804885','5697804886','5697804887','5697804888','0000000001','1714119303','9115526573','9115526574','1086562621','1086562622','1086640096','1086640097','1086640098','1086640099','1086666600','1086666601','1086666602','1086666603','1235978339','1235978340','1235978341','1235978342','1235978344','1235978345','1235978346','1235978347','1236398483','1236398484','1236398485','1236398486','1236398487','1236398488','1236398489','1236398490','1236491926','1236491927','1236491932','1236491933','2212132698','4513269875','4919841253','4919841254','4919841255','4919884164','4919884165','4919884166','4919884167','4920306523','4920306524','4920306525','4920306526','4920342906','4920342907','4920342908','4920415107','7211115171','7211115172','7211115320','7211523354','7211547856','7212220201','7212339621','7214395999','7214656979','7215233655','7215338422','7215387879','7215636520','7215966890','7216665724','7217587771','7217587772','7218852991','7218852992','7218995342','7219355639','7219494453','8211115230','8211115256','8211115257','8211115368','8211115591','8211234580','8211234581','8211234582','8211234583','8211234584','8211548563','8211548965','8212221430','8214569875','8214585696','8214585697','8214586587','8214586596','8214586964','8215033255','8215485697','8215658545','8215658568','8215684565','8215685456','8215685458','8215685658','8215686452','8215803354','8215833255','8215856587','8215865458','8215867898','8215896545','8218349699','8219943469','9115721159','9115721160','1086753626','1086753627','1087480480','1087480481','1087480482','1087480483','1823167166','1823167167','4919365036','4919365037','4919431905','4919431906','0000000000','0000000001','0000000002','0000000003','0000000004','0000000005','0000000006','0000000007','0000000008','1087555293','1087555294','1235820783','1236092009','1236092010','1086784793','1087002250','1087002251','1712985315','1712985316','1712985317','1713310727','1713310728','1713310729','1713745743','1713745745','1714155982','4920408267','9114767492','9114767493','9114767494','9114767495','9114767496','9114767497','9115746594','9115746596','9115746598','9115746800','9115746802','9115746804','9115746806','9115746808','9115746810','1165458754','1165458755','1165458756','1165458757','1165458758','1165458759','1165458760','1165458761','1165458762','1165458763','1166100890','1166100891','1166100899','1307254200','1307272729','1307272730','1307272787','1307272788','1307453740','1307453741','1307495516','1307495517','1307495518','1307495519','1307495520','1307495521','1307495522','1307495523','1307580049','1307580050','1307580051','1307580052','1307580053','1307580054','1307580055','1307580056','1307639667','1307656515','1307656516','1307656517','1307656518','1307656519','1307656520','1307682271','1307682272','1307682273','1307682274','1307682275','1307682276','1307682277','1307682278','1307806449','1307806450','1307892444','1307892445','1307892446','2220943562','2220943563','2220943564','2220943565','2220943566','4944400837','4944400838','4944400843','4944400844','4944400845','4944400846','4944430432','4944430434','4944526981','4944526982','4944797584','4944797585','4944857108','4944857109','4944871073','4944871074','4944882402','4944882403','4944969159','4944969160','4945057550','4945057551','5697435685','5697435686','5697475778','5697475779','5697493940','5697493941','5697493991','5697493992','5697537452','5697537453','5697670009','5697670010','0000000000','0000000001','0000000002','0000000003','0000000004','0000000005','0000000006','0000000007','0000000008','0000000009','0000000011','1087555257','1087555258','1235767774','1235767775','1236092009','1236092010','1236494038','1713451939','1713451940','1713451941','1714057335','1714057337','4919363521','4919363523','4919875832','4919875834','4919875836','4919875838','4919875840','4919875842','4920042066','4920042067','4920042068','4920429397','4920458606','4920458607','4920458608','4920458609','4920528756','5692974796','9115687383','1122196188','1122196189','1122196190','1122196191','1122196192','1122196193','1122196194','1122196195','1727497996','1727497997','1727497998','1727497999','1061863002','1061863003','1061863004','1061986205','1061986206','1062179364','1062179365','1062324550','1062324551','1062324552','1062429620','1062429621','1062429622','1062429623','1062429624','1062429625','1062429626','1062429627','1062429628','1062429629','1062429630','1165566020','1165566021','1165566022','1165660754','1165660755','1165769212','1165769213','1165769255','1165769256','1165769257','1165769280','1165769281','1165956453','1165956454','1165956455','1165984141','1307403023','1307403024','1307403025','1307403026','1307403027','1307403028','1307403032','1307403033','1307403034','1307436663','1307436664','1307436665','1307436666','1307436667','1307436668','1307767483','1307767484','1307767485','1307800738','1307800739','1307832487','1307832488','1307832489','1307832490','1307832491','1307832492','1307832493','1307832494','1307832495','1307866751','1307866752','1307898121','1307898122','1307898123','1307923802','1307923804','1307923806','1307923808','1660512724','1660512725','1660512726','1660512727','1660512728','1660512729','1660512730','2220467737','2220467738','2220467739','4944411082','4944411083','4944411084','4944487845','4944487846','4944487847','4944487848','4944487849','4944487850','4944487851','4944494726','4944494727','4944539563','4944539564','4944539565','4944539566','4944746088','4944746089','4944746090','4944878261','4944878262','4944878263','4944878264','4944998728','4944998729','4944998730','4944998731','5697476492','5697476493','5697520220','5697520221','5697662386','5697662387','5697662388','5697677707','5697677708','5697677709','5697774323','5697774324','5697774325','5697774326','5697774327','5697796773','5697796774','9124812318','9124812319','9124812320','1307535101','1307535102','1307553819','1307553820','1307553821','1307553822','1307864703','1307864705','1307871620','1307871621','1307871622','1307871623','1307902350','1307902351','1307902353','1307902376','1307902377','1307902378','1307902379','1307902380','1307902381','1307902387','1307902388','1307902389','1307902390','1307902391','1307902392','1307902393','1307902397','1307902398','1307906001','1307906002','1307906003','1307906004','1307906005','1307906006','1307906007','1307906008','1307906009','1307920390','1307920392','4944454786','4944454787','4944454788','4944454789','4944524965','4944524966','4944580811','4944580812','4944656661','4944656662','4944656663','4944716316','4944716317','4944716318','4944743780','4944743781','4944751959','4944751960','4944751961','4944751962','4944765346','4944765347','4944765348','4944765349','4944765350','4944765351','4944769526','4944769527','4944830429','4944830430','4944839004','4944839006','4944839008','4944839010','4944839012','4944839014','4944839015','4944839016','4944839017','4944839018','4944898033','4944898034','4944898054','4944898055','4944898056','4944898057','4944898058','4944898059','4944898060','4944898062','4944898063','4944898064','4944898065','4944898066','4944898067','4944898068','4944898069','4944898070','4944898071','4944898072','4944898073','4944898074','4944898075','4944898076','4944898077','4944898078','4944898079','4944898080','4944898081','4944898082','4944898083','4944898084','4944898085','4944898086','4944898087','4944898088','4944898089','4944898090','4944898091','4944909159','4944909160','4944909161','4944909162','4944909163','4944909165','4944909166','4944909167','4944909168','4944919516','4944919517','4944951580','4944951581','4945032258','4945032259','4945032266','4945032267','4945086327','4945086328','4945086329','4945086330','5697447532','5697447533','5697456558','5697456559','5697456560','5697495266','5697501621','5697501622','5697520870','5697520871','5697520872','5697574300','5697574301','5697574302','5697574323','5697574324','5697578743','5697578744','5697578745','5697583410','5697583411','5697583412','5697666367','5697666368','5697666369','5697666370','5697666371','','0000000000','0000000001','0000000002','0000000003','1000030740','1000030784','1000031288','1000031342','1000031430','1000031440','1000031441','1000031967','1000032229','1086715055','1086715056','1086715057','1086715058','1087055301','1087055302','1087395129','1087395130','1087630751','1087630752','1236259722','1236259723','1236393171','1236393172','1236393173','1236393174','1236495062','1236495063','4920097628','4920097629','4920097630','4920097631','4920097632','1165681330','1165681332','1165681334','1165681336','1165946250','2220492287','4944469849','4945077001','5697564114','5697564159','5697564160','5697667706','5697677617','5697783423','1122246766','1122246767','1122253659','1122253660','1122290220','1122290221','1122290222','1122290223','1392653483','1601515918','1601515920','1601515922','1727617770','1727617771','1727617772','1727645347','1727645348','1727806411','2291395332','2291395333','2291615537','2291615538','2291634066','2291634067','2291638482','2291638483','2291638484','2291638485','5622970444','5622970447','5623128201','5623128202','9068728834','9068728835','9068728836','9068728837','9068728863','9068832267','9068832268','9068832269','9068832270','9657847126','9657847127','9657847128','9657847129','9657847130','9657847131','1307436663','1307436664','1307436665','1307436666','1307436667','1307436668','1307547204','1307547206','1307547235','1307547236','1307547237','1307547238','1307547255','1307547257','1307547261','1307547262','1307633961','1307633962','1307633963','1307633964','1307633965','1307677785','1307677786','1307677787','1307677788','1307677789','1307677790','1307767483','1307767484','1307767485','4944487832','4944487833','4944487834','4944487835','4944487843','4944487844','4944487853','4944487864','4944487865','4944487866','4944487867','4944487875','4944487877','4944487879','4944487881','4944494431','4944494432','4944494433','4944494434','4944539563','4944539564','4944539565','4944539566','4944595324','4944595325','4944595326','4944595327','4944595328','4944595329','4944658948','4944658949','4944658950','4944658951','4944658952','4944658953','4944658954','4944658955','4944658956','4944658957','4944658958','4944658959','4944702457','4944702458','4944702459','4944702460','4944702461','4944783962','4944783964','4944783966','4944783968','4944783970','4944783972','1165916386','1165946250','4944684207','4944684258','4945019590','4945077000','4945077001','5697433108','5697564114','5697564159','5697667705','5697677617','5697698772','5697783423','5697835724','1334245914','1334245915','1630694585','1630694586','1630694587','1630694588','1630694589','1630694590','1630694591','1630762961','1630762962','1630762963','1630762964','1630762965','1630762966','1630838428','1630838429','1749717483','1749717484','1750396346','1750396347','1750396348','1750396349','1750419750','4988660911','4988660912','4988915601','4988915602','4988915603','4988915604','4988915605','4988915606','4988915607','9195580018','9195580019','9195580020','9195580021','9195952307','9195952332','4944356499','5746264800','5746264801','4944968743','4944968744','4944968745','2295871890','2295871891','2295871892','2295871893','2295871894','2295871895','2295871896','2295871897','2295871898','4904987375','4904987376','4904987391','4904987392','4904991604','4904991605','4904991606','4904991607','4904991610','4904991611','4904991625','4904991626','5697135779','5697135780')");
 // 	recs.addFixedFilter("where  gds='TRAVELPORT'");
		//recs.addFixedFilter("where  gds='NDC'");
		
		if (company!=null)
		  recs.addFilter("company", company);
	 	//recs.addFixedFilter("where (fecha_proc >= '01/01/2020' )AND (fecha_proc <= '02/03/2020' )  and company in ('TURVISA','CTS','TERRA','SOL','CONTRAVEL','RIC','CIR','TC')");
//		  recs.addFixedFilter("where (creation_date >= '01/01/2020' )AND (creation_date <= '04/03/2020' )AND (fecha_proc <= '03/03/2020' ) ");
		//recs.addFixedFilter("where tarifa+impuestos_total<>tarifa_base_contax");
//		recs.addFixedFilter("where ( route like '%null%')");
	//	recs.addFixedFilter("where (  autoriz_cc like '%;%' )");
//		recs.addFixedFilter("where comision_factura<0");
	//		recs.addFixedFilter("where customer_id_reducido is null");
	//	recs.addFixedFilter("where boleto_original is not null");
//		recs.addFixedFilter("where codigo_base_moneda <>'USD' ");
//				recs.addJoin("JOIN","TUR_PNR_SEGMENTOAEREO","TUR_PNR_SEGMENTOAEREO.interface_id = TUR_PNR_BOLETO.interface_id and TUR_PNR_SEGMENTOAEREO.currency_base<>'USD' ");
//	recs.addJoin("JOIN","TUR_PNR_BOOKING","TUR_PNR_BOOKING.interface_id = TUR_PNR_BOLETO.interface_id and TUR_PNR_BOOKING.monto_orig<>0 and TUR_PNR_BOOKING.monto/TUR_PNR_BOOKING.monto_orig<18 ");
//recs.addFixedFilter("where numeroboleto in ('3181338030','3619404375','3181613462',				'3181119943','3180947649',				'3181581695','3619396494','3619399522','3180975545','3181012010',	'3181603211','3180912035','3485012929','3485009567','3410783711','3410783564','3410751048','3410731864','3181004941') ");
//	recs.addFixedFilter("where tipo_operacion='EMD' and CodigoAeroli00632','86500595','86500654' )  )");
//		recs.addFixedFilter("where  ( gds in ('AMADEUS','TRAVELPORT')) ");
//	recs.addFixedFilter("where tipo_operacion='EMD' AND (gds='TRAVELPORT' OR gds='AMADEUS')");
//		recs.addFixedFilter("where codigo_moneda<>codigo_base_moneda AND codigo_base_moneda<>'USD' AND (gds='SABRE')");
//		recs.addFixedFilter("where aeropuerto_origen=aeropuerto_destino");
//		recs.addFixedFilter("where  codigo_moneda<>'MXN' AND (gds='SABRE') and ( fecha_proc < '16/02/2023') ");
//		recs.addFixedFilter("where  (  (customer_id_reducido='' or customer_id_reducido is null)) ");
	//	recs.addFixedFilter("where  ( office_Id in ('86500514','86960134','86516555','865' )  and codigopnr like  '1A%' )");
//		recs.addFixedFilter("where (gds='SABRE') and ( fecha_proc < '09/01/2025')");
//		recs.addFixedFilter("where ( creation_date > '01/07/2024') and ( creation_date < '30/07/2024')");
//		recs.addFixedFilter("where ( fecha_proc = '02/01/2024')");
//		recs.addFixedFilter("where ( fecha_proc < '04/01/2025')  ");
	//	recs.addFixedFilter("where porcentajeover is null and ( fecha_proc = '29/01/2025')  ");
		
	//	recs.addFixedFilter("where yq<>0 and (creation_date >= '01/01/2020' )");
//			recs.addFixedFilter("where (creation_date >= '01/01/2021' ) AND (gds='SABRE' OR gds='AMADEUS')");
//		recs.addFixedFilter("where codigopnr in ('MZFDOY','KNSRFB','CUBXIX','NTKBSC','JRXRIU','ISUIJM','JPPCIP','NETZKL','KRBZGR','ECGIIZ','LDMXIP','ORFBSV','CXWJDG','HWIUSJ','HCQWAT','GDVUFN','DIPTWE','FQQDSK','IMOJNX','HXUAWZ','OEQUCA','HWLMTQ','FTBQQI','LMFZMN','EXOLIR','OBWPMO','FFPXAU','CRKHEI','FKKUIX','HLDOAH','MVILVO','JGTQGA','GXXSII','ZZVKHU','NVIZNW','CAAUZE','NMZJHV','LRCVWT','IEVTPA','ESBVQN','MIBUTJ','LHESWT','KSRYKP','MNVMNF','MJRTVV','FSGOKB','MUKAPA','LUJVZW','MRWCIK','EKSWBQ','WJUVQW','AVGMIZ','LLSJRZ','OWOVXQ','IRADVO','FUTXEV','FKJQQV','NZIDSI','JPECAC','DPELWC','EFILMI','MJHFWG','JDETVS','QCZPQI','SAQXUH','ERCGUZ','HMSKHK','CSUETW','GJXCQO','GPDPJI','NOLQTI','EHSAKW','NDRKTS','OBDCBX','MMVHKY','ANXFEC','EDJQNX','GDGEFR','IOPPIE','LNMIIO','ILNWTI','GYPJYD','HXKUUW','NJCUUB','NRQDTL','KLYNSM','GUVRNY','UYPGWQ','FVAUFC','NGFTMO','IFLDCV','MLUIUU','OGLCID','GJANCX','KGSLLU','EFJUWQ','MLYONH','CEKSNN','LSUNRV','MHYGFV','IJICQW','EUMTMD','ZZXWNB','STNOES','FPNOMC','NFCHFZ','HYCLIN','GISALC','EABAEA','AVVXGS','EKXBPH','EEOXAU','CRWAQG','MPAAWB','GBLYZS','JKXSKZ','OIMVSX','CDKXMT','CMGFSJ','MCXCRI','YERIZN','NDJBLQ','NCTJJU','NVKQKV','HGQRYA','JZEJEU','JEJVWK','YSANSL','LJAQMF','ODFTAJ','LYQJOY','IUDOBF','FFIIKJ','LWGBDG','KNNSTU','FRRYSE','EGTSZM','OPRLWX','KAZCBF','MWZUKS','NMGCKB','HBXBSD','NTKJJQ','NOVYHF','JEESDJ','LWFJPZ','KCAMTH','DIIFAL','ICSRRF','APRIJF','ISQDCH','EHXIXN','HIXTPB','JUVJIL','DTQIKF','YHGCFX','MMIIKK','FEAVUH','FPAKTW','GVQJPD','WAEHUE','NHMDFA','FFHGYM','KQRBEY','MDJULR','IGZBVS','SXBNZI','KRGLCG','HGHZCV','GXYAFR','KNLIBO','NFFQJN','ORKAKT','MWEJTU','JRHLVO','OFCUSW','EPTTEJ','WXGPRB','EAWHRH','FOCJZF','GDIBIO','IJBTDN','DDRPBW','NVMQYE','MBYKKU','JUBXRQ','MROBSH','JDEUGJ','CQMNIV','DBVXJJ','AGNWQB','QBFMEE','DTPHCL','LGVYLL','FPXAAT','LJHNGR','JJAYTI','LHHHFC','HBTWNK','IPFFLD','HWJCZI','FKHGGQ','ODBGLM','MEYJJB','GYPQGM','HXNMUU','EDKYGZ','JCJMFF','OFFRER','GHEQLO','JZJOKJ','HMVTJO','INPXXN','LWKBSG','MQFFYN','GBLOGC','NFNESE','GXXIVO','OEQGIZ','LPVFLP','ERKARJ','CWSKPO','LYBGWM','HCERKB','ICCWFY','JWHPBG','DNLTPT','CQIZWN','KPBMQO','MZCWQA','AVGKWF','GVNMGC','GAPOYK','KLNAMK','FMKWFS','JHSFAA','FHZLOV','JDEORJ','JYSOXP','GDDDKB','LGCQKT','DHQDBC','IJIQNE','HFHXHR','CQMLXF','MKVJUA','IQXBZK','NNJTJA','JGUJBH','IYIOWJ','NCDDJG','GEUGYP','JKYLHH','FPVBAB','GXEDBY','KLUSVF','ORZMUB','OZCRDH','OMPYTS','JBTDZG','HKFBUA','LOJXXV','FWJBWA','LZAGUS','GQGLJT','FPTDOO','DBHTPY','MNCHYL','EAVMBR','IASEMP','SKAAMP','NLFCLU','NJRARO','KDZWQI','NJQPOU','JRHJOH','HXMSID','ILKAGJ','KKVKZE','NTCGPR','SXCZXC','OETAGF','NNMDXQ','KQTGNZ','FKBURB','TSPLUK','IJQFUI','ESALIH','FIPYWS','YDLPDP','CAEFLE','MMVLXJ','LXTEIA','FPMAIY','LOELWK','JAUKUE','FHSNYJ','LGXHOV','IJLVTR','NIJBQT','DVXRDQ','NAWMUJ','FOSMLE','QIZCGC','NQHFGR','HEZTYN','NTICXM','YGQEUC','JXGHHT','JPRIPM','LTUDPJ','OENPUQ','JWNABX','CYWMJD','HMPIUC','FEQELU','KOVNTO','JRIOTR','EXFBHB','YRGXDM','KTDPYP','TMOEIH','MTAPNG','AGNVCC','FZPRCN','FTYQWA','OBQSQO','NPESGU','FZEMZJ','JVQNPA','MLOITH','ILXMER','FYYMBO','STUVGY','LQOVUV','CFGEPN','JCTEIN','NYAHEF','DJTPFC','MVUJVN','DUWDNZ','KOPYNA','DELJDX','ABJJEF','UMUAMT','IJFUDF','MIKJMF','JHRVWA','MANYML','SJHFOT','AKPSMN','GPRMLZ','HUFTFW','EDYUMM','NZLTSO','JYQECJ','DXWDAH','JCPNSP','OTFZHJ','HYMPNG','KFEWFO','FOKUTJ','FKJLEL','OWXEQG','JFHBVF','IHFEKD','OHKKFW','JZXIHS','IDKBXQ','DYPTFO','MXHHSJ','DNJSYI','MYYIFW','KZJNAB','ILNGFR','MNYJTD','LHLTCT','NDUAKW','JMCNIR','LIURZP','HBIFYJ','IUMFNK','DBEZKA','JPYHET','HNYSUZ','AKIBJM','LYRRCQ','JQHPNI','IVQMBS','GVDPDF','IDVGIH','HQRJXN','GTTKOI','GTVHWL','NMLGAO','IRXHJM','DQQVBE','GKWKCE','KJZHFG','IAERKP','KJZDJY','NWFOQC','EEVRQK','LVPOHA','OASORE','IHJUIJ','YASOVU','WHHYKB','LFYVHQ','EBDTVK','JZJVMN','TVROOW','FGQGMT','KWRQUE','KIZIUL','GNRRNZ','AMCLTN','GHVVWU','MJHWBG','LOGWIQ','GUTRCR','FTCGJH','EYIDIO','KJAQZV','GTDFYF','DZYFII','DELBVC','DOWWCN','NWCETJ','KQDVXQ','NDTKLT','YXHRTV','EUSFRG','KZXMEK','FNZXAC','GESNSS','MYYMLU','DGVIVI','ENZMJF','IOVRGD','MMZRJB','DTPYGW','KDCOSD','IZCXFU','LPGHNR','ENFNPJ','LZSVPJ','ISNSCP','DPRQXE','NNHQUI','GEUWRX','FIWWOK','FFZJJE','IFDRFR','FRJATP','KFAFZD','OHXYLB','ICEEJU','MUMNEE','WKEVNM','LAIFQK','ENZUGQ','ZHJVFG','TRHDLZ','JAFQPH','LUDBBA','NDOCTH','OTOOPC','QCBGIH','MFMWHK','HMWVAO','IQQVEA','ENDIRW','MMQNIZ','LLXRJU','JBQFAK','DTOOSR','MZLIEL','IKXLHP','DUOMIL','OSFTAC','ILNQRI','GASLBE','HMWWOI','GDTPRC','JXEWNE','IYDAIO','GAVGUH','LQTBJB','NJGCMP','HMYNDV','LZANRB','OBQPZA','NSAUFE','JGFPJV','DVYCFF','FBKTGN','FGKSRA','NQMZDM','JZGTIV','AVTISA','MVJUKI','OKMHDL','NNUPOU','HAEZNT','UIPRDB','IUJDIN','NPTHBG','MJSJSZ','SIGFPF','DUXDPO','NMJIIJ','KIHFOC','MLOPGU','GBEVBN','IDWMUM','ISYVYQ','NQQWLJ','EEVMXK','ERFRKR','HFNGOW','NVEBPB','EQELRB','FBIQSH','DNZPQQ','LAIRMX','IRGQRO','FBUKSG','WMUVVV','HWPJVS','XUZEYR','EGZVBZ','EFCNCW') ");
//		recs.addFixedFilter("where strpos(ident_fiscal,';')>0 ");
	//	recs.addFixedFilter("where ( pais_destino in ('DO','DR','DM') or pais_origen in ('DO','DR','DM')) ");
	//	recs.addFixedFilter("where  ( fecha_proc < '21/01/2025' ) and( gds='GALILEO' or CodigoAerolinea  ='WS' or (gds='AMADEUS' and iva_factura =0) )");
	//	recs.addFixedFilter("where  (nro_iata is null and gds='ORACLE')");
//		recs.addFixedFilter("where tarifa_usa is  null ");
		recs.addFixedFilter("where  (tipo_pasajero is null and gds='GALILEO')");

//		recs.addFixedFilter("where CodigoAerolinea  ='AC' ");
//		recs.addFixedFilter("where route  like '%QKL%' ");
//	recs.addJoin("JOIN","TUR_PNR_IMPUESTOS","TUR_PNR_IMPUESTOS.interface_id = TUR_PNR_BOLETO.interface_id and TUR_PNR_IMPUESTOS.CodigoImpuesto='XT' AND TUR_PNR_IMPUESTOS.Importe=0");
//		recs.addFixedFilter("where mini_route like '%EZE%' AND mini_route like '%AEP%' ");
				//		recs.addFixedFilter("where tarifa_usa>10000");
//	recs.addFixedFilter("where ( fecha_proc < '09/02/2025' ) ");
	//	recs.addFixedFilter("where  (reemitted='S') and  not  exists (select 1 from tur_pnr_boleto b where b.boleto_original = tur_pnr_boleto.numeroboleto) ");
//		recs.addFixedFilter("where  (reemitted='S') ");
//		recs.addFixedFilter("where nombre_tarjeta like '%0%'");
//			recs.addFixedFilter("where tarifa_usa>1000");
//		recs.addFixedFilter("where (aeropuerto_origen in ('ZPE','AIH','CDT','YIA') or (aeropuerto_destino in ('ZPE','AIH','CDT','YIA'))) ");
		//recs.addFixedFilter("where  creation_date>departure_date ");
		
//		recs.addFixedFilter("where tarifa=0 and impuestos_total_factura<>0" );
//			recs.addFixedFilter("where interface_id in (select interface_id from tur_pnr_segmentoaereo where tur_pnr_segmentoaereo.interface_id=tur_pnr_boleto.interface_id and monto_orig>5000)");
	//	recs.addFixedFilter("where interface_id in (select interface_id from tur_pnr_segmentoaereo where tur_pnr_segmentoaereo.interface_id=tur_pnr_boleto.interface_id and LENGTH(codigosegmento)>1)");
//		recs.addFixedFilter("where interface_id in (select interface_id from tur_pnr_segmentoaereo where tur_pnr_segmentoaereo.interface_id=tur_pnr_boleto.interface_id and fare_basic like '%PV79%')");
//		recs.addFixedFilter("where (gds='SABRE') and ( creation_date >= '01/01/2025') and ( creation_date < '09/01/2025')");
	//	recs.addFixedFilter("where gds='SABRE' and ( fecha_proc < '24/03/2025' ) and creation_date <> creation_date_date");
		
	//	recs.addFixedFilter("where ( fecha_proc < '09/01/2025' ) and interface_id in (select interface_id from tur_pnr_segmentoaereo where tur_pnr_segmentoaereo.interface_id=tur_pnr_boleto.interface_id and (Arrivo='TQO' or Destino='TQO' or despegue='TQO'))");
	//	recs.addFixedFilter("where numeroboleto in ('2095904199','2095904204','2095904189','2095904170','2095904169','2095904159','2095904158','2095904156','2095904157','2095904148','2095904149','2095904136','2095908365','2095904130','2095904116','2095904110','2095904100','2095906292','2095904093','2095904075','2095904067','2095904080','2095904079','2095904066','2095904065','2095905084','2095904055','2095904054','2095904052','2095905083','2095904062','2095904058','2095904035','2095904039','2095899589','2095904020','2095904017','2095904018','2095904042','2095895244','2095903426','2095904002','2095895243','2095904000','2095903308','2095895238','2095895239','2095895232','2095895225','2095901852','2095895211','2095901083','2095901100','2095895195','2095895194','2095895169','2095895189','2095895188','2095895171','2095899336','2095895193','2095895190','2095899271','2095895146','2095895145','2095899270','2095895155','2095898690','2095895147','2095895133','2095898527','2095895134','2095898526','2095898413','2095896207','2095895110','2095895109','2095895108','2095895072','2095895073','2095896197','2095896113','2095896105','2095896068','2095895046','2095896034','2095896036','2095896037','2095896035','2095895044','2095895045','2095895063','2095895033','2095895028','2095895027','2095895023','2095895024','2095895041','2095895042','2095895433','2095887244','2095895307','2095884925','2095884926','2095895257','2095895268','2095887245','2095895001','2095895007','2095887247','2095895006','2095895258','2095895269','2095887248','2095895002','2095894197','2095894332','2095894255','2095887240','2095887239','2095894331','2095894196','2095894177','2095894097','2095894096','2095894098','2095887236','2095887235','2095893422','2095887210','2095887209','2095887222','2095887220','2095887219','2095887221','2095887218','2095887189','2095891878','2095887194','2095887185','2095887174','2095887170','2095887180','2095887175','2095887177','2095887181','2095887178','2095887169','2095887176','2095887179','2095887182','2095887183','2095887184','2095887141','2095887147','2095887104','2095887096','2095887097','2095889819','2095887094','2095884817','2095884816','2095887086','2095887070','2095887076','2095887069','2095887079','2095887062','2095887055','2095887056','2095887045','2095887042','2095887043','2095887059','2095887025','2095887010','2095887011','2095887019','2095887008','2095887018','2095887028','2095887009','2095879238','2095879211','2095879220','2095879215','2095879212','2095879234','2095879236','2095879206','2095885872','2095879205','2095879237','2095879192','2095879185','2095879176','2095879181','2095879182','2095879175','2095879186','2095879171','2095879166','2095879152','2095879149','2095879146','2095879147','2095879148','2095879159','2095879127','2095883533','2095879125','2095879136','2095883534','2095879109','2095879108','2095879091','2095879118','2095879081','2095879119','2095879093','2095879120','2095879107','2095879094','2095879098','2095879103','2095879066','2095879065','2095881011','2095881013','2095881012','2095881010','2095881009','2095881008','2095879042','2095879038','2095879049','2095879041','2095879051','2095879050','2095879028','2095879716','2095879029','2095879715','2095878244','2095879530','2095879004','2095872746','2095879003','2095878242','2095879010','2095878243','2095872734','2095872732','2095879006','2095879001','2095872749','2095879005','2095878230','2095872731','2095878241','2095872748','2095879000','2095872747','2095879008','2095872715','2095872690','2095872691','2095872685','2095872712','2095872688','2095872717','2095872696','2095872693','2095872678','2095872682','2095872686','2095872684','2095878068','2095872714','2095872689','2095872687','2095872692','2095872713','2095872716','2095872662','2095872665','2095872654','2095872668','2095872655','2095877063','2095872594','2095876318','2095875982','2095872595','2095872607','2095872596','2095872586','2095875868','2095875867') ");
	//	recs.addFixedFilter("where  tipo_operacion ='EMD' and route is not null");
		recs.addOrderBy("codigopnr");
		
		if (recs.getFilterValue("company")!=null && recs.getFilterValue("company").equals("DEFAULT")) {
			recs.getFilters().removeElement(recs.getFilter("company"));
		}
		PNRCache.clean();
		int paginado=0;
		int size=5000;
		String last ="";
		while (true) {
			recs.setOffset(paginado);
			recs.setPagesize(size);
			recs.setStatic(false);
			JIterator<BizPNRTicket> it = recs.getStaticIterator();
			int i =0;
			while (it.hasMoreElements()) {
				BizPNRTicket pnr = it.nextElement();
				i++;
				if (pnr.getCodigopnr().equals(last)) continue;
				last=pnr.getCodigopnr();
				try {
					BizUsuario.eventInterfaz(null, "Procesando ticket "+pnr.getCodigopnr(), i, recs.getStaticItems().size(), true, null);
					//pnr.execSaveCotizacion();
					//if (pnr.getTarifaUsa()==0)
					pnr.execReprocesar();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (i<size) break;
			paginado+=size;
			PssLogger.logDebug("Process Tickets: " + paginado );
		}		
	}
	


	public void execReprocesarPendientes() throws Exception {
		JRecords<BizTicketsFaltantes> recs = new JRecords<BizTicketsFaltantes>(BizTicketsFaltantes.class);
		recs.addFilter("reprocesar", true);
		JIterator<BizTicketsFaltantes> it = recs.getStaticIterator();
		BizBspLog.log("DEFAULT", "PRINCIPAL", BizBspLog.BSPLOG_MODULO_GENERAL, BizBspLog.BSPLOG_LOG , "Incio reprocesamiento PNRs [%4]", null, null, null, recs.getStaticItems().size(), 0, 0);
		int proc=0;
		while (it.hasMoreElements()) {
				BizTicketsFaltantes faltante = it.nextElement();
				BizPNRTicket t = new BizPNRTicket();
				t.dontThrowException(true);
				if (!t.ReadByBoleto(faltante.getCompany(), faltante.getNroBoleto())) continue;
		  	BizBSPCompany comp = BizBSPCompany.getObjBSPCompany(t.getCompany());
		  	if (comp!=null&&!comp.getObjExtraData().getInactiva()) {
		  		t.execReprocesar();
		  		proc++;
		  	}
				faltante.execProcessDelete();
			}		
		BizBspLog.log("DEFAULT", "PRINCIPAL", BizBspLog.BSPLOG_MODULO_GENERAL, BizBspLog.BSPLOG_LOG , "Fin reprocesamiento PNRs [%4]", null, null, null, proc, 0, 0);
	}
	@Override
	public void asignFiltersFromFilterBar(JFormFiltro filterBar) throws Exception {
		JFormControl control=filterBar.GetControles().findControl("codigopnr");
		if (control!=null) {
			if (control.hasValue()) {
				this.asignFilterByControl(control);
				this.AddValueToWinsMapFilter(control);
				return;
			} else {
				control=filterBar.GetControles().findControl("numeroboleto");
				if (control.hasValue()) {
					this.asignFilterByControl(control);
					this.AddValueToWinsMapFilter(control);
					return;
				} 
			}
		}
		super.asignFiltersFromFilterBar(filterBar);
	}
	
	protected void asignFilterByControl(JFormControl control) throws Exception {
		if (control.getIdControl().equals("tipo_operacion")) {
			if (!control.hasValue()) {
				return;
			}
		}
		if (control.getIdControl().equals("exchanged")) {
			if (control.getValue().equals("S")) {
				this.getRecords().addFilter("reemitted", "S","=");
				this.getRecords().addFilter("boleto_original", "null","=");
			}
			else if (control.getValue().equals("N")) {
				this.getRecords().addFixedFilter("where (reemitted='N' or boleto_original is not null)");
			}
			return;
		}
		if (control.getIdControl().equals("open")) {
			if (control.getValue().equals("S")) {
				this.getRecords().addFilter("reemitted", "N");
			}
			else if (control.getValue().equals("N")) {
				this.getRecords().addFilter("reemitted", "N","<>");
			}
			return;
		}

		if (control.getIdControl().equals("oversobre")) {
			if (control.getValue().equals("S")) {
				this.getRecords().addFilter("upfront_ref", "null","<>");
			}
			else if (control.getValue().equals("N")) {
				this.getRecords().addFilter("upfront_ref", "null");
			}
			return;
		}
		if (control.getIdControl().equals("CodigoPNR")) {
			if (control.hasValue()) {
				this.getRecords().addFilter("CodigoPNR", control.getValue().toUpperCase(), "=");
							return;
			}
		}
		if (control.getIdControl().equals("fare_savings")) {
			if (control.hasValue()) {
				String value = control.getValue();
				this.getRecords().addJoin( "TUR_PNR_SEGMENTOAEREO");
				this.getRecords().addFixedFilter("TUR_PNR_SEGMENTOAEREO.interface_id = TUR_PNR_BOLETO.interface_id");
				this.getRecords().addFilter("TUR_PNR_SEGMENTOAEREO","fare_basic", value, "ilike");
				return;
			}
		}
		if (control.getIdControl().equals("order_str")) {
			if (control.hasValue()) {
				String val = control.getValue();
				if (val.equals("Fecha"))
					this.getRecords().addOrderBy("creation_date_date", "desc");
				else if (val.equals("Fecha_asc"))
					this.getRecords().addOrderBy("creation_date_date", "asc");
				else if (val.equals("Boleto"))
					this.getRecords().addOrderBy("numeroboleto");
				else if (val.equals("Pasajero"))
					this.getRecords().addOrderBy("nombre_pasajero");
			}
			return;
		}
		if (control.getIdControl().equals("codigopnr")) {
		
		}
		super.asignFilterByControl(control);
	}
	
	
}
