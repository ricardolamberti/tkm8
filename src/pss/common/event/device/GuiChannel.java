package pss.common.event.device;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActSubmit;
import pss.core.winUI.forms.JBaseForm;

public class GuiChannel extends JWin {

  /**
   * Constructor de la Clase
   */
  public GuiChannel() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizChannel(); }
  public int GetNroIcono()   throws Exception { return 10006; }
  public String GetTitle()   throws Exception { return "Channel"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { 
  	return FormChannel.class; 
  }
  public String  getKeyField() throws Exception { return "id_channel"; }
  public String  getDescripField() { return "id_channel"; }
  public BizChannel GetcDato() throws Exception { return (BizChannel) this.getRecord(); }
 
  public void createActionMap() throws Exception {
		addAction(10,   "Ver dispositivo", null, 900, true, true);
		addAction(20,   "Send test Message", null, 5074, true, true);
		super.createActionMap();
	}  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	return super.OkAction(a);
  }
	
	
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==10) return new JActQuery(getDevice());
  	if (a.getId()==20) return new JActSubmit(this) {
  		@Override
  		public void submit() throws Exception {
  			GetcDato().execProcessSendMessage("TEST","Mensaje de prueba");
  			super.submit();
  		}
  	};
  	return super.getSubmitFor(a);
  }
  
  public GuiDevice getDevice() throws Exception {
  	GuiDevice device = new GuiDevice();
  	device.setRecord(GetcDato().getObjDevice());
  	return device;
  }

 }
