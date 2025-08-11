package pss.tourism.interfaceGDS.galileo.record;

import pss.core.tools.collections.JMap;

public class RecordA04  extends GalileoRecord {
	
	public static final String A04SEC = "A04SEC";
	public static final String A04ITN = "A04ITN";
	public static final String A04CDE = "A04CDE";
	public static final String A04NUM = "A04NUM";
	public static final String A04NME = "A04NME";
	public static final String A04FLT = "A04FLT";
	public static final String A04CLS = "A04CLS";
	public static final String A04STS = "A04STS";
	public static final String A04DTE = "A04DTE";
	public static final String A04TME = "A04TME";
	public static final String A04ARV = "A04ARV";
	public static final String A04IND = "A04IND";
	public static final String A04OCI = "A04OCI";
	public static final String A04OCC = "A04OCC";
	public static final String A04OCN = "A04OCN";
	public static final String A04DCI = "A04DCI";
	public static final String A04DCC = "A04DCC";
	public static final String A04DCN = "A04DCN";
	public static final String A04DOM = "A04DOM";
	public static final String A04SET = "A04SET";
	public static final String A04SVC = "A04SVC";
	public static final String A04STP = "A04STP";
	public static final String A04STO = "A04STO";
	public static final String A04BAG = "A04BAG";
	public static final String A04AIR = "A04AIR";
	public static final String A04DTR = "A04DTR";
	public static final String A04MIL = "A04MIL";
	public static final String A04FCI = "A04FCI";
	public static final String A04SIC = "A04SIC";
	public static final String A04COG = "A04COG";
	public static final String A04CGC = "A04CGC";
	public static final String A04CGN = "A04CGN";
	public static final String A04CGD = "A04CGD";
	public static final String A04CGT = "A04CGT";
	public static final String A04GCR = "A04GCR";
	public static final String A04GRR = "A04GRR";
	public static final String A04AFC = "A04AFC";
	public static final String A04ACC = "A04ACC";
	public static final String A04FFI = "A04FFI";
	public static final String A04FFM = "A04FFM";
	public static final String A04TKI = "A04TKI";
	public static final String A04TKT = "A04TKT";
	public static final String A04JTI = "A04JTI";
	public static final String A04JTM = "A04JTM";
	public static final String A04ANL = "A04ANL";
	public static final String A04NML = "A04NML";
	public static final String A04ACL = "A04ACL";
	public static final String A04ACN = "A04ACN";
	public static final String A04C01 = "A04C01";
	public static final String A04C02 = "A04C02";
	public static final String A04DDL = "A04DDL";
	public static final String A04DDD = "A04DDD";
	                                         
	

	public RecordA04() {
		ID = A04RECORD;
	}
	
	
	public boolean isDomestic() {
		return getFieldValue(A04DOM).equals("D");
	}

	
	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {

		int i = 0;

		if (!line.startsWith(A04RECORD))
			return line;

		addFieldValue(A04SEC , line.substring(i,i+=3));
		addFieldValue(A04ITN , line.substring(i,i+=2));
		addFieldValue(A04CDE , line.substring(i,i+=2));
		addFieldValue(A04NUM , line.substring(i,i+=3));
		addFieldValue(A04NME , line.substring(i,i+=12));
		addFieldValue(A04FLT , line.substring(i,i+=4));
		addFieldValue(A04CLS , line.substring(i,i+=2));
		addFieldValue(A04STS , line.substring(i,i+=2));
		addFieldValue(A04DTE , line.substring(i,i+=5));
		addFieldValue(A04TME , line.substring(i,i+=5));
		addFieldValue(A04ARV , line.substring(i,i+=5));
		addFieldValue(A04IND , line.substring(i,i+=1));
		addFieldValue(A04OCI , line.substring(i,i+16));
		addFieldValue(A04OCC , line.substring(i,i+=3));
		addFieldValue(A04OCN , line.substring(i,i+=13));
		addFieldValue(A04DCI , line.substring(i,i+16));
		addFieldValue(A04DCC , line.substring(i,i+=3));
		addFieldValue(A04DCN , line.substring(i,i+=13));
		addFieldValue(A04DOM , line.substring(i,i+=1));
		addFieldValue(A04SET , line.substring(i,i+=1));
		addFieldValue(A04SVC , line.substring(i,i+=4));
		addFieldValue(A04STP , line.substring(i,i+=1));
		addFieldValue(A04STO , line.substring(i,i+=1));
		addFieldValue(A04BAG , line.substring(i,i+=3));
		addFieldValue(A04AIR , line.substring(i,i+=4));
		addFieldValue(A04DTR , line.substring(i,i+=3));
		addFieldValue(A04MIL , line.substring(i,i+=5));
		addFieldValue(A04FCI , line.substring(i,i+=1));
		addFieldValue(A04SIC , line.substring(i,i+=1));
		
		String extra = line.substring(i);
		int pos = 0;
		pos = extra.indexOf("COG:");
		if (pos!=-1) {
			int j = pos;
			addFieldValue(A04COG   , extra.substring(j,j+=4));
			addFieldValue(A04CGC   , extra.substring(j,j+=3));
			addFieldValue(A04CGN   , extra.substring(j,j+=13));
			addFieldValue(A04CGD   , extra.substring(j,j+=1));
			addFieldValue(A04CGT   , extra.substring(j,j+=5));
		}
		pos = extra.indexOf("GCR:");
		if (pos!=-1) {
			int j = pos;
			addFieldValue(A04GCR   , extra.substring(j,j+=4));
			addFieldValue(A04GRR   , extra.substring(j,j+=6));
		}
		pos = extra.indexOf("AC:");
		if (pos!=-1) {
			int j = pos;
			addFieldValue(A04AFC   , extra.substring(j,j+=3));
			addFieldValue(A04ACC   , extra.substring(j,j+=12));
		}
		pos = extra.indexOf("FF:");
		if (pos!=-1) {
			int j = pos;
			addFieldValue(A04FFI   , extra.substring(j,j+=3));
			addFieldValue(A04FFM   , extra.substring(j,j+=5));
		}
		pos = extra.indexOf("TK:");
		if (pos!=-1) {
			int j = pos;
			addFieldValue(A04TKI   , extra.substring(j,j+=3));
			addFieldValue(A04TKT   , extra.substring(j,j+=1));
		}
		pos = extra.indexOf("JT:");
		if (pos!=-1) {
			int j = pos;
			addFieldValue(A04JTI   , extra.substring(j,j+=3));
			addFieldValue(A04JTM   , extra.substring(j,j+=5));
		}
		pos = extra.indexOf("ANL:");
		if (pos!=-1) {
			int j = pos;
			addFieldValue(A04ANL   , extra.substring(j,j+=4));
			addFieldValue(A04NML   , extra.substring(j,j+=24));
		}
		pos = extra.indexOf("ACL:");
		if (pos!=-1) {
			int j = pos;
			addFieldValue(A04ACL   , extra.substring(j,j+=4));
			addFieldValue(A04ACN   , extra.substring(j,j+=24));
		}
		pos = extra.indexOf("DDL:");
		if (pos!=-1) {
			int j = pos;
			addFieldValue(A04DDL   , extra.substring(j,j+=4));
			addFieldValue(A04DDD  , extra.substring(j,j+=7));
		}
		return line;
	}
}
