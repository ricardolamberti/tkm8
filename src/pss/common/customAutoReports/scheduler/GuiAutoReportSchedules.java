package pss.common.customAutoReports.scheduler;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiAutoReportSchedules extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiAutoReportSchedules() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 941; } 
  public String  GetTitle()    throws Exception  { return "Configuraciones de BackUp"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiAutoReportSchedule.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo Registro" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	zLista.AddIcono("");
  	zLista.AddColumnaLista("descr_tipo");
  	zLista.AddColumnaLista("descr_subtipo");  	
  	zLista.AddColumnaLista("time_to_run");
  	zLista.AddColumnaLista("last_run");
  	zLista.AddColumnaLista("active");
    getRecords().clearOrderBy();
  }

}
