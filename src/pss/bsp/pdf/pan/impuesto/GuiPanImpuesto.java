package pss.bsp.pdf.pan.impuesto;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiPanImpuesto extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiPanImpuesto() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizPanImpuesto(); }
  public int GetNroIcono()   throws Exception { return 10049; }
  public String GetTitle()   throws Exception { return "Impuesto"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormPanImpuesto.class; }
  public String  getKeyField() throws Exception { return "iso"; }
  public String  getDescripField() { return "contado"; }
  public BizPanImpuesto GetcDato() throws Exception { return (BizPanImpuesto) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
		createActionQuery();
  }
 }
