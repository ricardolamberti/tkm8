package pss.tourism.interfaceGDS.galileo.record;

import pss.core.tools.collections.JMap;

public class RecordA16   extends GalileoRecord {
	
	public static final String A16SEC = "A16SEC";
	public static final String A16TYP = "A16TYP";
	public static final String A16NUM = "A16NUM";
	public static final String A16DTE = "A16DTE";
	public static final String A16PRP = "A16PRP";
	public static final String A16HCC = "A16HCC";
	public static final String A16CTY = "A16CTY";
	public static final String A16MUL = "A16MUL";
	public static final String A16STT = "A16STT";
	public static final String A16OUT = "A16OUT";
	public static final String A16DAY = "A16DAY";
	public static final String A16NME = "A16NME";
	public static final String A16FON = "A16FON";
	public static final String A16FAX = "A16FAX";
	public static final String A16RTT = "A16RTT";
	public static final String A16RMS = "A16RMS";
	public static final String A16LOC = "A16LOC";
	public static final String A16C01 = "A16C01";
	public static final String A16ODN = "A16ODN";
	public static final String A16ODI = "A16ODI";
	public static final String A16ODD = "A16ODD";
	public static final String A16CO2 = "A16CO2";
	public static final String A16FFN = "A16FFN";
	public static final String A16FFI = "A16FFI";
	public static final String A16FIP = "A16FIP";
	public static final String A16FTI = "A16FTI";
	public static final String A16FFD = "A16FFD";
	public static final String A16C03 = "A16C03";
	public static final String A16CFN = "A16CFN";
	public static final String A16CFI = "A16CFI";
	public static final String A16CFD = "A16CFD";
	public static final String A16CIP = "A16CIP";
	public static final String A16C04 = "A16C04";
	public static final String A16DPN = "A16DPN";
	public static final String A16DPI = "A16DPI";
	public static final String A16DPP = "A16DPP";
	public static final String A16DTI = "A16DTI";
	public static final String A16DAD = "A16DAD";
	public static final String A16DCR = "A16DCR";
	public static final String A16DPD = "A16DPD";
	public static final String A16DPA = "A16DPA";
	public static final String A16DPF = "A16DPF";
	public static final String A16CO5 = "A16CO5";
	public static final String A16WAN = "A16WAN";
	public static final String A16WAI = "A16WAI";
	public static final String A16WAP = "A16WAP";
	public static final String A16WAD = "A16WAD";
	public static final String A16C06 = "A16C06";
	public static final String A16CO7 = "A16CO7";
	public static final String A16CAR = "A16CAR";
	public static final String A16STA = "A16STA";
	public static final String A16CDT = "A16CDT";
	public static final String A16CCC = "A16CCC";
	public static final String A16CVC = "A16CVC";
	public static final String A16CCT = "A16CCT";
	public static final String A16CNI = "A16CNI";
	public static final String A16PUP = "A16PUP";
	public static final String A16DOL = "A16DOL";
	public static final String A16PHN = "A16PHN";
	public static final String A16VCN = "A16VCN";
	public static final String A16VCI = "A16VCI";
	public static final String A16VIP = "A16VIP";
	public static final String A16EVV = "A16EVV";
	public static final String A16EVC = "A16EVC";
	public static final String A16EBN = "A16EBN";
	public static final String A16EAN = "A16EAN";
	public static final String A16CID = "A16CID";
	public static final String A16CII = "A16CII";
	public static final String A16CNO = "A16CNO";
	public static final String A16TTY = "A16TTY";
	public static final String A16VEN = "A16VEN";
	public static final String A16VNN = "A16VNN";
	public static final String A16UID = "A16UID";
	public static final String A16UNO = "A16UNO";
	public static final String A16DIN = "A16DIN";
	public static final String A16BKI = "A16BKI";
	public static final String A16AIR = "A16AIR";
	public static final String A16STS = "A16STS";
	public static final String A16REQ = "A16REQ";
	public static final String A16PLC = "A16PLC";
	public static final String A16DAT = "A16DAT";
	public static final String A16RFI = "A16RFI";
	public static final String A16RFC = "A16RFC";
	public static final String A16FOR = "A16FOR";
	public static final String A16PNN = "A16PNN";
	public static final String A16NAM = "A16NAM";
	public static final String A16EMD = "A16EMD";
	public static final String A16CEA = "A16CEA";
	public static final String A16EAM = "A16EAM";
	public static final String A16CTX = "A16CTX";
	public static final String A16TAM = "A16TAM";
	public static final String A16CTA = "A16CTA";
	public static final String A16TLA = "A16TLA";
	public static final String A16FXI = "A16FXI";
	public static final String A16FTX = "A16FTX";
	public static final String A16O11 = "A16O11";
		
