
package pss.common.security;

/**
 * @author rasensio
 */
public class WrongPasswordException extends LoginException {


  public WrongPasswordException(String zMessage) {
    super(zMessage);
  }

}
