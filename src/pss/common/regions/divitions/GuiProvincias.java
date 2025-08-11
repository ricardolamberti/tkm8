package  pss.common.regions.divitions;

import pss.core.services.fields.JString;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiProvincias extends JWins {

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiProvincias() throws Exception {
  }

  @Override
	public Class<? extends JWin> GetClassWin() { return GuiProvincia.class; }
  @Override
	public int GetNroIcono() throws Exception { return 73; }
  @Override
	public String GetTitle() throws Exception { return "Divisiones"; }

  @Override
	public void createActionMap() throws Exception {
    addActionNew     ( 1, "Nuevo Registro" );
  }

  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
     zLista.AddIcono("");
     zLista.AddColumnaLista("descripcion");
//     zLista.SetColumnaBusqueda("descripcion");
     this.getRecords().addOrderBy("descripcion");
  }
  

  public static JWins ObtenerProvincias( JString zClavePais ) throws Exception { return ObtenerProvincias( zClavePais.toString() ); }
  public static JWins ObtenerProvincias( String zClavePais ) throws Exception {
    GuiProvincias oProvincias = new GuiProvincias();
    oProvincias.getRecords().addFilter( "pais", zClavePais );
    return oProvincias;
  }
}
