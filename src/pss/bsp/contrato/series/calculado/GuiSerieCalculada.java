package pss.bsp.contrato.series.calculado;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiSerieCalculada extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiSerieCalculada() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizSerieCalculada(); }
  public int GetNroIcono()   throws Exception { return 15010; }
  public String GetTitle()   throws Exception { return "Estimacion"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormSerieCalculada.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "modelo"; }
  public BizSerieCalculada GetcDato() throws Exception { return (BizSerieCalculada) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
  }
 }
