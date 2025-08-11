package pss.tourism.pnr;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.consolid.model.trxOra.GuiTrxOras;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.GuiDetalle;
import pss.bsp.reembolsos.GuiReembolsos;
import pss.common.customList.config.dataBiz.GuiDataBizs;
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
import pss.tourism.voidManual.BizVoidManual;

public class GuiPNRTicket extends JWin {

	//GuiTravelFile oPos;
	
  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiPNRTicket() throws Exception {
  }
  
  @Override
  public int GetNroIcono() throws Exception {
  	return 5129;
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizPNRTicket(); }
  @Override
	public String GetTitle()       throws Exception { 
  	return "Boleto Nº "+GetcDato().getNumeroboleto()+ " (PNR "+GetcDato().getCodigopnr()+") "+GetcDato().getCreationDate(); 
  }
  @Override
	public String getKeyField() { return "CodigoPNR"; }
  @Override
	public String getDescripField() { return "CodigoPNR"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormPNRTicket.class; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
		this.createActionQuery();
  	BizAction a;
  	a=addAction(160, "Eliminar", null, 48, true, true);
  	a=addAction(10, "Voidear", null, 48, true, true);
  	a=addAction(11, "Desvoidear", null, 48, true, true);
  	a=addAction(13, "Desigualar DK", null, 48, true, true);
  	a.setConfirmMessage(true);
  	a.setMulti(true);
  	addAction(12, "Reprocesar", null, 17, true, true).setInToolbarMore(true).setMulti(true);
  	addAction(25, "Ver Original", null, 5129, true, true);
  	addAction(26, "Ver Contrato", null, 5129, true, true);
   	addAction(30, "Archivo Interface", null, 10047, false, false);
  	addAction(80, "Impuestos", null, 5117, false, false);
  	addAction(90, "Segmentos Vuelos", null, 5129, false, false);
  	addAction(95, "Bookings", null, 5129, false, false);
  	addAction(110, "Análisis de tarifa", null, 5129, false, false);
  	addAction(120, "Todos los Datos", null, 979, false, false);
  	addAction(100, "Ticket Conectados", null, 5117, false, false);
  	addAction(150, "Documentos", null, 5117, false, false);
  	addAction(170, "Reembolsos", null, 5117, false, false);
  }
  
  @Override
  public boolean OkAction(BizAction act) throws Exception {
  	if (act.getId()==10) return !this.GetcDato().isVoided();// &&  !GetcDato().isReemited()&&  !GetcDato().isVoided();
  	if (act.getId()==11) return this.GetcDato().isVoided() && BizVoidManual.isManualVoid(GetcDato().getCompany(), GetcDato().getNumeroboleto())!=null;// &&  !GetcDato().isReemited()&&  !GetcDato().isVoided();
  	if (act.getId()==12) return BizUsuario.IsAdminCompanyUser();// &&  !GetcDato().isReemited()&&  !GetcDato().isVoided();
  	if (act.getId()==160) return BizUsuario.IsAdminCompanyUser();// &&  !GetcDato().isReemited()&&  !GetcDato().isVoided();
    if (act.getId()==13) return BizVoidManual.isManualDK(GetcDato().getCompany(), GetcDato().getNumeroboleto())!=null;// &&  !GetcDato().isReemited()&&  !GetcDato().isVoided();
  	if (act.getId()==25) return this.GetcDato().getObjReemitted()!=null;
  	if (act.getId()==26) return this.GetcDato().hasUpfront();
   return super.OkAction(act);
  }

  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==10) return new JActSubmit(this, a.getId()) {
  		public void submit() throws Exception {
  		
  			GetcDato().execVoidear();
  		}
  	};
  	if (a.getId()==11) return new JActSubmit(this, a.getId()) {
  		public void submit() throws Exception {
  		
  			GetcDato().execDesvoidear();
  		}
  	};
  	if (a.getId()==12) return new JActSubmit(this, a.getId()) {
  		public void submit() throws Exception {
  			PNRCache.clean();
  			GetcDato().execReprocesar();
  		}
  	};
  	if (a.getId()==160) return new JActSubmit(this, a.getId()) {
  		public void submit() throws Exception {
  			GetcDato().execProcessDelete();
  		}
  	};
	  if (a.getId()==30) return new JActWins(getArchivos());
  	if (a.getId()==25) return new JActQuery(this.getReemitted());
  	if (a.getId()==26) return new JActQueryActive(this.getUpfornt());
     
