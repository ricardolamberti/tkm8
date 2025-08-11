package  pss.bsp.bspBusiness;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiCompanyExtraData extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiCompanyExtraData() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizCompanyExtraData(); }
  public int GetNroIcono()   throws Exception { return 5000; }
  public String GetTitle()   throws Exception { return "Compania TKM"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormCompanyExtraData.class; }
  public String  getKeyField() throws Exception { return "company"; }
  public String  getDescripField() { return "company"; }
  public BizCompanyExtraData GetcDato() throws Exception { return (BizCompanyExtraData) this.getRecord(); }

 }