	public RecordA16() {
		ID = A16RECORD;
	}

	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {

		int i = 0;

		if (!line.startsWith(ID))
			return line;

		String vartype = line.substring(3,6);
		String type = line.substring(3,4);
		if (vartype.equals("UZ:")) return doProcessUZ(mRecords, line);
		if (type.equals("A")) return doProcessA(mRecords, line);
		if (type.equals("7")) return doProcess7(mRecords, line);
		if (type.equals("B")) return doProcessB8(mRecords, line);
		if (type.equals("8")) return doProcessB8(mRecords, line);
		if (type.equals("6")) return doProcess65(mRecords, line);
		if (type.equals("5")) return doProcess65(mRecords, line);
		if (type.equals("9")) return doProcess9(mRecords, line);
		return line;
	}
	
	public String doProcessA(JMap<String, Object> mRecords, String line) throws Exception {
		int i = 0;
		addFieldValue(A16SEC , line.substring(i,i+=3));
		addFieldValue(A16TYP , line.substring(i,i+=1));
		addFieldValue(A16NUM , line.substring(i,i+=2));
		addFieldValue(A16DTE , line.substring(i,i+=7));
		addFieldValue(A16PRP , line.substring(i,i+=6));
		addFieldValue(A16HCC , line.substring(i,i+=2));
		addFieldValue(A16CTY , line.substring(i,i+=4));
		addFieldValue(A16MUL , line.substring(i,i+=1));
		addFieldValue(A16STT , line.substring(i,i+=2));
		addFieldValue(A16OUT , line.substring(i,i+=5));
		addFieldValue(A16DAY , line.substring(i,i+=3));
		addFieldValue(A16NME , line.substring(i,i+=20));
		addFieldValue(A16FON , line.substring(i,i+=17));
		addFieldValue(A16FAX , line.substring(i,i+=17));
		addFieldValue(A16RTT , line.substring(i,i+=1));
		addFieldValue(A16RMS , line.substring(i,i+=8));
		addFieldValue(A16LOC , line.substring(i,i+=20));
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		addFieldValue(A16ODN , line.substring(i,i+=(line.length()<i+223)?line.length()-i:223));
		if (line.length()>i)addFieldValue(A16ODI , line.substring(i,i+=(line.length()<i+3)?line.length()-i:3));
		if (line.length()>i)addFieldValue(A16ODD , line.substring(i,i+=(line.length()<i+220)?line.length()-i:220));
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		addFieldValue(A16FFN , line.substring(i,i+=(line.length()<i+66)?line.length()-1:66));
		if (line.length()>i)addFieldValue(A16FFI , line.substring(i,i+=(line.length()<i+3)?line.length()-i:3));
		if (line.length()>i)addFieldValue(A16FIP , line.substring(i,i+=(line.length()<i+2)?line.length()-i:2));
		if (line.length()>i)addFieldValue(A16FTI , line.substring(i,i+=1));
		if (line.length()>i)addFieldValue(A16FFD , line.substring(i,i+=(line.length()<i+60)?line.length()-i:60));
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
//		addFieldValue(A16CFN , line.substring(i,i+=35));
//		addFieldValue(A16CFI , line.substring(i,i+=3));
//		addFieldValue(A16CIP , line.substring(i,i+=2));
//		addFieldValue(A16CFD , line.substring(i,i+=30));
		if (line.length()>i)addFieldValue(A16CFN , line.substring(i,i+=(line.length()<i+35)?line.length()-i:35));
		if (line.length()>i)addFieldValue(A16CFI , line.substring(i,i+=(line.length()<i+3)?line.length()-i:3));
		if (line.length()>i)addFieldValue(A16CIP , line.substring(i,i+=(line.length()<i+2)?line.length()-i:2));
		if (line.length()>i)addFieldValue(A16CFD , line.substring(i,i+=(line.length()<i+30)?line.length()-i:30));
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
//		addFieldValue(A16DPN , line.substring(i,i+=94));
//		addFieldValue(A16DPI , line.substring(i,i+=3));
//		addFieldValue(A16DPP , line.substring(i,i+=2));
//		addFieldValue(A16DTI , line.substring(i,i+=1));
//		addFieldValue(A16DAD , line.substring(i,i+=12));
//		addFieldValue(A16DCR , line.substring(i,i+=3));
//		addFieldValue(A16DPD , line.substring(i,i+=12));
//		addFieldValue(A16DPA , line.substring(i,i+=1));
//		addFieldValue(A16DPF , line.substring(i,i+=60));
		if (line.length()>i)addFieldValue(A16DPN , line.substring(i,i+=(line.length()<i+94)?line.length()-i:94));
		if (line.length()>i)addFieldValue(A16DPI , line.substring(i,i+=(line.length()<i+3)?line.length()-i:3));
		if (line.length()>i)addFieldValue(A16DPP , line.substring(i,i+=(line.length()<i+2)?line.length()-i:2));
		if (line.length()>i)addFieldValue(A16DTI , line.substring(i,i+=(line.length()<i+1)?line.length()-i:1));
		if (line.length()>i)addFieldValue(A16DAD , line.substring(i,i+=(line.length()<i+12)?line.length()-i:12));
		if (line.length()>i)addFieldValue(A16DCR , line.substring(i,i+=(line.length()<i+3)?line.length()-i:3));
		if (line.length()>i)addFieldValue(A16DPD , line.substring(i,i+=(line.length()<i+12)?line.length()-i:12));
		if (line.length()>i)addFieldValue(A16DPA , line.substring(i,i+=(line.length()<i+1)?line.length()-i:1));
		if (line.length()>i)addFieldValue(A16DPF , line.substring(i,i+=(line.length()<i+60)?line.length()-i:6));
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
//		addFieldValue(A16WAN , line.substring(i,i+=105));
//		addFieldValue(A16WAI , line.substring(i,i+=3));
//		addFieldValue(A16WAP , line.substring(i,i+=2));
//		addFieldValue(A16WAD , line.substring(i,i+=100));
		if (line.length()>i)addFieldValue(A16WAN , line.substring(i,i+=(line.length()<i+105)?line.length()-i:105));
		if (line.length()>i)addFieldValue(A16WAI , line.substring(i,i+=(line.length()<i+3)?line.length()-i:3));
		if (line.length()>i)addFieldValue(A16WAP , line.substring(i,i+=(line.length()<i+2)?line.length()-i:2));
		if (line.length()>i)addFieldValue(A16WAD , line.substring(i,i+=(line.length()<i+100)?line.length()-i:100));
		line = this.getInput().readLine();
		if (line == null) return line;
		if (line.startsWith("A")) return line; 
		i=0;
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		return line;
	}
	public String doProcess7(JMap<String, Object> mRecords, String line) throws Exception {
		int i = 0;
		addFieldValue(A16SEC , line.substring(i,i+=3));
		addFieldValue(A16TYP , line.substring(i,i+=1));
		addFieldValue(A16NUM , line.substring(i,i+=2));
		addFieldValue(A16DTE , line.substring(i,i+=7));
		addFieldValue(A16HCC , line.substring(i,i+=2));
		addFieldValue(A16CTY , line.substring(i,i+=4));
		addFieldValue(A16STT , line.substring(i,i+=2));
		addFieldValue(A16OUT , line.substring(i,i+=5));
		addFieldValue(A16DAY , line.substring(i,i+=3));
		addFieldValue(A16NME , line.substring(i,i+=20));
		addFieldValue(A16RMS , line.substring(i));
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		if (line.startsWith("OD-")) {
			addFieldValue(A16ODN , line.substring(i));
			addFieldValue(A16ODI , line.substring(i,i+=3));
			addFieldValue(A16ODD , line.substring(i));
			line = this.getInput().readLine();
			if (line == null) return line;
			i=0;
		}
		if (line.startsWith("FF-")) {
			addFieldValue(A16FFN , line.substring(i,i+=66));
			addFieldValue(A16FFI , line.substring(i,i+=3));
			addFieldValue(A16FIP , line.substring(i,i+=2));
			addFieldValue(A16FTI , line.substring(i,i+=1));
			addFieldValue(A16FFD , line.substring(i,i+=60));
			line = this.getInput().readLine();
			if (line == null) return line;
			i=0;
		}
		if (line.startsWith("CF-")) {
			addFieldValue(A16CFN , line.substring(i,i+=35));
			addFieldValue(A16CFI , line.substring(i,i+=3));
			addFieldValue(A16CIP , line.substring(i,i+=2));
			addFieldValue(A16CFD , line.substring(i,i+=30));
			line = this.getInput().readLine();
			if (line == null) return line;
			i=0;
		}		
		if (line.startsWith("DP-")) {
			addFieldValue(A16DPN , line.substring(i));
			addFieldValue(A16DPI , line.substring(i,i+=3));
			addFieldValue(A16DPP , line.substring(i,i+=2));
			addFieldValue(A16DTI , line.substring(i,i+=1));
			addFieldValue(A16DAD , line.substring(i,i+=12));
			addFieldValue(A16DPD , line.substring(i,i+=12));
			addFieldValue(A16DPA , line.substring(i,i+=1));
			addFieldValue(A16DPF , line.substring(i,i+=60));
			line = this.getInput().readLine();
			if (line == null) return line;
			i=0;
		}
		if (line.startsWith("W-")) {
			addFieldValue(A16WAN , line.substring(i));
			addFieldValue(A16WAI , line.substring(i,i+=3));
			addFieldValue(A16WAP , line.substring(i,i+=2));
			addFieldValue(A16WAD , line.substring(i,i+=100));
			line = this.getInput().readLine();
			if (line == null) return line;
		}
		if (line.startsWith("A")) return line; 
		i=0;
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		return line;
	}
	public String doProcessB8(JMap<String, Object> mRecords, String line) throws Exception {
		int i = 0;
		addFieldValue(A16SEC , line.substring(i,i+=3));
		addFieldValue(A16TYP , line.substring(i,i+=1));
		addFieldValue(A16NUM , line.substring(i,i+=2));
		addFieldValue(A16DTE , line.substring(i,i+=7));
		addFieldValue(A16CAR , line.substring(i,i+=12));
		addFieldValue(A16STA , line.substring(i,i+=2));
		addFieldValue(A16CDT , line.substring(i,i+=5));
		addFieldValue(A16DAY , line.substring(i,i+=3));
		addFieldValue(A16CCC , line.substring(i,i+=4));
		addFieldValue(A16CVC , line.substring(i,i+=2));
		addFieldValue(A16CCT , line.substring(i,i+=4));
		addFieldValue(A16CNI , line.substring(i,i+=1));
		addFieldValue(A16PUP , line.substring(i,i+=26));
		addFieldValue(A16DOL , line.substring(i,i+=26));
		addFieldValue(A16PHN , line.substring(i,i+=40));
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		addFieldValue(A16ODN , line.substring(i,i+=(line.length()>=i+223?223:line.length()-i)));
		addFieldValue(A16ODI , line.substring(i,i+=(line.length()>=i+3?3:line.length()-i)));
		addFieldValue(A16ODD , line.substring(i,i+=(line.length()>=i+220?220:line.length()-i)));
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		addFieldValue(A16O11 , line.substring(i,i+=3));
		addFieldValue(A16ODI , line.substring(i));
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		addFieldValue(A16FFN , line.substring(i,i+=(line.length()>=i+66?66:line.length()-i)));
		addFieldValue(A16FFI , line.substring(i,i+=(line.length()>=i+3?3:line.length()-i)));
		addFieldValue(A16FIP , line.substring(i,i+=(line.length()>=i+2?2:line.length()-i)));
		addFieldValue(A16FTI , line.substring(i,i+=(line.length()>=i+1?1:line.length()-i)));
		addFieldValue(A16FFD , line.substring(i,i+=(line.length()>=i+60?60:line.length()-i)));
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		addFieldValue(A16CFN , line.substring(i,i+=(line.length()>=i+35?35:line.length()-i)));
		addFieldValue(A16CFI , line.substring(i,i+=(line.length()>=i+3?3:line.length()-i)));
		addFieldValue(A16CIP , line.substring(i,i+=(line.length()>=i+2?2:line.length()-i)));
		addFieldValue(A16CFD , line.substring(i,i+=(line.length()>=i+30?30:line.length()-i)));
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		addFieldValue(A16DPN , line.substring(i,i+=(line.length()>=i+94?94:line.length()-i)));
		addFieldValue(A16DPI , line.substring(i,i+=(line.length()>=i+3?3:line.length()-i)));
		addFieldValue(A16DPP , line.substring(i,i+=(line.length()>=i+2?2:line.length()-i)));
		addFieldValue(A16DTI , line.substring(i,i+=(line.length()>=i+1?1:line.length()-i)));
		addFieldValue(A16DAD , line.substring(i,i+=(line.length()>=i+12?12:line.length()-i)));
		addFieldValue(A16DCR , line.substring(i,i+=(line.length()>=i+3?3:line.length()-i)));
		addFieldValue(A16DPD , line.substring(i,i+=(line.length()>=i+12?12:line.length()-i)));
		addFieldValue(A16DPA , line.substring(i,i+=(line.length()>=i+1?1:line.length()-i)));
		addFieldValue(A16DPF , line.substring(i,i+=(line.length()>=i+60?60:line.length()-i)));
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		addFieldValue(A16WAN , line.substring(i,i+=(line.length()>=i+105?105:line.length()-i)));
		addFieldValue(A16WAI , line.substring(i,i+=(line.length()>=i+3?3:line.length()-i)));
		addFieldValue(A16WAP , line.substring(i,i+=(line.length()>=i+2?2:line.length()-i)));
		addFieldValue(A16WAD , line.substring(i,i+=(line.length()>=i+100?100:line.length()-i)));
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		addFieldValue(A16VCN , line.substring(i,i+=(line.length()>=i+100?100:line.length()-i)));
		addFieldValue(A16VCI , line.substring(i,i+=(line.length()>=i+3?3:line.length()-i)));
		addFieldValue(A16VIP , line.substring(i,i+=(line.length()>=i+2?2:line.length()-i)));
		addFieldValue(A16EVV , line.substring(i,i+=(line.length()>=i+20?20:line.length()-i)));
		addFieldValue(A16EVC , line.substring(i,i+=(line.length()>=i+25?25:line.length()-i)));
		addFieldValue(A16EBN , line.substring(i,i+=(line.length()>=i+20?20:line.length()-i)));
		addFieldValue(A16EAN , line.substring(i,i+=(line.length()>=i+30?30:line.length()-i)));
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		addFieldValue(A16CID , line.substring(i,i+=(line.length()>=i+3?3:line.length()-i)));
		addFieldValue(A16CII , line.substring(i,i+=(line.length()>=i+2?2:line.length()-i)));
		addFieldValue(A16CNO , line.substring(i,i+=(line.length()>=i+2?2:line.length()-i)));
		line = this.getInput().readLine();
		if (line == null) return line;
		if (line.startsWith("A")) return line; 
		i=0;
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		return line;
	}
	
