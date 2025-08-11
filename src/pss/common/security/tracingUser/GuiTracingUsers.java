package  pss.common.security.tracingUser;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiTracingUsers extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiTracingUsers() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10051; } 
  public String  GetTitle()    throws Exception  { return "Seguimiento de usuarios"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiTracingUser.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
//    addActionNew( 1, "Nuevo" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	zLista.AddColumnaLista("email");
  	zLista.AddColumnaLista("empresa");
  	zLista.AddColumnaLista("nombre");
  	zLista.AddColumnaLista("date_register");
  	zLista.AddColumnaLista("date_backregister");
  	zLista.AddColumnaLista("date_login");
  	zLista.AddColumnaLista("send_mail");
  	zLista.AddColumnaLista("clave");
  	zLista.AddColumnaLista("problems");
   	zLista.AddColumnaLista("intentos");
    
  }
}
