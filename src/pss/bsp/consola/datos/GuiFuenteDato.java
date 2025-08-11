package pss.bsp.consola.datos;

import java.util.Calendar;
import java.util.Date;

import pss.bsp.bo.GuiInterfacesBO;
import pss.bsp.bo.GuiInterfazBO;
import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.bspBusiness.BizBSPUser;
import pss.bsp.consola.datos.abiertos.GuiAbiertos;
import pss.bsp.consola.datos.cadenas.GuiCadenas;
import pss.bsp.consola.reportes.GuiReportes;
import pss.bsp.consolid.model.liquidacion.acumulado.BizLiqAcum;
import pss.bsp.consolid.model.liquidacion.acumulado.GuiLiqAcum;
import pss.bsp.consolid.model.liquidacion.acumulado.GuiLiqAcums;
import pss.bsp.consolid.model.liquidacion.header.BizLiqHeader;
import pss.bsp.consolid.model.mit.GuiMits;
import pss.bsp.consolidador.IConsolidador;
import pss.bsp.consolidador.consolidacion.detalle.BizConsolidacion;
import pss.bsp.consolidador.consolidacion.detalle.GuiConsolidaciones;
import pss.bsp.contrato.GuiContratos;
import pss.bsp.interfaces.copa.datos.GuiCopa;
import pss.bsp.interfaces.copa.datos.GuiCopas;
import pss.bsp.interfaces.dqb.datos.GuiDQB;
import pss.bsp.interfaces.dqb.datos.GuiDQBs;
import pss.bsp.pdf.GuiPDF;
import pss.bsp.pdf.GuiPDFs;
import pss.bsp.publicity.GuiPublicities;
import pss.bsp.ticketsOracle.GuiTicketOracles;
import pss.common.event.action.BizSqlEventAction;
import pss.common.event.action.GuiSqlEventActions;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JIntervalDate;
import pss.core.services.fields.JObject;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;
import pss.tourism.interfaceGDS.log.GuiInterfaceLogs;
import pss.tourism.pnr.GuiPNRFiles;
import pss.tourism.pnr.GuiPNROtros;
import pss.tourism.pnr.GuiPNRReservas;
import pss.tourism.pnr.GuiPNRTickets;

public class GuiFuenteDato extends JWin {

