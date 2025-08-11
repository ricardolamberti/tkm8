package pss.common.version.storeAndForward;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiStoreForwardsHeader extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiStoreForwardsHeader() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10039; } 
  public String  GetTitle()    throws Exception  { return "Envios a HO"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiStoreForwardHeader.class; }
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
