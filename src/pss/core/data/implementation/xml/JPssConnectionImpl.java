package pss.core.data.implementation.xml;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import pss.core.data.interfaces.connections.JPssConnection;

/**
 * @author PSS
 */
public class JPssConnectionImpl implements JPssConnection {

	private Connection oConnection;
	private static final String TEST_SQL = "SELECT TEST";

	public JPssConnectionImpl() {
		super();
	}

	public void testConnection() throws SQLException {
		Statement oStatement = this.oConnection.createStatement();
		oStatement.execute(JPssConnectionImpl.TEST_SQL);
		oStatement.close();
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
