package  pss.bsp.pdf.mex.impuesto;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiMexImpuesto extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiMexImpuesto() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizMexImpuesto(); }
  public int GetNroIcono()   throws Exception { return 10049; }
  public String GetTitle()   throws Exception { return "Impuesto"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormMexImpuesto.class; }
  public String  getKeyField() throws Exception { return "iso"; }
  public String  getDescripField() { return "contado"; }
  public BizMexImpuesto GetcDato() throws Exception { return (BizMexImpuesto) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
		createActionQuery();
  }
 }