	public String doProcess65(JMap<String, Object> mRecords, String line) throws Exception {
		int i = 0;
		addFieldValue(A16SEC , line.substring(i,i+=3));
		addFieldValue(A16TYP , line.substring(i,i+=1));
		addFieldValue(A16NUM , line.substring(i,i+=2));
		addFieldValue(A16DTE , line.substring(i,i+=7));
		addFieldValue(A16TTY , line.substring(i,i+=1));
		addFieldValue(A16VEN , line.substring(i,i+=2));
		addFieldValue(A16VNN , line.substring(i,i+=32));
		addFieldValue(A16CCC , line.substring(i,i+=4));
		addFieldValue(A16STT , line.substring(i,i+=2));
		addFieldValue(A16CDT , line.substring(i,i+=5));
		addFieldValue(A16DAY , line.substring(i,i+=3));
		addFieldValue(A16CNI , line.substring(i,i+=1));
		line = this.getInput().readLine();
		if (line == null) return line;
		if (line.trim().length() == 0) return line;
			i=0;
		addFieldValue(A16ODN , line.substring(i,i+=(line.length()<i+223)?line.length()-1:223));
		if (line.length() >= i + 3) { // Verifica que haya al menos 3 caracteres disponibles
	    addFieldValue(A16ODI, line.substring(i, i + 3));
			i += 3;
		}

		if (line.length() >= i + 220) { // Verifica que haya al menos 220 caracteres
																		// disponibles
			addFieldValue(A16ODD, line.substring(i, i + 220));
			i += 220;
		}
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		if (line.length() >= i + 66) addFieldValue(A16FFN , line.substring(i,i+=66));
		if (line.length() >= i + 3) addFieldValue(A16FFI , line.substring(i,i+=3));
		if (line.length() >= i + 2) addFieldValue(A16FIP , line.substring(i,i+=2));
		if (line.length() >= i + 1) addFieldValue(A16FTI , line.substring(i,i+=1));
		if (line.length() >= i + 60) addFieldValue(A16FFD , line.substring(i,i+=60));
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		if (line.length() >= i + 35) addFieldValue(A16CFN , line.substring(i,i+=35));
		if (line.length() >= i + 3) addFieldValue(A16CFI , line.substring(i,i+=3));
		if (line.length() >= i + 2) addFieldValue(A16CIP , line.substring(i,i+=2));
		if (line.length() >= i + 30) addFieldValue(A16CFD , line.substring(i,i+=30));
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		if (line.length() >= i + 94) addFieldValue(A16DPN , line.substring(i,i+=94));
		if (line.length() >= i + 3) addFieldValue(A16DPI , line.substring(i,i+=3));
		if (line.length() >= i + 2) addFieldValue(A16DPP , line.substring(i,i+=2));
		if (line.length() >= i + 1) addFieldValue(A16DTI , line.substring(i,i+=1));
		if (line.length() >= i + 12) addFieldValue(A16DAD , line.substring(i,i+=12));
		if (line.length() >= i + 3) addFieldValue(A16DCR , line.substring(i,i+=3));
		if (line.length() >= i + 12) addFieldValue(A16DPD , line.substring(i,i+=12));
		if (line.length() >= i + 1) addFieldValue(A16DPA , line.substring(i,i+=1));
		if (line.length() >= i + 60) addFieldValue(A16DPF , line.substring(i,i+=60));
		if (line.length() >= i + 66) line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		if (line.length() >= i + 105) addFieldValue(A16WAN , line.substring(i,i+=105));
		if (line.length() >= i + 3) addFieldValue(A16WAI , line.substring(i,i+=3));
		if (line.length() >= i + 2) addFieldValue(A16WAP , line.substring(i,i+=2));
		if (line.length() >= i + 100) addFieldValue(A16WAD , line.substring(i,i+=100));
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		if (line.length() >= i + 105) addFieldValue(A16UID , line.substring(i,i+=105));
		if (line.length() >= i + 3) addFieldValue(A16CII , line.substring(i,i+=3));
		if (line.length() >= i + 2) addFieldValue(A16UNO , line.substring(i,i+=2));
		line = this.getInput().readLine();
		if (line == null) return line;
		if (line.startsWith("A")) return line; 
		i=0;
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		return line;
	}

