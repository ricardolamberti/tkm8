package pss.tourism.interfaceGDS.sabre.record;

import pss.core.tools.PssLogger;
import pss.core.tools.collections.JMap;

public class M0VoidRecord extends SabreRecord {

	public static final String RECORD_IDENTIFIER = "IU0MID"; // 2
	public static final String TRANSACTION_TYPE = "IU0TYP"; // 1
	public static final String INTERFACE_VERSION_NUMBER = "IU0VER"; // 2
	public static final String CUSTOMER = "IU0DKN"; // 10
	public static final String CUSTOMER_BRANCH = "IU0DKB"; // 3
	public static final String CUSTOMER_NUMBER = "IU0DKC"; // 7
	public static final String TICKET_NUMBER = "IU0TKI"; // 11
	public static final String NUMBER_OF_TICKETS = "IU0CNT"; // 2
	public static final String PNR_LOCATOR = "IU0PR1"; // 8
	public static final String DUPLICATE_VOID_INDICATOR = "IU0DUP"; // 1
	public static final String AUTHORIZATION_CODE_FOR_VOIDS = "IU0SAC"; // 15
	public static final String CARRIAGE_RETURN = "IU0CR2"; // 1

	public M0VoidRecord() {
		ID = SabreRecord.M0VOID;
	}

	// ==========================================================================
	// M05 Void Record - Control Void Message Data
	// ==========================================================================
	// This record is created from invoice and/or itinerary data.
	// This record stores constant data and general information data from the PNR
	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {

		PssLogger.logDebug("Parsing Void M0 begin ...........................................................");
		int i = 0;
		addFieldValue(RECORD_IDENTIFIER, line.substring(i, i += 2)); // RECORD
																																	// IDENTIFIER
																																	// 2
		addFieldValue(TRANSACTION_TYPE, line.substring(i, i += 1)); // TRANSACTION
																																// TYPE 1
		addFieldValue(INTERFACE_VERSION_NUMBER, line.substring(i, i += 2)); // INTERFACE
																																				// VERSION
																																				// NUMBER
																																				// 2
		addFieldValue(CUSTOMER, line.substring(i, i += 10)); // CUSTOMER NUMBER 10
		addFieldValue(CUSTOMER_BRANCH, line.substring(i - 10, i - 7)); // CUSTOMER
																																	// BRANCH 3
		addFieldValue(CUSTOMER_NUMBER, line.substring(i - 7, i)); // CUSTOMER NUMBER
																															// 7
		i+=3;
		addFieldValue(TICKET_NUMBER, line.substring(i, i += 11)); // TICKET NUMBER
																															// 11
		addFieldValue(NUMBER_OF_TICKETS, line.substring(i, i += 2)); // NUMBER OF
																																	// TICKETS 2
		addFieldValue(PNR_LOCATOR, line.substring(i, i += 8)); // PNR LOCATOR 8
		if (line.length()<i+1) return null;
		addFieldValue(DUPLICATE_VOID_INDICATOR, line.substring(i, i += 1)); // DUPLICATE
																																				// VOID
																																				// INDICATOR
																																				// 1
		if (line.length()<i+15) return null;
		addFieldValue(AUTHORIZATION_CODE_FOR_VOIDS, line.substring(i, i += 15)); // AUTHORIZATION
																																							// CODE
																																							// FOR
																																							// VOIDS
																																							// 15

		PssLogger.logDebug("Parsing Void M0 end ...........................................................");

		return null;

	}

	public String getPNRLocator() {
		return getFieldValue(M0VoidRecord.PNR_LOCATOR);
	}
	public String getIATA() {
		return getFieldValue(M0VoidRecord.CUSTOMER_NUMBER);
	}

	public String getTicketNumber() {
		return getFieldValue(M0VoidRecord.TICKET_NUMBER);
	}

}
