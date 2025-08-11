package pss.core.services.fields;

import java.io.Serializable;

/**
 * Title:
 * @author Iván Rubín
 */

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import pss.core.tools.JDateTools;
import pss.core.tools.PssLogger;
import pss.core.tools.formatters.JRegionalFormatterFactory;

public class JDateTime extends JDate {
	
	private boolean nomilliseconds = false;
	public String dbFormat=null;

	public static String DATETIME_FORMAT="dd/MM/yyyy HH:mm:ss";
	public static String DATETIME_FORMAT_SS="dd/MM/yyyy HH:mm";

	@Override
	public String getObjectType() {
		return JObject.JDATETIME;
	}

	@Override
	public Class getObjectClass() {
		return JDateTime.class;
	}

	public JDateTime() {
	}

	public JDateTime(String sformat) {
		this.dbFormat=sformat;
	}

	
	public JDateTime(JDate zDate) {
		this.setValue((Serializable)zDate.getObjectValue());
	}

	public JDateTime(boolean value) {
		nomilliseconds=value;
	}
	
	public JDateTime(Date zDate) {
		this.setValue(zDate);
	}

//	public JDateTime(String zDate) throws Exception {
//		JExcepcion.SendError("Not implemented");
//	}

	public Date getValue() throws Exception {
		preset();
		return getRawValue();
	};

	public Date getRawValue() throws Exception {
		return (getObjectValue()==null) ? null : (Date) getObjectValue();
	};

	@Override
	public String toRawFormattedString() throws Exception {
		if ( this.getRawValue() == null ) return "";
		return JDateTools.DateToString(this.getRawValue(), this.getUIFormat());
	}
	
	public String getUIFormat() throws Exception {
		return JRegionalFormatterFactory.getRegionalFormatter().getDateTimePattern();
	}
	public String getDbFormat() throws Exception {
		if (this.dbFormat!=null) return dbFormat;
		if (this.nomilliseconds)
			return "dd/MM/yyyy HH:mm:ss";
		else
			return "dd/MM/yyyy HH:mm:ss.SSS";
	}

	@Override
	public String toRawString() {
		try {
			if (getObjectValue()==null) return "";
	 		return JDateTools.DateToString((Date) getObjectValue(), this.getDbFormat());
		} catch (Exception e) {
			PssLogger.logDebug(e);
			return "Error";
		}
	}

	private static char getSeparatorIn(String zVal) {
		if (zVal.indexOf('/')>=0) return '/';
		else return '-';
	}

	@Override
	public void setValue(String zVal) throws Exception {
		if (zVal==null||(zVal=zVal.trim()).length()==0) {
			super.setValue((Date) null);
			return;
		}
		// get the separator and create a tokenizer with it
		char cSep=getSeparatorIn(zVal);
		String sDateTime=zVal.replace(cSep, '/');
		if (sDateTime.indexOf(',')!=-1) sDateTime=sDateTime.replace(',', ' ');
		if (sDateTime.trim().equals("")) super.setValue("");
		else if (sDateTime.length()<=10)
			super.setValue(JDateTools.StringToDate(sDateTime));
		else
			if (sDateTime.length()<=16)
				super.setValue(JDateTools.StringToDate(sDateTime, DATETIME_FORMAT_SS));
			else
				super.setValue(JDateTools.StringToDate(sDateTime, DATETIME_FORMAT));
	}

	public boolean before(JDateTime zDateTime) throws Exception {
		return this.compareTo(zDateTime)<0;
	}

	public boolean after(JDateTime zDateTime) throws Exception {
		return this.compareTo(zDateTime)>0;
	}

	public boolean equalsDate(JDateTime zDateTime) throws Exception {
		return this.compareTo(zDateTime)==0;
	}

	/**
	 * equals to 0 =====> Si las 2 fechas son iguales. smaller than 0 =====> Si el parémetro es mayor a la fecha del objetoa greater than 0 =====> Si el parámetro es menor a la fecha del objeto
	 */
	public int compareTo(JDateTime zDateTime) throws Exception {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(this.getValue());
		Calendar toCompare=new GregorianCalendar();
		toCompare.setTime(zDateTime.getValue());
		if (calendar.after(toCompare)) {
			return 1;
		} else if (calendar.before(toCompare)) {
			return -1;
		} else if (calendar.equals(toCompare)) {
			return 0;
		}
		return 0;
	}

	public Date set(Date zDate, int zCalendarField, int zValue) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(zDate);
		calendar.set(zCalendarField, zValue);
		return calendar.getTime();
	}

	public void set(int zCalendarField, int zValue) throws Exception {
		Date value=this.set(this.getValue(), zCalendarField, zValue);
		this.setValue(value);
	}

	public void setMonth(JDate zDate, int zMonth) throws Exception {
		this.set(Calendar.MONTH, zMonth);
	}

	public void setDay(int zDay) throws Exception {
		this.set(Calendar.DAY_OF_MONTH, zDay);
	}

	public void add(int zCalendarField, int zValue) throws Exception {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(this.getValue());
		calendar.add(zCalendarField, zValue);
		this.setValue(calendar.getTime());
	}

//	public Date addDays(int zValue) throws Exception {
//		this.add(Calendar.DAY_OF_MONTH, zValue);
//	}

	public void addMonths(int zValue) throws Exception {
		this.add(Calendar.MONTH, zValue);
	}

	public void addSeconds(int zCantSeconds) throws Exception {
		Calendar zDate=Calendar.getInstance();
		zDate.setTime(getValue());
		zDate.add(Calendar.SECOND, zCantSeconds);
		setValue(zDate.getTime());
	}

	public void addMinutes(int zMinutes) throws Exception {
		Calendar zDate=Calendar.getInstance();
		zDate.setTime(getValue());
		zDate.add(Calendar.MINUTE, zMinutes);
		setValue(zDate.getTime());
	}

	@Override
	public void setValueFormUI(String val) throws Exception {
		val=val.replaceAll(",", " ");
		val=val.replace("%3A", ":");
		super.setValueFormUI(val);
	}
	
	public boolean savedAsString() throws Exception {
		return this.dbFormat!=null; 
	}


}
