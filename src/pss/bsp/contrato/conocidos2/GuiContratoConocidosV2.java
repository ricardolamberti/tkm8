package pss.bsp.contrato.conocidos2;

import org.mozilla.javascript.EcmaError;

import pss.core.win.JWin;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;

public class GuiContratoConocidosV2 extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiContratoConocidosV2() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10003; } 
  public String  GetTitle()    throws Exception  { return "Contratos conocidos"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiContratoConocidoV2.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo Registro" );
    addActionNew( 10, "Auto cargar contratos" );
  }

  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==10) return new JActSubmit(this) {
  		@Override
  		public void submit() throws Exception {
  			runContratosConocidos();
  		}
  	};

  	return super.getSubmitFor(a);
  }


  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddColumnaLista("pais");
    zLista.AddColumnaLista("aerolineas");
    zLista.AddColumnaLista("descripcion");
    zLista.AddColumnaLista("periodo");
    
  }
  
  @Override
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
  	zFiltros.addEditResponsive("pais", "pais").setOperator("ilike");
  	zFiltros.addEditResponsive("Aerolinea", "aerolineas").setOperator("ilike");
  	zFiltros.addEditResponsive("descripcion", "descripcion").setOperator("ilike");
  	super.ConfigurarFiltros(zFiltros);
  }

  public void runContratosConocidos() throws Exception {
			GuiContratoConocidoV2 a = new GuiContratoConocidoV2();
			a.GetcDato().execFillContratosConocidos();

  }
}
