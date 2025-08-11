package pss.core.graph.model;

import pss.core.graph.Column;
import pss.core.graph.Graph;
import pss.core.graph.analize.Dataset;
import pss.core.graph.analize.Value;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JWin;
import pss.core.winUI.lists.JColumnaLista;

/**
 * El modelo vector es para dataset del formato: (Descripcion, valor)
 * 
 * @author ricardo.lamberti
 * 
 */

public class ModelVector extends ModelGraph {

	public static final String GRAPH_FUNCTION_DATASET="DATASET";
	public static final String GRAPH_FUNCTION_VALUE="VALUE";

	public ModelVector() {
		name="vector";
	}
	
	public boolean hasCategoria() {return false;}
	public boolean hasDataset() {return true;}
	public boolean hasValor1() {return true;}
	public boolean hasValor2() {return false;}
	public boolean hasValor3() {return false;}
	public boolean hasValor4() {return false;}
	public boolean hasValor5() {return false;}
	public boolean hasDatasetLine(){return false;}
	public boolean hasValorLine(){return false;}


	public void doAnalizeRowInGraph(JColumnaLista[] aColumns, JWin zWin, Graph gr) throws Exception {
		Dataset ds=null;
		Column grCol;
		grCol=getMapColumnFunction().getElement(ModelVector.GRAPH_FUNCTION_DATASET);
		if (grCol!=null) ds=extractDataset(aColumns, zWin, gr, grCol, ModelVector.GRAPH_FUNCTION_DATASET);

		if (ds!=null) {
			grCol=getMapColumnFunction().getElement(ModelVector.GRAPH_FUNCTION_VALUE);
			if (grCol!=null) {
				String value;
				Value val=new Value();
				val.setCategorie(ds.getName());
				if (grCol.getColumn().equalsIgnoreCase("count()")) {
					value="1";
				} else {
					 value=extractValue(aColumns, zWin, gr, grCol);
				}
				JMap<String, String> defa=JCollectionFactory.createMap();
				gr.addGraphValueAttributes(defa);
				val.addAttributes(zWin.getGraphValueAttributes(gr, defa));
				Value oldVal=ds.getValues().getElement(grCol.getColumn());
				double fvalor=value.equals("S")?1:value.equals("N")?0:JTools.isNumber(value)?Double.parseDouble(value):0;
				if (oldVal==null) {
					val.setData(fvalor);
					ds.getValues().addElement(grCol.getColumn(), val);
				} else
					oldVal.setData((Double)oldVal.getData()+fvalor);
			}
		}

	}


}