  /**
   * Constructor de la Clase
   */
  public GuiFuenteDato() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizFuenteDato(); }
  public int GetNroIcono()   throws Exception { return 10057; }
  public String GetTitle()   throws Exception { return "Fuente Datos"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception {	return FormFuenteDato.class; }
  public Class<? extends JBaseForm> getFormEmbedded() throws Exception { 	return FormFuenteDatoCard.class;  }
  public BizFuenteDato GetcDato() throws Exception { return (BizFuenteDato) this.getRecord(); }

  public void createActionMap() throws Exception {
  	this.createActionQuery();
		this.addAction(10, "Boletos PNR", null, 10056, false, false);
		this.addAction(11, "Boletos de hoy", null, 10056, false, false);
		this.addAction(15, "Hoteles, Autos, etc", null, 10056, false, false);
		this.addAction(20, "Boletos BSP", null, 10056, false, false);
		//this.addAction(500, "Boletos Oracle", null, 10056, false, false);
		this.addAction(30, "Interfaz BO", null, 10058, false, false);
	//	this.addAction(36, "Interfaz COPA", null, 10058, false, false);
///		this.addAction(35, "Interfaz publicidad", null, 10058, false, false);
		this.addAction(38, "Boletos Error", null, 10056, false, false);
		this.addAction(43, "Clientes", null, 10056, false, false);
		this.addAction(44, "Clientes Error", null, 10056, false, false);
		this.addAction(40, "Agregar Liquidación", null, 10056, true, true);
		this.addAction(50, "Agregar Interfaz BO", null, 10058, true, true);
		this.addAction(70, "Agregar Interfaz COPA", null, 10058, true, true);
				//	this.addAction(60, "Limpiar datos", null, 10058, true, true);
		BizAction  a=this.addAction(75, "Conciliar Todo", null, 10045, true, true, true, "Group");
		this.addAction(80, "Ver Conciliacion PNR", null, 10047, true, true, true, "Group");
		this.addAction(90, "Ver Conciliacion BO", null, 10047, true, true, true, "Group");
		this.addAction(100, "Contratos", null, 10056, false, false);
		this.addAction(110, "Reportes", null, 10056, false, false);
		this.addAction(150, "Liquidación", null, 10056, false, false);
		this.addAction(160, "Ult.liqudacion", null, 10056, false, false);
		this.addAction(120, "Reservas", null, 10056, false, false);
		this.addAction(39, "Abiertos", null, 10056, false, false);
		this.addAction(130, "Reemisiones", null, 10056, false, false);
			
  }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
 // 	if (a.getId()==15) return BizUsuario.IsAdminCompanyUser() || BizUsuario.getUsr().getCompany().equalsIgnoreCase("tur");
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
			if (a.getId()==10) return true;
			if (a.getId()==11) return true;
			if (a.getId()==39) return true;
			if (a.getId()==100) return true;
			if (a.getId()==130) return true;
			if (a.getId()==150) return true;
			if (a.getId()==160) return true;
						return false;
		}
		if (a.getId()==175) return BizUsuario.getUsr().isAnyAdminUser();
		if (a.getId()==500) return BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isCNS();
		if (a.getId()==39) return !BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isCNS();
		if (a.getId()==130) return !BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isCNS();
  	return super.OkAction(a);
  }
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==10) return new JActWins(this.getTickets());
   	if (a.getId()==11) return new JActWins(this.getTicketsHoy());
    if (a.getId()==15) return new JActWins(this.getHoteles());
  	if (a.getId()==20) return new JActWins(this.getPDFS());  	
  	if (a.getId()==500) return new JActWins(this.getTicketsOracle());  
  	if (a.getId()==35) return new JActWins(this.getPublicidad());  
  	if (a.getId()==36) return new JActWins(this.getCOPA());  
  	if (a.getId()==30) return new JActWins(this.getBOS());  
  	if (a.getId()==38) return new JActWins(this.getErrores());  
  	if (a.getId()==39) return new JActWins(this.getAbiertos());  
  	if (a.getId()==43) return new JActWins(this.getClientes());  
  	if (a.getId()==44) return new JActWins(this.getClientesError());  
  	if (a.getId()==40) return new JActNew(getNewPDF(),0);
		if (a.getId()==50) return new JActNew(getNewBO(),0);
		if (a.getId()==60) return new JActSubmit(this) {
			@Override
			public void submit() throws Exception {
				GetcDato().execProcessLimpiar();
			}
		};
		if (a.getId() == 75)	return new JActSubmit(this) {
			public void submit() throws Exception {
				conciliarTodo();
			}  
		};		
	 	if (a.getId()==100) return new JActWins(this.getContratos());
