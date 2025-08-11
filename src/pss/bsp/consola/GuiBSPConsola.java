package pss.bsp.consola;


import pss.bsp.bspBusiness.BSPService;
import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.bspBusiness.BizBSPUser;
import pss.bsp.carpeta.GuiBSPCarpetas;
import pss.bsp.chat.GuiChat;
import pss.bsp.consola.config.GuiBSPConfig;
import pss.bsp.consola.dashBoard.GuiDashBoard;
import pss.bsp.consola.datos.GuiFuenteDato;
import pss.bsp.consola.reportes.GuiReportes;
import pss.bsp.consolid.model.clientesView.GuiClienteViews;
import pss.bsp.consolid.model.gruposDK.detail.BizGrupoDKDetail;
import pss.bsp.consolid.model.liquidacion.acumulado.GuiLiqAcums;
import pss.bsp.consolid.model.liquidacion.agrupado.GuiLiqAgrupados;
import pss.bsp.consolid.model.liquidacion.header.BizLiqHeader;
import pss.bsp.consolid.model.liquidacion.header.GuiLiqHeaders;
import pss.bsp.contrato.BizContrato;
import pss.bsp.contrato.GuiContratos;
import pss.bsp.contrato.consolidado.GuiContratoConsolidados;
import pss.bsp.contrato.detalle.prorrateo.cliente.GuiClienteProrrateos;
import pss.bsp.event.GuiBSPSqlEvents;
import pss.common.customList.config.carpetas.GuiCarpetas;
import pss.common.customList.config.customlist.GuiCustomLists;
import pss.common.customList.config.relation.JRelations;
import pss.common.event.action.history.GuiSqlEventHistories;
import pss.common.layoutWysiwyg.GuiPlantillas;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecord;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActQueryActive;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;
import pss.tourism.pnr.GuiPNRReservas;

public class GuiBSPConsola extends JWin {


  /**
   * Constructor de la Clase
   */
  public GuiBSPConsola() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizBSPConsola(); }
  public int GetNroIcono()   throws Exception { return 10057; }
  public String GetTitle()   throws Exception { return "Consola"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception {
  	return FormBSPConsola.class;
  }
  public String  getKeyField() throws Exception { return "usuario"; }
  public String  getDescripField() { return "usuario"; }
  public BizBSPConsola GetcDato() throws Exception { return (BizBSPConsola) this.getRecord(); }
//  public String getHelpTag() { return "consola";}
  
  public void createActionMap() throws Exception {
		this.addAction(25, "Fuente Datos", null, 10058, true, true);
		this.addAction(50, "Data Mining", null, 10027, true, true );//.setRefreshAction(false);;
		this.addAction(57, "Reportes", null, 749, true, true);
		this.addAction(65, "Indicadores", null, 10042, true, true);
		this.addAction(55, "Contratos", null, 10045, true, true);
		this.addAction(56, "Consolidado", null, 10023, true, true);
		this.addAction(58, "Objetivos", null, 10045, true, true);
		//this.addAction(59, "Prorrateo", null, 10023, true, true);
		this.addAction(30, "Dash Board", null, 5012, true, true);
		this.addAction(60, "Configuracion", null, 10035, true, true);
		this.addAction(70, "Consolidado correo", null, 10023, false, false).setFilterAction(false);
		this.addAction(71, "Prorrateo correo", null, 10023, false, false).setFilterAction(false);
		this.addAction(80, "Reservas", null, 10045, true, false);
		this.addAction(90, "Chat IA", null, 10045, true, false);
		this.addAction(100, "Contratos", null, 10045, false, false);
		this.addAction(110, "Liquidaciones", null, 10045, true, true);
		this.addAction(120, "Liquidaciones", null, 10045, true, true); // para DKs
		this.addAction(130, "Admin.DKs", null, 5078, true, true); // para DKs
		this.addAction(210, "Plantillas", null, 63, true, true); 
		this.addAction(200, "Refrescar", null, 17, true, true); 
//		addAcciones();

  }
	public boolean forceCleanHistory() throws Exception {
		return true;
	}
  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
//  		if (a.getId()==25) return true;
//   		if (a.getId()==50) return true;
//   		if (a.getId()==55) return true;
   		if (a.getId()==120) return true;
   		if (a.getId()==200) return true;

			return false;
		}
  	
  	if (a.getId()==120) return false;
		if (a.getId()==110) return BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isCNS();
		if (a.getId()==130) return BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isCNS();

  	if (a.getId()==90) return BizUsuario.IsAdminCompanyUser();
  	return super.OkAction(a);
  }
  
	
   
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 25) return new JActQuery(this.getFuenteDatos());
		if (a.getId() == 30) return new JActQuery(this.getDashBoard());
		if (a.getId() == 50) return new JActWins(this.getDataMining(a));
		if (a.getId() == 55) return new JActWins(this.getContratos());
		if (a.getId() == 58) return new JActWins(this.getObjetivos());
		if (a.getId() == 56) return new JActWins(this.getConsolidado(false));
		if (a.getId() == 59) return new JActWins(this.getProrrateo(false));
		if (a.getId() == 70) return new JActWins(this.getConsolidado(true));
		if (a.getId() == 71) return new JActWins(this.getProrrateo(true));
		if (a.getId() == 57) return new JActQuery(this.getReportes());
		if (a.getId() == 65) return new JActWins(this.getEventos());
		if (a.getId() == 60) return new JActQuery(this.getConfigView());
		if (a.getId() == 80) return new JActWins(this.getReservas());
		if (a.getId() == 90) return new JActQueryActive(this.getChat());
		if (a.getId() == 100) return new JActWins(this.getContratosSlim());
		if (a.getId() == 110) return new JActWins(this.getLiquidaciones());
		if (a.getId() == 120) return new JActWins(this.getLiquidacionesDK());
		if (a.getId() == 130) return new JActWins(this.getDKs());
		if (a.getId() == 210) return new JActWins(this.ObtenerLayouts());
		if (a.getId() == 200) return new JActSubmit(this) {
			public void submit() throws Exception {
				new BSPService().updateInterfazNew(BizUsuario.getUsr().getCompany());
				super.submit();
			};
		};

		return null;
	}
	public JWins ObtenerLayouts() throws Exception {
  	GuiPlantillas oLayouts = new GuiPlantillas();
 		oLayouts.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
    oLayouts.getRecords().addOrderBy("descripcion");
    return oLayouts;
  }

	public JWin getConfigView()  throws Exception {
		GuiBSPConfig other = new  GuiBSPConfig();
		other.setRecord(GetcDato().getConfigView());
		return other;
	}
