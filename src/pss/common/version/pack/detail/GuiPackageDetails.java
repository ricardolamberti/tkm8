package pss.common.version.pack.detail;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiPackageDetails extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiPackageDetails() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10038; } 
  public String  GetTitle()    throws Exception  { return "Detalles Paquete"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiPackageDetail.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
//    addActionNew( 1, "Nuevo Registro" );
  }
 


  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    super.ConfigurarColumnasLista(zLista);
  }

}
