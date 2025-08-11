package  pss.common.security;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiLogTrace extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiLogTrace() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizLogTrace(); }
  @Override
	public int GetNroIcono()       throws Exception { return 10003; }
  @Override
	public String GetTitle()       throws Exception { return "Log Trace"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormLogTrace.class; }
  @Override
	public String getKeyField()   throws Exception { return "nrotrace"; }
  @Override
	public String getDescripField()                  { return "usuario"; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
  }


  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizLogTrace GetcDato() throws Exception {
    return (BizLogTrace) this.getRecord();
  }


}
