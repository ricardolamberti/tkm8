/**
 * 
 */
package  pss.common.dbManagement.synchro.base;

import pss.common.dbManagement.synchro.JDBClassTableField;
import pss.common.dbManagement.synchro.JDBClassTableFields;
import pss.common.dbManagement.synchro.JDBSqlAction;
import pss.common.dbManagement.synchro.JDBSqlActionList;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

/**
 * 
 * 
 */
public abstract class JBaseVirtualDBFields {
	/**
	 * 
	 */
	protected JMap<String, JBaseVirtualDBField> oFields = null;

	/**
	 * 
	 */
	String tableName;

	/**
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Class VirtualClass() throws Exception {
		String sClassName = JBDatos.getBaseMaster().getVirtualDBFieldsImpl(); // .getMetadataColumnsImpl();
		return Class.forName(sClassName);
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public static JBaseVirtualDBFields VirtualCreate() throws Exception {
		JBaseVirtualDBFields oCol = (JBaseVirtualDBFields) JBaseVirtualDBFields.VirtualClass().newInstance();
		return oCol;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public abstract String generateFieldsDeclarationSQL() throws Exception;

	/**
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	public JBaseVirtualDBField getFieldByName(String fieldName) throws Exception {
		if (this.oFields == null || this.oFields.isEmpty()) {
			return null;
		}

		if (fieldName == null || fieldName.isEmpty()) {
			return null;
		}

		if (this.oFields.containsKey(fieldName) == false) {
			return null;
		}

		JBaseVirtualDBField virtualDBField;

		virtualDBField = this.oFields.getElement(fieldName);
		return virtualDBField;
	}

	/**
	 * Llena los datos del campo en base a lo leido de las tablas de sistema del motor de la base de
	 * datos
	 * 
	 * @param pSystemDBFields
	 * @throws Exception
	 */
	public void processSystemDBField(JBaseSystemDBFields pSystemDBFields) throws Exception {
		pSystemDBFields.firstRecord();
		JBaseSystemDBField systemDBField = null;
		JBaseVirtualDBField virtualDBField;

		this.checkFields();

		try {
			while (pSystemDBFields.nextRecord()) {
				systemDBField = pSystemDBFields.getRecord();
				virtualDBField = JBaseVirtualDBField.VirtualCreate();
				virtualDBField.processSystemDBField(systemDBField);
				this.oFields.addElement(virtualDBField.getFieldName(), virtualDBField);
			} // end while
		} catch (Exception e) {
			PssLogger.logError(e, "Error procesando campo :" + systemDBField.getFieldName());
		}
	}

	/**
	 * Llena los datos del campo en base a lo leido de la clase que representa un campo
	 * 
	 * @param pClassTableFields
	 * @throws Exception
	 */
	public void processClassFields(JDBClassTableFields pClassTableFields) throws Exception {
		JIterator<JDBClassTableField> classTableFieldsIterator;
		JDBClassTableField classTableField = null;
		classTableFieldsIterator = pClassTableFields.getDBFieldsIterator();
		JBaseVirtualDBField virtualDBField;
		checkFields();

		try {
			while (classTableFieldsIterator.hasMoreElements()) {
				classTableField = classTableFieldsIterator.nextElement();
				virtualDBField = JBaseVirtualDBField.VirtualCreate();
				virtualDBField.processClassTable(classTableField);
				this.oFields.addElement(virtualDBField.getFieldName(), virtualDBField);
			} // end while
		} catch (Exception e) {
			PssLogger.logError(e, "Error procesando campo :" + classTableField.getFieldName());
		}

	}

	/**
	 * @param slaveVirtualDBFields
	 * @param sqlActionList
	 * @throws Exception
	 */
	public void getAlterTableActionList(JBaseVirtualDBFields slaveVirtualDBFields, JDBSqlActionList sqlActionList) throws Exception {
		JBaseVirtualDBField masterVirtualDBField;
		JBaseVirtualDBField slaveVirtualDBField;
		JIterator<JBaseVirtualDBField> virtualDBFieldIterator;
		JDBSqlAction sqlAction;
		String sql;
		String sqlActionReason;

		if (this.oFields == null || this.oFields.isEmpty()) {
			JExcepcion.SendError("La virtual table actual no tiene campos definidos");
		}

		virtualDBFieldIterator = this.oFields.getValueIterator();

		// Primero comparo los campos que estan en la tabla Master con los campos que vienen en la tabla
		// Slave
		while (virtualDBFieldIterator.hasMoreElements()) {
			masterVirtualDBField = virtualDBFieldIterator.nextElement();
			sqlAction = null;

			slaveVirtualDBField = slaveVirtualDBFields.getFieldByName(masterVirtualDBField.getFieldName());

			if (slaveVirtualDBField == null) {
				sqlAction = new JDBSqlAction();
				sql = masterVirtualDBField.generateAddColumnSQL(this.tableName);
				sqlAction.setAction(JDBSqlAction.ADD_COLUMN);
				sqlAction.setSql(sql);
				sqlActionList.addAction(sqlAction);
				sqlActionReason = "El campo [" + masterVirtualDBField.getFieldName() + "] no existe en la actual tabla [" + this.tableName + "]";
				PssLogger.logDebug(sqlActionReason);
				sqlActionList.addSqlActionReason(sqlActionReason);
				continue;
			}

			masterVirtualDBField.getAlterColumnAction(this.tableName, slaveVirtualDBField, sqlActionList);
		} // end while

		// Ahora busco los campos que estan en la tabla slave q no estan en la tabla Master para
		// borrarlos
		if (slaveVirtualDBFields.oFields != null && slaveVirtualDBFields.oFields.isEmpty() == false) {
			virtualDBFieldIterator = slaveVirtualDBFields.oFields.getValueIterator();

			while (virtualDBFieldIterator.hasMoreElements()) {
				slaveVirtualDBField = virtualDBFieldIterator.nextElement();

				if (this.oFields.containsKey(slaveVirtualDBField.getFieldName()) == false) {
					sqlAction = new JDBSqlAction();
					sqlAction.setAction(JDBSqlAction.DROP_COLUMN);
					sqlAction.setSql(slaveVirtualDBField.generateDropColumnSQL(this.tableName));
					sqlActionList.addAction(sqlAction);
					sqlActionReason = "El campo [" + slaveVirtualDBField.getFieldName() + "] no existe en la clase actual";
					PssLogger.logDebug(sqlActionReason);
					sqlActionList.addSqlActionReason(sqlActionReason);					
				}
			} // end while
		}
	}

	/**
	 * @throws Exception
	 */
	protected void checkFields() throws Exception {
		if (oFields == null) {
			oFields = JCollectionFactory.createMap();
		}
	}

	/**
	 * @param tableName
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return
	 */
	public JIterator<JBaseVirtualDBField> getDBFieldsIterator() {
		return this.oFields.getValueIterator();
	}

}