//	public void setConfigView(GuiBSPConfig c) throws Exception {
//		objConfig=c;
//	}


	private JWin getFuenteDatos()  throws Exception {
  	winsprorrateo=null;
		GuiFuenteDato f = new GuiFuenteDato();
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
			f.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
				f.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getTicketCompany()+")");
  	} 
  	if (!BizUsuario.getUsr().isAdminUser())
			f.GetcDato().setCompany(this.GetcDato().getConfigView().getCompany());
		return f;
	}


	
	private JWin getDashBoard()  throws Exception {
  	winsprorrateo=null;
		GuiDashBoard f = new GuiDashBoard();
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
			f.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else if (!BizUsuario.getUsr().isAdminUser())
			f.GetcDato().setCompany(this.GetcDato().getConfigView().getCompany());
		return f;
	}

	private JWin getChat()  throws Exception {
  	winsprorrateo=null;
		GuiChat f = new GuiChat();
		f.GetcDato().setCompany(this.GetcDato().getConfigView().getCompany());
		return f;
	}
	private JWins getDataMining(BizAction a)  throws Exception {
  	winsprorrateo=null;
		BizUsuario.getUsr().getObjBusiness().getCarpeta().GetcDato().execProcessCompletarCarpetas();
 	 
		GuiBSPCarpetas f = new GuiBSPCarpetas();
		f.SetVision(GuiCarpetas.VISION_NAV);
		f.getRecords().clearFilters();
    if (!BizUsuario.getUsr().isAdminUser())
			f.getRecords().addFilter("company", this.GetcDato().getConfigView().getCompany());
		if (!BizUsuario.IsAdminCompanyUser()){
			f.getRecords().addFilter("invisible", "S","<>");
			f.getRecords().addFixedFilter(
				"where (NOT EXISTS(SELECT 1 FROM LST_OWNERV2 WHERE LST_OWNERV2.company=LST_CARPETASV2.company and LST_OWNERV2.list_id=LST_CARPETASV2.customlist )  "
				+ "OR EXISTS(SELECT 1 FROM LST_OWNERV2 WHERE LST_OWNERV2.company=LST_CARPETASV2.company and LST_OWNERV2.list_id=LST_CARPETASV2.customlist and LST_OWNERV2.usuario='"+BizUsuario.getUsr().GetUsuario()+"' )) ");
		}

  	if (!BizUsuario.getUsr().isAdminUser())
			f.getRecords().addOrderBy("company","DESC");
		f.setPreviewFlag(JWins.PREVIEW_SI);
		f.setForceSelected(true);
   	f.setPreviewSplitPos(330);
		f.setModeView(JBaseWin.MODE_TREE);

		return f;
/*
 		BizUsuario.getUsr().getObjBusiness().getCarpeta().GetcDato().execProcessCompletarCarpetas();
 
		GuiBSPCarpetas f = new GuiBSPCarpetas();
		f.getRecords().clearFilters();
		if (!BizUsuario.getUsr().isAdminUser())
			f.getRecords().addFilter("company", this.GetcDato().getConfigView().getCompany());
		if (!BizUsuario.IsAdminCompanyUser())
			f.getRecords().addFilter("invisible", "S","<>");
		f.getRecords().addFixedFilter(
				"where (NOT EXISTS(SELECT 1 FROM LST_OWNER WHERE LST_OWNER.company=LST_CARPETASV2.company and LST_OWNER.list_id=LST_CARPETASV2.customlist )  "
				+ "OR EXISTS(SELECT 1 FROM LST_OWNER WHERE LST_OWNER.company=LST_CARPETASV2.company and LST_OWNER.list_id=LST_CARPETASV2.customlist and LST_OWNER.usuario='"+BizUsuario.getUsr().GetUsuario()+"' )) ");

		f.getRecords().addOrderBy("orden","DESC");
		f.setPreviewFlag(JWins.PREVIEW_SI);
		f.setForceSelected(true);
   	f.setPreviewSplitPos(430);
		f.SetVision(GuiCarpetas.VISION_NAV);

		return f;
*/
	}
	


	private JWins getDataMining2()  throws Exception {
  	winsprorrateo=null;
		GuiCustomLists f = new GuiCustomLists();
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
			f.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else 		if (!BizUsuario.getUsr().isAdminUser())
			f.getRecords().addFilter("company", this.GetcDato().getConfigView().getCompany());
		if (!BizUsuario.IsAdminCompanyUser()) {
			f.getRecords().addFilter("invisible", "S","<>");
			f.getRecords().addFixedFilter(
				"where (NOT EXISTS(SELECT 1 FROM LST_OWNERV2 WHERE LST_OWNERV2.company=LST_CUSTOM_LISTV2.company and LST_OWNERV2.list_id=LST_CUSTOM_LISTV2.list_id )  "
				+ "OR EXISTS(SELECT 1 FROM LST_OWNERV2 WHERE LST_OWNERV2.company=LST_CUSTOM_LISTV2.company and LST_OWNERV2.list_id=LST_CUSTOM_LISTV2.list_id and LST_OWNERV2.usuario='"+BizUsuario.getUsr().GetUsuario()+"' )) ");
		}
		f.getRecords().addOrderBy("description");
		f.setPreviewFlag(JWins.PREVIEW_SI);
		f.setForceSelected(true);
   	f.setPreviewSplitPos(330);
		return f;
//		GuiCompanies f = new GuiCompanies();
////		f.getRecords().addFilter("company", this.GetcDato().getObjConfig().getCompany());
//		f.setWithPreview(true);
//		f.setForceSelected(true);
//		return f;
	}


