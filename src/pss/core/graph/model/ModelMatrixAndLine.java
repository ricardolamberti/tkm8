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
 * El modelo MatrixAndLine es del tipo matriz, y un dataset extra, con informacion totalizadora, promedio, etc: 
 *     Cat1 Cat2  
 * DS1   0    2
 * DS2   1    3
 * Total 4    5
 * 

 * Y los datos vienen del siguiente modo
 * 
 * Categoria   Dataset  value 
 * Cat1         DS1       0
 * Cat1         DS2       1
 * Cat1         Total     4
 * Cat2         DS1       2
 * Cat2         DS2       3
 * Cat2         Total     5
 *  
 */

public class ModelMatrixAndLine extends ModelGraph {
	public static final String GRAPH_FUNCTION_CATEGORIE="CATEGORIE";
	public static final String GRAPH_FUNCTION_DATASET="DATASET";
	public static final String GRAPH_FUNCTION_DATASET_LINE="DATASET_LINE";
	public static final String GRAPH_FUNCTION_VALUE_LINE="VALUE_LINE";
	public static final String GRAPH_FUNCTION_VALUE="VALUE";
	public static final String GRAPH_FUNCTION_NONE="NONE";

	public ModelMatrixAndLine() {
		name = "matrix";
	}
	public boolean hasCategoria() {return true;}
	public boolean hasDataset() {return true;}
	public boolean hasValor1() {return true;}
	public boolean hasValor2() {return false;}
	public boolean hasValor3() {return false;}
	public boolean hasValor4() {return false;}
	public boolean hasValor5() {return false;}
	public boolean hasDatasetLine(){return true;}
	public boolean hasValorLine(){return true;}

	
	public void doAnalizeRowInGraph(JColumnaLista[] aColumns, JWin zWin,Graph gr) throws Exception {
		Dataset ds = null;
		Dataset dsLine = null;
		Categories cat = null;
		Column grCol;
		grCol=getMapColumnFunction().getElement(ModelMatrixAndLine.GRAPH_FUNCTION_CATEGORIE);
		if (grCol!=null) cat=extractCategorie(aColumns, zWin, gr, grCol);
		
		grCol=getMapColumnFunction().getElement(ModelMatrixAndLine.GRAPH_FUNCTION_DATASET);
		if (grCol!=null) ds=extractDataset(aColumns, zWin, gr, grCol, ModelMatrixAndLine.GRAPH_FUNCTION_DATASET);
		if (cat!=null&&ds!=null) {
			// segunda fase de busqueda de datos
			grCol=getMapColumnFunction().getElement(ModelMatrixAndLine.GRAPH_FUNCTION_VALUE);
			if (grCol!=null) {
				String value=extractValue(aColumns, zWin, gr, grCol);
				Value val=new Value();
				val.setCategorie(cat.getName());
				val.setData(value);
				ds.getValues().addElement(cat.getName(), val);
			}
		}
		
		grCol=getMapColumnFunction().getElement(ModelMatrixAndLine.GRAPH_FUNCTION_DATASET_LINE);
		if (grCol!=null) {
			dsLine=extractDataset(aColumns, zWin, gr, grCol, ModelMatrixAndLine.GRAPH_FUNCTION_DATASET_LINE);
			dsLine.setParentYAxis("S");
		}
		if (cat!=null&&dsLine!=null) {
			// tercera fase de busqueda de datos
			grCol=getMapColumnFunction().getElement(ModelMatrixAndLine.GRAPH_FUNCTION_VALUE_LINE);
			if (grCol!=null) {
				String value=extractValue(aColumns, zWin, gr, grCol);
				Value val=new Value();
				val.setCategorie(cat.getName());
				val.setData(value);
				JMap<String,String> defa = JCollectionFactory.createMap();
				gr.addGraphValueAttributes(defa);
				val.addAttributes(zWin.getGraphValueAttributes(gr,defa));
 			  dsLine.getValues().addElement(cat.getName(), val);
			}
		}
	}

	
	
	public void extractDataFromColumns(JColumnaLista[] aColumns,Graph gr) throws Exception {
		Column grColCat;
		Column grColDs;
		Column grColVl;
		Column grColDsLn;
		Column grColVlLn;
		JColumnaLista oCol;
		
		grColCat=getMapColumnFunction().getElement(ModelMatrixAndLine.GRAPH_FUNCTION_CATEGORIE);
		grColDs =getMapColumnFunction().getElement(ModelMatrixAndLine.GRAPH_FUNCTION_DATASET);
		grColVl =getMapColumnFunction().getElement(ModelMatrixAndLine.GRAPH_FUNCTION_VALUE);
		grColDsLn =getMapColumnFunction().getElement(ModelMatrixAndLine.GRAPH_FUNCTION_DATASET_LINE);
		grColVlLn =getMapColumnFunction().getElement(ModelMatrixAndLine.GRAPH_FUNCTION_VALUE_LINE);
		
		if (grColCat==null || grColDs==null || grColVl==null ) 
			throw new Exception("Incomplete Graph define");
		
		for(int i=0; i<aColumns.length; i++) {
			oCol=aColumns[i];
			String sColName=oCol.GetCampo();
			if (grColCat.getColumn().equals(sColName)) {
				gr.getAtributtes().addElement(Graph.GRAPH_ATTR_XAXISNAME, oCol.GetColumnaTitulo());
				
			}
			if (grColVl.getColumn().equals(sColName)) {
				gr.getAtributtes().addElement(Graph.GRAPH_ATTR_PYAXISNAME, oCol.GetColumnaTitulo());
				
			}
			if (grColVlLn!=null && grColVlLn.getColumn().equals(sColName)) {
				gr.getAtributtes().addElement(Graph.GRAPH_ATTR_SYAXISNAME, oCol.GetColumnaTitulo());
				
			}
		}
	}
}
