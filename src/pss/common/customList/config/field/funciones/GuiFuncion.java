package  pss.common.customList.config.field.funciones;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;

public class GuiFuncion extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiFuncion() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizFuncion(); }
  public int GetNroIcono()   throws Exception { return 10064; }
  public String GetTitle()   throws Exception { return "Fuci�n"; }
  public String  getKeyField() throws Exception { return "funcion"; }
  public String  getDescripField() { return "descripcion"; }
  public BizFuncion GetcDato() throws Exception { return (BizFuncion) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
  }
  

 }
