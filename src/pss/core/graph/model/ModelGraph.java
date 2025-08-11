package pss.core.graph.model;

import pss.core.graph.Column;
import pss.core.graph.Graph;
import pss.core.graph.analize.Categories;
import pss.core.graph.analize.Dataset;
import pss.core.services.fields.JGeoPosition;
import pss.core.services.fields.JObject;
import pss.core.tools.GeoPosition;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JWin;
import pss.core.winUI.lists.JColumnaLista;


public abstract class ModelGraph {
	private JMap<String,Column> mapColumnFunction;

	protected String name;

	
	public String getName() {
		return name;
	}
	
	public void addAttributes(JMap<String,String> atributtes) {
	}
	
	public abstract boolean hasCategoria();
	public abstract boolean hasDataset();
	public abstract boolean hasValor1();
	public abstract boolean hasValor2();
	public abstract boolean hasValor3();
	public abstract boolean hasValor4();
	public abstract boolean hasValor5();
	public abstract boolean hasDatasetLine();
	public abstract boolean hasValorLine();
	

	public  JMap<String,Column> getMapColumnFunction() {
		if (mapColumnFunction==null) 
			mapColumnFunction = JCollectionFactory.createOrderedMap();
		return mapColumnFunction;
	}
	
	public void extractDataFromColumns(JColumnaLista[] aColumns,Graph gr) throws Exception {
		
	}

	public abstract void doAnalizeRowInGraph(JColumnaLista[] aColumns, JWin zWin,Graph gr) throws Exception;
	public void endAnalize(JColumnaLista[] aColumns, Graph gr) throws Exception {
		
	}
		
	// buscar la categoria
	protected Categories extractCategorie(JColumnaLista[] aColumns, JWin zWin,Graph gr,Column grCol) throws Exception {
		Categories cat = null;
		JColumnaLista oCol;
		if (grCol.getColumn().equals("")) {
			cat = new Categories();
			cat.setName(gr.getAtributtes().getElement(Graph.GRAPH_ATTR_XAXISNAME));
			JMap<String,String> defa = JCollectionFactory.createMap();
			gr.addGraphCategorieAttributes(defa);
			gr.getCategories().addElement(gr.getAtributtes().getElement(Graph.GRAPH_ATTR_XAXISNAME), cat);
			return cat;
		}
		for(int i=0; i<aColumns.length; i++) {
			oCol=aColumns[i];
			String sColName=oCol.GetCampo();
			if (sColName.equals("")) continue;
			JObject<?> oProp=zWin.getRecord().getProp(sColName,false);
			if (oProp==null) continue;
			String data = zWin.getCategorieDescription(gr,oProp.toString());
			if (grCol.getColumn().toLowerCase().equals(sColName.toLowerCase())) {
					cat = gr.getCategories().getElement(data);
					if (cat==null)  {
						cat = new Categories();
						cat.setName(data);
						JMap<String,String> defa = JCollectionFactory.createMap();
						gr.addGraphCategorieAttributes(defa);
						cat.addAttributes(zWin.getGraphCategorieAttributes(gr,defa));
						GeoPosition position;
						try {
							position = grCol.getCampoGeoPosition()==null?null:grCol.getCampoGeoPosition().equals("")?null:((JGeoPosition) zWin.getRecord().getProp(grCol.getCampoGeoPosition())).getValue();
						} catch (Exception e) {
							position=null;
						}
						if (position!=null) cat.setGeoPosition(position);
						gr.getCategories().addElement(data, cat);
						

					}
					break;
			}
		}
		return cat;
	}

