package pss.core.services.fields;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * An object which holds a <code>double</code> value.<br>
 * It uses objects of class <code>Double</code> to store the value in its
 * <code>pVal</code> variable.<br>
 * It provides also a formatted <code>String</code> representation which suits
 * the generic number format suitable for the current user. Sublcasses of this
 * class can override the method <code>#isNumberFormatUsed()()</code> to say
 * if the number value must be formatted or not. If not, the resulting
 * <code>String</code> will be in the Java <code>String</code> representation
 * for numbers.
 *
 * @author Leonardo Pronzolino-Yannino
 * @version 1.0.0
 */

import pss.core.tools.JTools;
import pss.core.tools.formatters.JNumberFormatter;
import pss.core.tools.formatters.JRegionalDataFormatter;
import pss.core.tools.formatters.JRegionalFormatterFactory;

public class JFloat extends JObject<Double> {

	public final static int MAX_DIGITS = 16;
	public final static int MAX_DECIMALS = 4;
	private int precision = -1;
	private boolean acceptComma = false;
	

	public boolean isAcceptComma() {
		return acceptComma;
	}

	public void setAcceptComma(boolean acceptComma) {
		this.acceptComma = acceptComma;
	}

	/**
	 * @param precision the precision to set
	 */
	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public JFloat setPrec(int precision) {
		this.precision = precision;
		return this;
	}

	/**
	 * @return the precision
	 */
	public int getPrecision() throws Exception {
		return precision;
	}

	public double getValue() throws Exception {
		this.preset();
		return this.getRawValue();
	};

	public double getRawValue() throws Exception {
		return (this.hasValue()) ? getDoubleValue() : 0d;
	};

	public JFloat() {
	}


	public JFloat(double zVal) {
		super.setValue(new Double(zVal));
	}

	private double getDoubleValue() throws Exception {
		double ret = ((Number) this.getObjectValue()).doubleValue();
		if (this.precision < 0)
			return ret;
		return JTools.rd(ret, this.precision);
	}

	@Override
	public void setValue(String zVal) {
		if (zVal==null ||zVal.length() == 0) {
			super.setValue((Double) null);
			return;
		}
		if (isAcceptComma())
			super.setValue(new Double(JTools.normalizeDoubleWithComma(zVal.trim())));
		else
			super.setValue(new Double(JTools.normalizeDouble(zVal.trim())));
	}

	@Override
	public String getObjectType() {
		return JObject.JFLOAT;
	}

	@Override
	public Class getObjectClass() {
		return Double.class;
	}

	//////////////////////////////////////////////////////////////////////////////
	//
	// FORMATTING METHODS
	//
	//////////////////////////////////////////////////////////////////////////////

	/**
	 * Answers a <code>String</code> representation of the value this
	 * <code>JObject</code> holds, formatted to the user which is going to see it.
	 * It does the same as the <code>#toFormattedString()</code> method, except in
	 * that it does not invoke the <code>#Pre()</code> method first.
	 * <p>
	 * This method may be overridden by subclasses to provide appropriate
	 * representations, depending on the data type they represent.
	 * 
	 * @return a formatted <code>String</code> representation of the value this
	 *         <code>JObject</code> holds
	 */
	@Override
	public String toRawFormattedString() throws Exception {
		if (this.isNull())
			return null;
		JRegionalDataFormatter oFormater = JRegionalFormatterFactory.getRegionalFormatter();
		if (this.hasPrivatePrecision()) {
			return oFormater.formatNumber(this.getRawValue(), getPrivatePrecision());
		} else
			return oFormater.formatNumber(this.getRawValue());
	}

	private int getPrivatePrecision() throws Exception {
		if (this.precision >= 0)
			return this.precision;
		return this.getCustomPrecision();
	}

	private boolean hasPrivatePrecision() throws Exception {
		return this.getPrivatePrecision()>=0;
	}

	@Override
	public String toRawString() throws Exception {
		if (!this.hasValue())
			return "";
		return JNumberFormatter.formatNumberToString(this.getRawValue());
	}

	@Override
	public int getCustomPrecision() throws Exception {
		return -1;
	}
//
//	@Override
//	public boolean hasCustomPrecision() throws Exception {
//		return this.getCustomPrecision()!=-1;
//	}

	public String toInputString() throws Exception {
		Object obj = this.asObject();
		if (obj == null)
			return "";
	    if (String.valueOf(obj).toLowerCase().contains("e")) {
	        NumberFormat formatter = new DecimalFormat();
	        formatter.setMaximumFractionDigits(25);
	        return formatter.format(obj);
	    } else
	        return String.valueOf(obj);

	}

}
