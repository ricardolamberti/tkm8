package pss.tourism.interfaceGDS.galileo.record;

import pss.core.tools.collections.JMap;

public class RecordA21   extends GalileoRecord {
	
	public static final String A21SEC = "A21SEC";
	public static final String A21NFC = "A21NFC";
	public static final String A21NRT = "A21NRT";
	public static final String A21NAI = "A21NAI";
	public static final String A21NVC = "A21NVC";
	public static final String A21ITC = "A21ITC";
	public static final String A21NFR = "A21NFR";
	public static final String A21C01 = "A21C01";
	public static final String A21C02 = "A21C02";
	

	public RecordA21() {
		ID = A21RECORD;
	}

	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {

		int i = 0;

		if (!line.startsWith(ID))
			return line;

		addFieldValue(A21SEC , line.substring(i,i+=3));
		addFieldValue(A21NFC , line.substring(i,i+=3));
		addFieldValue(A21NRT , line.substring(i,i+=12));
		addFieldValue(A21NAI , line.substring(i,i+=20));
		addFieldValue(A21NVC , line.substring(i,i+=20));
		addFieldValue(A21ITC , line.substring(i,i+=12));
		addFieldValue(A21NFR , line.substring(i,i+=3));

		return line;
	}
}
