package  pss.bsp.interfaces.copa.detalle;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiCopaDetalle extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiCopaDetalle() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizCopaDetalle(); }
  public int GetNroIcono()   throws Exception { return 10020; }
  public String GetTitle()   throws Exception { return "Detalle Parseo"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormCopaDetalle.class; }
  public String  getKeyField() throws Exception { return "linea"; }
  public String  getDescripField() { return "marketing_id"; }
  public BizCopaDetalle GetcDato() throws Exception { return (BizCopaDetalle) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
		createActionQuery();
  }
  
 }
