package pss.common.terminals.messages.answer;

/**
 * Description:
 *
 * @author Iván Rubín, Ricardo Lamberti
 *
 */

public class AwrOperKey extends Answer {

  public static final char ANY_KEY = 0;

/// Teclas de Funciones Especiales.
  public static final char KEY_F1  = 'a';
  public static final char KEY_F2  = 'b';
  public static final char KEY_F3  = 'c';
  public static final char KEY_F4  = 'd';
  public static final char KEY_C1  = 'e';
  public static final char KEY_C2  = 'f';
  public static final char KEY_C3  = 'g';
  public static final char KEY_C4  = 'h';

  private char cKeyCode;

  @Override
	public boolean isKey()               { return true; }

  public char getKeyCode() { return this.cKeyCode; }

  /**
   *  Constructs a key event involving any key
   */
  public AwrOperKey() {
    this.cKeyCode = AwrOperKey.ANY_KEY;
  }

  /**
   *  Constructs a key event involving a particular key
   */
  public AwrOperKey( char c ) {
    this.cKeyCode = c;
  }

  @Override
	public boolean matches( Answer zEvent ) {
    if( zEvent.isKey() ) {

      /* Does this match any key */
      if( this.cKeyCode == AwrOperKey.ANY_KEY ) {
        return true;
      }

      /* will match on key code then */
      if( this.cKeyCode == ((AwrOperKey)zEvent).getKeyCode() ) {
        return true;
      }
    }

    return false;
  }
}
