package  pss.bsp.pdf.arg.impuesto;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiArgImpuesto extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiArgImpuesto() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizArgImpuesto(); }
  public int GetNroIcono()   throws Exception { return 10049; }
  public String GetTitle()   throws Exception { return "Impuesto"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormArgImpuesto.class; }
  public String  getKeyField() throws Exception { return "iso"; }
  public String  getDescripField() { return "contado"; }
  public BizArgImpuesto GetcDato() throws Exception { return (BizArgImpuesto) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
		createActionQuery();
  }
 }
