package pss.tourism.interfaceGDS.galileo.record;

import pss.core.tools.collections.JMap;

public class RecordA12   extends GalileoRecord {
	
	public static final String A12SEC = "A12SEC";
	public static final String A12CTY = "A12CTY";
	public static final String A12LOC = "A12LOC";
	public static final String A12PHN = "A12PHN";
	public static final String A12CO1 = "A12CO1";
	public static final String A12CO2 = "A12CO2";
	

	public RecordA12() {
		ID = A12RECORD;
	}

	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {

		int i = 0;

		if (!line.startsWith(ID))
			return line;

		addFieldValue(A12SEC , line.substring(i,i+=3));
		addFieldValue(A12CTY , line.substring(i,i+=3));
		addFieldValue(A12LOC , line.substring(i,i+=2));
		addFieldValue(A12PHN , line.substring(i));

		return line;
	}
}
