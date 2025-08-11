package pss.tourism.interfaceGDS.galileo.record;

import pss.core.tools.collections.JMap;

public class RecordA05 extends GalileoRecord {
	
	public static final String A05SEC = "A05SEC";
	public static final String A05ITN = "A05ITN";
	public static final String A05CDE = "A05CDE";
	public static final String A05NUM = "A05NUM";
	public static final String A05NME = "A05NME";
	public static final String A05FLT = "A05FLT";
	public static final String A05CLS = "A05CLS";
	public static final String A05STS = "A05STS";
	public static final String A05DTE = "A05DTE";
	public static final String A05TME = "A05TME";
	public static final String A05ARV = "A05ARV";
	public static final String A05IND = "A05IND";
	public static final String A05OCI = "A05OCI";
	public static final String A05OCC = "A05OCC";
	public static final String A05OCN = "A05OCN";
	public static final String A05DCI = "A05DCI";
	public static final String A05DCC = "A05DCC";
	public static final String A05DCN = "A05DCN";
	public static final String A05SVC = "A05SVC";
	public static final String A05STP = "A05STP";
	public static final String A05C01 = "A05C01";
	public static final String A05C02 = "A05C02";

                                             
	

	public RecordA05() {
		ID = A05RECORD;
	}

	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {

		int i = 0;

		if (!line.startsWith(A05RECORD))
			return line;

		addFieldValue(A05SEC , line.substring(i,i+=3));
		addFieldValue(A05ITN , line.substring(i,i+=2));
		addFieldValue(A05CDE , line.substring(i,i+=2));
		addFieldValue(A05NUM , line.substring(i,i+=3));
		addFieldValue(A05NME , line.substring(i,i+=12));
		addFieldValue(A05FLT , line.substring(i,i+=4));
		addFieldValue(A05CLS , line.substring(i,i+=2));
		addFieldValue(A05STS , line.substring(i,i+=2));
		addFieldValue(A05DTE , line.substring(i,i+=5));
		addFieldValue(A05TME , line.substring(i,i+=5));
		addFieldValue(A05ARV , line.substring(i,i+=5));
		addFieldValue(A05IND , line.substring(i,i+=1));
		addFieldValue(A05OCI , line.substring(i,i+=16));
		addFieldValue(A05OCC , line.substring(i,i+=3));
		addFieldValue(A05OCN , line.substring(i,i+=13));
		if (line.length()<i+16) return line;
		addFieldValue(A05DCI , line.substring(i,i+=16));
		if (line.length()<i+13) return line;
		addFieldValue(A05DCC , line.substring(i,i+=3));
		if (line.length()<i+13) return line;
		addFieldValue(A05DCN , line.substring(i,i+=13));
		if (line.length()<i+4) return line;
		addFieldValue(A05SVC , line.substring(i,i+=4));
		if (line.length()<i+1) return line;
		addFieldValue(A05STP , line.substring(i,i+=1));


		return line;
	}
}
