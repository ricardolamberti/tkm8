/*
 * Created on 25-ago-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.input;

import java.text.Format;

import pss.core.tools.formatters.JRegionalFormatterFactory;

public class JWebTimeInputFormat extends JWebInputFormat {

  JWebTimeInputFormat(String zPattern) {
    super(zPattern, true);
  }


  @Override
	protected void resolveValidationAttributes(String zJavaTimePattern) throws Exception {
    // validate separator
    char cSep = this.validateSeparator(zJavaTimePattern);
    if (cSep==0x00) {
      throw new RuntimeException("Time pattern without separator, or with different separators: " + zJavaTimePattern);
    }
    // validate h,m,s and build the JS pattern
    boolean bHasH = zJavaTimePattern.indexOf("HH") != -1;
    boolean bHasM = zJavaTimePattern.indexOf("mm") != -1;
    boolean bHasS = zJavaTimePattern.indexOf("ss") != -1;
    int iLength = zJavaTimePattern.length();
    int iExpectedLength = 0;
    if (bHasH) {
      iExpectedLength += 3;
    }
    if (bHasM) {
      iExpectedLength += 3;
    }
    if (bHasS) {
      iExpectedLength += 3;
    }
    iExpectedLength = iExpectedLength -1;
    if (iLength!=iExpectedLength) {
      throw new RuntimeException("Invalid time pattern: " + zJavaTimePattern);
    }
    // then, set the values for the validation pattern
    this.sValidationFormatChars = String.valueOf(cSep);
    this.sValidationPattern = this.textValPattern(zJavaTimePattern, cSep);
  }


  @Override
	protected Format resolveFormatter(String zJavaDatePattern) throws Exception {
    return JRegionalFormatterFactory.getRegionalFormatter().getCustomDateFormat(zJavaDatePattern);
  }


  private char validateSeparator(String zPattern) {
    int count = zPattern.length();
    char cLastFoundSep = 0x00;
    for (int i = 0; i < count; i++) {
      char c = zPattern.charAt(i);
      if (c != 'H' && c != 'm' && c != 's') {
        if (cLastFoundSep==0x00) {
          cLastFoundSep = c;
        } else if (cLastFoundSep!=c) {
          return 0x00; // two different separators !!!!
        }
      }
    }
    return cLastFoundSep;
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

}
