package pss.common.layout;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiLayoutCampo extends JWin {

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiLayoutCampo() throws Exception {
  }
  
  @Override
	public int GetNroIcono()       throws Exception { return 201; }
  @Override
	public String GetTitle()       throws Exception { return "Campo"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormLayoutCampo.class; }
  @Override
	public String getKeyField()    throws Exception { return "campo"; }
  @Override
	public String getDescripField()                 { return "descr_campo"; }
  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizLayoutCampo(); }

  public BizLayoutCampo GetcDato() throws Exception {
    return (BizLayoutCampo) this.getRecord();
  }

}
