package pss.bsp.bspBusiness;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiBSPChildCompany extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiBSPChildCompany() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizBSPChildCompany(); }
  public int GetNroIcono()   throws Exception { return 5000; }
  public String GetTitle()   throws Exception { return "Compania TKM"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormBSPChildCompany.class; }
  public String  getKeyField() throws Exception { return "child_company"; }
  public String  getDescripField() { return "child_company"; }
  public BizBSPChildCompany GetcDato() throws Exception { return (BizBSPChildCompany) this.getRecord(); }

 }
