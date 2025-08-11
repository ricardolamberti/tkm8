package pss.core.data.implementation.hibernate;

import java.sql.SQLException;

import pss.core.data.interfaces.connections.JBDato;
import pss.core.data.interfaces.connections.JPssDriver;

public class JHibernateDriverImpl extends JPssDriver {

	public JHibernateDriverImpl() {
		super();
	}
  
  @Override
	public String getDatabaseName() {
    return "";
  }
  
  public void setParamsMaster(JBDato zBaseSource) throws Exception {
  }
  
  
	@Override
	public void throwConnectionBroken(SQLException zException) throws Exception {
		
	}
	
	@Override
	public boolean isConnectionBroken(SQLException zException) throws Exception {
		return false;
	}

}
