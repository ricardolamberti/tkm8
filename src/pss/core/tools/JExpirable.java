package pss.core.tools;

/**
 *
 * Description: An object that expires should implement this interface
 *
 */

public interface JExpirable {

  public boolean hasExpired() throws Exception;
}
