package pss.common.event.device;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiQueueMessage extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiQueueMessage() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizQueueMessage(); }
  public int GetNroIcono()   throws Exception { return 6011; }
  public String GetTitle()   throws Exception { return "Cola dispositivo"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormQueueMessage.class; }
  public String  getKeyField() throws Exception { return "id_queue"; }
  public String  getDescripField() { return "id_queue"; }
  public BizQueueMessage GetcDato() throws Exception { return (BizQueueMessage) this.getRecord(); }

	

 }
