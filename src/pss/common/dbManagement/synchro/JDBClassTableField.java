package  pss.common.dbManagement.synchro;

import pss.common.dbManagement.synchro.base.JBaseVirtualDBField;
import pss.core.services.records.JProperty;
import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;

/**
 * Tiene informacion sobre un campo de una tabla de la base de datos, como el nombre el tipo y la
 * longitud. Tambien guarda informacion sobre el tipo de datos "JAVA" que lo representa en la clase.
 * 
 */
public class JDBClassTableField {
	/**
	 * Nombre del campo
	 */
	private String fieldName = new String(""); // Nombre del campo
	/**
	 * Tipo de campo, Almacena uno de los siguientes valores
	 * <li>{@link pss.core.services.records.JProperty.TIPO_CAMPO JProperty.TIPO_CAMPO}</li>
	 * <li>{@link pss.core.services.records.JProperty.TIPO_KEY JProperty.TIPO_KEY}</li>
	 */
	private String fieldType = new String(""); // Tipo de campo
	/**
	 * Tipo de dato JAVA que representa el tipo de dato del campo.
	 */
	@SuppressWarnings("unchecked")
	private Class fieldDataType = null;
	/**
	 * Indica si un campo es autonumerico TODO - FALTA ...
	 */
	private boolean isAutonumericField = false;
	/**
	 * Indica si el campo de la tabla de la base de datos puede ser nulo.
	 */
	private boolean isNullableField = true;
	/**
	 * Longitud del campo
	 */
	private int fieldLength = 0;
	/**
	 * Precision del campo
	 */
	private int fieldPrecition = 0;

	/**
	 * Constructor
	 * 
	 * @throws Exception
	 */
	public JDBClassTableField() throws Exception {
		this.fieldName = new String(""); // .setFieldName("");
		this.setFieldType(JProperty.TIPO_CAMPO);
		this.setAutonumericField(false);
		this.setNullableField(true); // Por default todos los campos pueden ser nulos
		this.setFieldLength(0);
		this.setFieldPrecition(0);
		this.fieldDataType = null;
	}

