package  pss.common.customList.config.field.funciones;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.winUI.forms.JBaseForm;

public class GuiFuncion extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiFuncion() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizFuncion(); }
  public int GetNroIcono()   throws Exception { return 10064; }
  public String GetTitle()   throws Exception { return "Fución"; }
  public String  getKeyField() throws Exception { return "funcion"; }
  public String  getDescripField() { return "descripcion"; }
  public BizFuncion GetcDato() throws Exception { return (BizFuncion) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
  }
  

 }
