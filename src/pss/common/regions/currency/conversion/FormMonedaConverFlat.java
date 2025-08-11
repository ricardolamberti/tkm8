package pss.common.regions.currency.conversion;

import pss.common.regions.currency.BizMoneda;
import pss.common.regions.currency.GuiMonedaPaises;
import pss.core.services.fields.JCurrency;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormControlResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;


public class FormMonedaConverFlat extends JBaseForm {

	public FormMonedaConverFlat() throws Exception {
  }

  public GuiMonedaConver getWin() { 
  	return (GuiMonedaConver) getBaseWin(); 
  }

  public BizMonedaConver getConver() throws Exception { 
  	return this.getWin().GetcDato(); 
  }

  @Override
  public void InicializarPanelHeader(JWin zBase) throws Exception {
  	this.AddItemLabel("Cotizaciones");
  }

  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
//    SetExitOnOk(true);
  	JFormPanelResponsive col = this.AddItemColumn();
  	JFormPanelResponsive icons = col.AddItemColumn(4);
  	BizMoneda m =BizMoneda.getMoneda(this.getConver().getMonedaSource());	
  	if (m.getNroIcono()!=0) icons.addButton( m.getNroIcono(), null, false, 4);
  	else icons.addButton( m.GetDescrip(),null, 4);
  	icons.addButton( 112, null, false, 1).size(11);
  	icons.AddItemLabel( "", 1);
  	m =BizMoneda.getMoneda(this.getConver().getMonedaTarget());	
  	if (m.getNroIcono()!=0) icons.addButton( m.getNroIcono(), null, false, 4);
  	else icons.addButton( m.GetDescrip(),null, 4);
  	col.addButton( this.getConver().getCotizCompra()+"", 20, 3).right();
  	col.addButton( this.getConver().getCotizVenta()+"", 20, 3).right();
  	col.AddItemLabel( "", 1);
  	JFormPanelResponsive line = col.AddItemColumn(2).AddInLinePanel();
//  	line.addButton( 13, 20, 0);
  	line.addButton( 6099, 10, 0);
  }
  

}