package pss.common.regions.currency;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiMonedas extends JWins {

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiMonedas() throws Exception {
  }

  @Override
	public int     GetNroIcono() throws Exception  { return 5041; }
  @Override
	public String  GetTitle()    throws Exception  { return "Monedas"; }
  @Override
	public Class<? extends JWin>   GetClassWin()                   { return GuiMoneda.class; }

  @Override
	public void createActionMap() throws Exception {
    addActionNew     ( 1, "Nueva Moneda" );
  }

  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("codigo");
    zLista.AddColumnaLista("descripcion");
    zLista.AddColumnaLista("simbolo");
  }


}
