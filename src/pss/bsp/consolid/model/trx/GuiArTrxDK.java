package pss.bsp.consolid.model.trx;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiArTrxDK extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiArTrxDK() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizArTrxDK(); }
  public int GetNroIcono()   throws Exception { return 10003; }
  public String GetTitle()   throws Exception { return "Trx"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormArTrxDK.class; }
  public String  getKeyField() throws Exception { return "transaccion_id"; }
  public String  getDescripField() { return "solicitante"; }
  public BizArTrxDK GetcDato() throws Exception { return (BizArTrxDK) this.getRecord(); }

 }
