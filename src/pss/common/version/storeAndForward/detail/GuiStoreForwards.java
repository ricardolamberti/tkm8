package pss.common.version.storeAndForward.detail;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiStoreForwards extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiStoreForwards() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10039; } 
  public String  GetTitle()    throws Exception  { return "Envios a HO"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiStoreForward.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
 //   addActionNew( 1, "Nuevo Registro" );
  }


 
  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    super.ConfigurarColumnasLista(zLista);
  }

}
