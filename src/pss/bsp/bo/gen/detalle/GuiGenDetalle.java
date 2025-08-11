package  pss.bsp.bo.gen.detalle;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiGenDetalle extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiGenDetalle() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizGenDetalle(); }
  public int GetNroIcono()   throws Exception { return 10036; }
  public String GetTitle()   throws Exception { return "Detalle Interaz BackOffice"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormGenDetalle.class; }
  public String  getKeyField() throws Exception { return "linea"; }
  public String  getDescripField() { return "d1"; }
  public BizGenDetalle GetcDato() throws Exception { return (BizGenDetalle) this.getRecord(); }
  public void createActionMap() throws Exception {
		createActionQuery();
	}

 }
