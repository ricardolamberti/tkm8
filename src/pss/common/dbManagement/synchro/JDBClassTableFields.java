package  pss.common.dbManagement.synchro;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

/**
 * <p>
 * Representa un grupo de campos que forman una tabla.
 * </p>
 * <p>
 * La informacion de cada campo se almacena en una instancia de la clase
 * {@link pss.common.dbManagement.synchro.JDBClassTableField JDBTableField}
 * </p>
 * 
 */
public class JDBClassTableFields {

	/**
	 * Mapa que contiene los campos de una tabla. Dicho mapa esta indexado por el nombre del campo
	 */
	private JMap<String, JDBClassTableField> fieldsMap = null;

	/**
	 * Constructor
	 * 
	 * @throws Exception
	 */
	public JDBClassTableFields() throws Exception {
	}

	/**
	 * Agrega un nuevo campo.
	 * 
	 * @param dbField
	 *          Objeto de la clase {@link pss.common.dbManagement.synchro.JDBClassTableField JDBTableField}}
	 *          con toda la informacion de cada campo de la tabla
	 * @throws Exception
	 */
	public void addNewField(JDBClassTableField dbField) throws Exception {
		checkForFields();
		fieldsMap.addElement(dbField.getFieldName(), dbField);
	}

	/**
	 * Busca toda la inforamcion de un campo en base a su nombre.
	 * 
	 * @param fieldName
	 *          Nombre del campo que se esta buscando
	 * @return Retorna un objeto del tipo
	 *         {@link pss.common.dbManagement.synchro.JDBClassTableField JDBTableField}. Si no encuentra
	 *         el campo especificado retorna <i><b>null</i></b>
	 */
	public JDBClassTableField BizDBField(String fieldName) {
		if (fieldsMap == null)
			return null;
		if (fieldsMap.containsKey(fieldName) == false)
			return null;
		return fieldsMap.getElement(fieldName);
	}

	/**
	 * Retorna un {@link pss.core.tools.collections.JIterator JIterator} q apunta a los campos guardados en el mapa 
	 * 
	 * @return Retorna 
	 */
	public JIterator<JDBClassTableField> getDBFieldsIterator() {
		checkForFields();
		return this.fieldsMap.getValueIterator();
	}

	/**
	 * Chequea si el mapa fue creado y en caso que no haya sido creado lo crea
	 */
	private void checkForFields() {
		if (this.fieldsMap == null)
			this.fieldsMap = JCollectionFactory.createMap();		
	}
}
