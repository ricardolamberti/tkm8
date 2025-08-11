package pss.core.tools.formatters;

/**
 * An object which provides formatting data which depends only on the country an
 * user belongs to.<br>
 * Only-country-related formatting data are date/time formats and the decimal
 * and grouping separators for the country.<br>
 *
 * @author Leonardo Pronzolino-Yannino
 * @version 1.0
 */

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import pss.common.regions.divitions.BizPais;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

public class JRegionalDataFormatter extends JAbstractRegionalFormatter {

  //////////////////////////////////////////////////////////////////////////////
  //
  //   INSTANCE VARIABLES
  //
  //////////////////////////////////////////////////////////////////////////////
  // the generic decimal format
  private DecimalFormat oNumberFormat;
//  private DecimalFormat oNumberToStringFormat;
  private JMap<String, DecimalFormat> oCustomNumberFormats;
  // the date/time formats
  private DateFormat oShortTimeFormat;
  private DateFormat oLongTimeFormat;
  private DateFormat oShortDateFormat;
  private DateFormat oLongDateFormat;
  private DateFormat oDateTimeFormat;
  private DateFormat oDayMonthFormat;
  private DateFormat oMonthYearFormat;
  private JMap<String, DateFormat> oCustomDateTimeFormats;


  //////////////////////////////////////////////////////////////////////////////
  //
  //   CONSTRUCTORS
  //
  //////////////////////////////////////////////////////////////////////////////
  public JRegionalDataFormatter(BizPais zFormattingRegion) throws Exception {
    super(zFormattingRegion);

    // create number format
    this.oNumberFormat = new DecimalFormat("###,##0.##############################;-###,##0.##############################", this.getFormattingSymbols());
    this.oNumberFormat.setDecimalSeparatorAlwaysShown(false);

    // create number To String format (used for editing number)
//    this.oNumberToStringFormat = new DecimalFormat("################.##############################;-################.##############################", this.getFormattingSymbols());
//    this.oNumberToStringFormat.setDecimalSeparatorAlwaysShown(false);
//    DecimalFormatSymbols newSymbols = oNumberToStringFormat.getDecimalFormatSymbols();
//    newSymbols.setDecimalSeparator('.');
//    oNumberToStringFormat.setDecimalFormatSymbols(newSymbols);

    // create date and time formats
    this.oShortTimeFormat = this.createDateFormat(this.getShortTimePattern());
    this.oLongTimeFormat = this.createDateFormat(this.getLongTimePattern());
    this.oShortDateFormat = this.createDateFormat(this.getShortDatePattern());
    this.oLongDateFormat = this.createDateFormat(this.getLongDatePattern());
    this.oMonthYearFormat = this.createDateFormat(this.getMonthYearPattern());
    this.oDayMonthFormat = this.createDateFormat(this.getDayMonthPattern());
    this.oDateTimeFormat = this.createDateFormat(this.getDateTimePattern());
  }


  //////////////////////////////////////////////////////////////////////////////
  //
  //   METHODS
  //
  //////////////////////////////////////////////////////////////////////////////

	//
  //   internal methods
  //
  private SimpleDateFormat createDateFormat(String zPattern) {
    SimpleDateFormat oFormat = new SimpleDateFormat(zPattern);
    oFormat.setLenient(false);
    return oFormat;
  }
  private JMap<String, DecimalFormat> getCustomNumberFormats() {
    if (this.oCustomNumberFormats == null) {
      this.oCustomNumberFormats = JCollectionFactory.createMap();
    }
    return this.oCustomNumberFormats;
  }
  private JMap<String, DateFormat> getCustomDateTimeFormats() {
    if (this.oCustomDateTimeFormats == null) {
      this.oCustomDateTimeFormats = JCollectionFactory.createMap();
    }
    return this.oCustomDateTimeFormats;
  }

