package  pss.bsp.pdf.bol.impuesto;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiBolImpuesto extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiBolImpuesto() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizBolImpuesto(); }
  public int GetNroIcono()   throws Exception { return 10049; }
  public String GetTitle()   throws Exception { return "Impuesto"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormBolImpuesto.class; }
  public String  getKeyField() throws Exception { return "iso"; }
  public String  getDescripField() { return "contado"; }
  public BizBolImpuesto GetcDato() throws Exception { return (BizBolImpuesto) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
		createActionQuery();
  }
 }
