package pss.tourism.interfaceGDS.galileo.record;

import pss.core.tools.collections.JMap;

public class RecordA28   extends GalileoRecord {
	
	public static final String A28SEC = "A28SEC";
	public static final String A28DAT = "A28DAT";
	

	public RecordA28() {
		ID = A28RECORD;
	}

	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {

		int i = 0;

		if (!line.startsWith(ID))
			return line;

		addFieldValue(A28SEC , line.substring(i,i+=3));
		addFieldValue(A28DAT , line.substring(i));

		return line;
	}
}
