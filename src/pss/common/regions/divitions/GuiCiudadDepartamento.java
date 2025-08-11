package  pss.common.regions.divitions;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiCiudadDepartamento extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiCiudadDepartamento() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizCiudadDepartamento(); }
  public int GetNroIcono()   throws Exception { return 15012; }
  public String GetTitle()   throws Exception { return "CiudadDepartamento"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormCiudadDepartamento.class; }
  public String  getKeyField() throws Exception { return "ciudad_id"; }
  public String  getDescripField() { return "nombre"; }
  public BizCiudadDepartamento GetcDato() throws Exception { return (BizCiudadDepartamento) this.getRecord(); }

 }
