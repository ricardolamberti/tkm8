package pss.core.graph.model;

import pss.core.graph.Column;
import pss.core.graph.Graph;
import pss.core.graph.analize.Categories;
import pss.core.graph.analize.Dataset;
import pss.core.graph.analize.Value;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.win.JWin;
import pss.core.winUI.lists.JColumnaLista;
/**
 * Util cuando los datos vienen en el siguiente modo:
 * 
 * descr_cuenta       Saldo No Pagado              Saldo no facturado
 * Proveedor1           10                        30
 * Proveedor2           310                       130
 * Proveedor3           410                       330
 * 
 * 		model.addColumn("descr_cuenta",ModelGrid.GRAPH_FUNCTION_CATEGORIE);
 *		model.addColumn(new Dataset("Saldo No Pagado"),"saldo_no_pagado",ModelGrid.GRAPH_FUNCTION_VALUE);
 *		model.addColumn(new Dataset("Saldo No Facturado"),"saldo_no_facturado",ModelGrid.GRAPH_FUNCTION_VALUE);
 *
 * Observese que como el dataset no esta en los datos de la tabla, se lo crea a mano y se lo asocia a la columna
 *
 */

public class ModelGridAndLine extends ModelGraph {
	public static final String GRAPH_FUNCTION_CATEGORIE="CATEGORIE";
	public static final String GRAPH_FUNCTION_VALUE="VALUE";
	public static final String GRAPH_FUNCTION_NONE="NONE";
	public static final String GRAPH_FUNCTION_VALUE_LINE="VALUE_LINE";

	public ModelGridAndLine() {
		name = "matrix";
	}

	public boolean hasCategoria() {return true;}
	public boolean hasDataset() {return false;}
	public boolean hasValor1() {return true;}
	public boolean hasValor2() {return true;}
	public boolean hasValor3() {return true;}
	public boolean hasValor4() {return true;}
	public boolean hasValor5() {return true;}
	public boolean hasDatasetLine(){return false;}
	public boolean hasValorLine(){return true;}

	
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
		getMapColumnFunction().addElement(zDataset.getName()+"_"+zFunction, col);
		return col;
		
	}
	public void doAnalizeRowInGraph(JColumnaLista[] aColumns, JWin zWin,Graph gr) throws Exception {
		Categories cat = null;
		Column grCol;
		int colIt;
		grCol=getMapColumnFunction().getElement(ModelGridAndLine.GRAPH_FUNCTION_CATEGORIE);
		if (grCol!=null) cat=extractCategorie(aColumns, zWin, gr, grCol);
		
		if (cat!=null) {
			// voy recorriendo las columnas, 
			for (colIt=0;colIt<aColumns.length;colIt++) {
				JIterator<Column> itColumn = getMapColumnFunction().getValueIterator();
				// y busco las que son GRAPH_FUNCTION_VALUE
				while (itColumn.hasMoreElements()) {
					grCol = itColumn.nextElement();
					if (grCol.getFunction().equals(ModelGridAndLine.GRAPH_FUNCTION_VALUE) && 
							grCol.getColumn().equals(aColumns[colIt].GetCampo()) &&
							grCol.isGridForm()) {
						// cuando encuentro una, agrego el dato
						Dataset ds = addDataset(gr,grCol.getDataset(),ModelGridAndLine.GRAPH_FUNCTION_VALUE,zWin);
						String value=extractValue(aColumns, zWin, gr, grCol);
						Value val=new Value();
						val.setCategorie(cat.getName());
						val.setData(value);
						JMap<String,String> defa = JCollectionFactory.createOrderedMap();
						gr.addGraphValueAttributes(defa);
						val.addAttributes(zWin.getGraphValueAttributes(gr,defa));
						ds.getValues().addElement(cat.getName(), val);
						break;
					}
					if (grCol.getFunction().equals(ModelGridAndLine.GRAPH_FUNCTION_VALUE_LINE) && 
							grCol.getColumn().equals(aColumns[colIt].GetCampo()) &&
							grCol.isGridForm()) {
						// cuando encuentro una, agrego el dato
						Dataset ds = addDataset(gr,grCol.getDataset(),ModelGridAndLine.GRAPH_FUNCTION_VALUE_LINE,zWin);
						ds.setParentYAxis("S");
						String value=extractValue(aColumns, zWin, gr, grCol);
						Value val=new Value();
						val.setCategorie(cat.getName());
						val.setData(value);
						JMap<String,String> defa = JCollectionFactory.createOrderedMap();
						gr.addGraphValueAttributes(defa);
						val.addAttributes(zWin.getGraphValueAttributes(gr,defa));
						ds.getValues().addElement(cat.getName(), val);
						break;
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
