/**
 * 
 */
package  pss.common.dbManagement.synchro;

import pss.core.tools.JExcepcion;

/**
 * Clase que representa un excepcin generada por en error de sintaxis en los parametros
 * 
 */
public class JSyntaxExcepcion extends JExcepcion {

	/**
	 * Constructor
	 * 
	 * @param msg
	 */
	public JSyntaxExcepcion(String msg) {
		super(msg);
	}
}	