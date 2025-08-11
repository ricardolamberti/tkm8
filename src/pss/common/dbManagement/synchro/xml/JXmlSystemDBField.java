/**
 * 
 */
package  pss.common.dbManagement.synchro.xml;

import pss.common.dbManagement.synchro.base.JBaseSystemDBField;
import pss.common.dbManagement.synchro.base.JBaseVirtualDBField;
import pss.core.services.records.JRecord;
import pss.core.tools.PssLogger;

/**
 * 
 *
 */
public class JXmlSystemDBField extends JBaseSystemDBField {

	/**
	 * @throws Exception
	 */
	public JXmlSystemDBField() throws Exception {
	}

	/**
	 * Evalua si un campo puede aceptar valores en null o no
	 * 
	 * @return True si puede aceptar valores null
	 * @throws Exception
	 */
	@Override
	public boolean IsNullable() throws Exception {
		return this.fieldIsNullAllowed.getValue().equalsIgnoreCase("YES");
	}

	/**
	 * Convierte el tipo sql caracteristico de cada motor de base de datos al tipo generico. La
	 * implementacion depende de cada motor de base de datos
	 * 
	 * @throws Exception
	 */
	@Override
	public String convertSQL2GenericDataType() throws Exception {
		// Analisis del tipo NUMERIC
		if (rawSQLDataType.getValue().equalsIgnoreCase("NUMERIC") || 
				rawSQLDataType.getValue().equalsIgnoreCase("INT8")) {
			return String.valueOf(JBaseVirtualDBField.C_TIPO_NUMBER);
		}

		// Analisis del tipo VARCHAR
		if (this.rawSQLDataType.getValue().equalsIgnoreCase("VARCHAR")) {
			if (this.fieldLength.getValue() < 4000) {
				return String.valueOf(JBaseVirtualDBField.C_TIPO_VARCHAR);
			}
			return String.valueOf(JBaseVirtualDBField.C_TIPO_TEXT);
		}

		// Analisis del tipo LOB
		if (this.rawSQLDataType.getValue().equalsIgnoreCase(("BYTEA"))) {
			return String.valueOf(JBaseVirtualDBField.C_TIPO_LOB);
		}
		
		// Analisis del tipo CHAR
		if (this.rawSQLDataType.getValue().equalsIgnoreCase(("CHAR"))) {
			return String.valueOf(JBaseVirtualDBField.C_TIPO_CHAR);
		}
		
		// Analisis del tipo de dato "TIMESTAMP"
		if (this.rawSQLDataType.getValue().equalsIgnoreCase(("TIMESTAMP")) ||
				this.rawSQLDataType.getValue().equalsIgnoreCase("DATE")) {
			return String.valueOf(JBaseVirtualDBField.C_TIPO_DATE);
		}
		
		// Analisis del tipo de dato "TIMESTAMP"
		if (this.rawSQLDataType.getValue().equalsIgnoreCase(("TEXT"))) {
			return String.valueOf(JBaseVirtualDBField.C_TIPO_TEXT);
		}		
		
		if (this.isAutonumeric()) { 
			return String.valueOf(JBaseVirtualDBField.C_TIPO_AUTONUMERICO);
		}
		
		PssLogger.logDebug("Tipo de campo Postgres " + this.rawSQLDataType.getValue() + " desconocido");
		return String.valueOf(JBaseVirtualDBField.C_TIPO_UNKNOWN);
	} // end ObtenerTipoGenerico

	/**
	 * @see pss.pss.common.dbManagement.synchro.base.JBaseSystemDBField#isAutonumeric()
	 */
	@Override
	protected boolean isAutonumeric() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @see pss.pss.common.dbManagement.synchro.base.JBaseSystemDBField#createProperties()
	 */
	@Override
	public void createProperties() throws Exception {
		addItem("TABLE_NAME", this.tableName);
		addItem("COLUMN_NAME", this.fieldName);
		addItem("IS_NULLABLE", fieldIsNullAllowed);
		addItem("SQL_DATA_TYPE", rawSQLDataType);
	//	addItem("COLUMN_SIZE", this.fieldLength);
	}
	
	/**
	 * @see pss.pss.common.dbManagement.synchro.base.JBaseSystemDBField#createFixedProperties()
	 */
	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(JRecord.KEY, "TABLE_NAME", "Nombre Tabla", true, true, 100);
		addFixedItem(JRecord.KEY, "COLUMN_NAME", "Nombre Columna", true, true, 100);
		addFixedItem(JRecord.FIELD, "SQL_DATA_TYPE", "Longitud", true, true, 100);
		addFixedItem(JRecord.FIELD, "IS_NULLABLE", "Longitud", true, true, 100);
//		addFixedItem(JRecord.FIELD, "COLUMN_SIZE", "Precision", true, true, 100);
	}

	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseSystemDBField#GetTable()
	 */

	
	/**
	 * Sobreescribe la el metodo para indicar que la tabla representada por esta clase es del tipo de
	 * tabla de sistema
	 * 
	 * @return true
	 */
	@Override
	public boolean isSystemTable() {
		return true;
	}

	@Override
	public boolean isDate() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFloat() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isNumber() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isString() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}	
}
