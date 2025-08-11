package  pss.bsp.pdf.col.impuesto;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiColImpuesto extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiColImpuesto() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizColImpuesto(); }
  public int GetNroIcono()   throws Exception { return 10049; }
  public String GetTitle()   throws Exception { return "Impuesto"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormColImpuesto.class; }
  public String  getKeyField() throws Exception { return "iso"; }
  public String  getDescripField() { return "contado"; }
  public BizColImpuesto GetcDato() throws Exception { return (BizColImpuesto) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
		createActionQuery();
  }
 }
