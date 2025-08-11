package  pss.common.diccionario;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiDiccionarios extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiDiccionarios() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10051; } 
  public String  GetTitle()    throws Exception  { return "Diccionarios"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiDiccionario.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nueva diccionario" );
  }
  
  



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	zLista.AddColumnaLista("descripcion");
   }

}
