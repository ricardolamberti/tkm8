package pss.tourism.pnr;

import java.util.Date;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.bspBusiness.GuiBSPCompany;
import pss.common.regions.company.GuiCompanies;
import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JObject;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
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
import pss.core.winUI.responsiveControls.JFormComboResponsive;
import pss.core.winUI.responsiveControls.JFormIntervalCDatetimeResponsive;

public class GuiPNROtros extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiPNROtros() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10023; } 
  public String  GetTitle()    throws Exception  { return "Otros segmentos"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiPNROtro.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
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
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		JFormControl oControl;
		if (BizUsuario.getUsr().isAdminUser())
			zFiltros.addComboResponsive("Agencia", "company", new GuiCompanies());
		else if ( BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
			GuiBSPCompany c= new GuiBSPCompany();
			c.setRecord(BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()));
			zFiltros.addComboResponsive("Agencia", "company", c.getHijos());
		}
		if (BizUsuario.IsAdminCompanyUser())
			zFiltros.addCDateResponsive("Proc.antes de", "fecha_proc").setOperator("<=");

		JFormComboResponsive combo=zFiltros.addComboResponsive("Producto", "product_code", new JControlCombo() {
			@Override
			public JWins getRecords(Object zSource, boolean zOneRow) throws Exception {
				return getProductos();
			}
		}, true);
		combo.SetValorDefault("ETR");;

		JFormIntervalCDatetimeResponsive d= zFiltros.addIntervalCDateResponsive("F.emisión", "creation_date");
		d.SetValorDefault(JDateTools.getFirstDayOfMonth(new Date()),JDateTools.getLastDayOfMonth(new Date()));
		
