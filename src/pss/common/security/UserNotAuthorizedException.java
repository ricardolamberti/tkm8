package  pss.common.security;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class UserNotAuthorizedException extends LoginException {

  public UserNotAuthorizedException(String zErrorCode, String zMessage) {
    super(zErrorCode, zMessage);
  }
  public UserNotAuthorizedException(String zOrigin, String zErrorCode, String zMessage, String zMethodOrigin) {
    super(zOrigin, zErrorCode, zMessage, zMethodOrigin);
  }
  public UserNotAuthorizedException(String zErrorCode, String zMessage, String zOrigin) {
    super(zErrorCode, zMessage, zOrigin);
  }
  public UserNotAuthorizedException() {
    super("99", "Usuario no autorizado");
  }


}
