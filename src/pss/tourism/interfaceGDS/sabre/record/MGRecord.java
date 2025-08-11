package pss.tourism.interfaceGDS.sabre.record;

import pss.core.tools.JTools;
import pss.core.tools.collections.JMap;

public class MGRecord extends SabreRecord {

	public static final String MESSAGE_ID = "IUGMID"; //
	public static final String INTERFACE_NAME_ITEM_NUMBER = "IUGPNO";
	public static final String PASSENGER_TYPE = "IUGPTY";
	public static final String CREATION_LOCAL_DATE = "IUGDTE";
	public static final String CREATION_LOCAL_TIME = "IUGTME";
	public static final String CRS = "IUGGDS";
	public static final String VALIDATION_CARRIER_CODE = "IUGVAL";
	public static final String VALIDATION_CARRIER_CODE_SPARE = "IUGVAS";
	public static final String EMD_DOCUMENT_PRE = "IUGEMD1";
	public static final String EMD_DOCUMENT = "IUGEMD";

	public MGRecord() {
		ID = SabreRecord.MG;
	}

	public String getPaxId() {
		if (!JTools.isNumberPure(this.getFieldValue(INTERFACE_NAME_ITEM_NUMBER))) return null;
		return Integer.parseInt(this.getFieldValue(INTERFACE_NAME_ITEM_NUMBER)) + "";
	}
	public String getEMDId() {
		return this.getFieldValue(EMD_DOCUMENT) + "";
	}

	// ==========================================================================
	// M2 Record - Passenger Ticket Data Record
	// ==========================================================================
	// This record contains passenger ticket data. This data is needed for the
	// printing
	// of the ticket. There is one M2 record per passenger ticketed.
	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {

		int i = 2;
		addFieldValue(MESSAGE_ID, ID); // MESSAGE ID
		if (line.length()>=i+2 && JTools.isNumberPure(line.substring(i, i + 2))) {
			addFieldValue(INTERFACE_NAME_ITEM_NUMBER, Integer.parseInt(line.substring(i, i += 2)) + "");
			addFieldValue(PASSENGER_TYPE, line.substring(i, i += 3));
			addFieldValue(CREATION_LOCAL_DATE, line.substring(i, i += 9));
			addFieldValue(CREATION_LOCAL_TIME, line.substring(i, i += 4));
			addFieldValue(CRS, line.substring(i, i += 2));
			addFieldValue(VALIDATION_CARRIER_CODE, line.substring(i, i += 2));
			addFieldValue(VALIDATION_CARRIER_CODE_SPARE, line.substring(i, i += 1));
			addFieldValue(EMD_DOCUMENT_PRE, line.substring(i, i += 3));
			addFieldValue(EMD_DOCUMENT, line.substring(i, i += 10));
		}
		while (true) {
			this.getInput().mark(10000);
			line = this.getInput().readLine();
			if (line == null)
				return line;
			if (line.startsWith(SabreRecord.MX) || line.startsWith(SabreRecord.MG)) {
				this.getInput().reset();
				return line;
			}

		}
	}

}