//	 	if (a.getId()==110) return new JActWins(this.getProgramacion());
	 	if (a.getId()==110) return new JActQuery(this.getReportes());
		if (a.getId()==70) return new JActNew(getNewCopa(),0);
		if (a.getId()==80)	return new JActWins(this.getConciliacionPNR(false));
		if (a.getId()==90)	return new JActWins(this.getConciliacionBO(false));
	 	if (a.getId()==120) return new JActWins(this.getReservas());  
	 	if (a.getId()==150) return new JActWins(this.getLiquidacionesDK());  
	 	if (a.getId()==160) return new JActQuery(this.getLiquidacionDK());  
	 	if (a.getId()==130) return new JActWins(this.getCadenas());  
	 		
  	return super.getSubmitFor(a);
  }
	 

  public GuiLiqAcum getLiquidacionDK() throws Exception {
		JRecords<BizLiqAcum> lastLiqs = new JRecords<BizLiqAcum>(BizLiqAcum.class);
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
			lastLiqs.addFilter("company", BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getParentCompany());
			lastLiqs.addFilter("dk",BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente());
		}else
			lastLiqs.addFilter("company", BizUsuario.getUsr().getCompany());
		lastLiqs.addFilter("estado", BizLiqHeader.PUBLICADO);
		lastLiqs.addOrderBy("liquidacion_id","desc");
		lastLiqs.setTop(1);
		JIterator<BizLiqAcum> it=lastLiqs.getStaticIterator();
		if (!it.hasMoreElements()) 
			return new GuiLiqAcum();
		BizLiqAcum lastLiq=it.nextElement();
		GuiLiqAcum acum = new GuiLiqAcum();
		acum.setRecord(lastLiq);
		return acum;

  }
	public GuiLiqAcums getLiquidacionesDK() throws Exception {
		GuiLiqAcums wins = new GuiLiqAcums();
		
		if (BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).isConcentrador()) {
			wins.getRecords().addFixedFilter(" company  in (" + BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).getCustomListCompany() + ")");
		} else if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
   		wins.getRecords().addFixedFilter("where company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getTicketCompany()+") and dk='"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"' ");
  	}else
			wins.getRecords().addFilter("company", BizBSPUser.getUsrBSP().getCompany());
		wins.getRecords().addFilter("estado",BizLiqHeader.PUBLICADO);
		wins.getRecords().addOrderBy("fecha_desde", "desc");

		return wins;
	}
	public GuiAbiertos getAbiertos() throws Exception {
		GuiAbiertos pdfs=new GuiAbiertos();
		pdfs.SetVision("SINTOT");
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		pdfs.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else if (!BizUsuario.getUsr().isAdminUser())
			pdfs.getRecords().addFilter("company", this.GetcDato().getCompany());
		return pdfs;
	}
	public JWins getProgramacion() throws Exception {
		GuiSqlEventActions wins = new GuiSqlEventActions();
		wins.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
		wins.getRecords().addFilter("tipo_periodicidad", BizSqlEventAction.SOLOUNAVEZ, "<>");
		wins.getRecords().addFilter("action", BizSqlEventAction.NOTIF, "<>");
		return wins;
	}
	private JWin getReportes()  throws Exception {
  	GuiReportes f = new GuiReportes();
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
			f.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
				f.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getTicketCompany()+")");
  	} 
  	if (!BizUsuario.getUsr().isAdminUser())
			f.GetcDato().setCompany(this.GetcDato().getCompany());
		return f;
	}
	public void conciliarTodo() throws Exception {
		GuiPDFs pdfs=new GuiPDFs();
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		pdfs.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else if (!BizUsuario.getUsr().isAdminUser())
			pdfs.getRecords().addFilter("company", this.GetcDato().getCompany());
		pdfs.getRecords().addFilter("estado", "CONCILIADO", "<>");
		pdfs.getRecords().addOrderBy("fecha_desde","DESC");
		JIterator<JWin> it = pdfs.getStaticIterator();
		while (it.hasMoreElements()) {
			GuiPDF pdf = (GuiPDF)it.nextElement();
			pdf.getConciliacion(true);
		}
	}

  public JWins getConciliacionBO(boolean force) throws Exception {
		GuiConsolidaciones con =	new GuiConsolidaciones();
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		con.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else if (!BizUsuario.getUsr().isAdminUser())
			con.getRecords().addFilter("company", GetcDato().getCompany());
		con.getRecords().addFilter("tipo", IConsolidador.BO);
		con.SetVision(BizConsolidacion.VISION_LIBRE);
		return con;
	}  
  public JWins getConciliacionPNR(boolean force) throws Exception {
		GuiConsolidaciones con =	new GuiConsolidaciones();
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		con.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else if (!BizUsuario.getUsr().isAdminUser())
			con.getRecords().addFilter("company", GetcDato().getCompany());
		con.getRecords().addFilter("tipo", IConsolidador.PNR);
		con.SetVision(BizConsolidacion.VISION_LIBRE);
		return con;
	}
	public GuiPDFs getPDFS() throws Exception {
		GuiPDFs pdfs=new GuiPDFs();
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		pdfs.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else if (!BizUsuario.getUsr().isAdminUser())
			pdfs.getRecords().addFilter("company", this.GetcDato().getCompany());
//		pdfs.getRecords().addFilter("owner", this.GetcDato().getUsuario());
		pdfs.getRecords().addOrderBy("estado","DESC" );
		pdfs.getRecords().addOrderBy("fecha_desde","DESC");
		return pdfs;
	}
	public GuiTicketOracles getTicketsOracle() throws Exception {
		GuiTicketOracles pdfs=new GuiTicketOracles();
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		pdfs.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else if (!BizUsuario.getUsr().isAdminUser())
			pdfs.getRecords().addFilter("company", this.GetcDato().getCompany());
//		pdfs.getRecords().addFilter("owner", this.GetcDato().getUsuario());
		pdfs.getRecords().addOrderBy("estado","DESC" );
		pdfs.getRecords().addOrderBy("fecha_desde","DESC");
		return pdfs;
	}
	public GuiPNRTickets getTicketsHoy() throws Exception {
		GuiPNRTickets pdfs=new GuiPNRTickets();
		pdfs.getRecords().addFilter("creation_date", new JIntervalDate(  new Date(),  new Date())).setDynamic(true);
		pdfs.SetVision("SINTOT");
		pdfs.setPreviewFlag(JWins.PREVIEW_NO);
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		pdfs.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
   		pdfs.getRecords().addFixedFilter(" ( company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getTicketCompany()+") and customer_id_reducido = '"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"') ");
  	} else 	if (!BizUsuario.getUsr().isAdminUser())
			pdfs.getRecords().addFilter("company", this.GetcDato().getCompany());
		return pdfs;
	}
	public GuiPNRTickets getTicketsOver() throws Exception {
		GuiPNRTickets pdfs=new GuiPNRTickets();
//		pdfs.getRecords().addFilter("creation_date", new JIntervalDate(  new Date(),  new Date())).setDynamic(true);
		pdfs.SetVision("OVER");
		pdfs.setPreviewFlag(JWins.PREVIEW_NO);
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		pdfs.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
   		pdfs.getRecords().addFixedFilter(" ( company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getTicketCompany()+") and customer_id_reducido = '"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"') ");
  	} else 	if (!BizUsuario.getUsr().isAdminUser())
			pdfs.getRecords().addFilter("company", this.GetcDato().getCompany());
		return pdfs;
	}
	public GuiPNRTickets getTicketsUpfront() throws Exception {
		GuiPNRTickets pdfs=new GuiPNRTickets();
//		pdfs.getRecords().addFilter("creation_date", new JIntervalDate(  new Date(),  new Date())).setDynamic(true);
		pdfs.SetVision("UPFRONT");
		pdfs.setPreviewFlag(JWins.PREVIEW_NO);
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		pdfs.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
   		pdfs.getRecords().addFixedFilter(" ( company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getTicketCompany()+") and customer_id_reducido = '"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"') ");
  	} else 	if (!BizUsuario.getUsr().isAdminUser())
			pdfs.getRecords().addFilter("company", this.GetcDato().getCompany());
		return pdfs;
	}
  public JWins getContratos() throws Exception {
  	GuiContratos wins = new GuiContratos();
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		wins.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
   		wins.getRecords().addFixedFilter("where company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getTicketCompany()+") and ( bsp_contrato.id in (select contrato from bsp_prorrateo where contrato = bsp_contrato.id and (cliente='ALL' or cliente like '%"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"%'))) ");
  	} else 		if (!BizUsuario.getUsr().isAdminUser()) {
			wins.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
	  	wins.getRecords().addOrderBy("company");
		
		}
  	wins.getRecords().addFilter("tipo_contrato", "C");

  	wins.getRecords().addOrderBy("fecha_desde","DESC");
  	wins.getRecords().addOrderBy("descripcion","ASC");
