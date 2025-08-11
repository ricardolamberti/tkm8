package pss.tourism.interfaceGDS.galileo.record;

import pss.core.tools.collections.JMap;

public class RecordA02 extends GalileoRecord {
	
	public static final String A02SEC = "A02SEC";
	public static final String A02NME = "A02NME";
	public static final String A02TRN = "A02TRN";
	public static final String A02TIN = "A02TIN";
	public static final String A02YIN = "A02YIN";
	public static final String A02TKT = "A02TKT";
	public static final String A02NBK = "A02NBK";
	public static final String A02INV = "A02INV";
	public static final String A02PIC = "A02PIC";
	public static final String A02FIN = "A02FIN";
	public static final String A02EIN = "A02EIN";
	public static final String A02FFN = "A02FFN";
	public static final String A02C01 = "A02C01";
	public static final String A02PN1 = "A02PN1";
	public static final String A02PNR = "A02PNR";
	public static final String A02PT1 = "A02PT1";
	public static final String A02PTL = "A02PTL";
	public static final String A02PA1 = "A02PA1";
	public static final String A02PA2 = "A02PA2";
	public static final String A02PG1 = "A02PG1";
	public static final String A02PG2 = "A02PG2";
	public static final String A02PS1 = "A02PS1";
	public static final String A02PSP = "A02PSP";
	public static final String A02PC1 = "A02PC1";
	public static final String A02PCC = "A02PCC";
	public static final String A02PP1 = "A02PP1";
	public static final String A02PPN = "A02PPN";
	public static final String A02PD1 = "A02PD1";
	public static final String A02PDE = "A02PDE";
	public static final String A02SCI = "A02SCI";
	public static final String A02SCD = "A02SCD";
	public static final String A02SCN = "A02SCN";
	public static final String A02SCA = "A02SCA";
	public static final String A02FQI = "A02FQI";
	public static final String A02FQS = "A02FQS";
	public static final String A02ENI = "A02ENI";
	public static final String A02EXN = "A02EXN";
	public static final String A02PMI = "A02PMI";
	public static final String A02PRM = "A02PRM";
	public static final String A02TLI = "A02TLI";
	public static final String A02TLD = "A02TLD";
	public static final String A02C35 = "A02C35";
	public static final String A02I35 = "A02I35";
	public static final String A02C02 = "A02C02";
	public static final String A02C03 = "A02C03";
	public static final String A02NTD = "A02NTD";
	public static final String A02NFC = "A02NFC";
	

