package pss.core.win;

import pss.core.winUI.lists.JWinList;


public class GuiVirtuals extends JWins {

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiVirtuals() throws Exception {
  }

	@Override
	public int     GetNroIcono() throws Exception  { return 157; }
	@Override
	public String  GetTitle()    throws Exception  { return "Registros"; }
	@Override
	public Class<? extends JWin>   GetClassWin() { return GuiVirtual.class; }
	
	@Override
	public void createActionMap() throws Exception {
	}
	
	@Override
	public void ConfigurarColumnasLista(JWinList zList) throws Exception {
		zList.AddIcono("");
		zList.AddColumnaLista("descripcion");
	}
	
}