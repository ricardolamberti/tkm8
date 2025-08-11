package pss.common.terminals.messages.answer;

/**
 * Description:
 *
 * @author Iván Rubín, Ricardo Lamberti
 *
 */

public class AwrChipCard extends Answer {
  public static final String ANY_CARD = null;

  private String sData;

  public String  getData()    { return this.sData; }

  @Override
	public boolean isChipCard() { return true; }

  /**
   * Constructs a chipcard event involving any chip card
   */
  public AwrChipCard() {
    this.sData = AwrChipCard.ANY_CARD;
  }

  /**
   * Constructs a chipcard event involving a particular card
   */
  public AwrChipCard( String zData ) {
    this.sData = zData;
  }

  @Override
	public boolean matches( Answer zEvent ) {
    /* We're talking about chipcards */
    if( zEvent.isChipCard() ) {

      /* Does this match any card? */
      if( this.sData == AwrChipCard.ANY_CARD ) {
        return true;
      }

      /* this will just match a particular card */
      if( this.sData.equals( ((AwrChipCard)zEvent).getData() ) ) {
        return true;
      }
    }

    return false;
  }


}
