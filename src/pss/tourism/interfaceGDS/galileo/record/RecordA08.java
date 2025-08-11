package pss.tourism.interfaceGDS.galileo.record;

import pss.core.tools.collections.JMap;

public class RecordA08 extends GalileoRecord {
	
	public static final String A08SEC = "A08SEC";
	public static final String A08FSI = "A08FSI";
	public static final String A08ITN = "A08ITN";
	public static final String A08FBC = "A08FBC";
	public static final String A08VAL = "A08VAL";
	public static final String A08NVBC= "A08NVBC";
	public static final String A08NVAC= "A08NVAC";
	public static final String A08TDGC= "A08TDGC";
	public static final String A08CFI = "A08CFI";
	public static final String A08CFB = "A08CFB";
	public static final String A08ENDI= "A08ENDI";
	public static final String A08END = "A08END";
	public static final String A08BAGI= "A08AGI";
	public static final String A08BAG = "A08BAG";
	public static final String A08C01 = "A08C01";
	public static final String A08EID = "A08EID";
	public static final String A08ENF = "A08ENF";
	public static final String A08C02 = "A08C02";
	public static final String A08C03 = "A08C03";


                                             
	

	public RecordA08() {
		ID = A08RECORD;
	}

	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {

		int i = 0;

		if (!line.startsWith(ID))
			return line;

		addFieldValue(A08SEC  , line.substring(i,i+=3));
		addFieldValue(A08FSI  , line.substring(i,i+=2));
		addFieldValue(A08ITN  , line.substring(i,i+=2));
		addFieldValue(A08FBC  , line.substring(i,i+=8));
		addFieldValue(A08VAL  , line.substring(i,i+=8));
		addFieldValue(A08NVBC , line.substring(i,i+=7));
		addFieldValue(A08NVAC , line.substring(i,i+=7));
		addFieldValue(A08TDGC , line.substring(i,i+=6));
		
		String extra = line.substring(i);
		int pos = 0;
		pos = extra.indexOf("F:");
		if (pos!=-1) {
			int j = pos;
			addFieldValue(A08CFI   , extra.substring(j,j+=2));
			if (extra.length()>=j+15)
			  addFieldValue(A08CFB   , extra.substring(j,j+=15));
		}
		pos = extra.indexOf("E:");
		int k =1;
		while (pos!=-1) {
			int j = pos;
			addFieldValue(A08ENDI+"_"+k   , extra.substring(j,j+=2));
			addFieldValue(A08END+"_"+k   , extra.substring(j,j+=28));
			k++;
			pos = extra.indexOf("E:",j);
		}
		pos = extra.indexOf("B:");
		if (pos!=-1) {
			int j = pos;
			addFieldValue(A08BAGI   , extra.substring(j,j+=2));
			addFieldValue(A08BAG   , extra.substring(j,j+=3));
		}

		this.getInput().mark(1000);
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		
		extra = line.substring(i);
		pos = extra.indexOf("EF:");
		if (pos!=-1) {
			int j = pos;
			addFieldValue(A08EID   , extra.substring(j,j+=3));
			addFieldValue(A08ENF   , extra.substring(j,j+=(extra.length()>=j+147?147:extra.length()-j)));
		} else {
			this.getInput().reset();
		}
		return line;
	}
}
