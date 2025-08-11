package pss.common.regions.currency;

import pss.common.regions.currency.conversion.GuiMonedaConvers;
import pss.common.security.BizUsuario;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActWins;

public class GuiModuloMoneda extends GuiModulo {

  public GuiModuloMoneda() throws Exception {
    super();
    SetModuleName( "Monedas" );
    SetNroIcono  ( 5041 );
  }

  @Override
	public BizAction createDynamicAction() throws Exception {
    return addAction(1, "Monedas", null, 5041 , true, true, true, "Group" );
  }

  @Override
	public void createActionMap() throws Exception {
    addAction(10, "Lista Monedas", null, 5041 , true, false, true);
    addAction(12, "Monedas", null, 5041 , true, false, true);
    addAction(15, "Conversiones", null, 6021, true, false, true);
  }
  
  @Override
	public JAct getSubmitFor(BizAction a) throws Exception {
  	if ( a.getId() == 1 ) return new JActQuery(this);
  	if ( a.getId() == 10 ) return new JActWins(this.getMonedas()); 
  	if ( a.getId() == 12 ) return new JActWins(this.getMonedasCompany());
  	if ( a.getId() == 15 ) return new JActWins(this.getMonedasConvers());
    return null;
  }    

  @Override
	public boolean isModuleComponent() throws Exception { return false; }

  
  public JWins getMonedas() throws Exception {
  	return new GuiMonedas();
  }
  
  public JWins getMonedasCompany() throws Exception {
  	GuiMonedaPaises g = new GuiMonedaPaises();
  	g.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
  	g.getRecords().addFilter("pais", BizUsuario.getUsr().getCountry());
  	g.getRecords().addFilter("moneda", BizUsuario.getUsr().findLocalCurrency(), "<>");
  	return g;
  }
  
  public JWins getMonedasConvers() throws Exception {
  	GuiMonedaConvers g = new GuiMonedaConvers();
  	g.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
  	g.getRecords().addFilter("pais", BizUsuario.getUsr().getCountry());
  	g.getRecords().addOrderBy("moneda_source", "asc");
  	g.getRecords().addOrderBy("moneda_target", "desc");
  	return g;
  }
  
}
