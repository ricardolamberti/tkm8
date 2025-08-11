package pss.bsp.publicity;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiPublicity extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiPublicity() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizPublicity(); }
  public int GetNroIcono()   throws Exception { return 2002; }
  public String GetTitle()   throws Exception { return "Campaña publicitaria"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormPublicity.class; }
  public String  getKeyField() throws Exception { return "linea"; }
  public String  getDescripField() { return "campana"; }
  public BizPublicity GetcDato() throws Exception { return (BizPublicity) this.getRecord(); }

 
 }
