package pss.common.event.manager;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;

public class GuiEventCode extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiEventCode() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return null;}
  public int GetNroIcono()   throws Exception { return 948; }
  public String GetTitle()   throws Exception { return "Codigo Evento"; }
//  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormRegister.class; }
  public String  getKeyField() throws Exception { return "event_code"; }
  public String  getDescripField() { return "descripcion"; }
  public BizEventCode GetcDato() throws Exception { return (BizEventCode) this.getRecord(); }

 }
