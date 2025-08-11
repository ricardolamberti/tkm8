package pss.common.customLogin;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiCustomLogins extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiCustomLogins() throws Exception {
  }


  @Override
	public int     GetNroIcono() throws Exception  { return 755; } 
  @Override
	public String  GetTitle()    throws Exception  { return "Login Customizables"; }
  @Override
	public Class<? extends JWin>   GetClassWin()                   { return GuiCustomLogin.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  @Override
	public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo Registro" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddColumnaLista("description"); 
    zLista.AddColumnaLista("date_to"); 
    zLista.AddColumnaLista("date_from"); 
    zLista.AddColumnaLista("hour_to"); 
    zLista.AddColumnaLista("hour_from"); 
   }

}