//	  if (a.getId()==40) return new JActSubmit(this, 40) {
//			public void submit() throws Exception {
//				GetcDato().execProcessCruzarNegocio();
//			}
//	  };
//  	if (a.getId()==50) return new JActQuery(this.getArchivo());
  	if (a.getId()==80) return new JActWins(this.getImpuestos());
  	if (a.getId()==90) return new JActWins(this.getSegmentos());
  	if (a.getId()==95) return new JActWins(this.getBookings());
  	if (a.getId()==110) return new JActWins(this.getSegmentosPrecios());
  	if (a.getId()==120) return new JActWins(this.getDatos());
   	if (a.getId()==100) return new JActWins(this.getConectados());
  	if (a.getId()==150) return new JActWins(this.getDocumentos());
  	if (a.getId()==170) return new JActWins(this.getReembolsos());

  	
  	return super.getSubmitFor(a);
  }
  
//  @Override
//	public boolean canConvertToURL() {
//  	return false;
//  }
//
//  private JWin getNewPNRNegocio() throws Exception {
//  	GuiNewAirTravel tk = new GuiNewAirTravel();
//  	tk.setRecord(this.GetcDato().createTxAirTicket(true));
////  	tk.GetcDato().addFilter("company", this.GetcDato().getCompany());
////  	tk.GetcDato().addFilter("codigo_pnr", this.GetcDato().getCodigopnr());
////  	tk.GetcDato().addFilter("ticket", this.GetcDato().getNumeroboleto());
//  	return tk;
//  }

  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizPNRTicket GetcDato() throws Exception {
    return (BizPNRTicket) this.getRecord();
  }

//  @Override
//	public JAct generateSale(GuiPOS pos) throws Exception {
//  	this.oPos = (GuiTravelFile)pos;
////  	if (this.oPos.GetcDato().getCurrentSale().getOperation().equals(BizSale.OPER_COTIZACION))
////  		JExcepcion.SendError("El viaje no está confirmado");
//  	return new JActWinsSelect(getPendingPNR(), this, true);
//  }

  private JWins getConectados() throws Exception {
  	JWins wins = new GuiPNRConnectedTickets();
  	wins.getRecords().addFilter("company", GetcDato().getCompany());
  	wins.getRecords().addFilter("NumeroBoleto", GetcDato().getNumeroboleto());
  	return wins;
  }
  private JWins getDocumentos() throws Exception {
  	GuiTrxOras wins = new GuiTrxOras();
  	wins.getRecords().addFixedFilter("where boleto like '"+GetcDato().getNumeroboleto()+"%' ");
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
  		wins.getRecords().addFixedFilter("where dk = '"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"' ");
  	}	
  	wins.getRecords().addOrderBy("fecha_factura","DESC");
  	return wins;
  }
  private JWins getReembolsos() throws Exception {
  	GuiReembolsos wins = new GuiReembolsos();
  	wins.getRecords().addFixedFilter("where boleto_rfnd = '"+GetcDato().getNumeroboleto()+"' ");
  	wins.getRecords().addOrderBy("fecha","DESC");
  	return wins;
  }