//	public JWins getDynamicWins(int inf) throws Exception {
//		GuiDynamics d = new GuiDynamics();
//		GuiCustomList clist = new GuiCustomList();
//		clist.setRecord(GetcDato().getFavorito(inf));
//		d.setCustomList(clist);
//		return d;
//	}
//	public JWin getCustomList(int inf) throws Exception {
//		GuiCustomList clist = new GuiCustomList();
//		clist.setRecord(GetcDato().getFavorito(inf));
//		return clist.getResult();
//	}
  public JWins getContratos() throws Exception {
  	winsprorrateo=null;
  	GuiContratos wins = new GuiContratos();
   	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
   		wins.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
   		wins.getRecords().addFixedFilter("where company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getTicketCompany()+") and ( bsp_contrato.id in (select contrato from bsp_prorrateo where contrato = bsp_contrato.id and (cliente='ALL' or cliente like '%"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"%'))) ");
  	} else if (!BizUsuario.getUsr().isAdminUser()) {
			wins.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
	  	wins.getRecords().addOrderBy("company");
		
		} 

  	wins.getRecords().addFilter("tipo_contrato", "C");
  	
  	wins.getRecords().addOrderBy("fecha_desde","DESC");
  	wins.getRecords().addOrderBy("descripcion","ASC");
