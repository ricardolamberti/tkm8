package pss.common.regions.currency.history;

import pss.common.regions.divitions.BizPais;
import pss.core.services.fields.JCurrency;
import pss.core.tools.JDateTools;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;


public class FormMonedaCotizacionFlat extends JBaseForm {


  /**
   * Constructor de la Clase
   */
  public FormMonedaCotizacionFlat() throws Exception {
  }

  public GuiMonedaCotizacion getWin() { 
  	return (GuiMonedaCotizacion) getBaseWin(); 
  }
  public BizMonedaCotizacion getCotiz() throws Exception { 
  	return this.getWin().GetcDato(); 
  }
  
  @Override
  public void InicializarPanelHeader(JWin zBase) throws Exception {
  	JFormPanelResponsive col = this.AddItemColumn(6);
  	col.AddItemLabel("", 1);
  	col.AddItemLabel("Fecha", 3);
  	col.AddItemLabel("Compra", 2).right();
  	col.AddItemLabel("Venta", 2).right();
  	col.AddItemLabel("Usuario", 2);
  }
  
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
  	JFormPanelResponsive col = this.AddItemColumn(6);
  	col.addButton(this.getWin().GetNroIcono(), null, false, 1);
  	col.AddItemLabel(JDateTools.DateToString(this.getCotiz().getFechaHora(), "dd/MM/yyyy HH:mm:ss"), 3);
  	col.AddItemLabel(this.getCotiz().getCotizCompra()+"", 4).right().bold();
  	col.AddItemLabel(this.getCotiz().getCotizVenta()+"", 4).right().bold();
  	col.AddItemLabel(this.getCotiz().getUsuario(), 2);
  }


} 