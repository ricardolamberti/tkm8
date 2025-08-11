package pss.bsp.contrato.detalle.mercado;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiDetalleMercado extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiDetalleMercado() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizDetalleMercado(); }
  public int GetNroIcono()   throws Exception { return 10020; }
  public String GetTitle()   throws Exception { return "Detalle mercado"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormDetalleMercado.class; }
  public String  getKeyField() throws Exception { return "linea"; }
  public String  getDescripField() { return "marketing_id"; }
  public BizDetalleMercado GetcDato() throws Exception { return (BizDetalleMercado) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
		createActionQuery();
  }
  
 }