	// extrae el dataset
	protected Dataset extractDataset(JColumnaLista[] aColumns, JWin zWin,Graph gr,Column grCol,String function) throws Exception {
		Dataset ds = null;
		JColumnaLista oCol;
		for(int i=0; i<aColumns.length; i++) {
			// tomo una dato de la columna
			oCol=aColumns[i];
			if (oCol==null) continue;
			String sColName=oCol.GetCampo();
			if (sColName.equals("")) continue;
			if (sColName.startsWith("rank_")) continue;
			JObject<?> oProp=zWin.getRecord().getProp(sColName);
			if (oProp==null) continue;
			if (!grCol.getColumn().toLowerCase().equals(sColName.toLowerCase())) continue;
//			String sPropFormatted;
			String data="";
			try {
//				sPropFormatted=oProp.toFormattedString();
				data = zWin.getDatasetDescription(gr,oProp.toFormattedString());
			} catch (Exception e) {
				data="";
			}
			GeoPosition position;
			try {
//				sPropFormatted=oProp.toFormattedString();
				position = grCol.getCampoGeoPosition().equals("")?null:((JGeoPosition) zWin.getRecord().getProp(grCol.getCampoGeoPosition())).getValue();
			} catch (Exception e) {
				position=null;
			}
//		  if (sPropFormatted==null) sPropFormatted="";
			ds = gr.getDatasets().getElement(data+"_"+function);
			if (ds!=null) continue;
			ds = new Dataset();
			ds.setName(data+"_"+function);
			ds.setColname(data);
			ds.setColor(zWin.getColor());
			if (position!=null) ds.setGeoPosition(position);
			JMap<String,String> defa = JCollectionFactory.createOrderedMap();
			gr.addGraphDatasetAttributes(defa);
			ds.addAttributes(zWin.getGraphDatasetAttributes(gr,defa));

			gr.getDatasets().addElement(ds.getName(), ds);
		}
		return ds;
	}

	protected String extractStringValue(JColumnaLista[] aColumns, JWin zWin,Graph gr,Column grCol) throws Exception {
		JColumnaLista oCol;
		for(int i=0; i<aColumns.length; i++) {
			// tomo una dato de la columna
			oCol=aColumns[i];
			if (oCol==null) continue;
			String sColName=oCol.GetCampo();
			if (sColName.equals("")) continue;
			JObject<?> oProp=zWin.getRecord().getProp(sColName);
			if (oProp==null) continue;
			String sPropFormatted=oProp.toFormattedString();
		  if (sPropFormatted==null) sPropFormatted="";
			String data = zWin.getGraphValue(gr,oProp.toString());
			if (grCol.getColumn().equals(sColName)) {
				double max = gr.getMaxValue();
				
					return data;
			}
		}
		return "";
	}	
	// extrae el valor
	protected String extractValue(JColumnaLista[] aColumns, JWin zWin,Graph gr,Column grCol) throws Exception {
		JColumnaLista oCol;
		for(int i=0; i<aColumns.length; i++) {
			// tomo una dato de la columna
			oCol=aColumns[i];
			if (oCol==null) continue;
			String sColName=oCol.GetCampo();
			if (sColName.equals("")) continue;
			JObject<?> oProp=zWin.getRecord().getProp(sColName);
			if (oProp==null) continue;
			String sPropFormatted=oProp.toFormattedString();
		  if (sPropFormatted==null) sPropFormatted="";
			String data = zWin.getGraphValue(gr,oProp.toString());
			if (grCol.getColumn()==null) continue;
			if (grCol.getColumn().equals(sColName)) {
				double max = gr.getMaxValue();
				data = data.equals("S")?"1":data.equals("N")?"0":data.equals("error")?"0":data.equals("")?"0":data;
				if (!JTools.isNumber(data)) return data;
				double ldata =  (double)Double.parseDouble(data);
				if (ldata>max) gr.setMaxValue(ldata);
				return ""+JTools.rd(ldata,2);
			}
		}
		return "";
	}	
	
	/**
	 * Formato de columna donde ya los graficos viene en forma de tabla
	 * 
	 * @param Dataset
	 * @param zColumn
	 * @param zFunction
	 * @return
	 */
	public Column addColumn(String zColumn,String zFunction) {
		return addColumn(zColumn, null, zFunction);
	}
	public Column addColumn(String zColumn, String zGeoColumn,String zFunction) {
		Column col=new Column();
		col.setColumn(zColumn);
		col.setCampoGeoPosition(zGeoColumn);
		col.setFunction(zFunction);
		getMapColumnFunction().addElement(zFunction, col);
		return col;

	}
}
