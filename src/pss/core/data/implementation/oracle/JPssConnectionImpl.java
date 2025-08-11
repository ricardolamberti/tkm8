/*
 * Created on 28/04/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package pss.core.data.implementation.oracle;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import pss.core.data.interfaces.connections.JPssConnection;
import pss.core.tools.PssLogger;

/**
 * @author rasensio
 */
public class JPssConnectionImpl implements JPssConnection {

	private static final String TEST_SQL = "SELECT 1 FROM DUAL";
	private static final String DATETIME_FORMAT_SQL = "ALTER SESSION SET NLS_DATE_FORMAT='dd/mm/yyyy hh24:mi:ss'";
	private Connection oConnection;


	public JPssConnectionImpl() {
		super();
	}

	public void testConnection() throws SQLException {
		/*Statement oStatement = this.oConnection.createStatement();
		oStatement.execute(TEST_SQL);
		oStatement.close();*/
	}

	public void initialize(long zLockTimeoutSeconds, boolean zIsReportDatabase) throws Exception {
		// establece el formato de fecha por unica vez al crear la conexion - IRA
		Statement stmtDate = this.oConnection.createStatement();
		try {
			stmtDate.executeUpdate(DATETIME_FORMAT_SQL);
			// case insensitive
			//stmtDate.executeUpdate("ALTER SESSION SET NLS_SORT = BINARY_CI");
			//stmtDate.executeUpdate("ALTER SESSION SET NLS_COMP = ANSI");
			//stmtDate.executeUpdate("ALTER SESSION SET NLS_COMP = LINGUISTIC") ;
		} finally {
			if (stmtDate != null) {
				try {
					stmtDate.close();
				} catch (Exception ignored) {
					PssLogger.logError("Pss Database: Error setting dateformat in connection");
				}
			}
		}

	}

	public Connection getConnection() {
		return this.oConnection;
	}

	public void wrap(Connection zConnection) {
		this.oConnection = zConnection;
	}

}
