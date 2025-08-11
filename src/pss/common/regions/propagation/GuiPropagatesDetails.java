package  pss.common.regions.propagation;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiPropagatesDetails extends JWins {


  /**
   * Constructor de la Clase
   */
  public GuiPropagatesDetails() throws Exception {
  }


  @Override
	public int     GetNroIcono() throws Exception  { return 10116; }
  @Override
	public String  GetTitle()    throws Exception  { return "Detalles de la propagación"; }
  @Override
	public Class<? extends JWin>   GetClassWin()                   { return GuiPropagatesDetail.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  @Override
	public void createActionMap() throws Exception {
    addActionNew     ( 1, "Nuevo Registro" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    super.ConfigurarColumnasLista(zLista);
  }

}
