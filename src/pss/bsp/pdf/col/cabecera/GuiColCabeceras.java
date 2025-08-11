package  pss.bsp.pdf.col.cabecera;

import pss.core.win.JWin;
import pss.core.winUI.lists.JWinList;
import pss.core.win.JWins;

public class GuiColCabeceras extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiColCabeceras() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10042; } 
  public String  GetTitle()    throws Exception  { return "Resultados Parseos"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiColCabecera.class; }
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
