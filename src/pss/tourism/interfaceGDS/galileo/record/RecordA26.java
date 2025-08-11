package pss.tourism.interfaceGDS.galileo.record;

import pss.core.tools.collections.JMap;

public class RecordA26   extends GalileoRecord {
	
	public static final String A26SEC = "A26SEC";
	public static final String A26DAT = "A26DAT";


	public RecordA26() {
		ID = A26RECORD;
	}

	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {

		int i = 0;

		if (!line.startsWith(ID))
			return line;

		addFieldValue(A26SEC , line.substring(i,i+=3));
		addFieldValue(A26DAT , line.substring(i));

		return line;
	}
}
