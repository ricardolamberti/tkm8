package pss.common.terminals.messages.answer;

/**
 * Description:
 * @author Iván Rubín
 */

public class AwrClientCancel extends AwrOperKey {

  public AwrClientCancel() {
    super( (char)0x1b );
  }

  @Override
	public boolean isCancel() { return true; }

  /* any problems answering that i'm a key too, inherited from JKey??? */

  /* actually I can leave matches as it stands, answering for the matching
     event whether it is a key and matching the key code then */
}
