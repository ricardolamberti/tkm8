package pss.tourism.carrier;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.winUI.forms.JBaseForm;

public class GuiCarrier extends JWin {


  /**
   * Constructor de la Clase
   */
  public GuiCarrier() throws Exception {
  }


  @Override
	public JRecord ObtenerDato()   throws Exception { return new BizCarrier(); }
  @Override
	public int GetNroIcono()   throws Exception { return 1113; }
  @Override
	public String GetTitle()   throws Exception { return "Carrier"; }
  @Override
	public Class<? extends JBaseForm> getFormBase() throws Exception { return FormCarrier.class; }
  @Override
	public String  getKeyField() throws Exception { 
  	return GetVision().equals("BSP")?"cod_iata":"carrier"; 
  	}
  @Override
	public String  getDescripField() { return "description"; }
  public BizCarrier GetcDato() throws Exception { return (BizCarrier) this.getRecord(); }


  @Override
	public JAct getSubmitFor(BizAction a) throws Exception {
//  	if (a.getId()==10) return new JActSubmit(this, a.getId()) {@Override
//		public void submit() throws Exception {GetcDato().execCreateArticle();}};
  	return null;
  }  
  
  @Override
	public boolean OkAction(BizAction zAct) throws Exception {
//    if ( zAct.getId() == 10) return BizTourismUser.isTourismUser(); 
    return true;
  }  
 }
