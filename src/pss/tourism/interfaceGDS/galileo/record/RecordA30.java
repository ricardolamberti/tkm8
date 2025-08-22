package pss.tourism.interfaceGDS.galileo.record;

import pss.core.tools.collections.JMap;

public class RecordA30 extends GalileoRecord {

	public static final String A30SEC = "A30SEC";
	public static final String A30PAX = "A30PAX";
	public static final String A30SEG = "A30SEG";
	public static final String A30CPN = "A30CPN";
	public static final String A30EMD = "A30EMD";
	public static final String A30AIR = "A30AIR";
	public static final String A30OCC = "A30OCC";
	public static final String A30DAT = "A30DAT";
	public static final String A30REQ = "A30REQ";
	public static final String A30RFC = "A30RFC";
	public static final String A30FOR = "A30FOR";
	public static final String A30CUN = "A30CUN";
	public static final String A30UNT = "A30UNT";
	public static final String A30RAT = "A30RAT";
	public static final String A30QAL = "A30QAL";
	public static final String A30CBA = "A30CBA";
	public static final String A30BAM = "A30BAM";
	public static final String A30CEA = "A30CEA";
	public static final String A30EAM = "A30EAM";
	public static final String A30CTX = "A30CTX";
	public static final String A30TX1 = "A30TX1";
	public static final String A30TC1 = "A30TC1";
	public static final String A30TX2 = "A30TX2";
	public static final String A30TC2 = "A30TC2";
	public static final String A30TX3 = "A30TX3";
	public static final String A30TC3 = "A30TC3";
	public static final String A30CTA = "A30CTA";
	public static final String A30TLA = "A30TLA";
	public static final String A30ATK = "A30ATK";
	public static final String A30C01 = "A30C01";
	public static final String A30ECD = "A30ECD";
	public static final String A30ECI = "A30ECI";
	public static final String A30ECP = "A30ECP";
	public static final String A30C02 = "A30C02";


	public RecordA30() {
		ID = A30RECORD;
	}

	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {

		int i = 0;

		if (!line.startsWith(ID))
			return line;

		addFieldValue(A30SEC, line.substring(i, i += 3));
		addFieldValue(A30PAX, line.substring(i, i += 2));
		addFieldValue(A30SEG, line.substring(i, i += 2));
		addFieldValue(A30CPN, line.substring(i, i += 1));
		addFieldValue(A30EMD, line.substring(i, i += 13));

		addFieldValue(A30AIR, line.substring(i, i += 2));
		addFieldValue(A30OCC, line.substring(i, i += 3));
		addFieldValue(A30DAT, line.substring(i, i += 5));
		addFieldValue(A30REQ, line.substring(i, i += 2));
		addFieldValue(A30RFC, line.substring(i, i += 3));
		addFieldValue(A30FOR, line.substring(i, i += 30));
		addFieldValue(A30CUN, line.substring(i, i += 3));
		addFieldValue(A30UNT, line.substring(i, i += 3));
		addFieldValue(A30RAT, line.substring(i, i += 12));
		addFieldValue(A30QAL, line.substring(i, i += 1));
		addFieldValue(A30CBA, line.substring(i, i += 3));
		addFieldValue(A30BAM, line.substring(i, i += 12));
		addFieldValue(A30CEA, line.substring(i, i += 3));
		addFieldValue(A30EAM, line.substring(i, i += 12));
		addFieldValue(A30CTX, line.substring(i, i += 3));
		addFieldValue(A30TX1, line.substring(i, i += 8));
		addFieldValue(A30TC1, line.substring(i, i += 2));
		addFieldValue(A30TX2, line.substring(i, i += 8));
		addFieldValue(A30TC2, line.substring(i, i += 2));
		addFieldValue(A30TX3, line.substring(i, i += 8));
		addFieldValue(A30TC3, line.substring(i, i += 2));
		addFieldValue(A30CTA, line.substring(i, i += 3));
		addFieldValue(A30TLA, line.substring(i, i += 12));
		addFieldValue(A30ATK, line.substring(i, i += 13));

		this.getInput().mark(2000);
		line = this.getInput().readLine();
		if (line.startsWith("A30")) {
			this.getInput().reset();
			return line;
		}
		if (line == null)
			return line;
		i = 0;
		if (line.trim().equals(""))
			return line;
		if (line.length()<83)
			return line;
		addFieldValue(A30ECD, line.substring(i, i += 83));
		addFieldValue(A30ECI, line.substring(i, i += 3));
		addFieldValue(A30ECP, line.substring(i, i += 80));

		

		return line;
	}


}
