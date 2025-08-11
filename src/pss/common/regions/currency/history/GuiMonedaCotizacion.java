package pss.common.regions.currency.history;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.winUI.forms.JBaseForm;

public class GuiMonedaCotizacion extends JWin {


  /**
   * Constructor de la Clase
   */
  public GuiMonedaCotizacion() throws Exception {
  }


  @Override
	public JRecord ObtenerDato()   throws Exception { return new BizMonedaCotizacion(); }
  @Override
	public int GetNroIcono()   throws Exception { return 54; }
  @Override
	public String GetTitle()   throws Exception { return "Cotización"; }
  @Override
	public Class<? extends JBaseForm> getFormBase() throws Exception { return FormMonedaCotizacion.class; }

  @Override
	public Class<? extends JBaseForm> getFormFlat() throws Exception { return FormMonedaCotizacionFlat.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  @Override
	public void createActionMap() throws Exception {
  	this.addAction(1, "Consultar", null, 117, false, false);
  }


  /**
   * Devuelvo el dato ya casteado
   */
  public BizMonedaCotizacion GetcDato() throws Exception { 
  	return (BizMonedaCotizacion) this.getRecord(); 
  }

 }
