package  pss.common.dbManagement.synchro.base;

import pss.core.data.interfaces.connections.JBDatos;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;

/**
 * Campo leido de las tablas de sistema del motor de la base de datos
 * 
 */
public abstract class JBaseSystemDBField extends JRecord {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	/**
	 * Nombre de la tabla al que pertenece la columna
	 */
	protected JString tableName = new JString();

	/**
	 * Nombre de la columna
	 */
	protected JString fieldName = new JString();

	/**
	 * Tipo de dato de la columna. El valor depende del motor de base de datos
	 */
	protected JString rawSQLDataType = new JString();

	/**
	 * Logitud de los campos tipos caracter (CHAR / VARCHAR)
	 */
	protected JInteger fieldLength = new JInteger();

	/**
	 * Maxima cantidad de digitos en un campo numerico.
	 */
	protected JInteger fieldPrecision = new JInteger();

	/**
	 * Cantidad de digitos decimales del numero
	 */
	protected JInteger fieldScale = new JInteger();

	/**
	 * Indica si el campo acepta valores nulos
	 */
	protected JString fieldIsNullAllowed = new JString();

	/**
	 * Evalua si un campo puede aceptar valores en null o no
	 * 
	 * @return True si puede aceptar valores null
	 * @throws Exception
	 */
	public abstract boolean IsNullable() throws Exception;

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
	protected abstract String convertSQL2GenericDataType() throws Exception;

	/**
	 * Indica si un campo es autonumerico o no
	 * 
	 * @return true si es autonumerico
	 * @throws Exception
	 */
	protected abstract boolean isAutonumeric() throws Exception;

	/**
	 * Indica si un campo es un tipo de dato string o no
	 * 
	 * @return true si es string
	 * @throws Exception
	 */
	public abstract boolean isString() throws Exception;

	/**
	 * Indica si un campo es un tipo de dato fecha o no
	 * 
	 * @return true si es fecha
	 * @throws Exception
	 */
	public abstract boolean isDate() throws Exception;

	/**
	 * Indica si un campo es un tipo de dato numero con punto flotante o no
	 * 
	 * @return true si es numero con punto floatante
	 * @throws Exception
	 */
	public abstract boolean isFloat() throws Exception;

	/**
	 * Indica si un campo es un tipo de dato numero o no
	 * 
	 * @return true si es numero
	 * @throws Exception
	 */
	public abstract boolean isNumber() throws Exception;

	/**
	 * Constructor
	 * 
	 * @throws Exception
	 */
	public JBaseSystemDBField() throws Exception {
	}

	/**
	 * Mapea los atributos de la clase Java con los campos de la base de datos
	 * 
	 * @see pss.core.services.records.JRecord#createProperties()
	 */
	@Override
	public void createProperties() throws Exception {
		// TODO - Pasarlo a las clases que heredan de esta
		addItem("Column_Name", fieldName);
		addItem("Data_Type", rawSQLDataType);
	}

	/**
	 * Define la estructura de la tabla donde se define los datos de los campos
	 * 
	 * @see pss.core.services.records.JRecord#createFixedProperties()
	 */
	@Override
	public void createFixedProperties() throws Exception {
		// TODO - Pasarlo a las clases que heredan de esta
		addFixedItem(KEY, "Column_Name", "Nombre", true, true, 100);
		addFixedItem(FIELD, "Data_Type", "Tipo", true, true, 100);
	}

	/**
	 * Retorna el nombre de la tabla
	 * 
	 * @return El nombre de la tabla si la clase representa una tabla fisica o un String vacio si la
	 *         tabla representada es virtual
	 * 
	 * @see pss.core.services.records.JBaseRecord#GetTable()
	 */
	@Override
	public String GetTable() throws Exception {
		return "";
	}

