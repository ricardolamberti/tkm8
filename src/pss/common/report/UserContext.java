package pss.common.report;

import pss.common.security.BizUsuario;

/**
 * Wrapper around {@link BizUsuario} exposing only the information required for
 * report generation.
 */
public final class UserContext {
  
  
    public static BizUsuario getUsuario() throws Exception {
        return BizUsuario.getUsr();
    }
 

  /**
   * Convenience factory creating the context for the currently authenticated
   * user.
   */
  public static UserContext fromCurrentUser() throws Exception {
      return new UserContext();
  }
}

 
