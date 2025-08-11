package  pss.common.agenda.rol;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiEventoRol extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiEventoRol() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizEventoRol(); }
  public int GetNroIcono()   throws Exception { return 15017; }
  public String GetTitle()   throws Exception { return "rol"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormEventoRol.class; }
  public String  getKeyField() throws Exception { return "id_rol"; }
  public String  getDescripField() { return "descripcion"; }
  public BizEventoRol GetcDato() throws Exception { return (BizEventoRol) this.getRecord(); }


 }
