package  pss.bsp.pdf.chl.impuesto;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiChileImpuesto extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiChileImpuesto() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizChileImpuesto(); }
  public int GetNroIcono()   throws Exception { return 10049; }
  public String GetTitle()   throws Exception { return "Impuesto"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormChileImpuesto.class; }
  public String  getKeyField() throws Exception { return "iso"; }
  public String  getDescripField() { return "contado"; }
  public BizChileImpuesto GetcDato() throws Exception { return (BizChileImpuesto) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
		createActionQuery();
  }
 }
