package pss.bsp.contrato.detalle;

import java.awt.event.KeyEvent;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.contrato.conocidos2.GuiContratoConocidoV2;
import pss.bsp.contrato.detalle.nivel.GuiNiveles;
import pss.bsp.contrato.detalle.prorrateo.GuiProrrateos;
import pss.bsp.contrato.detalle.prorrateo.tickets.BizTicketProrrateo;
import pss.bsp.contrato.detalle.prorrateo.tickets.GuiTicketProrrateos;
import pss.bsp.contrato.detalle.variaciones.GuiVariacionParticulares;
import pss.bsp.contrato.detalleAvianca.grilla.GuiObjetivosGrillas;
import pss.bsp.contrato.detalleAvianca.objetivos.GuiObjetivosAviancas;
import pss.bsp.contrato.detalleCopa.objetivos.GuiObjetivosCopas;
import pss.bsp.contrato.detalleCopaPorRutas.objetivos.GuiObjetivosCopasPorRutas;
import pss.bsp.contrato.detalleCopaWS.objetivos.GuiObjetivosCopasWS;
import pss.bsp.contrato.detalleLatamFamilia.calculo.GuiFamiliaLatamDetails;
import pss.bsp.contrato.detalleRutas.objetivos.GuiObjetivosRutas;
import pss.bsp.contrato.evaluate.GuiEvaluate;
import pss.bsp.contrato.logica.ILogicaContrato;
import pss.bsp.contrato.quevender.GuiQueVenders;
import pss.bsp.contrato.series.calculado.GuiSerieCalculadas;
import pss.bsp.event.GuiBSPSqlEvent;
import pss.common.event.sql.GuiSqlEvent;
import pss.common.security.BizUsuario;
import pss.core.data.interfaces.structure.IOrderByControl;
import pss.core.services.fields.JIntervalDate;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActFileGenerate;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActQueryActive;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;
import pss.tourism.pnr.GuiPNRReservas;

