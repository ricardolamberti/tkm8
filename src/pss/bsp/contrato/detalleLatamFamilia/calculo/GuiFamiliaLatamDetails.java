package pss.bsp.contrato.detalleLatamFamilia.calculo;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiFamiliaLatamDetails extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiFamiliaLatamDetails() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10032; } 
  public String  GetTitle()    throws Exception  { return "Familia-Latam"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiFamiliaLatamDetail.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nueva Aerolínea" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	zLista.AddIcono("");
    zLista.AddColumnaLista("familia");
    zLista.AddColumnaLista("peso");
    zLista.AddColumnaLista("proporcion");
    zLista.AddColumnaLista("ponderado");
  }

}
