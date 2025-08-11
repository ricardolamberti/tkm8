package pss.tourism.interfaceGDS.galileo.record;

import pss.core.tools.collections.JMap;

public class RecordA24   extends GalileoRecord {
	
	public static final String A24SEC = "A24SEC";
	public static final String A24FSI = "A24FSI";
	public static final String A24TY5 = "A24TY5";
	public static final String A24L51 = "A24L51";
	public static final String A24C01 = "A24C01";
	public static final String A24L52 = "A24L52";
	public static final String A24C02 = "A24C02";
	public static final String A24L53 = "A24L53";
	public static final String A24C03 = "A24C03";
	public static final String A24L54 = "A24L54";
	public static final String A24C04 = "A24C04";
	public static final String A24L55 = "A24L55";
	public static final String A24C05 = "A24C05";
	public static final String A24VAT = "A24VAT";
	public static final String A24C06 = "A24C06";
	public static final String A24C07 = "A24C07";
	

	public RecordA24() {
		ID = A24RECORD;
	}

	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {

		int i = 0;

		if (!line.startsWith(ID))
			return line;

		addFieldValue(A24SEC , line.substring(i,i+=3));
		addFieldValue(A24FSI , line.substring(i,i+=2));
		addFieldValue(A24TY5 , line.substring(i,i+=1));
		addFieldValue(A24L51 , line.substring(i,line.length()));
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		addFieldValue(A24L52 , line.substring(i));
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		addFieldValue(A24L53 , line.substring(i));
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		addFieldValue(A24L54 , line.substring(i));
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		addFieldValue(A24L55 , line.substring(i));
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		addFieldValue(A24VAT , line.substring(i));

		return line;
	}
}