//  private JWins getPagos() throws Exception {
//  	JWins wins = new GuiPNRPayments();
//  	wins.getRecords().addFilter("company", GetcDato().getCompany());
//  	wins.getRecords().addFilter("NumeroBoleto", GetcDato().getNumeroboleto());
//  	return wins;
//  }
  private JWins getImpuestos() throws Exception {
  	JWins wins = new GuiPNRTaxs();
  	wins.getRecords().addFilter("company", GetcDato().getCompany());
  	wins.getRecords().addFilter("interface_id", GetcDato().getId());
  	return wins;
  }
  private JWins getSegmentos() throws Exception {
  	JWins wins = new GuiPNRSegmentoAereos();
  	wins.getRecords().addFilter("company", GetcDato().getCompany());
  	wins.getRecords().addFilter("interface_id", GetcDato().getId());
  	wins.getRecords().addOrderBy("CodigoSegmento","ASC");
  	return wins;
  }
  private JWins getBookings() throws Exception {
  	JWins wins = new GuiBookings();
  	wins.getRecords().addFilter("company", GetcDato().getCompany());
  	wins.getRecords().addFilter("interface_id", GetcDato().getId());
  	wins.getRecords().addOrderBy("FechaDespegue","DESC");
  	return wins;
  }
  private JWins getSegmentosPrecios() throws Exception {
  	JWins wins = new GuiPNRFares();
  	wins.getRecords().addFilter("company", GetcDato().getCompany());
  	wins.getRecords().addFilter("interface_id", GetcDato().getId());
  	wins.getRecords().addOrderBy("fare","ASC");
  	return wins;
  }
  private JWins getDatos() throws Exception {
  	GuiDataBizs wins = new GuiDataBizs();
  	wins.getcRecords().setObjWinClass(this);
  	return wins;
  }
  private JWins getPendingPNR() throws Exception {
  	JWins wins = new GuiPNRTickets();
		wins.SetVision("SINTOT");
  	wins.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
  	wins.getRecords().addOrderBy("FechaCreacion", "desc");
//  	wins.getRecords().addFilter("consumed", false);
//   	wins.getRecords().addFilter("void", false);
//  	wins.getRecords().addFilter("CodigoCliente", this.oPos.GetcDato().getCurrentSale().getObjCustomer().getCodigoIdent()).setDynamic(true);
  	return wins;
  }
  private GuiPNRFiles getArchivos() throws Exception {
  	GuiPNRFiles wins = new GuiPNRFiles();
  	wins.readAll(this);
  	return wins;
  }
	private GuiPNRTicket getReemitted() throws Exception {
		GuiPNRTicket win = new GuiPNRTicket();
		win.setRecord(GetcDato().getObjReemitted());
		return win;
	}
	private JWin getUpfornt() throws Exception {
		GuiDetalle win = new GuiDetalle();
		BizDetalle det = GetcDato().getObjUpfront();
		if (det==null) throw new Exception("Contrato no encontrado");
		win.setRecord(det);
		return win.getRelativeWin();
	}

	@Override
	public boolean canConvertToURL() throws Exception {
		return false;
	}

	@Override
	public JAct Drop(JBaseWin zBaseWin) throws Exception {
//		if (zBaseWin instanceof GuiPNRTicket) {
//			BizPNRTicket pnrTicket = ((GuiPNRTicket)zBaseWin).GetcDato();
//
//		  GuiTxAirTicket tk = new GuiTxAirTicket();
//			tk.setDropListener(oPos);
//			tk.setRecord(pnrTicket.createTxAirTicket(false));
//		
//			return new JActUpdate(tk, 4);
//		}
//		if (zBaseWin instanceof GuiPNRTickets) {
//			this.GetcDato().execProcessTickets(((JRecords<BizPNRTicket>)zBaseWin.GetBaseDato()), oPos.GetccDato());
//			return new JActQuery(oPos);
//		}
		return null;
	}

//	public void setPos(GuiTravelFile pos) {
//		oPos = pos;
//	}
  
	protected boolean checkActionOnDrop(BizAction a) throws Exception {
		if (a.getId()==40) return false;
		return true;
	}

	@Override
	public String getFieldBackground(String zColName) throws Exception {
		if (GetVision().length()>0&&zColName.length()>0&&GetVision().toLowerCase().indexOf(zColName.toLowerCase())!=-1)
			return "559349";
		return super.getFieldBackground(zColName);
	}

//	
//	@Override
//	public String GetIconFile() throws Exception {
//		if (this.GetcDato().isAnulado())
//			return "inhabilitar.gif";
////		if (this.GetcDato().isTipoAereo())
////			return "Aereo.png";
//		if (this.GetcDato().isTipoBus())
//			return "Transporte.png";
//		return "Aereo.png";
//	}

	@Override
	public int GetSimpleClick() throws Exception {
		return 1;
	}
}
