package  pss.common.customList.config.dataBiz;

import pss.core.services.records.JRecords;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiDataBizs extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiDataBizs() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10064; } 
  public String  GetTitle()    throws Exception  { return "Datos"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiDataBiz.class; }
  
	@Override
	public JRecords<? extends BizDataBiz> ObtenerDatos() throws Exception {
		return new BizDataBizs();
	}
	
	public BizDataBizs getcRecords() throws Exception {
		return (BizDataBizs) this.getRecords();
	}
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
  }


  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("descripcion");
    zLista.AddColumnaLista("valor");
  }
  
  @Override
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
  	zFiltros.addEditResponsive("Busqueda", "descripcion");
  	super.ConfigurarFiltros(zFiltros);
  }

}
