package pss.common.customList.config.field.operadores;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;

public class GuiOperador extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiOperador() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizOperador(); }
  public int GetNroIcono()   throws Exception { return 10064; }
  public String GetTitle()   throws Exception { return "Operador"; }
  public String  getKeyField() throws Exception { return "operador"; }
  public String  getDescripField() { return "descripcion"; }
  public BizOperador GetcDato() throws Exception { return (BizOperador) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
  }
  

 }
