package pss.common.regions.currency.history;

import pss.common.security.BizUsuario;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.winUI.forms.JBaseForm;

public class GuiMonedaFecha extends GuiMonedaCotizacion {


  /**
   * Constructor de la Clase
   */
  public GuiMonedaFecha() throws Exception {
  }

  @Override
	public Class<? extends JBaseForm> getFormFlat() throws Exception { return FormMonedaFechaFlat.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  @Override
	public void createActionMap() throws Exception {
  	this.addAction(1, "Consultar", null, 117, false, false);
  	this.createActionUpdate();
  	this.createActionDelete();
  }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getId()==3) return BizUsuario.getUsr().isAnyAdminUser();
  	return super.OkAction(a);
  }

 }
