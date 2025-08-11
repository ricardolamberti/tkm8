package  pss.common.security.license.license;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiLicenses extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiLicenses() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 5051; } 
  public String  GetTitle()    throws Exception  { return "Licencias"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiLicense.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo Licencia" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    super.ConfigurarColumnasLista(zLista);
  }

}
