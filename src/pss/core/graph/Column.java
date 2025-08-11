package pss.core.graph;

import pss.core.graph.analize.Dataset;

/**
 * Asociasion entre un columna de JWebWInList y su funcion en un grafico
 * @author ricardo.lamberti
 *
 */
public class Column {
	/** solo se usa en el modelo grid*/
	private Dataset dataset=null;
	private String column;
	private String function;
	private String campoGeoPosition;
	
	

	public String getCampoGeoPosition() {
		return campoGeoPosition;
	}

	public void setCampoGeoPosition(String campoGeoPosition) {
		this.campoGeoPosition = campoGeoPosition;
	}

	public String getColumn() {
		return column;
	}
	
	public void setColumn(String column) {
		this.column=column;
	}
	
	public String getFunction() {
		return function;
	}
	
	public void setFunction(String function) {
		this.function=function;
	}

	
	public Dataset getDataset() {
		return dataset;
	}

	
	public void setDataset(Dataset dataset) {
		this.dataset=dataset;
	}

	public boolean isGridForm() {
		return this.dataset!=null;
	}
	
}
