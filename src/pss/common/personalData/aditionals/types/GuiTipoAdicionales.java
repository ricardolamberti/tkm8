package pss.common.personalData.aditionals.types;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiTipoAdicionales extends JWins {



  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiTipoAdicionales() throws Exception {
  }

  @Override
	public Class<? extends JWin> GetClassWin() { return GuiTipoAdicional.class; }
  @Override
	public String GetTitle() { return "Adicional"; }
  @Override
	public int GetNroIcono() { return 10023; }


  @Override
	public void createActionMap() throws Exception {
  	addActionNew     ( 1, "Nuevo Tipo" );
  }
  
  
  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("tipo");
    zLista.AddColumnaLista("descripcion");
  }
  
  
}
