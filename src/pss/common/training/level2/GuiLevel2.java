package pss.common.training.level2;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiLevel2 extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiLevel2() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizLevel2(); }
  public int GetNroIcono()   throws Exception { return 208; }
  public String GetTitle()   throws Exception { return "Nivel 2 - Entrenamiento"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormLevel2.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizLevel2 GetcDato() throws Exception { return (BizLevel2) this.getRecord(); }



 }
