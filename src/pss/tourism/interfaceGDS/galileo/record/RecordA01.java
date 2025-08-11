package pss.tourism.interfaceGDS.galileo.record;

import pss.core.tools.collections.JMap;

public class RecordA01 extends GalileoRecord {
	
	public static final String A01SEC = "A01SEC";
	public static final String A01CPI = "A01CPI";
	public static final String A01C01 = "A01C01";
	public static final String A01C02 = "A01C02";
	

	public RecordA01() {
		ID = A01RECORD;
	}

	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {

		int i = 0;

		if (!line.startsWith(A01RECORD))
			return line;

		addFieldValue(A01SEC , line.substring(i,i+=3));
		addFieldValue(A01CPI , line.substring(i,i+=33));
		if (line.length()>=i+1) addFieldValue(A01C01 , line.substring(i,i+=1));
		if (line.length()>=i+1) addFieldValue(A01C02 , line.substring(i,i+=1));

		
		return line;
	}


}
