/*
 * Created on 05/12/2003
 */
package pss.core.data.interfaces.connections;

import pss.core.tools.JExcepcion;

/**
 * @author rasensio
 */
public class JDatabaseNotFound extends JExcepcion {

	public JDatabaseNotFound(String zErrCode, String zMje) {
		super(zErrCode, zMje);
	}
  
  public static void sendError(String zMessage) throws Exception {
    throw new JDatabaseNotFound("99", zMessage);
  }

}
