package pss.core.tools.virtualGraph;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiVirtualGraphs extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiVirtualGraphs() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 15011; } 
  public String  GetTitle()    throws Exception  { return "Objetivos VirtualGraph"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiVirtualGraph.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {

  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    	zLista.AddColumnaLista("agrupador1").setVisible(false);
    	zLista.AddColumnaLista("agrupador2");
			zLista.AddColumnaLista("agrupador3").setVisible(false);
			zLista.AddColumnaLista("agrupador4").setVisible(false);
  		zLista.AddColumnaLista("cantidad1");
  		zLista.AddColumnaLista("cantidad2");
  		zLista.AddColumnaLista("cantidad3");
  		zLista.AddColumnaLista("cantidad4");
  		zLista.AddColumnaLista("cantidad5");
 
//    zLista.AddColumnaLista("porcentaje");
    
  }
  
  @Override
  public long selectSupraCount() throws Exception {
  	return -1;
  }
		

	@Override
	public void ConfigurarGraficos(JWinList oWinlist) throws Exception {
//  	Graph gr = new GraphScriptPie();
//  	gr.addAtributtes(Graph.GRAPH_ATTR_ROTATENAMES, "1");
//		gr.addAtributtes(Graph.GRAPH_ATTR_DECIMALPRECISON, "2");
//		gr.addAtributtes(Graph.GRAPH_ATTR_NUMBERPREFIX, "");
//		gr.addAtributtes(Graph.GRAPH_ATTR_EXPORTENABLED, "1");
//	
//  	gr.setTitle("");
//  	gr.getModel().addColumn("agrupador1", ModelMatrix.GRAPH_FUNCTION_CATEGORIE);
//		gr.getModel().addColumn("agrupador2", ModelMatrix.GRAPH_FUNCTION_DATASET);
//		gr.getModel().addColumn("cantidad1", ModelMatrix.GRAPH_FUNCTION_VALUE);
//
//  	oWinlist.addGrafico(gr);
//  	Graph gr2 = new GraphScriptWorldArc();
//  	gr2.addAtributtes(Graph.GRAPH_ATTR_ROTATENAMES, "1");
//		gr2.addAtributtes(Graph.GRAPH_ATTR_DECIMALPRECISON, "2");
//		gr2.addAtributtes(Graph.GRAPH_ATTR_NUMBERPREFIX, "");
//		gr2.addAtributtes(Graph.GRAPH_ATTR_EXPORTENABLED, "1");
//	
//		gr2.setTitle("");
//		ModelMatrix mg = new ModelMatrix();
//
//		mg.addColumn("agrupador1", "agrupador3", ModelMatrix.GRAPH_FUNCTION_CATEGORIE);
//		mg.addColumn("agrupador2", "agrupador4", ModelMatrix.GRAPH_FUNCTION_DATASET);
//		mg.addColumn("cantidad1", ModelMatrix.GRAPH_FUNCTION_VALUE);
//		gr2.setModel(mg);
//  	oWinlist.addGrafico(gr2);
//  	
//  	Graph gr3 = new GraphScriptSerieTemporal();
//  	gr3.addAtributtes(Graph.GRAPH_ATTR_ROTATENAMES, "1");
//		gr3.addAtributtes(Graph.GRAPH_ATTR_DECIMALPRECISON, "2");
//		gr3.addAtributtes(Graph.GRAPH_ATTR_NUMBERPREFIX, "");
//		gr3.addAtributtes(Graph.GRAPH_ATTR_EXPORTENABLED, "1");
//	
//  	gr3.setTitle("");
//  	ModelGrid mg3 = new ModelGrid();
//
//		mg3.addColumn("agrupador2", ModelGrid.GRAPH_FUNCTION_CATEGORIE);
//		mg3.addColumn("cantidad1",null,((BizQueVender)getRecords().getParent()).getFieldname1(), ModelGrid.GRAPH_FUNCTION_VALUE);
//		mg3.addColumn("cantidad2",null,((BizQueVender)getRecords().getParent()).getFieldname2(), ModelGrid.GRAPH_FUNCTION_VALUE);
//		gr3.setModel(mg3);
//  	oWinlist.addGrafico(gr3);
//  	
//  	Graph gr4 = new GraphMSLine();
//  	gr4.addAtributtes(Graph.GRAPH_ATTR_ROTATENAMES, "1");
//		gr4.addAtributtes(Graph.GRAPH_ATTR_DECIMALPRECISON, "2");
//		gr4.addAtributtes(Graph.GRAPH_ATTR_NUMBERPREFIX, "");
//		gr4.addAtributtes(Graph.GRAPH_ATTR_EXPORTENABLED, "1");
//	
//  	gr4.setTitle("");
//		ModelMatrix mg4 = new ModelMatrix();
//
//		mg4.addColumn("agrupador2", ModelMatrix.GRAPH_FUNCTION_CATEGORIE);
//		mg4.addColumn("agrupador1", ModelMatrix.GRAPH_FUNCTION_DATASET);
//		mg4.addColumn("cantidad1", ModelMatrix.GRAPH_FUNCTION_VALUE);
//		gr4.setModel(mg4);
//
//  	oWinlist.addGrafico(gr4);
  	super.ConfigurarGraficos(oWinlist);
	}
	
	@Override
	public void createTotalizer(JWinList oLista) throws Exception {
		super.createTotalizer(oLista);
	}

}
