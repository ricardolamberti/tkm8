package pss.tourism.interfaceGDS.amadeus.record;

import java.util.Date;

import pss.core.tools.JDateTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;


public class DRecord extends AmadeusRecord {
	
		public static final String CREATIONDATE="CREATIONDATE";
		public static final String UPDATEDATE="UPDATEDATE";
		public static final String CREATIONAIR="CREATIONAIR";
		public static final String CREATIONYEAR="CREATIONYEAR";
		public static final String CREATIONMONTH="CREATIONMONTH";

		public DRecord() {
			ID=D;
		}
		
		public String doProcess(JMap<String,Object>mRecords, String line) throws Exception {
			int i=0;
			if ( line.substring(i+=2).length() < 6 ) return line;
			
			JStringTokenizer tok = JCollectionFactory.createStringTokenizer(line.substring(i),AmadeusRecord.FIELD_SEPARATOR);
			
			String create = tok.nextToken(); //spare1
			String update = tok.nextToken(); //spare2
			String createair = tok.nextToken();
			
			String year = JDateTools.DateToString(new Date(),"yyyy").substring(0,2);
			//if (y!=0) year=""+y;
			addFieldValue(CREATIONDATE, year+create );
			addFieldValue(UPDATEDATE, year+update );
			addFieldValue(CREATIONAIR, year+createair );
			addFieldValue(CREATIONYEAR, year+createair.substring(0,2) );
			addFieldValue(CREATIONMONTH, createair.substring(2,4) );
			
			return line.substring(i);
		}

		public Date getCreationDate() throws Exception {
			return JDateTools.StringToDate(getFieldValue(CREATIONDATE),"yyyyMMdd");
		}
		
		public Date getUpdateDate() throws Exception {
			return JDateTools.StringToDate(getFieldValue(UPDATEDATE),"yyyyMMdd");
		}

		public Date getCreationDateAIR() throws Exception {
			return JDateTools.StringToDate(getFieldValue(CREATIONAIR),"yyyyMMdd");
		}
		
		public long getYear() throws Exception {
			return Long.parseLong( getFieldValue(CREATIONYEAR) );
		}
		
		public long getMonth() throws Exception {
			return Long.parseLong(  getFieldValue(CREATIONMONTH) );
		}
		
		


}
