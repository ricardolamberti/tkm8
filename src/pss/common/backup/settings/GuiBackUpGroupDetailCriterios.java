package  pss.common.backup.settings;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiBackUpGroupDetailCriterios extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiBackUpGroupDetailCriterios() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10052; } 
  public String  GetTitle()    throws Exception  { return "Criterios"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiBackUpGroupDetailCriterio.class; }
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