//  	wins.setForceToolbarPos("left");
//  	wins.setShowWinToolbar(false);
  	wins.setWithFooter(false);
		wins.setPreviewFlag(JWins.PREVIEW_SI);
  	wins.setForceSelected(true);
  	wins.SetVision(BizContrato.CONTRATOS);
   	wins.setPreviewSplitPos(500);
   
     
  	return wins;
  }
  public JWins getContratosSlim() throws Exception {
  	GuiContratos wins = new GuiContratos();
   	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
   		wins.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
   		wins.getRecords().addFixedFilter("where company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getTicketCompany()+") and ( bsp_contrato.id in (select contrato from bsp_prorrateo where contrato = bsp_contrato.id and (cliente='ALL' or cliente like '%"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"%'))) ");
  	} else if (!BizUsuario.getUsr().isAdminUser()) {
			wins.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
	  	wins.getRecords().addOrderBy("company");
		} 

   	wins.getRecords().addFilter("active","S");
   	wins.getRecords().addFilter("tipo_contrato", "C");
    	 		
  	wins.getRecords().addOrderBy("fecha_desde","DESC");
  	wins.getRecords().addOrderBy("descripcion","ASC");
  	wins.setShowWinToolbar(false);
  	wins.setWebPageable(false);
  	wins.setWithFooter(false);
  	wins.setShowFilters(false);
  	wins.setForceHideActions(true);
  	wins.setPreviewFlag(JWins.PREVIEW_NO);
  	wins.SetVision(BizContrato.SLIM);

  	 
  	return wins;
  }
  public JWins getObjetivos() throws Exception {
  	winsprorrateo=null;
  	GuiContratos wins = new GuiContratos();
   	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
   		wins.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
   		wins.getRecords().addFixedFilter("where company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getTicketCompany()+") and ( bsp_contrato.id in (select contrato from bsp_prorrateo where contrato = bsp_contrato.id and (cliente='ALL' or cliente like '%"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"%'))) ");
  	} else if (!BizUsuario.getUsr().isAdminUser()) {
			wins.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
	  	wins.getRecords().addOrderBy("company");
		
		} 

   	wins.getRecords().addFilter("tipo_contrato", "O");
    			
  	wins.getRecords().addOrderBy("fecha_desde","DESC");
  	wins.getRecords().addOrderBy("descripcion","ASC");
