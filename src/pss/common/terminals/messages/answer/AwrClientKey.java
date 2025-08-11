package pss.common.terminals.messages.answer;

import pss.core.services.fields.JString;

/**
 * Description:
 *
 * @author Iván Rubín, Ricardo Lamberti
 *
 */

public class AwrClientKey extends Answer {

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

  private JString pKeyCode = new JString();

  @Override
	public boolean isKey()               { return true; }

  public char getKeyCode() throws Exception { 
  	return this.pKeyCode.getValue().charAt(0); 
  }

  /**
   *  Constructs a key event involving any key
   */
  public AwrClientKey() {
    this.pKeyCode.setValue(String.valueOf(AwrClientKey.ANY_KEY));
  }

  /**
   *  Constructs a key event involving a particular key
   */
  public AwrClientKey( char c ) {
    this.pKeyCode.setValue(String.valueOf(c));
  }
  
  @Override
	public void loadFieldMap() throws Exception {
  	this.addValue("key_code", pKeyCode);
  }

  @Override
	public boolean matches( Answer zEvent ) throws Exception {
    /* talking about keys */
    if( zEvent.isKey() ) {

      /* Does this match any key */
      if( this.getKeyCode() == AwrClientKey.ANY_KEY ) {
        return true;
      }

      /* will match on key code then */
      if( this.getKeyCode() == ((AwrClientKey)zEvent).getKeyCode() ) {
        return true;
      }
    }

    return false;
  }
}
