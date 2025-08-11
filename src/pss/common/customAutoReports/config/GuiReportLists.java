package pss.common.customAutoReports.config;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiReportLists extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiReportLists() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10027; } 
  public String  GetTitle()    throws Exception  { return "Reportes Automáticos"; }
  public Class<? extends JWin>  GetClassWin()    { return GuiReportList.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo Reporte" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("report_desc");
    zLista.AddColumnaLista("report_fantasy_name");
    zLista.AddColumnaLista("report_type_desc");
  }

}
