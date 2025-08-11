package  pss.bsp.consola.datos;

import java.util.Calendar;
import java.util.Date;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.bspBusiness.BizBSPUser;
import pss.bsp.consolid.model.liquidacion.acumulado.BizLiqAcum;
import pss.bsp.consolid.model.liquidacion.header.BizLiqHeader;
import pss.bsp.contrato.BizContrato;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.nivel.JTipoPremioNivelSobreTotalPorFamilia;
import pss.bsp.gds.BizInterfazNew;
import pss.common.event.action.BizSqlEventAction;
import pss.common.event.action.GuiSqlEventActions;
import pss.common.event.action.history.BizSqlEventHistory;
import pss.common.event.sql.BizSqlEvent;
import pss.common.security.BizUsuario;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.services.JExec;
import pss.core.services.fields.JCurrency;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.collections.JIterator;
import pss.tourism.interfaceGDS.log.BizInterfaceLog;
import pss.tourism.pnr.BizPNRReserva;
import pss.tourism.pnr.BizPNRTicket;

public class BizFuenteDato extends JRecord {

	JString pCompany = new JString();
	JCurrency pTotal = new JCurrency() {
		public void preset() throws Exception {
			pTotal.setValue(getTotal());
		};
		public int getCustomPrecision() throws Exception {
			return 0;
		}
		
		public boolean hasCustomPrecision() throws Exception {
			return true;
		}
	};
	JCurrency pHoy = new JCurrency() {
		public void preset() throws Exception {
			pHoy.setValue(getHoy());
		};
		public int getCustomPrecision() throws Exception {
			return 0;
		}
		
		public boolean hasCustomPrecision() throws Exception {
			return true;
		}
	};
	JLong pClientesOk = new JLong() {
		public void preset() throws Exception {
			pClientesOk.setValue(getClientesOk());
		};
	};
	JLong pClientesError = new JLong() {
		public void preset() throws Exception {
			pClientesError.setValue(getClientesError());
		};
	};
	private long getExpireReservas() throws Exception {
		long fx =BizBSPUser.getUsrBSP().getBspConsola().getConfigView().getExpireReservas();
		if (fx==0) return 10;
		return fx;
	}
	public long getReservas() throws Exception {
		JRecords<BizPNRReserva> x = new JRecords<BizPNRReserva>(BizPNRReserva.class);
		x.dontThrowException(true);
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		x.addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else 		x.addFilter("company", getCompany());
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH,(int) getExpireReservas()*-1);
		
