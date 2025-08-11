package pss.bsp.consola.datos.cadenas;

import java.util.Calendar;
import java.util.Date;

import pss.bsp.contrato.GuiContrato;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.FormDetalle;
import pss.bsp.contrato.detalle.GuiDetalle;
import pss.bsp.contrato.logica.ILogicaContrato;
import pss.common.customList.config.carpetas.IContenidoCarpeta;
import pss.common.customList.config.dataBiz.GuiDataBizs;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecord;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;
import pss.tourism.pnr.BizPNRTicket;
import pss.tourism.pnr.FormPNRTicket;
import pss.tourism.pnr.GuiBookings;
import pss.tourism.pnr.GuiPNRConnectedTickets;
import pss.tourism.pnr.GuiPNRFares;
import pss.tourism.pnr.GuiPNRFiles;
import pss.tourism.pnr.GuiPNRSegmentoAereos;
import pss.tourism.pnr.GuiPNRTaxs;
import pss.tourism.pnr.GuiPNRTicket;
import pss.tourism.pnr.GuiPNRTickets;
import pss.tourism.pnr.PNRCache;

public class GuiCadena  extends JWin {

	//GuiTravelFile oPos;
	
  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiCadena() throws Exception {
  }
  
  @Override
  public int GetNroIcono() throws Exception {
  	return 5129;
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizCadena(); }
  @Override
	public String GetTitle()       throws Exception { 
  	return "Boleto N° "+GetcDato().getNumeroboleto()+ " (PNR "+GetcDato().getCodigopnr()+") "+GetcDato().getCreationDate(); 
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
  	addAction(20, "Ver Boleto", null, 5129, true, true);
  	addAction(21, "Ver Revisión 1", null, 5129, true, true);
  	addAction(22, "Ver Revisión 2", null, 5129, true, true);
  	addAction(23, "Ver Revisión 3", null, 5129, true, true);
  	addAction(24, "Ver Revisión 4", null, 5129, true, true);
  	addAction(25, "Ver Revisión 5", null, 5129, true, true);
  	addAction(26, "Ver Revisión 6", null, 5129, true, true);
  	addAction(27, "Ver Revisión 7", null, 5129, true, true);
  	addAction(28, "Ver Revisión 8", null, 5129, true, true);
  	addAction(29, "Ver Revisión 9", null, 5129, true, true);
  	addAction(30, "Ver Revisión 10", null, 5129, true, true);

   }
  
  @Override
  public boolean OkAction(BizAction act) throws Exception {
  	if (act.getId()==12) return BizUsuario.IsAdminCompanyUser();// &&  !GetcDato().isReemited()&&  !GetcDato().isVoided();
  	if (act.getId()==21) return this.GetcDato().hasObjReemitted(1);
  	if (act.getId()==22) return this.GetcDato().hasObjReemitted(2);
  	if (act.getId()==23) return this.GetcDato().hasObjReemitted(3);
  	if (act.getId()==24) return this.GetcDato().hasObjReemitted(4);
  	if (act.getId()==25) return this.GetcDato().hasObjReemitted(5);
  	if (act.getId()==26) return this.GetcDato().hasObjReemitted(6);
  	if (act.getId()==27) return this.GetcDato().hasObjReemitted(7);
  	if (act.getId()==28) return this.GetcDato().hasObjReemitted(8);
  	if (act.getId()==29) return this.GetcDato().hasObjReemitted(9);
  	if (act.getId()==30) return this.GetcDato().hasObjReemitted(10);
  	return super.OkAction(act);
  }

  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==20) return new JActQuery(this.getBoleto());
  	if (a.getId()==21) return new JActQuery(this.getReemitted(1));
  	if (a.getId()==22) return new JActQuery(this.getReemitted(2));
  	if (a.getId()==23) return new JActQuery(this.getReemitted(3));
  	if (a.getId()==24) return new JActQuery(this.getReemitted(4));
  	if (a.getId()==25) return new JActQuery(this.getReemitted(5));
  	if (a.getId()==26) return new JActQuery(this.getReemitted(6));
  	if (a.getId()==27) return new JActQuery(this.getReemitted(7));
  	if (a.getId()==28) return new JActQuery(this.getReemitted(8));
  	if (a.getId()==29) return new JActQuery(this.getReemitted(9));
  	if (a.getId()==30) return new JActQuery(this.getReemitted(10));

  	return super.getSubmitFor(a);
  }
  
  public BizCadena GetcDato() throws Exception {
    return (BizCadena) this.getRecord();
  }
  
	private GuiPNRTicket getReemitted(int level) throws Exception {
		GuiPNRTicket win = new GuiPNRTicket();
		win.setRecord(GetcDato().getObjReemitted(level));
		return win;
	}
	
	
	private GuiPNRTicket getBoleto() throws Exception {
		GuiPNRTicket win = new GuiPNRTicket();
		win.setRecord(GetcDato().getObjBoleto());
		return win;
	}


  
}