	/**
	 * Setea toda la informacion de un campo de la base de datos
	 * 
	 * @param pFieldName
	 *          Nombre del campo
	 * @param pFieldType
	 *          Tipo de campo. Los valores validos pueden ser:
	 *          <li>{@link pss.core.services.records.JProperty.TIPO_CAMPO JProperty.TIPO_CAMPO}</li>
	 *          <li>{@link pss.core.services.records.JProperty.TIPO_KEY JProperty.TIPO_KEY}</li>
	 * @param pFieldDataType
	 *          Clase java relacionada con el tipo de campo SQL
	 * @param pFieldLength
	 *          Longitud del campo
	 * @param pFieldPrecition
	 *          Precision del campo
	 * @param pIsNullableField
	 *          Indica si el campo permite valores nulos
	 * @param pIsAutonumericField
	 *          Indica si el campo es autonumerico
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void setFieldData(String pFieldName, String pFieldType, Class pFieldDataType, int pFieldLength, int pFieldPrecition, boolean pIsNullableField, boolean pIsAutonumericField) throws Exception {
		this.setFieldName(pFieldName);
		this.setFieldType(pFieldType);
		this.setAutonumericField(pIsAutonumericField);
		this.setNullableField(pIsNullableField);
		this.setFieldLength(pFieldLength);
		this.setFieldPrecition(pFieldPrecition);
		this.setFieldDataType(pFieldDataType);
	}

	/**
	 * Setea toda la informacion de un campo de la base de datos
	 * 
	 * @param pFieldName
	 *          Nombre del campo
	 * @param pFieldType
	 *          Tipo de campo.Los valores validos pueden ser:
	 *          <li>{@link pss.core.services.records.JProperty.TIPO_CAMPO JProperty.TIPO_CAMPO}</li>
	 *          <li>{@link pss.core.services.records.JProperty.TIPO_KEY JProperty.TIPO_KEY}</li>
	 * @param pFieldDataType
	 *          Clase java relacionada con el tipo de campo SQL
	 * @param pFieldLength
	 *          Longitud del campo
	 * @param pFieldPrecition
	 *          Precision del campo
	 * @param pIsNullableField
	 *          Indica si el campo permite valores nulos
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void setFieldData(String pFieldName, String pFieldType, Class pFieldDataType, int pFieldLength, int pFieldPrecition, boolean pIsNullableField) throws Exception {
		setFieldData(pFieldName, pFieldType, pFieldDataType, pFieldLength, pFieldPrecition, pIsNullableField, false);
	}

	/**
	 * Setea toda la informacion de un campo de la base de datos
	 * 
	 * @param pFieldName
	 *          Nombre del campo
	 * @param pFieldType
	 *          Tipo de campo. Los valores validos pueden ser:
	 *          <li>{@link pss.core.services.records.JProperty.TIPO_CAMPO JProperty.TIPO_CAMPO}</li>
	 *          <li>{@link pss.core.services.records.JProperty.TIPO_KEY JProperty.TIPO_KEY}</li>
	 * @param pFieldDataType
	 *          Clase java relacionada con el tipo de campo SQL
	 * @param pFieldLength
	 *          Longitud del campo
	 * @param pFieldPrecition
	 *          Precision del campo
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void setFieldData(String pFieldName, String pFieldType, Class pFieldDataType, int pFieldLength, int pFieldPrecition) throws Exception {
		setFieldData(pFieldName, pFieldType, pFieldDataType, pFieldLength, pFieldPrecition, false, false);
	}

	/**
	 * Setea toda la informacion de un campo de la base de datos
	 * 
	 * 
	 * @param pFieldName
	 *          Nombre del campo
	 * @param pFieldType
	 *          Tipo de campo. Los valores validos pueden ser:
	 *          <li>{@link pss.core.services.records.JProperty.TIPO_CAMPO JProperty.TIPO_CAMPO}</li>
	 *          <li>{@link pss.core.services.records.JProperty.TIPO_KEY JProperty.TIPO_KEY}</li>
	 * @param pFieldDataType
	 *          Clase java relacionada con el tipo de campo SQL
	 * @param pFieldLength
	 *          Longitud del campo
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void setFieldData(String pFieldName, String pFieldType, Class pFieldDataType, int pFieldLength) throws Exception {
		setFieldData(pFieldName, pFieldType, pFieldDataType, pFieldLength, 0, false, false);
	}

	/**
	 * Setea toda la informacion de un campo de la base de datos
	 * 
	 * @param dbProperty
	 *          Clase que guarda todos los parametros de un campo
	 * @throws Exception
	 */
	public void setFieldData(pss.core.services.records.JProperty dbProperty) throws Exception {
		this.setFieldName(dbProperty.GetCampo().toUpperCase());
		this.setFieldType(dbProperty.getType());
		this.setAutonumericField(dbProperty.ifAutonumerico());
		this.setNullableField(!dbProperty.isRequire());
		this.setFieldLength(dbProperty.getSize());
		this.setFieldPrecition(dbProperty.GetPrecision());
	}

	/**
	 * Retorna el nombre del campo de la base de datos
	 * 
	 * @return Nombre del campo, si no fue seteado retorna un string vacio
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * Setea el nombre del campo
	 * 
	 * @param pFieldName
	 *          Nombre del campo
	 */
	private void setFieldName(String pFieldName) throws Exception {
		if (pFieldName == null || pFieldName.isEmpty()) {
			JExcepcion.SendError("Nombre del campo es nulo o vacio");
		}
		this.fieldName = pFieldName;
	}

	/**
	 * Retorna el tipo de campo de la base de datos. Los valores validos pueden ser:
	 * <li>{@link pss.core.services.records.JProperty.TIPO_CAMPO JProperty.TIPO_CAMPO}</li>
	 * <li>{@link pss.core.services.records.JProperty.TIPO_KEY JProperty.TIPO_KEY}</li>
	 * 
	 * @return Tipo del campo, si no fue seteado retorna un string vacio
	 */
	public String getFieldType() {
		return fieldType;
	}

