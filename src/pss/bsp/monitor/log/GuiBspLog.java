package  pss.bsp.monitor.log;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiBspLog extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiBspLog() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizBspLog(); }
  public int GetNroIcono()   throws Exception { return 10032; }
  public String GetTitle()   throws Exception { return "Bsp log"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormBspLog.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "log"; }
  public BizBspLog GetcDato() throws Exception { return (BizBspLog) this.getRecord(); }

 }
