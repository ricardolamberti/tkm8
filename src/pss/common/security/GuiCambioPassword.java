package  pss.common.security;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiCambioPassword extends JWin {

  public GuiCambioPassword() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizCambioPassword(); }
  @Override
	public int GetNroIcono()       throws Exception { return 5023; }
  @Override
	public String GetTitle()       throws Exception { return "Cambio de contraseña"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormCambioPassword.class; }
  @Override
	public String getDescripField()                  { return "descrip"; }



  public BizCambioPassword GetcDato() throws Exception {
    return (BizCambioPassword) this.getRecord();
  }


}
