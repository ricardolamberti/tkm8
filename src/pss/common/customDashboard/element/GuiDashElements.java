package pss.common.customDashboard.element;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiDashElements extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiDashElements() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 92; } 
  public String  GetTitle()    throws Exception  { return "Elementos"; }
  public Class<? extends JWin>  GetClassWin()    { return GuiDashElement.class; }
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
    zLista.AddIcono("");
    zLista.AddColumnaLista("description");
    zLista.AddColumnaLista("module");
  }

}
