package pss.tourism.interfaceGDS.galileo.record;

import pss.core.tools.collections.JMap;

public class RecordA10  extends GalileoRecord {
	
	public static final String A10SEC = "A10SEC";
	public static final String A10EXI = "A10EXI";
	public static final String A10OTN = "A10OTN";
	public static final String A10BLK = "A10BLK";
	public static final String A10ONB = "A10ONB";
	public static final String A10OCI = "A10OCI";
	public static final String A10OII = "A10OII";
	public static final String A10OIN = "A10OIN";
	public static final String A10TYP = "A10TYP";
	public static final String A10CUR = "A10CUR";
	public static final String A10OTF = "A10OTF";
	public static final String A10PEN = "A10PEN";
	public static final String A10SCC = "A10SCC";
	public static final String A10C01 = "A10C01";
	public static final String A10CRT = "A10CRT";
	public static final String A10TII = "A10TII";
	public static final String A10TIT = "A10TIT";
	public static final String A10TIN = "A10TIN";
	public static final String A10TI1 = "A10TI1";
	public static final String A10TT1 = "A10TT1";
	public static final String A10TC1 = "A10TC1";
	public static final String A10TI2 = "A10TI2";
	public static final String A10TT2 = "A10TT2";
	public static final String A10TC2 = "A10TC2";
	public static final String A10TI3 = "A10TI3";
	public static final String A10TT3 = "A10TT3";
	public static final String A10TC3 = "A10TC3";
	public static final String A10TI4 = "A10TI4";
	public static final String A10TT4 = "A10TT4";
	public static final String A10TC4 = "A10TC4";
	public static final String A10TI5 = "A10TI5";
	public static final String A10TT5 = "A10TT5";
	public static final String A10TC5 = "A10TC5";
	public static final String A10C02 = "A10C02";
	public static final String A10OTA = "A10OTA";
	public static final String A10OCM = "A10OCM";
	public static final String A10POI = "A10POI";
	public static final String A10CDEP= "A10CDDEP";
	public static final String A10DOI = "A10DOI";
	public static final String A10FOP = "A10FOP";
	public static final String A10RAC = "A10RAC";
	public static final String A10C03 = "A10C03";
	public static final String A10TPI = "A10TPI";
	public static final String A10TP1 = "A10TP1";
	public static final String A10T1C = "A10T1C";
	public static final String A10TP2 = "A10TP2";
	public static final String A10T2C = "A10T2C";
	public static final String A10TP3 = "A10TP3";
	public static final String A10T3C = "A10T3C";
	public static final String A10ITT = "A10ITT";
	public static final String A10IT1 = "A10IT1";
	public static final String A10IT1C= "A10ITC";
	
	public static final String A10C04 = "A10C04";
	public static final String A10C05 = "A10C05";
                                             

                                             
	

	public RecordA10() {
		ID = A10RECORD;
	}
	
	
	public String getOriginalTicket()  throws Exception {
		return this.getFieldValue(A10TIT).substring(3,3+10);

	}

	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {

		int i = 0;

		if (!line.startsWith(ID))
			return line;

		addFieldValue(A10SEC , line.substring(i,i+=3));
		addFieldValue(A10EXI , line.substring(i,i+=2));
		addFieldValue(A10DOI , line.substring(i,i+=7));
		addFieldValue(A10POI , line.substring(i,i+=8));
		addFieldValue(A10CDEP, line.substring(i,i+=4));
		addFieldValue(A10OCM , line.substring(i,i+=9));
		addFieldValue(A10OIN , line.substring(i,i+=9));
		addFieldValue(A10FOP , line.substring(i,i+=19));
		addFieldValue(A10PEN , line.substring(i,i+=9));
		addFieldValue(A10SCC , line.substring(i,i+=9));
		addFieldValue(A10TYP , line.substring(i,i+=1));
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		int pos = 0;
		String impuestos=line;
		pos = impuestos.indexOf("TI:");
		if (pos!=-1) {
			addFieldValue(A10TII   , impuestos.substring(pos,pos+=3));
			addFieldValue(A10TIT   , impuestos.substring(pos,pos+=14));
			addFieldValue(A10TIN   , impuestos.substring(pos,pos+=4));
		}
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		pos = 0;
		impuestos=line;
		addFieldValue(A10CUR , line.substring(i,i+=3));
		addFieldValue(A10OTF , line.substring(i,i+=12));
		pos = impuestos.indexOf("T1:");
		if (pos!=-1) {
			addFieldValue(A10TI1   , impuestos.substring(pos,pos+3));
			addFieldValue(A10TT1   , impuestos.substring(pos+3,pos+11));
			addFieldValue(A10TC1   , impuestos.substring(pos+11,pos+13));
		}
		pos = impuestos.indexOf("T2:");
		if (pos!=-1) {
			addFieldValue(A10TI2   , impuestos.substring(pos,pos+3));
			addFieldValue(A10TT2   , impuestos.substring(pos+3,pos+11));
			addFieldValue(A10TC2   , impuestos.substring(pos+11,pos+13));
		}
		pos = impuestos.indexOf("T3:");
		if (pos!=-1) {
			addFieldValue(A10TI3   , impuestos.substring(pos,pos+3));
			addFieldValue(A10TT3   , impuestos.substring(pos+3,pos+11));
			addFieldValue(A10TC3   , impuestos.substring(pos+11,pos+13));
		}
		pos = impuestos.indexOf("T4:");
		if (pos!=-1) {
			addFieldValue(A10TI4   , impuestos.substring(pos,pos+3));
			addFieldValue(A10TT4   , impuestos.substring(pos+3,pos+11));
			addFieldValue(A10TC4   , impuestos.substring(pos+11,pos+13));
		}
		pos = impuestos.indexOf("T5:");
		if (pos!=-1) {
			addFieldValue(A10TI5   , impuestos.substring(pos,pos+3));
			addFieldValue(A10TT5   , impuestos.substring(pos+3,pos+11));
			addFieldValue(A10TC5   , impuestos.substring(pos+11,pos+13));
		}
		line = this.getInput().readLine();
		if (line == null) return line;
		if (line.equals("")) return line;
		i=0;
		pos = 0;
		impuestos=line;
		pos = impuestos.indexOf("OI:");
		if (pos!=-1) {
			addFieldValue(A10OII   , impuestos.substring(pos,pos+=3));
			addFieldValue(A10OIN   , impuestos.substring(pos,pos+=9));
			addFieldValue(A10OTN   , impuestos.substring(pos,pos+=19));
			line = this.getInput().readLine();
			if (line == null) return line;
		}
		i=0;
		pos = 0;
		impuestos=line;
		pos = impuestos.indexOf("IT:");
		if (pos!=-1) {
			addFieldValue(A10ITT , impuestos.substring(pos,pos+=3));
			while (impuestos.length()>pos+10) {
			addFieldValue(A10IT1+"_"+i , impuestos.substring(pos,pos+=8));
			addFieldValue(A10IT1C+"_"+i, impuestos.substring(pos,pos+=2));
			i++;
			}
		}

		return line;
	}
}
