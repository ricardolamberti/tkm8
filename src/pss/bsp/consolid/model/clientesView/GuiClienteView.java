package pss.bsp.consolid.model.clientesView;

import pss.bsp.consolid.model.cotizacionDK.GuiCotizacionDKs;
import pss.bsp.consolid.model.feeDK.GuiFeeDKs;
import pss.bsp.consolid.model.liquidacion.acumulado.GuiLiqAcums;
import pss.bsp.consolid.model.liquidacion.header.BizLiqHeader;
import pss.bsp.consolid.model.report.GuiReportDks;
import pss.bsp.dk.GuiClienteDKs;
import pss.common.event.mailing.GuiMailingPersona;
import pss.common.regions.company.BizCompany;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecord;
import pss.core.tools.JMessageInfo;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiClienteView extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiClienteView() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizClienteView(); }
  public int GetNroIcono()   throws Exception { return 10003; }
  public String GetTitle()   throws Exception { return GetcDato().getCustomerName(); }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormClienteView.class; }
  public String  getKeyField() throws Exception { return "customer_number"; }
  public String  getDescripField() { return "customer_name"; }
  public BizClienteView GetcDato() throws Exception { return (BizClienteView) this.getRecord(); }


	public void createActionMap() throws Exception {
		this.addAction(10, "Detalles", null, 10020, false, false, true, "Group");		
		this.addAction(20, "Liquidaciones", null, 10020, false, false, true, "Group");		
		this.addAction(30, "Cotizaciones", null, 10020, false, false, true, "Group");		
		this.addAction(40, "Gestión Fee", null, 10020, false, false, true, "Group");		
		this.addAction(45, "Reportes", null, 10020, false, false, true, "Group");		
//		this.addAction(20, "Asociar", null, 10020, true, true, true, "Group");		
		this.addAction(50, "Generar Site", null, 10074, true, true, true, "Group");		
		this.addAction(55, "Actualizar Site", null, 10020, true, true, true, "Group");		
		this.addAction(60, "Habilitar", null, 5511, true, true, true, "Group");		
		this.addAction(70, "Deshabilitar", null, 5510, true, true, true, "Group");		
		this.addAction(80, "Limpiar clave", null, 5513, true, true, true, "Group");		
		
		this.createActionQuery();
		this.createActionUpdate();

	}

	@Override
	public boolean OkAction(BizAction a) throws Exception {
		if (a.getId()==20) return GetcDato().hasExistCompany();
		if (a.getId()==30) return GetcDato().hasExistCompany();
		if (a.getId()==40) return GetcDato().hasExistCompany();
		if (a.getId()==45) return GetcDato().hasExistCompany() && GetcDato().getReporte4();
		if (a.getId()==50) return !GetcDato().hasExistCompany();
		if (a.getId()==55) return GetcDato().hasExistCompany();
		if (a.getId()==80) return GetcDato().hasExistCompany();
		if (a.getId()==60) return GetcDato().hasExistCompany() && !GetcDato().getHabilitado();
		if (a.getId()==70) return GetcDato().hasExistCompany() && GetcDato().getHabilitado();
		return super.OkAction(a);
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)
			return new JActWins(this.getDetail());
		if (a.getId() == 20)
			return new JActWins(this.getLiquidaciones());
		if (a.getId() == 30)
			return new JActWins(this.getCotizaciones());
		if (a.getId() == 40)
			return new JActWins(this.getFees());
		if (a.getId() == 45)
			return new JActWins(this.getReportesDK());
