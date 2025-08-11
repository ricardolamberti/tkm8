package  pss.common.pki.signs;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiSign extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiSign() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizSign(); }
  public int GetNroIcono()   throws Exception { return 10040; }
  public String GetTitle()   throws Exception { return "Firma digital"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormSign.class; }
  public String  getKeyField() throws Exception { return "sign_id"; }
  public String  getDescripField() { return "sign_description"; }
  public BizSign GetcDato() throws Exception { return (BizSign) this.getRecord(); }

 }
