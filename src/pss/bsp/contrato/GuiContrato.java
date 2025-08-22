package pss.bsp.contrato;

import java.awt.event.KeyEvent;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.bspBusiness.BizBSPUser;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.GuiDetalle;
import pss.bsp.contrato.detalle.GuiDetalles;
import pss.bsp.contrato.total.GuiTotales;
import pss.common.customList.config.carpetas.IContenidoCarpeta;
import pss.common.event.action.GuiSqlEventAction;
import pss.common.event.action.history.BizSqlEventHistory;
import pss.common.event.action.history.GuiSqlEventHistory;
import pss.common.layoutWysiwyg.BizPlantilla;
import pss.common.regions.company.BizCompany;
import pss.common.regions.company.GuiCompanies;
import pss.common.regions.company.GuiCompany;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActBack;
import pss.core.win.submits.JActFileGenerate;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.win.submits.JActWinsSelect;
import pss.core.winUI.forms.JBaseForm;
import pss.tourism.pnr.BizPNRReserva;
import pss.tourism.pnr.GuiPNRReservas;

public class GuiContrato extends JWin implements IContenidoCarpeta {



  /**
   * Constructor de la Clase
   */
  public GuiContrato() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizContrato(); }
  public int GetNroIcono()   throws Exception { return 15014; }
  public String GetTitle()   throws Exception { return GetcDato().isNullDescripcion()?"Contrato":GetcDato().getDescripcion(); }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormContrato.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "descripcion_larga"; }
  public BizContrato GetcDato() throws Exception { return (BizContrato) this.getRecord(); }

  public void createActionMap() throws Exception {
 		this.addAction(30, "Activar(on/off)", null, 15013, true, true, true, "Group").setMulti(true);
 		this.addAction(10, "Objetivos", null, 10020, false, false, true, "Group");
 		super.createActionMap();
		this.addAction(50, "Compartir", null, 10078, true, true);
		BizAction a = addAction(55, "Imprimir",KeyEvent.VK_F7, null, 10050, true, true);
		a.setImprimir(true);
		a.setImportance(1);
 		this.addAction(40, "Vista", null, 10020, true, true, true, "Group");
		this.addAction(80, "Revisado", null, 15013, true, true, true, "Group");
		this.addAction(60, "Recalcular", null, 15013, true, true, true, "Group");
		this.addAction(61, "Repoblar", null, 15013, true, true, true, "Group").setInToolbarMore(true).setMulti(true);
 		this.addAction(70, "Gen.Sig. Periodo", null, 10020, true, true, true, "Group");
 		this.addAction(100, "Copiar otra empresa", null, 10020, true, true, true, "Group").setInToolbarMore(true);
 		this.addAction(120, "Reservas", null, 10020, true, true, true, "Group");
 		this.addAction(150, "Totales", null, 10020, false, false, false, "Group");
 	  }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