//  	wins.setForceToolbarPos("left");
//  	wins.setShowWinToolbar(false);
  	wins.setWithFooter(false);
		wins.setPreviewFlag(JWins.PREVIEW_SI);
  	wins.setForceSelected(true);
   	wins.setPreviewSplitPos(500);
   
   	wins.SetVision(BizContrato.OBJETIVOS);
   
  	return wins;
  }
  public JWins getConsolidado(boolean email) throws Exception {
  	winsprorrateo=null;
  	GuiContratoConsolidados wins = new GuiContratoConsolidados();
  	wins.addOrderAdHoc("company", "ASC");
  	wins.addOrderAdHoc("anio", "ASC");
  	wins.addOrderAdHoc("mes", "ASC");
  	wins.addOrderAdHoc("id", "ASC");
  	wins.addOrderAdHoc("descripcion", "ASC");
  	if (email)
  		wins.setToolbarForced(JBaseWin.TOOLBAR_NONE);

  	return wins;
  }
 	GuiClienteProrrateos winsprorrateo; 

  public JWins getProrrateo(boolean email) throws Exception {
  	if (winsprorrateo!=null) return winsprorrateo;
  		winsprorrateo= new GuiClienteProrrateos();;
    	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
    		winsprorrateo.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
    	} else winsprorrateo.getRecords().addFilter("company", BizBSPUser.getUsrBSP().getCompany());
		winsprorrateo.setPreviewFlag(JWins.PREVIEW_NO);
  	if (email)
  		winsprorrateo.setToolbarForced(JBaseWin.TOOLBAR_NONE);
  	winsprorrateo.setRefreshOnlyOnUserRequest(true);

  	return winsprorrateo;
  }
  
	public GuiLiqHeaders getLiquidaciones() throws Exception {
		GuiLiqHeaders wins = new GuiLiqHeaders();
		
		if (BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).isConcentrador()) {
			wins.getRecords().addFixedFilter(" company  in (" + BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).getCustomListCompany() + ")");
		} else if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
   		wins.getRecords().addFixedFilter("where company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getTicketCompany()+") ");
  	}else
			wins.getRecords().addFilter("company", BizBSPUser.getUsrBSP().getCompany());
		wins.getRecords().addOrderBy("fecha_desde", "desc");

		return wins;
	}

	public JWins getLiquidacionesDK() throws Exception {
		BizGrupoDKDetail dks = new BizGrupoDKDetail();
		dks.addJoin(JRelations.JOIN, "BSP_GRUPO_DK", "BSP_GRUPO_DK.id_grupo=BSP_GRUPO_DK_DETAIL.id_grupo and BSP_GRUPO_DK.company=BSP_GRUPO_DK_DETAIL.company");
		dks.addFilter("BSP_GRUPO_DK", "use_liq", "S", "=");

		dks.dontThrowException(true);
		if (BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).isConcentrador()) {
			dks.addFixedFilter(" BSP_GRUPO_DK.company  in (" + BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).getCustomListCompany() + ")");
		} else if (BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).isDependant()) {
			dks.addFixedFilter("where BSP_GRUPO_DK.company  in (" + BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).getTicketCompany() + ") ");
		} else
			dks.addFilter("BSP_GRUPO_DK", "company", BizBSPUser.getUsrBSP().getCompany(), "=");
		dks.addFilter("dk", BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).getCodigoCliente());
		dks.addFilter("view_all", true);
		if (dks.read()) {

			GuiLiqAgrupados wins = new GuiLiqAgrupados();

			if (BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).isConcentrador()) {
				wins.getRecords().addFixedFilter(" company  in (" + BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).getCustomListCompany() + ")");
			} else if (BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).isDependant()) {
				wins.getRecords().addFixedFilter("where company  in (" + BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).getTicketCompany() + ") and grupo_dk=" + dks.getIdtipo() + " ");
			} else
				wins.getRecords().addFilter("company", BizBSPUser.getUsrBSP().getCompany());
			wins.getRecords().addFilter("estado", BizLiqHeader.PUBLICADO);
			wins.getRecords().addOrderBy("fecha_desde", "desc");

			return wins;
		}
		GuiLiqAcums wins = new GuiLiqAcums();

		if (BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).isConcentrador()) {
			wins.getRecords().addFixedFilter(" company  in (" + BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).getCustomListCompany() + ")");
		} else if (BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).isDependant()) {
			wins.getRecords().addFixedFilter("where company  in (" + BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).getTicketCompany() + ") and dk='" + BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).getCodigoCliente() + "' ");
		} else
			wins.getRecords().addFilter("company", BizBSPUser.getUsrBSP().getCompany());
		wins.getRecords().addFilter("estado", BizLiqHeader.PUBLICADO);
		wins.getRecords().addOrderBy("fecha_desde", "desc");

		return wins;
	}
  public JWins getEventos() throws Exception {
  	GuiBSPSqlEvents wins = new GuiBSPSqlEvents();
		if (!BizUsuario.getUsr().isAdminUser()) 
			wins.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
  	wins.getRecords().addFilter("activo", true);
		if (!BizUsuario.IsAdminCompanyUser())
			wins.getRecords().addFilter("invisible", "S","<>");
  	wins.getRecords().addOrderBy("descripcion");
//  	wins.setShowWinToolbar(false);
		wins.setPreviewFlag(JWins.PREVIEW_SI);
		wins.setForceSelected(true);
  	wins.setPreviewSplitPos(680);
  	
   	return wins;
  }

//  public JWins getReportes() throws Exception {
//  	GuiSqlEventHistories wins = new GuiSqlEventHistories();
//		if (!BizUsuario.getUsr().isAdminUser()) 
//			wins.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
//  	wins.getRecords().addOrderBy("fecha","DESC");
////  	wins.setShowWinToolbar(false);
//		wins.setPreviewFlag(JWins.PREVIEW_SI);
//		wins.setForceSelected(true);
//  	wins.setPreviewSplitPos(680);
//  	
//   	return wins;
//  }
	private JWin getReportes()  throws Exception {
  	GuiReportes f = new GuiReportes();
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
			f.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
				f.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getTicketCompany()+")");
  	} 
  	if (!BizUsuario.getUsr().isAdminUser())
			f.GetcDato().setCompany(BizUsuario.getUsr().getCompany());
		return f;
	}
  public JWins getReservas() throws Exception {
  	GuiPNRReservas wins = new GuiPNRReservas();
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
  		wins.getRecords().addFixedFilter(" company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCustomListCompany()+")");
  	} else if (!BizUsuario.getUsr().isAdminUser()) {
			wins.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
	  	wins.getRecords().addOrderBy("company");
		
		}
 
		wins.setPreviewFlag(JWins.PREVIEW_NO);
 
  	return wins;
  }
  public JWins getDKs() throws Exception {
  	GuiClienteViews wins = new GuiClienteViews();
  	wins.setPreviewFlag(JWins.PREVIEW_NO);
		return wins;
  }
//	public GuiGraphs getGraficos() throws Exception {
//		GuiGraphs g=new GuiGraphs();
//  	g.getRecords().addFilter("company", this.GetcDato().getCompany());
// // 	g.getRecords().addFilter("list_id", this.GetcDato().getListId());
//		return g;
//	}

}
