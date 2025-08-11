package  pss.common.personalData.personaRegistro.registros;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiRegistro extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiRegistro() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizRegistro(); }
  public int GetNroIcono()   throws Exception { return 10036; }
  public String GetTitle()   throws Exception { return "Registro"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormRegistro.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizRegistro GetcDato() throws Exception { return (BizRegistro) this.getRecord(); }

 }
