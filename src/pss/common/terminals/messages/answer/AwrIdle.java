package pss.common.terminals.messages.answer;

/**
 * Description:
 *
 * @author Iván Rubín
 *
 */

public class AwrIdle extends Answer {

  @Override
	public boolean isIdle() { return true; }

  @Override
	public boolean matches( Answer zEvent ) {
    boolean bAnswer = false;

    if( zEvent.isIdle() ) {
      bAnswer = true;
    }

    return bAnswer;
  }

}
