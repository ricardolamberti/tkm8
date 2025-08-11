package pss.bsp.consolid.model.segment;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiSegment extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiSegment() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizSegment(); }
  public int GetNroIcono()   throws Exception { return 10003; }
  public String GetTitle()   throws Exception { return "Segment"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormSegment.class; }
  public String  getKeyField() throws Exception { return "id_segment"; }
  public String  getDescripField() { return "pnr_locator"; }
  public BizSegment GetcDato() throws Exception { return (BizSegment) this.getRecord(); }

 }
