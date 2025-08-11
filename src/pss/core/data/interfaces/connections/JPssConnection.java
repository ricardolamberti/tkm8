/*
 * Created on 25/04/2003
 */
package pss.core.data.interfaces.connections;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author rasensio
 */
public interface JPssConnection {

	public void testConnection() throws SQLException;

	public void initialize(long zLockTimeoutSeconds, boolean zIsReportDatabase) throws Exception;

	public Connection getConnection();

	public void wrap(Connection zConnection);
}
