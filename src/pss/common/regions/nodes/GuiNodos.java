package  pss.common.regions.nodes;

import pss.core.connectivity.messageManager.common.core.JMMWins;
import pss.core.services.fields.JString;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiNodos extends JMMWins {


  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiNodos() throws Exception {
    getRecords().addOrderBy("nodo");
  }

  @Override
	public int     GetNroIcono() throws Exception  { return 10072; }
  @Override
	public String  GetTitle()    throws Exception  { return "Sucursales"; }
  @Override
	public Class<? extends JWin>   GetClassWin()                   { return GuiNodo.class; }



  @Override
	public void createActionMap() throws Exception {
    addActionNew     ( 1, "Nuevo Registro");
  }

  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
     zLista.AddIcono("");
//     zLista.AddColumnaLista("nodo");
     zLista.AddColumnaLista("descripcion");
//     zLista.AddColumnaLista("local");
  }
/*
  public int GetNroIcono() throws Exception{
    return 33;
  }
*/
//  public static JWins ObtenerNodosPaisLocal() throws Exception {
//    GuiNodos oNodos = new GuiNodos();
//    oNodos.getRecords().addFilter("pais",BizUsuario.getUsr().getCountry());
//    return oNodos;
//  }

  public static JWins ObtenerNodos( JString zClavePais, JString zClaveProvincia ) throws Exception { return ObtenerNodos( zClavePais.toString(), zClaveProvincia.toString() ); }
  public static JWins ObtenerNodos( String zClavePais, String zClaveProvincia ) throws Exception {
    GuiNodos oNodos = new GuiNodos();
    oNodos.getRecords().addFilter( "pais", zClavePais );
    oNodos.getRecords().addFilter( "provincia", zClaveProvincia );
    return oNodos;
  }

  // Obtener wins de nodos por [pais] [provincia] concentrador/no concetrador
  public static JWins ObtenerNodos( String zClavePais, String zClaveProvincia, boolean zConcentrador ) throws Exception {
    GuiNodos oNodos = new GuiNodos();

    if( zClavePais != null ) oNodos.getRecords().addFilter( "pais"        , zClavePais );
    if( zClaveProvincia != null ) oNodos.getRecords().addFilter( "provincia"   , zClaveProvincia );

    if( zConcentrador ) oNodos.getRecords().addFilter( "concentrador", "S" );
    else                oNodos.getRecords().addFilter( "concentrador", "N" );

    return oNodos;
  }

}
