package  pss.bsp.regions.detalle;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiRegionDetail extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiRegionDetail() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizRegionDetail(); }
  public int GetNroIcono()   throws Exception { return 10032; }
  public String GetTitle()   throws Exception { return "Pais regional"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormRegionDetail.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "descripcion_pais"; }
  public BizRegionDetail GetcDato() throws Exception { return (BizRegionDetail) this.getRecord(); }

 }
