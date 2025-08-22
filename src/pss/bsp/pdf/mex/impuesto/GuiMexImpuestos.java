package  pss.bsp.pdf.mex.impuesto;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiMexImpuestos extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiMexImpuestos() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10049; } 
  public String  GetTitle()    throws Exception  { return "Impuestos"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiMexImpuesto.class; }
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
    zLista.AddIcono("");
    zLista.AddColumnaLista("iso");
    zLista.AddColumnaLista("contado");
    zLista.AddColumnaLista("tarjeta");
  }

}
