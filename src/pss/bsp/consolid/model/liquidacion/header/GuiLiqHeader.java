package pss.bsp.consolid.model.liquidacion.header;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.consolid.model.diferenciaLiq.GuiDiferenciaLiqs;
import pss.bsp.consolid.model.liquidacion.acumulado.GuiLiqAcums;
import pss.bsp.consolid.model.liquidacion.agrupado.GuiLiqAgrupados;
import pss.bsp.consolid.model.liquidacion.detail.GuiLiqDetails;
import pss.bsp.consolid.model.liquidacion.errors.GuiLiqErrors;
import pss.bsp.consolid.model.liquidacion.terrestres.GuiLiqTerrestre;
import pss.bsp.contrato.BizContrato;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;
import pss.tourism.pnr.BizPNRTicket;

public class GuiLiqHeader extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiLiqHeader() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizLiqHeader(); }
  public int GetNroIcono()   throws Exception { return 10003; }
  public String GetTitle()   throws Exception { return GetcDato().getDescripcion(); }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormLiqHeader.class; }
  public Class<? extends JBaseForm> getFormNew() throws Exception { return FormNewLiqHeader.class; }
  public String  getKeyField() throws Exception { return "liquidacion_id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizLiqHeader GetcDato() throws Exception { return (BizLiqHeader) this.getRecord(); }

	public void createActionMap() throws Exception {
		this.addAction(10, "Detalles", null, 10020, false, false, true, "Group");
		this.addAction(20, "Informe", null, 10020, false, false, true, "Group");
		this.addAction(30, "Liquidaciones", null, 10020, false, false, true, "Group");
		this.addAction(120, "Dif.BSP", null, 10020, false, false, true, "Group");
			BizAction  a=this.addAction(40, "Conciliar", null, 10045, true, true, true, "Group").setInToolbar(true);
		a.setConfirmMessageDescription("Realizar la conciliacion puede insumir varios minutos");
		this.addAction(100, "Agrupados", null, 10020, false, false, true, "Group");
		
		
		a=this.addAction(50, "Liquidar", null, 10045, true, true, true, "Group").setInToolbar(true);
		a.setConfirmMessageDescription("Se realizará la Liquidación por DK");
		this.addAction(70, "Agregar Terrestres", null, 10058, true, true).setInToolbar(true);
		a=this.addAction(80, "Publicar", null, 10045, true, true, true, "Group").setInToolbar(true);
		a.setConfirmMessageDescription("Se publicará la Liquidación definitiva");
		a=this.addAction(90, "Des-Publicar", null, 10045, true, true, true, "Group").setInToolbar(true);
		a.setConfirmMessageDescription("Se des-publicará la Liquidación");

		
	
		super.createActionMap();
	}

	@Override
	public boolean OkAction(BizAction a) throws Exception {
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
			if (a.getId()==10) return true;
			return false;
		}
		if (a.getId()==90) return GetcDato().getEstado().equals(BizLiqHeader.PUBLICADO);
		if (a.getId()==80) return !GetcDato().getEstado().equals(BizLiqHeader.PUBLICADO);
		if (a.getId()==40) return !GetcDato().getEstado().equals(BizLiqHeader.PUBLICADO);
		if (a.getId()==50) return !GetcDato().getEstado().equals(BizLiqHeader.PUBLICADO);
		if (a.getId()==70) return !GetcDato().getEstado().equals(BizLiqHeader.PUBLICADO);
		if (a.getId()==JWin.ACTION_DELETE) return !GetcDato().getEstado().equals(BizLiqHeader.PUBLICADO);
		if (a.getId()==JWin.ACTION_UPDATE) return !GetcDato().getEstado().equals(BizLiqHeader.PUBLICADO);
			
		return super.OkAction(a);
	}
	
	 @Override
	public String getFieldForeground(String zColName) throws Exception {
		if (zColName.equals("estado")) {
			if (GetcDato().getEstado().equals(BizLiqHeader.PUBLICADO)) return "#008000";
			if (GetcDato().getEstado().equals(BizLiqHeader.PEND_LIQUIDAR)) return "#0f81b0";
			if (GetcDato().getEstado().equals(BizLiqHeader.PEND_CONCILIAR)) return "#baa705";
			if (GetcDato().getEstado().equals(BizLiqHeader.PEND_PUBLICAR)) return "#750fdf";
		}
		return super.getFieldForeground(zColName);
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)
			return new JActWins(this.getDetail());
		if (a.getId() == 20)
			return new JActWins(this.getError());
		if (a.getId() == 30)
			return new JActWins(this.getLiquidaciones());
		if (a.getId() == 100)
			return new JActWins(this.getAgrupados());
		if (a.getId() == 120)
			return new JActWins(this.getDetailDif());
		if (a.getId() == 40)	return new JActSubmit(this) {
			public void submit() throws Exception {
				GetcDato().execProcLimpiar();
				BizContrato.ExecCalcule(GetcDato().getCompany());
				BizPNRTicket.ExecCalculeOver(GetcDato().getCompany(),GetcDato().getFechaDesde(),GetcDato().getFechaHasta());
				GetcDato().execConciliar();
			}  
		};	
		if (a.getId() == 50)	return new JActSubmit(this) {
			public void submit() throws Exception {
				if (GetcDato().getEstado().equals(BizLiqHeader.PEND_CONCILIAR))
					throw new Exception("Antes de liquidar, debe Conciliar");
				GetcDato().execLiquidar();
			}  
		};	
		if (a.getId() == 80)	return new JActSubmit(this) {
			public void submit() throws Exception {
				if (GetcDato().getEstado().equals(BizLiqHeader.PEND_CONCILIAR))
					throw new Exception("Antes de publicar, debe Conciliar");
				if (GetcDato().getEstado().equals(BizLiqHeader.PEND_LIQUIDAR))
					throw new Exception("Antes de publicar, debe Liquidar");
				GetcDato().execPublicar(false);
			}  
		};	
		if (a.getId() == 85)	return new JActSubmit(this) {
			public void submit() throws Exception {
				if (GetcDato().getEstado().equals(BizLiqHeader.PEND_CONCILIAR))
					throw new Exception("Antes de publicar, debe Conciliar");
				if (GetcDato().getEstado().equals(BizLiqHeader.PEND_LIQUIDAR))
					throw new Exception("Antes de publicar, debe Liquidar");
				GetcDato().execPublicar(true);
			}  
		};	
		if (a.getId() == 90)	return new JActSubmit(this) {
			public void submit() throws Exception {
				GetcDato().execDespublicar();
			}  
		};	
		if (a.getId()==70) return new JActNew(getNewTerrestres(),0);
		
		return super.getSubmitFor(a);
	}
	
	public GuiLiqTerrestre getNewTerrestres() throws Exception {
		GuiLiqTerrestre terrestre = new GuiLiqTerrestre();
		terrestre.getRecord().addFilter("liquidacion_id", GetcDato().getLiquidacionId());
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
  		terrestre.getRecords().addFixedFilter("where company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getTicketCompany()+") ");
  		terrestre.getRecord().addFilter("estado", "PUBLICADO");
  	  		
		} else {
			terrestre.getRecord().addFilter("company", GetcDato().getCompany());
		
		}
		return terrestre;
	}

	public GuiLiqDetails getDetail() throws Exception {
		GuiLiqDetails wins = new GuiLiqDetails();
		wins.getRecords().addFilter("liquidacion_id", GetcDato().getLiquidacionId());
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
			wins.getRecords().addFixedFilter("where company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getTicketCompany()+") and ( bsp_liquidation_detail.dk = '"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"') ");
		} else
			wins.getRecords().addFilter("company", GetcDato().getCompany());
			
		wins.getRecords().addOrderBy("dk","asc");
		wins.getRecords().addOrderBy("linea","asc");
		
		return wins;
	}
	
	public GuiDiferenciaLiqs getDetailDif() throws Exception {
		GuiDiferenciaLiqs wins = new GuiDiferenciaLiqs();
		wins.getRecords().addFilter("id_liq", GetcDato().getLiquidacionId());
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
			wins.getRecords().addFixedFilter("where company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getTicketCompany()+") and ( bsp_liquidation_detail.dk = '"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"') ");
		} else
			wins.getRecords().addFilter("company", GetcDato().getCompany());
			
		wins.getRecords().addOrderBy("linea","asc");
		
		return wins;
	}
	
	public GuiLiqErrors getError() throws Exception {
		GuiLiqErrors wins = new GuiLiqErrors();
		wins.getRecords().addFilter("company", GetcDato().getCompany());
		wins.getRecords().addFilter("liquidacion_id", GetcDato().getLiquidacionId());
		wins.getRecords().addOrderBy("linea","asc");
		
		return wins;
	}	
	
	public GuiLiqAcums getLiquidaciones() throws Exception {
		GuiLiqAcums wins = new GuiLiqAcums();
		wins.getRecords().addFilter("company", GetcDato().getCompany());
		wins.getRecords().addFilter("liquidacion_id", GetcDato().getLiquidacionId());
		wins.getRecords().addOrderBy("dk","asc");
		
		return wins;
	}
	
	public GuiLiqAgrupados getAgrupados() throws Exception {
		GuiLiqAgrupados wins = new GuiLiqAgrupados();
		wins.getRecords().addFilter("company", GetcDato().getCompany());
		wins.getRecords().addFilter("liquidacion_id", GetcDato().getLiquidacionId());
		wins.getRecords().addOrderBy("grupo_dk","asc");
		
		return wins;
	}
 }
