package pss.common.training.level1;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiLevel1 extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiLevel1() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizLevel1(); }
  public int GetNroIcono()   throws Exception { return 208; }
  public String GetTitle()   throws Exception { return "Nivel 1 - Entrenamiento"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormLevel1.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizLevel1 GetcDato() throws Exception { return (BizLevel1) this.getRecord(); }



 }
