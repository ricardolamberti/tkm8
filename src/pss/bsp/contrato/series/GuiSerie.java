package pss.bsp.contrato.series;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiSerie extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiSerie() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizSerie(); }
  public int GetNroIcono()   throws Exception { return 15010; }
  public String GetTitle()   throws Exception { return "Serie"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormSerie.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "variable"; }
  public BizSerie GetcDato() throws Exception { return (BizSerie) this.getRecord(); }

 }
