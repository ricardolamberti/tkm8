package pss.core.data.implementation.postgresql8;

import java.sql.Statement;

import pss.common.security.BizUsuario;
import pss.core.data.connectionPool.JDBConnectionPools;
import pss.core.data.interfaces.connections.JBDato;
import pss.core.data.interfaces.connections.JBaseJDBC;
import pss.core.data.interfaces.connections.JConnectionBroken;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.tools.PssLogger;

/**
 * @author PSS
 */
public class JBaseJDBCImpl extends JBaseJDBC {

	private static final String DESCRIPTION = "Postgresql 8";
	private static final String METADATA_COLUMN_IMPL = "pss.common.dbManagement.synchro.JDBColumnaPostgresql";
	private static final String METADATA_COLUMNS_IMPL = "pss.common.dbManagement.synchro.JDBColumnasPostgresql";
	private static final String METADATA_INDEX_IMPL = "pss.common.dbManagement.synchro.JDBIndicePostgresql";
	private static final String METADATA_INDEXES_IMPL = "pss.common.dbManagement.synchro.JDBIndicesPostgresql";
	private static final String METADATA_TABLE_IMPL = "pss.common.dbManagement.synchro.JDBTablaPostgresql";
	private static final String METADATA_TABLES_IMPL = "pss.common.dbManagement.synchro.JDBTablasPostgresql";
	private static final String SETUP_COMPARISON_IMPL = "pss.common.dbManagement.synchro.JDBTableComparisonPostgresql8";
	private static final String CONNECTION_IMPL = "pss.core.data.implementation.postgresql8.JPssConnectionImpl";
	private static final String ROW_IMPL = "pss.core.data.implementation.postgresql8.JRegJDBCImpl";

	public JBaseJDBCImpl() throws Exception {
		super();
	}

	public void initializeConnectionByUser(BizUsuario usr) throws Exception {
		if (usr==null) 
			return;
		Statement stmtDate = this.connection.createStatement();
		try {
			PssLogger.logDebugSQL("Set TIME ZONE "+usr.getGMT());
			stmtDate.executeUpdate("Set TIME ZONE "+usr.getGMT());
		} finally {
			if (stmtDate != null) {
				try {
					stmtDate.close();
				} catch (Exception ignored) {
					PssLogger.logError("Pss Database: Error setting time zone in connection");
				}
			}
		}
	}
	public void testConnection() throws Exception {
		JBaseRegistro oRegistro = this.createRowInterface();
		oRegistro.ExecuteQuery("SELECT * FROM pg_stat_activity ");
		oRegistro.next();
		oRegistro.close();
	}

	@Override
	public String getDatabaseDescription() {
		return DESCRIPTION;
	}

	@Override
	public String getMetadataColumnImpl() {
		return METADATA_COLUMN_IMPL;
	}

	@Override
	public String getMetadataColumnsImpl() {
		return METADATA_COLUMNS_IMPL;
	}

	@Override
	public String getMetadataIndexImpl() {
		return METADATA_INDEX_IMPL;
	}

	@Override
	public String getMetadataIndexesImpl() {
		return METADATA_INDEXES_IMPL;
	}

	@Override
	public String getMetadataTableImpl() {
		return METADATA_TABLE_IMPL;
	}

	@Override
	public String getMetadataTablesImpl() {
		return METADATA_TABLES_IMPL;
	}

	@Override
	protected int timeoutValidConnection() throws Exception {
		return 10;
	}
	
	@Override
	public String getDatabaseName() {
		int iIndex = this.sConnectionUrl.lastIndexOf(":");
		return this.sConnectionUrl.substring(iIndex + 1);
	}

	@Override
	public String getSetupTableComparisonImpl() {
		return SETUP_COMPARISON_IMPL;
	}

	@Override
	public String getConnectionInterface() {
		return CONNECTION_IMPL;
	}

	@Override
	public String getRowInterface() {
		return ROW_IMPL;
	}

