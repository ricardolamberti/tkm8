package pss.common.terminals.messages.answer;

/**
 * Description:
 *
 * @author Iván Rubín, Ricardo Lamberti
 *
 */

public class AwrTimeout extends Answer
{
  private long lTimeout;

  public long getTimeout() { return this.lTimeout; }

  @Override
	public boolean isTimeout() { return true; }

  public AwrTimeout() {
  }
  public AwrTimeout( long zTimeout ) {
    if( zTimeout < 0 ) {
      throw new RuntimeException( "Invalid timeout " + zTimeout );
    }

    this.lTimeout = zTimeout;
  }

  @Override
	public boolean matches( Answer zEvent ) {
    /* talking about timeout */
    if( zEvent.isTimeout() ) {

      /* Will match on elapsed time (zEvent's timeout time) greater or equal than this */
      if( ((AwrTimeout)zEvent).getTimeout() >= this.lTimeout ) {
        return true;
      }
    }
    return false;
  }
}
