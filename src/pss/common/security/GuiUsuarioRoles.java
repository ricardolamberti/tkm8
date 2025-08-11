package  pss.common.security;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiUsuarioRoles extends JWins {

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiUsuarioRoles() throws Exception {
  }

  @Override
	public Class<? extends JWin> GetClassWin() { return GuiUsuarioRol.class; }
  @Override
	public int GetNroIcono() throws Exception { return 43; }
  @Override
	public String GetTitle() throws Exception { return "Perfiles del usuario"; }

  @Override
	public void createActionMap() throws Exception {
  	if (this.GetVision().equals(BizUsuarioRol.VISION_ROL))
  		addActionNew(1, "Nuevo Rol" );
  }

  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    if( this.GetVision().equals(BizUsuarioRol.VISION_ROL))
    	zLista.AddColumnaLista("descr_rol" );
    else
    	zLista.AddColumnaLista("descr_usuario" );
  }
}
