package pss.common.customAutoReports.config;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiReportFields extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiReportFields() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 90; } 
  public String  GetTitle()    throws Exception  { return "Campos"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiReportField.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo Campo" );
  }


  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("descr_campo");
    zLista.AddColumnaLista("screen_value");
  }
  

}
