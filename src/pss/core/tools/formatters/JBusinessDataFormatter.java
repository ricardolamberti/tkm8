package pss.core.tools.formatters;

/**
 * An object which provides formatting data which typically depends on the
 * country an user belongs to and another country in which a business takes
 * place.<br>
 * It may be also used to format business data which does not depend on the
 * user, but only on the business country. In order to do this, the formatting
 * and the business country must be the same.
 * Business-related formatting data are currency formats.<br>
 *
 * @author Leonardo Pronzolino-Yannino
 * @version 1.0
 */

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import pss.common.regions.currency.BizMoneda;
import pss.common.regions.divitions.BizPais;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;

public class JBusinessDataFormatter extends JAbstractRegionalFormatter {

  //////////////////////////////////////////////////////////////////////////////
  //
  //   INSTANCE VARIABLES
  //
  //////////////////////////////////////////////////////////////////////////////
  private BizPais oBusinessCountry;
  private JMap<String, DecimalFormat> oCurrencyFormats;
  private JList<DecimalFormat> oCurrencyFormatsByDecimals;
  private JMap<String, String> oCurrencyPatterns;
  private JMap<String, DecimalFormat> oNumberToStringFormats;
  private JList<DecimalFormat> oNumberToStringFormatsByDecimals;

  //////////////////////////////////////////////////////////////////////////////
  //
  //   CONSTRUCTORS
  //
  //////////////////////////////////////////////////////////////////////////////
  JBusinessDataFormatter(BizPais zUserCountry, BizPais zBusinessCountry) throws Exception {
    super(zUserCountry);
    this.oBusinessCountry = zBusinessCountry;
  }

  //////////////////////////////////////////////////////////////////////////////
  //
  //   METHODS
  //
  //////////////////////////////////////////////////////////////////////////////

  //
  //   format methods
  //

  /**
   * Formats a double to the format configured for the business region local
   * currency.
   */
  public String formatCurrency(double zValue) throws Exception {
    return formatCurrency(zValue, this.oBusinessCountry.GetMonedaLocal());
  }
  public String formatCurrencyRedondeoSimetrico(double zValue) throws Exception {
    return formatCurrencyRedondeoSimetrico(zValue, this.oBusinessCountry.GetMonedaLocal());
  }
  /**
   * Formats a double to the format configured for the given currency.
   */
  public String formatCurrency(double zValue, String zCurrencyId) throws Exception {
    return this.getCurrencyFormat(zCurrencyId).format(JTools.forceRd(zValue, getCurrencyFractionDigits(zCurrencyId)));
  }
  public String formatCurrencyRedondeoSimetrico(double zValue, String zCurrencyId) throws Exception {
    return this.getCurrencyFormat(zCurrencyId).format(JTools.rd(zValue, getCurrencyFractionDigits(zCurrencyId)));
  }
  /**
   * Formats a double to the format configured for the given decimals count.
   */
  public String formatCurrency(double zValue, int zDecimals) throws Exception {
    return this.getCurrencyFormat(zDecimals).format(JTools.forceRd(zValue, zDecimals));
  }
  public String formatCurrencyRedondeoSimetrico(double zValue, int zDecimals) throws Exception {
    return this.getCurrencyFormat(zDecimals).format(JTools.rd(zValue, zDecimals));
  }