//  	wins.setForceToolbarPos("left");
//  	wins.setShowWinToolbar(false);
		wins.setPreviewFlag(JWins.PREVIEW_SI);
  	wins.setForceSelected(true);
   	wins.setPreviewSplitPos(500);
   
     
  	return wins;
//  	winsprorrateo=null;
//  	GuiContratoConsolidados wins = new GuiContratoConsolidados();
//  	wins.addOrderAdHoc("company", "ASC");
//  	wins.addOrderAdHoc("anio", "ASC");
//  	wins.addOrderAdHoc("mes", "ASC");
//  	wins.addOrderAdHoc("id", "ASC");
//  	wins.addOrderAdHoc("descripcion", "ASC");
////  	if (email)
//  		wins.setToolbarForced(JBaseWin.TOOLBAR_NONE);
//
//  	return wins;
  }
  public JWins getReservas() throws Exception {
	  	GuiPNRReservas wins = new GuiPNRReservas();
	  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
	  		wins.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
	  	} else	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
	   		wins.getRecords().addFixedFilter(" ( company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getTicketCompany()+") and customer_id_reducido = '"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"') ");
	  	} else if (!BizUsuario.getUsr().isAdminUser()) {
				wins.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
	  	wins.getRecords().addOrderBy("company");
		
		}
		wins.setPreviewFlag(JWins.PREVIEW_NO);

     
  	return wins;
  }
  public JWins getCadenas() throws Exception {
  	GuiCadenas wins = new GuiCadenas();
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		wins.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
  		wins.getRecords().addFixedFilter(" ( company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getTicketCompany()+") and customer_id_reducido = '"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"') ");
 		
  	} else if (!BizUsuario.getUsr().isAdminUser()) {
			wins.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
	  	wins.getRecords().addOrderBy("company");
		
		}
		wins.setPreviewFlag(JWins.PREVIEW_NO);
	 	wins.getRecords().addOrderBy("numeroboleto","DESC");
	  
     
  	return wins;
  }
	public GuiPNRTickets getTickets() throws Exception {
		GuiPNRTickets pdfs=new GuiPNRTickets();
		pdfs.SetVision("SINTOT");
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		pdfs.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
  		pdfs.getRecords().addFixedFilter(" ( company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getTicketCompany()+") and customer_id_reducido = '"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"') ");
  	} else if (!BizUsuario.getUsr().isAdminUser())
			pdfs.getRecords().addFilter("company", this.GetcDato().getCompany());
