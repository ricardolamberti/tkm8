package  pss.common.agenda.feriado;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiFeriado extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiFeriado() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizFeriado(); }
  public int GetNroIcono()   throws Exception { return 2003; }
  public String GetTitle()   throws Exception { return "Feriado"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormFeriado.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizFeriado GetcDato() throws Exception { return (BizFeriado) this.getRecord(); }
 
 }
