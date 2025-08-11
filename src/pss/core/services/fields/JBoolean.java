package pss.core.services.fields;

import pss.core.data.BizPssConfig;

/**
 * An object which holds a <code>boolean</code> value.<br>
 * It uses objects of class <code>Boolean</code> to store the value in its
 * <code>pVal</code> variable.<br>
 * It provides also a formatted <code>String</code> representation of the yes/no
 * value. Sublcasses of this class can override both the methods
 * <code>#getYesFormattedValue()</code> and <code>#getNoFormattedValue()</code>,
 * used to answer the formatted representation of the value.
 *
 * @author Leonardo Pronzolino-Yannino
 * @version 1.0.0
 */

import pss.core.tools.JExcepcion;

public class JBoolean extends JObject<Boolean> {

  public boolean getValue() throws Exception { preset(); return getBooleanValue(); };
  public boolean getRawValue() { return getBooleanValue(); };

  private boolean getBooleanValue() {
    if ( this.getObjectValue() == null) 
    	return false;
    return ((Boolean)this.getObjectValue()).booleanValue();
  }

  public JBoolean() {
  }

  public boolean isTrue() {
    Boolean value = (Boolean) this.getObjectValue();
    return value != null && value.booleanValue();
  }

  public boolean isFalse() {
    Boolean value = (Boolean) this.getObjectValue();
    return value == null || !value.booleanValue();
  }

  public JBoolean( boolean zVal ) {
    super.setValue( new Boolean( zVal ) );
  }

  @Override
	public void setValueFormUI( String zVal ) {
    if ( zVal!=null && zVal.equalsIgnoreCase("S") )
      super.setValue( Boolean.TRUE );
    else if ( zVal!=null && !zVal.equalsIgnoreCase("") )
      super.setValue( Boolean.FALSE );
    else 
    	super.setNull();
   }

  @Override
	public void setValue( String zVal ) {
    if ( zVal == null || zVal.length() == 0 ) { 
    	super.setValue((Boolean)null); 
    	return; 
    }
    
    if ( zVal.equalsIgnoreCase("S") || zVal.equalsIgnoreCase("true") )
      super.setValue( Boolean.TRUE );
    else if ( !zVal.equalsIgnoreCase("") )
      super.setValue( Boolean.FALSE );
    else 
    	super.setNull();
  }

//  public String toString() {
  /**
   * Answers the value this <code>JObject</code> holds as a <code>String</code>.
   * It does the same as the <code>#toString()</code> method, except in that
   * it does not invoke the <code>#Pre()</code> method first.
   * <p>
   * This method may be overridden by subclasses to provide appropriate
   * representations, depending on the data type they represent.
   *
   * @return the value this <code>JObject</code> holds as a <code>String</code>
   */
  @Override
	public String toRawString() {
    try {
     if(	BizPssConfig.getPssConfig().isAcceptNullInBoolean()) // lo pongo configurable, ya que no quieren los null, pero advierto, muchos componentes no funcionan si no aceptan los null
				return isNull()?"":getBooleanValue() ? "S" : "N";
    	return getBooleanValue() ? "S" : "N"; // debatido entre todos y quedo que lo mejor es que el boolean no tenga nulls
		} catch (Exception e) {
			return "";
		}
  }

  @Override
	public String getObjectType() { return JObject.JBOOLEAN; }
  @Override
	public Class getObjectClass() { return Boolean.class; }


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
    return getRawValue() ? this.getYesFormattedValue() : this.getNoFormattedValue();
  }

  protected String getYesFormattedValue() {
    return "S";
  }
  protected String getNoFormattedValue() {
    return "N";
  }

  /**************************************************************************
   * Convierte un boolean(true/false) en un String a Y/N (Yes/No).
   **************************************************************************/
  public static String convertBoolToYN(boolean zBool) throws Exception {
    if (zBool) return "Y";
    return "N";
  }

  /**************************************************************************
   * Convierte el valor logico Y/N (Yes/No) a boolean (true/false).
   * Excepciones:
   *   - Si el valor de zYESNO es diferente a Y o N
   **************************************************************************/
  public static boolean convertYNtoBoolean(String zYESNO) throws Exception {
    if (zYESNO.equalsIgnoreCase("Y")) return true;
    if (zYESNO.equalsIgnoreCase("N")) return false;
    JExcepcion.SendError("Valor logico invalido");
    return false;
  }

  /**************************************************************************
   * Convierte el valor logico Y/N (Yes/No) a S o N.
   * Excepciones:
   *   - Si el valor de zYESNO es diferente a Y o N
   **************************************************************************/
  public static  String convertYNtoSN(String zYESNO) throws Exception {
    if (zYESNO.equalsIgnoreCase("Y")) return "S";
    if (zYESNO.equalsIgnoreCase("N")) return "N";
    JExcepcion.SendError("Valor logico invalido");
    return "";
  }
}
