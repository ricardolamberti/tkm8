package  pss.bsp.market.detalle;

import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.winUI.lists.JWinList;

public class GuiMarketsDetails extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiMarketsDetails() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10032; } 
  public String  GetTitle()    throws Exception  { return "Familia-aerolineas"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiMarketDetail.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nueva ruta" );
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
    zLista.AddColumnaLista("prioridad");
    zLista.AddColumnaLista("ruta");
  }

}
