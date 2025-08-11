package pss.core.data.implementation.h2;

import java.sql.SQLException;

import pss.core.data.interfaces.connections.JBDato;
import pss.core.data.interfaces.connections.JConnectionBroken;
import pss.core.data.interfaces.connections.JPssDriver;
import pss.core.tools.PssLogger;

public class JH2DriverImpl extends JPssDriver {

	public JH2DriverImpl() {
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
		if (zException.getErrorCode() == 17002) { 
			PssLogger.logDebug("error connecting to databawe " + zException );
			throw new JConnectionBroken(zException);
		}
		throw zException;
	}
	
	@Override
	public boolean isConnectionBroken(SQLException zException) throws Exception {
		return false;
	}

}
