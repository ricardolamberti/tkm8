package pss.report;

import pss.common.security.BizUsuario;

/**
 * Minimal information required for report generation. Instances are created
 * from the current {@link BizUsuario} using its certificate and login name.
 */
public final class UserContext {
    private final String certificado;
    private final String login;

    public UserContext(String certificado, String login) {
        this.certificado = certificado;
        this.login = login;
    }

    public String getCertificado() {
        return certificado;
    }

    public String getLogin() {
        return login;
    }

    /**
     * Convenience factory creating the context for the currently authenticated
     * user.
     */
    public static UserContext fromCurrentUser() throws Exception {
        BizUsuario usr = BizUsuario.getUsr();
        return new UserContext(usr.GetCertificado(), usr.getUsuario());
    }
}
