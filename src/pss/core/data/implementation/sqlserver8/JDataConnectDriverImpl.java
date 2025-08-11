/*
 * Created on 28/04/2003
 */
package pss.core.data.implementation.sqlserver8;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import pss.core.data.interfaces.connections.JBDato;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.data.interfaces.connections.JBaseJDBC;
import pss.core.data.interfaces.connections.JConnectionBroken;
import pss.core.data.interfaces.connections.JDatabaseNotFound;
import pss.core.data.interfaces.connections.JPssConnection;
import pss.core.data.interfaces.connections.JPssDriver;
import pss.core.tools.PssLogger;

/**
 * @author rasensio
 */
public class JDataConnectDriverImpl extends JPssDriver {

  private static final String CLIENT_LICENCE = "7998093774919805";


  public JDataConnectDriverImpl() {
    super();
  }
  /**
   * Implmenetation of create connection method
   */
  @Override
	public JPssConnection createConnection(String zURL) throws Exception {
    this.sURL = zURL;
    Properties oProperties = new Properties();
    oProperties.put("connectString", "DRIVER={SQL Server};Server=" + this.getHostName() + ";Database=" + this.getDatabaseName());
    oProperties.put("clientLicense", CLIENT_LICENCE);
    Driver oDriver = DriverManager.getDriver(zURL);
    Connection oConnection = oDriver.connect(zURL, oProperties);
    JPssConnection oConnectionImpl = this.createConnectionImpl();
    oConnectionImpl.wrap(oConnection);
    return oConnectionImpl;
  }
  /**
   * Devuelve el nombre de la máquina
   */
  @Override
	public String getHostName() {
    String sBeginMachine = this.sURL.substring(this.sURL.lastIndexOf("//") + 2);
    String sMachine = sBeginMachine.substring(0, sBeginMachine.indexOf("/"));
    return sMachine;
  }
  /**
   * Returns the database name parsed
   */
  @Override
	public String getDatabaseName() {
    String sBeginMachine = this.sURL.substring(this.sURL.lastIndexOf("//") + 2);
    //String sMachine = sBeginMachine.substring(0, sBeginMachine.indexOf("/"));
    String sBeginBase = sBeginMachine.substring(sBeginMachine.indexOf("/") + 1);
    int iUseCursors = sBeginBase.indexOf("/");
    String sDatabase;
    if (iUseCursors == -1) {
      sDatabase = sBeginBase; // no encontro useCursors...
    } else {
      sDatabase = sBeginBase.substring(0, iUseCursors); // lo encontro...
    }
    return sDatabase;
  }
  /**
   * Arroja una exception JConnectionBroken en caso que el error corresponda
   * a una périda de conexión con la base de datos. Caso contrario, lanza la
   * exception original(el parámetro)
   * @param zSQLex Exception que se evaluará para saber si es una falla de conexión
   */
  @Override
	public void throwConnectionBroken(SQLException zException) throws Exception {
    if (zException.getSQLState() != null) {
      if (zException.getSQLState().equalsIgnoreCase(JBaseJDBC.CODE_CONN_BROKEN)
        || zException.getSQLState().equalsIgnoreCase(JBaseJDBC.CODE_CONN_UNABLE_TO_ESTABLISH)
        || zException.getSQLState().equalsIgnoreCase(JBaseJDBC.CODE_CONN_UNESTABLISHED)
        || zException.getSQLState().equalsIgnoreCase(JBaseJDBC.CODE_CONN_REJECTED)) {
          PssLogger.logDebug("Error de conexión con la base [" + zException + "]");
          throw new JConnectionBroken(zException);
      }
    }
    throw zException;
  }

  @Override
	public boolean isConnectionBroken(SQLException zException) throws Exception {
    if (zException.getSQLState() == null)
      return false;
    PssLogger.logDebug("Verificando si se perdió la conexión: [" + zException + "]");
    if (zException.getSQLState().equalsIgnoreCase(JBaseJDBC.CODE_CONN_BROKEN)
      || zException.getSQLState().equalsIgnoreCase(JBaseJDBC.CODE_CONN_UNABLE_TO_ESTABLISH)
      || zException.getSQLState().equalsIgnoreCase(JBaseJDBC.CODE_CONN_UNESTABLISHED)
      || zException.getSQLState().equalsIgnoreCase(JBaseJDBC.CODE_CONN_REJECTED)) {
      PssLogger.logDebug("Conexión perdida");
      return true;
    }
    return false;
  }

  @Override
	public boolean isSystemAuthenticated() {
    return true;
  }

  @Override
	public Connection getConnectionForReports() throws Exception {
    JBaseJDBC oBaseDefault = (JBaseJDBC)JBDatos.GetBases().getPrivateCurrentDatabase();
    JBDato oJBDCRep = null;
    String nameReport = oBaseDefault.GetName() + "_REPORT";

    try {
      oJBDCRep = JBDatos.GetBases().getDatabaseByName(nameReport);
    } catch (JDatabaseNotFound e) {
      oJBDCRep = JBDatos.getBaseReport();
      JBDatos.GetBases().addItem(oJBDCRep);
      oJBDCRep.open();
    }
    return oJBDCRep.GetConnection();
  }

  @Override
	public boolean isAntiBlockingSystemEnabled() {
    return true;
  }

}
