/**
 * 
 */
package  pss.common.dbManagement.synchro.oracle9;

import pss.common.dbManagement.synchro.base.JBaseSystemDBField;
import pss.common.dbManagement.synchro.base.JBaseVirtualDBField;
import pss.core.services.records.JRecord;
import pss.core.tools.PssLogger;

/**
 * 
 *
 */
public class JOracle9SystemDBField extends JBaseSystemDBField {

	/**
	 * Constructor
	 * @throws Exception
	 */
	public JOracle9SystemDBField() throws Exception {
		super();
	}

	/**
	 * Evalua si un campo puede aceptar valores en null o no
	 * 
	 * @return True si puede aceptar valores null
	 * @throws Exception
	 */
	@Override
	public boolean IsNullable() throws Exception {
		return this.fieldIsNullAllowed.getValue().equalsIgnoreCase("Y");
	}	
	
	/**
	 * Convierte el tipo sql caracteristico de cada motor de base de datos al tipo generico. La
	 * implementacion depende de cada motor de base de datos
	 * 
	 * @throws Exception
	 */
	@Override
	public String convertSQL2GenericDataType() throws Exception {
		// Analisis del tipo VARCHAR2
		if (this.rawSQLDataType.getValue().equalsIgnoreCase("VARCHAR2")) {
			if (this.fieldLength.getValue() < 4000) {
				return String.valueOf(JBaseVirtualDBField.C_TIPO_VARCHAR);
			}
			if (this.fieldLength.getValue() < 8000) {
				return String.valueOf(JBaseVirtualDBField.C_TIPO_TEXT);
			}
			return String.valueOf(JBaseVirtualDBField.C_TIPO_LOB);
		}
		
		// Analisis del tipo NUMBER
		if (rawSQLDataType.getValue().equalsIgnoreCase("NUMBER")) {
			return String.valueOf(JBaseVirtualDBField.C_TIPO_NUMBER);
		}		

		// Analisis del tipo CHAR
		if (this.rawSQLDataType.getValue().equalsIgnoreCase("CHAR")) {
			return String.valueOf(JBaseVirtualDBField.C_TIPO_CHAR);
		}	

		// Analisis del tipo DATE
		if (this.rawSQLDataType.getValue().equalsIgnoreCase("DATE")) {
			return String.valueOf(JBaseVirtualDBField.C_TIPO_DATE);
		}

		// Analisis del los tipos LOB
		if (this.rawSQLDataType.getValue().equalsIgnoreCase("BLOB") || this.rawSQLDataType.getValue().equalsIgnoreCase("CLOB")) {
			return String.valueOf(JBaseVirtualDBField.C_TIPO_LOB);
		}		
		
		// TODO - Esto esta pendiente de resolver
		if (this.isAutonumeric()) {
			return String.valueOf(JBaseVirtualDBField.C_TIPO_AUTONUMERICO);
		}

		PssLogger.logDebug("Tipo de campo Oracle " + this.rawSQLDataType.getValue() + " desconocido");
		return String.valueOf(JBaseVirtualDBField.C_TIPO_UNKNOWN);
	
	} // end ObtenerTipoGenerico


	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseSystemDBField#createProperties()
	 */
	@Override
	public void createProperties() throws Exception {
		super.createProperties();
		addItem("TABLE_NAME", this.tableName);
		addItem("NULLABLE", this.fieldIsNullAllowed);
		addItem("DATA_SCALE", this.fieldScale);
		addItem("DATA_LENGTH", this.fieldLength);
		addItem("DATA_PRECISION", this.fieldPrecision);
	}

	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseSystemDBField#createFixedProperties()
	 */
	@Override
	public void createFixedProperties() throws Exception {
		super.createFixedProperties();
		addFixedItem(JRecord.FIELD, "TABLE_NAME", "Table Name", true, true, 100);
		addFixedItem(JRecord.FIELD, "NULLABLE", "Es Nulo", true, true, 100);
		addFixedItem(JRecord.FIELD, "DATA_SCALE", "Escala", true, true, 100);
		addFixedItem(JRecord.FIELD, "DATA_LENGTH", "Longitud", true, true, 100);
		addFixedItem(JRecord.FIELD, "DATA_PRECISION", "Precision", true, true, 100);
	}

	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseSystemDBField#GetTable()
	 */
	@Override
	public String GetTable() {
		return "ALL_TAB_COLUMNS";
	}	

	
	
	
	/**
	 * Indica si un campo es autonumerico o no
	 * 
	 * @return true si es autonumerico
	 * @throws Exception
	 */
	protected boolean isAutonumeric() throws Exception {
		// TODO - Pendiente de desarrollo
//		String sTabla = tableName.getValue();
//		String sql = "SELECT SEQUENCE_NAME FROM USER_SEQUENCES WHERE SEQUENCE_NAME = '" + sTabla + "_" + fieldName.getValue() + "'";
//		JBaseRegistro oReg = JBaseRegistro.VirtualCreate();
//		oReg.ExecuteQuery(sql);
//		if (oReg.next()) {
//			oReg.close();
//			return true;
//		}
//		oReg.close();		
		return false;
	}
	
	/**
	 * Sobreescribe la el metodo para indicar que la tabla representada por esta clase es del tipo de tabla de sistema
	 * 
	 * @return true	  
	 */
	@Override
	public boolean isSystemTable() {
		return true;
	}

	@Override
	public boolean isDate() throws Exception {
		if (this.rawSQLDataType.getValue().equalsIgnoreCase("DATE")) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isFloat() throws Exception {
		if (rawSQLDataType.getValue().equalsIgnoreCase("NUMBER") && this.fieldScale.getValue()>0) 
			return true;
	// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isNumber() throws Exception {
		// Analisis del tipo NUMBER
		if (rawSQLDataType.getValue().equalsIgnoreCase("NUMBER") && this.fieldScale.getValue()<=0) 
			return true;
	// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isString() throws Exception {
		// TODO Auto-generated method stub
		if (this.rawSQLDataType.getValue().equalsIgnoreCase("VARCHAR2")) 
			return true;
			
		return false;
	}			
}
