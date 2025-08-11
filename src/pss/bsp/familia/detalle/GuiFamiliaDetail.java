package pss.bsp.familia.detalle;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiFamiliaDetail extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiFamiliaDetail() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizFamiliaDetail(); }
  public int GetNroIcono()   throws Exception { return 10032; }
  public String GetTitle()   throws Exception { return "Familia aerolinea"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormFamiliaDetail.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizFamiliaDetail GetcDato() throws Exception { return (BizFamiliaDetail) this.getRecord(); }

 }
