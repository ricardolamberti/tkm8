package pss.common.regions.currency.history;

import pss.common.regions.divitions.BizPais;
import pss.core.services.fields.JCurrency;
import pss.core.tools.JDateTools;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;


public class FormMonedaFechaFlat extends JBaseForm {


  /**
   * Constructor de la Clase
   */
  public FormMonedaFechaFlat() throws Exception {
  }

  public GuiMonedaFecha getWin() { 
  	return (GuiMonedaFecha) getBaseWin(); 
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
  }
  
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
  	JFormPanelResponsive col = this.AddItemColumn(6);
  	col.addButton(this.getWin().GetNroIcono(), null, false, 1);
  	col.AddItemLabel(JDateTools.DateToString(this.getCotiz().getFecha(), "dd/MM/yyyy"), 3);
  	col.AddItemLabel(this.getCotiz().getCotizCompra()+"", 2).right().bold();
  	col.AddItemLabel(this.getCotiz().getCotizVenta()+"", 2).right().bold();
  	col.addButton(13, 2, 2);
	}

} 