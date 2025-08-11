package pss.bsp.contrato.detalle.prorrateo.tickets;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.winUI.forms.JBaseForm;
import pss.tourism.pnr.GuiPNRTicket;

public class GuiTicketProrrateo extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiTicketProrrateo() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizTicketProrrateo(); }
  public int GetNroIcono()   throws Exception { return 15011; }
  public String GetTitle()   throws Exception { return "Ticket prorrateo"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormTicketProrrateo.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "ticket.NumeroBoleto"; }
  public BizTicketProrrateo GetcDato() throws Exception { return (BizTicketProrrateo) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
   	this.addAction(50, "Ver ticket", null, 5012, true, true, true, "Group" );

  }
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 50)  return new JActQuery(getObjTicket());

	  return super.getSubmitFor(a);
  }

  public GuiPNRTicket getObjTicket() throws Exception {
  	GuiPNRTicket pnr = new GuiPNRTicket();
  	pnr.setRecord(GetcDato().getObjTicket());
  	return pnr;
  }

  @Override
  public int GetDobleClick() throws Exception {
  	return 50;
  }
  
 
  
  
  @Override
  public boolean canConvertToURL() throws Exception {
  	return false;
  }
 }
