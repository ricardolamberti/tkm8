package pss.tourism.interfaceGDS.galileo.record;

import pss.core.tools.collections.JMap;

public class RecordA07 extends GalileoRecord {
	
	public static final String A07SEC  ="A07SEC";
	public static final String A07FSI  ="A07FSI";
	public static final String A07CRB  ="A07CRB";
	public static final String A07TBF  ="A07TBF";
	public static final String A07CRT  ="A07CRT";
	public static final String A07TTA  ="A07TTA";
	public static final String A07CRE  ="A07CRE";
	public static final String A07EQV  ="A07EQV";
	public static final String A07NRI  ="A07NRI";
	public static final String A07NRT  ="A07NRT";
	public static final String A07CUR  ="A07CUR";
	public static final String A07TI1  ="A07TI1";
	public static final String A07TT1  ="A07TT1";
	public static final String A07TC1  ="A07TC1";
	public static final String A07TI2  ="A07TI2";
	public static final String A07TT2  ="A07TT2";
	public static final String A07TC2  ="A07TC2";
	public static final String A07TI3  ="A07TI3";
	public static final String A07TT3  ="A07TT3";
	public static final String A07TC3  ="A07TC3";
	public static final String A07TI4  ="A07TI4";
	public static final String A07TT4  ="A07TT4";
	public static final String A07TC4  ="A07TC4";
	public static final String A07TI5  ="A07TI5";
	public static final String A07TT5  ="A07TT5";
	public static final String A07TC5  ="A07TC5";
	public static final String A07CO1  ="A07CO1";
	public static final String A07ITT  ="A07ITT";
	public static final String A07IT1  ="A07IT1";
	public static final String A07IT1C ="A07IT1C ";
	public static final String A07IT2  ="A07IT2";
	public static final String A07IT2C ="A07IT2C ";
	public static final String A07IT3  ="A07IT3";
	public static final String A07IT3C ="A07IT3C ";
	public static final String A07IT4  ="A07IT4";
	public static final String A07IT4C ="A07IT4C ";
	public static final String A07IT5  ="A07IT5";
	public static final String A07IT5C ="A07IT5C ";
	public static final String A07IT6  ="A07IT6";
	public static final String A07IT6C ="A07IT6C ";
	public static final String A07IT7  ="A07IT7";
	public static final String A07IT7C ="A07IT7C ";
	public static final String A07IT8  ="A07IT8";
	public static final String A07IT8C ="A07IT8C ";
	public static final String A07IT9  ="A07IT9";
	public static final String A07IT9C ="A07IT9C";
	public static final String A07IT10 ="A07IT10";
	public static final String A07IT10C="A07IT10C";
	public static final String A07IT11 ="A07IT11";
	public static final String A07IT11C="A07IT11C";
	public static final String A07IT12 ="A07IT12";
	public static final String A07IT12C="A07IT12C";
	public static final String A07IT13 ="A07IT13";
	public static final String A07IT13C="A07IT13C";
	public static final String A07IT14 ="A07IT14";
	public static final String A07IT14C="A07IT14C";
	public static final String A07IT15 ="A07IT15";
	public static final String A07IT15C="A07IT15C";
	public static final String A07IT16 ="A07IT16";
	public static final String A07IT16C="A07IT16C";
	public static final String A07IT17 ="A07IT17";
	public static final String A07IT17C="A07IT17C";
	public static final String A07IT18 ="A07IT18";
	public static final String A07IT18C="A07IT18C";
	public static final String A07IT19 ="A07IT19";
	public static final String A07IT19C="A07IT19C";
	public static final String A07IT20 ="A07IT20";
	public static final String A07IT20C="A07IT20C";
	public static final String A07C02  ="A07C02";
	public static final String A07C03  ="A07C03";

                                             
	