//		JFormCDate d= zFiltros.NuevoCDate("emisión desde", "creation_date");
//		d.setOperator(">=");
//		d.setIdControl("fdesde");
//		d.SetValorDefault(JDateTools.getFirstDayOfMonth(new Date()));
//		d=	zFiltros.NuevoCDate("emisión hasta", "creation_date");
//		d.setOperator("<=");
//		d.setIdControl("fhasta");
//		d.SetValorDefault(JDateTools.getLastDayOfMonth(new Date()));
//		zFiltros.NuevoEdit("Boleto", JObject.JSTRING, "NumeroBoleto");
		zFiltros.addEditResponsive("Pasajero", JObject.JSTRING, "nombre_pasajero", "ilike");
		zFiltros.addEditResponsive("PNR", JObject.JSTRING, "codigo_pnr").setOperator("ilike");
		zFiltros.addEditResponsive("GDS", JObject.JSTRING, "gds").setOperator("ilike");
		zFiltros.addEditResponsive("Hotel", JObject.JSTRING, "hotel_fname").setOperator("ilike");
		zFiltros.addEditResponsive("Aeropuerto", JObject.JSTRING, "aeropuerto").setOperator("ilike");

		
		zFiltros.addComboResponsive("Ordenar x ", "order_str", new JControlCombo() {
			@Override
			public JWins getRecords(Object zSource, boolean zOneRow) throws Exception {
				return getOrderOptions();
			}
		}, false).SetValorDefault("Fecha");
		
	}
	
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		JGrupoColumnaLista datos=zLista.AddGrupoColumnaLista("Datos ticket");
		JGrupoColumnaLista hotel=zLista.AddGrupoColumnaLista("Hotel");
		JGrupoColumnaLista auto=zLista.AddGrupoColumnaLista("Auto");
		JGrupoColumnaLista tour=zLista.AddGrupoColumnaLista("Tour");
		JGrupoColumnaLista tren=zLista.AddGrupoColumnaLista("Tren");
		JGrupoColumnaLista importe=zLista.AddGrupoColumnaLista("Montos");
		JGrupoColumnaLista comision=zLista.AddGrupoColumnaLista("Comision");
		JGrupoColumnaLista otros=zLista.AddGrupoColumnaLista("Otros");
		
		if (BizUsuario.getUsr().isAdminUser())
			zLista.AddColumnaLista("company").setGrupo(datos);
		zLista.AddColumnaLista("codigo_pnr").setGrupo(datos);
		zLista.AddColumnaLista("codigo_seg").setGrupo(datos);
		zLista.AddColumnaLista("product_desc").setGrupo(datos);
		zLista.AddColumnaLista("creation_date").setGrupo(datos);
		zLista.AddColumnaLista("fecha_reserva").setGrupo(datos);
		zLista.AddColumnaLista("fecha_salida").setGrupo(datos);
		if (BizUsuario.getUsr().isAdminUser())
			zLista.AddColumnaLista("fecha_proc").setGrupo(datos);
		zLista.AddColumnaLista("service_type").setGrupo(datos);
		zLista.AddColumnaLista("service_code").setGrupo(datos);
		zLista.AddColumnaLista("car_code").setGrupo(datos);
		zLista.AddColumnaLista("pasajeros").setGrupo(datos);
		zLista.AddColumnaLista("nombre_pasajero").setGrupo(datos);
		
		
		zLista.AddColumnaLista("hotel_codigo").setGrupo(hotel);
		zLista.AddColumnaLista("hotel_fname").setGrupo(hotel);
		zLista.AddColumnaLista("hotel_name").setGrupo(hotel);
		zLista.AddColumnaLista("habitaciones").setGrupo(hotel);
		zLista.AddColumnaLista("noches").setGrupo(hotel);
		zLista.AddColumnaLista("aeropuerto").setGrupo(hotel);

		zLista.AddColumnaLista("vehiculo_proveedor").setGrupo(auto);
		zLista.AddColumnaLista("nro_autos").setGrupo(auto);
		zLista.AddColumnaLista("equipamiento").setGrupo(auto);
		zLista.AddColumnaLista("tasa_garantia").setGrupo(auto);
		zLista.AddColumnaLista("info").setGrupo(auto);
		
		zLista.AddColumnaLista("pais_origen").setGrupo(tren);
		zLista.AddColumnaLista("pais_destino").setGrupo(tren);

		zLista.AddColumnaLista("tour_name").setGrupo(tour);

		zLista.AddColumnaLista("importe_local").setGrupo(importe);
		zLista.AddColumnaLista("tasas_local").setGrupo(importe);
		zLista.AddColumnaLista("fee_local").setGrupo(importe);
		zLista.AddColumnaLista("precio_total").setGrupo(importe);

		zLista.AddColumnaLista("comision_codigo").setGrupo(comision);
		zLista.AddColumnaLista("comision_texto").setGrupo(comision);
		zLista.AddColumnaLista("comision_monto").setGrupo(comision);
		zLista.AddColumnaLista("rate_code").setGrupo(comision);

		zLista.AddColumnaLista("nombre_cliente").setGrupo(otros);
		zLista.AddColumnaLista("domicilio").setGrupo(otros);
		zLista.AddColumnaLista("telefono").setGrupo(otros);
		zLista.AddColumnaLista("fax").setGrupo(otros);
		zLista.AddColumnaLista("void").setGrupo(otros);
		zLista.AddColumnaLista("info").setGrupo(otros);
		zLista.AddColumnaLista("gds").setGrupo(otros);
		zLista.AddColumnaLista("nro_iata").setGrupo(otros);
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
			}
			return;
		}
		if (control.getIdControl().equals("codigo_pnr")) {
		
		}
		super.asignFilterByControl(control);
	}
	private JWins getProductos() throws Exception {
		return JWins.createVirtualWinsFromMap(BizPNROtro.getMapProductos());
	}
	private JWins getOrderOptions() throws Exception {
		JMap<String, String> map = JCollectionFactory.createOrderedMap();
		map.addElement("Fecha", JLanguage.translate("Fecha(Descendente)"));
		map.addElement("Fecha_asc", JLanguage.translate("Fecha(Ascendente)"));
		return JWins.createVirtualWinsFromMap(map);
	}
	private void execReprocesarTodo(boolean soloanioactual) throws Exception {
		JRecords<BizPNROtro> recs = this.getRecords();
		if (soloanioactual)
			recs.addFilter("creation_date", JDateTools.getFirstDayOfYear(new Date()),">=");
		recs.addOrderBy("codigo_pnr");
		if (recs.getFilterValue("company")!=null && recs.getFilterValue("company").equals("DEFAULT")) {
			recs.getFilters().removeElement(recs.getFilter("company"));
		}
		int paginado=0;
		String last ="";
		while (true) {
			recs.setOffset(paginado);
			recs.setPagesize(50);
			recs.setStatic(false);
			JIterator<BizPNROtro> it = recs.getStaticIterator();
			int i =0;
			while (it.hasMoreElements()) {
				BizPNROtro pnr = it.nextElement();
				i++;
				if (pnr.getCodigoPNR().equals(last)) continue;
				last=pnr.getCodigoPNR();
				try {
					pnr.execReprocesar();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (i<50) break;
			paginado+=50;
		}		
	}
}
