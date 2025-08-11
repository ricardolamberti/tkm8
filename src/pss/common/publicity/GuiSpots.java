package pss.common.publicity;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiSpots extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiSpots() throws Exception {
  }


  @Override
	public int     GetNroIcono() throws Exception  { return 754; } 
  @Override
	public String  GetTitle()    throws Exception  { return "Spots publicitarios"; }
  @Override
	public Class<? extends JWin>   GetClassWin()                   { return GuiSpot.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  @Override
	public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo Registro" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    super.ConfigurarColumnasLista(zLista);
  }

}
