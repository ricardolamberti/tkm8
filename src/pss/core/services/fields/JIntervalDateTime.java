package pss.core.services.fields;

import java.util.Date;

import pss.core.tools.JDateTools;
import pss.core.tools.PssLogger;

public class JIntervalDateTime extends JObject<String> {

	public static String DATETIME_FORMAT="dd/MM/yyyy HH:mm:ss";
	private boolean nomilliseconds = false;
	public String dbFormat=null;

	public String getValue() throws Exception { preset(); return getRawValue(); }
  public String getRawValue() { return (getObjectValue()==null) ? "": (String)this.getObjectValue(); };  

  public JIntervalDateTime() { }
  public JIntervalDateTime(String zdbFormat) {
  	dbFormat = zdbFormat;
  }

  public JIntervalDateTime( Date startDate, Date endDate ) {
    try {
			setValue( startDate, endDate );
		} catch (Exception e) {
		}
  }
	public String getDbFormat() throws Exception {
		if (this.dbFormat!=null) return dbFormat;
		if (this.nomilliseconds)
			return "dd/MM/yyyy HH:mm:ss";
		else
			return "dd/MM/yyyy HH:mm:ss.SSS";
	}
	public void setValue( String formated ) throws Exception {
		if (formated==null || formated.equals("")) {
			super.setValue(null);
			return;
		}
		int pos = getValue().indexOf(" - ");
		if (pos==1) new Exception("Unformatted range: "+getValue());
		super.setValue(formated);
	}

	public void setValue( Date startDate, Date endDate ) throws Exception {
    super.setValue(JDateTools.DateToString(startDate,getDbFormat())+" - "+JDateTools.DateToString(endDate,getDbFormat()));
  }

  public boolean isEmpty() throws Exception {
    if ( this.isNull() ) return true;
    return getValue().trim().length() == 0;
  }

  public Date getStartDateValue() throws Exception{
  	return  getInternalStartDateValue(getValue());
  }
  public Date getEndDateValue() throws Exception{
  	return  getInternalEndDateValue(getValue());
  }
  public String getStartDateValueAsString() throws Exception{
  		try {
  			if (getObjectValue()==null) return "";
  	 		return JDateTools.DateToString((getStartDateValue()), this.getDbFormat());
  		} catch (Exception e) {
  			PssLogger.logDebug(e);
  			return "Error";
  		}
  }
  public String getEndDateValueAsString() throws Exception{
		try {
			if (getObjectValue()==null) return "";
	 		return JDateTools.DateToString((getEndDateValue()), this.getDbFormat());
		} catch (Exception e) {
			PssLogger.logDebug(e);
			return "Error";
		}
  }

  private Date getInternalStartDateValue(String value) throws Exception{
  	int pos = value.indexOf(" - ");
  	if (pos==-1) return null;
  	String d = value.substring(0, pos);
  	return JDateTools.StringToDate(d,getDbFormat());
  }
  private Date getInternalEndDateValue(String value) throws Exception{
  	int pos = value.indexOf(" - ");
  	if (pos==-1) return null;
  	String d = value.substring(pos+3);
  	return JDateTools.StringToDate(d,getDbFormat());
  }
  @Override
	public String getObjectType() { return JObject.JINTERVALDATETIME; }
  @Override
	public Class getObjectClass() { return String.class; }

  public boolean equals( String zString ) {
    return this.toString().equals( zString );
  }

}



	
