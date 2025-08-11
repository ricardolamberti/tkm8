package pss.common.terminals.messages.answer;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class AwrEndOfSequence extends Answer {

  @Override
	public boolean isEndOfSequence() { return true; }

  @Override
	public boolean matches(Answer zEvent) {

        if( zEvent.isExternal() ) {
          return true;
        }

        return false;
  }

}