  //
  //   getters
  //
  /**
   * Answers the decimal formatter used to format values of the business region
   * local currency.
   */
  public DecimalFormat getCurrencyFormat() throws Exception {
    return getCurrencyFormat(this.oBusinessCountry.GetMonedaLocal());
  }
  /**
   * Answers the decimal formatter used to format values of the given currency.
   */
  public DecimalFormat getCurrencyFormat(String zCurrencyId) throws Exception {
    DecimalFormat format = this.getCurrencyFormatsByCurrency().getElement(zCurrencyId);
    if (format!=null) return format;

    BizMoneda oMoneda = BizMoneda.obtenerMoneda(zCurrencyId, true);
//    String sBusinessCountryId = this.oBusinessCountry.GetPais();
//    oMoneda.SetNoExcSelect(true);
//    if (!oMoneda.Read(zCurrencyId)) {
//      JExcepcion.SendError("La moneda '" + zCurrencyId + "' no está configurada");
//    }
    format = new DecimalFormat(oMoneda.getCurrencyFormat(), this.getFormattingSymbols());
    DecimalFormatSymbols newSymbols = format.getDecimalFormatSymbols();
    newSymbols.setCurrencySymbol(oMoneda.GetSimbolo());
    format.setDecimalFormatSymbols(newSymbols);
    this.getCurrencyFormatsByCurrency().addElement(zCurrencyId, format);
    this.getCurrencyPatternsByCurrency().addElement(zCurrencyId, oMoneda.getCurrencyFormat());
    return format;
  }

  /**
   * Answers the decimal formatter used to format values of the given currency.
   */
  public DecimalFormat getCurrencyFormat(int zDecimals) throws Exception {
    JList<DecimalFormat> oFormats = this.getCurrencyFormatsByDecimalsList(zDecimals);
    DecimalFormat oFormat = oFormats.getElementAt(zDecimals);
    if (oFormat==null) {
      String sPattern = createPatternByDecimals(zDecimals);
      oFormat = new DecimalFormat(sPattern, this.getFormattingSymbols());
      oFormats.setElementAt(zDecimals, oFormat);
    }
    return oFormat;
  }

  public static String createPatternByDecimals(int zDecimals) throws Exception {
    return createPatternByNumberDecimals(zDecimals);
/*    String sPattern = "#,###,##0";
    if (zDecimals > 0) {
      sPattern += '.';
      for (int i = 0; i < zDecimals; i++) {
        sPattern += "0";
      }
    }
    sPattern = sPattern + ";-" + sPattern;
    return sPattern;
 */
  }

  public static String createPatternByNumberDecimals(int zDecimals) throws Exception {
    return createPatternByNumberDecimals(zDecimals,7,true,true);
  }

  public static String createPatternByNumberDecimals(int zDecimals, int zNumber) throws Exception {
    return createPatternByNumberDecimals(zDecimals,zNumber,true,true);
  }


  public static String createPatternByNumberDecimals(int zDecimals, int zNumber, boolean zGrop, boolean zZero) throws Exception {
    String sPattern = new String();
    String sDat = new String();
    sDat =  (zGrop ? ",###":"###");
    for (int i = 0; i < (zNumber/3); i++) {
      sPattern += sDat;
    }
    for (int i = 0; i < (zNumber % 3); i++) {
      sPattern = "#" + sPattern;
    }
    if (sPattern.startsWith(",")) sPattern = sPattern.substring(1,sPattern.length());

    sPattern = sPattern.substring(0,sPattern.length()-1) +  ( zZero? "0":"#") ;

    if (zDecimals > 0) {
      sPattern += '.' + JTools.stringRepetition("0",zDecimals);
    }
    sPattern = sPattern + ";-" + sPattern;
    return sPattern;
  }

  /**
   * Answers the formatting pattern used to format values of the business region
   * local currency.
   */
  public String getCurrencyPattern() throws Exception {
    return getCurrencyPattern(this.oBusinessCountry.GetMonedaLocal());
  }
  /**
   * Answers the formatting pattern used to format values of the given currency.
   */
  public String getCurrencyPattern(String zCurrencyId) throws Exception {
    String sPattern = this.getCurrencyPatternsByCurrency().getElement(zCurrencyId);
    if (sPattern==null) {
      this.getCurrencyFormat(zCurrencyId);
      sPattern = this.getCurrencyPatternsByCurrency().getElement(zCurrencyId);
    }
    return sPattern;
  }

  /**
   * Answers the currency symbol for the business region local currency.
   */
  public String getCurrencySymbol() throws Exception {
    return getCurrencySymbol(this.oBusinessCountry.GetMonedaLocal());
  }
  /**
   * Answers the currency symbol for the given curency.
   */
  public String getCurrencySymbol(String zCurrencyId) throws Exception {
    return this.getCurrencyFormat(zCurrencyId).getDecimalFormatSymbols().getCurrencySymbol();
  }

