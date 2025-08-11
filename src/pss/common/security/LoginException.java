package pss.common.security;

import pss.core.tools.JExcepcion;

public class LoginException extends JExcepcion {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2056491289333495975L;

	public LoginException(String zMessage) {
		super(zMessage);
	}

	public LoginException(String zErrorCode, String zMessage) {
		super(zErrorCode, zMessage);
	}

	public LoginException(String zOrigin, String zErrorCode, String zMessage, String zMethodOrigin) {
		super(zOrigin, zErrorCode, zMessage, zMethodOrigin);
	}

	public LoginException(String zErrorCode, String zMessage, String zOrigin) {
		super(zErrorCode, zMessage, zOrigin);
	}

}
