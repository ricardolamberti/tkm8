package pss.common.customForm;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiCustomFormField extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiCustomFormField() throws Exception {
  }


  @Override
	public JRecord ObtenerDato()   throws Exception { return new BizCustomFormField(); }
  @Override
	public int GetNroIcono()   throws Exception {
  	return 5104; 
  }
  @Override
	public String GetTitle()   throws Exception { return "Período"; }
  @Override
	public Class<? extends JBaseForm> getFormBase() throws Exception { return FormCustomFormField.class; }
  @Override
	public String  getKeyField() throws Exception { return "secuencia"; }
  @Override
	public String  getDescripField() { return "fecha_desde"; }
  public BizCustomFormField GetcDato() throws Exception { return (BizCustomFormField) this.getRecord(); }


  
  
 }
