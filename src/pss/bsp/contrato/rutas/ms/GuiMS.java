package pss.bsp.contrato.rutas.ms;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiMS extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiMS() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizMS(); }
  public int GetNroIcono()   throws Exception { return 15011; }
  public String GetTitle()   throws Exception { return "Objetivo MS"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormMS.class; }
  public String  getKeyField() throws Exception { return "linea"; }
  public String  getDescripField() { return "aerolinea"; }
  public BizMS GetcDato() throws Exception { return (BizMS) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
  }

  @Override
  public boolean canConvertToURL() throws Exception {
  	return false;
  }
 }
