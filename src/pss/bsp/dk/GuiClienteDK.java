package  pss.bsp.dk;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.winUI.forms.JBaseForm;

public class GuiClienteDK extends JWin {


  /**
   * Constructor de la Clase
   */
  public GuiClienteDK() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizClienteDK(); }
  public int GetNroIcono()   throws Exception { return 10036; }
  public String GetTitle()   throws Exception { return "Cotizacion DK"; 
  }
  public Class<? extends JBaseForm> getFormBase() throws Exception { 
return FormClienteDK.class; 
  }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { 
    	return "dk"; 
  }
  public BizClienteDK GetcDato() throws Exception { return (BizClienteDK) this.getRecord(); }

  public void createActionMap() throws Exception {
			super.createActionMap();
	}  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	return super.OkAction(a);
  }
	
	
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  
  	return super.getSubmitFor(a);
  }
  

 }
