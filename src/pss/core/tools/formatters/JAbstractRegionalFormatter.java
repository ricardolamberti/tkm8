package pss.core.tools.formatters;

/**
 * An abstract basic implementation for an object which provides formatting data
 * related to a country.<br>
 *
 * @author Leonardo Pronzolino-Yannino
 * @version 1.0
 */

import java.text.DecimalFormatSymbols;

import pss.common.regions.divitions.BizPais;

public abstract class JAbstractRegionalFormatter {

  //////////////////////////////////////////////////////////////////////////////
  //
  //   STATIC VARIABLES
  //
  //////////////////////////////////////////////////////////////////////////////
  // default values
  protected static final char DEFAULT_DECIMAL_SEPARATOR = '.';
  protected static final char DEFAULT_GROUPING_SEPARATOR = ',';

  //////////////////////////////////////////////////////////////////////////////
  //
  //   INSTANCE VARIABLES
  //
  //////////////////////////////////////////////////////////////////////////////
  // the country this formatter works for
  private BizPais userCountry;
  // the decimal symbols for this formatter
  private DecimalFormatSymbols formattingSymbols;

  //////////////////////////////////////////////////////////////////////////////
  //
  //   CONSTRUCTORS
  //
  //////////////////////////////////////////////////////////////////////////////
  JAbstractRegionalFormatter(BizPais zUserCountry) throws Exception {
    this.userCountry = zUserCountry;
    this.formattingSymbols = this.createFormattingSymbols();
  }


  //////////////////////////////////////////////////////////////////////////////
  //
  //   METHODS
  //
  //////////////////////////////////////////////////////////////////////////////
  //
  //   public getters
  //
  /**
   * Answers the decimal separactor char.
   */
  public char getDecimalSeparator() {
    return this.formattingSymbols.getDecimalSeparator();
  }
  /**
   * Answers the grouping separactor char.
   */
  public char getGroupingSeparator() {
    return this.formattingSymbols.getGroupingSeparator();
  }
  //
  //   internal getters
  //
  protected BizPais getUserCountry() {
    return this.userCountry;
  }
  protected DecimalFormatSymbols getFormattingSymbols() {
    return this.formattingSymbols;
  }
  //
  //   initialization methods
  //
  protected DecimalFormatSymbols createFormattingSymbols() throws Exception {
    // create custom decimal symbols
    DecimalFormatSymbols oSymbols = new DecimalFormatSymbols();
    String sDecimalSeparator = this.getUserCountry().getDecimalSeparator();
    String sGroupingSeparator = this.getUserCountry().getGroupingSeparator();
    if (sDecimalSeparator == null || (sDecimalSeparator = sDecimalSeparator.trim()).length() < 1) {
      oSymbols.setDecimalSeparator(DEFAULT_DECIMAL_SEPARATOR);
    } else {
      oSymbols.setDecimalSeparator(sDecimalSeparator.charAt(0));
    }
    if (sGroupingSeparator == null || (sGroupingSeparator = sGroupingSeparator.trim()).length() < 1) {
      oSymbols.setGroupingSeparator(DEFAULT_GROUPING_SEPARATOR);
    } else {
      oSymbols.setGroupingSeparator(sGroupingSeparator.charAt(0));
    }
    return oSymbols;
  }

}