	@Override
	public String getAdminUserFileName() {
		return "auth.dat";
	}

	@Override
	public String getCommonUserFileName() {
		return "auth.dat";
	}

	@Override
	public String getLowerCaseInstruction() {
		return "lower";
	}

	/**
	 * Creates the master database
	 */
	public void setParamsMaster(JBDato zBaseSource) throws Exception {
		this.SetName(zBaseSource.GetName() + "_CONFIG");
		this.SetInterface(zBaseSource.GetInterface());
		this.SetClassInterface(zBaseSource.GetClassInterface());
		// this.sBaseDatos = zBaseSource.GetName() + "_CONFIG";
		this.sUsername = zBaseSource.GetUsername();
		this.sPassword = zBaseSource.getPassword();
		this.sUserService = zBaseSource.GetUserService();
		this.sAuth = zBaseSource.getAuthenticationMode();
		this.sDriverName = zBaseSource.GetDriverName();
		this.sDriverInterface = zBaseSource.getDriverInterface();
		this.sConnectionInterface = zBaseSource.getConnectionInterface();
		this.sConnectionUrl = zBaseSource.getConnectionURL();
		// int iMaxConnections = -1;

		// Cambia la base Default por la base Master jdbc:JSQLConnect://Dev2/Pss
		// String sUntilMachine = zBaseSource.getConnectionURL().substring(0,
		// zBaseSource.getConnectionURL().lastIndexOf(":") + 1);
		// this.sConnectionUrl = sUntilMachine + this.sUsername;

		oConnectionPools = JDBConnectionPools.getInstance();
		this.oDriverImpl = this.createDriverImpl(this.sConnectionUrl, this.sUsername, this.sPassword, this.sDriverName, this.sConnectionInterface, this.sDriverInterface, this.lLockTimeoutSeconds);
		// this.oConnectionPools.addPool(this.sBaseDatos, iMaxConnections, this.oDriverImpl);
	}
	
  @Override
	public boolean isPostGreSQL() {return true;}	

	/**
	 * <p>
	 * Setear en cada variable que resuelve un tipo de objeto de la base de datos la clase que
	 * implementa en forma fisica o virtual el objeto de la base de datos correspondiente
	 * </p>
	 */
	protected void initializeVirtualClassesNames() {
		systemDBFieldImpl = new String("pss.common.dbManagement.synchro.postgreSQL8.JPostgreSQL8SystemDBField");
		systemDBFieldsImpl = new String("pss.common.dbManagement.synchro.postgreSQL8.JPostgreSQL8SystemDBFields");
		systemDBIndexImpl = new String("pss.common.dbManagement.synchro.postgreSQL8.JPostgreSQL8SystemDBIndex");
		systemDBIndexesImpl = new String("pss.common.dbManagement.synchro.postgreSQL8.JPostgreSQL8SystemDBIndexes");
		systemDBTableImpl = new String("pss.common.dbManagement.synchro.postgreSQL8.JPostgreSQL8SystemDBTable");
		systemDBTablesImpl = new String("pss.common.dbManagement.synchro.postgreSQL8.JPostgreSQL8SystemDBTables");
		virtualDBFieldImpl = new String("pss.common.dbManagement.synchro.postgreSQL8.JPostgreSQL8VirtualDBField");
		virtualDBFieldsImpl = new String("pss.common.dbManagement.synchro.postgreSQL8.JPostgreSQL8VirtualDBFields");
		virtualDBIndexImpl = new String("pss.common.dbManagement.synchro.postgreSQL8.JPostgreSQL8VirtualDBIndex");
		virtualDBIndexesImpl = new String("pss.common.dbManagement.synchro.postgreSQL8.JPostgreSQL8VirtualDBIndexes");
		virtualDBTableImpl = new String("pss.common.dbManagement.synchro.postgreSQL8.JPostgreSQL8VirtualDBTable");
	}

}
