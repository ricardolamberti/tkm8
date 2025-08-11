/**
 * 
 */
package  pss.common.dbManagement.synchro.base;

import pss.common.dbManagement.synchro.JDBClassTable;
import pss.common.dbManagement.synchro.JDBSqlAction;
import pss.common.dbManagement.synchro.JDBSqlActionList;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JList;

/**
 * 
 * 
 */
public abstract class JBaseVirtualDBTable {
	/**
	 * Indica que la representacion virtual de la tabla, campo o indice fisica del sistema de la base
	 * de datos.
	 */
	public final static char DB_VIRTUAL_TYPE_SYSTEM = 'S';
	/**
	 * Indica que la representacion virtual de la tabla, campo o indice es de una clase.
	 */
	public final static char DB_VIRTUAL_TYPE_CLASS = 'C';

	/**
	 * Indica el tipo de representacion virtual. Puede ser que represente a una clase
	 * {@link #DB_VIRTUAL_TYPE_CLASS} o a una tabla fisica {@link #DB_VIRTUAL_TYPE_SYSTEM}
	 */
	protected char virtualDBTableType = ' ';
	/**
	 * 
	 */
	protected String tableName = new String();

	/**
	 * 
	 */
	protected JBaseVirtualDBIndexes virtualDBIndexes = null;

	/**
	 * 
	 */
	protected JBaseVirtualDBFields virtualDBFields = null;

	/**
	 * Instancia de la clase que representa un tabla, solo se setea si la tabla virtual es tipo
	 * {@link #DB_VIRTUAL_TYPE_CLASS}
	 */
	protected JDBClassTable dbClassTableInstance = null;
	
	/**
	 * Lista de clases que implementan la tabla
	 */
	protected JList<String> classNames = null; 

