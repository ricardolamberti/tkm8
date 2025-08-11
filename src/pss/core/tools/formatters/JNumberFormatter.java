package pss.core.tools.formatters;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class JNumberFormatter {

	private static DecimalFormat numerFormat;
	
	private static DecimalFormat getDecimalFormatter() {
		if (numerFormat!=null) return numerFormat;
	  // create number To String format (used for editing number)

		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		symbols.setGroupingSeparator(',');
		
	  DecimalFormat format = new DecimalFormat("################.##############################;-################.##############################", symbols);
	  format.setDecimalSeparatorAlwaysShown(false);
	  return (numerFormat=format);
	}
  
	public static String formatNumberToString(double zValue) throws Exception {
		return getDecimalFormatter().format(zValue);
	}
	public static String formatNumberToString(long zValue) throws Exception {
		return getDecimalFormatter().format(zValue);
	}


}