public class GuiDetalle extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiDetalle() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizDetalle(); }
  public int GetNroIcono()   throws Exception { return 15011; }
  public String GetTitle()   throws Exception { return GetcDato().getDescripcion().equals("")?getTitle():GetcDato().getDescripcion(); }
  public String getTitle()   throws Exception { return "Objetivo"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception {
  	if (GetVision().equals("VIEW" ))
  		 return FormEditDetalle.class;
  	return FormDetalle.class; 
  }
  public Class<? extends JBaseForm> getFormNew() throws Exception { return FormEditDetalle.class; }
  public Class<? extends JBaseForm> getFormUpdate() throws Exception { return FormEditDetalle.class; }
  @Override
  public Class<? extends JBaseForm> getFormFlat() throws Exception {
  	return FormDetalleFlat.class;
  }
  public String  getKeyField() throws Exception { return "linea"; }
  public String  getDescripField() { return "variable"; }
  @Override
  public String getSearchFields() throws Exception {
  	return "descripcion";
  }
  public BizDetalle GetcDato() throws Exception { return (BizDetalle) this.getRecord(); }
  public BizDetalle GetxDato() throws Exception { 
  	return (BizDetalle) this.getRelativeWin().getRecord(); 
  }

  
  public void createActionMap() throws Exception {
   	this.addAction(50, "Tickets Objetivo", null, 5012, false, false, false, "Group" );
   	this.addAction(60, "Tickets B.Comis.", null, 5012, false, false, false, "Group" );
   	this.addAction(61, "Tickets Reembolsos", null, 5012, false, false, false, "Group" );
   	this.addAction(65, "Tickets Aux.", null, 5012, false, false, false, "Group" );
		this.addAction(20, "Recalcular", null, 15016, true, true, true, "Group").setMulti(true);;
   	this.addAction(30, "Historico", null, 406, false, false, false, "Group" );
   	this.addAction(31, "Objetivos", null, 406, false, false, false, "Group" );
   	this.addAction(32, "Objetivos", null, 406, false, false, false, "Group" );
   	this.addAction(33, "Grilla", null, 406, false, false, false, "Group" );
   	this.addAction(34, "Objetivos", null, 406, false, false, false, "Group" );
   	this.addAction(35, "Niveles", null, 406, false, false, false, "Group" );
   	this.addAction(36, "Objetivos", null, 406, false, false, false, "Group" );
   	this.addAction(37, "Prorrateo", null, 406, false, false, false, "Group" );
   	this.addAction(38, "Objetivos", null, 406, false, false, false, "Group" );
   	this.addAction(40, "Ver estimadores", null, 5012, true, true, true, "Group" );
		this.addAction(10, "Ver ind. Objetivo", null, 10020, true, true, true, "Group");
		this.addAction(15, "Ver ind. B.Comis.", null, 10020, true, true, true, "Group");
		this.addAction(17, "Ver ind. Aux.", null, 10020, true, true, true, "Group");
   	this.addAction(190, "Familia tarifaria", null, 406, false, false, false, "Group" );
 		BizAction a = addAction(90, "Imprimir",KeyEvent.VK_F7, null, 10050, true, true);
		a.setImprimir(true);
		a.setImportance(1);
   	this.addAction(100, "Extra info", null, 5133, true, true, true, "Group" );
   	this.addAction(110, "Calculo Objetivo", null, 5012, false, false, false, "Group" );
   	this.addAction(111, "Calculo por definicion", null, 5012, false, false, false, "Group" );
   	this.addAction(120, "Calculo B.Comisionable", null, 5012, false, false, false, "Group" );
   	this.addAction(130, "Calculo Auxiliar", null, 5012, false, false, false, "Group" );
  	this.addAction(200, "Tickets Prorrateo", null, 5012, false, false, false, "Group" );
  	this.addAction(210, "Copiar sig.Contrato", null, 5012, true, true, true, "Group" );
  	this.addAction(500, "Clonar" ,null, 84, true, true);
  	this.addAction(510, "Generar Modelo" ,null, 84, true, true);
  	this.addAction(520, "Copiar a base" ,null, 84, false, false);
  	this.addAction(700, "Evaluar Meta" ,null, 9, true, true);
  	this.addAction(701, "Evaluar Base Comisionable" ,null, 9, true, true);
  	this.addAction(702, "Evaluar Auxiliar" ,null, 9, true, true);

   	this.addAction(600, "Reserva meta", null, 5012, false, false, false, "Group" );
   	this.addAction(601, "Reserva base", null, 5012, false, false, false, "Group" );
   	this.addAction(602, "Reserva aux", null, 5012, false, false, false, "Group" );

  	super.createActionMap();
  }
  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getId()==10) return GetxDato().hasDetalle();
  	if (a.getId()==15) return GetxDato().hasDetalleGanancia();
  	if (a.getId()==17) return GetxDato().hasDetalleAuxiliar();
  	if (a.getId()==37) return GetxDato().isUpfront();
  	if (a.getId()==200) return GetxDato().isUpfront();
  	if (a.getId()==50) return GetxDato().hasDetalle();
  	if (a.getId()==60) return GetxDato().hasDetalleGanancia() || GetxDato().isCopaPorRutas();
  	if (a.getId()==61) return !GetxDato().isUpfront();
  	if (a.getId()==65) return GetxDato().hasDetalleAuxiliar();
  	if (a.getId()==40) return GetxDato().isDatamining();
  	if (a.getId()==700) return GetxDato().isDataminingRutas() && GetxDato().getObjEvent()!=null;
  	if (a.getId()==701) return GetxDato().isDataminingRutas() && GetxDato().getObjEventGanancia()!=null;
  	if (a.getId()==702) return GetxDato().isDataminingRutas() && GetxDato().getObjEventAuxiliar()!=null;
  	if (a.getId()==190) return GetxDato().isDataminingLatamFamilia();
