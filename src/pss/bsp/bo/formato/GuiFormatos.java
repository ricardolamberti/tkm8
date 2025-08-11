package  pss.bsp.bo.formato;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiFormatos extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiFormatos() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10023; } 
  public String  GetTitle()    throws Exception  { return "Formatos Interfaz"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiFormato.class; }
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