//  	if (a.getId()==50) return GetcDato().hasAtencion();
  	
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
  		if (a.getId()==1) return true;
  		if (a.getId()==10) return true;
  	  
			return false;
		}
  	if (a.getId()==61) return BizUsuario.getUsr().isAnyAdminUser();
   	if (a.getId()==100) return BizUsuario.getUsr().isAnyAdminUser();
     	return super.OkAction(a);
  }
    
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)	return new JActWins(this.getDetail());
		if (a.getId() == 55)
			return new JActFileGenerate(this, a.getId()) {
				@Override
				public String generate() throws Exception {
					return GetcDato().imprimirResumen();
				}
		};
		if (a.getId() == 30)
				return new JActSubmit(this) {
				@Override
				public void submit() throws Exception {
					GetcDato().execProcessActive();
					super.submit();
				}
		};

		if (a.getId() == 80)
			return new JActSubmit(this) {
			@Override
			public void submit() throws Exception {
				GetcDato().execProcessFinAtencion();
				super.submit();
			}
	};
		if (a.getId() == 40)	return new JActQuery(this.getVista());
		if (a.getId() == 60)
			return new JActSubmit(this) {
			@Override
			public void submit() throws Exception {
				GetcDato().execProcessRecalcular();
				super.submit();
			}
	};
	if (a.getId() == 61)
		return new JActSubmit(this) {
		@Override
		public void submit() throws Exception {
			GetcDato().execProcessRepoblar();
			super.submit();
		}
};
	if (a.getId() == 70)
		return new JActSubmit(this) {
		@Override
		public void submit() throws Exception {
			GetcDato().execProcessSigPeriodo();
			super.submit();
		}
	};
	if (a.getId() == 100) return new JActWinsSelect(new GuiCompanies(), this);

	if (a.getId()== 50) return new JActNew(this.getCompartir(a), BizAction.DROP);
	if (a.getId()==20) return getActResultPreview(a, true);
	if (a.getId()==22) return getActResultPreview(a, true);
	if (a.getId()==18) return getActResultPreview(a, true);
	if (a.getId() == 120) return new JActWins(getReservasMeta());
	if (a.getId() == 150) return new JActWins(getTotales());
	return super.getSubmitFor(a);
	}
	
	@Override
	public int GetSimpleClick() throws Exception {
		return 40;
	}
	public JWin getCompartir(BizAction a) throws Exception {
		GuiSqlEventAction sql = new GuiSqlEventAction();
		sql.getRecord().addFilter("company", BizBSPUser.getUsrBSP().getCompany());
		sql.getRecord().addFilter("class_evento", BizContrato.class.getCanonicalName());
		sql.getRecord().addFilter("id_evento", GetcDato().getId());
		sql.getRecord().addFilter("descripcion", "Informe contrato");
  	BizPlantilla pl = BizCompany.getObjPlantilla(BizBSPUser.getUsrBSP().getCompany(), "sys_contrato");
		if (pl!=null) sql.getRecord().addFilter("id_plantilla",pl.getId());
		sql.setDropListener(this);
		return sql;
	}

	public JWin getVista() throws Exception {
//		if (getNumberDetalle()==1) {
//			return getFirstDetalle();
//		}
		return this;
	}
	GuiDetalle objDet;
	
	public GuiDetalle getFirstDetalle() throws Exception {
		if (objDet!=null) return objDet;
		GuiDetalles wins = new GuiDetalles();
		wins.getRecords().addFilter("id", GetcDato().getId());
		wins.getRecords().setTop(1);
		wins.readAll();
		if (!wins.nextRecord()) return null;
		return objDet= (GuiDetalle)wins.getRecord();
	
 }
	public long getNumberDetalle() throws Exception {
		GuiDetalles wins = new GuiDetalles();
		wins.getRecords().addFilter("id", GetcDato().getId());
		return wins.SelectCount();
	
 }
	
	public JWins getDetail() throws Exception {
		GuiDetalles wins = new GuiDetalles();
		wins.getRecords().addFilter("id", GetcDato().getId());
		wins.getRecords().addOrderBy("prioridad","ASC");
			wins.getRecords().addOrderBy("kicker","ASC");
		wins.getRecords().addOrderBy("descripcion","DESC");
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
			wins.getRecords().addFixedFilter("where company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getTicketCompany()+") and ( bsp_contrato_detalle.linea in (select detalle from bsp_prorrateo where detalle = bsp_contrato_detalle.linea and (cliente='ALL' or cliente like '%"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"%'))) ");
		} else {
			wins.getRecords().addFilter("company", GetcDato().getCompany());
				
		}
			
		return wins;
	}
	
	
	public JWins getReservasMeta() throws Exception {
		GuiPNRReservas reservas = new GuiPNRReservas();
		reservas.getRecords().setStatic(true);
		GuiDetalles wins = new GuiDetalles();
		wins.getRecords().addFilter("id", GetcDato().getId());
		JIterator<BizDetalle> it = getDetail().getRecords().getStaticIterator();
		while (it.hasMoreElements()) {
			BizDetalle det = it.nextElement();
			JRecords<BizPNRReserva> reservatDet = det.getListaReservasMeta();
			reservas.getRecords().getStaticItems().addElements(reservatDet.getStaticItems());
		}
		
		return reservas;
	}
	
	public JWins getTotales() throws Exception {
		GuiTotales tots = new GuiTotales();
		
		tots.getRecords().addFilter("id", GetcDato().getId());
	
		return tots;
	}
	
	
	@Override
	public String getFieldForeground(String zColName) throws Exception {
		if (GetcDato().hasAtencion()) return "a81e1e";
		return super.getFieldBackground(zColName);
	}
	
	@Override
	public String getFieldTooltip(String zColName) throws Exception {
		if (GetcDato().hasAtencion())
			return GetcDato().getAtencion();
		return super.getFieldTooltip(zColName);
	}
	
	public String getImageIfTrue(String zColName)  throws Exception{
		if (zColName.equals("has_atencion")) return "/images/alert_column.gif";
		return super.getImageIfTrue(zColName);
	}
//	@Override
//	public String getScrollToolbar() throws Exception {
//		return "none";
//	}
	
	@Override
	public JAct Drop(JBaseWin zBaseWin) throws Exception {
	  	if (zBaseWin instanceof GuiSqlEventAction) {
  		GuiSqlEventAction action = (GuiSqlEventAction)zBaseWin;
  		
  		if (action.GetcDato().isAccionDOWNLOAD()) {
  			return new JActFileGenerate(action) {
  				public String generate() throws Exception {
  		  		BizSqlEventHistory hist=((GuiSqlEventAction)getResult()).GetcDato().getObjSqlEvent().generarAviso(null,((GuiSqlEventAction)getResult()).GetcDato(),false);
  					return hist.getArchivoAsociado();
  				};
  			};
  		}

  		BizSqlEventHistory hist = action.GetcDato().execProcessExecute(null);
   		if (hist==null) return new JActBack(this);
   	    		
  		GuiSqlEventHistory ghist = new GuiSqlEventHistory();
  		ghist.setRecord(hist);
  		return new JActQuery(ghist);
  	}
	  if (zBaseWin instanceof GuiCompany) {
	  	GuiCompany compania = (GuiCompany)zBaseWin;
					GetcDato().execProcessCopiarOtraEmpresa(compania.GetcDato());
		}
		return super.Drop(zBaseWin);
	}


	@Override
	public boolean read(String company, String id) throws Exception {
		GetcDato().addFilter("company", company);
		GetcDato().addFilter("active", "S");
		GetcDato().addFilter("descripcion", id);
		GetcDato().dontThrowException(true);
		return GetcDato().read();
	}


	@Override
	public JAct getActResultPreview(BizAction a, boolean inform) throws Exception {
		return new JActWins(getDetail());
	}


	@Override
	public boolean isSystemProtect() throws Exception {
		return false;
	}


	@Override
	public String imprimir(BizAction a) throws Exception {
		return GetcDato().imprimirResumen();
	}



 }
