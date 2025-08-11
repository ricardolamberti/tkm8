package pss.common.restJason.apiUser.apiUserDetails;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiApiUserDetails extends JWins {



  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiApiUserDetails() throws Exception {
  }

  @Override
	public Class<? extends JWin> GetClassWin() { return GuiApiUserDetail.class; }
  @Override
	public int GetNroIcono() throws Exception { return 719; }
  @Override
	public String GetTitle() throws Exception { return "Usuarios de Api"; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
    addActionNew     ( 1, "Nueva Relación" );
  }


  //-------------------------------------------------------------------------//
  // Configuro las columnas que quiero mostrar en la grilla
  //-------------------------------------------------------------------------//
  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("json_path");

  }


}
