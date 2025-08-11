package pss.bsp.hoteles.detalle;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiHotelDetail extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiHotelDetail() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizHotelDetail(); }
  public int GetNroIcono()   throws Exception { return 10032; }
  public String GetTitle()   throws Exception { return "grupo hotel"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormHotelDetail.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizHotelDetail GetcDato() throws Exception { return (BizHotelDetail) this.getRecord(); }

 }
