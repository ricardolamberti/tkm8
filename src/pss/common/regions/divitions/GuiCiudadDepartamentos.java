package  pss.common.regions.divitions;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiCiudadDepartamentos extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiCiudadDepartamentos() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 15012; } 
  public String  GetTitle()    throws Exception  { return "CiudadDepartamentos"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiCiudadDepartamento.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
  //  addActionNew( 1, "Nuevo Registro" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    super.ConfigurarColumnasLista(zLista);
  }

}
