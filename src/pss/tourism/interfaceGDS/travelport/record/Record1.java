package pss.tourism.interfaceGDS.travelport.record;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;

public class Record1 extends TravelPortRecord {

	public static final String OFFICE = "OFFICE";

	public static final String IATA = "IATA";

	public static final String LOCATOR = "LOCATOR";

	public static final String VERSION = "VERSION";

	public static final String INVOICE = "INVOICE";

	public static final String NUMBER_OF_ITINERARY_CHANGES = "NUMBER_OF_ITINERARY_CHANGES";

	private String DATE_CHANGE = "DATE_CHANGE";

	private String TIME_CHANGE = "TIME_CHANGE";
	private String DUPLICATED = "DUPLICATED";

	public Record1() {
		ID = ONE;
	}

	// ==========================================================================
	// AMD Record - Retrans && PNRLocator
	// ==========================================================================
	public String doProcess(JMap<String, Object> mRecords, String line)
			throws Exception {

		int i = 0;

		if (line.substring(i += 2).length() < 2)
			return line;

		JStringTokenizer tok = JCollectionFactory.createStringTokenizer(line
				.substring(i), TravelPortRecord.FIELD_SEPARATOR);
		tok.skipEmptyTokens(false);
		
		int count = tok.countTokens();

		addFieldValue(VERSION, tok.nextToken()); count--;
		addFieldValue(LOCATOR, tok.nextToken().substring(3)); count--;
		String invoice = tok.nextToken(); count--;
		String nc="";
		if (invoice.startsWith("IN") ) {
			addFieldValue(INVOICE, invoice.substring(3)); 
			nc = tok.nextToken().substring(3); count--;
		} else {
			nc = invoice.substring(3);
		
		}
		
		addFieldValue(NUMBER_OF_ITINERARY_CHANGES, nc);
		if (nc.equals("0") == false) {
			String datetime = tok.nextToken(); count--;
			addFieldValue(DATE_CHANGE, datetime.substring(3, 3 + 7));
			addFieldValue(TIME_CHANGE, datetime.substring(10+1));
		}
		if ( count > 0 ) {
			addFieldValue(DUPLICATED, tok.nextToken()); count--;
		}
		

		return line;
	}

	public String getVersion() {
		return getFieldValue(VERSION);
	}

	public String getPNRLocator() {
		return getFieldValue(LOCATOR);
	}

	public boolean isDuplicated() {
		return ( getFieldValue(DUPLICATED) != null );
	}


}
