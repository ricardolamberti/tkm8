package pss.tourism.interfaceGDS.galileo.record;

import pss.core.tools.collections.JMap;

public class RecordA09 extends GalileoRecord {
	
	public static final String A09SEC = "A09SEC";
	public static final String A09FSI = "A09FSI";
	public static final String A09TY5 = "A09TY5";
	public static final String A09L51 = "A09L51";
	public static final String A09C01 = "A09C01";
	public static final String A09l52 = "A09l52";
	public static final String A09C02 = "A09C02";
	public static final String A09l53 = "A09l53";
	public static final String A09C03 = "A09C03";
	public static final String A09l54 = "A09l54";
	public static final String A09C04 = "A09C04";
	public static final String A09l55 = "A09l55";
	public static final String A09C05 = "A09C05";
	public static final String A09VAT = "A09VAT";
	public static final String A09C06 = "A09C06";
	public static final String A09C07 = "A09C07";


                                             
	

	public RecordA09() {
		ID = A09RECORD;
	}

	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {

		int i = 0;

		if (!line.startsWith(ID))
			return line;

		addFieldValue(A09SEC , line.substring(i,i+=3));
		addFieldValue(A09FSI , line.substring(i,i+=2));
		addFieldValue(A09TY5 , line.substring(i,i+=1));
		addFieldValue(A09L51 , line.substring(i));
		this.getInput().mark(2000);
  	line = this.getInput().readLine();
		if (line == null) return line;
		if (line.equals("")) {
			this.getInput().reset();
			return line;
		} 
		i=0;
		addFieldValue(A09l52 , line.substring(i));
		this.getInput().mark(2000);
		line = this.getInput().readLine();
		if (line == null) return line;
		if (line.equals("")) {
			this.getInput().reset();
			return line;
		} 
		i=0;
		addFieldValue(A09l53 , line.substring(i));
		this.getInput().mark(2000);
		line = this.getInput().readLine();
		if (line == null) return line;
		if (line.equals("")) {
			this.getInput().reset();
			return line;
		} 
		i=0;	
		addFieldValue(A09l54 , line.substring(i));
		this.getInput().mark(2000);
		line = this.getInput().readLine();
		if (line == null) return line;
		if (line.equals("")) {
			this.getInput().reset();
			return line;
		} 
		i=0;	
		addFieldValue(A09l55 , line.substring(i));
		this.getInput().mark(2000);
		line = this.getInput().readLine();
		if (line == null) return line;
		if (line.equals("")) {
			this.getInput().reset();
			return line;
		} 
		i=0;	
		addFieldValue(A09VAT , line.substring(i));

		return line;
	}
}
