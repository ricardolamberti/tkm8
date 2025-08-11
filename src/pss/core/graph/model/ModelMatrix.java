package pss.core.graph.model;

import pss.core.graph.Column;
import pss.core.graph.Graph;
import pss.core.graph.analize.Categories;
import pss.core.graph.analize.Dataset;
import pss.core.graph.analize.Value;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JWin;
import pss.core.winUI.lists.JColumnaLista;

/**
 * El modelo MatrixAndLine es del tipo matriz: 
 *     Cat1 Cat2  
 * DS1   0    2
 * DS2   1    3
 * 
 * Y los datos vienen del siguiente modo
 * 
 * Categoria   Dataset  value
 * Cat1         DS1       0
 * Cat1         DS2       1
 * Cat2         DS1       2
 * Cat2         DS2       3
 * 
 * Observese en este caso que los nombres de las categorias y los datasets son tomadas de los datos 
 * ( en contraposicion a lo que sucede con ModelGrid)
 * @author ricardo.lamberti
 *
 */
public class ModelMatrix extends ModelGraph {
	public static final String GRAPH_FUNCTION_CATEGORIE="CATEGORIE";
	public static final String GRAPH_FUNCTION_DATASET="DATASET";
	public static final String GRAPH_FUNCTION_VALUE="VALUE";
	public static final String GRAPH_FUNCTION_NONE="NONE";

	public ModelMatrix() {
		name = "matrix";
	}
	
	public boolean hasCategoria() {return true;}
	public boolean hasDataset() {return true;}
	public boolean hasValor1() {return true;}
	public boolean hasValor2() {return false;}
	public boolean hasValor3() {return false;}
	public boolean hasValor4() {return false;}
	public boolean hasValor5() {return false;}
	public boolean hasDatasetLine(){return false;}
	public boolean hasValorLine(){return false;}

	public void doAnalizeRowInGraph(JColumnaLista[] aColumns, JWin zWin,Graph gr) throws Exception {
		Dataset ds = null;
		Dataset dsLine = null;
		Categories cat = null;
		Column grCol;
		grCol=getMapColumnFunction().getElement(ModelMatrix.GRAPH_FUNCTION_CATEGORIE);
		if (grCol!=null) cat=extractCategorie(aColumns, zWin, gr, grCol);
		
		grCol=getMapColumnFunction().getElement(ModelMatrix.GRAPH_FUNCTION_DATASET);
		if (grCol!=null) ds=extractDataset(aColumns, zWin, gr, grCol, ModelMatrix.GRAPH_FUNCTION_DATASET);
		if (cat!=null&&ds!=null) {
			// segunda fase de busqueda de datos
			grCol=getMapColumnFunction().getElement(ModelMatrix.GRAPH_FUNCTION_VALUE);
			if (grCol!=null) {
				String value=extractValue(aColumns, zWin, gr, grCol);
				Value val=new Value();
				val.setCategorie(cat.getName());
				val.setData(value);
				JMap<String,String> defa = JCollectionFactory.createOrderedMap();
				gr.addGraphValueAttributes(defa);
				val.addAttributes(zWin.getGraphValueAttributes(gr,defa));
				ds.getValues().addElement(cat.getName(), val);
			}
		}
		

	}


	public void extractDataFromColumns(JColumnaLista[] aColumns,Graph gr) throws Exception {
		Column grColCat;
		Column grColDs;
		Column grColVl;
		JColumnaLista oCol;
		
		grColCat=getMapColumnFunction().getElement(ModelMatrix.GRAPH_FUNCTION_CATEGORIE);
		grColDs =getMapColumnFunction().getElement(ModelMatrix.GRAPH_FUNCTION_DATASET);
		grColVl =getMapColumnFunction().getElement(ModelMatrix.GRAPH_FUNCTION_VALUE);
		
		if (grColCat==null || grColDs==null || grColVl==null) throw new Exception("Incomplete Graph define");
		
		for(int i=0; i<aColumns.length; i++) {
			oCol=aColumns[i];
			String sColName=oCol.GetCampo();
			if (grColVl.getColumn()==null) continue;
			if (grColVl.getColumn().equals(sColName)) {
				gr.getAtributtes().addElement(Graph.GRAPH_ATTR_YAXISNAME, oCol.GetColumnaTitulo());
				
			}
			if (grColCat.getColumn().equals("") || grColCat.getColumn().equals(sColName)) {
				gr.getAtributtes().addElement(Graph.GRAPH_ATTR_XAXISNAME, oCol.GetColumnaTitulo());
				
			}
		}
	}
	
}
