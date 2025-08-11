package  pss.common.dbManagement.synchro;

import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;

public class JDBSqlActionList {

	private String sRelatedTable = ""; // Tabla Referente al Sql
	private String sTableClass = ""; // Clase relacionada a la tabla

	private boolean bTruncated = false; // Indica si trunca la tabla antes de
																			// insertar los datos
	private boolean bReplicated = false; // Indica si la tabla se tiene que
																				// replicar
	//	private boolean bDataInstallation = false; // INDICA SI LOS DATOS SE INSERTAN
	//																							// SOLO EN CASO DE UNA INSTALACION

	private JList<JDBSqlAction> oSqlActionList = null;
	private String mainSqlAction = null; // Set la accion SQL Generica
	
	/**
	 * Nombres de las clases que referencian a la tabla sobre la que hay que ejecutar los cambios
	 */
	protected JList<String> classNames = null;
	protected JList<String> sqlActionReasons = null;
	
	// Lista principal de acciones, describen la accion en general a ejecutar sobre la base de datos
  public static final String CREATE_TABLE = new String("CREATE TABLE");
  public static final String ALTER_TABLE = new String("ALTER TABLE");
  public static final String DROP_TABLE = new String("DROP TABLE");
  
  public static final String CREATE_INDEX = new String("CREATE INDEX");
  public static final String ALTER_INDEX = new String("ALTER INDEX");
  public static final String DROP_INDEX = new String("DROP INDEX");
	
	public JDBSqlActionList() {

	}
	
	/*****************************************
	 * Retorna si la tabla tiene que truncarse
	 *****************************************/
	public boolean getTruncated() {
		return bTruncated;
	}

	/***************************************************************
	 * Retorna si los datos deben insertarse en caso de instalacion
	 **************************************************************/
	//	@Deprecated
	//	public boolean getDataInstallation() {
	//		return bDataInstallation;
	//	}

	/*****************************************************************
	 * Método para setear la tabla relacionada con el Sql del objeto *
	 *****************************************************************/
	public void setRelatedTable(String oRelatedTable) {
		sRelatedTable = oRelatedTable;
	}

	/******************************************************************
	 * Método para obtener la tabla relacionada con el Sql del objeto *
	 ******************************************************************/
	public String getRelatedTable() {
		return sRelatedTable;
	}

	/*****************************************
	 * Setea si la tabla esta replicada o no *
	 *****************************************/
	public void setReplicated() {
		bReplicated = true;
	}

	/**
	 * Retorna si la tabla esta replicada o no
	 * 
	 * @return
	 */
	public boolean ifReplicated() {
		return bReplicated;
	}
	
	/*****************************************
	 * Setea si la tabla tiene que truncarse
	 *****************************************/
	public void setTruncated() {
		bTruncated = true;
	}
	

	/******************************************************************
	 * Obtiene el nombre de la clase que está realacionada con la tabla
	 ******************************************************************/
	public String getTableClass() {
		return sTableClass;
	}
	
	/**
	 * Setea el nombre de la clase que esta relacionada con la tabla
	 */
	public void setTableClass(String tableClass) {
		this.sTableClass = tableClass;
	}
	
	/**
	 * Indica si la clase fue inicializada indicando la tabla sobre la que contiene las acciones 
	 * a ejecutar 
	 * 
	 * @return true si la clase fue inicializada 
	 */
	public boolean ifInitialized() {
		if (sRelatedTable.isEmpty()) {
			return false;
		}
		return true;
	}
	
	/**
	 * Agrega una accion a ser ejecutada
	 * 
	 * @param sqlAction
	 */
	public void addAction(JDBSqlAction sqlAction) {
		// Si la lista no fue inicializada la crea
		if (oSqlActionList == null) {
			oSqlActionList = JCollectionFactory.createList();
		}
		
		// Setea la accion generica correspondiente en base a la accion particular 
		// en caso que no haya sido seteada
		if (this.mainSqlAction == null) {
			mainSqlAction = decodeMainAction(sqlAction.getAction());
		}
		
		oSqlActionList.addElement(sqlAction);
	}
		
	/**
	 * Retorna la accion sql generica
	 * 
	 * @return
	 */
	public String getMainSqlAction() {
		return mainSqlAction;
	}
	
	public String decodeMainAction(String sqlAction) {
		if (sqlAction == JDBSqlAction.ADD_TABLE) {
			return JDBSqlActionList.CREATE_TABLE;
		}
		
		if (sqlAction == JDBSqlAction.ADD_COLUMN ||
				sqlAction == JDBSqlAction.MODIFY_COLUMN ||
				sqlAction == JDBSqlAction.DROP_COLUMN) {
			return JDBSqlActionList.ALTER_TABLE;
		}
		
		if (sqlAction == JDBSqlAction.DROP_TABLE) {
			return JDBSqlActionList.DROP_TABLE;
		}
		
		if (sqlAction == JDBSqlAction.ADD_INDEX ||
				sqlAction == JDBSqlAction.ADD_PRIMARY_KEY) {
			return JDBSqlActionList.CREATE_INDEX;
		}
		
		if (sqlAction == JDBSqlAction.DROP_INDEX ||
				sqlAction == JDBSqlAction.DROP_PRIMARY_KEY) {
			return JDBSqlActionList.DROP_INDEX;
		}

		// TODO - Falta ALTER INDEX !!!
		
		return new String("");
	}
	
