package pss.tourism.interfaceGDS.galileo.record;

import pss.core.tools.collections.JMap;

public class RecordA00 extends GalileoRecord {
	
	public static final String A01SEC = "A01SEC";
	public static final String A01CPI = "A01CPI";
	public static final String A01C01 = "A01C01";
	public static final String A01C02 = "A01C02";
	

	public RecordA00() {
		ID = A00RECORD;
	}

	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {

		int i = 0;

		if (!line.startsWith(A00RECORD))
			return line;

		line = this.getInput().readLine();
		if (line == null) return line;


		
		return line;
	}


}
