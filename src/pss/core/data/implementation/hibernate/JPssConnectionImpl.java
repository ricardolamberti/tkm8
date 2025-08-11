package pss.core.data.implementation.hibernate;

import java.sql.Connection;
import java.sql.SQLException;

import pss.core.data.interfaces.connections.JPssConnection;

/**
 * @author PSS
 */
public class JPssConnectionImpl implements JPssConnection {

	// private static final String TEST_SQL = "SELECT 1";
	private Connection oConnection;


	public JPssConnectionImpl() {
		super();
	}

	public void testConnection() throws SQLException {
	}

	public void initialize(long zLockTimeoutSeconds, boolean zIsReportDatabase) throws Exception {
		

	}

	public Connection getConnection() {
		return this.oConnection;
	}

	public void wrap(Connection zConnection) {
		this.oConnection = zConnection;
	}

}
