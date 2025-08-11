package pss.common.event.device;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.winUI.forms.JBaseForm;

public class GuiTypeDevice extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiTypeDevice() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizTypeDevice(); }
  public int GetNroIcono()   throws Exception { return 900; }
  public String GetTitle()   throws Exception { return "Type Device"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormTypeDevice.class; }
  public String  getKeyField() throws Exception { return "id_typedevice"; }
  public String  getDescripField() { return "description"; }
  public BizTypeDevice GetcDato() throws Exception { return (BizTypeDevice) this.getRecord(); }

	
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
