package pss.core.graph.analize;

import pss.core.graph.Graph;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JColumnaLista;

/**
 * Analiza una lista de graficos, prepara la informacion para que ser subida a un xml y luego ser levantada por el fusionChart.
 * un Jwin: contiene los datos y los graficos a generar
 * un JWebWinList: genera el XML de los datos (en este proceso esta clase ayuda al ordenamiento de lo datos)
 * 
 * Basicamente llena los datos de la clase Graph
 * 
 * La transformacion es desde la forma:
 * 
 * CATEGORIA			DATASET			 VALOR
 * Productor A    Vendedor A		1000   
 * Productor A    Vendedor B		 100   
 * Productor A    Vendedor C		  10   
 * Productor B    Vendedor A	 10000   
 * Productor B    Vendedor B		1000
 * 
 * A la forma:
 * Grafico de barras
 * 	              Categorias: Producto A, Producto B
 * 			 DataSets: Vendedor A ( 1000    ,10000)
 *  							 Vendedor B (  100    , 1000)
 *  							 Vendedor C (   10    ,     )
 *  
 * @author ricardo.lamberti
 *
 */
public class AnalizeDataGraph {
	private JList<Graph> graphs;
	private int graphRefresh=-1;
	
	public int getGraphRefresh() {
		return graphRefresh;
	}

	
	public void setGraphRefresh(int graphRefresh) {
		if (graphRefresh<this.graphRefresh || this.graphRefresh==-1)
			this.graphRefresh=graphRefresh;
	}

	/**
	 * Agrega de a un grafico, no usado
	 * @param gr
	 */
	public void addGraphic(Graph gr) {
		getGraphs().addElement(gr);
	}
	
	/**
	 * Inicia un analisis, recibe la lista de graficos asociados al JWin
	 * @param zGraph Lista de graficos asociados
	 * @throws Exception 
	 */
	public void startAnalize(JColumnaLista[] aColumns,JList<Graph> zGraph) throws Exception {
		setGraphs(zGraph);
		analizeGraphs(aColumns);
	}

	public void analizeGraphs(JColumnaLista[] aColumns) throws Exception {
	   
	  JIterator<Graph> it = getGraphs().getIterator();
		while(it.hasMoreElements()) {
			Graph gr = it.nextElement();
			setGraphRefresh(gr.getRefresh());
			gr.getModel().extractDataFromColumns(aColumns,gr);
			if (!gr.hasExtractDataIntegred()) {
				extractAlternativeData(gr,aColumns,gr.getAlternativeData());
			}
		}
	}

	public void endAnalize(JColumnaLista[] aColumns,JList<Graph> zGraph) throws Exception {
	  JIterator<Graph> it = getGraphs().getIterator();
		while(it.hasMoreElements()) {
			Graph gr = it.nextElement();
			gr.getModel().endAnalize(aColumns,gr);
		}
	}

	public void extractAlternativeData(Graph gr,JColumnaLista[] aColumns,JWins alternative) throws Exception {
		alternative.readAll();
		while (alternative.nextRecord()) {
			JWin win = alternative.getRecord();
			analizeRowInGraph(aColumns,win,gr);
		}
	}

	
	/**
	 * Analiza los datos de una fila y los formatea para generar losgraficos asociados
	 * @param aColumns Mapa de columnas
	 * @param zWin JWin con los datos de la Fila
	 * @throws Exception
	 */
	public void analizeRow(JColumnaLista[] aColumns, JWin zWin) throws Exception {
	   
		  JIterator<Graph> it = getGraphs().getIterator();
			while(it.hasMoreElements()) {
				// me fijo si lo necesito para algun grafico
				Graph gr = it.nextElement();
				if (!gr.hasExtractDataIntegred()) continue;
					analizeRowInGraph(aColumns,zWin,gr);
			}
	}
	

	/** analiza la fila para un grafico
	 * 
	 * @param aColumns Mapa de columnas
	 * @param zWin JWin con los datos de la Fila
	 * @param gr el grafico a analizar
	 * @throws Exception
	 */
	private void analizeRowInGraph(JColumnaLista[] aColumns, JWin zWin,Graph gr) throws Exception {
			// primera fase de busqueda de datos
			gr.getModel().doAnalizeRowInGraph(aColumns,zWin,gr);
		}
	 
	/**
	 * Devuelve la lista de graficos
	 * @return lista de graficos
	 */
	public JList<Graph> getGraphs() {
		if (graphs==null)
			graphs = JCollectionFactory.createList();
		return graphs;
	}

	/**
	 * setea una lista de graficos
	 * @param graphs lista de graficos
	 */
	public void setGraphs(JList<Graph> graphs) {
		this.graphs=graphs;
	}
}
