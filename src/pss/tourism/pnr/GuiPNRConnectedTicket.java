package pss.tourism.pnr;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiPNRConnectedTicket extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiPNRConnectedTicket() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizPNRConnectedTicket(); }
  public int GetNroIcono()   throws Exception { return 15011; }
  public String GetTitle()   throws Exception { return "Conexion"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormPNRConnectedTicket.class; }
  public String  getKeyField() throws Exception { return "numeroboletoconectado"; }
  public String  getDescripField() { return "numeroboletoconectado"; }
  public BizPNRConnectedTicket GetcDato() throws Exception { return (BizPNRConnectedTicket) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
		this.createActionQuery();
  }

 }
