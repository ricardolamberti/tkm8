package  pss.bsp.pdf.mex.cabecera;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiMexCabeceras extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiMexCabeceras() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10042; } 
  public String  GetTitle()    throws Exception  { return "Resultados Parseos"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiMexCabecera.class; }
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
