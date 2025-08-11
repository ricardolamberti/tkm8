package  pss.common.pki.ejbca;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiPKIConfiguraciones extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiPKIConfiguraciones() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10046; } 
  public String  GetTitle()    throws Exception  { return "Configurador validador"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiPKIConfiguracion.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
   // addActionNew( 1, "Nuevo Registro" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    super.ConfigurarColumnasLista(zLista);
  }

}