//  	if (a.getId()==100) return BizUsuario.getUsr().isAnyAdminUser();
  	if (a.getId()==110) return GetxDato().hasDetalleDM();
  	if (a.getId()==111) return !GetxDato().hasDetalleDM()&&!GetxDato().hasDetalleGananciaDM();
  	if (a.getId()==120) return GetxDato().hasDetalleGananciaDM();
  	if (a.getId()==130) return GetxDato().hasDetalleAuxiliarDM();
  	if (a.getId()==210) return BizUsuario.getUsr().isAnyAdminUser();
  	if (a.getId()==510) return BizUsuario.getUsr().isAnyAdminUser();
   	if (a.getId()==30) return !BizBSPCompany.getObjBSPCompany(GetcDato().getCompany()).isDependant();

  	return super.OkAction(a);
  }
  
	@Override
	public JAct getSubmit(BizAction a) throws Exception {
		if (a.getId()==1) return new JActQueryActive(this.getRelativeWin());
		return super.getSubmit(a);
	}
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 30)  return new JActWins(getHistorico());
		if (a.getId() == 31)  return new JActWins(getObjetivos(a));
		if (a.getId() == 32)  return new JActWins(getObjetivoAvianca(a));
		if (a.getId() == 33)  return new JActWins(getObjetivoGrilla(a));
		if (a.getId() == 34)  return new JActWins(getObjetivoRutas(a));
		if (a.getId() == 36)  return new JActWins(getObjetivosWS(a));
		if (a.getId() == 37)  return new JActWins(getProrrateos(a));
		if (a.getId() == 38)  return new JActWins(getObjetivosPorRutas(a));

		if (a.getId() == 10)  return new JActQuery(getIndicador());
		if (a.getId() == 15)  return new JActQuery(getIndicadorGanancia());
		if (a.getId() == 17)  return new JActQuery(getIndicadorAuxiliar());
		if (a.getId() == 20)	return new JActSubmit(this) {
			@Override
			public void submit() throws Exception {
				GetxDato().execProcessPopulate();
				super.submit();
			}
		};
		if (a.getId() == 40)  return new JActWins(getEstimadores());
		if (a.getId() == 35)  return new JActWins(getNiveles());
		if (a.getId() == 50)  return new JActWins(GetxDato().getDetalle());
		if (a.getId() == 60)  return new JActWins(GetxDato().getDetalleGanancia());
		if (a.getId() == 61)  return new JActWins(GetxDato().getDetalleReembolsos());
		if (a.getId() == 65)  return new JActWins(GetxDato().getDetalleAuxiliar());
		if (a.getId() == 190) return new JActWins(getDetalleLatamFamilia());
		if (a.getId() == 200)  return new JActWins(getDetalleProrrateo());
		if (a.getId() == 90)	return new JActFileGenerate(this, a.getId()) {
				@Override
				public String generate() throws Exception {
					return imprimirResumen();
				}
		};
		if (a.getId() == 100)  return new JActWins(getQueVender());
		if (a.getId() == 110)  return new JActQuery(getDataminigIndicador());
		if (a.getId() == 111)  return new JActQuery(getViewEdit());
		if (a.getId() == 120)  return new JActQuery(getDataminigComisionable());
		if (a.getId() == 130)  return new JActQuery(getDataminigAuxiliar());
		if (a.getId() == 210)	return new JActSubmit(this) {
			@Override
			public void submit() throws Exception {
				GetxDato().execProcessCopiar();
				super.submit();
			}
		};
  	if (a.getId()==500) return new JActNew(getClon(),0,true,false,true);
  	if (a.getId()==510) return new JActNew(getModelo(),0,true,false,true);
		if (a.getId() == 520)	return new JActSubmit(this) {
			@Override
			public void submit() throws Exception {
				GetcDato().processClonarBaseYAux();
				super.submit();
			}
		};
		if (a.getId() == 600)  return new JActWins(getListaReservasMeta());
		if (a.getId() == 601)  return new JActWins(getListaReservasBase());
		if (a.getId() == 602)  return new JActWins(getListaReservasAux());
		
		if (a.getId() == 700)  return new JActQueryActive(getEvaluate(1));
		if (a.getId() == 701)  return new JActQueryActive(getEvaluate(2));
		if (a.getId() == 702)  return new JActQueryActive(getEvaluate(3));
		
		return super.getSubmitFor(a);
	}
  public GuiFamiliaLatamDetails getDetalleLatamFamilia() throws Exception {
		GuiFamiliaLatamDetails fam = new GuiFamiliaLatamDetails();
		fam.getRecords().addFilter("company", GetcDato().getCompany());
		fam.getRecords().addFilter("id_contrato", GetcDato().getId());
		fam.getRecords().addFilter("id_detalle", GetcDato().getLinea());
		fam.getRecords().addOrderBy("peso","ASC");
		return fam;
	}
  
  public GuiPNRReservas getListaReservasMeta() throws Exception {
  	GuiPNRReservas res = new GuiPNRReservas();
  	res.setRecords(GetcDato().getListaReservasMeta());
  	return res;
  }
  public GuiPNRReservas getListaReservasBase() throws Exception {
  	GuiPNRReservas res = new GuiPNRReservas();
  	res.setRecords(GetcDato().getListaReservasBase());
  	return res;
  }
  public GuiPNRReservas getListaReservasAux() throws Exception {
  	GuiPNRReservas res = new GuiPNRReservas();
  	res.setRecords(GetcDato().getListaReservasAux());
  	return res;
  }
	public String imprimirResumen() throws Exception {
		BizDetalle det = GetcDato();
		BizDetalle newdet = det.getObjLogicaInstance().getBiz();
		newdet.copyProperties(det);
		return newdet.imprimirResumen();
	}
	
	 public JWin getRelativeWin() throws Exception {
		  GuiDetalle win=this;
		 	ILogicaContrato logica = GetcDato().getObjLogicaInstance();
		 	GuiDetalle newwin = logica.getWin();
		 	if (!this.getClass().equals(newwin.getClass())) {
		 		win=newwin;
			 	BizDetalle biz = logica.getBiz();
			 	biz.copyProperties(this.GetcDato());
			 	win.setRecord(biz);
		 	} 
		 	return win;
		}

	 public JWins getQueVender() throws Exception {
			GuiQueVenders var = new GuiQueVenders();
			var.getRecords().addFilter("company", GetcDato().getCompany());
			var.getRecords().addFilter("id_contrato", GetcDato().getId());
			var.getRecords().addFilter("id_detalle", GetcDato().getLinea());
			var.getRecords().addOrderBy("id");
			return var;
		}
	public GuiSqlEvent getIndicador() throws Exception {
		GuiSqlEvent var = new GuiBSPSqlEvent();
		var.setRecord(GetxDato().getObjEvent());
		var.GetcDato().setFDesde(GetcDato().getObjContrato().getFechaDesde());
		var.GetcDato().setFHasta(GetcDato().getObjContrato().getFechaHasta());
		return var;
	}
	public JWin getViewEdit() throws Exception {
		GuiDetalle var = this.GetcDato().getObjLogicaInstance().getWin();
  	var.GetcDato().copyProperties(this.GetcDato());
  	var.SetVision("VIEW");
  	return var;
  }
	public JWin getDataminigIndicador() throws Exception {
		GuiSqlEvent var = getIndicador();
  	var.GetcDato().fillVirtuals();
  	var.SetVision("E");
  	return var;
  }
	public JWin getDataminigComisionable() throws Exception {
		GuiSqlEvent var = (GuiSqlEvent) getIndicadorGanancia();
  	var.GetcDato().fillVirtuals();
  	var.SetVision("E");
  	return var;
  }
	public JWin getDataminigAuxiliar() throws Exception {
		GuiSqlEvent var =  (GuiSqlEvent)getIndicadorAuxiliar();
  	var.GetcDato().fillVirtuals();
  	var.SetVision("E");
  	return var;
  }
	public JWin getEvaluate(long id) throws Exception {
		GuiEvaluate var =  new GuiEvaluate();
  	var.GetcDato().setId(GetcDato().getLinea());
  	var.GetcDato().setEvent(id);
  	return var;
  }
	public JWin getIndicadorGanancia() throws Exception {
		GuiSqlEvent var = new GuiBSPSqlEvent();
		var.setRecord(GetxDato().getObjEventGanancia());
		var.GetcDato().setFDesde(GetcDato().getObjContrato().getFechaDesde());
		var.GetcDato().setFHasta(GetcDato().getObjContrato().getFechaHasta());
		return var;
	}
	public JWin getIndicadorAuxiliar() throws Exception {
		GuiSqlEvent var = new GuiBSPSqlEvent();
		var.setRecord(GetxDato().getObjEventAuxiliar());
		var.GetcDato().setFDesde(GetcDato().getObjContrato().getFechaDesde());
		var.GetcDato().setFHasta(GetcDato().getObjContrato().getFechaHasta());
		return var;
	}
	
	 public JWins getDetalleProrrateo() throws Exception {
	  	GuiTicketProrrateos	c = new GuiTicketProrrateos();
			c.getRecords().addFilter("company", GetxDato().getCompany());
			c.getRecords().addFilter("contrato", GetxDato().getId());
			c.getRecords().addFilter("detalle", GetxDato().getLinea());
			c.getRecords().addFilter("fecha", new JIntervalDate(GetxDato().getFechaDesdeCalculo(),GetxDato().getFechaCalculo()));
			c.getRecords().setParent(this.GetxDato());
			c.getRecords().addCorteControl("customer_id_reducido", new IOrderByControl<BizTicketProrrateo>() {
				
					@Override
					public String getDescription(BizTicketProrrateo rec) throws Exception {
						return rec.getCustomer();
					}
					
					@Override
						public String getID(BizTicketProrrateo rec) throws Exception {
							return getDescription(rec);
						}
				});
			
	  	return c; 	
	  }

	 public JWins getHistorico() throws Exception {
	  	GuiSerieCalculadas	c = new GuiSerieCalculadas();
	  	c.getRecords().addFilter("modelo", this.GetxDato().getModelo());
	  	c.getRecords().addFilter("variable", this.GetxDato().getVariable());
	  	c.getRecords().addFilter("company", this.GetxDato().getCompany());
	  	c.getRecords().addFilter("fecha", this.GetxDato().getObjContrato().getFechaHasta(),"<=").setDynamic(true);
	  	c.getRecords().addFilter("fecha", this.GetxDato().getObjContrato().getFechaDesde(),">=").setDynamic(true);
	  	c.getRecords().addOrderBy("fecha","DESC");
	  	c.getRecords().setParent(this.GetxDato());
	  	return c;
	  	
	  }
	 public JWins getObjetivos(BizAction a) throws Exception {
	  	GuiObjetivosCopas	c = new GuiObjetivosCopas();
			c.getRecords().addFilter("company", GetxDato().getCompany());
			c.getRecords().addFilter("contrato", GetxDato().getId());
			c.getRecords().addFilter("detalle", GetxDato().getLinea());
			
	  	return c;
	  	
	  }
	 public JWins getProrrateos(BizAction a) throws Exception {
	  	GuiProrrateos	c = new GuiProrrateos();
			c.getRecords().addFilter("company", GetxDato().getCompany());
			c.getRecords().addFilter("contrato", GetxDato().getId());
			c.getRecords().addFilter("detalle", GetxDato().getLinea());
			
	  	return c;
	  	
	  }
	 public JWins getObjetivosWS(BizAction a) throws Exception {
	  	GuiObjetivosCopasWS	c = new GuiObjetivosCopasWS();
			c.getRecords().addFilter("company", GetxDato().getCompany());
			c.getRecords().addFilter("contrato", GetxDato().getId());
			c.getRecords().addFilter("detalle", GetxDato().getLinea());
			
	  	return c;
	  	
	  }
	 public JWins getObjetivosPorRutas(BizAction a) throws Exception {
	  	GuiObjetivosCopasPorRutas	c = new GuiObjetivosCopasPorRutas();
			c.getRecords().addFilter("company", GetxDato().getCompany());
			c.getRecords().addFilter("contrato", GetxDato().getId());
			c.getRecords().addFilter("detalle", GetxDato().getLinea());
			
	  	return c;
	  	
	  }
	 public JWins getObjetivoRutas(BizAction a) throws Exception {
	  	GuiObjetivosRutas	c = new GuiObjetivosRutas();
			c.getRecords().addFilter("company", GetxDato().getCompany());
			c.getRecords().addFilter("contrato", GetxDato().getId());
			c.getRecords().addFilter("detalle", GetxDato().getLinea());
			c.SetVision(GetcDato().getPax()?"PAX":"MS");
	  	return c;
	  	
	  }
	 public JWins getObjetivoAvianca(BizAction a) throws Exception {
		  GuiObjetivosAviancas	c = new GuiObjetivosAviancas();
			c.getRecords().addFilter("company", GetxDato().getCompany());
			c.getRecords().addFilter("contrato", GetxDato().getId());
			c.getRecords().addFilter("detalle", GetxDato().getLinea());
			c.setParent(this);
	  	return c;
	  	
	  }
	 public JWins getObjetivoGrilla(BizAction a) throws Exception {
		  GuiObjetivosGrillas	c = new GuiObjetivosGrillas();
			c.getRecords().addFilter("company", GetxDato().getCompany());
			c.getRecords().addFilter("contrato", GetxDato().getId());
			c.getRecords().addFilter("detalle", GetxDato().getLinea());
			
	  	return c;
	  	
	  }
	 public JWins getEstimadores() throws Exception {
	  	GuiVariacionParticulares	c = new GuiVariacionParticulares();
	  	c.getRecords().addFilter("id_contrato", this.GetxDato().getId());
	  	c.getRecords().addFilter("linea_contrato", this.GetxDato().getLinea());
	  	c.getRecords().addFilter("company", this.GetxDato().getCompany());
	  	c.getRecords().addOrderBy("fecha_desde");
	  	
	  	c.getRecords().setParent(this.GetxDato());
	  	return c;
	  	
	  }
	 public JWins getNiveles() throws Exception {
	  	GuiNiveles	c = new GuiNiveles();
	  	c.getRecords().addFilter("id", this.GetxDato().getId());
	  	c.getRecords().addFilter("linea", this.GetxDato().getLinea());
	  	c.getRecords().addFilter("company", this.GetxDato().getCompany());
	  	c.getRecords().addOrderBy("valor");
	  	c.getRecords().setParent(this.GetxDato());
	  	return c;
	  	
	  }
 
	  @Override
		public boolean canConvertToURL() throws Exception {
			return false;
		}
	  

  	public JWin getClon() throws Exception {
		  ILogicaContrato logica = GetcDato().getObjLogicaInstance();
		 	GuiDetalle newDoc = logica.getWin();
		 	newDoc.setRecord(GetcDato().getClon());
  //		newDoc.setDropListener(this);
  		newDoc.GetcDato().SetVision("CLON");
  		return newDoc;
  	}
  	
  	public JWin getModelo() throws Exception {
		  ILogicaContrato logica = GetcDato().getObjLogicaInstance();
		 	GuiDetalle newDoc = logica.getWin();
		 	newDoc.setRecord(GetcDato().getClon());
  //		newDoc.setDropListener(this);
  		newDoc.GetcDato().SetVision("CLON");
  		GuiContratoConocidoV2 conocido = new GuiContratoConocidoV2();
  		
  		conocido.GetcDato().setAerolineas(GetcDato().getIdAerolinea());
  		conocido.GetcDato().setTipoContrato(newDoc.GetcDato().getLogicaContrato());
  		conocido.GetcDato().setDescripcion(GetcDato().getDescripcion());
  		conocido.GetcDato().setPeriodo(GetcDato().getPeriodo());
  		if (GetcDato().getFirstNivel()!=null) {
	  		conocido.GetcDato().setTipoObjetivo(GetcDato().getFirstNivel().getTipoObjetivo());
	  		conocido.GetcDato().setTipoPremio(GetcDato().getFirstNivel().getTipoPremio());
  		}
  		conocido.GetcDato().setTipoContrato(GetcDato().getLogicaContrato());
  		conocido.GetcDato().setObjDetalle(newDoc.GetcDato());
  		conocido.GetcDato().setObjetivoExtra(GetcDato().getKicker());
  		conocido.GetcDato().setPais(BizBSPCompany.getObjBSPCompany(GetcDato().getCompany()).getPais());
  		return conocido;
  	}
 }
