package  pss.common.customList.config.field.funciones;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiFunciones extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiFunciones() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10064; } 
  public String  GetTitle()    throws Exception  { return "Funciones"; }
  public Class<? extends JWin>  GetClassWin()    { return GuiFuncion.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
  }


  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("descripcion");
  }
  

}
