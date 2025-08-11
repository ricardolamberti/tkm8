package pss.tourism.interfaceGDS.galileo.record;

import pss.core.tools.collections.JMap;

public class RecordA15   extends GalileoRecord {
	
	public static final String A15SEC = "A15SEC";
	public static final String A15SEG = "A15SEG";
	public static final String A15RMK = "A15RMK";
	public static final String A15CO1 = "A15CO1";
	public static final String A15CO2 = "A15CO2";
	

	public RecordA15() {
		ID = A15RECORD;
	}

	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {

		int i = 0;

		if (!line.startsWith(ID))
			return line;

		addFieldValue(A15SEC , line.substring(i,i+=3));
		addFieldValue(A15SEG , line.substring(i,i+=2));
		addFieldValue(A15RMK , line.substring(i));

		return line;
	}
}
