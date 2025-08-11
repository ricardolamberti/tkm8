package  pss.common.todolist;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiToDoLists extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiToDoLists() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10051; } 
  public String  GetTitle()    throws Exception  { return "Tareas pendientes"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiToDoList.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nueva tarea" );
  }
  
  



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	zLista.AddColumnaLista("description");
  	zLista.AddColumnaLista("status").setActionOnClick(100);
   }
  
  @Override
  public int getWebPageSize() {
  	return -1;
  }

  

}
