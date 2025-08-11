package pss.tourism.interfaceGDS.galileo.record;

import pss.core.tools.collections.JMap;

public class RecordA13   extends GalileoRecord {
	
	public static final String A13SEC = "A13SEC";
	public static final String A13ADT = "A13ADT";
	public static final String A13DTA = "A13DTA";
	public static final String A13CO1 = "A13CO1";
	public static final String A13CO2 = "A13CO2";
	

	public RecordA13() {
		ID = A13RECORD;
	}

	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {

		int i = 0;

		if (!line.startsWith(ID))
			return line;

		addFieldValue(A13SEC , line.substring(i,i+=3));
		addFieldValue(A13ADT , line.substring(i,i+=2));
		addFieldValue(A13DTA , line.substring(i));

		return line;
	}
}
