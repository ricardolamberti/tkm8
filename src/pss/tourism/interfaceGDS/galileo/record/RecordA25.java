package pss.tourism.interfaceGDS.galileo.record;

import pss.core.tools.collections.JMap;

public class RecordA25   extends GalileoRecord {
	
	public static final String A25SEC = "A25SEC";
	

	public RecordA25() {
		ID = A25RECORD;
	}

	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {

		int i = 0;

		if (!line.startsWith(ID))
			return line;

		addFieldValue(A25SEC , line.substring(i,i+=3));

		return line;
	}
}