	public RecordA07() {
		ID = A07RECORD;
	}

	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {

		int i = 0;

		if (!line.startsWith(ID))
			return line;

		addFieldValue(A07SEC   , line.substring(i,i+=3));
		addFieldValue(A07FSI   , line.substring(i,i+=2));
		addFieldValue(A07CRB   , line.substring(i,i+=3));
		addFieldValue(A07TBF   , line.substring(i,i+=12));
		addFieldValue(A07CRT   , line.substring(i,i+=3));
		addFieldValue(A07TTA   , line.substring(i,i+=12));
		addFieldValue(A07CRE   , line.substring(i,i+=3));
		addFieldValue(A07EQV   , line.substring(i,i+=12));
		String impuestos = line.substring(i);
		int pos = 0;
		pos = impuestos.indexOf("NR:");
		if (pos!=-1) {
			addFieldValue(A07NRI   , impuestos.substring(pos,pos+3));
			addFieldValue(A07NRT   , impuestos.substring(pos+3,pos+11));
			if (pos==0) i+=11;
		}
		if (line.substring(i).equals("")) return line;
		addFieldValue(A07CUR   , line.substring(i,i+=3));
		pos = impuestos.indexOf("T1:");
		if (pos!=-1) {
			addFieldValue(A07TI1   , impuestos.substring(pos,pos+3));
			addFieldValue(A07TT1   , impuestos.substring(pos+3,pos+11));
			addFieldValue(A07TC1   , impuestos.substring(pos+11,pos+13));
		}
		pos = impuestos.indexOf("T2:");
		if (pos!=-1) {
			addFieldValue(A07TI2   , impuestos.substring(pos,pos+3));
			addFieldValue(A07TT2   , impuestos.substring(pos+3,pos+11));
			addFieldValue(A07TC2   , impuestos.substring(pos+11,pos+13));
		}
		pos = impuestos.indexOf("T3:");
		if (pos!=-1) {
			addFieldValue(A07TI3   , impuestos.substring(pos,pos+3));
			addFieldValue(A07TT3   , impuestos.substring(pos+3,pos+11));
			addFieldValue(A07TC3   , impuestos.substring(pos+11,pos+13));
		}
		pos = impuestos.indexOf("T4:");
		if (pos!=-1) {
			addFieldValue(A07TI4   , impuestos.substring(pos,pos+3));
			addFieldValue(A07TT4   , impuestos.substring(pos+3,pos+11));
			addFieldValue(A07TC4   , impuestos.substring(pos+11,pos+13));
		}
		pos = impuestos.indexOf("T5:");
		if (pos!=-1) {
			addFieldValue(A07TI5   , impuestos.substring(pos,pos+3));
			addFieldValue(A07TT5   , impuestos.substring(pos+3,pos+11));
			addFieldValue(A07TC5   , impuestos.substring(pos+11,pos+13));
		}
		
		
		this.getInput().mark(1000);
		line = this.getInput().readLine();
		if (line == null) return line;
		if (line.startsWith("A07")) {
			this.getInput().reset();
			return line;
		}
		if (line.length()<3) return line;
		i=0;
		String mark = line.substring(i,i+=3);
		addFieldValue(A07ITT   , mark);
		if (i+10>line.length()) return line;
		if (mark.equalsIgnoreCase("et:")) {
			addFieldValue(A07IT1   , line.substring(i,i+=11));
			addFieldValue(A07IT1C  , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT2   , line.substring(i,i+=11));
			addFieldValue(A07IT2C  , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT3   , line.substring(i,i+=11));
			addFieldValue(A07IT3C  , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT4   , line.substring(i,i+=11));
			addFieldValue(A07IT4C  , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT5   , line.substring(i,i+=11));
			addFieldValue(A07IT5C  , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT6   , line.substring(i,i+=11));
			addFieldValue(A07IT6C  , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT7   , line.substring(i,i+=11));
			addFieldValue(A07IT7C  , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT8   , line.substring(i,i+=11));
			addFieldValue(A07IT8C  , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT9   , line.substring(i,i+=11));
			addFieldValue(A07IT9C  , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT10  , line.substring(i,i+=11));
			addFieldValue(A07IT10C , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT11  , line.substring(i,i+=11));
			addFieldValue(A07IT11C , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT12  , line.substring(i,i+=11));
			addFieldValue(A07IT12C , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT13  , line.substring(i,i+=11));
			addFieldValue(A07IT13C , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT14  , line.substring(i,i+=11));
			addFieldValue(A07IT14C , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT15  , line.substring(i,i+=11));
			addFieldValue(A07IT15C , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT16  , line.substring(i,i+=11));
			addFieldValue(A07IT16C , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT17  , line.substring(i,i+=11));
			addFieldValue(A07IT17C , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT18  , line.substring(i,i+=11));
			addFieldValue(A07IT18C , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT19  , line.substring(i,i+=11));
			addFieldValue(A07IT19C , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT20  , line.substring(i,i+=11));
			addFieldValue(A07IT20C , line.substring(i,i+=2));

		} else {
			addFieldValue(A07IT1   , line.substring(i,i+=8));
			addFieldValue(A07IT1C  , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT2   , line.substring(i,i+=8));
			addFieldValue(A07IT2C  , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT3   , line.substring(i,i+=8));
			addFieldValue(A07IT3C  , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT4   , line.substring(i,i+=8));
			addFieldValue(A07IT4C  , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT5   , line.substring(i,i+=8));
			addFieldValue(A07IT5C  , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT6   , line.substring(i,i+=8));
			addFieldValue(A07IT6C  , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT7   , line.substring(i,i+=8));
			addFieldValue(A07IT7C  , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT8   , line.substring(i,i+=8));
			addFieldValue(A07IT8C  , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT9   , line.substring(i,i+=8));
			addFieldValue(A07IT9C  , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT10  , line.substring(i,i+=8));
			addFieldValue(A07IT10C , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT11  , line.substring(i,i+=8));
			addFieldValue(A07IT11C , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT12  , line.substring(i,i+=8));
			addFieldValue(A07IT12C , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT13  , line.substring(i,i+=8));
			addFieldValue(A07IT13C , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT14  , line.substring(i,i+=8));
			addFieldValue(A07IT14C , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT15  , line.substring(i,i+=8));
			addFieldValue(A07IT15C , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT16  , line.substring(i,i+=8));
			addFieldValue(A07IT16C , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT17  , line.substring(i,i+=8));
			addFieldValue(A07IT17C , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT18  , line.substring(i,i+=8));
			addFieldValue(A07IT18C , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT19  , line.substring(i,i+=8));
			addFieldValue(A07IT19C , line.substring(i,i+=2));
			if (i+10>line.length()) return line;
			addFieldValue(A07IT20  , line.substring(i,i+=8));
			addFieldValue(A07IT20C , line.substring(i,i+=2));
		
		}

		
		return line;
	}
}
