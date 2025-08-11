/**
 * 
 */
package  pss.common.dbManagement.synchro.postgreSQL8;

import pss.common.dbManagement.synchro.base.JBaseSystemDBField;
import pss.common.dbManagement.synchro.base.JBaseVirtualDBField;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.PssLogger;

/**
 * 
 * 
 */
public class JPostgreSQL8SystemDBField extends JBaseSystemDBField {

	/**
	 * Almancena los valores por default que se le ponen al campo de la tabla. Se usa para detectar
	 * los campso autonumericos.
	 */
	JString columnDefault = new JString();

	/**
	 * @throws Exception
	 */
	public JPostgreSQL8SystemDBField() throws Exception {
		// TODO Auto-generated constructor stub
		addFilter("TABLE_SCHEMA", "public");
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
		// if (rawSQLDataType.getValue().equalsIgnoreCase("NUMERIC") || rawSQLDataType.getValue().equalsIgnoreCase("INT8") || rawSQLDataType.getValue().equalsIgnoreCase("INT4")) {
		if (this.isNumber()) {
			if (this.isAutonumeric()) {
				return String.valueOf(JBaseVirtualDBField.C_TIPO_AUTONUMERICO);
			}
			return String.valueOf(JBaseVirtualDBField.C_TIPO_NUMBER);
		}

		if (this.isFloat()) {
			return String.valueOf(JBaseVirtualDBField.C_TIPO_NUMBER);
		}
		
		// Analisis del tipo VARCHAR
		if (this.rawSQLDataType.getValue().equalsIgnoreCase("VARCHAR")) {
				return String.valueOf(JBaseVirtualDBField.C_TIPO_VARCHAR);
		}

		// Analisis del tipo LOB
		if (this.rawSQLDataType.getValue().equalsIgnoreCase("BYTEA")) {
			return String.valueOf(JBaseVirtualDBField.C_TIPO_LOB);
		}

		// Analisis del tipo CHAR
		if (this.rawSQLDataType.getValue().equalsIgnoreCase("CHAR")) {
			return String.valueOf(JBaseVirtualDBField.C_TIPO_CHAR);
		}

		// Analisis del tipo de dato "TIMESTAMP"
		if (this.rawSQLDataType.getValue().equalsIgnoreCase("TIMESTAMP") || this.rawSQLDataType.getValue().equalsIgnoreCase("DATE")) {
			return String.valueOf(JBaseVirtualDBField.C_TIPO_DATE);
		}

		// Analisis del tipo de dato "TIMESTAMP"
		if (this.rawSQLDataType.getValue().equalsIgnoreCase("TEXT")) {
			return String.valueOf(JBaseVirtualDBField.C_TIPO_TEXT);
		}

		if (this.rawSQLDataType.getValue().equalsIgnoreCase("BIGSERIAL")) { // this.isAutonumeric()) {
			return String.valueOf(JBaseVirtualDBField.C_TIPO_AUTONUMERICO);
		}

		PssLogger.logDebug("Tipo de campo Postgres " + this.rawSQLDataType.getValue() + " desconocido");
		return String.valueOf(JBaseVirtualDBField.C_TIPO_UNKNOWN);
	} // end ObtenerTipoGenerico

	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseSystemDBField#isAutonumeric()
	 */
	@Override
	protected boolean isAutonumeric() throws Exception {
		if (this.isNumber()) {
			if (this.columnDefault.isNotNull() && this.columnDefault.toString().substring(0, 7).equalsIgnoreCase("nextval")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseSystemDBField#createProperties()
	 */
	@Override
	public void createProperties() throws Exception {
		addItem("TABLE_NAME", this.tableName);
		addItem("COLUMN_NAME", this.fieldName);
		addItem("IS_NULLABLE", this.fieldIsNullAllowed);
		addItem("UDT_NAME", this.rawSQLDataType);
		addItem("CHARACTER_MAXIMUM_LENGTH", this.fieldLength);
		addItem("NUMERIC_PRECISION", this.fieldPrecision);
		addItem("NUMERIC_SCALE", this.fieldScale);
		addItem("COLUMN_DEFAULT", this.columnDefault);

	}

	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseSystemDBField#createFixedProperties()
	 */
	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(JRecord.KEY, "TABLE_NAME", "Nombre Tabla", true, true, 100);
		addFixedItem(JRecord.KEY, "COLUMN_NAME", "Nombre Columna", true, true, 100);
		addFixedItem(JRecord.FIELD, "UDT_NAME", "Tipo Dato", true, true, 100);
		addFixedItem(JRecord.FIELD, "CHARACTER_MAXIMUM_LENGTH", "Longitud", true, true, 100);
		addFixedItem(JRecord.FIELD, "NUMERIC_PRECISION", "Precision", true, true, 100);
		addFixedItem(JRecord.FIELD, "NUMERIC_SCALE", "Escala", true, true, 100);
		addFixedItem(JRecord.FIELD, "IS_NULLABLE", "Permite Nulos", true, true, 100);
		addFixedItem(JRecord.FIELD, "COLUMN_DEFAULT", "Valor por defecto", true, true, 100);
	}

	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseSystemDBField#GetTable()
	 */
	@Override
	public String GetTable() {
		return "INFORMATION_SCHEMA.COLUMNS";
	}

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
		if ((this.rawSQLDataType.getValue().equalsIgnoreCase(("TIMESTAMP")) || this.rawSQLDataType.getValue().equalsIgnoreCase("DATE"))) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isFloat() throws Exception {
		if (rawSQLDataType.getValue().equalsIgnoreCase("NUMERIC") && this.fieldScale.getValue() > 0)
			return true;
		return false;
	}

	@Override
	public boolean isNumber() throws Exception {
		if ((rawSQLDataType.getValue().equalsIgnoreCase("NUMERIC") || rawSQLDataType.getValue().equalsIgnoreCase("INT8") || rawSQLDataType.getValue().equalsIgnoreCase("INT4")) && this.fieldScale.getValue() == 0) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isString() throws Exception {
		if (this.rawSQLDataType.getValue().equalsIgnoreCase("VARCHAR") || 
				this.rawSQLDataType.getValue().equalsIgnoreCase("CHAR") || 
				this.rawSQLDataType.getValue().equalsIgnoreCase("BPCHAR") || 
				this.rawSQLDataType.getValue().equalsIgnoreCase("TEXT") || 
				this.rawSQLDataType.getValue().equalsIgnoreCase("BYTEA")) {
			return true;
		}
		return false;
	}

	public void applyDefaultFilters() throws Exception {
		addFilter("TABLE_SCHEMA", "public");
	}
	
	
	

}
