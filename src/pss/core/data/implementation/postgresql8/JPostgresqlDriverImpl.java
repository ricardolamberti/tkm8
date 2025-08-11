package pss.core.data.implementation.postgresql8;

import java.sql.SQLException;

import pss.core.data.interfaces.connections.JBDato;
import pss.core.data.interfaces.connections.JConnectionBroken;
import pss.core.data.interfaces.connections.JPssDriver;
import pss.core.tools.PssLogger;

public class JPostgresqlDriverImpl extends JPssDriver {

	public JPostgresqlDriverImpl() {
		super();
	}
  
  @Override
	public String getDatabaseName() {
    int iIndex = this.sURL.lastIndexOf(':');
    return this.sURL.substring(iIndex+1);
  }
  
  public void setParamsMaster(JBDato zBaseSource) throws Exception {
  }
  
  
	@Override
	public void throwConnectionBroken(SQLException zException) throws Exception {
		if (zException.getErrorCode() == 17002 || zException.getSQLState().equals("08006")) { 
			PssLogger.logDebug("error connecting to database " + zException );
			throw new JConnectionBroken(zException);
		}
		throw zException;
	}
	
	@Override
	public boolean isConnectionBroken(SQLException zException) throws Exception {
		return false;
	}

}
