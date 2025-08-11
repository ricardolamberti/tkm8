package  pss.common.regions.nodes;

import pss.core.win.JWin;
import pss.core.win.JWins;

public class GuiRegiones extends JWins {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiRegiones() throws Exception {
  }

  @Override
	public int     GetNroIcono() throws Exception  { return 76; }
  @Override
	public String  GetTitle()    throws Exception  { return "Regiones"; }
  @Override
	public Class<? extends JWin>   GetClassWin()                   { return GuiRegion.class; }


  @Override
	public void createActionMap() throws Exception {
    addActionNew     ( 1, "Nuevo Registro" );
  }
}
