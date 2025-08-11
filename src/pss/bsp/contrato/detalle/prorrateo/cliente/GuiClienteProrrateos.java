package pss.bsp.contrato.detalle.prorrateo.cliente;

import java.util.Date;

import org.apache.commons.codec.net.URLCodec;

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

public class GuiClienteProrrateos  extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiClienteProrrateos() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 15011; } 
  public String  GetTitle()    throws Exception  { return "Liquidación"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiClienteProrrateo.class; }
  JFilterMap filter;


  public JFilterMap getFilterMap() {
		return filter;
	}


	public void setFilterMap(JFilterMap action) {
		this.filter = action;
	}
  @Override
	public BizClienteProrrateos ObtenerDatos() {
		return new BizClienteProrrateos();
	}
  
	public BizClienteProrrateos getcRecords() throws Exception {
		return (BizClienteProrrateos) this.getRecords();
	}
	
	
  public void createActionMap() throws Exception {
  	addAction(50, "Compartir", null, 10078, true, true);
  	addAction(55, "Imprimir", null, 10050, true, true).setImprimir(true);
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
//		String file = this.hashCode()+".pdf";
//		
//		String content = GuiModuloBSP.getBSPConsolaUsuario().getHtmlView(71,"html",a.getFilterMap()==null?getRecords().createFilterMap():a.getFilterMap());
//		JTools.writeStringToFile(content,JPath.PssPathTempFiles()+"/"+file);
//		return file; 	
  	JFilterMap map =( a.getFilterMap()!=null)? a.getFilterMap():this.getRecords().createFilterMap();
		GuiSqlEventAction sql = new GuiSqlEventAction();
		sql.getRecord().addFilter("company", BizBSPUser.getUsrBSP().getCompany());
		sql.getRecord().addFilter("class_evento", BizHeaderProrrateo.class.getCanonicalName());
		sql.getRecord().addFilter("id_evento", BizBSPUser.getUsrBSP().getCompany());
		sql.getRecord().addFilter("descripcion", "Liquidación");
		sql.getRecord().addFilter("parametros", map.serialize());
  	
		BizPlantilla pl = BizCompany.getObjPlantilla(BizBSPUser.getUsrBSP().getCompany(), "sys_prorrateo");
		if (pl!=null) sql.getRecord().addFilter("id_plantilla",pl.getId());
		this.setFilterMap(a.getFilterMap());
		
		BizHeaderProrrateo obj=new BizHeaderProrrateo();
		obj.read(BizBSPUser.getUsrBSP().getCompany(),"",map);
		String s = new URLCodec().decode(pl.generateDocSimple(obj,sql.GetcDato()));
  	String file = "doc"+this.hashCode()+".html";
		String content = pl.htmlToHtml(file,true,s);
		//JTools.writeStringToFile(content,JPath.PssPathTempFiles()+"/"+file);
		return content;

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
  	zLista.AddColumnaLista("cliente");
  	zLista.AddColumnaLista("descripcion");
  	zLista.AddColumnaLista("numero");
  	zLista.AddColumnaLista("monto");
  	zLista.AddColumnaLista("comision");
//    super.ConfigurarColumnasLista(zLista);
  }
  
  @Override
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
 		JFormIntervalCDatetimeResponsive d= zFiltros.addIntervalCDateResponsive("Periodo", "periodo");
 		d.SetValorDefault(JDateTools.getFirstDayOfMonth(new Date()),JDateTools.getLastDayOfMonth(new Date()));

 		zFiltros.addEditResponsive("Cliente", "cliente");
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

	@Override
	public boolean hasSubDetail() throws Exception {
		return true;
	}



}
