/*
 * Created on 30/04/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package pss.core.data.implementation.sqlserver8;

import pss.core.data.interfaces.connections.JPssDriver;

/**
 * @author rasensio
 */
public class JSQLConnectDriverImpl extends JPssDriver {

	public JSQLConnectDriverImpl() {
		super();
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

  @Override
	public boolean isAntiBlockingSystemEnabled() {
    return true;
  }

}
