package  pss.common.dbManagement.synchro.base;

import pss.common.dbManagement.synchro.JDBClassTableField;
import pss.common.dbManagement.synchro.JDBSqlActionList;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.services.records.JProperty;
import pss.core.tools.PssLogger;

/**
 * <p>
 * Representa un campo de una tabla en forma virtual. Esta clase representa un campo generico tanto
 * sea de una tabla real como de una clase.
 * </p>
 * <p>
 * Ademas, a traves de metodos abstractos presenta el template que resuelve el armado del SQL que
 * maneja el campo actual para cada implementacion del motor de la base de datos
 * </p>
 * 
 */
public abstract class JBaseVirtualDBField {
	/**
	 * ID tipo generico NUMBER
	 */
	public final static char C_TIPO_NUMBER = 'N';
	/**
	 * ID tipo generico CHAR
	 */
	public final static char C_TIPO_CHAR = 'C';
	/**
	 * ID tipo generico VARCHAR
	 */
	public final static char C_TIPO_VARCHAR = 'V';
	/**
	 * ID tipo generico DATE
	 */
	public final static char C_TIPO_DATE = 'D';
	/**
	 * ID tipo generico TEXT
	 */
	public final static char C_TIPO_TEXT = 'T';
	/**
	 * ID tipo generico AUTONUMERICO
	 */
	public final static char C_TIPO_AUTONUMERICO = 'A';
	/**
	 * ID tipo generico DESCONOCIDO
	 */
	public final static char C_TIPO_UNKNOWN = 'X';
	/**
	 * ID tipo generico LOB
	 */
	public final static char C_TIPO_LOB = 'L';

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	/**
	 * Nombre de la columna
	 */
	protected String fieldName = null;

	/**
	 * Logitud de los campos tipos caracter (CHAR / VARCHAR)
	 */
	protected int fieldLength = 0;

	/**
	 * Cantidad de digitos decimales del numero
	 */
	protected int fieldScale = 0;

	/**
	 * TRUE si el campo acepta valores nulos
	 */
	protected boolean fieldIsNullable = false;

	/**
	 * TRUE si el campo es autonumerico
	 */
	protected boolean fieldIsAutonumeric = false;

	/**
	 * Tipo de campo, Almacena uno de los siguientes valores
	 * <li>{@link pss.core.services.records.JProperty.TIPO_CAMPO JProperty.TIPO_CAMPO}</li>
	 * <li>{@link pss.core.services.records.JProperty.TIPO_KEY JProperty.TIPO_KEY}</li>
	 */
	protected String fieldType = new String("");

	/**
	 * Almacena el tipo generico que unifica los diferentes tipos de datos implementados por cada
	 * motor de base de datos
	 */
	/*
	 * protected JString fieldGenericDataType = new JString() { @Override public void preset() throws
	 * Exception { convertSQL2GenericDataType(); } };
	 */
	protected String fieldGenericDataType = String.valueOf(C_TIPO_UNKNOWN);

	/**
	 * Constructor
	 * 
	 * @throws Exception
	 */
	public JBaseVirtualDBField() throws Exception {
	}

	/**
	 * Retorna la implementacion que corresponde al motor de la base de datos instalado de esta clase.
	 * 
	 * @return La implementacion que corresponde al motor de la base de datos instalado
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Class<JBaseVirtualDBField> VirtualClass() throws Exception {
		String sClassName = JBDatos.getBaseMaster().getVirtualDBFieldImpl(); // .getMetadataColumnImpl();
		return (Class<JBaseVirtualDBField>) Class.forName(sClassName);
	}

	/**
	 * Retorna una instancia de la clase que guarda la representacion de una columna
	 * 
	 * @return Una instancia de la clase que lee las columnas de las tablas de sistema del motor de la
	 *         base de datos
	 * @throws Exception
	 */
	public static JBaseVirtualDBField VirtualCreate() throws Exception {
		JBaseVirtualDBField virtualDBField = JBaseVirtualDBField.VirtualClass().newInstance();
		return virtualDBField;
	}

	/**
	 * Chequea si el nombre del campo es reservado para el motor de base de datos configurado
	 * 
	 * @param fieldName
	 * @return True si el nombre del campo es reservado
	 * @throws Exception
	 */
	protected abstract boolean isFieldNameIsReserved(String fieldName) throws Exception;

