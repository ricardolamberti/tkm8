/*
 * Created on 25/04/2003
 */
package pss.core.data.implementation.sqlserver8;

import pss.core.data.interfaces.connections.JBaseJDBC;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.tools.JNativeTools;
import pss.core.tools.PssLogger;

/**
 * @author rasensio
 */
public class JBaseJDBCImpl extends JBaseJDBC {

	public static final int ERROR_LOCK_TIMEOUT = 1222;
	public static final int ERROR_ARITHMETIC_OVERFLOW = 8115;
	public static final int ERROR_ILEGAL_DATETIME = 242;
	public static final int ERROR_DUPLICATE_KEY = 2601;
	public static final String TEST_SQL = "SELECT 1";
	public static final String DATETIME_FORMAT_SQL = "SET DATEFORMAT DMY";
	public static final String LOCK_TIMEOUT = "SET LOCK_TIMEOUT ";
	public static final String SYNCHRONIZE_DATE_SQL = "SELECT CONVERT(VARCHAR(20), GETDATE(), 120) AS date";
	private static final String DESCRIPTION = "SQLServer 2000";
	// private static final String METADATA_COLUMN_IMPL =
	// "pss.core.data.metadata.JColumnaSQLSERVER";
	private static final String METADATA_COLUMN_IMPL = "pss.common.dbManagement.synchro.JDBColumnaSQLSERVER";
	// private static final String METADATA_COLUMNS_IMPL =
	// "pss.core.data.metadata.JColumnasSQLSERVER";
	private static final String METADATA_COLUMNS_IMPL = "pss.common.dbManagement.synchro.JDBColumnasSQLSERVER";
	// private static final String METADATA_INDEX_IMPL =
	// "pss.core.data.metadata.JIndiceSQLSERVER";
	private static final String METADATA_INDEX_IMPL = "pss.common.dbManagement.synchro.JDBIndiceSQLSERVER";
	// private static final String METADATA_INDEXES_IMPL =
	// "pss.core.data.metadata.JIndicesSQLSERVER";
	private static final String METADATA_INDEXES_IMPL = "pss.common.dbManagement.synchro.JDBIndicesSQLSERVER";
	// private static final String METADATA_TABLE_IMPL =
	// "pss.core.data.metadata.JTablaSQLSERVER";
	private static final String METADATA_TABLE_IMPL = "pss.common.dbManagement.synchro.JDBTablaSQLSERVER";
	// private static final String METADATA_TABLES_IMPL =
	// "pss.core.data.metadata.JTablasSQLSERVER";
	private static final String METADATA_TABLES_IMPL = "pss.common.dbManagement.synchro.JDBTablasSQLSERVER";
	// private static final String SETUP_COMPARISON_IMPL =
	// "pss.common.dbManagement.synchronice.JTableComparisonSQLServer8";
	private static final String SETUP_COMPARISON_IMPL = "pss.common.dbManagement.synchro.JDBTableComparisonSQLServer8";
	private static final String CONNECTION_IMPL = "pss.core.data.implementation.sqlserver8.JPssConnectionImpl";
	private static final String ROW_IMPL = "pss.core.data.implementation.sqlserver8.JRegJDBCImpl";

	public JBaseJDBCImpl() throws Exception {
		super();
	}

	public void testConnection() throws Exception {
		JBaseRegistro oRegistro = this.createRowInterface();
		oRegistro.ExecuteQuery("select @@spid spid");
		oRegistro.next();
		PssLogger.logWait("ConnectionSPID: " + oRegistro.CampoAsStr("spid"));
		oRegistro.close();
	}

	@Override
	public String getDatabaseName() {
		int iIndex = this.sConnectionUrl.lastIndexOf("/");
		return this.sConnectionUrl.substring(iIndex + 1);
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
		return "authadmsqlserver8.dat";
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
	 * Synchronize the system and the database time through native code
	 */
	@Override
	public void synchronizeSystemAndDataBaseTime() throws Exception {
		JBaseRegistro oReg = JBaseRegistro.VirtualCreate();
		oReg.ExecuteQuery(SYNCHRONIZE_DATE_SQL);
		// oReg.First(); 26-05-2005 MM No anda el FIRST en este tipo de registros
		oReg.next();
		String dateTime = oReg.CampoAsStr("date");
		int iYear = Integer.parseInt(dateTime.substring(0, 4));
		int iMonth = Integer.parseInt(dateTime.substring(5, 7));
		int iDay = Integer.parseInt(dateTime.substring(8, 10));
		int iHour = Integer.parseInt(dateTime.substring(11, 13));
		int iMinute = Integer.parseInt(dateTime.substring(14, 16));
		int iSecond = Integer.parseInt(dateTime.substring(17, 19));

		oReg.close();
		if (System.getProperty("os.name").startsWith("Windows")) {
			// not supported in POSIX, yet!
			if (dateTime.length() == 19) {
				if (JNativeTools.setSystemDateAndTime(iYear, iMonth, iDay, iHour, iMinute, iSecond, 500) != 0) {
					PssLogger.logError("No se pudo sincronizar la hora con la pc de la base de datos.");
				}
			}
		}
	}

	/**
	 * Establece los valores de un JBDato para usar el JReport, y agrega una
	 * conexión al pool
	 */

	@Override
	protected void asignMasterParams() throws Exception {
		String sUntilMachine = this.getConnectionURL().substring(0, this.getConnectionURL().lastIndexOf("/") + 1);
		this.sConnectionUrl = sUntilMachine + "Master";
	}

	@Override
	public boolean isSQLServer() {
		return true;
	}

	@Override
	public boolean isSQL92() {
		return true;
	}

	// public boolean isAutoCommit() {return true;}

	/**
	 * <p>
	 * Setear en cada variable que resuelve un tipo de objeto de la base de datos
	 * la clase que implementa en forma fisica o virtual el objeto de la base de
	 * datos correspondiente
	 * </p>
	 */
	protected void initializeVirtualClassesNames() {
		systemDBFieldImpl = new String("pss.common.dbManagement.synchro.sqlServer8.JSqlserver8SystemDBField");
		systemDBFieldsImpl = new String("pss.common.dbManagement.synchro.sqlServer8.JSqlserver8SystemDBFields");
		systemDBIndexImpl = new String("pss.common.dbManagement.synchro.sqlServer8.JSqlserver8SystemDBIndex");
		systemDBIndexesImpl = new String("pss.common.dbManagement.synchro.sqlServer8.JSqlserver8SystemDBIndexes");
		systemDBTableImpl = new String("pss.common.dbManagement.synchro.sqlServer8.JSqlserver8SystemDBTable");
		systemDBTablesImpl = new String("pss.common.dbManagement.synchro.sqlServer8.JSqlserver8SystemDBTables");
		virtualDBFieldImpl = new String("pss.common.dbManagement.synchro.sqlServer8.JSqlserver8VirtualDBField");
		virtualDBFieldsImpl = new String("pss.common.dbManagement.synchro.sqlServer8.JSqlserver8VirtualDBFields");
		virtualDBIndexImpl = new String("pss.common.dbManagement.synchro.sqlServer8.JSqlserver8VirtualDBIndex");
		virtualDBIndexesImpl = new String("pss.common.dbManagement.synchro.sqlServer8.JSqlserver8VirtualDBIndexes");
		virtualDBTableImpl = new String("pss.common.dbManagement.synchro.sqlServer8.JSqlserver8VirtualDBTable");
	}
}
