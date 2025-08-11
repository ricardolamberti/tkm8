package  pss.common.security;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: PuntoSur Soluciones</p>
 * @author PSS
 * @version 1.0
 */

public class JInvalidUserException extends LoginException {

  public JInvalidUserException() {
    super("99", "El usuario es inválido");
  }

}
