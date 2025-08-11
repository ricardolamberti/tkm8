package pss.core.data.implementation.postgresql8;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import pss.common.security.BizUsuario;
import pss.core.data.implementation.sqlserver8.JBaseJDBCImpl;
import pss.core.data.interfaces.connections.JPssConnection;
import pss.core.tools.PssLogger;

/**
 * @author PSS
 */
public class JPssConnectionImpl implements JPssConnection {

	// private static final String TEST_SQL = "SELECT 1";
	private static final String DATETIME_FORMAT_SQL = "SET SESSION DateStyle TO ISO, DMY";
	private static final String SET_TIMEZONE = "SET TIME ZONE ";
	private Connection oConnection;


	public JPssConnectionImpl() {
		super();
	}

	public void testConnection() throws SQLException {
		Statement oStatement = this.oConnection.createStatement();
		oStatement.execute(JBaseJDBCImpl.TEST_SQL);
		oStatement.close();
	}

	public void initialize(long zLockTimeoutSeconds, boolean zIsReportDatabase) throws Exception {
		// establece el formato de fecha por unica vez al crear la conexion - IRA
		Statement stmtDate = this.oConnection.createStatement();
		try {
			stmtDate.executeUpdate(DATETIME_FORMAT_SQL);
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
