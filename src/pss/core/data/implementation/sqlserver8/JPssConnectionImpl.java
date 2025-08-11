/*
 * Created on 25/04/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package pss.core.data.implementation.sqlserver8;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import pss.core.data.interfaces.connections.JPssConnection;

/**
 * @author rasensio
 */
public class JPssConnectionImpl implements JPssConnection {

  private Connection oConnection;

  public JPssConnectionImpl() {
  }

  public void testConnection() throws SQLException {
    Statement oStatement = this.oConnection.createStatement();
    oStatement.execute(JBaseJDBCImpl.TEST_SQL);
    oStatement.close();
  }

  /**
   * Initializes the connection setting once the DateTime format
   */
  public void initialize(long zLockTimeoutSeconds, boolean zIsReportDatabase) throws Exception {
    this.oConnection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
    this.oConnection.setAutoCommit(false);
    Statement oStatement = this.oConnection.createStatement();
    try {
      oStatement.executeUpdate(JBaseJDBCImpl.DATETIME_FORMAT_SQL);
      if (!zIsReportDatabase) {
        oStatement.executeUpdate(JBaseJDBCImpl.LOCK_TIMEOUT + String.valueOf(zLockTimeoutSeconds * 1000));
      }
    } finally {
      if (oStatement != null) {
        try {
          oStatement.close();
        } catch (SQLException e) {}
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
