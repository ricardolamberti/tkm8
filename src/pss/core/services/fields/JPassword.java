package pss.core.services.fields;

/**
 * An object which holds a password.<br>
 * It uses objects of class <code>String</code> to store the password value in
 * its <code>pVal</code> variable.<br>
 *
 * @author Leonardo Pronzolino-Yannino
 * @version 1.0.0
 */

public class JPassword extends JString {

//  private char  pKey[] = JSlkAlph.SlkAlphaGetKeyOne();

  public JPassword() {
    super();
  }

/*  public void SetKey( String zKey ) {
    pKey = zKey.toCharArray();
  }

  public String GetKey() {
    return new String(pKey);
  }*/

  @Override
	public String getObjectType() { return JObject.JPASSWORD; }

  public JPassword( String zVal ) {
    super(zVal);
  }

/*  public JPassword( char zKey[] ) {
    super();
    SetKey( new String(zKey) );
  }*/


}



