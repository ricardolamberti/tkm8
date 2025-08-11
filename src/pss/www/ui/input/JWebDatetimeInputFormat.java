package pss.www.ui.input;

import java.text.Format;

import pss.core.tools.JDateTools;
import pss.core.tools.formatters.JRegionalFormatterFactory;


public class JWebDatetimeInputFormat extends JWebInputFormat {
  private String sCalendarJSFormat;

	
	JWebDatetimeInputFormat(String zPattern) {
		super(zPattern, true);
	}

	@Override
	protected Format resolveFormatter(String zJavaDatePattern) throws Exception {
    return JRegionalFormatterFactory.getRegionalFormatter().getCustomDateFormat(zJavaDatePattern);}

	@Override
	protected void resolveValidationAttributes(String javapattern) throws Exception {
	  int cSep = javapattern.indexOf(' ');
	  int cSepT = javapattern.indexOf(':');
	  
    if (cSep==-1) { // only date or time
    	if (cSepT==-1) {
      	JWebDateInputFormat oDateinput = new JWebDateInputFormat(getPattern());
      	oDateinput.resolveFormatter(javapattern);
        sCalendarJSFormat = oDateinput.getCalendarJSFormat();
        this.sValidationFormatChars = String.valueOf(JDateTools.validateSeparator(javapattern));
        this.sValidationPattern = sCalendarJSFormat;
    	} else {
       	JWebTimeInputFormat oDateinput = new JWebTimeInputFormat(getPattern());
      	oDateinput.resolveFormatter(javapattern);
        sCalendarJSFormat = "HM"+(javapattern.indexOf("s")==-1?"":"S");
        this.sValidationFormatChars = ":";
        this.sValidationPattern = sCalendarJSFormat;
   		
    	}
    } else { // datetime
    	String strDate = javapattern.substring(0,cSep);
    	String strTime = javapattern.substring(cSep+1);
    	JWebDateInputFormat oDateinput = new JWebDateInputFormat(getPattern());
    	JWebTimeInputFormat oTimeinput = new JWebTimeInputFormat(getPattern());
    	oDateinput.resolveValidationAttributes(strDate);
    	oTimeinput.resolveValidationAttributes(strTime);
    	sCalendarJSFormat = oDateinput.getValidationPattern()+" "+oTimeinput.getValidationPattern();
      this.sValidationFormatChars = String.valueOf(JDateTools.validateSeparator(strDate));
      this.sValidationPattern = sCalendarJSFormat;
    }
	}

  public String getCalendarJSFormat() {
    return this.sCalendarJSFormat;
  }
}
