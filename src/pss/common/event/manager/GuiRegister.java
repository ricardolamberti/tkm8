package pss.common.event.manager;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiRegister extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiRegister() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizRegister(); }
  public int GetNroIcono()   throws Exception { return 117; }
  public String GetTitle()   throws Exception { return "Registro Evento"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormRegister.class; }
  public String  getKeyField() throws Exception { return "event_code"; }
  public String  getDescripField() { return "event_code"; }
  public BizRegister GetcDato() throws Exception { return (BizRegister) this.getRecord(); }

 }