	public String doProcessUZ(JMap<String, Object> mRecords, String line) throws Exception {
		int i = 0;
		addFieldValue(A16SEC , line.substring(i,i+=3));
		addFieldValue(A16DPI , line.substring(i,i+=3));
		addFieldValue(A16DPP , line.substring(i,i+=2));
		addFieldValue(A16DIN , line.substring(i,i+=1));
		if (line.length()>=i+5) addFieldValue(A16DTE , line.substring(i,i+=5));
		if (line.length()>=i+3)addFieldValue(A16DCR , line.substring(i,i+=3));
		if (line.length()>=i+12)addFieldValue(A16DPD , line.substring(i,i+=12));
		if (line.length()>=i+1)addFieldValue(A16DPA , line.substring(i,i+=1));
		if (line.length()>i+60) 
			addFieldValue(A16DPF , line.substring(i,i+=60));
		else
			addFieldValue(A16DPF , line.substring(i));
		line = this.getInput().readLine();
		if (line == null) return line;
		if (line.startsWith("A")) return line; 
		i=0;
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		return line;
	}

	public String doProcess9(JMap<String, Object> mRecords, String line) throws Exception {
		int i = 0;
		addFieldValue(A16SEC , line.substring(i,i+=3));
		addFieldValue(A16TYP , line.substring(i,i+=1));
		addFieldValue(A16NUM , line.substring(i,i+=2));
		addFieldValue(A16BKI , line.substring(i,i+=1));
		addFieldValue(A16AIR , line.substring(i,i+=2));
		addFieldValue(A16STS , line.substring(i,i+=2));
		addFieldValue(A16REQ , line.substring(i,i+=2));
		addFieldValue(A16PLC , line.substring(i,i+=3));
		addFieldValue(A16DAT , line.substring(i,i+=5));
		addFieldValue(A16RFI , line.substring(i,i+=1));
		addFieldValue(A16RFC , line.substring(i,i+=3));
		addFieldValue(A16FOR , line.substring(i,i+=30));
		addFieldValue(A16PNN , line.substring(i,i+=2));
		addFieldValue(A16NAM , line.substring(i,i+=55));
		addFieldValue(A16EMD , line.substring(i,i+=15));
		addFieldValue(A16CEA , line.substring(i,i+=3));
		addFieldValue(A16EAM , line.substring(i,i+=12));
		addFieldValue(A16CTX , line.substring(i,i+=3));
		addFieldValue(A16TAM , line.substring(i,i+=8));
		addFieldValue(A16CTA , line.substring(i,i+=3));
		addFieldValue(A16TLA , line.substring(i,i+=12));
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		if (line.trim().equals("")) return line;
		addFieldValue(A16FXI , line.substring(i,i+=3));
		addFieldValue(A16FTX , line.substring(i));

		return line;
	}
}
