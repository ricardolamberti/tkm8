package pss.tourism.interfaceGDS.galileo.record;

import pss.core.tools.collections.JMap;

public class RecordA14   extends GalileoRecord {
	
	public static final String A14SEC = "A14SEC";
	public static final String A14RMK = "A14RMK";
	public static final String A14CO1 = "A14CO1";
	public static final String A14CO2 = "A14CO2";
	public static final String A14SA  = "A14SA";
	

	public RecordA14() {
		ID = A14RECORD;
	}

	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {

		int i = 0;

		if (!line.startsWith(ID))
			return line;

		addFieldValue(A14SEC , line.substring(i,i+=3));
		addFieldValue(A14RMK , line.substring(i));
		if ( line.substring(i).startsWith("SA-")) {
			addFieldValue(A14SA , line.substring(i+3));
				
		}
		return line;
	}
}
