/*
 * Created on 25-ago-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.input;

import java.text.Format;

import pss.core.tools.JDateTools;
import pss.core.tools.formatters.JRegionalFormatterFactory;

public class JWebDateInputFormat extends JWebInputFormat {


  private String sCalendarJSFormat;


  JWebDateInputFormat(String zPattern) {
    super(zPattern, true);
  }


  public static String getClientDateFormat(String zJavaDatePattern) throws Exception {
    // validate separator
    char cSep = validateSeparator(zJavaDatePattern);
    if (cSep==0x00) {
      throw new RuntimeException("Date pattern without separator, or with different separators: " + zJavaDatePattern);
    }
    // validate y,m,d and build the JS pattern
    String sJSDatePattern = zJavaDatePattern;
    if (sJSDatePattern.indexOf("yyyy")!=-1) {
      sJSDatePattern = sJSDatePattern.replaceAll("yyyy", "y");
    } else if (sJSDatePattern.indexOf("yy")!=-1) {
      // ok, but do nothing
    } else {
      throw new RuntimeException("No year parameter in date pattern: " + zJavaDatePattern + ". It must be one of: 'yy', 'yyyy'");
    }
    if (sJSDatePattern.indexOf("MMMM")!=-1) {
      sJSDatePattern = sJSDatePattern.replaceAll("MMMM", "MM");
    } else if (sJSDatePattern.indexOf("MMM")!=-1) {
      sJSDatePattern = sJSDatePattern.replaceAll("MMM", "M");
    } else if (sJSDatePattern.indexOf("MM")!=-1) {
      sJSDatePattern = sJSDatePattern.replaceAll("MM", "mm");
    } else if (sJSDatePattern.indexOf("M")!=-1) {
      sJSDatePattern = sJSDatePattern.replaceAll("M", "m");
    } else {
      throw new RuntimeException("No month parameter in date pattern: " + zJavaDatePattern + ". It must be one of: 'M', 'MM', 'MMM', 'MMMM'");
    }
    if (sJSDatePattern.indexOf("dd")!=-1 || sJSDatePattern.indexOf("d")!=-1) {
      // ok, but do nothing
    } else {
      throw new RuntimeException("No day parameter in date pattern: " + zJavaDatePattern + ". It must be one of: 'd', 'dd'");
    }
    // set the calendar JS format
    return sJSDatePattern;
  }


  @Override
	protected void resolveValidationAttributes(String zJavaDatePattern) throws Exception {
    // validate separator
    char cSep = validateSeparator(zJavaDatePattern);
    if (cSep==0x00) {
      throw new RuntimeException("Date pattern without separator, or with different separators: " + zJavaDatePattern);
    }
    // validate y,m,d and build the JS pattern
    this.sCalendarJSFormat = getClientDateFormat(zJavaDatePattern);
    // then, set the values for the validation pattern
    this.sValidationFormatChars = String.valueOf(cSep);
    this.sValidationPattern = this.textValPattern(zJavaDatePattern, cSep);
  }


  @Override
	protected Format resolveFormatter(String zJavaDatePattern) throws Exception {
    return JRegionalFormatterFactory.getRegionalFormatter().getCustomDateFormat(zJavaDatePattern);
  }


  private static char validateSeparator(String zPattern) {
    return JDateTools.validateSeparator(zPattern);
  }

  
  
  private String textValPattern(String zJavaDatePattern, char cSep) {
    char[] patternChars = new char[3];
    int count = zJavaDatePattern.length();
    char c;
    char cLast = 0x00;
    int iAddedChars = 0;
    for (int i = 0; i < count; i++) {
      c = Character.toUpperCase(zJavaDatePattern.charAt(i));
      if (cLast==0x00) {
        patternChars[iAddedChars++] = c;
        cLast = c;
      } else if (c!=cSep && c!=cLast) {
        patternChars[iAddedChars++] = c;
        cLast = c;
      }
    }
    return new String(patternChars, 0, iAddedChars);
  }


  public String getCalendarJSFormat() {
    return this.sCalendarJSFormat;
  }

}
