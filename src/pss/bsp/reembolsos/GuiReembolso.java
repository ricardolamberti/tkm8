package pss.bsp.reembolsos;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.winUI.forms.JBaseForm;
import pss.tourism.pnr.GuiPNRTicket;

public class GuiReembolso extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiReembolso() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizReembolso(); }
  public int GetNroIcono()   throws Exception { return 10003; }
  public String GetTitle()   throws Exception { return "Reembolso"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormReembolso.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "importe"; }
  public BizReembolso GetcDato() throws Exception { return (BizReembolso) this.getRecord(); }

  public void createActionMap() throws Exception {
    createActionQuery();
    addAction(20, "Ver Boleto", null, 5129, true, true);
   }
  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getId()==20) return this.getBoleto()!=null;
  	return super.OkAction(a);
  }
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==20) return new JActQuery(this.getBoleto());
  	return super.getSubmitFor(a);
  }
	
	private GuiPNRTicket getBoleto() throws Exception {
		GuiPNRTicket win = new GuiPNRTicket();
		win.setRecord(GetcDato().getObjBoleto());
		return win;
	}

 }
