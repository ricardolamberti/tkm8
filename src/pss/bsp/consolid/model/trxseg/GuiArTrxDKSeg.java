package pss.bsp.consolid.model.trxseg;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiArTrxDKSeg extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiArTrxDKSeg() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizArTrxDKSeg(); }
  public int GetNroIcono()   throws Exception { return 10003; }
  public String GetTitle()   throws Exception { return "Trx"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormArTrxDKSeg.class; }
  public String  getKeyField() throws Exception { return "factura"; }
  public String  getDescripField() { return "descripcion"; }
  public BizArTrxDKSeg GetcDato() throws Exception { return (BizArTrxDKSeg) this.getRecord(); }

 }