//		pdfs.getRecords().addOrderBy("fechacreacion","ASC");
		return pdfs;
	}
	public GuiPNROtros getHoteles() throws Exception {
		GuiPNROtros pdfs=new GuiPNROtros();
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
			pdfs.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
  		pdfs.getRecords().addFixedFilter(" ( company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getTicketCompany()+") and customer_id_reducido = '"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"') ");
  	} else if (!BizUsuario.getUsr().isAdminUser())
			pdfs.getRecords().addFilter("company", this.GetcDato().getCompany());
//		pdfs.getRecords().addOrderBy("fechacreacion","ASC");
		return pdfs;
	}

	public GuiInterfacesBO getBOS() throws Exception {
		GuiInterfacesBO intbo=new GuiInterfacesBO();
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
			intbo.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
	  	} else if (!BizUsuario.getUsr().isAdminUser())
			intbo.getRecords().addFilter("company", this.GetcDato().getCompany());
//		intbo.getRecords().addFilter("owner", this.GetcDato().getUsuario());
		intbo.getRecords().addOrderBy("fecha_desde","DESC");
		return intbo;
	}
	public GuiCopas getCOPA() throws Exception {
		GuiCopas intbo=new GuiCopas();
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
			intbo.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
	  	} else if (!BizUsuario.getUsr().isAdminUser())
			intbo.getRecords().addFilter("company", this.GetcDato().getCompany());
//		intbo.getRecords().addFilter("owner", this.GetcDato().getUsuario());
		intbo.getRecords().addOrderBy("fecha_desde","DESC");
		return intbo;
	}
	
	public GuiDQBs getDQB() throws Exception {
		GuiDQBs intbo=new GuiDQBs();
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
			intbo.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
	  	} else if (!BizUsuario.getUsr().isAdminUser())
			intbo.getRecords().addFilter("company", this.GetcDato().getCompany());
//		intbo.getRecords().addFilter("owner", this.GetcDato().getUsuario());
		intbo.getRecords().addOrderBy("fecha_desde","DESC");
		return intbo;
	}
	
	public GuiMits getMIT() throws Exception {
		GuiMits intbo=new GuiMits();
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
			intbo.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
	  } else if (!BizUsuario.getUsr().isAdminUser())
			intbo.getRecords().addFilter("company", this.GetcDato().getCompany());
		intbo.getRecords().addOrderBy("date_from","DESC");
		return intbo;
	}
	public GuiPublicities getPublicidad() throws Exception {
		GuiPublicities intbo=new GuiPublicities();
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
			intbo.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
	  	} else if (!BizUsuario.getUsr().isAdminUser())
     		intbo.getRecords().addFilter("company", this.GetcDato().getCompany());
