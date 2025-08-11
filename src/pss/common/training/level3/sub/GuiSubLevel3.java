package pss.common.training.level3.sub;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiSubLevel3 extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiSubLevel3() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizSubLevel3(); }
  public int GetNroIcono()   throws Exception { return 208; }
  public String GetTitle()   throws Exception { return "Sub Nivel 3 - Entrenamiento"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormSubLevel3.class; }
  public String  getKeyField() throws Exception { return "subid"; }
  public String  getDescripField() { return "descripcion"; }
  public BizSubLevel3 GetcDato() throws Exception { return (BizSubLevel3) this.getRecord(); }



 }
