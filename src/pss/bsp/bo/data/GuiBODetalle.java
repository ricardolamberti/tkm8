package  pss.bsp.bo.data;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiBODetalle extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiBODetalle() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizBODetalle(); }
  public int GetNroIcono()   throws Exception { return 2002; }
  public String GetTitle()   throws Exception { return "Detalle Interfaz"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormBODetalle.class; }
  public String  getKeyField() throws Exception { return "linea"; }
  public String  getDescripField() { return "aerolinea"; }
  public BizBODetalle GetcDato() throws Exception { return (BizBODetalle) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
		this.createActionQuery();
  }
 }
