package pss.bsp.contrato.conocidos;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiContratoConocido extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiContratoConocido() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizContratoConocido(); }
  public int GetNroIcono()   throws Exception { return 10003; }
  public String GetTitle()   throws Exception { return "Contrato conocido"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormContratoConocido.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizContratoConocido GetcDato() throws Exception { return (BizContratoConocido) this.getRecord(); }

 }
