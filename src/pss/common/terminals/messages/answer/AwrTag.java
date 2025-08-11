package pss.common.terminals.messages.answer;

/**
 * Description:
 *
 * @author Iván Rubín, Ricardo Lamberti
 *
 */

public class AwrTag extends Answer {
  public static final String ANY_TAG = null;

  private String sData;

  @Override
	public boolean isTag()               { return true; }

  public String getData() { return this.sData; }

  /**
   *  Constructs a tag event involving any tag
   */
  public AwrTag() {
    this.sData = AwrTag.ANY_TAG;
  }

  /**
   *  Constructs a tag event involving a particular tag
   */
  public AwrTag( String zData ) {
    this.sData = zData;
  }

  @Override
	public boolean matches( Answer zEvent ) {
    /* talking about tags */
    if( zEvent.isTag() ) {

      /* does this match any tag? */
      if( this.sData == AwrTag.ANY_TAG ) {
        return true;
      }

      /* will match a particular tag. Nonsense.... */
      if( this.sData.equals( ((AwrTag)zEvent).getData() ) ) {
        return true;
      }
    }
    return false;
  }
}

