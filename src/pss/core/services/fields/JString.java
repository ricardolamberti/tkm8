package pss.core.services.fields;

import java.io.Serializable;

import pss.core.tools.JTools;

public class JString extends JObject<String> {

  public String getValue() throws Exception { preset(); return getRawValue(); }
  public String getRawValue() { return (getObjectValue()==null) ? "": (String)this.getObjectValue(); };  

  public JString() { }

  public JString( String zVal ) {
    setValue( zVal );
  }

  @Override
	public void setValue( String zVal )  {
    if ( zVal == null || zVal.length() == 0 ) { setNull(); return;}
    super.setValue((Serializable)zVal);
  }

  public boolean isEmpty() throws Exception {
    if ( this.isNull() ) return true;
    return getValue().trim().length() == 0;
  }

  public int getIntValue() throws Exception{
    return Integer.valueOf(getValue()).intValue();
  }
  public double getFloatValue() throws Exception{
  	if  (!JTools.isNumber(getValue())) return 0;
    try {
			return Float.valueOf(getValue()).floatValue();
		} catch (Exception e) {
			// alguna entradas burla el isnumber
			return 0;
		}
  }
  @Override
	public String getObjectType() { return JObject.JSTRING; }
  @Override
	public Class getObjectClass() { return String.class; }

  public boolean equals( String zString ) {
    return this.toString().equals( zString );
  }
}


