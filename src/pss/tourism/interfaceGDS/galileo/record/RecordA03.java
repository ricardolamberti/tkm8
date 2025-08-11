package pss.tourism.interfaceGDS.galileo.record;

import pss.core.tools.collections.JMap;

public class RecordA03  extends GalileoRecord {
	
	public static final String A03SEC = "A03SEC";
	public static final String A03FFP = "A03FFP";
	public static final String A03FCC = "A03FCC";
	public static final String A03FSP = "A03FSP";
	public static final String A03FFN = "A03FFN";
	public static final String A03CAI = "A03CAI";
	public static final String A03CAA = "A03CAA";
	public static final String A03C01 = "A03C01";
	public static final String A03C02 = "A03C02";
	

	public RecordA03() {
		ID = A03RECORD;
	}

	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {

		int i = 0;

		if (!line.startsWith(A03RECORD))
			return line;

		addFieldValue(A03SEC , line.substring(i,i+=3));
		addFieldValue(A03FFP , line.substring(i,i+=21));
		addFieldValue(A03FCC , line.substring(i,i+=2));
		addFieldValue(A03FSP , line.substring(i,i+=1));
		addFieldValue(A03FFN , line.substring(i,i+=20));
		addFieldValue(A03CAI , line.substring(i,i+=1));
		addFieldValue(A03CAA , line.substring(i,i+=30));
//		line = this.getInput().readLine();
//		if (line == null) return line;
//		if (line.startsWith("A0")) return line; 
//		i=0;		
//		line = this.getInput().readLine();
//		if (line == null) return line;
//		i=0;

		return line;
	}


}
