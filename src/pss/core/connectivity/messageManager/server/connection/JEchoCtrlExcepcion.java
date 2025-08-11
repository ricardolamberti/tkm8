package pss.core.connectivity.messageManager.server.connection;

import pss.core.tools.JExcepcion;

public class JEchoCtrlExcepcion extends JExcepcion {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3328529341443669638L;

	public JEchoCtrlExcepcion(String errCode, String mje, String funcOrigen) {
		super(errCode, mje, funcOrigen);
		// TODO Auto-generated constructor stub
	}

	public JEchoCtrlExcepcion(String errCode, String msg) {
		super(errCode, msg);
		// TODO Auto-generated constructor stub
	}

	public JEchoCtrlExcepcion(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

	public JEchoCtrlExcepcion(String code, String message, Throwable cause) {
		super(code, message, cause);
		// TODO Auto-generated constructor stub
	}

	public JEchoCtrlExcepcion(String origen, String errCode, String mje, String funcOrigen) {
		super(origen, errCode, mje, funcOrigen);
		// TODO Auto-generated constructor stub
	}

}
