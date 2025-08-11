package pss.common.layout;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiLayoutClonar extends JWin {
	
	public GuiLayoutClonar(){}
	
  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizLayoutClonar(); }
  @Override
	public String GetTitle()       throws Exception { return "Clonar Layout"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormLayoutClonar.class; }
  @Override
	public int GetNroIcono() throws Exception { return 857; }
  
  public BizLayoutClonar GetcDato() throws Exception {
    return (BizLayoutClonar) this.getRecord();
  }

}
