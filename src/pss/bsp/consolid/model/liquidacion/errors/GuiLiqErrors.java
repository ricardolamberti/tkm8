package pss.bsp.consolid.model.liquidacion.errors;

import java.util.HashSet;
import java.util.Set;

import pss.bsp.consolid.model.liquidacion.detail.BizLiqDetail;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JObject;
import pss.core.services.records.JRecords;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;
import pss.tourism.pnr.BizPNRTicket;
import pss.tourism.pnr.PNRCache;
import pss.tourism.voidManual.BizVoidManual;

public class GuiLiqErrors extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiLiqErrors() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10003; } 
  public String  GetTitle()    throws Exception  { return "Informes"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiLiqError.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
//    addActionNew( 1, "Nuevo Registro" );
		addAction(12, "Reprocesar todo", null, 17, true, true);
		addAction(20, "Voidear No voideados", null, 17, true, true);
		addAction(30, "Igualar DKs", null, 17, true, true);
 }

  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==12) return new JActSubmit(this, a.getId()) {
  		public void submit() throws Exception {
  			execReprocesarTodo();
  		}
  	};
  	if (a.getId()==20) return new JActSubmit(this, a.getId()) {
  		public void submit() throws Exception {
  			execVoidearTodo();
  		}
  	};
  	if (a.getId()==30) return new JActSubmit(this, a.getId()) {
  		public void submit() throws Exception {
  			execDKsTodo();
  		}
  	};
	
  	return super.getSubmitFor(a);
  }
  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getId()==12) return BizUsuario.IsAdminCompanyUser();
  	return super.OkAction(a);
  }

  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
//    zLista.AddColumnaLista("linea");
    zLista.AddColumnaLista("type_descr");
    zLista.AddColumnaLista("error");
  	zLista.AddColumnaLista("Org.","organization");
  	zLista.AddColumnaLista("dk");
  	zLista.AddColumnaLista("tipo_doc");
  	zLista.AddColumnaLista("nro_doc");
  	zLista.AddColumnaLista("fecha_doc");
  	zLista.AddColumnaLista("nro_boleto");
  	zLista.AddColumnaLista("gds_tkm");
  	zLista.AddColumnaLista("emision_tkm");
  	zLista.AddColumnaLista("tarjeta_tkm");
  	zLista.AddColumnaLista("fecha_salida_tkm");
  	zLista.AddColumnaLista("fecha_fin_tkm");
  	zLista.AddColumnaLista("origen_tkm");
  	zLista.AddColumnaLista("destino_tkm");

  	zLista.AddColumnaLista("control_iata");
  	zLista.AddColumnaLista("control_prestador");
    zLista.AddColumnaLista("control_ruta");
  	zLista.AddColumnaLista("pasajero");
  	zLista.AddColumnaLista("reserva");
  	zLista.AddColumnaLista("control_tarifa");
  	zLista.AddColumnaLista("control_iva");
  	zLista.AddColumnaLista("control_tua");
  	zLista.AddColumnaLista("control_importe");
  	zLista.AddColumnaLista("control_saldo");
   	zLista.AddColumnaLista("control_tipo_servicio");
  	zLista.AddColumnaLista("control_clase");
//  	zLista.AddColumnaLista("control_clases");
  	zLista.AddColumnaLista("control_lineas");
  	zLista.AddColumnaLista("control_forma_pago");
  	zLista.AddColumnaLista("porc_incentivo");
  	zLista.AddColumnaLista("incentivo");
  	zLista.AddColumnaLista("tipo");
  }
  
  @Override
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
  	zFiltros.addComboResponsive("Criticidad", "type_error", new JControlCombo() {
  		@Override
  		public JWins getRecords(Object zSource, boolean zOneRow) throws Exception {
  			return getTiposErrores();
  		}
  	}, true);
  	zFiltros.addEditResponsive("Busqueda", JObject.JSTRING, "error");
  	zFiltros.addEditResponsive("Boleto", JObject.JSTRING, "nro_boleto");
  	zFiltros.addEditResponsive("Nro.Doc.", JObject.JSTRING, "nro_doc");
		
    super.ConfigurarFiltros(zFiltros);
  }
  
  @Override
	protected void asignFilterByControl(JFormControl control) throws Exception {
		if (control.getIdControl().equals("type_error") && control.hasValue()) {
			if (control.getValue().equals(BizLiqError.CR_ALLERRORS) ) {
				this.getRecords().addFilter("type_error",BizLiqError.CR_OK,"<>").setDynamic(true);
				return;
			} 
		}
		super.asignFilterByControl(control);
  }

	protected JWins getTiposErrores() throws Exception {
			return JWins.createVirtualWinsFromMap(BizLiqError.getTiposErrores());
	}

	private synchronized void execVoidearTodo() throws Exception {
		String company = BizUsuario.getUsr().getCompany();
		JRecords<BizLiqError> recs = new JRecords<BizLiqError>(BizLiqError.class); 
		long liqId = Long.parseLong(getFilterValue("liquidacion_id"));
		recs.addFilter("company", company);
		recs.addFilter("liquidacion_id", liqId);
		recs.addFilter("type_error", BizLiqError.CR_VOID);

		Set<String> boletosAnulados = new HashSet<>();
		JIterator<BizLiqError> it = recs.getStaticIterator();
		while (it.hasMoreElements()) {
			BizLiqError error = it.nextElement();
			String nroBoleto = error.getNroBoleto();
			if (!boletosAnulados.contains(nroBoleto)) {
				new BizVoidManual().execAnular(error.getCompany(), nroBoleto);
				boletosAnulados.add(nroBoleto);
			}
		}
	}
	private void execDKsTodo() throws Exception {
		long liqId = Long.parseLong(getFilterValue("liquidacion_id"));
		JRecords<BizLiqDetail> pnrs = new JRecords<BizLiqDetail>(BizLiqDetail.class);
		pnrs.SetSQL("SELECT "
				+ "   b.* "
				+ " FROM public.tur_pnr_boleto t"
				+ " JOIN public.bsp_liquidation_detail b"
				+ "  ON t.company = b.company"
				+ " AND t.numeroboleto = b.nro_boleto"
				+ " WHERE t.customer_id_reducido <> b.dk and "
				+ " liquidacion_id ="+liqId);

			JIterator<BizLiqDetail> it=pnrs.getStaticIterator();
			while (it.hasMoreElements()) {
				BizLiqDetail tk = it.nextElement();
				new BizVoidManual().execChangeDK(tk.getCompany(), tk.getNroBoleto(), tk.getDk());
			}
			
		
	}
	private void execReprocesarTodo() throws Exception {
		String company = BizUsuario.getUsr().getCompany();
		JRecords<BizPNRTicket> recs = new JRecords<BizPNRTicket>(BizPNRTicket.class); 
		long liqId = Long.parseLong(getFilterValue("liquidacion_id"));
		recs.addFixedFilter("where interface_id in (select interface_id from bsp_liquidation_error where bsp_liquidation_error.interface_id is not null and bsp_liquidation_error.liquidacion_id="+liqId+")");
		recs.addFilter("company", company);
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
				//if (!pnr.getGDS().equals("ORACLE")) continue;
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
//			paginado+=size;
			PssLogger.logDebug("Process Tickets: " + paginado );
		}		
	}
	
}
