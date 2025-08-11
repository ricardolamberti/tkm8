package pss.common.customLogin;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiCustomLoginComponents extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiCustomLoginComponents() throws Exception {
  }


  @Override
	public int     GetNroIcono() throws Exception  { return 754; } 
  @Override
	public String  GetTitle()    throws Exception  { return "Componente"; }
  @Override
	public Class<? extends JWin>   GetClassWin()                   { return GuiCustomLoginComponent.class; }
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
  	zLista.AddColumnaLista("texto");
  }

}
