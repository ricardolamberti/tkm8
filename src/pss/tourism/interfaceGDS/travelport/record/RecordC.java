package pss.tourism.interfaceGDS.travelport.record;

import pss.core.tools.collections.JMap;

public class RecordC extends TravelPortRecord {

	private String EXCHANGE_TYPE="EXCHANGE_TYPE";
	private String DOCUMENT_TYPE="DOCUMENT_TYPE";
	private String AIRLINE_NUMBER="AIRLINE_NUMBER";
	private String TICKET_NUMBER="TICKET_NUMBER";

	public RecordC() {
		ID = CRECORD;
	}

	public String doProcess(JMap<String, Object> mRecords, String line)
			throws Exception {
		int i = 0;
		if (line.substring(i += 1).length() < 2)
			return line;


		addFieldValue(EXCHANGE_TYPE , line.substring(i, i += 1));
		addFieldValue(DOCUMENT_TYPE , line.substring(i, i += 1));
		addFieldValue(AIRLINE_NUMBER , line.substring(i, i += 3));
		addFieldValue(TICKET_NUMBER , line.substring(i, i += 10));

		
		return line;
	}
	
	public String getExchangeType() {
		return getFieldValue(EXCHANGE_TYPE);
	}
	
	public String getDocumentType() {
		return getFieldValue(DOCUMENT_TYPE);
	}
	
	public String getAirline() {
		return getFieldValue(AIRLINE_NUMBER);
	}
	
	public String getTicketNumber() {
		return getFieldValue(TICKET_NUMBER);
	}
	
	
	

}