	/**
	 * @return
	 * @throws Exception
	 */
	public abstract String getFieldDeclarationSQL() throws Exception;

	/**
	 * @return
	 * @throws Exception
	 */
	protected abstract String generateDataTypeDescSQL() throws Exception;

	/**
	 * @return
	 * @throws Exception
	 */
	protected abstract String generateAddColumnSQL(String tableName) throws Exception;

	/**
	 * @return
	 * @throws Exception
	 */
	protected abstract void generateAlterColumnTypeSQL(String tableName, JDBSqlActionList sqlActionList) throws Exception;

	/**
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	protected abstract void generateAlterColumnNullPropertySQL(String tableName, JDBSqlActionList sqlActionList) throws Exception;

	/**
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	protected abstract void generateAlterColumnAutonumericSQL(String tableName, JDBSqlActionList sqlActionList) throws Exception;

	/**
	 * @return
	 * @throws Exception
	 */
	protected abstract String generateDropColumnSQL(String tableName) throws Exception;

	/**
	 * Retorna el nombre del campo
	 * 
	 * @return Nombre del campo. Si el nombre del campo no fue seteado retorna un String vacio. Nunca
	 *         retorna null;
	 */
	public String getFieldName() {
		if (fieldName == null) {
			return "";
		}
		return fieldName;
	}

	/**
	 * Retorna la longitud del campo
	 * 
	 * @return Longitud del campo
	 */
	public int getFieldLength() {
		return fieldLength;
	}

	/**
	 * Retorna los decimales de un campo numerico
	 * 
	 * @return Decimales de un campo numerico, si el campo no es numerico retorna 0
	 */
	public int getFieldScale() {
		return fieldScale;
	}

	/**
	 * Evalua si el campo permite valores nulos
	 * 
	 * @return True si el campo admite valores nulos
	 */
	public boolean isFieldIsNullable() {
		return fieldIsNullable;
	}

	/**
	 * Retorna el valor del campo generico
	 * 
	 * @return the fieldGenericDataType
	 */
	public String getFieldGenericDataType() {
		return fieldGenericDataType;
	}

	/**
	 * Evalua si el campo representado es tipo String o no.
	 * 
	 * @return True si el campo representa un campo tipo String
	 * @throws Exception
	 */
	public boolean isString() throws Exception {
		if (this.fieldGenericDataType.equalsIgnoreCase(String.valueOf(C_TIPO_CHAR))) {
			return true;
		}
		// if (this.fieldGenericDataType.toString().equalsIgnoreCase(String.valueOf(C_TIPO_LOB))) {
		// return true;
		// }
		// if (this.fieldGenericDataType.toString().equalsIgnoreCase(String.valueOf(C_TIPO_TEXT))) {
		// return true;
		// }

		if (this.fieldGenericDataType.toString().equalsIgnoreCase(String.valueOf(C_TIPO_VARCHAR))) {
			return true;
		}
		return false;
	}

	/**
	 * Evalua si el campo representado es tipo Date o no.
	 * 
	 * @return True si el campo representa un campo tipo Date
	 * @throws Exception
	 */
	public boolean isDate() throws Exception {
		if (this.fieldGenericDataType.toString().equalsIgnoreCase(String.valueOf(C_TIPO_DATE))) {
			return true;
		}
		return false;
	}

	/**
	 * Evalua si el campo representado es tipo Numerico o no.
	 * 
	 * @return True si el campo representa un campo tipo numerico
	 * @throws Exception
	 */
	public boolean isNumeric() throws Exception {
		if (this.fieldGenericDataType.toString().equalsIgnoreCase(String.valueOf(C_TIPO_NUMBER))) {
			return true;
		}
		if (this.fieldGenericDataType.toString().equalsIgnoreCase(String.valueOf(C_TIPO_AUTONUMERICO))) {
			return true;
		}
		return false;
	}

	/**
	 * Evalua si el campo representado es tipo LOB (Large OBject) o no.
	 * 
	 * @return True si el campo representa un campo tipo LOB
	 * @throws Exception
	 */
	public boolean isLOB() throws Exception {
		if (this.fieldGenericDataType.toString().equalsIgnoreCase(String.valueOf(C_TIPO_LOB))) {
			return true;
		}
		return false;
	}

