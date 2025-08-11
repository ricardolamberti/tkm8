package pss.core.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;

import pss.common.security.BizUsuario;
import pss.core.services.fields.JHour;

public class JDateTools {

	public static String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
	public static String DEFAULT_HOUR_FORMAT = "HH:mm:ss";
	
	public static Date MaxDate() throws Exception {
		return StringToDate("29991230", "yyyymmdd");
	}


	public static Date MinDate() throws Exception {
		return StringToDate("19000101", "yyyymmdd");
	}
	//extrae el date si es un datetime
	public static String YYYYMMDDToDefault(String s) throws Exception {
		String fecha=s;
		if (fecha.indexOf(" ")!=-1) fecha =s.substring(0,s.indexOf(" "));
		Date d = StringToDate(fecha, "yyyy-MM-dd");
		return DateToString(d);
	}

	
	Date oDate;

	public JDateTools() {
	}

	public JDateTools(Date d) {
		oDate = d;
	}

	public Date getValue() {
		return oDate;
	}

	public void setValue(Date d) {
		oDate = d;
	}

  public static char validateSeparator(String zPattern) {
    int count = zPattern.length();
    char cLastFoundSep = 0x00;
    for (int i = 0; i < count; i++) {
      char c = zPattern.charAt(i);
      if (c != 'd' && c != 'M' && c != 'y') {
        if (cLastFoundSep==0x00) {
          cLastFoundSep = c;
        } else if (cLastFoundSep!=c) {
          return '/'; // two different separators !!!!
        }
      }
    }
    return cLastFoundSep;
  }

  
	// --------------------------------------------------------------------------
	// //
	// Convierto una fecha a un string
	// --------------------------------------------------------------------------
	// //
	public static String DateToString(Date zVal) {
		if (zVal==null) return "";
		return DateToString(zVal, JDateTools.DEFAULT_DATE_FORMAT);
	}
	
	public static String DateToString(Date zVal, String zFormat) {
		if (zVal==null) return "";
		SimpleDateFormat simple = new SimpleDateFormat();
		simple.applyPattern(zFormat);
		return simple.format(zVal);
	}



	public static String CurrentDateTime() throws Exception {
		return CurrentDate(DEFAULT_DATE_FORMAT + " " + DEFAULT_HOUR_FORMAT);
	}

	public static String CurrentDate() throws Exception {
		return CurrentDate(DEFAULT_DATE_FORMAT);
	}

	public static String CurrentDate(String zFmt) throws Exception {
		Date oDate = new Date();
		return DateToString(oDate, zFmt);
	}

	public static Date today() throws Exception {
		// hacerlo mas optimo pero tiene la hora, min, seg tiene que ser igual a 0
		return JDateTools.StringToDate(JDateTools.DateToString(new Date()), DEFAULT_DATE_FORMAT);
	}

	public static boolean between(Date date, Date dateStart, Date dateEnd) throws Exception {
		if (date == null || dateStart == null || dateEnd == null) return false;
		return (dateEqualOrAfter(date, dateStart) && dateEqualOrBefore(date, dateEnd));
	}

	public static boolean dateEqualOrAfter(Date date, Date date2) throws Exception {
		if (date==null && date2==null) return true;
		if (date!=null && date2==null) return false;
		if (date==null && date2!=null) return true;
		if (JDateTools.equalsDate(date,date2))	return true;
		return (date.after(date2));

	}
	public static boolean betweenHours(Date date, JHour hourStart, JHour hourEnd) throws Exception {
		if (date == null || hourStart == null || hourEnd == null) return false;
		JHour ahora = JHour.toJHour(date);
		return (ahora.after(hourStart) && ahora.before(hourEnd));
	}

