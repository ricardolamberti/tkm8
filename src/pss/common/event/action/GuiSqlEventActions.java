package pss.common.event.action;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiSqlEventActions extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiSqlEventActions() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 15010; } 
  public String  GetTitle()    throws Exception  { return "Acciones"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiSqlEventAction.class; }
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
    zLista.AddColumnaLista("descr_action");
    zLista.AddColumnaLista("descr_data");
    zLista.AddColumnaLista("usuario");
    zLista.AddColumnaLista("correo");
    zLista.AddColumnaLista("telefono");
    zLista.AddColumnaLista("tipo_periodicidad");
    
  }

}
