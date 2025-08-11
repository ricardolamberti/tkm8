package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.collections.JMap;

public class TRecord  extends AmadeusRecord {

	private String TICKET_TYPE = "TICKET_TYPE";
	private String AIRLINE_NUMBER = "AIRLINE_NUMBER";
	private String TICKET_NUMBER = "TICKET_NUMBER";
	
	public static String TOUR_CODE="TOUR_CODE";
	public static String AITAN="AITAN";
	public static String ENDORSEMENT="ENDORSEMENT";
	public TRecord() {
		ID = T;
	}
	// ==========================================================================
	// Itinerary Record
	// ==========================================================================
	public String doProcess(JMap<String, Object> mRecords, String line)
			throws Exception {

		int i = 2;
		if (line.length()<10)
			return line;
		addFieldValue(TICKET_TYPE , line.substring(i, i += 1));
		addFieldValue(AIRLINE_NUMBER , line.substring(i, i += 3));
		addFieldValue(TICKET_NUMBER , line.substring(i + 1, i += 11));


		return line;
	}

	public String getTicketNumber() {
	
		return getFieldValue(TICKET_NUMBER);
	}

}