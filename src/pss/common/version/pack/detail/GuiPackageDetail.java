package pss.common.version.pack.detail;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiPackageDetail extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiPackageDetail() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizPackageDetail(); }
  public int GetNroIcono()   throws Exception { return 10038; }
  public String GetTitle()   throws Exception { return "Detalle Paquete"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormPackageDetail.class; }
  public String  getKeyField() throws Exception { return "id_sub_package"; }
  public String  getDescripField() { return "key_pack"; }
  public BizPackageDetail GetcDato() throws Exception { return (BizPackageDetail) this.getRecord(); }
 
 }
