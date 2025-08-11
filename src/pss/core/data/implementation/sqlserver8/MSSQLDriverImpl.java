/*
 * Created on 28/04/2003
 */
package pss.core.data.implementation.sqlserver8;

import java.sql.SQLException;

import pss.core.data.interfaces.connections.JPssDriver;


/**
 * @author rasensio
 */
public class MSSQLDriverImpl extends JPssDriver {

	public MSSQLDriverImpl() {
		super();
	}

	@Override
	public boolean isConnectionBroken(SQLException zException) throws Exception {
		return false;
	}

  @Override
	public boolean isAntiBlockingSystemEnabled() {
    return true;
  }

}