  /**
   * Answers the decimal places used to format values of the business region
   * local currency.
   */
  public int getCurrencyFractionDigits() throws Exception {
    return getCurrencyFractionDigits(this.oBusinessCountry.GetMonedaLocal());
  }
  public int getMaximumCurrencyFractionDigits() throws Exception {
    return getMaximumCurrencyFractionDigits(this.oBusinessCountry.GetMonedaLocal());
  }

  /**
   * Answers the decimal places used to format values of the given currency.
   */
  public int getCurrencyFractionDigits(String zCurrencyId) throws Exception {
    return this.getCurrencyFormat(zCurrencyId).getMinimumFractionDigits();
  }
  public int getMaximumCurrencyFractionDigits(String zCurrencyId) throws Exception {
    return this.getCurrencyFormat(zCurrencyId).getMaximumFractionDigits();
  }

  public int getOptionalDecimals() throws Exception {
    return this.getOptionalDecimals(this.oBusinessCountry.GetMonedaLocal());
  }
  public int getOptionalDecimals(String moneda) throws Exception {
  	return BizMoneda.obtenerMoneda(moneda, true).getOptionalDeciamls();
  }

  public int getOptionalMultiplicator() throws Exception {
    return this.getOptionalDecimals(this.oBusinessCountry.GetMonedaLocal());
  }
  public long getOptionalMultiplicador(String moneda) throws Exception {
  	return BizMoneda.obtenerMoneda(moneda, true).getOptionalMultiplicatorValue();
  }
  public String getOptionalMultiplicadorDescription(String moneda) throws Exception {
  	return BizMoneda.obtenerMoneda(moneda, true).getOptionalMultiplicatorDescription();
  }

  public int getTotalFractionDigits() throws Exception {
    return this.getCurrencyFractionDigits() + this.getOptionalDecimals();
  }
  public int getTotalFractionDigits(String moneda) throws Exception {
    return this.getCurrencyFractionDigits(moneda) + this.getOptionalDecimals(moneda);
  }

  

  //
  //   internal methods
  //
  private JMap<String, DecimalFormat> getCurrencyFormatsByCurrency() {
    if (this.oCurrencyFormats==null) {
      this.oCurrencyFormats = JCollectionFactory.createMap();
    }
    return this.oCurrencyFormats;
  }
  private JMap<String, String> getCurrencyPatternsByCurrency() {
    if (this.oCurrencyPatterns==null) {
      this.oCurrencyPatterns = JCollectionFactory.createMap();
    }
    return this.oCurrencyPatterns;
  }
  private JMap<String, DecimalFormat> getNumberToStringFormatsByCurrency() {
    if (this.oNumberToStringFormats==null) {
      this.oNumberToStringFormats = JCollectionFactory.createMap();
    }
    return this.oNumberToStringFormats;
  }


  private synchronized JList<DecimalFormat> getCurrencyFormatsByDecimalsList(int zMinimumDecimals) {
    if (this.oCurrencyFormatsByDecimals==null) {
      this.oCurrencyFormatsByDecimals = JCollectionFactory.createList(zMinimumDecimals + 1);
    }
    if (zMinimumDecimals >= this.oCurrencyFormatsByDecimals.size()) {
      int iToAdd = zMinimumDecimals - this.oCurrencyFormatsByDecimals.size() + 1;
      for (int i = 0; i < iToAdd; i++) {
        this.oCurrencyFormatsByDecimals.addElement(null);
      }
    }
    return this.oCurrencyFormatsByDecimals;
  }



