package pss.bsp.consolid.model.clientes;

import pss.common.regions.company.BizCompany;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiClientes extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiClientes() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10003; } 
  public String  GetTitle()    throws Exception  { return "Clientes Consolid"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiCliente.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo Registro" );
    this.addAction(50, "Generar", null, 10045, true, true, true, "Group");
  }
  
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {

		if (a.getId() == 50)	return new JActSubmit(this) {
			public void submit() throws Exception {
				execGenerar();
			}  
		};	
		return super.getSubmitFor(a);
	}
  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddColumnaLista("customer_number");
    zLista.AddColumnaLista("customer_name");
    zLista.AddColumnaLista("has_company");
    
  }
  
  @Override
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
   	zFiltros.addEditResponsive("customer number", "customer_number").setOperator("=");
   	zFiltros.addEditResponsive("customer name", "customer_name").setOperator("ilike");
     	super.ConfigurarFiltros(zFiltros);
  }

  public void execGenerar() throws Exception {
  	JRecords<BizCliente> recs = new JRecords<BizCliente>(BizCliente.class);
  	JIterator<BizCliente> it = recs.getStaticIterator();
  	
   	while (it.hasMoreElements()) {
  		BizCliente cliente = it.nextElement();
  		if (cliente.getStatus().equals("I")) continue;
 			cliente.execActualizar(BizUsuario.getUsr().getCompany());
   	}
  	it = recs.getStaticIterator();
    	
  	while (it.hasMoreElements()) {
  		BizCliente cliente = it.nextElement();
  		if (cliente.getStatus().equals("I")) continue;
//  		BizLiqAcum acum = new BizLiqAcum();
//  		acum.dontThrowException(true);
//  		acum.addFilter("dk", cliente.getCustomerNumber());
//  		if (!acum.exists()) continue;
  		BizCompany comp = new BizCompany();
  		comp.dontThrowException(true);
  		if (comp.Read("C_"+cliente.getCustomerNumber())) {
   			continue;
  		}
 		  cliente.execGenerar();
   	}
  }
}
