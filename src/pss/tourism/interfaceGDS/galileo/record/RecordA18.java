package pss.tourism.interfaceGDS.galileo.record;

import pss.core.tools.collections.JMap;

public class RecordA18   extends GalileoRecord {
	
	public static final String A18SEC = "A18SEC";
	public static final String A18ETV = "A18ETV";
	public static final String A18ETA = "A18ETA";
	public static final String A18C01 = "A18C01";
	

	public RecordA18() {
		ID = A18RECORD;
	}

	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {

		int i = 0;

		if (!line.startsWith(ID))
			return line;

		addFieldValue(A18SEC , line.substring(i,i+=3));
		addFieldValue(A18ETV , line.substring(i,i+=3));
		addFieldValue(A18ETA , line.substring(i,i+=8));

		return line;
	}
}
