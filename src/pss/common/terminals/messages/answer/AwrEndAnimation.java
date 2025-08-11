package pss.common.terminals.messages.answer;

/**
 * Description:
 *
 * @author Iván Rubín, Ricardo Lamberti
 *
 */

public class AwrEndAnimation extends Answer
{
  @Override
	public boolean isEndAnimation()           { return true; }

  /* Constructs a timeout event with a given end animation */
  public AwrEndAnimation() {
  }

  @Override
	public boolean matches( Answer zEvent ) {
    return zEvent.isEndAnimation();
  }
}
