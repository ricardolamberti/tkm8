package pss.www.platform.users.online;

import pss.common.security.BizUsuario;
import pss.common.security.type.GuiUsuarioTipos;
import pss.core.graph.Graph;
import pss.core.graph.implementations.GraphScriptMultiChart;
import pss.core.graph.model.ModelMatrix;
import pss.core.services.records.JBaseRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;


public class GuiOnlineUsers extends JWins {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiOnlineUsers() throws Exception {
  }
	public JBaseRecord ObtenerBaseDato() throws Exception {
		return new BizOnlineUsers();
	}
  @Override
	public Class<? extends JWin> GetClassWin() { return GuiOnlineUser.class; }
  @Override
	public int GetNroIcono() throws Exception { return 745; }
  @Override
	public String GetTitle() throws Exception { 
	  return "Usuarios Online"; 
	}


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
  }

  @Override
  public boolean canConvertToURL() throws Exception {
  	return false;
  }
  
  public void ConfigurarFiltros(JFormFiltro filter) throws Exception {
  	if (BizUsuario.hasUserType())
  		filter.addComboResponsive("Tipo usuario", "tipo_usuario", new GuiUsuarioTipos(), true);
		filter.addCheckResponsive("Agrupado", "agrupado").SetValorDefault(true).setRefreshForm(true);
  }
  
  @Override
  public boolean isWebPageable() {
  	return false;
  }

  @Override
  public void ConfigurarGraficos(JWinList oWinlist) throws Exception {
  	Graph gr = new GraphScriptMultiChart();
		gr.addAtributtes(Graph.GRAPH_ATTR_ROTATENAMES, "1");
		gr.addAtributtes(Graph.GRAPH_ATTR_DECIMALPRECISON, "2");
		gr.addAtributtes(Graph.GRAPH_ATTR_NUMBERPREFIX, "");
		gr.addAtributtes(Graph.GRAPH_ATTR_EXPORTENABLED, "1");
	
		gr.setTitle("Request per user");
		gr.getModel().addColumn("user", ModelMatrix.GRAPH_FUNCTION_CATEGORIE);
		gr.getModel().addColumn("subsession", ModelMatrix.GRAPH_FUNCTION_DATASET);
		gr.getModel().addColumn("req_count", ModelMatrix.GRAPH_FUNCTION_VALUE);
		this.addFilterAdHoc("agrupado", "S");
  	oWinlist.addGrafico(gr);
  	super.ConfigurarGraficos(oWinlist);
  }

  @Override
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddColumnaLista("user");
    zLista.AddColumnaLista("user_description");
    zLista.AddColumnaLista("persistent");
    zLista.AddColumnaLista("cached");
    zLista.AddColumnaLista("timeout");
    zLista.AddColumnaLista("ip");
    if (getFilterValue("Agrupado")==null || getFilterValue("Agrupado").equals("N"))
    	zLista.AddColumnaLista("subsession");
    zLista.AddColumnaLista("last_reqtime");
    zLista.AddColumnaLista("idle_time");
    zLista.AddColumnaLista("req_count");
    zLista.AddColumnaLista("req_obj");
    zLista.AddColumnaLista("req_his");
    zLista.AddColumnaLista("req_promedio");
    if (BizUsuario.hasUserType())
    	zLista.AddColumnaLista("tipo_usuario");
    zLista.AddColumnaLista("app");
    zLista.AddColumnaLista("req_db");
    zLista.AddColumnaLista("req_dbfree");
    zLista.AddColumnaLista("base");
//    zLista.AddColumnaLista("memory");
//    zLista.AddColumnaLista("agrupado");
  }
}
