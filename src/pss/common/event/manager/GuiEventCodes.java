package pss.common.event.manager;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiEventCodes extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiEventCodes() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 948; } 
  public String  GetTitle()    throws Exception  { return "Eventos"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiEventCode.class; }
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
		zLista.AddIcono("");
	  zLista.AddColumnaLista("descr_modulo");
	  zLista.AddColumnaLista("descripcion");
  }

}
