package pss.core.services.fields;

/**
 * An object which holds an time value.<br>
 * It stores the time value as a <code>String</code> with format HH:mm:ss.<br>
 * It provides also a formatted <code>String</code> representation which suits
 * the time format suitable for the current user. Sublcasses of this class
 * can override the method <code>#isShortFormat()</code> to format the value
 * either to the short or to the long time format configured for the user.
 *
 * @author Leonardo Pronzolino-Yannino
 * @version 1.0.0
 */

import java.util.Calendar;
import java.util.Date;

import pss.core.tools.JTools;
import pss.core.tools.formatters.JRegionalFormatterFactory;

public class JHour extends JString {

  public JHour() {
    super();
  }

  @Override
	public String getValue() throws Exception {
    preset();
    String val = getRawValue();
    return (val == null) ? "00:00:00" : val;
  }

  @Override
	public String getRawValue() {
    return (String)getObjectValue();
  }

  @Override
	public String getObjectType() { return JObject.JHOUR; }

  public JHour( String zVal ) {
    super(zVal);
  }

  @Deprecated
  // Use getLongValue()
  public long GetValorLong() throws Exception {
    return getLongValue();
  }

  public long getLongValue() throws Exception {
    char sValor[] = this.getValue().toCharArray();
    StringBuffer result = new StringBuffer(6);
    for( int i = 0; i < sValor.length; i++ ) {
      if( Character.isDigit( sValor[i] ) ) {
        result.append( sValor[i] );
      }
    }
    return Long.parseLong(result.toString());
  }

  /*************************************************************************
   * Retorna la hora sin los dos puntos (:)
   *************************************************************************/
  public String GetValorString() throws Exception {
    String sValor = this.getValue();
    if (sValor == null) return "";
    if (sValor.length()<6) return "";
    return sValor.substring(0,2)+ sValor.substring(3,5) + sValor.substring(6);
  }

  public String GetHour() throws Exception {
    String sValor = this.getValue();
    if (sValor == null) return "";
    if (sValor.length()<6) return "";
    return sValor.substring(0,2);
  }

  public String GetMinute() throws Exception {
    String sValor = this.getValue();
    if (sValor == null) return "";
    if (sValor.length()<6) return "";
    return sValor.substring(3,5);
  }

  public String GetSecond() throws Exception {
    String sValor = this.getValue();
    if (sValor == null) return "";
    if (sValor.length()<6) return "";
    return sValor.substring(6);
  }


  /*************************************************************************
   * Retorna la hora con los dos puntos (:)
   *************************************************************************/
  public static String StringToJHour(String zVal)  {
    if (zVal == null) return "";
    if (zVal.length()>6) return "";
    return zVal.substring(0,2)+ ":" +zVal.substring(2,4)+ ":" + zVal.substring(4);
  }

  public static JHour toJHour(String zVal)  {
    if (zVal == null) return new JHour("");
    if (zVal.length()>6) return new JHour("");
    return new JHour(zVal.substring(0,2)+ ":" +zVal.substring(2,4)+ ":" + zVal.substring(4));
  }
  
  public static JHour toJHour(Date zVal)  {
    if (zVal == null) return new JHour("");
    Calendar fecha = Calendar.getInstance();
    fecha.setTime(zVal);
    return new JHour(JTools.LPad(""+fecha.get(Calendar.HOUR_OF_DAY),2,"0")+ ":" +JTools.LPad(""+fecha.get(Calendar.MINUTE),2,"0")+ ":" + JTools.LPad(""+fecha.get(Calendar.SECOND),2,"0"));
  }

  public boolean after(JHour zWhen) throws Exception{
    if(this.GetValorLong() < zWhen.GetValorLong()) return false;
    return true;
  }

  public boolean before(JHour zWhen) throws Exception{
    if(this.GetValorLong() > zWhen.GetValorLong()) return false;
    return true;
  }


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
    if( this.isRawNull() ) {
      return "";
    }
    return JRegionalFormatterFactory.getRegionalFormatter().formatLongTime(this.getValueAsDate());
  }

  private Date getValueAsDate() throws Exception {
    String sHour = getRawValue();
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sHour.substring(0,2)));
    cal.set(Calendar.MINUTE, Integer.parseInt(sHour.substring(3,5)));
    cal.set(Calendar.SECOND, Integer.parseInt(sHour.substring(6)));
    return cal.getTime();
  }

  /*************************************************************************/
  public long asSeconds() throws Exception {
    String sHour = getRawValue();
    if (sHour.length()==6){
      return Integer.parseInt(sHour.substring(0,2)) * 3600 +
      Integer.parseInt(sHour.substring(2,4)) * 60   +
      Integer.parseInt(sHour.substring(5));
    }
    return Integer.parseInt(sHour.substring(0,2)) * 3600 +
           Integer.parseInt(sHour.substring(3,5)) * 60   +
           Integer.parseInt(sHour.substring(6));
  }
	@Override
	public void setValueFormUI(String val) throws Exception {
		super.setValueFormUI(val);
	}
	
}
