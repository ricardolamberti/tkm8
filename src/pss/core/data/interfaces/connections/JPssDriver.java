package pss.core.data.interfaces.connections;

import java.net.SocketException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;

/**
 * @author rasensio
 */
public class JPssDriver {

  protected String sConnectionInterface;
  protected String sURL;
  protected String sUser;
  protected String sPassword;
  protected String sDriverName;
  protected long lLockTimeoutSeconds;
  protected JList<Driver> oDrivers = JCollectionFactory.createList();

  /**
   * Constructor
   */
  public JPssDriver() {}

  /**
   * Initializes the driver manager
   */
  public void initialize(String zUrl, String zUser, String zPassword, String
                         zConnectionInterface, String zDriverName, long zLockTimeoutSeconds) throws Exception {
    this.sURL = zUrl;
    this.sUser = zUser;
    this.sPassword = zPassword;
    this.sConnectionInterface = zConnectionInterface;
    this.sDriverName = zDriverName;
    this.lLockTimeoutSeconds = zLockTimeoutSeconds;
    if (zDriverName.equals("NO-DRIVER")) return;
    try { // register the driver and adds it to the list
      Driver oDriver = (Driver)Class.forName(zDriverName).newInstance();
      DriverManager.registerDriver(oDriver);
      oDrivers.addElement(oDriver);
      PssLogger.logDebug("Se registró el driver '" + zDriverName + "'" );
    } catch (Exception ex) {
      PssLogger.logError(ex);
      JExcepcion.SendError("Pss Driver Manager: No puedo registrar el driver JDBC^ '" + zDriverName + "'" );
    }
  }

  public JPssConnection createConnection(boolean zIsReportDatabase) throws Exception {
    JPssConnection oConnection;
    if( this.sUser == null || this.sUser.length() == 0 ) {
      oConnection = this.createConnection(this.sURL);
    } else {
      oConnection = this.createConnection(this.sURL, this.sUser, this.sPassword);
    }
    oConnection.initialize(this.lLockTimeoutSeconds, zIsReportDatabase);
    return oConnection;
  }

  /**
   * Returns a new connection based on the setted interface
   */
  protected JPssConnection createConnection(String zURL) throws Exception {
    this.sURL = zURL;
    JPssConnection oConnection = this.createConnectionImpl();
    oConnection.wrap(DriverManager.getConnection(zURL));
    return oConnection;
  }
  /**
   * Returns a new connection based on the setted interface
   */
  protected JPssConnection createConnection(String zURL, String zUser, String zPassword) throws Exception {
    this.sURL = zURL;
    JPssConnection oConnection = this.createConnectionImpl();
    oConnection.wrap(DriverManager.getConnection(zURL, zUser, zPassword));
    return oConnection;
  }
  /**
   * Creates a JPssConnection implementation
   */
  protected JPssConnection createConnectionImpl() throws Exception {
    return (JPssConnection)Class.forName(this.sConnectionInterface).newInstance();
  }
  public String getDatabaseName() {
    return "";
  }

  public String getHostName() {
    return "";
  }

  /**
   * Release the drivers registered
   */
  public void releaseDrivers() {
    JIterator<Driver> oIterator = this.oDrivers.getIterator();
    while (oIterator.hasMoreElements()) {
      Driver oDriver = oIterator.nextElement();
      try {
        DriverManager.deregisterDriver(oDriver);
        PssLogger.logDebug("Driver JDBC '" + oDriver.getClass().getName() + "' deregistrado");
      } catch (SQLException ex) {
        PssLogger.logError("No puedo deregistrar el driver JDBC '" + oDriver.getClass().getName() + "'" );
        PssLogger.logError(ex);
      }
    }
  }
  /**
   * Throws the connection broken exception
   */
  public void throwConnectionBroken(SQLException zException) throws Exception {
    if (zException.getSQLState() != null) {
      if (zException.getSQLState().equalsIgnoreCase(JBaseJDBC.CODE_CONN_BROKEN)
        || zException.getSQLState().equalsIgnoreCase(JBaseJDBC.CODE_CONN_UNABLE_TO_ESTABLISH)
        || zException.getSQLState().equalsIgnoreCase(JBaseJDBC.CODE_CONN_BROKEN_JTDS)
        || zException.getSQLState().equalsIgnoreCase(JBaseJDBC.CODE_CONN_UNESTABLISHED)
        || zException.getSQLState().equalsIgnoreCase(JBaseJDBC.CODE_CONN_REJECTED)) {
          PssLogger.logDebug("Error de conexión con la base [" + zException + "]");
          throw new JConnectionBroken(zException);
      }
    }
    throw zException;
  }
  /**
   * When a socket exception ocurred
   */
  public void throwConnectionBroken(SocketException zException) throws Exception {
    PssLogger.logError(zException);
    throw new JConnectionBroken(zException);
  }
  /**
   * must throw exception
   */
  public boolean isConnectionBroken(SQLException zException) throws Exception {
    return false;
  }

  public boolean isSystemAuthenticated() {
    return false;
  }

  public Connection getConnectionForReports() throws Exception {
    JBaseJDBC oBaseDefault = (JBaseJDBC)JBDatos.GetBases().getPrivateCurrentDatabase();
    return oBaseDefault.GetConnection();
  }

  public boolean isAntiBlockingSystemEnabled() {
    return false;
  }


}
