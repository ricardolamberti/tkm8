package pss.tourism.pnr;

import java.util.Date;

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
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JGrupoColumnaLista;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.responsiveControls.JFormIntervalCDatetimeResponsive;
import pss.tourism.carrier.GuiCarriers;

public class GuiPNRReservas  extends JWins {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiPNRReservas() throws Exception {
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiPNRReserva.class;
	}

  @Override
  public int GetNroIcono() throws Exception {
  	return 5129;
  }


	@Override
	public String GetTitle() throws Exception {
		return "Reservas";
	}


	// -------------------------------------------------------------------------//
	// Mapeo las acciones con las operaciones
	// -------------------------------------------------------------------------//
	@Override
	public void createActionMap() throws Exception {
		// addActionNew ( 1, "Nuevo Registro" );
		addAction(12, "Reprocesar todo", null, 17, true, true);
		addAction(14, "Reprocesar año", null, 17, true, true);
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
  	return super.getSubmitFor(a);
  }
  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getId()==12) return BizUsuario.IsAdminCompanyUser();
  	if (a.getId()==14) return BizUsuario.IsAdminCompanyUser();
  	return super.OkAction(a);
  }
	// -------------------------------------------------------------------------//
	// Configuro las columnas que quiero mostrar en la grilla
	// -------------------------------------------------------------------------//
	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		JGrupoColumnaLista datos=zLista.AddGrupoColumnaLista("Datos boleto");
		JGrupoColumnaLista otros=zLista.AddGrupoColumnaLista("Otros");
		if (BizUsuario.getUsr().isAdminUser())
			zLista.AddColumnaLista("company").setGrupo(datos);
		zLista.AddColumnaLista("codigo_pnr").setGrupo(datos);
		zLista.AddColumnaLista("LA", "CodigoAerolinea").setGrupo(datos);
		zLista.AddColumnaLista("office_id").setGrupo(datos);
		zLista.AddColumnaLista("nombre_pasajero").setGrupo(datos);
		zLista.AddColumnaLista("creation_date").setGrupo(datos);
		zLista.AddColumnaLista("departure_date").setGrupo(datos);
		if (BizUsuario.getUsr().IsAdminCompanyUser())
			zLista.AddColumnaLista("fecha_proc").setGrupo(datos);
		zLista.AddColumnaLista("Ruta", "route").setGrupo(otros);
		zLista.AddColumnaLista("Origen", "aeropuerto_origen").setGrupo(otros);	
		zLista.AddColumnaLista("Destino", "aeropuerto_destino").setGrupo(otros);		
		zLista.AddColumnaLista("Vendedor", "vendedor").setGrupo(otros);
	}	


	@Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		JFormControl oControl;
	//	zFiltros.addGenericResponsive("Busqueda","").setFilterNeverHide(true);
		if (BizUsuario.getUsr().isAdminUser())
			zFiltros.addComboResponsive("Agencia", "company", new GuiCompanies());

		if (BizUsuario.IsAdminCompanyUser())
			zFiltros.addCDateResponsive("Proc.antes de", "fecha_proc").setOperator("<=");


		JFormIntervalCDatetimeResponsive d= zFiltros.addIntervalCDateResponsive("F.emisión", "creation_date");
			d.SetValorDefault(JDateTools.getFirstDayOfMonth(new Date()),JDateTools.getLastDayOfMonth(new Date()));
		JFormIntervalCDatetimeResponsive dv= zFiltros.addIntervalCDateResponsive("F.despegue", "departure_date");
		zFiltros.addWinLovResponsive("LA",  "CodigoAerolinea",new GuiCarriers().addOrderAdHoc("description", "asc")).setShowKey(true);
		zFiltros.addEditResponsive("Pasajero", JObject.JSTRING, "nombre_pasajero", "ilike");
		zFiltros.addEditResponsive("PNR", JObject.JSTRING, "codigo_pnr").setOperator("ilike");
