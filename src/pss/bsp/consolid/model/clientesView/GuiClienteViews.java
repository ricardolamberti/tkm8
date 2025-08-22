package pss.bsp.consolid.model.clientesView;

import pss.bsp.consolid.model.clientes.GuiClientes;
import pss.core.services.records.JRecords;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiClienteViews extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiClienteViews() throws Exception {
  }

	@Override
	public JRecords<BizClienteView> ObtenerDatos() {
		return new BizClienteViews();
	}


  
	public BizClienteViews getcRecords() throws Exception {
		return (BizClienteViews) this.getRecords();
	}
	
  public int     GetNroIcono() throws Exception  { return 10003; } 
  public String  GetTitle()    throws Exception  { return "Clientes Consolid"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiClienteView.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
 //   addActionNew( 1, "Nuevo Registro" );
    this.addAction(50, "Generar", null, 10045, true, true, true, "Group");
  }
  
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {

		if (a.getId() == 50)	return new JActSubmit(this) {
			public void submit() throws Exception {
				new GuiClientes().execGenerar();
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
    zLista.AddColumnaLista("email");
    zLista.AddColumnaLista("active");
    zLista.AddColumnaLista("I1","reporte1");
    zLista.AddColumnaLista("I2","reporte2");
    zLista.AddColumnaLista("I3","reporte3");
    zLista.AddColumnaLista("I4","reporte4");
    zLista.AddColumnaLista("I5","reporte5");
    zLista.AddColumnaLista("I6","reporte6");
    zLista.AddColumnaLista("I7","reporte7");
    zLista.AddColumnaLista("I8","reporte8");
        
  }
  
  @Override
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
   	zFiltros.addEditResponsive("customer number", "customer_number").setOperator("=");
   	zFiltros.addEditResponsive("customer name", "customer_name").setOperator("ilike");
    super.ConfigurarFiltros(zFiltros);
  }

}
