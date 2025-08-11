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

public class ModelBullet extends ModelGraph {
	public static final String GRAPH_FUNCTION_CATEGORIE = "CATEGORIE";
	public static final String GRAPH_FUNCTION_ZONE1 = "ZONE1";
	public static final String GRAPH_FUNCTION_ZONE2 = "ZONE2";
	public static final String GRAPH_FUNCTION_ZONE3 = "ZONE3";
	public static final String GRAPH_FUNCTION_LABELZONE1 = "LABELZONE1";
	public static final String GRAPH_FUNCTION_LABELZONE2 = "LABELZONE2";
	public static final String GRAPH_FUNCTION_LABELZONE3 = "LABELZONE3";
	public static final String GRAPH_FUNCTION_DATA = "DATA";
	public static final String GRAPH_FUNCTION_OBJ = "OBJ";

	public ModelBullet() {
		name = "matrix";
	}

	public boolean hasCategoria() {
		return true;
	}

	public boolean hasDataset() {
		return false;
	}

	public boolean hasValor1() {
		return true;
	}

	public boolean hasValor2() {
		return true;
	}

	public boolean hasValor3() {
		return true;
	}

	public boolean hasValor4() {
		return true;
	}

	public boolean hasValor5() {
		return true;
	}

	public boolean hasDatasetLine() {
		return false;
	}

	public boolean hasValorLine() {
		return false;
	}

	/**
	 * Formato de columna donde ya los graficos viene en forma de grilla
	 * 
	 * @param Dataset
	 * @param zColumn
	 * @param zFunction
	 * @return
	 */
	public Column addColumn(Dataset zDataset, String zColumn, String zFunction) {
		return addColumn(zDataset, zColumn, null, zFunction);
	}

	public Column addColumn(Dataset zDataset, String zColumn, String zGeoColumn, String zFunction) {
		Column col = new Column();
		col.setDataset(zDataset);
		col.setColumn(zColumn);
		col.setCampoGeoPosition(zGeoColumn);
		col.setFunction(zFunction);
		getMapColumnFunction().addElement(zDataset.getName() + "_" + zFunction, col);
		return col;

	}

	public void doAnalizeRowInGraph(JColumnaLista[] aColumns, JWin zWin, Graph gr) throws Exception {
		Categories cat = null;
		Column grCol;
		int colIt;
		grCol = getMapColumnFunction().getElement(ModelBullet.GRAPH_FUNCTION_CATEGORIE);
		if (grCol != null)
			cat = extractCategorie(aColumns, zWin, gr, grCol);

		if (cat != null) {
			// voy recorriendo las columnas,
			for (colIt = 0; colIt < aColumns.length; colIt++) {
				JIterator<Column> itColumn = getMapColumnFunction().getValueIterator();
				String zone = null;
				String zonelabel = null;
				// y busco las que son GRAPH_FUNCTION_VALUE
				while (itColumn.hasMoreElements()) {
					grCol = itColumn.nextElement();
					if (grCol.getColumn().equals(aColumns[colIt].GetCampo()) && grCol.isGridForm()) {
						if (grCol.getFunction().equals(ModelBullet.GRAPH_FUNCTION_ZONE1))
							zone = ModelBullet.GRAPH_FUNCTION_ZONE1;
						else if (grCol.getFunction().equals(ModelBullet.GRAPH_FUNCTION_ZONE2))
							zone = ModelBullet.GRAPH_FUNCTION_ZONE2;
						else if (grCol.getFunction().equals(ModelBullet.GRAPH_FUNCTION_ZONE3))
							zone = ModelBullet.GRAPH_FUNCTION_ZONE3;
						else if (grCol.getFunction().equals(ModelBullet.GRAPH_FUNCTION_DATA))
							zone = ModelBullet.GRAPH_FUNCTION_DATA;
						else if (grCol.getFunction().equals(ModelBullet.GRAPH_FUNCTION_OBJ))
							zone = ModelBullet.GRAPH_FUNCTION_OBJ;
						else if (grCol.getFunction().equals(ModelBullet.GRAPH_FUNCTION_LABELZONE1))
							zonelabel = ModelBullet.GRAPH_FUNCTION_LABELZONE1;
						else if (grCol.getFunction().equals(ModelBullet.GRAPH_FUNCTION_LABELZONE2))
							zonelabel = ModelBullet.GRAPH_FUNCTION_LABELZONE2;
						else if (grCol.getFunction().equals(ModelBullet.GRAPH_FUNCTION_LABELZONE3))
							zonelabel = ModelBullet.GRAPH_FUNCTION_LABELZONE3;
						if (zone != null) {
							Dataset ds = addDataset(gr, grCol.getDataset(), zone, zWin);
							String value = extractValue(aColumns, zWin, gr, grCol);
							Value val = new Value();
							val.setCategorie(cat.getName());
							val.setData(value);
							JMap<String, String> defa = JCollectionFactory.createOrderedMap();
							gr.addGraphValueAttributes(defa);
							val.addAttributes(zWin.getGraphValueAttributes(gr, defa));
							ds.getValues().addElement(cat.getName(), val);
							break;
						}
						if (zonelabel != null) {
							Dataset ds = addDataset(gr, grCol.getDataset(), zonelabel, zWin);
							String value = extractStringValue(aColumns, zWin, gr, grCol);
							Value val = new Value();
							val.setCategorie(cat.getName());
							val.setData(value);
							JMap<String, String> defa = JCollectionFactory.createOrderedMap();
							gr.addGraphValueAttributes(defa);
							val.addAttributes(zWin.getGraphValueAttributes(gr, defa));
							ds.getValues().addElement(cat.getName(), val);
							break;
						}
					}
				}
			}
		}
	}

	private Dataset addDataset(Graph gr, Dataset dataset, String function, JWin zWin) throws Exception {
		Dataset ds = gr.getDatasets().getElement(dataset.getName());
		if (ds == null) {
			gr.getDatasets().addElement(dataset.getName(), dataset);
		}
		dataset.setType(function);
		return dataset;
	}

	public Column addColumn(String zColumn, String zFunction) {
		return addColumn(zColumn, null, zFunction);
	}

	public Column addColumn(String zColumn, String zGeoColumn, String zFunction) {
		if (zFunction.equals(GRAPH_FUNCTION_CATEGORIE))
			return super.addColumn(zColumn, zGeoColumn, zFunction);
		return addColumn(new Dataset(zColumn), zColumn, zGeoColumn, zFunction);
	}

	public Column addColumn(String zColumn, String zGeoColumn, String title, String zFunction) {
		if (zFunction.equals(GRAPH_FUNCTION_CATEGORIE))
			return super.addColumn(zColumn, zGeoColumn, zFunction);
		return addColumn(new Dataset(zColumn, title), zColumn, zGeoColumn, zFunction);
	}

}
