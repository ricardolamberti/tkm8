package  pss.common.dbManagement.synchro;

import java.io.File;

import pss.JPath;
import pss.common.components.JSetupParameters;
import pss.core.services.records.JBaseRecord;
import pss.core.services.records.JProperty;
import pss.core.services.records.JRecord;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;

/**
 * Tiene la informacion que relaciona a la clase con una tabla de la base de datos, como el nombre
 * de la tabla, su estructura y sus indices. Tambien guarda informacion sobre el nombre de la clase
 * y el paquete al que pertenece.
 * 
 */
public class JDBClassTable {

	/**
	 * Nombre de la tabla a la que hace referencia la clase
	 */
	private String tableName = null;
	/**
	 * Informacion de los campos de la tabla
	 */
	private JDBClassTableFields dbFields = new JDBClassTableFields();
	/**
	 * Informacion de los indices relacionados con la tabla
	 */
	private JDBClassIndexes oIndexes = new JDBClassIndexes();
	/**
	 * Nombre del paquete al que pertenece la clase que representa clase seteada en {@link #tableName}
	 */
	private String packageName = null;
	/**
	 * Nombre de la clase que representa la tabla seteada en {@link #tableName}
	 */
	private String className = null;
	/**
	 * Parametros configurados en la clase seteada en {@link #className}
	 */
	private JSetupParameters oSetupParameters = null;

	/**
	 * Guarda el nombre completo de todas la class que referencian a la misma tabla
	 */
	private JList<String> otherClasses = null;
	
	/**
	 * Constructor
	 * 
	 * @throws Exception
	 */
	public JDBClassTable() throws Exception {
	}

