package  pss.bsp.gds;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiInterfazNew extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiInterfazNew() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizInterfazNew(); }
  public int GetNroIcono()   throws Exception { return 15014; }
  public String GetTitle()   throws Exception { return "Interfaz Novedad"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormInterfazNew.class; }
  public String  getKeyField() throws Exception { return "company"; }
  public String  getDescripField() { return "lastupdate"; }
  public BizInterfazNew GetcDato() throws Exception { return (BizInterfazNew) this.getRecord(); }

 }
