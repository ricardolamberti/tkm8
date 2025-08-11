package pss.tourism.interfaceGDS.galileo.record;

import pss.core.tools.collections.JMap;

public class RecordA06 extends GalileoRecord {
	
	public static final String A06SEC = "A06SEC";
	public static final String A06SEG = "A06SEG";
	public static final String A06SEN = "A06SEN";
	public static final String A06SMK = "A06SMK";
	public static final String A06C01 = "A06C01";
	public static final String A06COG = "A06COG";
	public static final String A06BLK = "A06BLK";
	public static final String A06C02 = "A06C02";
	public static final String A06C03 = "A06C03";

                                             
	

	public RecordA06() {
		ID = A06RECORD;
	}

	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {

		int i = 0;

		if (!line.startsWith(A06RECORD))
			return line;

		addFieldValue(A06SEC , line.substring(i,i+=3));
		addFieldValue(A06SEG , line.substring(i,i+=2));
		addFieldValue(A06SEN , line.substring(i,i+=3));
		addFieldValue(A06SMK , line.substring(i,i+=1));
		line = this.getInput().readLine();
		if (line == null) return line;
		if (line.startsWith("COG")) {
			i=0;		
			addFieldValue(A06COG , line.substring(i,i+=4));
			addFieldValue(A06SEN , line.substring(i,i+=3));
			addFieldValue(A06BLK , line.substring(i,i+=2));
		}			


		return line;
	}
}
