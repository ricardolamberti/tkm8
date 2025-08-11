package pss.common.customLogin;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiCustomLoginLogo extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiCustomLoginLogo() throws Exception {
  }


  @Override
	public JRecord ObtenerDato()   throws Exception { return new BizCustomLoginLogo(); }
  @Override
	public int GetNroIcono()   throws Exception { return 755; }
  @Override
	public String GetTitle()   throws Exception { return "Subir Imagen"; }
  @Override
	public Class<? extends JBaseForm> getFormBase() throws Exception { return FormCustomLoginLogo.class; }
  @Override
	public String  getKeyField() throws Exception { return "directory"; }
  @Override
	public String  getDescripField() { return "file"; }
  public BizCustomLoginLogo GetcDato() throws Exception { return (BizCustomLoginLogo) this.getRecord(); }


	@Override
	public void createActionMap() throws Exception {
		createActionUpdate();

	}

}