package pss.common.dbManagement.synchro;

import pss.core.tools.JExcepcion;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

/**
 * Define un indice logico de una tabla
 * 
 */
public class JDBClassIndex {

	/**
	 * Nombre del indice
	 */
	private String indexName = null;

	/**
	 * Descripcion de los campos del indice
	 */
	private JMap<String, JDBClassIndexField> indexFields = null;

	/**
	 * Indica si el indice es primary Key de la tabla
	 */
	private boolean indexIsPrimaryKey = false;

	/**
	 * Determina si el indice funciona como clustered o no.
	 */
	// TODO - Por ahora esta funcionalidad queda
	// supeditada a la descripta en SQL SERVER. Hay que estudiar la de postgres y oracle
	private boolean indexIsClustered = false;

	/**
	 * Indica si la clave del indice es unica
	 */
	private boolean indexIsUnique = true;

	/**
	 * Retorna el nombre del indice
	 * 
	 * @return Nombre del indice
	 */
	public String getIndexName() {
		if (indexName == null) {
			return "";
		}
		return indexName;
	}

	/**
	 * Setea el nombre del indice
	 * 
	 * @param pIndexName
	 *          Nombre del indice
	 * @throws Exception
	 *           Si el nombre del indice es vacio o nulo
	 */
	public void setIndexName(String pIndexName) throws Exception {
		if (pIndexName == null || pIndexName.isEmpty()) {
			JExcepcion.SendError("Nombre del indice es nulo o vacio");
		}
		this.indexName = pIndexName;
	}

	/**
	 * Chequea que el mapa se haya creado antes de ser usado
	 */
	private void checkForIndexFields() {
		if (this.indexFields == null) {
			indexFields = JCollectionFactory.createMap();
		}
	}

	/**
	 * Retorna el mapa de campos del indice
	 * 
	 * @return Mapa de campos o un mapa vacio si no fue seteado. No retorna null
	 */
	public JMap<String, JDBClassIndexField> getIndexFields() {
		checkForIndexFields();
		return indexFields;
	}

	/**
	 * Retorna el iterador de campos del indice
	 * 
	 * @return Iterador de campos del indice
	 */
	public JIterator<JDBClassIndexField> getIndexFieldIterator() {
		checkForIndexFields();
		return this.indexFields.getValueIterator();
	}

	/**
	 * Agrega un nuevo campo al indice
	 * 
	 * @param pFieldName Nombre del campo
	 * @throws Exception
	 */
	public void addField(String pFieldName) throws Exception {
		addField(pFieldName, JDBClassIndexField.TIPO_ORDEN_ASC);
	}
	
	/**
	 * Agrega un nuevo campo al indice
	 * 
	 * @param pFieldName Nombre del campo
	 * @param pSortFieldOrder Criterio de orden del campo
	 * @throws Exception 
	 */
	public void addField(String pFieldName, String pSortFieldOrder) throws Exception {
		JDBClassIndexField oIndexField;
		oIndexField = new JDBClassIndexField();
		
		oIndexField.setFieldName(pFieldName);
		oIndexField.setSortFieldOrder(pSortFieldOrder);
		addField(oIndexField);
	}
	
	/**
	 * Agrega un nuevo campo al indice
	 * 
	 * @param pTableIndiceField
	 *          Objeto del tipo
	 *          {@link pss.common.dbManagement.synchro.JDBClassIndexField JDBTableIndiceField} que
	 *          representa un campo del indice
	 * @throws Exception
	 */
	public void addField(JDBClassIndexField pTableIndiceField) throws Exception {
		if (pTableIndiceField == null) {
			JExcepcion.SendError("Informacion sobre el campo es nulo");
		}

		if (pTableIndiceField.getFieldName().isEmpty()) {
			JExcepcion.SendError("Nombre del campo es invalido");
		}

		checkForIndexFields();
		this.indexFields.addElement(pTableIndiceField.getFieldName(), pTableIndiceField);
	}

	/**
	 * @return the indexIsPrimaryKey
	 */
	public boolean isIndexIsPrimaryKey() {
		return indexIsPrimaryKey;
	}

	/**
	 * @param indexIsPrimaryKey
	 *          the indexIsPrimaryKey to set
	 */
	public void setIndexIsPrimaryKey(boolean indexIsPrimaryKey) {
		this.indexIsPrimaryKey = indexIsPrimaryKey;
	}

	/**
	 * @return the indexIsClustered
	 */
	public boolean isIndexIsClustered() {
		return indexIsClustered;
	}

	/**
	 * @param indexIsClustered
	 *          the indexIsClustered to set
	 */
	public void setIndexIsClustered(boolean indexIsClustered) {
		this.indexIsClustered = indexIsClustered;
	}

	/**
	 * Indica si el indice es unico
	 * 
	 * @return true si el indice es unico
	 */
	public boolean isIndexIsUnique() {
		return indexIsUnique;
	}

	/**
	 * Setea si el indice es unico o no
	 * 
	 * @param indexIsUnique
	 *          true si el indice es unico.
	 */
	public void setIndexIsUnique(boolean indexIsUnique) {
		this.indexIsUnique = indexIsUnique;
	}

}
