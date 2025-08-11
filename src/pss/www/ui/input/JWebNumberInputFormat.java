/*
 * Created on 25-ago-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.input;

import java.text.Format;

import pss.core.tools.JTools;
import pss.core.tools.formatters.JRegionalFormatterFactory;

public class JWebNumberInputFormat extends JWebInputFormat {

	private int iTotalDigits;
	private int iDecimals;
	private char cGroupingSep;
	private char cDecimalSep;

	JWebNumberInputFormat(String zPattern, int zTotalDigits, int zDecimals, char zGroupingSeparator, char zDecimalSeparator) {
		super(zPattern, false);
		this.iTotalDigits = zTotalDigits;
		this.iDecimals = zDecimals;
		this.cGroupingSep = zGroupingSeparator;
		this.cDecimalSep = zDecimalSeparator;
	}

	private boolean isValidSep(char sep) {
		return sep == '.' || sep == ',';
	}

	@Override
	protected void resolveValidationAttributes(String zJavaTimePattern) throws Exception {
		// resolve flags
		boolean hasDecimalSep = false;
		if (this.iDecimals > 0) {
			if (!this.isValidSep(this.cDecimalSep)) {
				throw new RuntimeException("Not a valid decimal separator: " + this.cDecimalSep);
			} else if (this.cDecimalSep == this.cGroupingSep) {
				throw new RuntimeException("Grouping and decimal separators cannot be the same: " + this.cDecimalSep);
			}
			hasDecimalSep = true;
		}
		boolean hasGroupingSep = this.isValidSep(this.cGroupingSep);
		// set format chars
		if (hasDecimalSep) {
			this.sValidationFormatChars = String.valueOf(this.cDecimalSep);
		} else {
			this.sValidationFormatChars = " ";
		}
		if (hasGroupingSep) {
			this.sValidationFormatChars += String.valueOf(this.cGroupingSep);
		} else {
			this.sValidationFormatChars += " ";
		}
		// resolve validation pattern
		this.sValidationPattern = JTools.justifyStrings("0", null, this.iTotalDigits<this.iDecimals?0:this.iTotalDigits - this.iDecimals, '0');
		if (hasDecimalSep) {
			this.sValidationPattern += ".";
			this.sValidationPattern += JTools.justifyStrings("0", null, this.iDecimals, '0');
		}
	}

	@Override
	protected Format resolveFormatter(String zJavaDatePattern) throws Exception {
		return JRegionalFormatterFactory.getRegionalFormatter().getCustomNumberFormat(zJavaDatePattern);
	}

	@SuppressWarnings("unused")
	private char validateSeparator(String zPattern) {
		int count = zPattern.length();
		char cLastFoundSep = 0x00;
		for (int i = 0; i < count; i++) {
			char c = zPattern.charAt(i);
			if (c != 'H' && c != 'm' && c != 's') {
				if (cLastFoundSep == 0x00) {
					cLastFoundSep = c;
				} else if (cLastFoundSep != c) {
					return 0x00; // two different separators !!!!
				}
			}
		}
		return cLastFoundSep;
	}

	@SuppressWarnings("unused")
	private String textValPattern(String zJavaDatePattern, char cSep) {
		char[] patternChars = new char[3];
		int count = zJavaDatePattern.length();
		char c;
		char cLast = 0x00;
		int iAddedChars = 0;
		for (int i = 0; i < count; i++) {
			c = Character.toUpperCase(zJavaDatePattern.charAt(i));
			if (cLast == 0x00) {
				patternChars[iAddedChars++] = c;
				cLast = c;
			} else if (c != cSep && c != cLast) {
				patternChars[iAddedChars++] = c;
				cLast = c;
			}
		}
		return new String(patternChars, 0, iAddedChars);
	}

}
