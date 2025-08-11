package pss.bsp.consolid.model.liquidacion.terrestres;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiLiqTerrestre extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiLiqTerrestre() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizLiqTerrestre(); }
  public int GetNroIcono()   throws Exception { return 10003; }
  public String GetTitle()   throws Exception { return "Archivo Terrestres"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormLiqTerrestre.class; }
  public String  getKeyField() throws Exception { return "linea"; }
  public String  getDescripField() { return "filename"; }
  public BizLiqTerrestre GetcDato() throws Exception { return (BizLiqTerrestre) this.getRecord(); }

 }
