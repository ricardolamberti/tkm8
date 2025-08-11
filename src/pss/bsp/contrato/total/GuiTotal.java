package pss.bsp.contrato.total;

import pss.bsp.contrato.quevender.GuiQueVender;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;
import pss.tourism.pnr.GuiBookings;
import pss.tourism.pnr.GuiPNRTickets;

public class GuiTotal extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiTotal() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizTotal(); }
  public int GetNroIcono()   throws Exception { return 15011; }
  public String GetTitle()   throws Exception { return "Total"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormTotal.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "grupo"; }
  public BizTotal GetcDato() throws Exception { return (BizTotal) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
	 }

  @Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		return super.getSubmitFor(a);
	}


 }
