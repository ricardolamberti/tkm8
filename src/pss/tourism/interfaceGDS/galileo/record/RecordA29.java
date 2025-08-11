package pss.tourism.interfaceGDS.galileo.record;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

public class RecordA29   extends GalileoRecord {
	
	public static final String A29SEC = "A29SEC";
	public static final String A29PAX = "A29PAX";
	public static final String A29SEG = "A29SEG";
	public static final String A29SVC = "A29SVC";
	public static final String A29BKI = "A29BKI";
	public static final String A29EID = "A29EID";
	public static final String A29RFI = "A29RFI";
	public static final String A29NAM = "A29NAM";
	public static final String A29EMD = "A29EMD";
	public static final String A29CNJ = "A29CNJ";
	public static final String A29VCR = "A29VCR";
	public static final String A29CBA = "A29CBA";
	public static final String A29BAM = "A29BAM";
	public static final String A29CEA = "A29CEA";
	public static final String A29EAM = "A29EAM";
	public static final String A29CTX = "A29CTX";
	public static final String A29TAM = "A29TAM";
	public static final String A29CTA = "A29CTA";
	public static final String A29TLA = "A29TLA";
	public static final String A29BSR = "A29BSR";
	public static final String A29TYP = "A29TYP";
	public static final String A29AMT = "A29AMT";
	public static final String A29ACI = "A29ACI";
	public static final String A29APC = "A29APC";
	public static final String A29C01 = "A29C01";
	public static final String A29EDS = "A29EDS";
	public static final String A29EDI = "A29EDI";
	public static final String A29EM2 = "A29EM2";
	public static final String A29C02 = "A29C02";


	public RecordA29() {
		ID = A29RECORD;
	}
	
	public static JMap<String, EMD> mEMDs = JCollectionFactory.createMap();

	public static void resetEMDMap() {
		mEMDs = JCollectionFactory.createMap();
	}

	public static boolean isEMD() {
		return (mEMDs.isEmpty() == false);
	}

	public static int countEMDs() {
		return (mEMDs.size());
	}

	public static Object[] getIds() {
		return (mEMDs.getKeys());
	}

	public static String getEMDTicket(String id) {
		return mEMDs.getElement(id).ticket;
	}

	public static String getEMDAirline(String id) {
		return mEMDs.getElement(id).airline;
	}

	public class EMD {
		String ticket;
		String airline;
	}


	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {

		int i = 0;

		if (!line.startsWith(ID))
			return line;

		addFieldValue(A29SEC , line.substring(i,i+=3));
		addFieldValue(A29PAX , line.substring(i,i+=2));
		addFieldValue(A29SEG , line.substring(i,i+=2));
		addFieldValue(A29SVC , line.substring(i,i+=4));
		addFieldValue(A29BKI , line.substring(i,i+=1));
		addFieldValue(A29EID , line.substring(i,i+=1));
		addFieldValue(A29RFI , line.substring(i,i+=1));
		addFieldValue(A29NAM , line.substring(i,i+=55));
		addFieldValue(A29EMD , line.substring(i,i+=13));
		addFieldValue(A29CNJ , line.substring(i,i+=13));
		addFieldValue(A29VCR , line.substring(i,i+=2));
		addFieldValue(A29CBA , line.substring(i,i+=3));
		addFieldValue(A29BAM , line.substring(i,i+=12));
		addFieldValue(A29CEA , line.substring(i,i+=3));
		addFieldValue(A29EAM , line.substring(i,i+=12));
		addFieldValue(A29CTX , line.substring(i,i+=3));
		addFieldValue(A29TAM , line.substring(i,i+=12));
		addFieldValue(A29CTA , line.substring(i,i+=3));
		addFieldValue(A29TLA , line.substring(i,i+=12));
		addFieldValue(A29BSR , line.substring(i,i+=13));
		addFieldValue(A29TYP , line.substring(i,i+=2));
		addFieldValue(A29AMT , line.substring(i,i+=12));
		addFieldValue(A29ACI , line.substring(i,i+=1));
		addFieldValue(A29APC , line.substring(i,i+=8));
		
		EMD e = new EMD();
		e.ticket = getEMDTicket();
		e.airline = getAirline();

		mEMDs.addElement(e.ticket, e);

		
		line = this.getInput().readLine();
		if (line == null) return line;
		i=0;
		if (line.trim().equals("")) return line;
		if (line.length()<243) {
			addFieldValue(A29EDS , line.substring(i));
			return line;
		}
		addFieldValue(A29EDS , line.substring(i,i+=243));
		addFieldValue(A29EDI , line.substring(i,i+=3));
		addFieldValue(A29EM2 , line.substring(i,i+=240));

		return line;
	}
	
	public String getAirline() {
		return getFieldValue(A29VCR);
	}

	public String getBaseCurrency() {
		return getFieldValue(A29CBA);
	}

	public String getTaxCurrency() {
		return getFieldValue(A29CTX);
	}

	public String getTaxCode1() {
		return getFieldValue(A29CTA);
	}

	public double getTaxAmount1() {
		try {
			return Double.parseDouble(getFieldValue(A29TAM));
		} catch (Exception ee) {
			return 0;
		}
	}



	public double getBaseAmount() {
		try {
			return Double.parseDouble(getFieldValue(A29BAM));
		} catch (Exception ee) {
			return 0;
		}

	}

	public String getTotalCurrency() {
		return getFieldValue(A29CTA);
	}

	public double getTotalAmount() {
		try {
			return Double.parseDouble(getFieldValue(A29TLA));
		} catch (Exception ee) {
			return 0;
		}

	}

	public String getEquivCurrency() {
		return getFieldValue(A29CEA);
	}

	public double getEquivAmount() {
		try {
			return Double.parseDouble(getFieldValue(A29EAM));
		} catch (Exception ee) {
			return 0;
		}

	}

	public String getEMDTicket() {
		return getFieldValue(A29EMD).trim().substring(3);
	}

}