	public static boolean dateEqualOrBefore(Date date, Date date2) throws Exception {
		if (date==null && date2==null) return true;
		if (date!=null && date2==null) return true;
		if (date==null && date2!=null) return false;
		if (JDateTools.equalsDate(date,date2))	return true;
		return (date.before(date2));
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

	public void set(int zCalendarField, int zValue) throws Exception {
		Date value = JDateTools.set(this.getValue(), zCalendarField, zValue);
		this.setValue(value);
	}
	static public Date getDateStartDay(Date zValue) throws Exception {
		if (zValue==null) return null;
		GregorianCalendar c =new GregorianCalendar();
		c.setTime(zValue);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	static public Date getDateEndDay(Date zValue) throws Exception {
		if (zValue==null) return null;
		GregorianCalendar c =new GregorianCalendar();
		c.setTime(zValue);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}
	static public long getAnioActual() throws Exception {
		GregorianCalendar c =new GregorianCalendar();
		c.setTime(new Date());
		return c.get(Calendar.YEAR);
	}
	// --------------------------------------------------------------------------
	// //
	// Convierto una fecha a un string
	// --------------------------------------------------------------------------
	// //
	public static String CurrentTime() {
		return CurrentTime(DEFAULT_HOUR_FORMAT);
	}

	public static String CurrentTime(String zFmt) {
		return TimeToString(new Date(), zFmt);
	}

	// --------------------------------------------------------------------------
	// //
	// Convierte un string a una fecha
	// --------------------------------------------------------------------------
	// //
	public static Date StringToDate(String zVal) throws Exception {
		return StringToDate(zVal, DEFAULT_DATE_FORMAT);
	}
	public static Date StringToDateTime(String zVal) throws Exception {
	  if ( zVal.trim().length() == 10 ) // Vino solo la fecha
	    return StringToDate(zVal);
		return StringToDate(zVal, DEFAULT_DATE_FORMAT+" "+DEFAULT_HOUR_FORMAT);
	}

	public static Date StringToDateNonExec(String zVal, String zFormat) throws Exception {
		try {
			return StringToDate(zVal,zFormat);
		} catch (Exception e) {
			return null;
		}
	}
	public static Date StringToDate(String zVal, String zFormat) throws Exception {
		if (zVal==null) return null;
		if (zVal.equals("")) return null;
		if ( zFormat.indexOf('/') >=0  )
			zFormat = zFormat.replace('/', '-');
		if ( zFormat.indexOf(',') >=0  )
			zFormat = zFormat.replace(',', ' ');
		if ( zVal.indexOf('/')>=0 )
			zVal = zVal.replace('/', '-');
		
		SimpleDateFormat oSimple = new SimpleDateFormat();
		oSimple.applyPattern(zFormat);
		oSimple.setLenient(false);
		try {
			return oSimple.parse(zVal);
		} catch (Exception E) {
			if (zVal.endsWith("0229")&&zVal.length()==8) {
				//error bisiesto
				return oSimple.parse(JTools.replace(zVal, "0229" , "0228"));
			}
			Object zParams[] =  new String[]{zVal,zFormat};
			JExcepcion.SendError(BizUsuario.getMessage("Fecha inválida", zParams));
		}
		return null;
	}
	public static Date StringToTime(String zVal, String zFormat) throws Exception {
		if (zVal==null) return null;
		if (zVal.equals("")) return null;
		if ( zFormat.indexOf('/') >=0  )
			zFormat = zFormat.replace('/', '-');
		if ( zFormat.indexOf(',') >=0  )
			zFormat = zFormat.replace(',', ' ');
		if ( zVal.indexOf('/')>=0 )
			zVal = zVal.replace('/', '-');
		
		SimpleDateFormat oSimple = new SimpleDateFormat();
		oSimple.applyPattern(zFormat);
		oSimple.setLenient(false);
		try {
			return oSimple.parse(zVal);
		} catch (Exception E) {
			Object zParams[] =  new String[]{zVal,zFormat};
			JExcepcion.SendError(BizUsuario.getMessage("Hora inválida", zParams));
		}
		return null;
	}

	// --------------------------------------------------------------------------
	// //
	// Devuelvo el separador de la fecha
	// --------------------------------------------------------------------------
	// //
	public static char getSeparatorIn(String zVal) {
		if (zVal.indexOf('/') >= 0)
			return '/';
		else if (zVal.indexOf('.') >= 0)
			return '.';
		else
			return '-'; // default
	}

	// --------------------------------------------------------------------------
	// //
	// Verifico si la fecha es valida
	// --------------------------------------------------------------------------
	// //
	public static boolean CheckDatetime(String zVal) throws Exception {
		int sep = zVal.indexOf(' ');
		if (sep == -1) 
			return CheckDate(zVal);
		return CheckDate(zVal.substring(0,sep)) && CheckHour(zVal.substring(sep+1));
	}
	public static boolean CheckDatetime(String zVal,boolean exception) throws Exception {
		try {
			return CheckDatetime(zVal);
		} catch (Exception e) {
			if (exception)
				throw e;
		}
		return false;
	}
	
	public static boolean CheckDate(String zVal) throws Exception {
		boolean bRc;

		// ----------------------------------
		// Inicializo las variables locales
		// ----------------------------------
		bRc = true;

		char cSeparator = getSeparatorIn(zVal);
		String sSeparator = String.valueOf(cSeparator);
		if (StringToDate(zVal, "dd" + sSeparator + "MM" + sSeparator + "yyyy") == null) {
			bRc = false;
		}

		// --------------------
		// Seteo la respuesta
		// --------------------
		return bRc;

	}
	
	public static boolean CheckHour(String zHour) {
		Integer iAux;
		try {
			iAux=new Integer(zHour.substring(0, 2));
			if (iAux.intValue()<0||iAux.intValue()>23) return false;

			iAux=new Integer(zHour.substring(3, 5));
			if (iAux.intValue()<0||iAux.intValue()>59) return false;

			iAux=new Integer(zHour.substring(6, 8));
			if (iAux.intValue()<0||iAux.intValue()>59) return false;

		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public static Date set(Date zDate, int zCalendarField, int zValue) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(zDate);
		calendar.set(zCalendarField, zValue);
		return calendar.getTime();
	}

	// --------------------------------------------------------------------------
	// //
	// Devuelvo una fecha a partir de otra como parametro restandole los dias
	// tambien recibidos como parametro
	// --------------------------------------------------------------------------
	// //
	public static Date SubDays(Date zDate, long zDays) {
		Date oDate = new Date(zDate.getTime() - (1000 * 60 * 60 * 24 * zDays));
		return oDate;
	}

	public static Date SubHours(Date zDate, long zHours) {
		Date oDate = new Date(zDate.getTime() - (1000 * 60 * 60 * zHours));
		return oDate;
	}

	public static Date SubMinutes(Date zDate, long zMinutes) {
		Date oDate = new Date(zDate.getTime() - (1000 * 60 * zMinutes));
		return oDate;
	}

	public static Date SubSeconds(Date zDate, long zSeconds) {
		Date oDate = new Date(zDate.getTime() - (1000 * zSeconds));
		return oDate;
	}

	public static Date AddMonths(Date zDate, int zMeses) {
		Calendar oCalendar = Calendar.getInstance();
		oCalendar.setTime(zDate);
		oCalendar.add(Calendar.MONTH, zMeses);
		return oCalendar.getTime();
	}

	public static Date AddYears(Date zDate, int anios) {
		Calendar oCalendar = Calendar.getInstance();
		oCalendar.setTime(zDate);
		oCalendar.add(Calendar.YEAR, anios);
		return oCalendar.getTime();
	}
	public static int getDayOfYears(Date zDate) {
		Calendar oCalendar = Calendar.getInstance();
		oCalendar.setTime(zDate);
		return oCalendar.get(Calendar.DAY_OF_YEAR);
	}

	public static int getDayOfMonth(Date zDate) throws Exception {
		String sDate = JDateTools.DateToString(zDate);
		return Integer.parseInt(sDate.substring(0, 2));
	}

	public static Date getFirstDayOfMonth(Date zDate) throws Exception {
		String sDate = JDateTools.DateToString(zDate, "ddMMyyyy");
		return JDateTools.StringToDate("01" + sDate.substring(2), "ddMMyyyy");
	}

	public static Date getFirstDayOfYear(Date zDate) throws Exception {
		String sDate = JDateTools.DateToString(zDate, "ddMMyyyy");
		return JDateTools.StringToDate("0101" + sDate.substring(4), "ddMMyyyy");
	}

	public static int getDaysBetween(Date begin, Date end) throws Exception {
		return (int) ((end.getTime() - begin.getTime()) / (1000 * 60 * 60 * 24));
	}
	public static int getMinutesBetween(Date begin, Date end) throws Exception {
		return (int) ((end.getTime() - begin.getTime()) / (1000 * 60));
	}
	public static int getYearsBetween(Date begin, Date end) throws Exception {
		if (begin==null) return 0;
		if (end==null) return 0;
		Calendar c1 = Calendar.getInstance();
		c1.setTime(begin);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(end);
    int diff = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
    if (c1.get(Calendar.MONTH) < c2.get(Calendar.MONTH) || 
        (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) && c1.get(Calendar.DATE) < c2.get(Calendar.DATE))) {
        diff--;
    }
    return diff;
   }
	
	public static long subDates(Date begin, Date end) throws Exception {
		return (end.getTime() - begin.getTime()) / (1000 * 60 * 60 * 24);
	}

	public static Date addDays(Date zDate, long zDays) {
		Date oDate = new Date(zDate.getTime() + (1000 * 60 * 60 * 24 * zDays));
		return oDate;
	}

	public static Date addHours(Date zDate, long hours) {
		Date oDate = new Date(zDate.getTime() + (1000 * 60 * 60 * hours));
		return oDate;
	}

	public static Date addMill(Date zDate, int value) throws Exception {
		return new JDateTools(new Date(zDate.getTime())).addMill(value);
	}
	
	public static Date addMinutes(Date zDate, long zMinutes) {
		Date oDate = new Date(zDate.getTime() + (1000 * 60 * zMinutes));
		return oDate;
	}

	/**
	 * @deprecated use addHours()
	 */
	@Deprecated
	public static Date AddHours(Date zDate, long zHours) {
		Date oDate = new Date(zDate.getTime() + (1000 * 60 * 60 * zHours));
		return oDate;
	}

	/**
	 * @deprecated use addMinutes()
	 */
	@Deprecated
	public static Date AddMinutes(Date zDate, long zMinutes) {
		Date oDate = new Date(zDate.getTime() + (1000 * 60 * zMinutes));
		return oDate;
	}

	/**
	 * @deprecated use addSeconds
	 */
	@Deprecated
	public static Date AddSeconds(Date zDate, long zSeconds) {
		Date oDate = new Date(zDate.getTime() + (1000 * zSeconds));
		return oDate;
	}

	// --------------------------------------------------------------------------
	// //
	// Convierte un string a una fecha
	// --------------------------------------------------------------------------
	// //
	public static String LongToString(long zLong, String zFormat) throws Exception {

		Date zVal = new Date(zLong);
		String sRta = null;
		SimpleDateFormat oSimple = new SimpleDateFormat();
		oSimple.applyPattern(zFormat);
		sRta = oSimple.format(zVal);
		return sRta;
	}

	// --------------------------------------------------------------------------
	// //
	// Combina un Tipo "Date sin Hora" con un String que representa la hora
	// Ej: zFecha = 12/nov/2001 00:00:00 zHora='134521'
	// zFormatHora='HHmmss', este parámtro indica el formato de la hora recibida
	//
	// Resultado = tipo Date = 12/nov/2001 13:45:21
	// --------------------------------------------------------------------------
	// //
	public static String DateTimeToString(Date zFecha, String zHora, String format) throws Exception {
		String sFecha = JDateTools.DateToString(zFecha, "dd-MM-yyyy");
		if (zHora!=null && !zHora.isEmpty()) sFecha = sFecha.concat(" "+zHora);
		Date date = JDateTools.StringToDateTime(sFecha);
		return JDateTools.DateToString(date, format);
	}

	public static Date DateTimeToDate(Date zFecha, String zHora) throws Exception {
		return DateTimeToDate(zFecha, zHora, "HH:mm:ss");
	}

	public static Date DateTimeToDate(Date zFecha, String zHora, String zFormatHora) throws Exception {
		String pFecha = JDateTools.DateToString(zFecha, "yyyyMMdd").concat(zHora);
		return JDateTools.StringToDate(pFecha, "yyyyMMdd".concat(zFormatHora));
	}

	public static String DateToActuateFormat(Date zVal) throws Exception {
		return DateToString(zVal, "yyyy-dd-MM");
	}
	public static String DateTimeToActuateFormat(Date zVal) throws Exception {
		return DateToString(zVal, "yyyy-dd-MM  HH:mm:ss");
	}
	public static String TimeToActuateFormat(Date zVal) throws Exception {
		return DateToString(zVal, "HH:mm");
	}
	public static Date getCurrentGMTDate(long gmtoffset) throws Exception {
		return StringToDate( getGMTDate(gmtoffset,"yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss" );
	}
	
	public static String getGMTDate(long gmtoffset, String fmt) throws Exception {
		Date d = new Date();
		java.text.SimpleDateFormat format = new SimpleDateFormat(fmt);
		java.util.Calendar cal = Calendar.getInstance(new SimpleTimeZone((int)(3600000 * gmtoffset), "GMT"));
		format.setCalendar(cal);
		return format.format(d);
	}

	public static boolean equalsDate(Date one, Date two) throws Exception {
		if (one==null || two==null) return false;
		JDateTools zOne = new JDateTools(one);
		JDateTools zTwo = new JDateTools(two);
		return zOne.equalsDate(zTwo);
	}

	public static Date getLastDayOfMonth(Date zDate) throws Exception {
		if (zDate==null) 
			return null;
		return new JDateTools(JDateTools.getFirstDayOfMonth(JDateTools.AddMonths(zDate, 1))).addDays(-1);
	}

	public static Date getNow() throws Exception {
		Calendar c = Calendar.getInstance();
		c.setTime(getCurrentGMTDate(BizUsuario.getUsr().getGMT()));
		return c.getTime();
	}
	public static Date getYesterday() throws Exception {
		Calendar c = Calendar.getInstance();
		c.setTime(getCurrentGMTDate(BizUsuario.getUsr().getGMT()));
		c.add(Calendar.DAY_OF_MONTH, -1);
		return c.getTime();
	}
	public static Date getBackDays(int day) throws Exception {
		Calendar c = Calendar.getInstance();
		c.setTime(getCurrentGMTDate(BizUsuario.getUsr().getGMT()));
		c.add(Calendar.DAY_OF_MONTH, -1*day);
		return c.getTime();
	}
	public static Date getLastDayOfYear(Date zDate) throws Exception {
		return new JDateTools(JDateTools.getFirstDayOfYear(JDateTools.AddYears(zDate, 1))).addDays(-1);
	}
	public static Date getLastDayPreviousMonth() throws Exception {
		Calendar c = Calendar.getInstance();
		c.setTime(getFirstDayOfMonth(new Date()));
		c.add(Calendar.DAY_OF_MONTH, -1);
		return c.getTime();
	}
	public JDateTools add(int zCalendarField, int zValue) throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.getValue());
		calendar.add(zCalendarField, zValue);
		this.setValue(calendar.getTime());
		return this;
	}

	public Date addDays(int zValue) throws Exception {
		this.add(Calendar.DAY_OF_MONTH, zValue);
		return this.getValue();
	}
	
	public Date addMill(int value) throws Exception {
		this.add(Calendar.MILLISECOND, value);
		return this.getValue();
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

	public int compareTo(JDateTools oAuxDate) throws Exception {
		String sDateAux = JDateTools.DateToString(oAuxDate.getValue());

		String sDateFormatted = sDateAux.substring(6) + sDateAux.substring(3, 5) + sDateAux.substring(0, 2);

		return compareTo(sDateFormatted);
	}

	public boolean before(JDateTools zDate) throws Exception {
		return this.compareTo(zDate) < 0;
	}

	public boolean after(JDateTools zDate) throws Exception {
		return this.compareTo(zDate) > 0;
	}

	public boolean equalsDate(JDateTools zDate) throws Exception {
		return this.compareTo(zDate) == 0;
	}

	protected boolean isShortFormat() {
		return true;
	}

	public static int getDayOfTheWeek() throws Exception {
		Calendar oDate = Calendar.getInstance();
		int iDay = oDate.get(Calendar.DAY_OF_WEEK);
		return iDay;
	}
	public static int getDayOfTheWeek(Date d) throws Exception {
		Calendar oDate = Calendar.getInstance();
		oDate.setTime(d);
		int iDay = oDate.get(Calendar.DAY_OF_WEEK);
		return iDay;
	}
  /**
   * Convert string to date
   * 
   * @param zStr input string
   * @param zFormat date format
   * @return the date object
   * @throws Exception
   */
	@Deprecated
  public static Date strToDate( String zStr, String zFormat )  throws Exception {
  	return StringToDate(zStr, zFormat);
  }
  
//  /**
//   * @param zDate input date
//   * @param zFormat date format
//   * @return string representing a date in the format received
//   * @throws Exception
//   */
  
	@Deprecated
  public static String dateToStr( Date zDate, String zFormat )  throws Exception {
  	return DateToString(zDate, zFormat);
  }
  public static String getMonthDescr(Date fecha) throws Exception {
  	if (fecha==null) return "";
  	String mes = JDateTools.DateToString(fecha, "MM");
  	if (mes.equals("01")) return "Enero";
  	if (mes.equals("02")) return "Febrero";
  	if (mes.equals("03")) return "Marzo";
  	if (mes.equals("04")) return "Abril";
  	if (mes.equals("05")) return "Mayo";
  	if (mes.equals("06")) return "Junio";
  	if (mes.equals("07")) return "Julio";
  	if (mes.equals("08")) return "Agosto";
  	if (mes.equals("09")) return "Septiembre";
  	if (mes.equals("10")) return "Octubre";
  	if (mes.equals("11")) return "Noviembre";
  	if (mes.equals("12")) return "Diciembre";
  	return "";
  }
  public static String formatFechaLarga(Date fecha) throws Exception {
  	if (fecha==null) return "";
  	String sfecha = JDateTools.DateToString(fecha, "dd");
  	sfecha+=" de ";
  	String mes = JDateTools.DateToString(fecha, "MM");
  	if (mes.equals("01")) sfecha+="Enero";
  	if (mes.equals("02")) sfecha+="Febrero";
  	if (mes.equals("03")) sfecha+="Marzo";
  	if (mes.equals("04")) sfecha+="Abril";
  	if (mes.equals("05")) sfecha+="Mayo";
  	if (mes.equals("06")) sfecha+="Junio";
  	if (mes.equals("07")) sfecha+="Julio";
  	if (mes.equals("08")) sfecha+="Agosto";
  	if (mes.equals("09")) sfecha+="Septiembre";
  	if (mes.equals("10")) sfecha+="Octubre";
  	if (mes.equals("11")) sfecha+="Noviembre";
  	if (mes.equals("12")) sfecha+="Diciembre";
  	sfecha+=" del "+JDateTools.DateToString(fecha, "yyyy");
  	return sfecha;
  }

  public static String formatFechaMediana(Date fecha) throws Exception {
  	if (fecha==null) return "";
  	String sfecha = JDateTools.DateToString(fecha, "dd");
  	sfecha+=" ";
  	String mes = JDateTools.DateToString(fecha, "MM");
  	if (mes.equals("01")) sfecha+="Ene";
  	if (mes.equals("02")) sfecha+="Feb";
  	if (mes.equals("03")) sfecha+="Mar";
  	if (mes.equals("04")) sfecha+="Abr";
  	if (mes.equals("05")) sfecha+="May";
  	if (mes.equals("06")) sfecha+="Jun";
  	if (mes.equals("07")) sfecha+="Jul";
  	if (mes.equals("08")) sfecha+="Ago";
  	if (mes.equals("09")) sfecha+="Sep";
  	if (mes.equals("10")) sfecha+="Oct";
  	if (mes.equals("11")) sfecha+="Nov";
  	if (mes.equals("12")) sfecha+="Dic";
  	sfecha+=" "+JDateTools.DateToString(fecha, "yyyy");
  	return sfecha;
  }
  
	public static int getMonthNumber(Date zDate) throws Exception {
		String sDate = JDateTools.DateToString(zDate);
		return Integer.parseInt(sDate.substring(3, 5));
	}
	
	public static int getYearNumber(Date zDate) throws Exception {
		String sDate = JDateTools.DateToString(zDate);
		return Integer.parseInt(sDate.substring(6));
	}

	public static long getHoursBetween(Date begin, Date end) throws Exception {
		return (long) ((end.getTime() - begin.getTime()) / (1000 * 60 * 60));
	}

	public static Date getStartDate(String range) throws Exception {
		if (range==null) return null;
		if (range.equals("")) return null;
		return JDateTools.StringToDate(range.substring(0, 10), "dd-MM-yyyy");
	}
	
	public static Date getEndDate(String range) throws Exception {
		if (range==null) return null;
		if (range.equals("")) return null;
		return JDateTools.StringToDate(range.substring(10), "dd-MM-yyyy");
	}

	public static Date CurrentDateTimeToDate() throws Exception {
		return StringToDateTime(CurrentDate(DEFAULT_DATE_FORMAT + " " + DEFAULT_HOUR_FORMAT));
	}

	public static String getDayOfWeekName(Date value) throws Exception {
		int dayofWeek = JDateTools.getDayOfTheWeek(value);	
		 switch (dayofWeek) {
		 case 1: return "Domingo";
		 case 2: return "Lunes";
		 case 3: return "Martes";
		 case 4: return "Miércoles";
		 case 5: return "Jueves";
		 case 6: return "Viernes";
		 case 7: return "Sábado";
		 default: return "Desconocido";
		 }		
	}

}
