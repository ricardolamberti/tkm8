package pss.common.terminals.messages.answer;

/**
 * Description:
 *
 * @author Iván Rubín, Ricardo Lamberti
 *
 */

public class AwrStringInput extends Answer {
  public static final String ANY_INPUT = null;

  private String sData;

  public String getData() { return this.sData; }

  /**
   *  Constructs a stringinput event involving any input
   */
  public AwrStringInput() {
    this.sData = AwrStringInput.ANY_INPUT;
  }

  @Override
	public boolean isStringInput()       { return true; }

  /**
   *  Constructs a stringinput event involving a particular input string
   */
  public AwrStringInput( String zData ) {
    this.sData = zData;
  }

  @Override
	public boolean matches( Answer zEvent ) {
    /* talking about stringinputs */
    if( zEvent.isStringInput() ) {

      /* does this match any input? */
      if( this.sData == AwrStringInput.ANY_INPUT ) {
        return true;
      }

      /* will match a particular stringinput uh somebody will actually use this? don't think so...*/
      if( this.sData.equals( ((AwrStringInput)zEvent).getData() ) ) {
        return true;
      }
    }

    return false;
  }
}
