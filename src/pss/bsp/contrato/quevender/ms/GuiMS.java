package pss.bsp.contrato.quevender.ms;

import pss.bsp.contrato.quevender.GuiQueVender;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;
import pss.tourism.pnr.GuiBookings;
import pss.tourism.pnr.GuiPNRTickets;

public class GuiMS extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiMS() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizMS(); }
  public int GetNroIcono()   throws Exception { return 15011; }
  public String GetTitle()   throws Exception { return "Objetivo MS"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormMS.class; }
  public String  getKeyField() throws Exception { return "agrupador2"; }
  public String  getDescripField() { return "cantidad1"; }
  public BizMS GetcDato() throws Exception { return (BizMS) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
		this.addAction(10, "Detalle", null, 10020, true, true, true, "Group");
		this.addAction(11, "Detalle", null, 10020, true, true, true, "Group");
  }

  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==10) return new JActWins(getDetalle());
  	if (a.getId()==11) return new JActWins(getDetalleBok());
 	return super.getSubmitFor(a);
  }

  @Override
  public String getDobleClick() throws Exception {
  	if (((GuiQueVender)((GuiMSs)getParent()).getParent()).GetcDato().isTicket())return ""+10;
  	return ""+11;
  }
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getId()==10) return ((GuiQueVender)((GuiMSs)getParent()).getParent()).GetcDato().isTicket();
  	if (a.getId()==11) return !((GuiQueVender)((GuiMSs)getParent()).getParent()).GetcDato().isTicket();
  	return super.OkAction(a);
  }
	
	public GuiPNRTickets getDetalle() throws Exception {
  	GuiPNRTickets	c = new GuiPNRTickets();
  	c.setRecords(GetcDato().getDetalleTickets());
  	return c;
  }
	public GuiBookings getDetalleBok() throws Exception {
		GuiBookings	c = new GuiBookings();
  	c.setRecords(GetcDato().getDetalleBooking());
  	return c;
  }
  @Override
  public boolean canConvertToURL() throws Exception {
  	return false;
  }
 }
