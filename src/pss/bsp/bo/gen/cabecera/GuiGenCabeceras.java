package  pss.bsp.bo.gen.cabecera;

import pss.core.win.JWin;
import pss.core.winUI.lists.JWinList;
import pss.core.win.JWins;

public class GuiGenCabeceras extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiGenCabeceras() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10049; } 
  public String  GetTitle()    throws Exception  { return "Interfaces Backoffice"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiGenCabecera.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
 //   addActionNew( 1, "Nuevo Registro" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    super.ConfigurarColumnasLista(zLista);
  }

}
