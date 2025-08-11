package  pss.common.event.sql.serie;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiVirtualSeries extends JWins {

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiVirtualSeries() throws Exception {
  }

	@Override
	public int     GetNroIcono() throws Exception  { return 157; }
	@Override
	public String  GetTitle()    throws Exception  { return "Registros"; }
	@Override
	public Class<? extends JWin>   GetClassWin() { return GuiVirtualSerie.class; }
	
	@Override
	public void createActionMap() throws Exception {
	}
	
	@Override
	public void ConfigurarColumnasLista(JWinList zList) throws Exception {
		zList.AddIcono("");
		zList.AddColumnaLista("descripcion");
	}
	
}