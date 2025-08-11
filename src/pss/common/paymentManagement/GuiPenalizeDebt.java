package pss.common.paymentManagement;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiPenalizeDebt extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiPenalizeDebt() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizPenalizeDebt(); }
  public int GetNroIcono()   throws Exception { return 92; }
  public String GetTitle()   throws Exception { return "Penalidad de Deuda"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormPenalizeDebt.class; }
  public String  getKeyField() throws Exception { return "company"; }
  public String  getDescripField() { return "company"; }
  public BizPenalizeDebt GetcDato() throws Exception { return (BizPenalizeDebt) this.getRecord(); }

 }
