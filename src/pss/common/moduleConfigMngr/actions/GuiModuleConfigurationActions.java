package  pss.common.moduleConfigMngr.actions;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiModuleConfigurationActions extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiModuleConfigurationActions() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 410; } 
  public String  GetTitle()    throws Exception  { return "Acciones Modulo Configuracion"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiModuleConfigurationAction.class; }
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
