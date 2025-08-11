/**
 * 
 */
package  pss.common.dbManagement.synchro.h2;

import pss.common.dbManagement.synchro.base.JBaseSystemDBField;
import pss.common.dbManagement.synchro.base.JBaseVirtualDBField;
import pss.core.services.records.JRecord;
import pss.core.tools.PssLogger;

/**
 * 
 *
 */
public class JH2SystemDBField extends JBaseSystemDBField {

	/**
	 * @throws Exception
	 */
	public JH2SystemDBField() throws Exception {
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
	 * @return Tipo de dato generico que representa el tipo de dato del campo fisico de la tabla. Si
	 *         no puede determinarse el tipo de dato de la tabla retorna
	 *         {@link JBaseVirtualDBField#C_TIPO_UNKNOWN C_TIPO_UNKNOWN}
	 * 
	 * @throws Exception
	 */
	@Override
	protected String convertSQL2GenericDataType() throws Exception {
		// Analisis del tipo VARCHAR
		if (this.rawSQLDataType.getValue().equalsIgnoreCase("VARCHAR")) {
			if (this.fieldLength.getValue() < 4000) {
				return String.valueOf(JBaseVirtualDBField.C_TIPO_VARCHAR);
			}
			if (this.fieldLength.getValue() < 8000) {
				return String.valueOf(JBaseVirtualDBField.C_TIPO_TEXT);
			}
			return String.valueOf(JBaseVirtualDBField.C_TIPO_LOB);
		}

		// Analisis del tipo NUMERIC
		if (rawSQLDataType.getValue().equalsIgnoreCase("NUMERIC") || 
				rawSQLDataType.getValue().equalsIgnoreCase("INT")) {
			
				
			if (this.isAutonumeric()) {
				return String.valueOf(JBaseVirtualDBField.C_TIPO_AUTONUMERICO); // Es autonumerico
			}

			return String.valueOf(JBaseVirtualDBField.C_TIPO_NUMBER); // No es autonumerico
		}

		// Analisis del tipo TEXT
		if (this.rawSQLDataType.getValue().equalsIgnoreCase("TEXT")) {
			return String.valueOf(JBaseVirtualDBField.C_TIPO_TEXT);
		}

		// Analisis del tipo CHAR
		if (this.rawSQLDataType.getValue().equalsIgnoreCase("CHAR")) {
			return String.valueOf(JBaseVirtualDBField.C_TIPO_CHAR);
		}

		// Analisis del tipo DATETIME
		if (this.rawSQLDataType.getValue().equalsIgnoreCase("DATETIME")) {
			return String.valueOf(JBaseVirtualDBField.C_TIPO_DATE);
		}

		// Analisis del tipo IMAGE
		if (this.rawSQLDataType.getValue().equalsIgnoreCase("IMAGE")) {
			return String.valueOf(JBaseVirtualDBField.C_TIPO_NUMBER);
		}

		PssLogger.logDebug("Tipo de campo H2 " + this.rawSQLDataType.getValue() + " desconocido");
		return String.valueOf(JBaseVirtualDBField.C_TIPO_UNKNOWN);

		// if ( this.pTipo.GetValor().equalsIgnoreCase("UNIQUEIDENTIFIER") )
		// this.pTipoGenerico.SetValor(String.valueOf(C_TIPO_AUTONUMERICO));
		// else
	}

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
		super.createProperties();
		addItem("TABLE_NAME", this.tableName);
		addItem("IS_NULLABLE", this.fieldIsNullAllowed);
		addItem("NUMERIC_SCALE", this.fieldScale);
		addItem("CHARACTER_MAXIMUM_LENGTH", this.fieldLength);
		addItem("NUMERIC_PRECISION", this.fieldPrecision);
	}

	/**
	 * @see
	 * @see pss.core.services.records.JRecord#createFixedProperties()
	 */
	@Override
	public void createFixedProperties() throws Exception {
		super.createFixedProperties();
		addFixedItem(JRecord.FIELD, "TABLE_NAME", "Table Name", true, true, 100);
		addFixedItem(JRecord.FIELD, "IS_NULLABLE", "Es Nulo", true, true, 100);
		addFixedItem(JRecord.FIELD, "NUMERIC_SCALE", "Escala", true, true, 100);
		addFixedItem(JRecord.FIELD, "CHARACTER_MAXIMUM_LENGTH", "Longitud", true, true, 100);
		addFixedItem(JRecord.FIELD, "NUMERIC_PRECISION", "Precision", true, true, 100);
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
