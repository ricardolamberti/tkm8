package  pss.bsp.monitor.log;

import pss.common.regions.company.GuiCompanies;
import pss.core.win.GuiVirtuals;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiBspLogs extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiBspLogs() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10032; } 
  public String  GetTitle()    throws Exception  { return "Bsp logs "; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiBspLog.class; }
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

  @Override
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
  	zFiltros.addComboResponsive("Company", "company", new GuiCompanies() );
  	zFiltros.addComboResponsive("Type", "log_type", new JControlCombo() {
  		@Override
  		public JWins getRecords(Object zSource, boolean zOneRow) throws Exception {
  			return getTipos();
  		}
  	}, true);  
  	zFiltros.addComboResponsive("Thread", "log_thread", new JControlCombo() {
  		@Override
  		public JWins getRecords(Object zSource, boolean zOneRow) throws Exception {
  			return getHilos();
  		}
  	}, true); 
  	
  	super.ConfigurarFiltros(zFiltros);
  }
  
	protected JWins getTipos() throws Exception {
		return JWins.createVirtualWinsFromMap(BizBspLog.getTipos());
	}	
	protected GuiVirtuals getHilos() throws Exception {
		GuiVirtuals hilos = new GuiVirtuals();
	 	String sSql ="SELECT log_thread as descripcion,log_thread as valor, 1 as icono, '' as icono_string  FROM BSP_LOG group by log_thread ";
	  hilos.getRecords().SetSQL(sSql);
	  return hilos;
	}	
}
