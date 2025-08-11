package pss.core.services.fields;

import pss.core.tools.formatters.JNumberFormatter;

/**
 * An object which holds an <code>int</code> value.<br>
 * It uses objects of class <code>Integer</code> to store the value in its
 * <code>pVal</code> variable.<br>
 * It provides also a formatted <code>String</code> representation which suits
 * the generic number format suitable for the current user. Sublcasses of this
 * class can override the method <code>#isNumberFormatUsed()()</code> to say
 * if the number value must be formatted or not. If not, the resulting
 * <code>String</code> will be in the Java <code>String</code> representation
 * for numbers.
 *
 * @author Leonardo Pronzolino-Yannino
 * @version 1.0.0
 */


public class JInteger extends JObject<Integer> {

  public int getValue() throws Exception { preset(); return this.getRawValue(); };
  public int getRawValue() { return (getObjectValue()==null) ? 0: ((Number)getObjectValue()).intValue(); };  

  public JInteger() { }

  public JInteger( int zVal ) {
    super.setValue( zVal );
  }

  @Override
	public void setValue( String zVal ) {
    if ( zVal == null || zVal.length() == 0) { super.setValue((Integer)null); return;}
    super.setValue( new Integer( zVal.trim() ) );
  }

  @Override
	public String getObjectType() { return JObject.JINTEGER; }
  @Override
	public Class getObjectClass() { return Integer.class; }


  //////////////////////////////////////////////////////////////////////////////
  //
  //   FORMATTING METHODS
  //
  //////////////////////////////////////////////////////////////////////////////

  /**
   * Answers a <code>String</code> representation of the value this
   * <code>JObject</code> holds, formatted to the user which is going to see it.
   * It does the same as the <code>#toFormattedString()</code> method, except in
   * that it does not invoke the <code>#Pre()</code> method first.
   * <p>
   * This method may be overridden by subclasses to provide appropriate
   * representations, depending on the data type they represent.
   *
   * @return a formatted <code>String</code> representation of the value this
   *         <code>JObject</code> holds
   */
  @Override
	public String toRawFormattedString() throws Exception {
    return this.toRawString();
  }
  
  @Override
	public String toRawString() throws Exception {
    if ( this.isRawNull()) return "";
    return JNumberFormatter.formatNumberToString(this.getRawValue());
  }

  public String toInputString() throws Exception {
  	Object obj= this.asObject();
  	if (obj==null) return "";
  	return String.valueOf(obj);
  }

}

