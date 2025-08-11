package pss.core.services.fields;

/**
 * An object which holds a <code>Date</code> value.<br>
 * It uses objects of class <code>Date</code> to store the value in its
 * <code>pVal</code> variable.<br>
 * It provides also a formatted <code>String</code> representation which suits
 * the date format suitable for the current user. Sublcasses of this class
 * can override the method <code>#isShortFormat()</code> to format the value
 * either to the short or to the long date format configured for the user.
 *
 * @author Leonardo Pronzolino-Yannino
 * @version 1.0.0
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.formatters.JRegionalFormatterFactory;

// ========================================================================== //
// Clase para tipo de dato JDate
// ========================================================================== //
public class JDate extends JObject<Date> {

	public char SEPARATOR_CHAR = '-';
	public String dbFormat=null;

	public Date getValue() throws Exception {
		preset();
		return getRawValue();
	};

	public Date getRawValue() throws Exception {
		return (getObjectValue() == null) ? null : (Date) getObjectValue();
	};

	public JDate(String sformat) {
		this.dbFormat=sformat;
	}

	public JDate() {
	}

	public JDate(Date zVal) {
		super.setValue(zVal);
	}
	
	public boolean savedAsString() throws Exception {
		return this.dbFormat!=null; 
	}

	public void setValue(Object zVal) {
		if (zVal instanceof String) {
			try {this.setValue((String)zVal);} catch (Exception e) {}
			return;
		}
		super.setValue(zVal);
	}
	
	@Override
	public void setValue(String zVal) throws Exception {
		if (zVal == null || zVal.length() == 0 ) {
			super.setValue((Date) null);
			return;
		}
		super.setValue(JDateTools.StringToDate(zVal, JDateTools.DEFAULT_DATE_FORMAT));
	}

	public static Date today() throws Exception {
		// hacerlo mas optimo pero tiene la hora, min, seg tiene que ser igual a 0
		return JDateTools.StringToDate(JDateTools.DateToString(new Date()), JDateTools.DEFAULT_DATE_FORMAT);
	}

	// --------------------------------------------------------------------------
	// //
	// Convierto un time a un string
	// --------------------------------------------------------------------------
	// //
	public static String TimeToString(Date zVal, String zFormat) {
		String sRta = null;
		SimpleDateFormat oSimple = new SimpleDateFormat();
		oSimple.applyPattern(zFormat);
		try {
			sRta = oSimple.format(zVal);
		} catch (Exception E) {
			sRta = null;
		}
		return sRta;
	}
	
	public String getDbFormat() throws Exception {
		if (this.dbFormat==null) return JDateTools.DEFAULT_DATE_FORMAT;
		return this.dbFormat;
	}
	
	public String getUIFormat() throws Exception {
		if (this.isShortFormat())
			return JRegionalFormatterFactory.getRegionalFormatter().getShortDatePattern();
		else
			return JRegionalFormatterFactory.getRegionalFormatter().getLongDatePattern();
	}

	// --------------------------------------------------------------------------
	// //
	// Devuelvo el date como string
	// --------------------------------------------------------------------------
	// //
	// public String toString() {
	/**
	 * Answers the value this <code>JObject</code> holds as a <code>String</code>.
	 * It does the same as the <code>#toString()</code> method, except in that it
	 * does not invoke the <code>#Pre()</code> method first.
	 * <p>
	 * This method may be overridden by subclasses to provide appropriate
	 * representations, depending on the data type they represent.
	 * 
	 * @return the value this <code>JObject</code> holds as a <code>String</code>
	 */
	@Override
	public String toRawString() {
		try {
			if (getObjectValue() == null)
				return "";
			return JDateTools.DateToString((Date) getObjectValue(), this.getDbFormat());
		} catch (Exception e) {
			return "Error";
		}
	}

	public Date set(Date zDate, int zCalendarField, int zValue) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(zDate);
		calendar.set(zCalendarField, zValue);
		return calendar.getTime();
	}

	public void set(int zCalendarField, int zValue) throws Exception {
		Date value = this.set(this.getValue(), zCalendarField, zValue);
		this.setValue(value);
	}

	public void setMonth(JDate zDate, int zMonth) throws Exception {
		this.set(Calendar.MONTH, zMonth);
	}

	public void setDay(int zDay) throws Exception {
		this.set(Calendar.DAY_OF_MONTH, zDay);
	}

	public void add(int zCalendarField, int zValue) throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.getValue());
		calendar.add(zCalendarField, zValue);
		this.setValue(calendar.getTime());
	}

	public Date addDays(int zValue) throws Exception {
		this.add(Calendar.DAY_OF_MONTH, zValue);
		return this.getValue();
	}

	public void addMonths(int zValue) throws Exception {
		this.add(Calendar.MONTH, zValue);
	}

	/**
	 * Reset the hour, minutes and second to 0
	 */
	public void resetTime() throws Exception {
		java.text.SimpleDateFormat oFormat = new java.text.SimpleDateFormat("ddMMyyyy");
		this.setValue(oFormat.parse(oFormat.format(this.getValue())));
	}

	public JDate(long zLong) {
		super.setValue(new Date(zLong));
	}

	/*************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
	 * 0 =====> Si las 2 fechas son iguales. < 0 =====> Si el parémetro es mayor a
	 * la fecha del objetoa > 0 =====> Si el parámetro es menor a la fecha del
	 * objeto
	 * 
	 ************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
	public int compareTo(String yyyyMMdd) throws Exception {
		String sDate = JDateTools.DateToString(getValue());
		String thisyyyyMMdd = sDate.substring(6) + sDate.substring(3, 5) + sDate.substring(0, 2);

		return thisyyyyMMdd.compareTo(yyyyMMdd);
	}

	public int compareTo(JDate oAuxDate) throws Exception {
		String sDateAux = JDateTools.DateToString(oAuxDate.getValue());

		String sDateFormatted = sDateAux.substring(6) + sDateAux.substring(3, 5) + sDateAux.substring(0, 2);

		return compareTo(sDateFormatted);
	}

	public boolean before(JDate zDate) throws Exception {
		return this.compareTo(zDate) < 0;
	}

	public boolean after(JDate zDate) throws Exception {
		return this.compareTo(zDate) > 0;
	}

	public boolean equalsDate(JDate zDate) throws Exception {
		return this.compareTo(zDate) == 0;
	}

	/**
	 * Returns a new instance with the same value
	 */
	public JDate copy() throws Exception {
		Date oValue = this.getValue();
		Date oNewValue = new Date(oValue.getTime());
		return new JDate(oNewValue);
	}

	public int getYear() throws Exception {
		return Integer.parseInt(JDateTools.DateToString(getValue()).substring(6));
	}

	public int getMonth() throws Exception {
		return Integer.parseInt(JDateTools.DateToString(getValue()).substring(3, 5));
	}

	public int getDays() throws Exception {
		return Integer.parseInt(JDateTools.DateToString(getValue()).substring(0, 2));
	}

	@Override
	public String getObjectType() {
		return JObject.JDATE;
	}

	@Override
	public Class getObjectClass() {
		return Date.class;
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
		if (this.isNull()) return "";
		return JDateTools.DateToString(this.getRawValue(), this.getUIFormat()); 
	}
	
	

	protected boolean isShortFormat() {
		return true;
	}

	public JHour getTime() throws Exception {
		JHour oHora = new JHour();
		Calendar zDate = Calendar.getInstance();
		zDate.setTime(getValue());
		oHora.setValue(JTools.LPad(String.valueOf(zDate.get(Calendar.HOUR_OF_DAY)), 2, "0") + ":" + JTools.LPad(String.valueOf(zDate.get(Calendar.MINUTE)), 2, "0") + ":" + JTools.LPad(String.valueOf(zDate.get(Calendar.SECOND)), 2, "0"));
		return oHora;
	}

	public void stripTime() throws Exception {
		Calendar zDate = Calendar.getInstance();
		zDate.setTime(getValue());
		zDate.set(Calendar.HOUR_OF_DAY, 0);
		zDate.set(Calendar.MINUTE, 0);
		zDate.set(Calendar.SECOND, 0);
		setValue(zDate.getTime());
	}

	public void addHours(int zCantHours) throws Exception {
		Calendar zDate = Calendar.getInstance();
		zDate.setTime(getValue());
		zDate.add(Calendar.HOUR, zCantHours);
		setValue(zDate.getTime());
	}

	public static Date DateTimeToDate(Date zFecha, JHour zHora) throws Exception {
		return JDateTools.DateTimeToDate(zFecha, JTools.LPad(zHora.GetValorLong()+"",6,"0") , "HHmmss");
	}

	public void addSeconds(int zCantSeconds) throws Exception {
		Calendar zDate = Calendar.getInstance();
		zDate.setTime(getValue());
		zDate.add(Calendar.SECOND, zCantSeconds);
		setValue(zDate.getTime());
	}

	public void addMiliseg(int zCant) throws Exception {
		Calendar zDate = Calendar.getInstance();
		zDate.setTime(getValue());
		zDate.add(Calendar.MILLISECOND, zCant);
		setValue(zDate.getTime());
	}

	public void addMinutes(int zMinutes) throws Exception {
		Calendar zDate = Calendar.getInstance();
		zDate.setTime(getValue());
		zDate.add(Calendar.MINUTE, zMinutes);
		setValue(zDate.getTime());
	}

	public static boolean ifVigente(JDate pVigDesde, JDate pVigHasta, JHour pHoraDesde, JHour pHoraHasta, boolean bHoraTipoRango) throws Exception {
		long lFechaHoy = Long.parseLong(JDateTools.CurrentDate("yyyyMMdd"));
		long lHoraHoy = Long.parseLong(JDateTools.CurrentTime("HHmmss"));

		long lFechaDesde = Long.parseLong(JDateTools.DateToString(pVigDesde.getValue(), "yyyyMMdd"));
		long lFechaHasta = Long.parseLong(JDateTools.DateToString(pVigHasta.getValue(), "yyyyMMdd"));
		long lHoraDesde = pHoraDesde.GetValorLong();
		long lHoraHasta = pHoraHasta.GetValorLong();

		if (bHoraTipoRango) {
			if (lFechaHoy >= lFechaDesde && lFechaHasta >= lFechaHoy && lHoraHoy >= lHoraDesde && lHoraHasta >= lHoraHoy)
				return true;
		} else {
			if ((lFechaHoy > lFechaDesde || lFechaHoy == lFechaDesde && lHoraHoy >= lHoraDesde) && (lFechaHasta > lFechaHoy || lFechaHoy == lFechaHasta && lHoraHoy < lHoraHasta))
				return true;

		}
		return false;
	}

	public static String getSqlDateBetween(String sCampoFecha, String sCampoHora, JDate pFechaDesde, JHour pHoraDesde, JDate pFechaHasta, JHour pHoraHasta) throws Exception {
		return getSqlDateBetween(sCampoFecha, sCampoHora, pFechaDesde, pHoraDesde, pFechaHasta, pHoraHasta, false, null);
	}

	public static String getSqlDateBetween(String sCampoFecha, String sCampoHora, JDate pFechaDesde, JHour pHoraDesde, JDate pFechaHasta, JHour pHoraHasta, boolean bAcumulado, JDate dFechaDesdeAcumulado) throws Exception {
		String sWhere = "";

		// fecha y Hora
		if (bAcumulado) {
			sWhere += " AND (" + sCampoFecha + " < '" + pFechaDesde + "' ";
			sWhere += " or (" + sCampoFecha + " = '" + pFechaDesde + "' and " + sCampoHora + "<='" + pHoraDesde + "')) ";
			if (dFechaDesdeAcumulado.isNotNull())
				sWhere += " AND (" + sCampoFecha + " >= '" + dFechaDesdeAcumulado + "' ";
		} else {
			if (pHoraDesde.getValue().equals("00:00:00"))
				sWhere += " AND " + sCampoFecha + " >= '" + pFechaDesde + "' ";
			else {
				sWhere += " AND (" + sCampoFecha + " > '" + pFechaDesde + "' ";
				sWhere += " or (" + sCampoFecha + " = '" + pFechaDesde + "' and " + sCampoHora + ">='" + pHoraDesde.GetValorString() + "')) ";
			}
			if (pHoraHasta.getValue().trim().equals("23:59:59"))
				sWhere += " AND " + sCampoFecha + " <= '" + pFechaHasta + "' ";
			else {
				sWhere += " AND (" + sCampoFecha + " < '" + pFechaHasta + "' ";
				sWhere += " or (" + sCampoFecha + " = '" + pFechaHasta + "' and " + sCampoHora + "<='" + pHoraHasta.GetValorString() + "')) ";
			}
		}
		return sWhere;
	}
}
