package  pss.common.regions.nodes;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiNodoDatabases extends JWins {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiNodoDatabases() throws Exception {
  }

  @Override
	public int     GetNroIcono() throws Exception  { return 77; }
  @Override
	public String  GetTitle()    throws Exception  { return "Databases"; }
  @Override
	public Class<? extends JWin>   GetClassWin()                   { return GuiNodoDatabase.class; }

  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
    addActionNew     ( 1, "Nuevo Registro");
  }





  //-------------------------------------------------------------------------//
  // Configuro las columnas que quiero mostrar en la grilla
  //-------------------------------------------------------------------------//
  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("host_db");
    zLista.AddColumnaLista("seccion_ini");
    zLista.AddColumnaLista("is_master");
    zLista.AddColumnaLista("conectado");
    zLista.AddColumnaLista("is_base_local");
  }

}
