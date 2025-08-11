package  pss.common.moduleConfigMngr.transactions;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiModuleConfigTransactions extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiModuleConfigTransactions() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 924; } 
  public String  GetTitle()    throws Exception  { return "Transacciones Configuracion Modulo"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiModuleConfigTransaction.class; }
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