	/**
	 * Setea el tipo campo. Los valores validos pueden ser:
	 * <li>{@link pss.core.services.records.JProperty.TIPO_CAMPO JProperty.TIPO_CAMPO}</li>
	 * <li>{@link pss.core.services.records.JProperty.TIPO_KEY JProperty.TIPO_KEY}</li>
	 * 
	 * @param pFieldType
	 *          Tipo de campo
	 * @throws Exception
	 *           Si el tipo de campo es null o vacio. O el tipo de campo no es ninguno de los valores
	 *           validos.
	 */
	public void setFieldType(String pFieldType) throws Exception {
		if (pFieldType == null || pFieldType.isEmpty()) {
			JExcepcion.SendError("El tipo de campo es nulo o vacio");
		}

		if (pFieldType.compareTo(JProperty.TIPO_CAMPO) != 0 && pFieldType.compareTo(JProperty.TIPO_CLAVE) != 0) {
			JExcepcion.SendError("El tipo de campo: " + pFieldType + " es invalido");
		}

		this.fieldType = pFieldType;
	}

	/**
	 * Retorna si el campo es autonumerico o no
	 * 
	 * @return true si el campo es autonumerico
	 */
	public boolean isAutonumericField() {
		return isAutonumericField;
	}

	/**
	 * Indica si un campo es autonumerico o no
	 * 
	 * @param isAutonumericField
	 *          true para indicar que el campo es autonumerico
	 */
	public void setAutonumericField(boolean isAutonumericField) {
		this.isAutonumericField = isAutonumericField;
	}

	/**
	 * Indica si un campo puede aceptar valores nulos o no
	 * 
	 * @return true si puede aceptar valores en nulo
	 */
	public boolean isNullableField() {
		return isNullableField;
	}

	/**
	 * Permite setear si un campo permite valores NULL o no. Los campos que son CLAVE no tienen que
	 * permitir los valores NULL. Los campos que son CAMPOS normales tienen que permitir los NULOS.
	 * 
	 * @param pIsNullableField
	 */
	public void setNullableField(boolean pIsNullableField) {
		if (this.fieldType != JProperty.TIPO_CLAVE && pIsNullableField == false) {
			PssLogger.logDebug("Ignorando seteo atributo \"IS NULLABLE\" en campo " + this.fieldName + " dado que fisicamente todos los campos pueden ser nulos, valor \"NOT NULL\" se maneja logicamente");
			return;
		}
		if (this.fieldType == JProperty.TIPO_CLAVE && pIsNullableField != false) {
			PssLogger.logError("El campo " + this.fieldName + " es clave pero esta configurado como NULO - Forzando el campo como NO NULO");
			isNullableField = false;
			return;
		}
		this.isNullableField = pIsNullableField;
	}

	/**
	 * Retorna la longitud del campo
	 * 
	 * @return longitud del campo
	 */
	public int getFieldLength() {
		return fieldLength;
	}

	/**
	 * Setea la longitud del campo
	 * 
	 * @param fieldLength
	 *          longitud del campo
	 */
	public void setFieldLength(int fieldLength) {
		this.fieldLength = fieldLength;
	}

	/**
	 * Retorna la precision de un campo
	 * 
	 * @return Precision de un campo
	 */
	public int getFieldPrecition() {
		return fieldPrecition;
	}

	/**
	 * Setea la precision de un campo
	 * 
	 * @param fieldPrecition
	 *          Precision de un campo
	 */
	public void setFieldPrecition(int fieldPrecition) {
		this.fieldPrecition = fieldPrecition;
	}

	/**
	 * Retorna la clase JAVA que representa el campo de la tabla
	 * 
	 * @return Retorna el tipo de clase. null si no hay nada configurado
	 */
	@SuppressWarnings("unchecked")
	public Class getFieldDataType() {
		return fieldDataType;
	}

	/**
	 * Retorna el nombre de la clase que representa un campo de la tabla.
	 * 
	 * @return Nombre de la clase si esta configurado. Sino retorna un string vacio. No retorna null
	 */
	public String getFieldDataTypeFullName() {
		if (this.fieldDataType == null) {
			return "";
		}
		return this.fieldDataType.toString();
	}

