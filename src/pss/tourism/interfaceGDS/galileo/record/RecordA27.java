package pss.tourism.interfaceGDS.galileo.record;

import pss.core.tools.collections.JMap;

public class RecordA27   extends GalileoRecord {
	
	public static final String A27SEC = "A27SEC";
	public static final String A27DAT = "A27DAT";
	

	public RecordA27() {
		ID = A27RECORD;
	}

	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {

		int i = 0;

		if (!line.startsWith(ID))
			return line;

		addFieldValue(A27SEC , line.substring(i,i+=3));
		addFieldValue(A27DAT , line.substring(i));

		return line;
	}
}
