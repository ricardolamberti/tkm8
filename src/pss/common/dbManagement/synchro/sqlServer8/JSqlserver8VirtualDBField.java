/**
 * 
 */
package pss.common.dbManagement.synchro.sqlServer8;

import pss.common.dbManagement.synchro.JDBSqlAction;
import pss.common.dbManagement.synchro.JDBSqlActionList;
import pss.common.dbManagement.synchro.base.JBaseVirtualDBField;
import pss.core.tools.JExcepcion;

/**
 * 
 * 
 */
public class JSqlserver8VirtualDBField extends JBaseVirtualDBField {

	/**
	 * Si necesita actualizarse un campo (generar el alter table alter column) algunas bases tienen
	 * dos instrucciones separadas para cambiar el tipo de dato y otro para cambiar el estado null/no
	 * null del campo. Es por ello que la clase JBaseVirtualDBField puede llamar a los metodos
	 * {@link #generateAlterColumnTypeSQL(String)} y luego
	 * {@link #generateAlterColumnNullPropertySQL(String)} en ese orden. En el caso de SQLServer todo
	 * se resuelve con una sola instruccion con lo cual si se llama a los dos metodos consecutivos
	 * seria un error proque generaria exactamente el mismo codigo SQL con lo cual esta variable se
	 * usa para prevenir dicha eventualidad.
	 */
	protected boolean ifAlterColumnCalled = false;

	/**
	 * Constructor
	 * 
	 * @throws Exception
	 */
	public JSqlserver8VirtualDBField() throws Exception {
		super();
	}

	@Override
	protected boolean isFieldNameIsReserved(String fieldName) throws Exception {
		return false;
	}

	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseVirtualDBField#generateAddColumnSQL()
	 */
	@Override
	protected String generateAddColumnSQL(String tableName) throws Exception {

		String sql;

		sql = "ALTER TABLE " + tableName + " ";
		sql += "ADD ";
		sql += this.getFieldDeclarationSQL();

		return sql;

	}

	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseVirtualDBField#generateAlterColumnSQL()
	 */
	@Override
	protected void generateAlterColumnTypeSQL(String tableName, JDBSqlActionList sqlActionList) throws Exception {

		if (this.ifAlterColumnCalled) {
			return;
		}
		
		String sql = new String("");
		JDBSqlAction sqlAction;
		
		sql = "ALTER TABLE " + tableName + " ";
		sql += "ALTER COLUMN ";
		sql += this.getFieldDeclarationSQL();

		sqlAction = new JDBSqlAction();
		sqlAction.setAction(JDBSqlAction.MODIFY_COLUMN);
		sqlAction.setSql(sql);
		sqlActionList.addAction(sqlAction);			
		
		ifAlterColumnCalled = true;

		return;
	}

	/**
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	@Override
	protected void generateAlterColumnNullPropertySQL(String tableName, JDBSqlActionList sqlActionList) throws Exception {
		this.generateAlterColumnTypeSQL(tableName, sqlActionList);
		return;
	}

	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseVirtualDBField#generateAlterColumnAutonumericSQL(java.lang.String, pss.common.dbManagement.synchro.JDBSqlActionList)
	 */
	@Override
	protected void generateAlterColumnAutonumericSQL(String tableName, JDBSqlActionList sqlActionList) throws Exception {
		// TODO Auto-generated method stub
	}
	
	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseVirtualDBField#generateDropColumnSQL()
	 */
	@Override
	protected String generateDropColumnSQL(String tableName) throws Exception {
		String sql;

		sql = "ALTER TABLE " + tableName + " ";
		sql += "DROP COLUMN ";
		sql += this.fieldName;

		return sql;
	}

	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseVirtualDBField#getFieldDeclarationSQL()
	 */
	@Override
	public String getFieldDeclarationSQL() throws Exception {
		String sql = new String("");

		sql = this.fieldName + " ";
		sql += generateDataTypeDescSQL();

		if (this.fieldIsNullable == false) {
			sql += " NOT ";
		} 			

		sql += " NULL ";
		
		return sql;
	}

	/**
	 * Realiza la conversion del tipo de datos generico al tipo de datos SQL especifico de cada motor
	 * de base de datos.
	 * 
	 * @return tipo de dato SQL
	 * @throws Exception
	 */
	@Override
	protected String generateDataTypeDescSQL() throws Exception {
		String sql = new String("");

		// Proceso los tipos numericos
		if (this.fieldGenericDataType.charAt(0) == JBaseVirtualDBField.C_TIPO_NUMBER || this.fieldGenericDataType.charAt(0) == JBaseVirtualDBField.C_TIPO_AUTONUMERICO) {
			// Formato: NUMERIC(precision,scale) o NUMERIC(precision)
			sql = "NUMERIC (";
			sql += this.fieldLength;
			if (this.fieldScale > 0) {
				sql += ",";
				sql += this.fieldScale;
			}
			sql += ")";

			// TODO - Falta resolver ... ver la siguiente pagina
			// http://technet.microsoft.com/es-es/library/ms186775.aspx
//			if (this.fieldGenericDataType.charAt(0) == JBaseVirtualDBField.C_TIPO_AUTONUMERICO) {
//				sql += " IDENTITY ";
//			}

			return sql;
		}

		// Proceso campo tipo char
		if (this.fieldGenericDataType.charAt(0) == JBaseVirtualDBField.C_TIPO_CHAR) {
			// Description: fixed-length, blank padded
			sql = "CHAR(" + this.fieldLength + ")";
			return sql;
		}

		// Proceso campo tipo varchar
		if (this.fieldGenericDataType.charAt(0) == JBaseVirtualDBField.C_TIPO_VARCHAR) {
			// Description: variable-length with limit
			sql = "VARCHAR(" + this.fieldLength + ")";
			return sql;
		}

		// Proceso campo tipo text
		if (this.fieldGenericDataType.charAt(0) == JBaseVirtualDBField.C_TIPO_TEXT) {
			// Description: variable-length with limit
			return "VARCHAR(MAX)";
		}

		// Proceso campo tipo LOB
		if (this.fieldGenericDataType.charAt(0) == JBaseVirtualDBField.C_TIPO_LOB) {
			// Description: variable-length with limit
			return "VARBINARY(MAX)";
		}

		// Proceso campo tipo Date
		if (this.fieldGenericDataType.charAt(0) == JBaseVirtualDBField.C_TIPO_DATE) {
			// Description: both date and time
			return "DATETIME";
		}

		JExcepcion.SendError("El tipo de dato Generico " + this.fieldGenericDataType + " no puede ser convertido a ningun tipo de datos PostgreSQL8");
		return sql;

	}

}
