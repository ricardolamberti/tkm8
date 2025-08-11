package  pss.common.security.license.license.detail;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.winUI.forms.JBaseForm;

public class GuiLicenseDetail extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiLicenseDetail() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizLicenseDetail(); }
  public int GetNroIcono()   throws Exception { return 5050; }
  public String GetTitle()   throws Exception { return "Detalle Licencia"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormLicenseDetail.class; }
  public String  getKeyField() throws Exception { return "field"; }
  public String  getDescripField() { return "count"; }
  public BizLicenseDetail GetcDato() throws Exception { return (BizLicenseDetail) this.getRecord(); }
  
  @Override
  public boolean OkAction(BizAction zAct) throws Exception {
  	if (this.GetVision().equals("PROTECT")) return false;
  	return super.OkAction(zAct);
  }

 }
