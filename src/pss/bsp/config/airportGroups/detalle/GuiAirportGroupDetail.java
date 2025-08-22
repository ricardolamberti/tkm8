package  pss.bsp.config.airportGroups.detalle;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;

public class GuiAirportGroupDetail extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiAirportGroupDetail() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizAirportGroupDetail(); }
  public int GetNroIcono()   throws Exception { return 10032; }
  public String GetTitle()   throws Exception { return "Aeropuerto"; }
//  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormCarrierGroupDetail.class; }
  public String  getKeyField() throws Exception { return "airport"; }
  public String  getDescripField() { return "descr_airport"; }
  public BizAirportGroupDetail GetcDato() throws Exception { return (BizAirportGroupDetail) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
		this.createActionDelete();
  }
  
 }
