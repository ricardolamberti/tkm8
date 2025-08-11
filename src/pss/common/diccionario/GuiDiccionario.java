package  pss.common.diccionario;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiDiccionario extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiDiccionario() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizDiccionario(); }
  public int GetNroIcono()   throws Exception { return 10051; }
  public String GetTitle()   throws Exception { return "Diccionario"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormDiccionario.class; }
  public String  getKeyField() throws Exception { return "id_dicc"; }
  public String  getDescripField() { return "descripcion"; }
  public BizDiccionario GetcDato() throws Exception { return (BizDiccionario) this.getRecord(); }



 }
