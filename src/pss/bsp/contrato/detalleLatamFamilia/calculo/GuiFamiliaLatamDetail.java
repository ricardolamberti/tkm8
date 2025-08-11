package pss.bsp.contrato.detalleLatamFamilia.calculo;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiFamiliaLatamDetail extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiFamiliaLatamDetail() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizFamiliaLatamDetail(); }
  public int GetNroIcono()   throws Exception { return 10032; }
  public String GetTitle()   throws Exception { return "Familia Latam"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormFamiliaLatamDetail.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "familia"; }
  public BizFamiliaLatamDetail GetcDato() throws Exception { return (BizFamiliaLatamDetail) this.getRecord(); }

 }
