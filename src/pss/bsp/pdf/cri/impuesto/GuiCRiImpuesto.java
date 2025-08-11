package  pss.bsp.pdf.cri.impuesto;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiCRiImpuesto extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiCRiImpuesto() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizCRiImpuesto(); }
  public int GetNroIcono()   throws Exception { return 10049; }
  public String GetTitle()   throws Exception { return "Impuesto"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormCRiImpuesto.class; }
  public String  getKeyField() throws Exception { return "iso"; }
  public String  getDescripField() { return "contado"; }
  public BizCRiImpuesto GetcDato() throws Exception { return (BizCRiImpuesto) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
		createActionQuery();
  }
 }
