package pss.bsp.consolid.model.liquidacion.header;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiLiqHeaders extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiLiqHeaders() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10003; } 
  public String  GetTitle()    throws Exception  { return "Liquidaciones"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiLiqHeader.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
  	if (!BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) 
  	    addActionNew( 1, "Nuevo Registro" );
  }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
  		   
	
  	return super.OkAction(a);
  }


  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddColumnaLista("descripcion");
    zLista.AddColumnaLista("estado");
  }
  
  @Override
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
     	super.ConfigurarFiltros(zFiltros);
  }

}
