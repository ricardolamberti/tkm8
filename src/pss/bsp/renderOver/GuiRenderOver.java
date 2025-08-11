package  pss.bsp.renderOver;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiRenderOver extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiRenderOver() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizRenderOver(); }
  public int GetNroIcono()   throws Exception { return 10032; }
  public String GetTitle()   throws Exception { return "Render"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormRenderOver.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "detalle"; }
  public BizRenderOver GetcDato() throws Exception { return (BizRenderOver) this.getRecord(); }

 }
