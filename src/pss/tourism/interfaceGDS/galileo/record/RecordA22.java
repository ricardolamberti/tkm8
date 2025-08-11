package pss.tourism.interfaceGDS.galileo.record;

import pss.core.tools.collections.JMap;

public class RecordA22   extends GalileoRecord {
	
	public static final String A22SEC = "A22SEC";
	public static final String A22SEG = "A22SEG";
	public static final String A22PAS = "A22PAS";
	public static final String A22STT = "A22STT";
	public static final String A22SEN = "A22SEN";
	public static final String A22CHX = "A22CHX";
	public static final String A22C01 = "A22C01";
	public static final String A22C02 = "A22C02";
	

	public RecordA22() {
		ID = A22RECORD;
	}

	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {

		int i = 0;

		if (!line.startsWith(ID))
			return line;

		addFieldValue(A22SEC , line.substring(i,i+=3));
		addFieldValue(A22SEG , line.substring(i,i+=2));
		addFieldValue(A22PAS , line.substring(i,i+=2));
		addFieldValue(A22STT , line.substring(i,i+=1));
		addFieldValue(A22SEN , line.substring(i,i+=6));
		addFieldValue(A22CHX , line.substring(i,i+=20));

		return line;
	}
}
