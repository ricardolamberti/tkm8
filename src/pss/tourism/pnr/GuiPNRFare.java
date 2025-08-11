package pss.tourism.pnr;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiPNRFare  extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiPNRFare() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizPNRFare(); }
  public int GetNroIcono()   throws Exception { return 15006; }
  public String GetTitle()   throws Exception { return "Analisis tarifa"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormPNRFare.class; }
  public String  getKeyField() throws Exception { return "secuencia"; }
  public String  getDescripField() { return "fare"; }
  public BizPNRFare GetcDato() throws Exception { return (BizPNRFare) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
		this.createActionQuery();
  }

 }
