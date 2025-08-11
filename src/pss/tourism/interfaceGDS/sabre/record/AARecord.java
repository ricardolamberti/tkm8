package pss.tourism.interfaceGDS.sabre.record;

import java.util.Date;

import pss.core.tools.JDateTools;
import pss.core.tools.collections.JMap;
import pss.tourism.interfaceGDS.Tools;

public class AARecord extends SabreRecord {
	
		public static final String SYSTEMDATE="systemdate";
		public static final String SYSTEMTIME="systemtime";

		public AARecord() {
			ID="AA";
		}
		
		// ==========================================================================
		// AA Record - Date & Time
		// ==========================================================================
		public String doProcess(JMap<String,Object>mRecords, String line) throws Exception {
			if (line.substring(2, 7).trim().length() != 0) {
				addFieldValue(SYSTEMDATE, line.substring(2, 4) + Tools.convertMonthStringToNumber(line.substring(4, 7)) + JDateTools.DateToString(new Date(), "yyyy") );
			}
			addFieldValue(SYSTEMTIME, line.substring(7, 11));
			return line.substring(11);
		}
		
		public Date getCreationDate(long year) throws Exception {
			String creation = getFieldValue(SYSTEMDATE);
			if (getCreationTime().equals(""))
				return JDateTools.StringToDate(year==0?creation:creation.substring(0,4)+year, "ddMMyyyy");
			return JDateTools.StringToDate((year==0?creation:creation.substring(0,4)+year)+getCreationTime(), "ddMMyyyyHHmm");
		}
		
		public String getCreationTime() {
			return getFieldValue(SYSTEMTIME);
		}


}
