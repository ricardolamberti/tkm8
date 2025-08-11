package  pss.common.regions.nodes;

import pss.core.win.JWin;
import pss.core.win.JWins;

public class GuiRegionNodos extends JWins {

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiRegionNodos() throws Exception {
  }

  @Override
	public int     GetNroIcono() throws Exception  { return 90; }
  @Override
	public String  GetTitle()    throws Exception  { return "Region Nodos"; }
  @Override
	public Class<? extends JWin>   GetClassWin()                   { return GuiRegionNodo.class; }

  @Override
	public void createActionMap() throws Exception {
    addActionNew     ( 1, "Nuevo Registro" );
  }
}
