package pss.common.event.device;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiDevice extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiDevice() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizDevice(); }
  public int GetNroIcono()   throws Exception { return 900; }
  public String GetTitle()   throws Exception { return "Device"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormDevice.class; }
  public String  getKeyField() throws Exception { return "id_device"; }
  public String  getDescripField() { return "id_device"; }
  public BizDevice GetcDato() throws Exception { return (BizDevice) this.getRecord(); }

	
  public void createActionMap() throws Exception {
		addAction(10,   "Cola mensajes", null, 6011, false, false);
		addAction(20,   "Inactive", null, 12, true, true);
		addAction(30,   "Active", null, 5607, true, true);
		super.createActionMap();
	}  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getId()==20) return GetcDato().getActive();
  	if (a.getId()==30) return !GetcDato().getActive();
  	return super.OkAction(a);
  }
	
	
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
   	if (a.getId()==10) return new JActWins(getQueues());
   	if (a.getId()==20) return new JActSubmit(this) {
   		@Override
   		public void submit() throws Exception {
   			GetcDato().setActive(false);
   			GetcDato().execProcessUpdate();
   			super.submit();
   		}
   	};
   	if (a.getId()==30) return new JActSubmit(this) {
   		@Override
   		public void submit() throws Exception {
   			GetcDato().setActive(true);
   			GetcDato().execProcessUpdate();
   			super.submit();
   		}
   	};
    return super.getSubmitFor(a);
  }
  
  public GuiQueueMessages getQueues() throws Exception {
  	GuiQueueMessages queues = new GuiQueueMessages();
  	queues.getRecords().addFilter("id_device", GetcDato().getIdDevice());
  	queues.getRecords().addOrderBy("date","DESC");
  	return queues;
  }
 }
