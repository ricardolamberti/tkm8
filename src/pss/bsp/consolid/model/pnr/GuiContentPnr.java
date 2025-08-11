package pss.bsp.consolid.model.pnr;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiContentPnr extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiContentPnr() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizContentPnr(); }
  public int GetNroIcono()   throws Exception { return 10003; }
  public String GetTitle()   throws Exception { return "Trx"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormContentPnr.class; }
  public String  getKeyField() throws Exception { return "id_ctscontent_pnr"; }
  public String  getDescripField() { return "name_rec"; }
  public BizContentPnr GetcDato() throws Exception { return (BizContentPnr) this.getRecord(); }

 }