//		if (a.getId() == 20)
//			return new JActWinsSelect(this.getMailing(),this,true);
		if (a.getId() == 50)	return new JActSubmit(this) {
			public void submit() throws Exception {
				GetcDato().execGenerar();
				setMessage(new JMessageInfo("Site generado!"));
				
			}  
		};	
		if (a.getId() == 55)	return new JActSubmit(this) {
			public void submit() throws Exception {
	  		BizCompany comp = new BizCompany();
	  		comp.dontThrowException(true);
	  		if (comp.Read("C_"+GetcDato().getCustomerNumber())) {
	  			GetcDato().execActualizar(BizUsuario.getUsr().getCompany());
	  			setMessage(new JMessageInfo("Site Actualizado!"));
					return;
	  		}
  			setMessage(new JMessageInfo(JMessageInfo.TYPE_ERROR,"Site Inexistente!"));
		
			}  
		};
		if (a.getId() == 60)	return new JActSubmit(this) {
			public void submit() throws Exception {
	  		
				GetcDato().getObjUsuario().execActivar();
				GetcDato().clean();
				setMessage(new JMessageInfo("Usuario Activado"));
			}  
		};	
		if (a.getId() == 70)	return new JActSubmit(this) {
			public void submit() throws Exception {
	  		
				GetcDato().getObjUsuario().execDesactivar();
				GetcDato().clean();
				setMessage(new JMessageInfo("Usuario desactivado"));
			}
		};	
		if (a.getId() == 80)	return new JActSubmit(this) {
			public void submit() throws Exception {
	  		
				GetcDato().getObjUsuario().execBlanquearClave();
				GetcDato().clean();
				setMessage(new JMessageInfo("Clave blanqueada"));
			}  
		};
		return super.getSubmitFor(a);
	}
	

	@Override
	public JAct Drop(JBaseWin zBaseWin) throws Exception {
		if (zBaseWin instanceof GuiMailingPersona) {
			GuiMailingPersona mail = (GuiMailingPersona) zBaseWin;
			mail.GetcDato().setNumero(this.GetcDato().getCustomerNumber());
			mail.GetcDato().setDescipcion(this.GetcDato().getCustomerName());
			mail.GetcDato().execProcessUpdate();
		}
		return super.Drop(zBaseWin);
	}
	public GuiClienteDKs getMailing() throws Exception {
		GuiClienteDKs wins = new GuiClienteDKs();
		wins.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
		//wins.getRecords().addFilter("tipo", BizMailingPersona.CLIENTE_REDUCIDO);
			
		wins.getRecords().addOrderBy("dk","asc");
		
		return wins;
	}
	public GuiClienteDKs getDetail() throws Exception {
		GuiClienteDKs wins = new GuiClienteDKs();
//		wins.getRecords().addFixedFilter("where (codigo='"+ GetcDato().getCustomerNumber()+"' or numero='"+GetcDato().getCustomerNumber()+"') ");
		wins.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
		wins.getRecords().addFilter("dk", GetcDato().getCustomerNumber());
			
		wins.getRecords().addOrderBy("dk","asc");
		
		return wins;
	}	
	public GuiReportDks getReportesDK() throws Exception {
		GuiReportDks wins = new GuiReportDks();
		
		wins.getRecords().addFilter("id_dk",GetcDato().getObjClienteDK().getId());
  	wins.getRecords().addFilter("company",GetcDato().getObjClienteDK().getCompany());
		wins.getRecords().addOrderBy("date_from", "desc");
			
		return wins;
	}
	public GuiFeeDKs getFees() throws Exception {
		GuiFeeDKs wins = new GuiFeeDKs();
		
		wins.getRecords().addFilter("id_dk",GetcDato().getObjClienteDK().getId());
  	wins.getRecords().addFilter("company",GetcDato().getObjClienteDK().getCompany());
		wins.getRecords().addOrderBy("priority", "asc");
		wins.SetVision("DK_ADMIN");
		
		return wins;
	}
	public GuiCotizacionDKs getCotizaciones() throws Exception {
		GuiCotizacionDKs wins = new GuiCotizacionDKs();
		
		wins.getRecords().addFilter("id_dk",GetcDato().getObjClienteDK().getId());
  	wins.getRecords().addFilter("company",GetcDato().getObjClienteDK().getCompany());
		wins.getRecords().addOrderBy("fecha", "desc");
		wins.SetVision("DK_ADMIN");
		
		return wins;
	}
	public GuiLiqAcums getLiquidaciones() throws Exception {
		GuiLiqAcums wins = new GuiLiqAcums();
		
		wins.getRecords().addFilter("dk",GetcDato().getCustomerNumber());
  	wins.getRecords().addFilter("estado",BizLiqHeader.PUBLICADO);
		wins.getRecords().addOrderBy("fecha_desde", "desc");
		wins.SetVision("DK_ADMIN");
		
		return wins;
	}
 }
