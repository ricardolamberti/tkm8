package pss.common.terminals.messages.answer;

/**
 * Description:
 * @author Iván Rubín
 */

public class AwrManualCancel extends Answer {

  public AwrManualCancel() {
  }

  @Override
	public boolean isManualCancel() { return true; }

  @Override
	public boolean matches( Answer zEvent ) {

    if( zEvent.isManualCancel() ) {
      return true;
    }

    return false;
  }

  /* any problems answering that i'm a key too, inherited from JKey??? */

  /* actually I can leave matches as it stands, answering for the matching
     event whether it is a key and matching the key code then */
}