	/**
	 * Retorna el nombre de la tabla
	 * 
	 * @return Nombre de la tabla
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * Setea el nombre de la tabla representada por la clase
	 * 
	 * @param tableName
	 *          Nombre de la tabla
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * Retorna la clase que almacena los campos de la tabla representada. Esta clase se llena
	 * automaticamente al procesar una clase que hereda de la clase
	 * {@link pss.core.services.records.JRecord JRecord} utilizando el metodo
	 * {@link #processJRecordClass(String, String) processJRecordClass}.
	 * 
	 * @return Clase {@link pss.common.dbManagement.synchro.JDBClassTableFields JDBTableFields} que
	 *         contiene informacion sobre los campos a la que hacer referencia la tabla. Si no hay
	 *         ningun campo cargado devuelve una clase sin campos seteados pero no retorna null.
	 * @throws Exception
	 */
	public JDBClassTableFields getDbFields() throws Exception {
		checkDbFields();
		return dbFields;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public JDBClassIndexes getClassIndexes() throws Exception {
		this.checkClassIndexes();
		return this.oIndexes;
	}

	/**
	 * Chequea que la clase que maneja los campos se haya creado antes de manejarlo
	 * 
	 * @throws Exception
	 */
	private void checkDbFields() throws Exception {
		if (this.dbFields == null)
			this.dbFields = new JDBClassTableFields();
	}

	/**
	 * Chequea que la clase que maneja los indices se haya creado antes de manejarlo
	 * 
	 * @throws Exception
	 */
	private void checkClassIndexes() throws Exception {
		if (this.oIndexes == null)
			this.oIndexes = new JDBClassIndexes();
	}

	/**
	 * Procesa un archivo para determinar si el archivo representa una tabla fisica de la base de
	 * datos. Si es asi setea automaticamente los atributos correspondientes y retorna <b><i>true</i></b>.
	 * 
	 * @param directoryName
	 *          Nombre del directorio donde esta el archivo a procesar
	 * @param fileName
	 *          Nombre del archivo
	 * @return <b><i>true</i></b> si la clase representa una tabla fisica de la base de datos
	 * @throws Exception
	 */
	public boolean processJRecordClass(String directoryName, String fileName) throws Exception {
		// Solamente proceso los .class
		if (fileName.lastIndexOf(".class") == -1)
			return false;

		// Descarto las inner classes
		if (fileName.indexOf('$') != -1)
			return false;

		// Formateo y guardo el nombre del package  y de la clase.
		this.packageName = directoryName.substring(JPath.PssPath().length() + 1).replace(File.separatorChar, '.');
		this.className = fileName.substring(0, fileName.length() - 6);

		if (className.equalsIgnoreCase("BizLoginTrace"))
			PssLogger.logDebug("Solo para debuguear");
		
		try {
			// Filtro las clases base
			if (Class.forName(getClassFullName()) == JRecord.class || Class.forName(getClassFullName()) == JBaseRecord.class) {
				return false;
			}

			// Filtro las clases que no heredan de JRecord
			if (JRecord.class.isAssignableFrom(Class.forName(getClassFullName())) == false) {
				PssLogger.logDebug("Class " + getClassFullName() + " is not asignable to JRecord class");
				return false;
			}

			// Descarto las interfaces
			if (Class.forName(getClassFullName()).isInterface()) {
				PssLogger.logDebug("Class " + getClassFullName() + " is an inteface and will not be processed");
				return false;
			}

		} catch (Throwable t) {
			PssLogger.logDebug(t, "Se detecto la siguiente error CRITICO chequeando la clase " + getClassFullName());
//			t.printStackTrace();
			return false;
		}

		// Trato de instanciar la clase
		JRecord oDbClassInstance;

		try {
			PssLogger.logDebug("Class " + getClassFullName() + " will be processed as JRecord class");
			oDbClassInstance = (JRecord) Class.forName(getClassFullName()).newInstance();
		} catch (InstantiationException e) {
			PssLogger.logDebug("Se detecto la siguiente excepcion " + e.getMessage() + " tratando de instanciar la clase " + getClassFullName());
			return false;
		} catch (Exception e) {
			PssLogger.logDebug("Se detecto la siguiente excepcion tratando de instanciar la clase " + getClassFullName());
			e.printStackTrace();
			return false;
		} catch (Throwable t) {
			PssLogger.logDebug("Se detecto la siguiente error CRITICO tratando de instanciar la clase " + getClassFullName());
			t.printStackTrace();
			return false;
		}

		// Obtengo el nombre de la tabla relacionada
		PssLogger.logDebug("Getting Table name ...");
		this.tableName = oDbClassInstance.GetTable();

		if (!oDbClassInstance.canSynchro())
			return false;
		// Si el nombre de la tabla es null o vacia es que la tabla es virtual.
		if (this.tableName == null) {
			return false;
		}

		if (this.tableName.isEmpty()) {
			return false;
		} // end if

		if (oDbClassInstance.isSystemTable()) {
			return false;
		}

		// Fuerzo el nombre de la tabla en mayusculas
		tableName = tableName.toUpperCase();

		PssLogger.logDebug("Procesando clase " + getClassFullName() + " - Tabla: " + tableName);

		// Si no tiene Fixed Properties no tengo informacion de campos
		PssLogger.logDebug("Checking fixed properties");
		if (oDbClassInstance.getFixedProperties() == null) {
			return false;
		} // end if

		if (oDbClassInstance.getFixedProperties().isEmpty()) {
			return false;
		} // end if

		// Proceso campos
		PssLogger.logDebug("getting fields properties");
		JDBClassTableField dbField;
		JProperty oProperty;
		JDBClassIndex oPrimaryKey = null;

		// Chequeo que se haya creado la instancia
		checkDbFields();

		// Proceso campo por campo
		JIterator<JProperty> oPropIterator;
		oPropIterator = oDbClassInstance.getFixedProperties().getValueIterator();

		while (oPropIterator.hasMoreElements()) {
			oProperty = oPropIterator.nextElement();

			if (oProperty.getType() != JProperty.TIPO_CAMPO && oProperty.getType() != JProperty.TIPO_CLAVE) {
				PssLogger.logDebug("Descartando campo : " + oProperty.GetCampo() + " porque es del tipo : " + oProperty.getType());
				continue;
			}

			dbField = new JDBClassTableField();
			dbField.setFieldData(oProperty);

			dbField.setFieldDataType(oDbClassInstance.getProp(dbField.getFieldName()).getClass());
			this.dbFields.addNewField(dbField);

			PssLogger.logDebug("Adding field :" + dbField.getFieldName() + " - " + dbField.getFieldDataTypeFullName() + " - " + dbField.getFieldType() + " - " + dbField.getFieldLength());

			// Crea la primary key de la tabla usando los campos declarados como clave
			if (oProperty.getType() == JProperty.TIPO_CLAVE) {
				if (oPrimaryKey == null) {
					oPrimaryKey = new JDBClassIndex();
					oPrimaryKey.setIndexIsPrimaryKey(true);
					oPrimaryKey.setIndexIsClustered(true);
					oPrimaryKey.setIndexIsUnique(true);
					oPrimaryKey.setIndexName("PK_" + tableName);
				}

				oPrimaryKey.addField(oProperty.GetCampo(), JDBClassIndexField.TIPO_ORDEN_ASC);
			}

		} // end while
		PssLogger.logDebug("Fields properties read ok");

		// Obtengo los parametrosgenerales
		PssLogger.logDebug("Getting Table setup parameters");
		this.oSetupParameters = new JSetupParameters(JSetupParameters.NIVEL_DATOS_TRANSACCION);
		oDbClassInstance.setupConfiguration(oSetupParameters);
		PssLogger.logDebug("Table setup parameters read ok");

		// Proceso los indices secundarios creados explicitamente en la clase que representa la tabla
		oIndexes.addVirtualIndexes(oDbClassInstance.getMyIndexes());		

		if (oIndexes.ifHasPrimaryKey() == false) {
			// Si se creo un indice tipo primary key lo agrego a los indices
			if (oPrimaryKey != null) {
				this.oIndexes.addVirtualIndex(oPrimaryKey);
			}
		}

		return true;
	}

	/**
	 * Retorna el nombre de la clase que representa en el formato <i>package.class</i>
	 * 
	 * @return Nombre completo de la clase
	 */
	public String getClassFullName() {
		return packageName + '.' + className;
	}

	/**
	 * Retorna sentencia SQL que tiene que ejectarse despues de crearse la tabla
	 * 
	 * @return Sentencia SQL
	 */
	// TODO - Esta rutina tiene que recibir un parametro para pedir el tipo de sentencia (pre/post) y
	// la accion (create table, alter table, etc.)
	public String getCustomPostCreateTableSQL() {
		if (oSetupParameters == null) {
			return "";
		}
		return this.oSetupParameters.getNewTableFunction();
	}
	
	/**
	 * Evalua si hay que exportar los datos que estan en la tabla referenciada por la clase tienen que ser exportados o no.
	 * Los datos se extraen de la base de datos configurada en el archivo pss.ini 
	 *  
	 * @return true si tiene que exportar los datos 
	 */
	public boolean isExportData() {
		if (oSetupParameters == null) {
			return false;
		}
		return this.oSetupParameters.isExportData();
	}

	/**
	 * @see #getCustomPostCreateTableSQL()
	 * @return
	 */
	public boolean ifTruncate() {
		if (oSetupParameters == null) {
			return false;
		}
		return oSetupParameters.isDataTruncated();
	}

	/**
	 * Retorna el nombre del campo que es autonumerico
	 * 
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public String getAutonumericFieldName() throws Exception {
		String sFieldName = null;
		JIterator<JDBClassTableField> itDbTableField;
		JDBClassTableField oDbTableField;

		checkDbFields();
		itDbTableField = this.dbFields.getDBFieldsIterator();

		while (itDbTableField.hasMoreElements()) {
			oDbTableField = itDbTableField.nextElement();
			if (oDbTableField.isAutonumericField()) {
				sFieldName = oDbTableField.getFieldName();
			} // end if
		} // end while
		return sFieldName;
	}

	/**
	 * 
	 */
	protected void checkOthersClasses() {
		if (this.otherClasses == null) {
			this.otherClasses = JCollectionFactory.createList();
		}
	}
	
	/**
	 * @param className
	 * @throws Exception
	 */
	public void addOthersClassWithSameTable(String className) throws Exception {
		this.checkOthersClasses();
		this.otherClasses.addElement(className);
	}
	
	/**
	 * @return
	 */
	public JList<String> getOthersClasses() {
		checkOthersClasses();
		this.otherClasses.addElement(this.getClassFullName());
		return this.otherClasses;
	}
}
