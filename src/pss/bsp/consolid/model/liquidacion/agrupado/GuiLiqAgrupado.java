package pss.bsp.consolid.model.liquidacion.agrupado;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.consolid.model.liquidacion.detail.GuiLiqDetails;
import pss.common.customList.config.relation.JRelations;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActFileGenerate;
import pss.core.win.submits.JActReport;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiLiqAgrupado extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiLiqAgrupado() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizLiqAgrupado(); }
  public int GetNroIcono()   throws Exception { return 10003; }
  public String GetTitle()   throws Exception { return "Liquidacion"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormLiqAgrupado.class; }
  public String  getKeyField() throws Exception { return "liquidacion_id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizLiqAgrupado GetcDato() throws Exception { return (BizLiqAgrupado) this.getRecord(); }

	public void createActionMap() throws Exception {
		this.addAction(10, "Detalles", null, 10020, false, false, true, "Group");		
		this.addAction(20, "Imprimir PDF", null, 10020, true, true, true, "Group").setNuevaVentana(true);		
		this.addAction(30, "Exportar Excel", null, 10020, true, true, true, "Group");		
//		BizAction a=this.addAction(80, "Publicar", null, 10045, true, true, true, "Group").setInToolbar(true);
//		a.setConfirmMessageDescription("Se publicará la Liquidación definitiva");
//		a=this.addAction(90, "Des-Publicar", null, 10045, true, true, true, "Group").setInToolbar(true);
//		a.setConfirmMessageDescription("Se des-publicará la Liquidación");
//		
//		this.addAction(100, "Marcar pendiente cobro", null, 10020, true, true, true, "Group").setMulti(true);		
//		this.addAction(110, "Desmarcar pendiente cobro", null, 10020, true, true, true, "Group").setMulti(true);
		
		this.createActionQuery();

	}

	@Override
	public boolean OkAction(BizAction a) throws Exception {
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
			if (a.getId()==10) return true;
			if (a.getId()==20) return true;
			if (a.getId()==30) return true;
			return false;
		}
//		if (a.getId()==90) return  !GetVision().equals("DK_ADMIN") &&  GetcDato().getEstado().equals(BizLiqHeader.PUBLICADO);
//		if (a.getId()==80) return  !GetVision().equals("DK_ADMIN") && !GetcDato().getEstado().equals(BizLiqHeader.PUBLICADO);
//		if (a.getId()==100) return !GetVision().equals("DK_ADMIN") && !GetcDato().getPendiente();
//		if (a.getId()==110) return !GetVision().equals("DK_ADMIN") &&  GetcDato().getPendiente();

		return super.OkAction(a);
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)
			return new JActWins(this.getDetail());
		if (a.getId() == 20)
			return new JActReport(this.GetcDato().createDataSource(),20) ;
		if (a.getId() == 30)	return new JActFileGenerate(this, a.getId()) {
			@Override
			public String generate() throws Exception {
				return GetcDato().exportarAExcel();
			}
		};	
//		if (a.getId() == 80)	return new JActSubmit(this) {
//			public void submit() throws Exception {
//				GetcDato().execPublicar();
//			}  
//		};	
//		if (a.getId() == 90)	return new JActSubmit(this) {
//			public void submit() throws Exception {
//				GetcDato().execDespublicar();
//			}  
//		};	
//		if (a.getId() == 100)	return new JActSubmit(this) {
//			public void submit() throws Exception {
//				setMessage(GetcDato().execMarcar());
//			}  
//		};
//		if (a.getId() == 110)	return new JActSubmit(this) {
//			public void submit() throws Exception {
//				GetcDato().execDesmarcar();
//			}  
//		};
	
		return super.getSubmitFor(a);
	}
	
	
	public GuiLiqDetails getDetail() throws Exception {
		GuiLiqDetails wins = new GuiLiqDetails();
		wins.getRecords().addFilter("liquidacion_id", GetcDato().getLiquidacionId());
		wins.getRecords().addJoin(JRelations.JOIN, "BSP_GRUPO_DK_DETAIL", "BSP_GRUPO_DK_DETAIL.dk=BSP_LIQUIDATION_DETAIL.dk and BSP_GRUPO_DK_DETAIL.company=BSP_LIQUIDATION_DETAIL.company");
		wins.getRecords().addJoin(JRelations.JOIN, "BSP_GRUPO_DK", "BSP_GRUPO_DK.id_grupo=BSP_GRUPO_DK_DETAIL.id_grupo and BSP_GRUPO_DK.company=BSP_GRUPO_DK_DETAIL.company");
		wins.getRecords().addFilter("BSP_GRUPO_DK", "id_grupo", GetcDato().getGrupoDK(),"=");
	  if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
			wins.getRecords().addFixedFilter("where bsp_liquidation_detail.company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getTicketCompany()+") ");//and ( bsp_liquidation_detail.dk = '"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"') ");
		} else
			wins.getRecords().addFilter("company", GetcDato().getCompany());
			
		wins.getRecords().addOrderBy("linea","asc");
		wins.SetVision(GetVision());
		return wins;
	}

 }
