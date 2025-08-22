package  pss.bsp.config.carrierGroups.detalle;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;

public class GuiCarrierGroupDetail extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiCarrierGroupDetail() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizCarrierGroupDetail(); }
  public int GetNroIcono()   throws Exception { return 10032; }
  public String GetTitle()   throws Exception { return "Aerolinea"; }
//  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormCarrierGroupDetail.class; }
  public String  getKeyField() throws Exception { return "carrier"; }
  public String  getDescripField() { return "descr_carrier"; }
  public BizCarrierGroupDetail GetcDato() throws Exception { return (BizCarrierGroupDetail) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
		this.createActionDelete();
  }
  
 }