	/**
	 * Evalua si el campo representado es tipo text o no.
	 * 
	 * @return True si el campo representa un campo tipo text
	 * @throws Exception
	 * @deprecated Usar {@link #isLOB() isLOB} en lugar de esta API
	 */
	@Deprecated
	public boolean isText() throws Exception {
		if (this.fieldGenericDataType.toString().equalsIgnoreCase(String.valueOf(C_TIPO_TEXT))) {
			return true;
		}
		return false;
	}

	/**
	 * Evalua si el campo representado es tipo Float o no. Se entiende por un tipo float un campo
	 * numerico que tenga configurado decimales.
	 * 
	 * @return True si el campo representa un campo tipo Float
	 * @throws Exception
	 */
	public boolean isFloat() throws Exception {
		if (isNumeric()) {
			if (this.fieldScale > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Evalua si el campo representado es tipo entero o no. Se entiende por un tipo entero un campo
	 * numerico que no tenga configurado decimales.
	 * 
	 * @return True si el campo representa un campo tipo Entero
	 * @throws Exception
	 */
	public boolean isInteger() throws Exception {
		if (isNumeric()) {
			if (fieldScale == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Llena los datos del campo en base a lo leido de las tablas de sistema del motor de la base de
	 * datos
	 * 
	 * @param pSystemDBField
	 * @throws Exception
	 */
	public void processSystemDBField(JBaseSystemDBField pSystemDBField) throws Exception {
		fieldName = pSystemDBField.getFieldName().toUpperCase();
		fieldGenericDataType = pSystemDBField.getGenericDataType();

		if (isString()) {
			this.fieldLength = pSystemDBField.getFieldLength();
		} else {
			this.fieldLength = pSystemDBField.getFieldPrecision();
		}

		fieldScale = pSystemDBField.getFieldScale();
		fieldIsNullable = pSystemDBField.IsNullable();
		fieldIsAutonumeric = pSystemDBField.isAutonumeric();
		fieldType = JProperty.TIPO_CAMPO; // TODO - Pendiente
	}

	/**
	 * Llena los datos del campo en base a lo leido de la clase que representa un campo
	 * 
	 * @param pClassTableField
	 * @throws Exception
	 */
	public void processClassTable(JDBClassTableField pClassTableField) throws Exception {
		fieldName = pClassTableField.getFieldName();
		fieldLength = pClassTableField.getFieldLength();
		fieldScale = pClassTableField.getFieldPrecition();
		fieldIsNullable = pClassTableField.isNullableField();
		fieldGenericDataType = pClassTableField.getFieldSQLDataType();
		fieldIsAutonumeric = pClassTableField.isAutonumericField();
		fieldType = pClassTableField.getFieldType();
	}

	/**
	 * Compara el campo actual con el campo que se pasa por parametro. Si los campos son diferentes
	 * retorna el sql action con el alter table correspondiente para actualizar el campo al campo
	 * actual
	 * 
	 * @param virtualDbField
	 * @param sqlAction
	 * @return true si los campos son diferentes.
	 * @throws Exception
	 */
	public boolean getAlterColumnAction(String tableName, JBaseVirtualDBField virtualDbField, JDBSqlActionList sqlActionList) throws Exception {
		boolean bHasTypeDiff = false;
		boolean bHasNullDiff = false;
		boolean bHasAutonumericDiff = false;
		String sqlActionReason;

		if (this.fieldGenericDataType.charAt(0) != virtualDbField.fieldGenericDataType.charAt(0)) {
			sqlActionReason = "El campo ["+ this.fieldName +"] difiere en el tipo de dato. Valor Actual [" + this.fieldGenericDataType + "] vs valor anterior [" + virtualDbField.fieldGenericDataType + "]";
			sqlActionList.addSqlActionReason(sqlActionReason);
			PssLogger.logDebug(sqlActionReason);
			bHasTypeDiff = true;
		}

		if (this.ifMustCheckFieldLength(this.fieldGenericDataType.charAt(0))) {
			if (this.fieldLength != virtualDbField.fieldLength) {
				sqlActionReason = "El campo ["+ this.fieldName +"] difiere en la longitud. Valor Actual [" + this.fieldLength + "] vs valor anterior [" + virtualDbField.fieldLength + "]";
				sqlActionList.addSqlActionReason(sqlActionReason);
				PssLogger.logDebug(sqlActionReason);
				bHasTypeDiff = true;
			}
		}

		if (this.ifMustCheckFieldScale(this.fieldGenericDataType.charAt(0))) {
			if (this.fieldScale != virtualDbField.fieldScale) {
				sqlActionReason = "El campo ["+ this.fieldName +"] difiere en la escala. Valor Actual [" + this.fieldScale + "] vs valor anterior [" + virtualDbField.fieldScale + "]";
				sqlActionList.addSqlActionReason(sqlActionReason);
				PssLogger.logDebug(sqlActionReason);				
				bHasTypeDiff = true;
			}
		}

		if (this.fieldIsNullable != virtualDbField.fieldIsNullable) {
			sqlActionReason = "El campo ["+ this.fieldName +"] difiere en aceptar nulos. Valor Actual [" + this.fieldIsNullable + "] vs valor anterior [" + virtualDbField.fieldIsNullable + "]";
			sqlActionList.addSqlActionReason(sqlActionReason);
			PssLogger.logDebug(sqlActionReason);			
			bHasNullDiff = true;
		}

		 if (this.fieldIsAutonumeric != virtualDbField.fieldIsAutonumeric) {
			 sqlActionReason = "El campo ["+ this.fieldName +"] difiere en ser autonumerico. Valor Actual [" + this.fieldIsAutonumeric + "] vs valor anterior [" + virtualDbField.fieldIsAutonumeric + "]";
				sqlActionList.addSqlActionReason(sqlActionReason);
				PssLogger.logDebug(sqlActionReason);			 
			bHasAutonumericDiff = true;
		}

		// TODO - Esto esta mal que este aca porque esto no produce
		// if (this.fieldType.equalsIgnoreCase(virtualDbField.fieldType) == false) {
		// PssLogger.logDebug("El campo actual difiere en la fieldType. Valor Actual [" +
		// this.fieldType + "] vs valor anterior [" + virtualDbField.fieldType + "]");
		// bHasDiff = true;
		// }

		if (bHasNullDiff == false && bHasTypeDiff == false) {
			return false;
		}

		if (bHasTypeDiff) {
			this.generateAlterColumnTypeSQL(tableName, sqlActionList);
			// TODO - Faltan los pre y los postsql
			// TODO - Falta evaluar si la transformacion es valida para el motor de base de datos no
			// hago
			// nada, sino deberia hacer un proceso automatico de conversion de datos.
			// }
		}

		if (bHasNullDiff) {
			this.generateAlterColumnNullPropertySQL(tableName, sqlActionList);
		}

		if (bHasAutonumericDiff) {
			this.generateAlterColumnAutonumericSQL(tableName, sqlActionList);
		}
		
		return true;
	}

	/**
	 * Valida si un campo deber comparar la longitud con otro campo dependiendo de su tipo generico
	 * 
	 * @param fieldGenericDataType
	 * @return
	 */
	protected boolean ifMustCheckFieldLength(char fieldGenericDataType) {
		if (fieldGenericDataType == JBaseVirtualDBField.C_TIPO_DATE || fieldGenericDataType == JBaseVirtualDBField.C_TIPO_LOB || fieldGenericDataType == JBaseVirtualDBField.C_TIPO_TEXT || fieldGenericDataType == JBaseVirtualDBField.C_TIPO_AUTONUMERICO) {
			return false;
		}

		return true;
	}

	/**
	 * @param fieldGenericDataType
	 * @return
	 */
	protected boolean ifMustCheckFieldScale(char fieldGenericDataType) {
		if (fieldGenericDataType == JBaseVirtualDBField.C_TIPO_NUMBER) {
			return true;
		}

		return false;
	}

	// ---------------------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------------------

	// TODO - Ojo usar esta logica para determinar la longitud a guardar
	// public int getLength() throws Exception {
	// // if (isString())
	// // return pLongitud.getValue();
	// // else
	// // return pPrecision.getValue();
	// }

	// /**
	// * Retorna la cantidad de decimales de un campo.
	// *
	// * @return Cantidad de decimales de un campo. Si el campo no es numerico retorna 0
	// * @throws Exception
	// */
	// public int getDecimals() throws Exception {
	// if (isNumeric() == false) {
	// return 0;
	// }
	// return this.fieldScale.getValue();
	// }

	// /**
	// * Retorna el tipo generico de tipo de dato
	// *
	// * @return Tipo generico de dato
	// * @throws Exception
	// */
	// public String getGenericDataType() throws Exception {
	// return this.fieldGenericDataType.getValue();
	// }

}
