package  pss.common.publicity;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiCampaigns extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiCampaigns() throws Exception {
  }


  @Override
	public int     GetNroIcono() throws Exception  { return 755; } 
  @Override
	public String  GetTitle()    throws Exception  { return "Campañas publicitarias"; }
  @Override
	public Class<? extends JWin>   GetClassWin()                   { return GuiCampaign.class; }
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
