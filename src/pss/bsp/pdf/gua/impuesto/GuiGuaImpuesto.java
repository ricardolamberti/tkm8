package  pss.bsp.pdf.gua.impuesto;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiGuaImpuesto extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiGuaImpuesto() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizGuaImpuesto(); }
  public int GetNroIcono()   throws Exception { return 10049; }
  public String GetTitle()   throws Exception { return "Impuesto"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormGuaImpuesto.class; }
  public String  getKeyField() throws Exception { return "iso"; }
  public String  getDescripField() { return "contado"; }
  public BizGuaImpuesto GetcDato() throws Exception { return (BizGuaImpuesto) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
		createActionQuery();
  }
 }