	/**
	 * Retorna la implementacion que corresponde al motor de la base de datos instalado de esta clase.
	 * 
	 * @return La implementacion que corresponde al motor de la base de datos instalado
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	// TODO - Cambiar getMetadataColumnImpl x getMetadataSystemFieldImpl
	public static Class<JBaseSystemDBField> VirtualClass() throws Exception {
		// String sClassName = JBDatos.getBaseMaster().getMetadataColumnImpl();
		String sClassName = JBDatos.getBaseMaster().getSystemDBFieldImpl();
		return (Class<JBaseSystemDBField>) Class.forName(sClassName);
	}

	/**
	 * Retorna una instancia de la clase que lee las columnas de las tablas de sistema del motor de la
	 * base de datos
	 * 
	 * @return Una instancia de la clase que lee las columnas de las tablas de sistema del motor de la
	 *         base de datos
	 * @throws Exception
	 */
	public static JBaseSystemDBField VirtualCreate() throws Exception {
		JBaseSystemDBField oColumn = JBaseSystemDBField.VirtualClass().newInstance();
		return oColumn;
	}

	/**
	 * Retorna el nombre del campo
	 * 
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName.toString();
	}

	/**
	 * Retorna el tipo SQL. Este valor depende del motor de la base de datos
	 * 
	 * @return the rawSQLDataType
	 */
	public String getRawSQLDataType() {
		return rawSQLDataType.toString();
	}

	/**
	 * Retorna la longitud del campo
	 * 
	 * @return the fieldLength
	 * @throws Exception
	 */
	public int getFieldLength() throws Exception {
		return fieldLength.getValue();
	}

	/**
	 * Retorna la presicion del campo
	 * 
	 * @return the fieldPrecision
	 * @throws Exception
	 */
	public int getFieldPrecision() throws Exception {
		return fieldPrecision.getValue();
	}

	/**
	 * Retorna la escala
	 * 
	 * @return the fieldScale
	 * @throws Exception
	 */
	public int getFieldScale() throws Exception {
		return fieldScale.getValue();
	}

	/**
	 * Retorna el tipo generico de tipo de dato que contiene el campo
	 * 
	 * @return Tipo de dato generico
	 */
	public String getGenericDataType() {
		try {
			return this.convertSQL2GenericDataType();
		} catch (Exception e) {
			PssLogger.logError(e, "Error obteniendo el tipo de dato generico");
			return String.valueOf(JBaseVirtualDBField.C_TIPO_UNKNOWN);
		}
	}

	// /**
	// * Retorna el valor almacenado en la base de datos respecto si el campo puede ser null o no
	// * @return the fieldIsNullAllowed
	// */
	// public String getFieldIsNullAllowed() {
	// return fieldIsNullAllowed.toString();
	// }

	public String getFormatedColumnName() throws Exception {
		String sColAux = this.getFieldName();
		int iPos = sColAux.indexOf("_");
		String sRta;
		if (iPos > 2) {
			String sColIni = sColAux.substring(0, iPos);
			String sColEnd = sColAux.substring(iPos + 1);
			sRta = JTools.FirstLetterUpper(sColIni) + JTools.FirstLetterUpper(sColEnd);
		} else
			sRta = JTools.FirstLetterUpper(sColAux);
		return sRta;
	}

	public String getJavaDataType() throws Exception {
		String sDataType;
		if (isString()) {
			sDataType = "String";
		} else if (isDate()) {
				sDataType = "Date";
		} else if (isFloat()) {
					sDataType = "double";
		} else {
					sDataType = "long";
		}
		return sDataType;
	}

	public String getPssDataType() throws Exception {
		if (isString()) {
			return "JString";
		}
		if (isDate()) {
			return "JDate";
		}
		if (isFloat()) {
			return "JFloat";
		}
		if (isNumber()) {
			return "JLong";
		}
		return "";
	}

	public String getStringLength() throws Exception {
		String sLen;
		if (isString()) {
			// sLen = pLongitud.AsString() ;
			sLen = this.getFieldLength() + "";
		} else {
			if (isDate()) {
				sLen = "10";
			} else {
				if (isFloat()) {
					sLen = this.getFieldPrecision() + "," + this.getFieldScale();
				} else {
					sLen = this.getFieldPrecision() + "";
				}
			}
		}
		return sLen;
	}

	public void applyDefaultFilters() throws Exception {
	}

	
}
