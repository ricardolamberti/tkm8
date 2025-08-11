package pss.tourism.interfaceGDS.galileo.record;

import pss.core.tools.collections.JMap;

public class RecordA20   extends GalileoRecord {
	
	public static final String A20SEC = "A20SEC";
	public static final String A20SEG = "A20SEG";
	public static final String A20PAX = "A20PAX";
	public static final String A20IND = "A20IND";
	public static final String A20CDE = "A20CDE";
	public static final String A20STS = "A20STS";
	public static final String A20REQ = "A20REQ";
	public static final String A20AIR = "A20AIR";
	public static final String A20OCC = "A20OCC";
	public static final String A20DCC = "A20DCC";
	public static final String A20FLT = "A20FLT";
	public static final String A20DAT = "A20DAT";
	public static final String A20NAM = "A20NAM";
	public static final String A20C01 = "A20C01";
	public static final String A20TXI = "A20TXI";
	public static final String A20TXT = "A20TXT";
	public static final String A20C02 = "A20C02";
	public static final String A20C03 = "A20C03";


	public RecordA20() {
		ID = A20RECORD;
	}

	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {

		int i = 0;

		if (!line.startsWith(ID))
			return line;

		addFieldValue(A20SEC , line.substring(i,i+=3));
		addFieldValue(A20SEG , line.substring(i,i+=2));
		addFieldValue(A20PAX , line.substring(i,i+=2));
		addFieldValue(A20IND , line.substring(i,i+=3));
		addFieldValue(A20CDE , line.substring(i,i+=4));
		addFieldValue(A20STS , line.substring(i,i+=2));
		if (line.length()<=16) return line;
		addFieldValue(A20REQ , line.substring(i,i+=2));
		addFieldValue(A20AIR , line.substring(i,i+=2));
		addFieldValue(A20OCC , line.substring(i,i+=3));
		addFieldValue(A20DCC , line.substring(i,i+=3));
		addFieldValue(A20FLT , line.substring(i,i+=4));
		addFieldValue(A20DAT , line.substring(i,i+=5));
		addFieldValue(A20NAM , line.substring(i));
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		addFieldValue(A20TXI , line.substring(i,i+=3));
		addFieldValue(A20TXT , line.substring(i));

		return line;
	}
}
