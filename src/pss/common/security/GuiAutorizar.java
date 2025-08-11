package  pss.common.security;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;


public class GuiAutorizar extends JWin {

  public GuiAutorizar() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizAutorizar(); }
  @Override
	public int GetNroIcono()       throws Exception { return 43; }
  @Override
	public String GetTitle()       throws Exception { return "Autorización"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return null; }
  @Override
	public String getKeyField()   throws Exception { return "login"; }
  @Override
	public String getDescripField()                  { return "login"; }


  @Override
	public void createActionMap() throws Exception {
  }

  public BizAutorizar GetcDato() throws Exception {
    return (BizAutorizar) this.getRecord();
  }


}



