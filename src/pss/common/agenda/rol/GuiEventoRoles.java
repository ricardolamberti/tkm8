package  pss.common.agenda.rol;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiEventoRoles extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiEventoRoles() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 15017; } 
  public String  GetTitle()    throws Exception  { return "roles"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiEventoRol.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo Rol" );
  }


//	protected boolean checkActionOnDrop(BizAction a) throws Exception {
//		return true;
//	}
  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	zLista.AddColumnaLista("descripcion");
  }

}
