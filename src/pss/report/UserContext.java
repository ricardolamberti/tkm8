package pss.report;

import pss.common.security.BizUsuario;

/**
 * Wrapper around {@link BizUsuario} exposing only the information required for
 * report generation.
 */
public final class UserContext {
    private final BizUsuario usuario;

    public UserContext(BizUsuario usuario) {
        this.usuario = usuario;
    }

    public BizUsuario getUsuario() {
        return usuario;
    }

    public String getLogin() throws Exception {
        return usuario.getUsuario();
    }
}
