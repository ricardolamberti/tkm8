package pss.bsp.consola.reportes;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.bspBusiness.BizBSPUser;
import pss.bsp.consola.datos.abiertos.GuiAbiertos;
import pss.bsp.consola.datos.cadenas.GuiCadenas;
import pss.bsp.consolid.model.cuf.GuiCufs;
import pss.bsp.consolid.model.liquidacion.acumulado.BizLiqAcum;
import pss.bsp.consolid.model.liquidacion.acumulado.GuiLiqAcum;
import pss.bsp.consolid.model.liquidacion.acumulado.GuiLiqAcums;
import pss.bsp.consolid.model.liquidacion.header.BizLiqHeader;
import pss.bsp.consolid.model.mit.GuiMits;
import pss.bsp.consolid.model.repoCarrier.GuiRepoCarriers;
import pss.bsp.consolid.model.repoDK.GuiRepoDKs;
import pss.bsp.consolid.model.report.GuiReportDks;
import pss.bsp.consolid.model.rfnd.GuiRfnds;
import pss.bsp.interfaces.dqb.datos.GuiDQB;
import pss.bsp.interfaces.dqb.datos.GuiDQBs;
import pss.bsp.pdf.GuiPDF;
import pss.bsp.pdf.GuiPDFs;
import pss.common.event.action.BizSqlEventAction;
import pss.common.event.action.GuiSqlEventActions;
import pss.common.event.action.history.GuiSqlEventHistories;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;
import pss.tourism.pnr.GuiPNROtros;
import pss.tourism.pnr.GuiPNRTickets;

public class GuiReportes extends JWin {

  /**
   * Constructor de la Clase
   */
  public GuiReportes() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizReportes(); }
  public int GetNroIcono()   throws Exception { return 10057; }
  public String GetTitle()   throws Exception { return "Reportes"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception {	return FormReportes.class; }
  public BizReportes GetcDato() throws Exception { return (BizReportes) this.getRecord(); }

  public void createActionMap() throws Exception {
  	this.createActionQuery();
		this.addAction(130, "Reemisiones", null, 10056, false, false);
		this.addAction(45, "Interfaz MIT", null, 10058, false, false);
		this.addAction(37, "Interfaz DQB", null, 10058, false, false);
		this.addAction(50, "Interfaz CUF", null, 10058, false, false);
		this.addAction(160, "Interfaz Dks", null, 10058, false, false);
		this.addAction(200, "Interfaz Carrier", null, 10058, false, false);
		this.addAction(39, "Abiertos", null, 10056, false, false);
	//	this.addAction(71, "Agregar Interfaz DQB", null, 10058, true, true);
		this.addAction(170, "Boletos Over", null, 10056, false, false);
		this.addAction(175, "Boletos Upfront", null, 10056, false, false);
		this.addAction(110, "Reportes", null, 749, false, false);
		this.addAction(190, "Interfaz CTR917", null, 749, false, false);
		this.addAction(210, "Interfaz RFND", null, 10058, false, false);
	
  }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
 // 	if (a.getId()==15) return BizUsuario.IsAdminCompanyUser() || BizUsuario.getUsr().getCompany().equalsIgnoreCase("tur");
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {

						return false;
		}
				if (a.getId()==500) return BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isCNS();
				if (a.getId()==175) return BizUsuario.getUsr().isAnyAdminUser();
				if (a.getId()==50) return BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isCNS();
				if (a.getId()==37) return BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isCNS();
				if (a.getId()==71) return BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isCNS();
				if (a.getId()==190) return BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isCNS();
				if (a.getId()==200) return BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isCNS();
				if (a.getId()==45) return BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isCNS();
				if (a.getId()==160) return BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isCNS();
				if (a.getId()==210) return BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isCNS();
 	return super.OkAction(a);
  }
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==37) return new JActWins(this.getDQB());  
  	if (a.getId()==39) return new JActWins(this.getAbiertos());  
  	if (a.getId()==50) return new JActWins(this.getCUF());  
  	if (a.getId()==45) return new JActWins(this.getMIT());  
	 	if (a.getId()==71) return new JActNew(getNewDQB(),0);
	 	if (a.getId()==130) return new JActWins(this.getCadenas());  
	 	if (a.getId()==170) return new JActWins(this.getTicketsOver());  
		if (a.getId()==110) return new JActWins(this.getReportes());
		if (a.getId()==175) return new JActWins(this.getTicketsUpfront());  
		if (a.getId()==160) return new JActWins(this.getDKs());  
		if (a.getId()==190) return new JActWins(this.getReportesDK());  
		if (a.getId()==200) return new JActWins(this.getCarriers());  
		if (a.getId()==210) return new JActWins(this.getRFNDs());  
				
  	return super.getSubmitFor(a);
  }
	public GuiReportDks getReportesDK() throws Exception {
		GuiReportDks wins = new GuiReportDks();
		
		wins.getRecords().addFilter("company",GetcDato().getCompany());
		wins.getRecords().addOrderBy("date_from", "desc");
			
		return wins;
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
	public JWins getRFNDs() throws Exception {
		GuiRfnds pdfs=new GuiRfnds();
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		pdfs.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else if (!BizUsuario.getUsr().isAdminUser())
			pdfs.getRecords().addFilter("company", this.GetcDato().getCompany());
		return pdfs;
	}
	public JWins getCUF() throws Exception {
		GuiCufs pdfs=new GuiCufs();
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		pdfs.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else if (!BizUsuario.getUsr().isAdminUser())
			pdfs.getRecords().addFilter("company", this.GetcDato().getCompany());
		return pdfs;
	}
	public JWins getDKs() throws Exception {
		GuiRepoDKs pdfs=new GuiRepoDKs();
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		pdfs.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else if (!BizUsuario.getUsr().isAdminUser())
			pdfs.getRecords().addFilter("company", this.GetcDato().getCompany());
		return pdfs;
	}
	public JWins getCarriers() throws Exception {
		GuiRepoCarriers pdfs=new GuiRepoCarriers();
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

	

	public JWins getReportes() throws Exception {
		GuiSqlEventHistories wins = new GuiSqlEventHistories();
		if (!BizUsuario.getUsr().isAdminUser())
			wins.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
		wins.getRecords().addOrderBy("fecha", "DESC");
		// wins.setShowWinToolbar(false);
		wins.setPreviewFlag(JWins.PREVIEW_SI);
		wins.setForceSelected(true);
		wins.setPreviewSplitPos(680);

		return wins;
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
