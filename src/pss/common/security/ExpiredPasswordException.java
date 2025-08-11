package  pss.common.security;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class ExpiredPasswordException extends LoginException {

  public ExpiredPasswordException(String zMessage) {
    super(zMessage);
  }

}
