package  pss.common.security;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;


public class GuiWebUserProfile extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiWebUserProfile() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizWebUserProfile(); }
  public int GetNroIcono()   throws Exception { return 401; }
  public String GetTitle()   throws Exception { return "Perfil usuario Web"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormWebUserProfile.class; }
  public String  getKeyField() throws Exception { return "USUARIO"; }
  public String  getDescripField() { return "SKIN"; }
  public BizWebUserProfile GetcDato() throws Exception { return (BizWebUserProfile) this.getRecord(); }

 }