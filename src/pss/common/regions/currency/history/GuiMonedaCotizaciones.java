package pss.common.regions.currency.history;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiMonedaCotizaciones extends JWins {
  /**
   * Constructor de la Clase
   */
  public GuiMonedaCotizaciones() throws Exception {
  }

  @Override
	public int     GetNroIcono() throws Exception  { return 54; }
  @Override
	public String  GetTitle()    throws Exception  { return "Histórico"; }
  @Override
	public Class<? extends JWin>   GetClassWin() { return GuiMonedaCotizacion.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  @Override
	public void createActionMap() throws Exception {
//    this.addActionNew( 1, "Nueva Cotización" );
  }
  
  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    if (zLista.isForExport()) {
	  	zLista.AddIcono("");
	    zLista.AddColumnaLista("fecha_hora");
	    zLista.AddColumnaLista("cotiz_compra").setTagHtml("b");
	    zLista.AddColumnaLista("cotiz_venta").setTagHtml("b");
	    zLista.AddColumnaLista("usuario");
    } else {
    	zLista.addColumnWinAction("Flat", 1);
    }
  }
  
  @Override
  protected void configureList(JWinList list) throws Exception {
  	list.setHideActions(true);;
  }

}
