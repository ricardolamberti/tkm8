package pss.common.paymentManagement;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiPenalizeDebts extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiPenalizeDebts() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 92; } 
  public String  GetTitle()    throws Exception  { return "Penalidades de deuda"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiPenalizeDebt.class; }
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
    	zLista.AddIcono("");
    	zLista.AddColumnaLista("COMPANY");
    	zLista.AddColumnaLista("PENALIZE_TIME");
  }

}
