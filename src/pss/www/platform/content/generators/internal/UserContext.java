package pss.www.platform.content.generators.internal;

import pss.common.security.BizUsuario;

/**
 * Wrapper exposing only the information required for report generation.
 */
public final class UserContext {

	private final String userId;

	private UserContext(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	/**
	 * Creates a context from the given {@link BizUsuario}.
	 */
	public static UserContext from(BizUsuario usr) throws Exception {
		return new UserContext(usr.GetCertificado());
	}

	/**
	 * Convenience factory creating the context for the currently authenticated
	 * user.
	 */
	public static UserContext fromCurrentUser() throws Exception {
		return from(BizUsuario.getUsr());
	}
}
