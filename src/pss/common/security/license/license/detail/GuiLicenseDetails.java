package  pss.common.security.license.license.detail;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiLicenseDetails extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiLicenseDetails() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 5050; } 
  public String  GetTitle()    throws Exception  { return "Detalles Licencias"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiLicenseDetail.class; }
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