	/**
	 * <p>
	 * Retorna el tipo generico SQL asociado con la clase JAVA que representa el campo de la tabla de
	 * la base de datos. Los tipos generico deben ser interpretados por el driver de cada motor de
	 * base de datos para traducirlo a un tipo de datos concreto.
	 * </p>
	 * <p>
	 * Los valores validos pueden ser:
	 * <li>{@link pss.common.dbManagement.synchro.base.JBaseVirtualDBField#C_TIPO_VARCHAR JBaseVirtualDBField.C_TIPO_VARCHAR}</li>
	 * <li>{@link pss.common.dbManagement.synchro.base.JBaseVirtualDBField#C_TIPO_NUMBER JBaseVirtualDBField.C_TIPO_NUMBER}</li>
	 * <li>{@link pss.common.dbManagement.synchro.base.JBaseVirtualDBField#C_TIPO_DATE JBaseVirtualDBField.C_TIPO_DATE}</li>
	 * <li>{@link pss.common.dbManagement.synchro.base.JBaseVirtualDBField#C_TIPO_UNKNOWN JBaseVirtualDBField.C_TIPO_UNKNOWN}</li>
	 * </p>
	 * <p>
	 * Si la clase que representa el campo tiene traduccion a los valores conocidos retorna dicho
	 * valor. Sino retorna
	 * {@link pss.common.dbManagement.synchro.base.JBaseVirtualDBField#C_TIPO_UNKNOWN JBaseVirtualDBField.C_TIPO_UNKNOWN}
	 * </p>
	 * 
	 * @return Tipo generico SQL
	 */
	public String getFieldSQLDataType() {
		String javaDataType;

		javaDataType = fieldDataType.toString();
		if (javaDataType.indexOf("JString") >= 0) {
			if (this.fieldLength >= 4000) {
				return String.valueOf(JBaseVirtualDBField.C_TIPO_TEXT);
			}
			return String.valueOf(JBaseVirtualDBField.C_TIPO_VARCHAR);
		}
		if (javaDataType.indexOf("JMultiple") >= 0) {
			return String.valueOf(JBaseVirtualDBField.C_TIPO_VARCHAR);
		}

		if (javaDataType.indexOf("JBoolean") >= 0 || javaDataType.indexOf("JColour") >= 0 || javaDataType.indexOf("JHour") >= 0 || javaDataType.indexOf("JGeoPosition") >= 0 || javaDataType.indexOf("JPassword") >= 0) {
			return String.valueOf(JBaseVirtualDBField.C_TIPO_VARCHAR);
		}

		if (javaDataType.indexOf("JLong") >= 0 || javaDataType.indexOf("JInteger") >= 0) {
			if (this.isAutonumericField) {
				return String.valueOf(JBaseVirtualDBField.C_TIPO_AUTONUMERICO);
			}
			return String.valueOf(JBaseVirtualDBField.C_TIPO_NUMBER);
		}

		if (javaDataType.indexOf("JFloat") >= 0 || javaDataType.indexOf("JCurrency") >= 0) {
			return String.valueOf(JBaseVirtualDBField.C_TIPO_NUMBER);
		}

		if (javaDataType.indexOf("JDateTime") >= 0 || javaDataType.indexOf("JDate") >= 0) {
			return String.valueOf(JBaseVirtualDBField.C_TIPO_DATE);
		}

		if (javaDataType.indexOf("JLOB") >= 0) {
			return String.valueOf(JBaseVirtualDBField.C_TIPO_LOB);
		}
		
		return String.valueOf(JBaseVirtualDBField.C_TIPO_UNKNOWN);
	}

	/**
	 * Setea la clase que representa a un campo de la tabla. Este metodo resuelve tambien aquellos
	 * campos que estan representados por una INNER CLASS buscando su clase base para resolver la
	 * union entre el tipo de dato JAVA y el tipo de dato SQL Generico.
	 * 
	 * @param pFieldDataType
	 */
	@SuppressWarnings("unchecked")
	public void setFieldDataType(Class pFieldDataType) {
		if (pFieldDataType.toString().indexOf('$') < 0) {
			this.fieldDataType = pFieldDataType;
			return;
		}

		this.fieldDataType = pFieldDataType;
		while (true) {
			this.fieldDataType = this.fieldDataType.getSuperclass();
			String oBaseClass = this.fieldDataType.toString();
			if (oBaseClass.indexOf("$") < 0) {
				PssLogger.logDebug("Converting class " + pFieldDataType.toString() + " to " + oBaseClass);
				break;
			} // end if
		} // end while
	}

}
