package pss.common.mail.message;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiMessage extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiMessage() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizMessage(); }
  public int GetNroIcono()   throws Exception { return 10036; }
  public String GetTitle()   throws Exception { return "Mensaje"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormMessage.class; }
  public String  getKeyField() throws Exception { return "id_message"; }
  public String  getDescripField() { return "title"; }
  public BizMessage GetcDato() throws Exception { return (BizMessage) this.getRecord(); }

 }
