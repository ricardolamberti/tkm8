package  pss.common.pki.users;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiUsuarioFirmas  extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiUsuarioFirmas() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10040; } 
  public String  GetTitle()    throws Exception  { return "Firmas Digitales"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiUsuarioFirma.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
//    addActionNew( 1, "Nuevo Registro" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    super.ConfigurarColumnasLista(zLista);
  }

}
