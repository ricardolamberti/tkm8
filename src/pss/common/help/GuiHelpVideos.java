package pss.common.help;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiHelpVideos extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiHelpVideos() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 5606; } 
  public String  GetTitle()    throws Exception  { return "Videos de Ayuda"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiHelpVideo.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
     // addActionNew( 1, "Nuevo Registro" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    	zLista.AddIcono("");
    	zLista.AddColumnaLista("DESCRIPTION");
  }

}
