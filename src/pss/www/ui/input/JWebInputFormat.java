/*
 * Created on 25-ago-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.input;

import java.text.Format;

import pss.core.tools.JTools;


public abstract class JWebInputFormat {

  // the values provided in the server
  private String sPattern;
  private Format oFormatter;

  // the values to be sent to the client JS functions
  String sValidationPattern;
  String sValidationFormatChars;
  int iMaxLength;
  

  //
  //  CONSTRUCTORS
  //
  JWebInputFormat(String zPattern, boolean zUsePatternLength) {
    this.sPattern = zPattern;
    if (zUsePatternLength) {
      this.iMaxLength = zPattern.length();
    } else {
      this.iMaxLength = -1;
    }
  }

  //
  //  static accessors to instantiate formats
  //
  public static JWebDateInputFormat date(String zPattern) throws Exception {
    JWebDateInputFormat oFormat = new JWebDateInputFormat(zPattern);
    oFormat.resolveValues();
    return oFormat;
  }

  public static JWebDatetimeInputFormat datetime(String zPattern) throws Exception {
    JWebDatetimeInputFormat oFormat = new JWebDatetimeInputFormat(zPattern);
    oFormat.resolveValues();
    return oFormat;
  }

  public static JWebTimeInputFormat time(String zPattern) throws Exception {
    JWebTimeInputFormat oFormat = new JWebTimeInputFormat(zPattern);
    oFormat.resolveValues();
    return oFormat;
  }
  public static JWebNumberInputFormat number(int zTotalDigits, int zDecimals, char zGroupingSeparator, char zDecimalSeparator) throws Exception {
    String sPattern;
    if (zDecimals > 0) {
      sPattern = "#,##0.";
      sPattern = JTools.justifyStrings(sPattern, null, sPattern.length() + zDecimals, '0');
    } else {
      sPattern = "#,##0";
    }
    JWebNumberInputFormat oFormat = new JWebNumberInputFormat(sPattern, zTotalDigits, zDecimals, zGroupingSeparator, zDecimalSeparator);
    oFormat.resolveValues();
    return oFormat;
  }
  public static JWebStringInputFormat string(int zLength, String zInputAttributes) throws Exception {
    JWebStringInputFormat oFormat = new JWebStringInputFormat(zLength, zInputAttributes);
    oFormat.resolveValues();
    return oFormat;
  }


  //
  // PUBLIC API
  //

  public String getPattern() {
    return this.sPattern;
  }

  public int getMaxLength() {
    return this.iMaxLength;
  }

  public Format getFormatter() {
    return this.oFormatter;
  }

  public String getValidationFormatChars() {
    return this.sValidationFormatChars;
  }

  public String getValidationPattern() {
    return this.sValidationPattern;
  }


  //
  //  internal implementation
  //
  void resolveValues() throws Exception {
    this.resolveValidationAttributes(this.sPattern);
    this.oFormatter = this.resolveFormatter(this.sPattern);
  }



  //
  //  METHODS TO IMPLEMENT IN SUBCLASSES
  //
  protected abstract void resolveValidationAttributes(String zPattern) throws Exception;
  protected abstract Format resolveFormatter(String zPattern) throws Exception;




}
