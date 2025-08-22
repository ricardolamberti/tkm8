package  pss.bsp.pdf.gua.impuesto;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiGuaImpuestos extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiGuaImpuestos() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10049; } 
  public String  GetTitle()    throws Exception  { return "Impuestos"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiGuaImpuesto.class; }
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
