package pss.common.restJason.apiUser;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiApiUsers extends JWins {



  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiApiUsers() throws Exception {
  }

  @Override
	public Class<? extends JWin> GetClassWin() { return GuiApiUser.class; }
  @Override
	public int GetNroIcono() throws Exception { return 719; }
  @Override
	public String GetTitle() throws Exception { return "Usuarios de Api"; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
    addActionNew     ( 1, "Nueva Usuario" );
  }


  //-------------------------------------------------------------------------//
  // Configuro las columnas que quiero mostrar en la grilla
  //-------------------------------------------------------------------------//
  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("descr_usuario");
    zLista.AddColumnaLista("active");
    zLista.AddColumnaLista("user_active");
    zLista.AddColumnaLista("token_duedate");
  }


}
