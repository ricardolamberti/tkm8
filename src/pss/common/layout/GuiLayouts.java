package pss.common.layout;

import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiLayouts extends JWins {


  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiLayouts() throws Exception {
  }

  @Override
	public int     GetNroIcono() throws Exception  { return 204; }
  @Override
	public String  GetTitle()    throws Exception  { return "Layouts"; }
  @Override
	public Class<GuiLayout>   GetClassWin()                   { return GuiLayout.class; }
  
  @Override
	public void createActionMap() throws Exception {
    addActionNew     ( 1, "Nuevo Registro" );
  }

  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("layout");
    zLista.AddColumnaLista("descripcion").setActionOnClick(1);
  }


}
