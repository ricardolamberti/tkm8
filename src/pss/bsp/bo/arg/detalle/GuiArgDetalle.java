package  pss.bsp.bo.arg.detalle;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiArgDetalle extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiArgDetalle() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizArgDetalle(); }
  public int GetNroIcono()   throws Exception { return 2002; }
  public String GetTitle()   throws Exception { return "Detalle Interfaz"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormArgDetalle.class; }
  public String  getKeyField() throws Exception { return "linea"; }
  public String  getDescripField() { return "aerolinea"; }
  public BizArgDetalle GetcDato() throws Exception { return (BizArgDetalle) this.getRecord(); }

 }
