package pss.core.services.fields;

import pss.core.tools.JTools;

/**
 * An object which holds a <code>long</code> value.<br>
 * It uses objects of class <code>Long</code> to store the value in its
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

import pss.core.tools.formatters.JNumberFormatter;

public class JLong extends JObject<Long> {

  public final static int MAX_DIGITS = 16;

  public JLong() {
  }

  public JLong( long zVal ) {
    super.setValue( new Long( zVal ) );
  }

  public long getValue() throws Exception { preset(); return getRawValue(); };
  public long getRawValue() throws Exception { return (this.hasValue()) ? ((Number)getObjectValue()).longValue() : 0L; };

  @Override
	public void setValue( String zVal ) {
    if ( zVal == null || zVal.length() == 0 ) { super.setValue((Long)null); return;}
    super.setValue( new Long( JTools.getNumberFloatToLong(zVal.trim() ) ) );
  }
  public void setValue( int  zVal ) {
      setValue( new Long(zVal) );
  }

  @Override
	public String getObjectType() { return JObject.JLONG; }
  @Override
	public Class getObjectClass() { return Long.class; }


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
  	if ( this.isRawNull()) return "";
  	return String.valueOf(this.asObject());
  }


}



