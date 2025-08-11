package  pss.bsp.gds;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiGds extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiGds() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizGds(); }
  public int GetNroIcono()   throws Exception { return 15009; }
  public String GetTitle()   throws Exception { return "Interfaz gds"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormGds.class; }
  public String  getKeyField() throws Exception { return "numeroboleto"; }
  public String  getDescripField() { return "numeroboleto"; }
  public BizGds GetcDato() throws Exception { return (BizGds) this.getRecord(); }

 }
