package  pss.common.backup.settings;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiBackUpGroups extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiBackUpGroups() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 941; } 
  public String  GetTitle()    throws Exception  { return "Grupos de BackUp"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiBackUpGroup.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo Registro" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    super.ConfigurarColumnasLista(zLista);
  }

}
