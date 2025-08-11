/**
 * 
 */
package  pss.common.dbManagement.synchro.informix;

import pss.common.dbManagement.synchro.JDBSqlAction;
import pss.common.dbManagement.synchro.JDBSqlActionList;
import pss.common.dbManagement.synchro.base.JBaseVirtualDBField;
import pss.core.tools.JExcepcion;

/**
 * 
 * 
 */
public class JInformixVirtualDBField extends JBaseVirtualDBField {

	/**
	 * Para los campos autonumericos, el tipo de dato es dependiente si el campo se esta agregando a
	 * la tabla o si se esta ejecutando un alter table. Esta variable ayuda a determinar si estoy
	 * agregando un campo o lo estoy modificando para resolver esos casos.
	 */
	protected boolean isAddingNewColumn = true;

	/**
	 * @throws Exception
	 */
	public JInformixVirtualDBField() throws Exception {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Chequea si el nombre del campo es reservado para el motor de base de datos configurado
	 * 
	 * @param fieldName
	 * @return True si el nombre del campo es reservado
	 * @throws Exception
	 */
	@Override
	protected boolean isFieldNameIsReserved(String fieldName) throws Exception {
		if (fieldName.compareToIgnoreCase("offset") == 0)
			return true;
		return false;
	}

	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseVirtualDBField#generateAddColumnSQL()
	 */
	@Override
	protected String generateAddColumnSQL(String tableName) throws Exception {
		String sql;
		
		isAddingNewColumn = true;
		
		sql = "ALTER TABLE " + tableName + " ";
		sql += "ADD COLUMN ";
		sql += this.getFieldDeclarationSQL();

		return sql;

	}

	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseVirtualDBField#generateAlterColumnSQL()
	 */
	@Override
	protected void generateAlterColumnTypeSQL(String tableName, JDBSqlActionList sqlActionList) throws Exception {
		String sql;
		JDBSqlAction sqlAction;

		isAddingNewColumn = false;
		
		sql = "ALTER TABLE " + tableName + " ";
		sql += "ALTER COLUMN ";
		sql += this.fieldName + " TYPE ";
		sql += generateDataTypeDescSQL();

		sqlAction = new JDBSqlAction();
		sqlAction.setAction(JDBSqlAction.MODIFY_COLUMN);
		sqlAction.setSql(sql);
		sqlActionList.addAction(sqlAction);

		// TODO - Faltan los pre y los postsql
		// TODO - Falta evaluar si la transformacion es valida para el motor de base de datos no
		// hago
		// nada, sino deberia hacer un proceso automatico de conversion de datos.

		return;
	}

	/**
	 * @param tableName
	 * @throws Exception
	 */
	@Override
	protected void generateAlterColumnNullPropertySQL(String tableName, JDBSqlActionList sqlActionList) throws Exception {
		String sql = new String("");
		JDBSqlAction sqlAction;

		sql = "ALTER TABLE " + tableName + " ";
		sql += "ALTER COLUMN ";
		sql += this.fieldName;

		if (this.fieldIsNullable) {
			sql += " DROP ";
		} else {
			sql += " SET ";
		}

		sql += "NOT NULL";

		sqlAction = new JDBSqlAction();
		sqlAction.setAction(JDBSqlAction.MODIFY_COLUMN);
		sqlAction.setSql(sql);
		sqlActionList.addAction(sqlAction);

		return;
	}

	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseVirtualDBField#generateAlterColumnAutonumericSQL(java.lang.String,
	 *      pss.common.dbManagement.synchro.JDBSqlActionList)
	 */
	@Override
	protected void generateAlterColumnAutonumericSQL(String tableName, JDBSqlActionList sqlActionList) throws Exception {
		String sql;
		String seqName;
		JDBSqlAction sqlAction;
		seqName = tableName + "_SEQ";

		sqlAction = new JDBSqlAction();
		sqlAction.setAction(JDBSqlAction.MODIFY_COLUMN);

		sql = "ALTER TABLE " + tableName;
		sql += " ALTER COLUMN " + this.fieldName;
		if (this.fieldIsAutonumeric) {
			sql += " SET DEFAULT nextval('" + seqName + "')";
		} else {
			sql += " DROP DEFAULT";
		}

		sqlAction.setSql(sql);

		if (this.fieldIsAutonumeric) {
			sql = "CREATE SEQUENCE " + seqName;
			sqlAction.setPreSql(sql);

			sql = "ALTER SEQUENCE " + seqName;
			sql += " OWNED BY ";
			sql += tableName + "." + this.fieldName;
			sqlAction.setPostSql(sql);
		}

		sqlActionList.addAction(sqlAction);
		return;
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
			sql += " NOT NULL ";
		}

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
		if (this.fieldGenericDataType.charAt(0) == JBaseVirtualDBField.C_TIPO_NUMBER) {
			// Storage Size: variable
			// Description: user-specified precision, exact
			// Range: no limit
			// Formato: NUMERIC(precision,scale) o NUMERIC(precision)
			sql = "NUMERIC(";
			sql += this.fieldLength;
			if (this.fieldScale > 0) {
				sql += ",";
				sql += this.fieldScale;
			}
			sql += ")";
			return sql;
		}

		// Proceso campo autonumerico
		if (this.fieldGenericDataType.charAt(0) == JBaseVirtualDBField.C_TIPO_AUTONUMERICO) {
			if (isAddingNewColumn) {
				// Storage Size: 8 bytes
				// Description: large autoincrementing integer
				// Range: 1 to 9223372036854775807
				return "BIGSERIAL";
			} else {
				return "INTEGER";
			}
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
			return "TEXT";
		}

		// Proceso campo tipo LOB
		if (this.fieldGenericDataType.charAt(0) == JBaseVirtualDBField.C_TIPO_LOB) {
			// Description: variable-length with limit
			return "BYTEA";
		}

		// Proceso campo tipo Date
		if (this.fieldGenericDataType.charAt(0) == JBaseVirtualDBField.C_TIPO_DATE) {
			// Storage: Size 8 bytes
			// Description: both date and time
			// Low Value: 4713 BC
			// High Value: 5874897 AD
			// Resolution: 1 microsecond / 14 digits
			return "TIMESTAMP";
		}

		JExcepcion.SendError("El tipo de dato Generico " + this.fieldGenericDataType + " no puede ser convertido a ningun tipo de datos PostgreSQL8");
		return sql;

	}

}