//		zFiltros.addComboResponsive("GDS ", "gds", new JControlCombo() {
//			@Override
//			public JWins getRecords(Object zSource, boolean zOneRow) throws Exception {
//				return getGDS();
//			}
//		}, false);
		zFiltros.addEditResponsive("Ruta", JObject.JSTRING, "route").setOperator("ilike");
		zFiltros.addEditResponsive("Origen", JObject.JSTRING, "aeropuerto_origen").setOperator("ilike");
		zFiltros.addEditResponsive("Destino", JObject.JSTRING, "aeropuerto_destino").setOperator("ilike");
		zFiltros.addEditResponsive("Vendedor", JObject.JSTRING, "vendedor").setOperator("ilike");
		zFiltros.addCheckThreeResponsive(null, "Internacional","Internacional","Todos","Domestico");

		zFiltros.addComboResponsive("Ordenar x ", "order_str", new JControlCombo() {
			@Override
			public JWins getRecords(Object zSource, boolean zOneRow) throws Exception {
				return getOrderOptions();
			}
		}, false).SetValorDefault("Fecha");
		
	}
	private JWins getGDS() throws Exception {
		JMap<String, String> map = JCollectionFactory.createOrderedMap();
		map.addElement("AMADEUS", JLanguage.translate("Amadeus"));
		map.addElement("SABRE", JLanguage.translate("Sabre"));
		map.addElement("NDC", JLanguage.translate("Ndc"));
		map.addElement("TRAVELPORT", JLanguage.translate("Worldspan"));
		map.addElement("GALILEO", JLanguage.translate("Galileo"));
		return JWins.createVirtualWinsFromMap(map);
	}
	private JWins getOrderOptions() throws Exception {
		JMap<String, String> map = JCollectionFactory.createOrderedMap();
		map.addElement("Fecha", JLanguage.translate("Fecha(Descendente)"));
		map.addElement("Fecha_asc", JLanguage.translate("Fecha(Ascendente)"));
		map.addElement("Pasajero", JLanguage.translate("Pasajero"));
		return JWins.createVirtualWinsFromMap(map);
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		GuiPNRReservas.process();
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

				GuiPNRReservas mgr = new GuiPNRReservas();
			
				mgr.execReprocesarTodo(false);

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
		JRecords<BizPNRReserva> recs = this.getRecords();
		if (soloanioactual)
			recs.addFilter("creation_date", JDateTools.getFirstDayOfYear(new Date()),">=");

	
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
//	recs.addFixedFilter("where tipo_operacion='EMD' and CodigoAerolinea is null ");
//	recs.addFixedFilter("where tipo_operacion='EMD' AND (gds='TRAVELPORT' OR gds='AMADEUS')");
//		recs.addFixedFilter("where codigo_moneda<>codigo_base_moneda AND codigo_base_moneda<>'USD' AND (gds='SABRE')");
//		recs.addFixedFilter("where aeropuerto_origen=aeropuerto_destino");

	//	recs.addFixedFilter("where yq<>0 and (creation_date >= '01/01/2020' )");
			recs.addFixedFilter("where (creation_date >= '01/01/2020' ) and void='S'  AND (gds='SABRE' OR gds='AMADEUS')");
//		recs.addFixedFilter("where fecha_proc < '02/06/2020'");

//		recs.addFixedFilter("where CodigoAerolinea  ='CM' ");
//		recs.addFixedFilter("where route  like '%QKL%' ");
//	recs.addJoin("JOIN","TUR_PNR_IMPUESTOS","TUR_PNR_IMPUESTOS.interface_id = TUR_PNR_BOLETO.interface_id and TUR_PNR_IMPUESTOS.CodigoImpuesto='XT' AND TUR_PNR_IMPUESTOS.Importe=0");
//		recs.addFixedFilter("where mini_route like '%EZE%' AND mini_route like '%AEP%' ");
				//		recs.addFixedFilter("where tarifa_usa>10000");
	//	recs.addFixedFilter("where ( fecha_proc < '01/07/2020' ) ");
	//	recs.addFixedFilter("where  (reemitted='S') and  not  exists (select 1 from tur_pnr_boleto b where b.boleto_original = tur_pnr_boleto.numeroboleto) ");
//		recs.addFixedFilter("where  (reemitted='S') ");
//		recs.addFixedFilter("where nombre_tarjeta like '%0%'");
//			recs.addFixedFilter("where tarifa_usa>1000");
//		recs.addFixedFilter("where tarifa=0 and impuestos_total_factura<>0" );
//			recs.addFixedFilter("where interface_id in (select interface_id from tur_pnr_segmentoaereo where tur_pnr_segmentoaereo.interface_id=tur_pnr_boleto.interface_id and monto_orig>5000)");
			recs.addOrderBy("codigo_pnr");
		
		if (recs.getFilterValue("company")!=null && recs.getFilterValue("company").equals("DEFAULT")) {
			recs.getFilters().removeElement(recs.getFilter("company"));
		}
		int paginado=0;
		int size=5000;
		String last ="";
		while (true) {
			recs.setOffset(paginado);
			recs.setPagesize(size);
			recs.setStatic(false);
			JIterator<BizPNRReserva> it = recs.getStaticIterator();
			int i =0;
			while (it.hasMoreElements()) {
				BizPNRReserva pnr = it.nextElement();
				i++;
				if (pnr.getCodigopnr().equals(last)) continue;
				last=pnr.getCodigopnr();
				try {
					BizUsuario.eventInterfaz(null, "Procesando reserva "+pnr.getCodigopnr(), i, recs.getStaticItems().size(), true, null);
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
			PssLogger.logDebug("Process Reservas: " + paginado );
		}		
	}
	

	@Override
	public void asignFiltersFromFilterBar(JFormFiltro filterBar) throws Exception {
		JFormControl control=filterBar.GetControles().findControl("codigo_pnr");
		if (control.hasValue()) {
			this.asignFilterByControl(control);
			this.AddValueToWinsMapFilter(control);
			return;
		} 
		super.asignFiltersFromFilterBar(filterBar);
	}
	
	protected void asignFilterByControl(JFormControl control) throws Exception {
	
		if (control.getIdControl().equals("order_str")) {
			if (control.hasValue()) {
				String val = control.getValue();
				if (val.equals("Fecha"))
					this.getRecords().addOrderBy("creation_date", "desc");
				else if (val.equals("Fecha_asc"))
					this.getRecords().addOrderBy("creation_date", "asc");
				else if (val.equals("Pasajero"))
					this.getRecords().addOrderBy("nombre_pasajero");
			}
			return;
		}
		if (control.getIdControl().equals("codigo_pnr")) {
		
		}
		super.asignFilterByControl(control);
	}
	
	
}
