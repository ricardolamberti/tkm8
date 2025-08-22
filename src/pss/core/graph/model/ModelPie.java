package pss.core.graph.model;

import pss.core.graph.Column;
import pss.core.graph.Graph;
import pss.core.graph.analize.Dataset;
import pss.core.graph.analize.Value;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.win.JWin;
import pss.core.winUI.lists.JColumnaLista;

public class ModelPie extends ModelGraph {
	public static final String GRAPH_FUNCTION_VALUE="VALUE";
	public static final String GRAPH_FUNCTION_COMPLEMENTO="COMPLEMENTO";

	public ModelPie() {
		name = "vector";
	}

	public boolean hasCategoria() {return false;}
	public boolean hasDataset() {return false;}
	public boolean hasValor1() {return true;}
	public boolean hasValor2() {return true;}
	public boolean hasValor3() {return true;}
	public boolean hasValor4() {return true;}
	public boolean hasValor5() {return true;}
	public boolean hasDatasetLine(){return false;}
	public boolean hasValorLine(){return false;}

	double acumulado=0;
	
	/**
	 * Formato de columna donde ya los graficos viene en forma de grilla
	 * @param Dataset
	 * @param zColumn
	 * @param zFunction
	 * @return
	 */
	public Column addColumn(Dataset zDataset, String zColumn,String zFunction) {
		Column col = new Column();
		col.setDataset(zDataset);
		col.setColumn(zColumn);
		col.setFunction(zFunction);
		getMapColumnFunction().addElement(zDataset.getName(), col);
		return col;
		
	}
	public void doAnalizeRowInGraph(JColumnaLista[] aColumns, JWin zWin,Graph gr) throws Exception {
		Column grCol;
		int colIt;
		
			// voy recorriendo las columnas, 
			for (colIt=0;colIt<aColumns.length;colIt++) {
				JIterator<Column> itColumn = getMapColumnFunction().getValueIterator();
				// y busco las que son GRAPH_FUNCTION_VALUE
				while (itColumn.hasMoreElements()) {
					grCol = itColumn.nextElement();
					if (grCol.getFunction().equals(ModelPie.GRAPH_FUNCTION_VALUE) && 
							grCol.getColumn().equals(aColumns[colIt].GetCampo()) &&
							grCol.isGridForm()) {
						// cuando encuentro una, agrego el dato
						Dataset ds = addDataset(gr,grCol.getDataset(),ModelPie.GRAPH_FUNCTION_VALUE,zWin);
						String value=extractValue(aColumns, zWin, gr, grCol);
						
						JMap<String,String> defa = JCollectionFactory.createOrderedMap();
						gr.addGraphValueAttributes(defa);
						Value val=ds.getValues().getElement(grCol.getColumn());
						double fvalor=value.equals("S")?1:value.equals("N")?0:JTools.isNumber(value)?Double.parseDouble(value):0;
						acumulado+=fvalor;
						if (val==null) {
							val=new Value();
							val.setCategorie(grCol.getColumn());
							val.setData(new Double(fvalor));
							ds.getValues().addElement(grCol.getColumn(), val);
						} else
							val.setData((Double)val.getData()+fvalor);
						val.addAttributes(zWin.getGraphValueAttributes(gr,defa));
					}
					if (grCol.getFunction().equals(ModelPie.GRAPH_FUNCTION_COMPLEMENTO) && 
							grCol.getColumn().equals(aColumns[colIt].GetCampo()) &&
							grCol.isGridForm()) {
						// cuando encuentro una, agrego el dato
						Dataset ds = addDataset(gr,grCol.getDataset(),ModelPie.GRAPH_FUNCTION_COMPLEMENTO,zWin);
						String value=extractValue(aColumns, zWin, gr, grCol);
						
						JMap<String,String> defa = JCollectionFactory.createOrderedMap();
						gr.addGraphValueAttributes(defa);
						Value val=ds.getValues().getElement(grCol.getColumn());
						double fvalor=value.equals("S")?1:value.equals("N")?0:JTools.isNumber(value)?Double.parseDouble(value):0;
						if (val==null) {
							val=new Value();
							val.setCategorie(grCol.getColumn());
							val.setData(new Double(fvalor));
							ds.getValues().addElement(grCol.getColumn(), val);
						} else
							val.setData((Double)val.getData()+fvalor);
						val.addAttributes(zWin.getGraphValueAttributes(gr,defa));
					}
				}
			}
		
	}
	
	public void endAnalize(JColumnaLista[] aColumns, Graph gr) throws Exception {
		Column grCol;
		int colIt;
		
			// voy recorriendo las columnas, 
			for (colIt=0;colIt<aColumns.length;colIt++) {
				JIterator<Column> itColumn = getMapColumnFunction().getValueIterator();
				// y busco las que son GRAPH_FUNCTION_VALUE
				while (itColumn.hasMoreElements()) {
					grCol = itColumn.nextElement();
					if (grCol.getFunction().equals(ModelPie.GRAPH_FUNCTION_COMPLEMENTO) && 
							grCol.getColumn().equals(aColumns[colIt].GetCampo()) &&
							grCol.isGridForm()) {
						// cuando encuentro una, agrego el dato
						Dataset ds = addDataset(gr,grCol.getDataset(),ModelPie.GRAPH_FUNCTION_COMPLEMENTO,null);
						Value val=ds.getValues().getElement(grCol.getColumn());
						if (val==null) {
								continue;
						} else {
							double restante=(Double)val.getData()-acumulado<0?0:(Double)val.getData()-acumulado;
							val.setData(restante);
							ds.setColname(ds.getColname()+ " ("+restante+")");
						}
							
					}
					if (grCol.getFunction().equals(ModelPie.GRAPH_FUNCTION_VALUE) && 
							grCol.getColumn().equals(aColumns[colIt].GetCampo()) &&
							grCol.isGridForm()) {
						// cuando encuentro una, agrego el dato
						Dataset ds = addDataset(gr,grCol.getDataset(),ModelPie.GRAPH_FUNCTION_VALUE,null);
						Value val=ds.getValues().getElement(grCol.getColumn());
						if (val==null) {
								continue;
						} else {
							ds.setColname(ds.getColname()+ " ("+val.getData()+")");
						}
							
					}

				}
			}
		
	}

		private Dataset addDataset(Graph gr, Dataset dataset, String function, JWin zWin) throws Exception {
				Dataset ds = gr.getDatasets().getElement(dataset.getName());
				if (ds==null)  {
					gr.getDatasets().addElement(dataset.getName(), dataset);
				}
			return dataset;
		}

}
