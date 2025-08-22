package pss.bsp.contrato.detalle.prorrateo.tickets;

import java.util.Date;

import pss.JPath;
import pss.bsp.GuiModuloBSP;
import pss.bsp.bspBusiness.BizBSPUser;
import pss.bsp.contrato.detalle.prorrateo.header.BizHeaderProrrateo;
import pss.common.event.action.GuiSqlEventAction;
import pss.common.event.action.history.BizSqlEventHistory;
import pss.common.event.action.history.GuiSqlEventHistory;
import pss.common.layoutWysiwyg.BizPlantilla;
import pss.common.regions.company.BizCompany;
import pss.core.services.records.JFilterMap;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActBack;
import pss.core.win.submits.JActFileGenerate;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActQuery;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.responsiveControls.JFormIntervalCDatetimeResponsive;

public class GuiTicketProrrateos extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiTicketProrrateos() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 15011; } 
  public String  GetTitle()    throws Exception  { return "Tickets"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiTicketProrrateo.class; }
  JFilterMap filter;


  public JFilterMap getFilterMap() {
		return filter;
	}


	public void setFilterMap(JFilterMap action) {
		this.filter = action;
	}
  @Override
	public BizTicketProrrateos ObtenerDatos() {
		return new BizTicketProrrateos();
	}

	public long selectSupraCount() throws Exception {
		return 0;
	}
	public BizTicketProrrateos getcRecords() throws Exception {
		return (BizTicketProrrateos) this.getRecords();
	}
	
	
  public void createActionMap() throws Exception {
 //   addActionNew( 1, "Nuevo Objetivo" );
  	addAction(50, "Compartir", null, 10078, true, true);
 // 	addAction(55, "Imprimir", null, 10050, true, true).setImprimir(true);
  }

	@Override
	public boolean OkAction(BizAction a) throws Exception {
		if (a.getId()==50) return true;
		if (a.getId()==55) return true;
		
		return a.getId()<5;
	}

  @Override
  public JAct getSubmitFor(final BizAction a) throws Exception {
  	if (a.getId()==50) return new JActNew(this.getCompartir(a), 4);
  	if (a.getId()==55) return new JActFileGenerate(this) { 
			public String generate() throws Exception {
				return imprimir(a);
			};
		};

  	return super.getSubmitFor(a);
  }
  
  public String imprimir(BizAction a) throws Exception {
		String file = this.hashCode()+".html";
		a.addFilter("periodo",getRecords().getFilterIntervalValue("fecha"));
		String content = GuiModuloBSP.getBSPConsolaUsuario().getHtmlView(71,"html",a.getFilterMap()==null?getRecords().createFilterMap():a.getFilterMap());
		JTools.writeStringToFile(content,JPath.PssPathTempFiles()+"/"+file);
		return file; 	
  }

	public JWin getCompartir(BizAction a) throws Exception {
		GuiSqlEventAction sql = new GuiSqlEventAction();
		sql.getRecord().addFilter("company", BizBSPUser.getUsrBSP().getCompany());
		sql.getRecord().addFilter("class_evento", BizHeaderProrrateo.class.getCanonicalName());
		sql.getRecord().addFilter("id_evento", BizBSPUser.getUsrBSP().getCompany());
		sql.getRecord().addFilter("descripcion", "Liquidación");
		sql.getRecord().addFilter("parametros",( a.getFilterMap()!=null)? a.getFilterMap().serialize():this.getRecords().createFilterMap().serialize());
  	BizPlantilla pl = BizCompany.getObjPlantilla(BizBSPUser.getUsrBSP().getCompany(), "sys_prorrateo");
		if (pl!=null) sql.getRecord().addFilter("id_plantilla",pl.getId());
		sql.setDropListener(this);
		this.setFilterMap(a.getFilterMap());
		return sql;
	}

  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddColumnaLista("Cliente","ticket.customer_id_reducido");
    zLista.AddColumnaLista("ticket.NumeroBoleto");
    zLista.AddColumnaLista("ticket.nombre_pasajero");
    
    zLista.AddColumnaLista("ticket.creation_date");
    zLista.AddColumnaLista("ticket.CodigoAerolinea");
    
    zLista.AddColumnaLista("ticket.air_intinerary");
    zLista.AddColumnaLista("ticket.carrier_intinerary");
    zLista.AddColumnaLista("ticket.class_intinerary");
    zLista.AddColumnaLista("ticket.fecha_intinerary");

    zLista.AddColumnaLista("ticket.tarifa_factura");
    zLista.AddColumnaLista("porcentaje");
    zLista.AddColumnaLista("comision");
    
  }
  
  @Override
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
  	if (getFilterValue("detalle")==null ) {
  		JFormIntervalCDatetimeResponsive d= zFiltros.addIntervalCDateResponsive("Periodo", "fecha");
  		d.SetValorDefault(JDateTools.getFirstDayOfMonth(new Date()),JDateTools.getLastDayOfMonth(new Date()));
 		}
  	super.ConfigurarFiltros(zFiltros);
  }
  
	public void createTotalizer(JWinList oLista) throws Exception {
//		oLista.addTotalizerText("ruta", "Totales"); // un texto
//		oLista.addTotalizer("cantidad", 0, JTotalizer.OPER_SUM); // la suma del
//		oLista.addTotalizer("total", 0, JTotalizer.OPER_SUM); // la suma del
//		oLista.addTotalizer("faltante", 0, JTotalizer.OPER_SUM); // la suma del
//		oLista.addTotalizer("puntos", 0, JTotalizer.OPER_SUM); // la suma del
//		oLista.addTotalizer("ingreso", 2, JTotalizer.OPER_SUM); // la suma del
	}

	
  @Override
  public JAct Drop(JBaseWin zBaseWin) throws Exception {
   	if (zBaseWin instanceof GuiSqlEventAction) {
  		GuiSqlEventAction action = (GuiSqlEventAction)zBaseWin;
  		if (action.GetcDato().isAccionDOWNLOAD()) {
  			return new JActFileGenerate(action) { 
  				public String generate() throws Exception {
  		  		BizSqlEventHistory hist=((GuiSqlEventAction)getResult()).GetcDato().getObjSqlEvent().generarAviso(getFilterMap(),((GuiSqlEventAction)getResult()).GetcDato(),false);
  					return hist.getArchivoAsociado();
  				};
  			};
  		}
  		BizSqlEventHistory hist = action.GetcDato().execProcessExecute(this.getFilterMap());
  		if (hist==null) return new JActBack(this);
  		GuiSqlEventHistory ghist = new GuiSqlEventHistory();
  		ghist.setRecord(hist);
  		return new JActQuery(ghist);
  	}
   	return super.Drop(zBaseWin);
  }





}
