package pss.common.regions.currency.conversion;

import pss.common.security.BizUsuario;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.lists.JWinList;
//import pss.erp.Interfaces.afipCurrExchange.GuiAfipCurrencyExchanges;

public class GuiMonedaConvers extends JWins {
  /**
   * Constructor de la Clase
   */
  public GuiMonedaConvers() throws Exception {
  }

  @Override
	public int     GetNroIcono() throws Exception  { return 5041; }
  @Override
	public String  GetTitle()    throws Exception  { return "Cotizaciones Monedas"; }
  @Override
	public Class<? extends JWin>   GetClassWin()                   { return GuiMonedaConver.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  @Override
 public void createActionMap() throws Exception {
    this.addActionNew( 1, "Nuevo Registro" );
//    this.addAction(10, "Cotiz AFIP", null, 5041, false, false);
  }
  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getId()==1) return BizUsuario.getUsr().isAnyAdminUser();
  	return super.OkAction(a);
  }
  
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
//  	if (a.getId()==10) return new JActWins(this.getCotizacionesAFIP());
  	return null;
  }
  
  
  
  
  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	if (zLista.isForExport()) {
			zLista.AddColumnaLista("descr_conver_html");//.setTagHtml("b");
		  zLista.AddColumnaLista("Compra", "cotiz_compra").setActionOnClick(20);
		  zLista.AddColumnaLista("Venta", "cotiz_venta").setActionOnClick(20);
  	} else {
  		zLista.addColumnWinAction("Flat", 1);
  		zLista.setWithHeader(false);
  	}
  }
  
  @Override
  protected void configureList(JWinList list) throws Exception {
  	list.setHideActions(true);
  }
  
  @Override
  public boolean isForceHideActions() {
  	return true;
  }

  @Override
  public boolean hasNavigationBar() throws Exception {
  	return !this.GetVision().equals(BizMonedaConver.VISION_VIEW);
  }
  
 

  @Override
  public String getToolbarForced() throws Exception {
  	if (this.GetVision().equals(BizMonedaConver.VISION_VIEW))
  		return JBaseWin.TOOLBAR_NONE;
  	return super.getToolbarForced();
  }
  
//  public JWins getCotizacionesAFIP() throws Exception {
//	  GuiAfipCurrencyExchanges g = new GuiAfipCurrencyExchanges();
//	  g.setPreviewFlag(PREVIEW_NO);
//	  return g;
//  }
  
}
