package  pss.common.customList.config.field.operadores;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiOperadores extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiOperadores() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10064; } 
  public String  GetTitle()    throws Exception  { return "Operaciones"; }
  public Class<? extends JWin>  GetClassWin()    { return GuiOperador.class; }
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
