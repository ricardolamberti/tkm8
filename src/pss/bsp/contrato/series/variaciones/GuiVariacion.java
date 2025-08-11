package pss.bsp.contrato.series.variaciones;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiVariacion extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiVariacion() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizVariacion(); }
  public int GetNroIcono()   throws Exception { return 15011; }
  public String GetTitle()   throws Exception { return "Variacion"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormVariacion.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizVariacion GetcDato() throws Exception { return (BizVariacion) this.getRecord(); }

  
 }
