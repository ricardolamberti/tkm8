package  pss.common.dbManagement.synchro.base;

import pss.core.data.interfaces.connections.JBDatos;
import pss.core.services.records.JRecords;

/**
 * Clase que representa un conjunto de campos de una tabla fisica de la base de datos
 *
 */
public class JBaseSystemDBFields extends JRecords<JBaseSystemDBField> {

	/**
	 * Constructor
	 * @throws Exception
	 */
	public JBaseSystemDBFields() throws Exception {
	}

	/**
	 * @see pss.core.services.records.JRecords#getBasedClass() JRecords.getBasedClass()
	 */
	@Override
	public Class<? extends JBaseSystemDBField> getBasedClass() {
		return JBaseSystemDBField.class;
	}

	/**
	 * @see pss.core.services.records.JRecords#GetTable()
	 */
	@Override
	public String GetTable() {
		return "";
	}

	/** 
	 * Lee todos los campos de una tabla determinada
	 * 
	 * @see pss.core.services.records.JRecords#readAll() JRecords.readAll()
	 */
	@Override
	public boolean readAll() throws Exception {
		this.endStatic();
		super.readAll();
		this.toStatic();
		this.setStatic(true);
		return true;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Class VirtualClass() throws Exception {
		// TODO - Cambiar getMetadataColumnsImpl x getMetadataSystemFieldsImpl		
		// String sClassName=JBDatos.getBaseMaster().getMetadataColumnsImpl();
		String sClassName=JBDatos.getBaseMaster().getSystemDBFieldsImpl();
		return Class.forName(sClassName);
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public static JBaseSystemDBFields VirtualCreate() throws Exception {
		JBaseSystemDBFields oCol=(JBaseSystemDBFields) JBaseSystemDBFields.VirtualClass().newInstance();
		return oCol;
	}

	/**
	 * @param fieldName
	 * @return
	 * @throws Exception 
	 */
	public JBaseSystemDBField getFieldByName(String fieldName) throws Exception {
		JBaseSystemDBField oField = null;

		this.firstRecord();
		while (this.nextRecord()) {
			oField = this.getRecord();
			
			if (oField.getFieldName().equalsIgnoreCase(fieldName) ) {
					return oField;
			}
			
		} // end while
	
		return null;
	}
	public void applyDefaultFilters() throws Exception {
	}
	
}
