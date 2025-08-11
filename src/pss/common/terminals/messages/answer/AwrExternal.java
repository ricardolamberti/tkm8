package pss.common.terminals.messages.answer;

/**
 * Description:
 *
 * @author Iv�n Rub�n, Ricardo Lamberti
 *
 */

public class AwrExternal extends Answer {

  public AwrExternal() {
  }

  @Override
	public boolean isExternal()          { return true; }

  @Override
	public boolean matches( Answer zEvent ) {

    if( zEvent.isExternal() ) {
      return true;
    }

    return false;
  }
}
