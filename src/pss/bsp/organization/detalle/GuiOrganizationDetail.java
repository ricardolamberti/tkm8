package  pss.bsp.organization.detalle;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiOrganizationDetail extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiOrganizationDetail() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizOrganizationDetail(); }
  public int GetNroIcono()   throws Exception { return 10032; }
  public String GetTitle()   throws Exception { return "Iata"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormOrganizationDetail.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "iata"; }
  public BizOrganizationDetail GetcDato() throws Exception { return (BizOrganizationDetail) this.getRecord(); }

 }