//		intbo.getRecords().addFilter("owner", this.GetcDato().getUsuario());
		intbo.getRecords().addOrderBy("fecha","DESC");
		return intbo;
	}
	public GuiPNRFiles getErrores() throws Exception {
		GuiPNRFiles errores=new GuiPNRFiles();
		errores.SetVision("ERROR");
	  errores.readAllErrors();
		return errores;
	}
	public GuiInterfaceLogs getClientes() throws Exception {
		GuiInterfaceLogs c = new GuiInterfaceLogs();
	  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
	  		c.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
	  	}  else 	if (BizUsuario.getUsr().getCompany().equals("YTEC"))
			c.addFilterAdHoc("company", "TRADYTEC");
	  	 else if (!BizUsuario.getUsr().getCompany().equals("DEFAULT"))
			c.addFilterAdHoc("company", BizUsuario.getUsr().getCompany());
		c.addOrderAdHoc("id", "DESC");
		c.setPreviewFlag(JWins.PREVIEW_NO);
		return c;

	}
	
	public GuiInterfaceLogs getClientesError() throws Exception {
		GuiInterfaceLogs t= new GuiInterfaceLogs();
		Calendar hace10Mintutos = Calendar.getInstance();
		hace10Mintutos.setTime(new Date());
		hace10Mintutos.add(Calendar.MINUTE, -360);
	  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) 
	  		t.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
	  	else 	if (BizUsuario.getUsr().getCompany().equals("YTEC"))
	 			t.getRecords().addFilter("company", "TRADYTEC");
	   	else if (!BizUsuario.getUsr().getCompany().equals("DEFAULT"))
				t.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
		t.getRecords().addFilter("lastecho", JDateTools.DateToString(hace10Mintutos.getTime(), "yyyy-MM-dd HH:mm:ss"), JObject.JDATE, "<=", "AND", "");
		return t;
	}
	public GuiPDF getNewPDF() throws Exception {
//	 	ISitita licencia = ((ISitita)this.GetcDato().getObjLicense());
//  	if (!licencia.canUpload())
//  		throw new Exception("Su licencia no le permite ingresa mas liquidaciones de BSP");
  //	licencia.testMaxUsosPorMes();
		GuiPDF pdf = new GuiPDF();
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		pdf.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else if (!BizUsuario.getUsr().isAdminUser())
			pdf.GetcDato().addFilter("company", this.GetcDato().getCompany());
//		pdf.GetcDato().addFilter("owner", BizUsuario.getUsr().GetUsuario());
		return pdf;
	}
	public GuiInterfazBO getNewBO() throws Exception {
//	 	ISitita licencia = ((ISitita)this.GetcDato().getObjLicense());
//	  	if (!licencia.canUpload())
//	  		throw new Exception("Su licencia no le permite ingresa interfaces de BackOffice");
		GuiInterfazBO bo = new GuiInterfazBO();
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		bo.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else if (!BizUsuario.getUsr().isAdminUser())
			bo.GetcDato().addFilter("company", this.GetcDato().getCompany());
//		bo.GetcDato().addFilter("owner", BizUsuario.getUsr().GetUsuario());
		return bo;
	}
	
	public GuiCopa getNewCopa() throws Exception {
// 	ISitita licencia = ((ISitita)this.GetcDato().getObjLicense());
//	if (!licencia.canUpload())
//		throw new Exception("Su licencia no le permite ingresa mas liquidaciones de BSP");
//	licencia.testMaxUsosPorMes();
		GuiCopa pdf = new GuiCopa();
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		pdf.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else if (!BizUsuario.getUsr().isAdminUser())
			pdf.GetcDato().addFilter("company", this.GetcDato().getCompany());
//	pdf.GetcDato().addFilter("owner", BizUsuario.getUsr().GetUsuario());
	return pdf;
}
	
	public GuiDQB getNewDQB() throws Exception {
// 	ISitita licencia = ((ISitita)this.GetcDato().getObjLicense());
//	if (!licencia.canUpload())
//		throw new Exception("Su licencia no le permite ingresa mas liquidaciones de BSP");
//	licencia.testMaxUsosPorMes();
		GuiDQB pdf = new GuiDQB();
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		pdf.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else if (!BizUsuario.getUsr().isAdminUser())
			pdf.GetcDato().addFilter("company", this.GetcDato().getCompany());
//	pdf.GetcDato().addFilter("owner", BizUsuario.getUsr().GetUsuario());
	return pdf;
}
	
	
	@Override
	public boolean canConvertToURL() throws Exception {
		return false;
	}
	
	
}
