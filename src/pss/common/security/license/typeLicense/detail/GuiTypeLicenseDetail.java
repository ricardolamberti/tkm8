package  pss.common.security.license.typeLicense.detail;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.winUI.forms.JBaseForm;

public class GuiTypeLicenseDetail extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiTypeLicenseDetail() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizTypeLicenseDetail(); }
  public int GetNroIcono()   throws Exception { return 912; }
  public String GetTitle()   throws Exception { return "Detalle tipo licencia"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormTypeLicenseDetail.class; }
  public String  getKeyField() throws Exception { return "field"; }
  public String  getDescripField() { return "value"; }
  public BizTypeLicenseDetail GetcDato() throws Exception { return (BizTypeLicenseDetail) this.getRecord(); }
  
@Override
	public boolean OkAction(BizAction zAct) throws Exception {
		if (this.GetVision().equals("PROTECT")) return false;
		return super.OkAction(zAct);
	}
 }
