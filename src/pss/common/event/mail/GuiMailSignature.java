package pss.common.event.mail;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiMailSignature extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiMailSignature() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizMailSignature(); }
  public int GetNroIcono()   throws Exception { return 10003; }
  public String GetTitle()   throws Exception { return "Firma Mail"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormMailSignature.class; }
  public String  getKeyField() throws Exception { return "username"; }
  public String  getDescripField() { return "username"; }
  public BizMailSignature GetcDato() throws Exception { return (BizMailSignature) this.getRecord(); }

 }
