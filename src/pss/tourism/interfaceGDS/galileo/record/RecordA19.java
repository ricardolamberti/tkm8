package pss.tourism.interfaceGDS.galileo.record;

import pss.core.tools.collections.JMap;

public class RecordA19   extends GalileoRecord {
	
	public static final String A19SEC = "A19SEC";
	public static final String A19MPD = "A19MPD";
	public static final String A19MTO = "A19MTO";
	public static final String A19MAT = "A19MAT";
	public static final String A19C01 = "A19C01";
	public static final String A19MSI = "A19MSI";
	public static final String A19MST = "A19MST";
	public static final String A19C02 = "A19C02";
	public static final String A19MEI = "A19MEI";
	public static final String A19MEB = "A19MEB";
	public static final String A19CO3 = "A19CO3";
	public static final String A19M11 = "A19M11";
	public static final String A19MR1 = "A19MR1";
	public static final String A19C04 = "A19C04";
	public static final String A19M2I = "A19M2I";
	public static final String A19MR2 = "A19MR2";
	public static final String A19C05 = "A19C05";
	public static final String A19M3I = "A19M3I";
	public static final String A19MR3 = "A19MR3";
	public static final String A19C06 = "A19C06";
	public static final String A19MTI = "A19MTI";
	public static final String A19MTN = "A19MTN";
	public static final String A19MBI = "A19MBI";
	public static final String A19MBS = "A19MBS";
	public static final String A19MRI = "A19MRI";
	public static final String A19MRE = "A19MRE";
	public static final String A19C07 = "A19C07";
	public static final String A19C08 = "A19C08";


	public RecordA19() {
		ID = A19RECORD;
	}

	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {

		int i = 0;

		if (!line.startsWith(ID))
			return line;

		addFieldValue(A19SEC , line.substring(i,i+=3));
		addFieldValue(A19MPD , line.substring(i,i+=3));
		addFieldValue(A19MTO , line.substring(i,i+=40));
		addFieldValue(A19MAT , line.substring(i,i+=15));
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		addFieldValue(A19MSI , line.substring(i,i+=3));
		addFieldValue(A19MST , line.substring(i,i+=52));
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		addFieldValue(A19MEI , line.substring(i,i+=3));
		addFieldValue(A19MEB , line.substring(i,i+=54));
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		addFieldValue(A19M11 , line.substring(i,i+=3));
		addFieldValue(A19MR1 , line.substring(i,i+=46));
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		addFieldValue(A19M2I , line.substring(i,i+=3));
		addFieldValue(A19MR2 , line.substring(i,i+=54));
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		addFieldValue(A19M3I , line.substring(i,i+=3));
		addFieldValue(A19MR3 , line.substring(i,i+=106));
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		addFieldValue(A19MTI , line.substring(i,i+=3));
		addFieldValue(A19MTN , line.substring(i,i+=13));
		addFieldValue(A19MBI , line.substring(i,i+=3));
		addFieldValue(A19MBS , line.substring(i,i+=13));
		addFieldValue(A19MRI , line.substring(i,i+=3));
		addFieldValue(A19MRE , line.substring(i,i+=1));

		return line;
	}
}