		x.addFilter("creation_date", cal.getTime(), ">=");
		return (long) x.selectCount();
	}
	public long getReportesProgramados() throws Exception {
//		GuiSqlEventActions wins = new GuiSqlEventActions();
//		wins.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
//		wins.getRecords().addFilter("tipo_periodicidad", BizSqlEventAction.SOLOUNAVEZ, "<>");
//		wins.getRecords().addFilter("action", BizSqlEventAction.NOTIF, "<>");

		JRecords<BizSqlEventAction> t= new JRecords<BizSqlEventAction>(BizSqlEventAction.class);
		t.addFilter("company", BizUsuario.getUsr().getCompany());
		t.addFilter("tipo_periodicidad", BizSqlEventAction.SOLOUNAVEZ, "<>");
		t.addFilter("action", BizSqlEventAction.NOTIF, "<>");
		return t.selectCount();
//		BizInterfazNew x = new BizInterfazNew();
//		x.dontThrowException(true);
//		x.read(getCompany());
//		return (long) x.getBookingMensual();
	}
	public Date getLastPnr() throws Exception {
		BizInterfazNew x = new BizInterfazNew();
		x.dontThrowException(true);
		x.read(getCompany());
		return x.getLastPnr();
	}
	JCurrency pBookingTotal = new JCurrency() {
		public void preset() throws Exception {
			pBookingTotal.setValue(getReservas());
		};
		public int getCustomPrecision() throws Exception {
			return 0;
		}
		
		public boolean hasCustomPrecision() throws Exception {
			return true;
		}
	};
	JCurrency pReportesProgramados = new JCurrency() {
		public void preset() throws Exception {
			pReportesProgramados.setValue(getReportesProgramados());
		};
		public int getCustomPrecision() throws Exception {
			return 0;
		}
		
		public boolean hasCustomPrecision() throws Exception {
			return true;
		}
	};
	JDate pLastPnr = new JDate() {
		public void preset() throws Exception {
			pLastPnr.setValue(getLastPnr());
		};
	};
	JLong pContratosActivos = new JLong() {
		public void preset() throws Exception {
			pContratosActivos.setValue(getTotalContratos());
		};
	};
	JCurrency pUltimaTotalFacturado = new JCurrency() {
		public void preset() throws Exception {
			pUltimaTotalFacturado.setSimbolo(true);
			pUltimaTotalFacturado.setMoneda(BizUsuario.getUsr().getObjCountry().GetMonedaLocal());
			if (pUltimaTotalFacturado.isRawNull()) 	calculeLiquidaciones();
		};
		
	};
	JCurrency pUltimaTotalComision = new JCurrency() {
		public void preset() throws Exception {
			pUltimaTotalComision.setSimbolo(true);
			pUltimaTotalComision.setMoneda(BizUsuario.getUsr().getObjCountry().GetMonedaLocal());
			if (pUltimaTotalComision.isRawNull()) 	calculeLiquidaciones();
		};
		
	};
	JCurrency pTotalFacturado = new JCurrency() {
		public void preset() throws Exception {
			pTotalFacturado.setSimbolo(true);
			pTotalFacturado.setMoneda(BizUsuario.getUsr().getObjCountry().GetMonedaLocal());
			if (pTotalFacturado.isRawNull()) 	calculeLiquidaciones();
		};
		
	};
	JCurrency pTotalComision = new JCurrency() {
		public void preset() throws Exception {
			pTotalComision.setSimbolo(true);
			pTotalComision.setMoneda(BizUsuario.getUsr().getObjCountry().GetMonedaLocal());
			if (pTotalComision.isRawNull()) 	calculeLiquidaciones();
		};
		
	};
	JCurrency pGananciaAnual = new JCurrency() {
		public void preset() throws Exception {
			pGananciaAnual.setSimbolo(true);
			pGananciaAnual.setMoneda(BizUsuario.getUsr().getObjCountry().GetMonedaLocal());
			getGananciaAnual();
		};
		
	};
	JCurrency pGananciaMensual = new JCurrency() {
		public void preset() throws Exception {
			pGananciaMensual.setSimbolo(true);
			pGananciaMensual.setMoneda(BizUsuario.getUsr().getObjCountry().GetMonedaLocal());
			getGananciaMensual();
		};
	};

	JCurrency pGananciaAnualDolares = new JCurrency() {
		public void preset() throws Exception {
			pGananciaAnualDolares.setSimbolo(true);
			pGananciaAnualDolares.setMoneda("USD");
			getGananciaAnual();
		};
	};
	JCurrency pGananciaMensualDolares = new JCurrency() {
		public void preset() throws Exception {
			pGananciaMensualDolares.setSimbolo(true);
			pGananciaMensualDolares.setMoneda("USD");
			getGananciaMensual();
		};
	};


	public void setCompany(String value) throws Exception {
		this.pCompany.setValue(value);
	}
	
	public String getCompany() throws Exception {
		return this.pCompany.getValue();
	}
	public long getTotal() throws Exception {
		BizInterfazNew x = new BizInterfazNew();
		x.dontThrowException(true);
  	 if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		x.addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
   	} 
   	else  x.addFilter("company", getCompany());
		return x.SelectSumLong("tick_tot");
//		return (long) x.getTicketTotal();
	}
	public long getHoy() throws Exception {
		BizPNRTicket t = new BizPNRTicket();
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		t.addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
  		t.addFixedFilter(" ( company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getTicketCompany()+") and customer_id_reducido = '"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"') ");
  	} else t.addFilter("company", getCompany());
		t.addFilter("creation_date", new Date());

		return t.selectCount();
	}
	public long getClientesOk() throws Exception {
		BizInterfaceLog t= new BizInterfaceLog();
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) 
  		t.addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	else if (BizUsuario.getUsr().getCompany().equals("YTEC"))
			t.addFilter("company","TRADYTEC");
  	else if (!BizUsuario.getUsr().getCompany().equals("DEFAULT"))
				t.addFilter("company", BizUsuario.getUsr().getCompany());
		return t.selectCount();
	}
	
	
	public long getClientesError() throws Exception {
		BizInterfaceLog t= new BizInterfaceLog();
		Calendar hace10Mintutos = Calendar.getInstance();
		hace10Mintutos.setTime(new Date());
		hace10Mintutos.add(Calendar.MINUTE, -360);
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) 
  		t.addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
   	else if (BizUsuario.getUsr().getCompany().equals("YTEC"))
			t.addFilter("company","TRADYTEC");
    else if (!BizUsuario.getUsr().getCompany().equals("DEFAULT"))
			t.addFilter("company", BizUsuario.getUsr().getCompany());
		t.addFilter("lastecho", JDateTools.DateToString(hace10Mintutos.getTime(), "yyyy-MM-dd HH:mm:ss"), JObject.JDATE, "<=", "AND", "");
