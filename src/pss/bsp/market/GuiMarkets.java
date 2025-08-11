package  pss.bsp.market;

import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.winUI.lists.JWinList;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;

public class GuiMarkets extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiMarkets() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10032; } 
  public String  GetTitle()    throws Exception  { return "Familias Tarifarias "; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiMarket.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo mercado" );
  }


  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getId()==1) return BizUsuario.IsAdminCompanyUser();
  	return super.OkAction(a);
  }

  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	zLista.AddIcono("");
    zLista.AddColumnaLista("descripcion");
  }

}
