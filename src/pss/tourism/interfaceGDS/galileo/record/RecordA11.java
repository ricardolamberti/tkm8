package pss.tourism.interfaceGDS.galileo.record;

import pss.core.tools.collections.JMap;

public class RecordA11   extends GalileoRecord {
	
	public static final String A11SEC = "A11SEC";
	public static final String A11TYP = "A11TYP";
	public static final String A11AMT = "A11AMT";
	public static final String A11REF = "A11REF";
	public static final String A11CCC = "A11CCC";
	public static final String A11CCN = "A11CCN";
	public static final String A11EXP = "A11EXP";
	public static final String A11APP = "A11APP";
	public static final String A11BLK = "A11BLK";
	public static final String A11MAN = "A11MAN";
	public static final String A11APC = "A11APC";
	public static final String A11PPO = "A11PPO";
	public static final String A11AVI = "A11AVI";
	public static final String A11AVS = "A11AVS";
	public static final String A11CVI = "A11CVI";
	public static final String A11CVS = "A11CVS";
	public static final String A11PGRI= "A11PGRI";
	public static final String A11PGR = "A11PGR";
	public static final String A11FTXI= "A11FTXI";
	public static final String A11FTX = "A11FTX";
	public static final String A11CCHI= "A11CCHI";
	public static final String A11CCH = "A11CCH";
	public static final String A11ONOI= "A11ONOI";
	public static final String A11ONO = "A11ONO";
	public static final String A11CO1 = "A11CO1";
	public static final String A11CO2 = "A11CO2";


	public RecordA11() {
		ID = A11RECORD;
	}

	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {

		int i = 0;

		if (!line.startsWith(ID))
			return line;

		addFieldValue(A11SEC  , line.substring(i,i+=3));
		addFieldValue(A11TYP  , line.substring(i,i+=2));
		addFieldValue(A11AMT  , line.substring(i,i+=12));
		addFieldValue(A11REF  , line.substring(i,i+=1));
		addFieldValue(A11CCC  , line.substring(i,i+=2));
		addFieldValue(A11CCN  , line.substring(i,i+=20));
		addFieldValue(A11EXP  , line.substring(i,i+=4));
		addFieldValue(A11APP  , line.substring(i,i+8));
		addFieldValue(A11BLK  , line.substring(i,i+=1));
		if (line.length()>i+10) {
			addFieldValue(A11MAN  , line.substring(i,i+=1));
			addFieldValue(A11APC  , line.substring(i,i+=6));//6
			addFieldValue(A11PPO  , line.substring(i,i+=3));
			
		}
		
		String extra = line.substring(i);
		int pos = 0;
		pos = extra.indexOf("A:");
		if (pos!=-1) {
			int j = pos;
			addFieldValue(A11AVI   , extra.substring(j,j+=2));
			addFieldValue(A11AVS   , extra.substring(j,j+=1));
		}
		pos = extra.indexOf("C:");
		if (pos!=-1) {
			int j = pos;
			addFieldValue(A11CVI   , extra.substring(j,j+=2));
			addFieldValue(A11CVS   , extra.substring(j,j+=1));
		}
		pos = extra.indexOf("P:");
		if (pos!=-1) {
			int j = pos;
			addFieldValue(A11PGRI   , extra.substring(j,j+=2));
			addFieldValue(A11PGR   , extra.substring(j,j+=2));
		}
		pos = extra.indexOf("F:");
		if (pos!=-1) {
			int j = pos;
			addFieldValue(A11FTXI   , extra.substring(j,j+=2));
			addFieldValue(A11FTX   , extra.substring(j,j+=41));
		}
		pos = extra.indexOf("H:");
		if (pos!=-1) {
			int j = pos;
			addFieldValue(A11CCHI   , extra.substring(j,j+=2));
			addFieldValue(A11CCH   , extra.substring(j,j+=30));
		}
		pos = extra.indexOf("O:");
		if (pos!=-1) {
			int j = pos;
			addFieldValue(A11ONOI   , extra.substring(j,j+=2));
			addFieldValue(A11ONO   , extra.substring(j,j+=3));
		}

		return line;
	}
}