  private synchronized JList<DecimalFormat> getNumberToStringFormatsByDecimalsList(int zMinimumDecimals) {
    if (this.oNumberToStringFormatsByDecimals==null) {
      this.oNumberToStringFormatsByDecimals = JCollectionFactory.createList(zMinimumDecimals + 1);
    }
    if (zMinimumDecimals >= this.oNumberToStringFormatsByDecimals.size()) {
      int iToAdd = zMinimumDecimals - this.oNumberToStringFormatsByDecimals.size() + 1;
      for (int i = 0; i < iToAdd; i++) {
        this.oNumberToStringFormatsByDecimals.addElement(null);
      }
    }
    return this.oNumberToStringFormatsByDecimals;
  }
  public DecimalFormat getNumberToStringFormat(int zDecimals) throws Exception {
    JList<DecimalFormat> oFormats = this.getNumberToStringFormatsByDecimalsList(zDecimals);
    DecimalFormat oFormat = oFormats.getElementAt(zDecimals);
    if (oFormat==null) {
      String sPattern = "####0";
      if (zDecimals > 0) {
        sPattern += '.';
        for (int i = 0; i < zDecimals; i++) {
          sPattern += "0";
        }
      }
      sPattern = sPattern + ";-" + sPattern;
      oFormat = new DecimalFormat(sPattern);
      DecimalFormatSymbols newSymbols = oFormat.getDecimalFormatSymbols();
      newSymbols.setDecimalSeparator('.');
      oFormat.setDecimalFormatSymbols(newSymbols);
      oFormats.setElementAt(zDecimals, oFormat);
    }
    return oFormat;
  }

  public DecimalFormat getNumberToStringFormat(String zCurrencyId) throws Exception {
    DecimalFormat oFormat = this.getNumberToStringFormatsByCurrency().getElement(zCurrencyId);
    if (oFormat==null) {
      BizMoneda oMoneda = new BizMoneda();
      oMoneda.SetNoExcSelect(true);
      if (!oMoneda.Read(zCurrencyId)) {
        JExcepcion.SendError("La moneda '" + zCurrencyId + "' no está configurada");
      }
      oFormat = new DecimalFormat(stripThousandSeperators(oMoneda.getCurrencyFormat()), this.getFormattingSymbols());
      DecimalFormatSymbols newSymbols = oFormat.getDecimalFormatSymbols();
      newSymbols.setCurrencySymbol(oMoneda.GetSimbolo());
      newSymbols.setDecimalSeparator('.');
      oFormat.setDecimalFormatSymbols(newSymbols);
      this.getNumberToStringFormatsByCurrency().addElement(zCurrencyId, oFormat);
    }
    return oFormat;
  }
  private String stripThousandSeperators(String xFormat) throws Exception {
    return xFormat.replaceAll(",","");
  }

  /**
   * Fixed Decimal point = "."
   * No Thousand Seperator
   * Decimals digits depend on the country
   */
  public String formatNumberToString(double zValue) throws Exception {
    return formatNumberToString(zValue, this.oBusinessCountry.GetMonedaLocal());
  }
  public String formatNumberToStringRedondeoSimetrico(double zValue) throws Exception {
    return formatNumberToStringRedondeoSimetrico(zValue, this.oBusinessCountry.GetMonedaLocal());
  }
  /**
   * Fixed Decimal point = "."
   * No Thousand Seperator
   * Decimals digits depend on the country
   */
  public String formatNumberToString(double zValue, String zCurrencyId) throws Exception {
    return this.getNumberToStringFormat(zCurrencyId).format(JTools.forceRd(zValue, getCurrencyFractionDigits(zCurrencyId)));
  }
  public String formatNumberToStringRedondeoSimetrico(double zValue, String zCurrencyId) throws Exception {
    return this.getNumberToStringFormat(zCurrencyId).format(JTools.rd(zValue, getCurrencyFractionDigits(zCurrencyId)));
  }


  /**
   * Formats the given number to a String with fixed decimal point ('.'), no
   * thousands separator and the given decimal count.
   */
  public String formatNumberToString(double zValue, int zDecimals) throws Exception {
    return this.getNumberToStringFormat(zDecimals).format(JTools.forceRd(zValue, zDecimals));
  }


}
