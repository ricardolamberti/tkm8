package  pss.bsp.interfaces.dqb.detalle;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.winUI.forms.JBaseForm;
import pss.tourism.pnr.GuiPNRTicket;

public class GuiDQBDetalle extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiDQBDetalle() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizDQBDetalle(); }
  public int GetNroIcono()   throws Exception { return 10020; }
  public String GetTitle()   throws Exception { return "Detalle Parseo"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormDQBDetalle.class; }
  public String  getKeyField() throws Exception { return "linea"; }
  public String  getDescripField() { return "marketing_id"; }
  public BizDQBDetalle GetcDato() throws Exception { return (BizDQBDetalle) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
		createActionQuery();
  	addAction(20, "Ver Boleto", null, 5129, true, true);
   }
	
	 @Override
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