	/**
	 * Retorna true si hay acciones en al lista
	 * 
	 * @return
	 */
	public boolean ifHasSqlActions() {
		if (this.oSqlActionList == null) {
			return false;
		}
		
		if (this.oSqlActionList.size() > 0) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Procesa las sentencias SQL siguiendo la logica del tipo de general de accion SQL que tiene que ejecutar. 
	 * 
	 * @throws Exception
	 */
	public void processSqlAction() throws Exception {
		
		PssLogger.logDebug("Entrando a procesar sentencias sql tipo [" + mainSqlAction + "] para la tabla [" + this.sRelatedTable + "]");
		
		if (this.mainSqlAction.equalsIgnoreCase(JDBSqlActionList.CREATE_TABLE) ||
				this.mainSqlAction.equalsIgnoreCase(JDBSqlActionList.DROP_TABLE) ||
				this.mainSqlAction.equalsIgnoreCase(JDBSqlActionList.CREATE_INDEX) ||
				this.mainSqlAction.equalsIgnoreCase(JDBSqlActionList.ALTER_INDEX) ||
				this.mainSqlAction.equalsIgnoreCase(JDBSqlActionList.DROP_INDEX) ) {
			processSecuencialSqlAction("");
			return;
		}
		
		if (this.mainSqlAction.equalsIgnoreCase(JDBSqlActionList.ALTER_TABLE)) {
			processAlterTableSqlAction();
		}
		
  	JExcepcion.SendError("La accion de SQL con ID [" + mainSqlAction + "] es deconocida");
	}
	
	/**
	 * Procesa las sentencias SQL en orden secuencial 
	 * 
	 * @throws Exception
	 */
	private void processSecuencialSqlAction(String actionFilter) throws Exception {
		JIterator<JDBSqlAction> oSqlActionIterator;
		JDBSqlAction sqlAction;
		String sql;

		oSqlActionIterator = this.oSqlActionList.getIterator();

		while (oSqlActionIterator.hasMoreElements()) {
			sqlAction = oSqlActionIterator.nextElement();

			if (actionFilter != null && actionFilter.isEmpty() == false && sqlAction.getAction() != actionFilter) {
				continue;
			}
			
			PssLogger.logDebug("Ejecutando accion tipo: " + sqlAction.getAction() );
			
			sql = sqlAction.getPreSql();
			if (sql != null && sql.isEmpty() == false) {
				PssLogger.logDebug("Ejecutando el siguiente SQL antes de la accion: ");
				execTransaction(sql);
			}

			sql = sqlAction.getSql();
			if (sql != null && sql.isEmpty() == false) {
				PssLogger.logDebug("Ejecutando el siguiente SQL: ");
				execTransaction(sql);
			}			
			
			sql = sqlAction.getPostSql();
			if (sql != null && sql.isEmpty() == false) {
				PssLogger.logDebug("Ejecutando el siguiente SQL despues de la accion: ");
				execTransaction(sql);
			}		
		} // end while
	}

	/**
	 * Procesa la logica de Alter Table donde primero se procesan 
	 * 
	 * @throws Exception
	 */
	private void processAlterTableSqlAction() throws Exception {
		processSecuencialSqlAction(JDBSqlAction.ADD_COLUMN);
		processSecuencialSqlAction(JDBSqlAction.MODIFY_COLUMN);
		processSecuencialSqlAction(JDBSqlAction.DROP_COLUMN);
	}
	
	/**
	 * Ejecuta una instruccion sql en la base de datos
	 * 
	 * @param zSql
	 * @throws Exception
	 */
	private void execTransaction(String zSql) throws Exception {
		JBaseRegistro oReg=JBaseRegistro.VirtualCreate();
		oReg.executeTransaction(zSql);
		oReg.getDatabase().commit();
		oReg.close();
	}		
	
	/**
	 * @return
	 * @throws Exception
	 */
	public JIterator<JDBSqlAction> getActionIterator() throws Exception {
		return this.oSqlActionList.getIterator();
	}
	
	/**
	 * @return
	 */
	public JList<String> getClassNames() {
		if (this.classNames == null) {
			this.classNames = JCollectionFactory.createList();
		}
		return this.classNames;
	}

	public void setClassNames(JList<String> classNames) {
		this.classNames = classNames;
	}
	
	public void addSqlActionReason(String sqlActionReason) {
		if (this.sqlActionReasons == null ) {
			this.sqlActionReasons = JCollectionFactory.createList();
		}
		this.sqlActionReasons.addElement(sqlActionReason);
	}
	
	public JList<String> getSqlActionReason() {
		if (this.sqlActionReasons == null ) {
			this.sqlActionReasons = JCollectionFactory.createList();
		}		
		return this.sqlActionReasons;
	}
}
