/**
 * 
 */
package pss.tourism.interfaceGDS;

import java.util.Date;

import pss.core.tools.JDateTools;
import pss.core.tools.JTools;

/**
 * @author sgalli
 *
 */
public class Tools {

	public static String convertMonthStringToNumber(String date) {
		if (date.equalsIgnoreCase("JAN") ) return "01";
		if (date.equalsIgnoreCase("FEB") ) return "02";
		if (date.equalsIgnoreCase("MAR") ) return "03";
		if (date.equalsIgnoreCase("APR") ) return "04";
		if (date.equalsIgnoreCase("MAY") ) return "05";
		if (date.equalsIgnoreCase("JUN") ) return "06";
		if (date.equalsIgnoreCase("JUL") ) return "07";
		if (date.equalsIgnoreCase("AUG") ) return "08";
		if (date.equalsIgnoreCase("SEP") ) return "09";
		if (date.equalsIgnoreCase("OCT") ) return "10";
		if (date.equalsIgnoreCase("NOV") ) return "11";
		if (date.equalsIgnoreCase("DEC") ) return "12";
		return "01";
	}
	public static String convertDateMonthStringToNumber(String date,long year) {
		date = date.substring(0,2)+convertMonthStringToNumber(date.substring(2,5))+year;
		return date;
	}
	public static String convertDateMonthStringToNumberShort(String date) {
		date = date.substring(0,2)+convertMonthStringToNumber(date.substring(2,5))+"20"+date.substring(5);
		return date;
	}
	public static String convertDateMonthStringToNumber(String date) {
		date = date.substring(0,2)+convertMonthStringToNumber(date.substring(2,5))+date.substring(5);
		return date;
	}
	
	public static String convertWithOriginalDateToStringYYYYMMDD(String date, Date getyear) throws Exception {
		
		long curryear = Long.parseLong( JDateTools.DateToString( getyear, "yyyy" ) ) ;
		String month = Tools.convertMonthStringToNumber( date.substring(2) );
		String day = date.substring(0,2);
		String currmonth = JDateTools.DateToString( getyear, "MM" );
		String currday = JDateTools.DateToString( getyear, "dd" );
		
		if ( Integer.parseInt(month+day) < Integer.parseInt(currmonth+currday)   ) curryear ++;

		return curryear+month+day;
	}

	
	public static String convertSpecialStringToStringYYYYMMDD(String date) throws Exception {
		return  convertSpecialStringToStringYYYYMMDD(date, true,0,0);
	}
	
	public static String convertSpecialStringToStringYYYYMMDD(String date, long year) throws Exception {
		return  convertSpecialStringToStringYYYYMMDD(date, true,year,0);
	}

	public static String convertSpecialStringToStringYYYYMMDD(String date, boolean changeyear, long year) throws Exception {
		return  convertSpecialStringToStringYYYYMMDD(date, changeyear,year,0);
	}

	
	public static String convertSpecialStringToStringYYYYMMDD(String date, boolean changeyear, long year, long month) throws Exception {
		String filemonth = Tools.convertMonthStringToNumber( date.substring(2) );
		
		int currmonth = Integer.parseInt( JDateTools.DateToString(new Date(),"MM") );
		if (month!=0)
			currmonth = (int)month;
		
		int curryear  = Integer.parseInt( JDateTools.DateToString(new Date(),"yyyy") );
		
		if (year!=0) return year+filemonth+date.substring(0,2);
		
		if ( changeyear )
		  if ( Integer.parseInt(filemonth) < currmonth   ) curryear ++;
		return  curryear+filemonth+date.substring(0,2);
			
//		return  curryear+filemonth+date.substring(0,2);
	}
	
	public static String convertAmadeusStringToStringYYYYMMDD(String date, boolean changeyear, long year, long month) throws Exception {
		String filemonth = Tools.convertMonthStringToNumber( date.substring(2) );
		
		int currmonth = Integer.parseInt( JDateTools.DateToString(new Date(),"MM") );
		if (month!=0)
			currmonth = (int)month;
		
		int curryear  = year!=0?(int)year:Integer.parseInt( JDateTools.DateToString(new Date(),"yyyy") ); //RJL esto de los a~nos esta mal cuando reproceso y paso el a~no todo se pasa al a~no actual
		
		//if (year!=0) return year+filemonth+date.substring(0,2);
		
		if ( year!=0&&changeyear )
		  if ( Integer.parseInt(filemonth) < currmonth   ) curryear ++;
		return  curryear+filemonth+date.substring(0,2);
			
//		return  curryear+filemonth+date.substring(0,2);
	}

	
	public static Date convertSpecialStringToDate(String date,long year) throws Exception {
		return convertSpecialStringToDate(date, true, year);
	}
	
	public static Date convertSpecialStringToDate(String date, boolean changeyear, long year) throws Exception {
		return JDateTools.StringToDate(convertSpecialStringToStringYYYYMMDD(date, changeyear, year), "yyyyMMdd");
	}

	public static String getOnlyNumbers(String number) throws Exception {
		if (number==null) return null;
		StringBuffer n=new StringBuffer("");
		number=number.trim();
		for ( int i = 0 ; i < number.length() ; i ++ ) {
			char c = number.charAt(i);
			if ( c == ' ' && n.length()>0) break;
			if ( JTools.isNumber(c) || c  == '.' || (i==0 && c=='-') )
				n.append(c);
		}
		String ret = n.toString();
		if ( ret.isEmpty() ) return "0";
		return ret;
	}
	
	public static String formatTime(String time) {
		if ( time.length() == 4 ) {
			return time.substring(0,2)+":"+time.substring(2);
		}
		if ( time.length() == 5 ) {
			if ( time.charAt(4) == 'P' ) {
				int hour = Integer.parseInt(time.substring(0,2))+12;
				if (hour==24) return "00"+":"+time.substring(2,4);
				return hour+":"+time.substring(2,4);
			}
			return time.substring(0,2)+":"+time.substring(2,4);
		}  
		return "";
	}
	public static boolean isOnlyLetters(String zStr, boolean bException) throws Exception {
		int i;
		boolean bResult=true;
		byte[] Byte=zStr.getBytes();
		int iLonB=zStr.length();

		try {
			for(i=0; i<iLonB; i++) {
				if (Byte[i]<128) {

					if (!((Byte[i]>=65&&Byte[i]<=90)||(Byte[i]>=97&&Byte[i]<=122)||(Byte[i]==32))) {

						bResult=false;
						break;
					}
				}
			}
			return bResult;
		} catch (Exception E) {
			return bResult;
		}
	}
	public static boolean isOnlyNumbers(String zStr, boolean bException) throws Exception {
		int i;
		boolean bResult = true;
		byte[] Byte = zStr.getBytes();
		int iLonB = zStr.length();

		try {
			for (i = 0; i < iLonB; i++) {
				if (Byte[i] < 128) {
					if (!(Byte[i] >= 48 && Byte[i] <= 57)) { // 48='0', 57='9'
						bResult = false;
						break;
					}
				}
			}
			return bResult;
		} catch (Exception E) {
			return bResult;
		}
	}


}
