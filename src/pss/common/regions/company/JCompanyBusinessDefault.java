package pss.common.regions.company;

import pss.common.regions.nodes.GuiNodo;
import pss.common.security.BizUsuario;
import pss.common.security.GuiUsuario;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.win.JWin;
import pss.core.win.JWins;

public class JCompanyBusinessDefault extends JCompanyBusiness {

	// Classes
	
  @Override
	public GuiNewCompany getNewWinInstance() throws Exception {return new GuiNewCompany();}
  @Override
	public GuiCompany getWinInstance() throws Exception {return new GuiCompany();}
  @Override
	public BizCompany getInstance() throws Exception {return new BizCompany();}
  @Override
	public JWin getSpecialNode() throws Exception {return new GuiNodo();}
  @Override
	public BizUsuario getUserInstance() throws Exception {return new BizUsuario();}
	public GuiUsuario getUserWinInstance() throws Exception {return new GuiUsuario();}

  // Labels
  
  @Override
	public String getLabelArticles() throws Exception {return "Productos";}
  @Override
	public String getLabelArticle() throws Exception {return "Producto";}
  @Override
	public String getLabelSubCliente() throws Exception {return "SubCuenta";}
  @Override
	public String getLabelSubClientes() throws Exception {return "SubCuentas";}
  @Override
	public String getLabelRegionOrigen() throws Exception {return "Ciudad de Origen";}
  @Override
	public String getLabelRegionOrigenPlural() throws Exception {return "Ciudades de Origen";}
//  @Override
//	public String getLabelRegionOrigenFiltro() throws Exception {return "Ciudad";}
  @Override
	public String getLabelReporteTerminal() throws Exception {return "Vendedor";}
  @Override
	public String getLabelNodo() throws Exception {return "Sucursal";}

  // Defaults
  @Override
	public boolean getDefaultCuentaContado() {return true;}
  
	public JMap<String, String> getEventCodes() throws Exception {
		JMap<String, String> map = JCollectionFactory.createOrderedMap();
		return map;
	}

  @Override
	public boolean isValidOperation(String zOperation) throws Exception {
  	return false;
  }
  
  @Override
	public boolean isValidOperationForCustomers(String zOperation) throws Exception {
  	return false;
  }
	@Override
	public void createBusinessHomePages() throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public JList<String> getProductTypes() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
  
	public boolean isUseTurnos() {
		return true;
	}


  public JWins getBusinessModules() throws Exception {
  	return null;
  }
@Override
public String getAfterLoginChangeHomePage() throws Exception {
	// TODO Auto-generated method stub
	return null;
} 
  
}