  //
  //   getters
  //
  /**
   * Answers the pattern to format times in short format.
   */
  public String getShortTimePattern() throws Exception {
    return this.getUserCountry().getShortTimeFormat();
  }
  /**
   * Answers the pattern to format dates in short format.
   */
  public String getShortDatePattern() throws Exception {
    return this.getUserCountry().getShortDateFormat();
  }
  /**
   * Answers the pattern to format times in long format.
   */
  public String getLongTimePattern() throws Exception {
    return this.getUserCountry().getLongTimeFormat();
  }
  /**
   * Answers the pattern to format dates in long format.
   */
  public String getLongDatePattern() throws Exception {
    return this.getUserCountry().getLongDateFormat();
  }
  /**
   * Answers the pattern to format dates in month year format.
   * Some of the bugui magics
   */
  public String getMonthYearPattern() throws Exception {
    return this.getPatternRemovingField('d');
  }

  /**
   * Answers the pattern to format dates in day/month format.
   * Some of the bugui magics
   */
  public String getDayMonthPattern() throws Exception {
    return this.getPatternRemovingField('y');
  }

  private String getPatternRemovingField(char zFieldChar) throws Exception {
    // find separator char
    char cSepChar = '-';
    String sPattern = this.getShortDatePattern();
    int iLength = sPattern.length();
    char c;
    for (int i = 0; i < iLength; i++) {
      c = sPattern.charAt(i);
      if (c!='d' && c!='M' && c!='y') {
        cSepChar = c;
        break;
      }
    }
    // remove day from pattern
    String sSepChar = String.valueOf(cSepChar);
    String sDoubleSepChar = sSepChar + sSepChar;
    StringBuffer pattern = new StringBuffer(this.getShortDatePattern());
    String sFieldAsString = String.valueOf(zFieldChar);
    int index = pattern.indexOf(sFieldAsString); 
    while (index != -1) {
      pattern = pattern.deleteCharAt(index);
      index = pattern.indexOf(sFieldAsString);
    }
    index = pattern.indexOf(sDoubleSepChar);
    if (index != -1) {
      String dummy = pattern.toString();
      pattern = new StringBuffer(dummy.replaceAll(sDoubleSepChar, sSepChar));
    } else if (pattern.indexOf(sSepChar) == 0) {
      pattern.delete(0,1);
    } else if (pattern.lastIndexOf(sSepChar) == pattern.length()-1) {
      pattern.delete(pattern.length()-1,pattern.length());
    }
    return pattern.toString();
  }
  
  
  /**
   * Answers the pattern to format timestamps.
   */
  public String getDateTimePattern() throws Exception {
    return this.getUserCountry().getShortDateFormat() + " " + this.getUserCountry().getLongTimeFormat();
  }

  /**
   * Answers the date/time format.
   */
  public DateFormat getDateTimeFormat() {
    return this.oDateTimeFormat;
  }
  /**
   * Answers the long time format.
   */
  public DateFormat getLongTimeFormat() {
    return this.oLongTimeFormat;
  }
  /**
   * Answers the short time format.
   */
  public DateFormat getShortTimeFormat() {
    return this.oShortTimeFormat;
  }
  /**
   * Answers the long date format.
   */
  public DateFormat getLongDateFormat() {
    return this.oLongDateFormat;
  }
  /**
   * Answers the short date format.
   */
  public DateFormat getShortDateFormat() {
    return this.oShortDateFormat;
  }
  
   
  /**
   * Answers a custom date format.
   */
  public DateFormat getCustomDateFormat(String zPattern) {
    DateFormat formatter = this.getCustomDateTimeFormats().getElement(zPattern);
    if (formatter == null) {
      formatter = this.createDateFormat(zPattern);
      this.getCustomDateTimeFormats().addElement(zPattern, formatter);
    }
    return formatter;
  }

