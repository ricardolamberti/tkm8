package  pss.common.dbManagement.synchro;

public class JDBSqlAction {

	// ---------------------------------------------------------- //
	//                        PROPIEDADES                         //
	// ---------------------------------------------------------- //
  // Distintos tipos de Acciones que se pueden ejecutar en la base *
  public static final String ADD_TABLE = "ADD_TABLE";
  public static final String DROP_TABLE = "DROP_TABLE";
  public static final String ADD_COLUMN = "ADD_COLUMN";
  public static final String MODIFY_COLUMN = "MODIFY_COLUMN";
  public static final String DROP_COLUMN = "DROP_COLUMN";
  public static final String ADD_INDEX = "ADD_INDEX";
  public static final String DROP_INDEX = "DROP_INDEX";
  public static final String ADD_PRIMARY_KEY = "ADD_PRIMARY_KEY";
  public static final String DROP_PRIMARY_KEY = "DROP_PRIMARY_KEY";
  
  public static final String DATA_INSERT = "DATA_INSERT";
  public static final String DATA_DELETE = "DATA_DELETE";
  
  public static final String EXECUTE = "EXECUTE";


	private String sAction; // indica cual es la acción que se ejecutara en la base
	private String sSql = null; // Este es el Sql que se ejecutará
	private String sPreSql = null; // Este es el Sql que se tiene que ejecutar antes de ejecutar el sql
	private String sPostSql = null; // este es el Sql que se tiene que ejecutar despues de ejecutar el sql
//	private String sRelatedTable = null; // Tabla Referente al Sql
//	private boolean bTruncated = false; // Indica si trunca la tabla antes de insertar los datos
//	private boolean bReplicated = false; // Indica si la tabla se tiene que replicar
//	private boolean bDataInstallation = false; // INDICA SI LOS DATOS SE INSERTAN SOLO EN CASO DE UNA INSTALACION
//	private String sTableClass = ""; // Clase relacionada a la tabla

	/************************
	 *      Constructors    *
	 ************************/
	/**
	 * Constructor
	 */
	public JDBSqlAction() { 
	}

	/***********************************************************************
	 *              Métodos para interfacear con los atributos             *
	 ***********************************************************************/
	/*************************************************
	 * Método para setear la accion que se ejecutará *
	 *************************************************/
	public void setAction(String Action) {
		sAction = Action;
	}

	/********************************************
	 * Método para obtener la accion del objeto *
	 ********************************************/
	public String getAction() {
		return sAction;
	}

	/****************************************
	 * Método para setear el sql del objeto *
	 ****************************************/
	public void setSql(String oSql) {
		sSql = oSql;
	}

	/*****************************************
	 * Método para obtener el Sql del objeto *
	 *****************************************/
	public String getSql() {
		return sSql;
	}

	/****************************************
	 * Método para setear el sql del objeto *
	 ****************************************/
	public void setPreSql(String oPreSql) {
		sPreSql = oPreSql;
	}

	/*****************************************
	 * Método para obtener el Sql del objeto *
	 *****************************************/
	public String getPreSql() {
		return sPreSql;
	}	

	/****************************************
	 * Método para setear el sql del objeto *
	 ****************************************/
	public void setPostSql(String oPostSql) {
		sPostSql = oPostSql;
	}

	/*****************************************
	 * Método para obtener el Sql del objeto *
	 *****************************************/
	public String getPostSql() {
		return sPostSql;
	}	
	
//	/*****************************************
//	 * Retorna si la tabla tiene que truncarse
//	 *****************************************/
//	public boolean getTruncated() {
//		return bTruncated;
//	}
//
//	/***************************************************************
//	 * Retorna si los datos deben insertarse en caso de instalacion
//	 **************************************************************/
//	public boolean getDataInstallation() {
//		return bDataInstallation;
//	}
//
//	/*****************************************************************
//	 * Método para setear la tabla relacionada con el Sql del objeto *
//	 *****************************************************************/
//	public void setRelatedTable(String oRelatedTable) {
//		sRelatedTable = oRelatedTable;
//	}
//
//	/******************************************************************
//	 * Método para obtener la tabla relacionada con el Sql del objeto *
//	 ******************************************************************/
//	public String getRelatedTable() {
//		return sRelatedTable;
//	}
//
//	/*****************************************
//	 * Setea si la tabla esta replicada o no *
//	 *****************************************/
//	public void setReplicated() {
//		bReplicated = true;
//	}
//
//	/*****************************************
//	 * Setea si la tabla esta replicada o no *
//	 *****************************************/
//	public void setTruncated() {
//		bTruncated = true;
//	}
//
//	/******************************************************************
//	 * Obtiene el nombre de la clase que está realacionada con la tabla
//	 ******************************************************************/
//	public String getTableClass() {
//		return sTableClass;
//	}


} // end Class