	public RecordA02() {
		ID = A02RECORD;
	}

	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {

		int i = 0;

		if (!line.startsWith(A02RECORD))
			return line;

		addFieldValue(A02SEC , line.substring(i,i+=3));
		addFieldValue(A02NME , line.substring(i,i+=33));
		addFieldValue(A02TRN , line.substring(i,i+=11));
		addFieldValue(A02TIN , line.substring(i,i+22));
		addFieldValue(A02YIN , line.substring(i,i+=1));
		addFieldValue(A02TKT , line.substring(i,i+=10));
		addFieldValue(A02NBK , line.substring(i,i+=2));//2
		if (line.length()>=i+8) addFieldValue(A02INV , line.substring(i,i+=9));//9
		if (line.length()>=i+6) addFieldValue(A02PIC , line.substring(i,i+=6));
		if (line.length()>=i+2) addFieldValue(A02FIN , line.substring(i,i+=2));
		if (line.length()>=i+2) addFieldValue(A02EIN , line.substring(i,i+=2));
		if (line.length()>=i+1) addFieldValue(A02FFN , line.substring(i,i+=1));
//	  addFieldValue(A02NBK , line.substring(i,i+=2));
//		addFieldValue(A02INV , line.substring(i,i+=9));
//		addFieldValue(A02PIC , line.substring(i,i+=6));
//		addFieldValue(A02FIN , line.substring(i,i+=2));
//		addFieldValue(A02EIN , line.substring(i,i+=2));
//		addFieldValue(A02FFN , line.substring(i,i+=1));

		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		
		String extra = line.substring(i);
		int pos = 0;
		pos = extra.indexOf("NR:");
		if (pos!=-1) {
			addFieldValue(A02PN1   , extra.substring(pos,pos+3));
			addFieldValue(A02PNR   , extra.substring(pos+3,pos+3+33));
		}
		pos = extra.indexOf("T:");
		if (pos!=-1) {
			addFieldValue(A02PT1   , extra.substring(pos,pos+2));
			addFieldValue(A02PTL   , extra.substring(pos+2,pos+2+13));
		}
		pos = extra.indexOf("A:");
		if (pos!=-1) {
			addFieldValue(A02PA1   , extra.substring(pos,pos+2));
			addFieldValue(A02PA2   , extra.substring(pos+2,pos+2+3));
		}
		pos = extra.indexOf("G:");
		if (pos!=-1) {
			addFieldValue(A02PG1   , extra.substring(pos,pos+2));
			addFieldValue(A02PG2   , extra.substring(pos+2,pos+2+1));
		}
		pos = extra.indexOf("S:");
		if (pos!=-1) {
			addFieldValue(A02PS1   , extra.substring(pos,pos+2));
			addFieldValue(A02PSP   , extra.substring(pos+2,pos+2+1));
		}
		pos = extra.indexOf("C:");
		if (pos!=-1) {
			addFieldValue(A02PC1   , extra.substring(pos,pos+2));
			addFieldValue(A02PCC   , extra.substring(pos+2,pos+2+2));
		}
		
		pos = extra.indexOf("P:");
		if (pos!=-1) {
			addFieldValue(A02PP1   , extra.substring(pos,pos+2));
			addFieldValue(A02PPN   , extra.substring(pos+2,pos+2+33));
		}
		
		pos = extra.indexOf("D:");
		if (pos!=-1) {
			addFieldValue(A02PD1   , extra.substring(pos,pos+2));
			addFieldValue(A02PDE   , extra.substring(pos+2,pos+2+7));
		}
		
		pos = extra.indexOf("SC:");
		if (pos!=-1) {
			addFieldValue(A02SCI   , extra.substring(pos,pos+3));
			addFieldValue(A02SCD   , extra.substring(pos+3,pos+3+2));
			addFieldValue(A02SCN   , extra.substring(pos+3+2,pos+3+2+11));
			addFieldValue(A02SCA   , extra.substring(pos+3+2+11,pos+3+2+11+4));
		}
		pos = extra.indexOf("SI:");
		if (pos!=-1) {
			addFieldValue(A02FQI   , extra.substring(pos,pos+3));
			addFieldValue(A02FQS   , extra.substring(pos+3,pos+3+1));
		}
		pos = extra.indexOf("EN:");
		if (pos!=-1) {
			addFieldValue(A02ENI   , extra.substring(pos,pos+3));
			addFieldValue(A02EXN   , extra.substring(pos+3,pos+3+55));
		}
		pos = extra.indexOf("PM:");
		if (pos!=-1) {
			addFieldValue(A02PMI   , extra.substring(pos,pos+3));
			addFieldValue(A02PRM   , extra.substring(pos+3,pos+3+10));
		}
		pos = extra.indexOf("TL:");
		if (pos!=-1) {
			addFieldValue(A02TLI   , extra.substring(pos,pos+3));
			addFieldValue(A02TLD   , extra.substring(pos+3,pos+3+8));
		}
		pos = extra.indexOf("NTD:");
		if (pos!=-1) {
			addFieldValue(A02NTD  , extra.substring(pos,pos+4));
			addFieldValue(A02NFC  , extra.substring(pos+4,pos+4+7));
		}
		pos = extra.indexOf("C35:");
		if (pos!=-1) {
			addFieldValue(A02C35   , extra.substring(pos,pos+4));
			addFieldValue(A02I35   , extra.substring(pos+4,pos+4+1));
		}

		return line;
	}


}