	/**
	 * @return
	 * @throws Exception
	 */
	public static JBaseVirtualDBTable VirtualCreate() throws Exception {
		JBaseVirtualDBTable oTabla = (JBaseVirtualDBTable) JBaseVirtualDBTable.VirtualClass().newInstance();
		return oTabla;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Class VirtualClass() throws Exception {
		String sClassName = JBDatos.getBaseMaster().getVirtualDBTableImpl(); // .getMetadataTableImpl();
		return Class.forName(sClassName);
	}

	/**
	 * @return
	 * @throws Exception
	 */
	protected abstract String generateCreateTableSQL() throws Exception;

	/**
	 * @return
	 * @throws Exception
	 */
	public abstract JDBSqlActionList generateDropTableSQL() throws Exception;
	
	/**
	 * @return
	 * @throws Exception
	 */
	public String getTableName() throws Exception {
		return tableName;
	}

	/**
	 * @param systemDBTable
	 * @throws Exception
	 */
	public void processSystemDBTable(JBaseSystemDBTable systemDBTable) throws Exception {
		virtualDBTableType = JBaseVirtualDBTable.DB_VIRTUAL_TYPE_SYSTEM;
		this.tableName = systemDBTable.getTableName().toUpperCase();
		checkVirtualDBFields();
		if (this.tableName.equalsIgnoreCase("sch_trace")) {
			System.out.println("sch_trace");
		}		
		this.virtualDBFields.processSystemDBField(systemDBTable.getSystemDBFields());
		checkVirtualDBIndexes();
		this.virtualDBIndexes.processSystemDBIndex(systemDBTable.getSystemDBIndexes());
	}

	/**
	 * @param classTable
	 * @throws Exception
	 */
	public void processClassTable(JDBClassTable classTable) throws Exception {
		virtualDBTableType = JBaseVirtualDBTable.DB_VIRTUAL_TYPE_CLASS;
		this.dbClassTableInstance = classTable;
		this.tableName = classTable.getTableName();
		checkVirtualDBFields();
		this.virtualDBFields.processClassFields(classTable.getDbFields());
		checkVirtualDBIndexes();
		this.virtualDBIndexes.processClassIndexes(classTable.getClassIndexes());
		this.classNames = classTable.getOthersClasses();
	}

	/**
	 * 
	 * 
	 * @return
	 * @throws Exception
	 */
	public JDBSqlActionList getCreateTableActionList() throws Exception {
		JDBSqlActionList sqlActionList = new JDBSqlActionList();
		JDBSqlAction sqlAction;
		JRecords<? extends JRecord> recordsToInsert = null;
		JRecord classRecord;		
		String sql;
		String sqlActionReason;
		
		// Seteo los valores del action list
		sqlActionList.setRelatedTable(this.tableName);
		// TODO - Falta setear la clase de la cual se esta basando la creacion de la tabla
		// sqlActionList.
		
		// Proceso el CreateTable
		sqlAction = new JDBSqlAction();
		
		sql = this.generateCreateTableSQL();
		sqlAction.setAction(JDBSqlAction.ADD_TABLE);
		sqlAction.setSql(sql);

		// TODO Falta resolver el preSQL
	
		if (this.dbClassTableInstance != null) {
			sqlAction.setPostSql(this.dbClassTableInstance.getCustomPostCreateTableSQL());
			if (this.dbClassTableInstance.isExportData()) {
				try {
				classRecord = (JRecord) Class.forName(this.dbClassTableInstance.getClassFullName()).newInstance();
				recordsToInsert = classRecord.getPreConfiguredData();
				} catch (Exception e) {
					PssLogger.logError(e, "Error obteniendo datos para insertar en la tabla " + this.tableName);
				}
			}
		}

		sqlActionList.addAction(sqlAction);
		sqlActionReason = "La tabla [" + this.tableName + "] no existe en la base de datos actual";
		PssLogger.logDebug(sqlActionReason);
		sqlActionList.addSqlActionReason(sqlActionReason);
		
		
		// Proceso los indices
		checkVirtualDBIndexes();
		this.virtualDBIndexes.getCreateIndexesActionList(sqlActionList);
		
		if (recordsToInsert != null) {
			recordsToInsert.firstRecord();
			while (recordsToInsert.nextRecord()) {
				classRecord = recordsToInsert.getRecord();
				sql = classRecord.getSqlInsert();
				sqlAction = new JDBSqlAction();
				sqlAction.setAction(JDBSqlAction.DATA_INSERT);
				sqlAction.setSql(sql);
				sqlActionList.addAction(sqlAction);
			}
		}
		
		return sqlActionList;
	}
	
	/**
	 * @param slaveVirtualTable
	 * @return
	 * @throws Exception
	 */
	public JDBSqlActionList getAlterTableActionList( JBaseVirtualDBTable slaveVirtualTable) throws Exception {
		JDBSqlActionList sqlActionList = new JDBSqlActionList();
		
		if (this.virtualDBFields == null) {
			JExcepcion.SendError("La virtual table Master no tiene campos configurados");
		}
		
		// JDBSqlAction sqlAction;
		this.checkVirtualDBFields();
		this.virtualDBFields.getAlterTableActionList(slaveVirtualTable.virtualDBFields, sqlActionList);
		
		this.checkVirtualDBIndexes();
		this.virtualDBIndexes.getIndexesModificationActionList(slaveVirtualTable.virtualDBIndexes, sqlActionList);
		
		return sqlActionList;
	}
	
	/**
	 * 
	 */
	protected void checkVirtualDBIndexes() throws Exception {
		if (this.virtualDBIndexes == null) {
			if (this.tableName == null || this.tableName.isEmpty()) {
				JExcepcion.SendError("Nombre de tabla vacio o nulo");
			}
			this.virtualDBIndexes = JBaseVirtualDBIndexes.VirtualCreate();
			this.virtualDBIndexes.setTableName(this.tableName);
		}
	}

	/**
	 * 
	 */
	protected void checkVirtualDBFields() throws Exception {
		if (this.virtualDBFields == null) {
			if (this.tableName == null || this.tableName.isEmpty()) {
				JExcepcion.SendError("Nombre de tabla vacio o nulo");
			}			
			this.virtualDBFields = JBaseVirtualDBFields.VirtualCreate();
			this.virtualDBFields.setTableName(this.tableName);
		}
	}

	/**
	 * 
	 */
	protected void checkClassNames() {
		if (this.classNames == null) {
			this.classNames = JCollectionFactory.createList();
		}
	}
	
	/**
	 * @return
	 */
	public JList<String> getClassNames() {
		checkClassNames();
		return this.classNames;
	}
	
	// ----------------------------------------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------------------------------------

	// /**
	// * JBaseVirtualDBTable
	// *
	// * Genera el SQL que describe el tipo y la longitud de un campo de una tabla, segun el formato
	// * especifico del motor de la base de datos donde se implementa.
	// *
	// * @param tableField -
	// * Representacion logica de un campo de la base de datos.
	// * @return retorna un string con el SQL que describe el tipo de la base de datos
	// * @throws Exception
	// */
	// protected abstract String makeSqlDataTypes(JDBClassTableField jdbTableField) throws Exception;
	//
	// /**
	// * JDBBaseTable
	// *
	// * Realiza la conversion del tipo de datos generico al tipo de datos SQL especifico de cada
	// motor
	// * de base de datos.
	// *
	// * @param DataType -
	// * tipo de datos generico tipificado en
	// * {@link pss.common.dbManagement.synchro.JBaseVirtualDBField JBaseVirtualDBField}
	// * @return tipo de dato SQL
	// * @throws Exception
	// */
	// protected abstract String getDataTypeDescSQL(String dataType) throws Exception;
	//
	// /**
	// * Genera el SQL que agrega un campo a la tabla
	// *
	// * @param jdbTableField -
	// * Representacion logica de un campo de la base de datos.
	// * @return retorna un string con el SQL que crea un nuevo campo
	// * @throws Exception
	// */
	// public abstract String addColumn(JDBClassTableField jdbTableField) throws Exception;
	//
	// /**
	// * JDBBaseTable
	// *
	// * Genera el SQL que describe un campo de una tabla, segun el formato especifico del motor de la
	// * base de datos donde se implementa.
	// *
	// * @param tableField -
	// * Representacion logica de un campo de la base de datos.
	// * @return retorna un string con el SQL que describe el campo dependiendo del motor de base de
	// * datos
	// * @throws Exception
	// */
	// protected abstract String makeColumnDescSQL(JDBClassTableField jdbTableField) throws Exception;
	//
	// /**
	// * JDBBaseTable
	// *
	// * Genera el SQL que modifica los atributos de un campo a la tabla
	// *
	// * @param tableField -
	// * Representacion logica de un campo de la base de datos.
	// * @return retorna un string con el SQL que modificar un campo
	// * @throws Exception
	// */
	// protected abstract String modifyColumn(JDBClassTableField jdbTableField) throws Exception;
	//
	//
	//
	//
}
