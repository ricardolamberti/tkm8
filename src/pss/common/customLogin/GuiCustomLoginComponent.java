package pss.common.customLogin;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiCustomLoginComponent extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiCustomLoginComponent() throws Exception {
  }


  @Override
	public JRecord ObtenerDato()   throws Exception { return new BizCustomLoginComponent(); }
  @Override
	public int GetNroIcono()   throws Exception { return 754; }
  @Override
	public String GetTitle()   throws Exception { return "Componente"; }
  @Override
	public Class<? extends JBaseForm> getFormBase() throws Exception { return FormCustomListComponent.class; }
  @Override
	public String  getKeyField() throws Exception { return "id"; }
  @Override
	public String  getDescripField() { return "link"; }
  public BizCustomLoginComponent GetcDato() throws Exception { return (BizCustomLoginComponent) this.getRecord(); }

 }
