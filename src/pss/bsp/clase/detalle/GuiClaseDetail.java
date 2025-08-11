package  pss.bsp.clase.detalle;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiClaseDetail extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiClaseDetail() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizClaseDetail(); }
  public int GetNroIcono()   throws Exception { return 10032; }
  public String GetTitle()   throws Exception { return "Clase aerolinea"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormClaseDetail.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizClaseDetail GetcDato() throws Exception { return (BizClaseDetail) this.getRecord(); }

 }
