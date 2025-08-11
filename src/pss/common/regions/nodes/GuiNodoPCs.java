package  pss.common.regions.nodes;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiNodoPCs extends JWins {


  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiNodoPCs() throws Exception {
  }

  @Override
	public int     GetNroIcono() throws Exception  { return 10063; }
  @Override
	public String  GetTitle()    throws Exception  { return "PCs"; }
  @Override
	public Class<? extends JWin>   GetClassWin()                   { return GuiNodoPC.class; }

  @Override
	public void createActionMap() throws Exception {
    addActionNew     ( 1, "Nuevo Registro" );
  }

  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("host");
    zLista.AddColumnaLista("Pss_path");
    zLista.AddColumnaLista("is_master");
  }


}
