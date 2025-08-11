package pss.www.platform.users.threads;

import pss.core.services.records.JBaseRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;


public class GuiThreads extends JWins {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiThreads() throws Exception {
  }
	public JBaseRecord ObtenerBaseDato() throws Exception {
		return new BizThreads();
	}
  @Override
	public Class<? extends JWin> GetClassWin() { return GuiThread.class; }
  @Override
	public int GetNroIcono() throws Exception { return 5601; }
  @Override
	public String GetTitle() throws Exception { 
	  return "Threads"; 
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
   }
  @Override
  public boolean isWebPageable() {
  	return false;
  }
  @Override
  public long selectSupraCount() throws Exception {
  	return -1;
  }
		
  @Override
  public void ConfigurarGraficos(JWinList oWinlist) throws Exception {
//  	Graph gr = new GraphScriptMultiChart();
//		gr.addAtributtes(Graph.GRAPH_ATTR_ROTATENAMES, "1");
//		gr.addAtributtes(Graph.GRAPH_ATTR_DECIMALPRECISON, "2");
//		gr.addAtributtes(Graph.GRAPH_ATTR_NUMBERPREFIX, "");
//		gr.addAtributtes(Graph.GRAPH_ATTR_EXPORTENABLED, "1");
//	
//		gr.setTitle("Request per user");
//		gr.getModel().addColumn("user", ModelMatrix.GRAPH_FUNCTION_CATEGORIE);
//		gr.getModel().addColumn("subsession", ModelMatrix.GRAPH_FUNCTION_DATASET);
//		gr.getModel().addColumn("req_count", ModelMatrix.GRAPH_FUNCTION_VALUE);
//		this.addFilterAdHoc("agrupado", "S");
//  	oWinlist.addGrafico(gr);
  	super.ConfigurarGraficos(oWinlist);
  }

  @Override
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	super.ConfigurarColumnasLista(zLista);
  }
}
