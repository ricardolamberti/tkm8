package  pss.bsp.auxiliar.lineaTemporal;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiLineaTemporal extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiLineaTemporal() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizLineaTemporal(); }
  public int GetNroIcono()   throws Exception { return 2002; }
  public String GetTitle()   throws Exception { return "Momento"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormLineaTemporal.class; }
  public String  getKeyField() throws Exception { return "fecha_serie"; }
  public String  getDescripField() { return "fecha_serie"; }
  public BizLineaTemporal GetcDato() throws Exception { return (BizLineaTemporal) this.getRecord(); }

 
 }