//		t.addFilter("lastecho", hace10Mintutos.getTime(),"<=");
		return t.selectCount();
	}
	public long getTotalContratos() throws Exception {
		JRecords<BizContrato> t= new JRecords<BizContrato>(BizContrato.class);
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		t.addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
   		t.addFixedFilter("where company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getTicketCompany()+") and ( bsp_contrato.id in (select contrato from bsp_prorrateo where contrato = bsp_contrato.id and (cliente='ALL' or cliente like '%"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"%'))) ");
  	} else t.addFilter("company", BizUsuario.getUsr().getCompany());
		t.addFilter("active", "S");
		return t.selectCount();
	}

	public double calculeLiquidaciones() throws Exception {
		JRecords<BizLiqAcum> t= new JRecords<BizLiqAcum>(BizLiqAcum.class);
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) 
  		t.addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	else if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
    		t.addFixedFilter("where company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getTicketCompany()+") and ( dk ='"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"') ");
   	} else 
  		t.addFilter("company", BizUsuario.getUsr().getCompany());
  	
		t.addFilter("estado", BizLiqHeader.PUBLICADO);
		t.addFilter("fecha_desde", JDateTools.getFirstDayOfYear(new Date()),">=");
		t.addFilter("fecha_desde", JDateTools.getLastDayOfYear(new Date()), "<=");
		t.addOrderBy("fecha_desde","desc");
		double totalFacturado=0;
		double totalComision=0;
		boolean first=true;
		JIterator<BizLiqAcum> it = t.getStaticIterator();
		while (it.hasMoreElements()) {
			BizLiqAcum liq = it.nextElement();
			if (first) {
				pUltimaTotalFacturado.setValue(liq.getTotalFacturado());
				pUltimaTotalComision.setValue(liq.getTotalComision());
				first=false;
			}
			totalFacturado+=liq.getTotalFacturado();
			totalComision+=liq.getTotalComision();
			first=false;

		}
		pTotalFacturado.setValue(totalFacturado);
		pTotalComision.setValue(totalComision);
		return 0;
	}
	public double getGananciaAnual() throws Exception {
		JRecords<BizContrato> t= new JRecords<BizContrato>(BizContrato.class);
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) 
  		t.addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	else if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
    		t.addFixedFilter("where company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getTicketCompany()+") and ( bsp_contrato.id in (select contrato from bsp_prorrateo where contrato = bsp_contrato.id and (cliente='ALL' or cliente like '%"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"%'))) ");
   	} else 
  		t.addFilter("company", BizUsuario.getUsr().getCompany());
  	t.addFilter("tipo_contrato", "C");
		t.addFilter("active", "S");
		t.addFilter("fecha_desde", JDateTools.getFirstDayOfYear(new Date()),">=");
		t.addFilter("fecha_desde", JDateTools.getLastDayOfYear(new Date()), "<=");
		double ganancia=0;
		double gananciaDolares=0;
		JIterator<BizContrato> it = t.getStaticIterator();
		while (it.hasMoreElements()) {
			BizContrato contrato = it.nextElement();
			ganancia+=contrato.getGanancia();
			gananciaDolares+=contrato.getGananciaDolares();

		}
		pGananciaAnual.setValue(ganancia);
		pGananciaAnualDolares.setValue(gananciaDolares);
		return ganancia;
	}
	public double getGananciaMensual() throws Exception {
		JRecords<BizContrato> t= new JRecords<BizContrato>(BizContrato.class);
		
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) 
  		t.addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	 else if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
    		t.addFixedFilter("where company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getTicketCompany()+") and ( bsp_contrato.id in (select contrato from bsp_prorrateo where contrato = bsp_contrato.id and (cliente='ALL' or cliente like '%"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"%'))) ");
   	}else
  		t.addFilter("company", BizUsuario.getUsr().getCompany());
  	t.addFilter("tipo_contrato", "C");
  	
		t.addFilter("active", "S");
		t.addFilter("fecha_desde", JDateTools.getFirstDayOfMonth(new Date()),">=");
		t.addFilter("fecha_desde", JDateTools.getLastDayOfMonth(new Date()), "<=");
		double ganancia=0;
		double gananciaDolares=0;
		JIterator<BizContrato> it = t.getStaticIterator();
		while (it.hasMoreElements()) {
			BizContrato contrato = it.nextElement();
			ganancia+=contrato.getGanancia();
			gananciaDolares+=contrato.getGananciaDolares();

		}
		pGananciaMensual.setValue(ganancia);
		pGananciaMensualDolares.setValue(gananciaDolares);
		return ganancia;
	}




	/**
   * Constructor de la Clase
   */
  public BizFuenteDato() throws Exception {
  }
  
  
  
  public void createProperties() throws Exception {
    this.addItem( "total", pTotal );
    this.addItem( "hoy", pHoy );
    this.addItem( "cliente_ok", pClientesOk );
  	this.addItem( "cliente_error", pClientesError );
    this.addItem( "booktotal", pBookingTotal );
    this.addItem( "repprog", pReportesProgramados );
    this.addItem( "last_pnr", pLastPnr );
  	this.addItem( "contratos_activos", pContratosActivos );
  	this.addItem( "ganancia_anual", pGananciaAnual );
  	this.addItem( "ganancia_mensual", pGananciaMensual );
  	this.addItem( "ganancia_anual_usa", pGananciaAnualDolares );
  	this.addItem( "ganancia_mensual_usa", pGananciaMensualDolares );
  	this.addItem( "ultima_total_facturado", pUltimaTotalFacturado );
   	this.addItem( "ultima_total_comision", pUltimaTotalComision );
   	this.addItem( "total_facturado", pTotalFacturado );
   	this.addItem( "total_comision", pTotalComision );

  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( FIELD, "total", "total", true, false, 15 );
    this.addFixedItem( FIELD, "hoy", "hoy", true, false, 15 );
    this.addFixedItem( FIELD, "cliente_ok", "cliente_ok", true, false, 15 );
    this.addFixedItem( FIELD, "cliente_error", "cliente_error", true, false, 15 );
    this.addFixedItem( FIELD, "booktotal", "booking total", true, false, 15 );
    this.addFixedItem( FIELD, "repprog", "reportes programados", true, false, 15 );
    this.addFixedItem( FIELD, "last_pnr", "last pnr", true, false, 15 );
    this.addFixedItem( FIELD, "contratos_activos", "contratos activos", true, false, 15 );
    this.addFixedItem( FIELD, "ganancia_anual", "Contratos mon.local", true, false, 18, 2 );
    this.addFixedItem( FIELD, "ganancia_mensual", "Contratos mon.local", true, false, 18, 2 );
    this.addFixedItem( FIELD, "ganancia_anual_usa", "Contratos en dolares", true, false, 18, 2 );
    this.addFixedItem( FIELD, "ganancia_mensual_usa", "Contratos en dolares", true, false, 18, 2 );
    this.addFixedItem( FIELD, "ultima_total_facturado", "Últ. facturado", true, false, 18, 2 );
    this.addFixedItem( FIELD, "ultima_total_comision", "Últ. Comisión", true, false, 18, 2 );
    this.addFixedItem( FIELD, "total_facturado", "Total facturado", true, false, 18, 2 );
    this.addFixedItem( FIELD, "total_comision", "Total comisión", true, false, 18, 2 );

  }

  public  void processLimpiar() throws Exception {
  	String sql="";
		JBaseRegistro reg;
  	sql="DELETE FROM TUR_PNR_SEGMENTOAEREO USING TUR_PNR_BOLETO where TUR_PNR_BOLETO.interface_id = TUR_PNR_SEGMENTOAEREO.interface_id AND  creation_date>'15/03/2016'";
		reg = JBaseRegistro.recordsetFactory();
		reg.Execute(sql);
		reg.close();
  	sql="DELETE FROM TUR_PNR_IMPUESTOS USING TUR_PNR_BOLETO where TUR_PNR_BOLETO.interface_id = TUR_PNR_IMPUESTOS.interface_id AND  creation_date>'15/03/2016'";
		reg = JBaseRegistro.recordsetFactory();
		reg.Execute(sql);
		reg.close();
  	sql="DELETE FROM TUR_PNR_BOLETO where creation_date>'15/03/2016'";
		reg = JBaseRegistro.recordsetFactory();
		reg.Execute(sql);
		reg.close();
		Calendar fecha = Calendar.getInstance();
		fecha.set(2016, 2,15);

		JRecords<BizSqlEvent> recs = new JRecords<BizSqlEvent>(BizSqlEvent.class);
		JIterator<BizSqlEvent> it = recs.getStaticIterator();
		while (it.hasMoreElements()) {
			BizSqlEvent ev = it.nextElement();
			ev.processPopulate(null,fecha.getTime());
		}
		
//		JRecords<BizDetalle> recsD = new JRecords<BizDetalle>(BizDetalle.class);
//		JIterator<BizDetalle> itD = recsD.getStaticIterator();
//		while (itD.hasMoreElements()) {
//			BizDetalle ev = itD.nextElement();
//			ev.processPopulate(fecha.getTime());
//		}
		
  }
  
  public void execProcessLimpiar() throws Exception {
		JExec oExec = new JExec(this, "processLimpiar") {

			@Override
			public void Do() throws Exception {
				processLimpiar();
			}
		};
		oExec.execute();
	}

}

