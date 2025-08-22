package pss.tourism.interfaceGDS.galileo.record;

import pss.core.tools.collections.JMap;

public class RecordHeader extends GalileoRecord {
	
	public static final String T50BID = "T50BID";
	public static final String T50TRC = "T50TRC";
	public static final String T50SPC = "T50SPC";
	public static final String T50MIR = "T50MIR";
	public static final String T50SZE = "T50SZE";
	public static final String T50SEQ = "T50SEQ";
	public static final String T50CRE = "T50CRE";
	public static final String T50DTE = "T50DTE";
	public static final String T50TME = "T50TME";
	public static final String T50ISS = "T50ISS";
	public static final String T50ISC = "T50ISC";
	public static final String T50ISA = "T50ISA";
	public static final String T50ISN = "T50ISN";
	public static final String T50DFT = "T50DFT";
	public static final String T50LNI = "T50LNI";
	public static final String T50INP = "T50INP";
	public static final String T50OUT = "T50OUT";
	public static final String T50CO1 = "T50CO1";
	public static final String T50BPC = "T50BPC";
	public static final String T50TPC = "T50TPC";
	public static final String T50AAN = "T50AAN";
	public static final String T50RCL = "T50RCL";
	public static final String T50ORL = "T50ORL";
	public static final String T50OCC = "T50OCC";
	public static final String T50OAM = "T50OAM";
	public static final String T50AGS = "T50AGS";
	public static final String T50SBI = "T50SBI";
	public static final String T50AGT = "T50AGT";
	public static final String T50SIN = "T50SIN";
	public static final String T50DTY = "T50DTY";
	public static final String T50PNR = "T50PNR";
	public static final String T50EHT = "T50EHT";
	public static final String T50DTL = "T50DTL";
	public static final String T50NMC = "T50NMC";
	public static final String T50C02 = "T50C02";
	public static final String T50CUR = "T50CUR";
	public static final String T50FAR = "T50FAR";
	public static final String T50DML = "T50DML";
	public static final String T50CUR2 ="T50CUR2";
	public static final String T501TX = "T501TX";
	public static final String T501TC = "T501TC";
	public static final String T502TX = "T502TX";
	public static final String T502TC = "T502TC";
	public static final String T503TX = "T503TX";
	public static final String T503TC = "T503TC";
	public static final String T504TX = "T504TX";
	public static final String T504TC = "T504TC";
	public static final String T505TX = "T505TX";
	public static final String T505TC = "T505TC";
	public static final String T50CMM = "T50CMM";
	public static final String T50COM = "T50COM";
	public static final String T50RTE = "T50RTE";
	public static final String T50ITC = "T50ITC";
	public static final String T50C03 = "T50C03";
	public static final String T50IND = "T50IND";
	public static final String T50IN1 = "T50IN1";
	public static final String T50IN2 = "T50IN2";
	public static final String T50IN3 = "T50IN3";
	public static final String T50IN4 = "T50IN4";
	public static final String T50IN5 = "T50IN5";
	public static final String T50IN6 = "T50IN6";
	public static final String T50IN7 = "T50IN7";
	public static final String T50IN8 = "T50IN8";
	public static final String T50IN9 = "T50IN9";
	public static final String T50IN10 ="T50IN10";
	public static final String T50IN11 ="T50IN11";
	public static final String T50IN12 ="T50IN12";
	public static final String T50IN13 ="T50IN13";
	public static final String T50IN14 ="T50IN14";
	public static final String T50IN15 ="T50IN15";
	public static final String T50IN16 ="T50IN16";
	public static final String T50IN17 ="T50IN17";
	public static final String T50PCC = "T50PCC";
	public static final String T50ISO = "T50ISO";
	public static final String T50DMI = "T50DMI";
	public static final String T50DST = "T50DST";
	public static final String T50DPC = "T50DPC";
	public static final String T50DSQ = "T50DSQ";
	public static final String T50DLN = "T50DLN";
	public static final String T50SMI = "T50SMI";
	public static final String T50SPC2 ="T50SPC2";
	public static final String T50SHP = "T50SHP";
	public static final String T50C04 = "T50C04";
	public static final String T50CNT = "T50CNT";
	public static final String T50CRN = "T50CRN";
	public static final String T50CPN = "T50CPN";
	public static final String T50PGN = "T50PGN";
	public static final String T50FFN = "T50FFN";
	public static final String T50ARN = "T50ARN";
	public static final String T50WLN = "T50WLN";
	public static final String T50SDN = "T50SDN";
	public static final String T50FBN = "T50FBN";
	public static final String T50EXC = "T50EXC";
	public static final String T50PYN = "T50PYN";
	public static final String T50PHN = "T50PHN";
	public static final String T50ADN = "T50ADN";
	public static final String T50MSN = "T50MSN";
	public static final String T50RRN = "T50RRN";
	public static final String T50AXN = "T50AXN";
	public static final String T50LSN = "T50LSN";
	public static final String T50C05 = "T50C05";
	public static final String T50C06 = "T50C06";


