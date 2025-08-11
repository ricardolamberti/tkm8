package pss.bsp.contrato.detalle.variaciones;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiVariacionParticular extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiVariacionParticular() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizVariacionPaticular(); }
  public int GetNroIcono()   throws Exception { return 15011; }
  public String GetTitle()   throws Exception { return "Variacion"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormVariacionParticular.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizVariacionPaticular GetcDato() throws Exception { return (BizVariacionPaticular) this.getRecord(); }

 }