  /**
   * Answers a custom time format.
   */
  public DateFormat getCustomTimeFormat(String zPattern) {
    DateFormat formatter = this.getCustomDateTimeFormats().getElement(zPattern);
    if (formatter == null) {
      formatter = this.createDateFormat(zPattern);
      this.getCustomDateTimeFormats().addElement(zPattern, formatter);
    }
    return formatter;
  }
  
  
  /**
   * Answers the generic number formatter.
   */
  public DecimalFormat getNumberFormat() {
    return this.oNumberFormat;
  }
  /**
   * Answers a custom number format, configured to use the decimal and thousands
   * separators defined in this formatter.
   */
  public NumberFormat getCustomNumberFormat(String zPattern) {
    DecimalFormat formatter = this.getCustomNumberFormats().getElement(zPattern);
    if (formatter == null) {
      formatter = new DecimalFormat(zPattern);
      formatter.setDecimalFormatSymbols(this.getFormattingSymbols());
      this.getCustomNumberFormats().addElement(zPattern, formatter);
    }
    return formatter;
  }
  public DecimalFormat getCustomDecimalFormat(String zPattern) {
  	return (DecimalFormat)getCustomNumberFormat(zPattern);
  }
  /**
   * Answers a custom number format with fixed decimals quantity, configured
   * to use the decimal and thousands separators defined in this formatter.
   */
  public NumberFormat getCustomNumberFormat(int zDecimalCount) {
    String sPattern;
    if (zDecimalCount > 0) {
      sPattern = "#,##0.";
      sPattern = JTools.justifyStrings(sPattern, null, sPattern.length() + zDecimalCount, '0');
    } else {
      sPattern = "#,##0";
    }
    return this.getCustomNumberFormat(sPattern);
  }
  /**
   * Answers the decimal separactor char.
   */
  @Override
	public char getDecimalSeparator() {
    return this.getFormattingSymbols().getDecimalSeparator();
  }
  /**
   * Answers the grouping separactor char.
   */
  @Override
	public char getGroupingSeparator() {
    return this.getFormattingSymbols().getGroupingSeparator();
  }

  //
  //   format methods
  //
  /**
   * Formats a double to number format
   */
  public String formatNumber(double zValue) {
    return this.oNumberFormat.format(zValue);
  }
  /**
   * Formats a double to number format, with fixed decimals.
   */
  public String formatNumber(double zValue, int zDecimals) {
    return this.getCustomNumberFormat(zDecimals).format(zValue);
  }
  /**
   * Formats a long to number format
   */
  public String formatNumber(long zValue) {
    return this.oNumberFormat.format(zValue);
  }
  /**
   * Formats a long to number format, with fixed decimals.
   */
  public String formatNumber(long zValue, int zDecimals) {
    return this.getCustomNumberFormat(zDecimals).format(zValue);
  }
  /**
   * Fixed Decimal point = "."
   * No Thousand Seperator
   * Decimals digits only if necesary ( MinimumFractionDigits = 0 ).
   */
//  public String formatNumberToString(double zValue) throws Exception {
//    return this.oNumberToStringFormat.format(zValue);
//  }
//  public String formatNumberToString(long zValue) throws Exception {
//    return this.oNumberToStringFormat.format(zValue);
//  }

  /**
   * Formats a Date to short time format
   */
  public String formatShortTime(Date zTime) {
    return this.oShortTimeFormat.format(zTime);
  }
  /**
   * Formats a Date to long time format
   */
  public String formatLongTime(Date zTime) {
    return this.oLongTimeFormat.format(zTime);
  }
  /**
   * Formats a Date to short time format
   */
  public String formatShortDate(Date zDate) {
    return this.oShortDateFormat.format(zDate);
  }
  /**
   * Formats a Date to short time format
   */
  public String formatDateTime(Date zDate) {
    return this.oDateTimeFormat.format(zDate);
  }
  /**
   * Formats a Date to long time format
   */
  public String formatLongDate(Date zDate) {
    return this.oLongDateFormat.format(zDate);
  }
  /**
   * Formats a Date to month and year format
   */
  public String formatMonthYearDate(Date zDate) {
    return this.oMonthYearFormat.format(zDate);
  }
  /**
   * Formats a Date to day and month format
   */
  public String formatDayMonthDate(Date zDate) {
    return this.oDayMonthFormat.format(zDate);
  }
  


}
