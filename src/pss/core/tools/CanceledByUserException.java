package pss.core.tools;


public class CanceledByUserException extends Exception {

  public CanceledByUserException() {
    super();
  }

  public CanceledByUserException(String detailedMsg) {
    super(detailedMsg);
  }

}
