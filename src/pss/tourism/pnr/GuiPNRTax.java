package pss.tourism.pnr;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiPNRTax extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiPNRTax() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizPNRTax(); }
  public int GetNroIcono()   throws Exception { return 15006; }
  public String GetTitle()   throws Exception { return "Impuesto"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormPNRTax.class; }
  public String  getKeyField() throws Exception { return "secuencia"; }
  public String  getDescripField() { return "codigoimpuesto"; }
  public BizPNRTax GetcDato() throws Exception { return (BizPNRTax) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
		this.createActionQuery();
  }

 }
