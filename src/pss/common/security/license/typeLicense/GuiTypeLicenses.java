package  pss.common.security.license.typeLicense;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiTypeLicenses extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiTypeLicenses() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 912; } 
  public String  GetTitle()    throws Exception  { return "Tipos de licencia"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiTypeLicense.class; }
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
