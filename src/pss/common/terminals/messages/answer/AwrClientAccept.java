package pss.common.terminals.messages.answer;

/**
 * Description:
 *
 * @author Iván Rubín
 *
 */

public class AwrClientAccept extends AwrOperKey {

  public AwrClientAccept() {
    super( (char)0xd );
  }

  @Override
	public boolean isAccept() { return true; }

  /* any problems answering that i'm a key too, inherited from JKey??? */

  /* actually I can leave matches as it stands, asking to the matching
     event whether it is a key and matching the key code then */
}
