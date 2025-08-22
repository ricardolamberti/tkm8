package pss.common.cache;

import pss.common.security.BizUsuario;
import pss.www.platform.actions.JWebActionFactory;

/** Resolves user and session identifiers from the current request. */
public final class UserContext {
	private UserContext() {
	}

	/**
	 * Current user identifier.
	 *
	 * @return resolved user id or {@code "anon"} if not available
	 * @throws Exception
	 */
	public static String currentUserId() throws Exception {

		return BizUsuario.getUsr().GetUsuario();
	}

	/**
	 * Current session identifier.
	 *
	 * @return resolved session id or {@code "nopage"} if not available
	 */
	public static String currentSessionId() {
		return JWebActionFactory.getCurrentRequest().getSession().getSessionId();

	}
}