	public RecordHeader() {
		ID = "T5";
	}

	public String doProcess(JMap<String, Object> mRecords, String line)
			throws Exception {

		int i = 0;

		if (!line.startsWith("T5"))
			return line;

		addFieldValue(T50BID , line.substring(i,i+=2));
		addFieldValue(T50TRC , line.substring(i,i+=2));
		addFieldValue(T50SPC , line.substring(i,i+=4));
		addFieldValue(T50MIR , line.substring(i,i+=2));
		addFieldValue(T50SZE , line.substring(i,i+=5));
		addFieldValue(T50SEQ , line.substring(i,i+=5));
		addFieldValue(T50CRE , line.substring(i,i+12));
		addFieldValue(T50DTE , line.substring(i,i+=7));
		addFieldValue(T50TME , line.substring(i,i+=5));
		addFieldValue(T50ISS , line.substring(i,i+29));
		addFieldValue(T50ISC , line.substring(i,i+=2));
		addFieldValue(T50ISA , line.substring(i,i+=3));
		addFieldValue(T50ISN , line.substring(i,i+=24));
		addFieldValue(T50DFT , line.substring(i,i+=7));
		addFieldValue(T50LNI , line.substring(i,i+12));
		addFieldValue(T50INP , line.substring(i,i+=6));
		addFieldValue(T50OUT , line.substring(i,i+=6));
		
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		
		addFieldValue(T50BPC , line.substring(i,i+=4));
		addFieldValue(T50TPC , line.substring(i,i+=4));
		addFieldValue(T50AAN , line.substring(i,i+=9));
		addFieldValue(T50RCL , line.substring(i,i+=6));
		addFieldValue(T50ORL , line.substring(i,i+=6));
		addFieldValue(T50OCC , line.substring(i,i+=2));
		addFieldValue(T50OAM , line.substring(i,i+=1));
		addFieldValue(T50AGS , line.substring(i,i+=6));
		addFieldValue(T50SBI , line.substring(i,i+=1));
		addFieldValue(T50AGT , line.substring(i,i+4));
		addFieldValue(T50SIN , line.substring(i,i+=2));
		addFieldValue(T50DTY , line.substring(i,i+=2));
		addFieldValue(T50PNR , line.substring(i,i+=7));
		addFieldValue(T50EHT , line.substring(i,i+=3));
		addFieldValue(T50DTL , line.substring(i,i+=7));
		addFieldValue(T50NMC , line.substring(i,i+=3));
		
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		
		addFieldValue(T50CUR , line.substring(i,i+=3));
		addFieldValue(T50FAR , line.substring(i,i+=12));
		addFieldValue(T50DML , line.substring(i,i+=1));
		addFieldValue(T50CUR2, line.substring(i,i+=3));
		addFieldValue(T501TX , line.substring(i,i+=8));
		addFieldValue(T501TC , line.substring(i,i+=2));
		addFieldValue(T502TX , line.substring(i,i+=8));
		addFieldValue(T502TC , line.substring(i,i+=2));
		addFieldValue(T503TX , line.substring(i,i+=8));
		addFieldValue(T503TC , line.substring(i,i+=2));
		addFieldValue(T504TX , line.substring(i,i+=8));
		addFieldValue(T504TC , line.substring(i,i+=2));
		addFieldValue(T505TX , line.substring(i,i+=8));
		addFieldValue(T505TC , line.substring(i,i+=2));
		addFieldValue(T50CMM , line.substring(i,i+12));
		addFieldValue(T50COM , line.substring(i,i+=8));
		addFieldValue(T50RTE , line.substring(i,i+=4));
		addFieldValue(T50ITC , line.substring(i,i+=15));
	
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		
		addFieldValue(T50IND , line.substring(i,i+23));
		addFieldValue(T50IN1 , line.substring(i,i+=1));
		addFieldValue(T50IN2 , line.substring(i,i+=1));
		addFieldValue(T50IN3 , line.substring(i,i+=1));
		addFieldValue(T50IN4 , line.substring(i,i+=1));
		addFieldValue(T50IN5 , line.substring(i,i+=1));
		addFieldValue(T50IN6 , line.substring(i,i+=1));
		addFieldValue(T50IN7 , line.substring(i,i+=1));
		addFieldValue(T50IN8 , line.substring(i,i+=1));
		addFieldValue(T50IN9 , line.substring(i,i+=1));
		addFieldValue(T50IN10, line.substring(i,i+=1));
		addFieldValue(T50IN11, line.substring(i,i+=1));
		addFieldValue(T50IN12, line.substring(i,i+=1));
		addFieldValue(T50IN13, line.substring(i,i+=1));
		addFieldValue(T50IN14, line.substring(i,i+=1));
		addFieldValue(T50IN15, line.substring(i,i+=1));
		addFieldValue(T50IN16, line.substring(i,i+=1));
		addFieldValue(T50IN17, line.substring(i,i+=1));
		addFieldValue(T50PCC , line.substring(i,i+=3));
		addFieldValue(T50ISO , line.substring(i,i+=3));
		addFieldValue(T50DMI , line.substring(i,i+=2));
		addFieldValue(T50DST , line.substring(i,i+=1));
		addFieldValue(T50DPC , line.substring(i,i+=4));
		addFieldValue(T50DSQ , line.substring(i,i+=5));
		addFieldValue(T50DLN , line.substring(i,i+=6));
		addFieldValue(T50SMI , line.substring(i,i+=2));
		addFieldValue(T50SPC2, line.substring(i,i+=4));
		addFieldValue(T50SHP , line.substring(i,i+=4));
		
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		
		addFieldValue(T50CNT , line.substring(i,i+48));
		addFieldValue(T50CRN , line.substring(i,i+=3));
		addFieldValue(T50CPN , line.substring(i,i+=3));
		addFieldValue(T50PGN , line.substring(i,i+=3));
		addFieldValue(T50FFN , line.substring(i,i+=3));
		addFieldValue(T50ARN , line.substring(i,i+=3));
		addFieldValue(T50WLN , line.substring(i,i+=3));
		addFieldValue(T50SDN , line.substring(i,i+=3));
		addFieldValue(T50FBN , line.substring(i,i+=3));
		addFieldValue(T50EXC , line.substring(i,i+=3));
		addFieldValue(T50PYN , line.substring(i,i+=3));
		addFieldValue(T50PHN , line.substring(i,i+=3));
		addFieldValue(T50ADN , line.substring(i,i+=3));
		addFieldValue(T50MSN , line.substring(i,i+=3));
		addFieldValue(T50RRN , line.substring(i,i+=3));
		addFieldValue(T50AXN , line.substring(i,i+=3));
		addFieldValue(T50LSN , line.substring(i,i+=3));

		line = this.getInput().readLine();
		return line;
		
	}





}
