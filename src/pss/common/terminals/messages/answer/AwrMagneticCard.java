package pss.common.terminals.messages.answer;

/**
 * Description:
 *
 * @author Iván Rubín, Ricardo Lamberti
 *
 */

public class AwrMagneticCard extends Answer {
  public static final String ANY_TRACK_DATA = null;

  private String sTrack1;
  private String sTrack2;
  private String sTrack3;

  public String getTrack1() { return this.sTrack1; }
  public String getTrack2() { return this.sTrack2; }
  public String getTrack3() { return this.sTrack3; }

  @Override
	public boolean isMagneticCard()      { return true; }

  /**
   * Constructs a magnetic card event involving any track data for tracks 1, 2 & 3
   */
  public AwrMagneticCard() {
    this.sTrack1 = this.sTrack2 = this.sTrack3 = AwrMagneticCard.ANY_TRACK_DATA;
  }

  /**
   *  Constructs a magnetic card event specifing particular tracks
   *
   *  Consider that any track whose value is ANY_TRACK_DATA
   *  will match without evaluation against other event.
   *
   */
  public AwrMagneticCard( String zTrack1, String zTrack2, String zTrack3 ) {
    this.sTrack1 = zTrack1;
    this.sTrack2 = zTrack2;
    this.sTrack3 = zTrack3;
  }

  @Override
	public boolean matches( Answer zEvent ) {
    /* talking about magnetic cards */
    if( zEvent.isMagneticCard() ) {

      /* Does this match any track data */
      if( this.sTrack1 == AwrMagneticCard.ANY_TRACK_DATA &&
          this.sTrack2 == AwrMagneticCard.ANY_TRACK_DATA &&
          this.sTrack3 == AwrMagneticCard.ANY_TRACK_DATA ) {

        return true;
      }

      /* Will match a particular card by matching its tracks */
      if( (this.sTrack1 != AwrMagneticCard.ANY_TRACK_DATA && this.sTrack1.equals( ((AwrMagneticCard)zEvent).getTrack1() ) ) &&
          (this.sTrack2 != AwrMagneticCard.ANY_TRACK_DATA && this.sTrack2.equals( ((AwrMagneticCard)zEvent).getTrack2() ) ) &&
          (this.sTrack3 != AwrMagneticCard.ANY_TRACK_DATA && this.sTrack3.equals( ((AwrMagneticCard)zEvent).getTrack3() ) ) ) {

        return true;
      }
    }

    return false;
  }
}
