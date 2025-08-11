package pss.tourism.interfaceGDS.sabre.record;

import java.io.IOException;
import java.util.Date;

import com.ibm.icu.util.Calendar;

import pss.core.tools.collections.JMap;
import pss.tourism.interfaceGDS.Tools;
import pss.tourism.pnr.BizPNRTicket;

public class M0Record extends SabreRecord {

	public static final String RECORD_IDENTIFIER = "IU0MID"; // 
	public static final String TRANSACTION_TYPE = "IU0TYP"; // 
	public static final String INTERFACE_VERSION_NUMBER = "IU0VER"; // 
	public static final String CUSTOMER = "IU0DKN"; // 
	public static final String CUSTOMER_BRANCH = "IU0DKB"; // 
	public static final String CUSTOMER_NUMBER = "IU0DKC"; // 
	public static final String BLANK_not_used_SPARE = "IU0CTJ"; // 
	public static final String PREVIOUSLY_INVOICED_INDICATOR = "IU0PIV"; // 
	public static final String PNR_RECORD_CONTROL_CHECK = "IU0RRC"; // 
	public static final String TYPE_OF_QUEUE_PROCESSING = "IU0QUE"; // 
	public static final String SPARE = "IU0CDK"; // 
	public static final String INVOICE_NUMBER = "IU0IVN"; // 
	public static final String AGENCY_ARC_IATA_NUMBER = "IU0ATC"; // 
	public static final String PNR_LOCATOR = "IU0PNR"; // 
	public static final String LINK_PNR_LOCATOR = "IU0PNL"; // 
	public static final String SUBSCRIBER_INTERFACE_OPTION_INDICATOR = "IU0OPT"; // 
	public static final String PHONE_AN_RECEIVED_INDICATOR = "IU0F00"; // 
	public static final String ENTITLEMENTS = "IU0F01"; // 
	public static final String FARE_CALCULATION = "IU0F02"; // 
	public static final String INVOICE_ITINERARY_REMARKS = "IU0F03"; // 
	public static final String INTERFACE_REMARKS = "IU0F04"; // 
	public static final String STATUTE_MILES = "IU0F05"; // 
	public static final String OPTION_6_ITINERARIES_TO_POS_Q_DIT_ENTRY = "IU0F06"; // 
	public static final String CUSTOMER_PROFITABILITY_RECORDS = "IU0F07"; // 
	public static final String MISCELLANEOUS_CHARGE_ORDER_RECORDS = "IU0F08"; // 
	public static final String PREPAID_TICKET_ADVICE_RECORDS = "IU0F09"; // 
	public static final String TOUR_ORDER_RECORDS = "IU0F0A"; // 
	public static final String SEGMENT_ASSOCIATED_REMARKS_RECORDS = "IU0F0B"; // 
	public static final String SPARE2 = "IU0F0C"; // 
	public static final String SPARE3 = "IU0F0D"; // 
	public static final String AMOUNT_RECORD_ID = "IU0F0E"; // 
	public static final String SPARE5 = "IU0F0F"; // 
	public static final String ATB_INDICATORS = "IU0ATB"; // 
	public static final String DUPLICATE_INTERFACE_INDICATOR = "IU0DUP"; // 
	public static final String BOOKING_AGENT_INFORMATION = "IU0BPC"; // 
	public static final String BOOKING_AGENT_LOCATION = "IU0PCC"; // 
	public static final String BOOKING_AGENT_DUTY_CODE = "IU0IDC"; // 
	public static final String BOOKING_AGENT_SINE = "IU0IAG"; // 
	public static final String LNIATA = "IU0LIN"; // 
	public static final String REMOTE_PRINTER_ARC_NUMBER = "IU0RPR"; // 
	public static final String PNR_CREATION_DATE = "IU0PDT"; // 
	public static final String TIME_PNR_CREATED = "IU0TIM"; // 
	public static final String INVOICING_AGENCY_CITY_CODE = "IU0IS4"; // 
	public static final String INVOICING_AGENT_DUTY_CODE = "IU0IS1"; // 
	public static final String INVOICING_AGENT_CODE = "IU0IS3"; // 
	public static final String SPARE6 = "IU0TCO"; // 
	public static final String BRANCH_ID = "IU0IDB"; // 
	public static final String DEPARTURE_DATE = "IU0DEP"; // 
	public static final String ORIGIN_CITY_CODE = "IU0ORG"; // 
	public static final String ORIGIN_CITY_NAME = "IU0ONM"; // 
	public static final String DESTINATION_CITY_CODE = "IU0DST"; // 
	public static final String DESTINATION_CITY_NAME = "IU0DNM"; // 
	public static final String NUMBER_OF_PASSENGERS_M1 = "IU0NM1"; // 
	public static final String NUMBER_OF_TICKETED_PASSENGERS_M2 = "IU0NM2"; // 
	public static final String NUMBER_OF_ITINERARY_SEGMENTS_M3 = "IU0NM3"; // 
	public static final String NUMBER_OF_ENTITLEMENTS_M4 = "IU0NM4"; // 
	public static final String NUMBER_OF_ACCOUNTING_LINES_M5 = "IU0NM5"; // 
	public static final String NUMBER_OF_FARE_CALC_LINES_M6 = "IU0NM6"; // 
	public static final String NUMBER_OF_ITINERARY_REMARK_LINES_M7 = "IU0NM7"; // 
	public static final String NUMBER_OF_INVOICE_REMARK_LINES_M8 = "IU0NM8"; // 
	public static final String NUMBER_OF_INTERFACE_REMARK_LINES_M9 = "IU0NM9"; // 
	public static final String NUMBER_OF_CUSTOMER_PROFITABILITY_ITEMS = "IU0NMA"; // 
	public static final String SPARE7 = "IU0ADC"; // 
	public static final String NUMBER_OF_PHONE_FIELDS_PRESENT = "IU0PHC"; // 
	public static final String IUR_M0_RECORD_CREATION_TIME = "IU0TYM"; // 
	public static final String IUR_M0_RECORD_CREATION_DATE = "IU0MDT"; // 
	public static final String CARRIAGE_RETURN = "IU0CR1"; // 
	public static final String TRAVEL_POLICY_NUMBER = "IU0TPR"; // 
	public static final String FIRST_ADDRESS_FIELD = "IU0BL1"; // 
	public static final String SECOND_ADDRESS_FIELD = "IU0BL2"; // 
	public static final String PHONE_FIELD_1 = "IU0PH1"; // 
	public static final String PHONE_FIELD_2 = "IU0PH2"; // 
	public static final String PHONE_FIELD_3 = "IU0PH3"; // 
	public static final String RECEIVED_FIELD = "IU0RCV";
	
	protected String branch = null;
	
	protected String tipoPrestacion=BizPNRTicket.TIPO_AEREO;

	public void setTipoPrestacion(String zValue) throws Exception {
		tipoPrestacion = zValue;
	}

	public String getTipoPrestacion() throws Exception {
		return tipoPrestacion;
	}

	public M0Record() {
		ID = SabreRecord.M0;
	}

	// ==========================================================================
	// M0 Record - Control and Constant Data Record
	// ==========================================================================
	// This record is created from invoice and/or itinerary data.
	// This record stores constant data and general information data from the PNR
	// Notes: Some levels fields cannot be determinated only reading the reference
	// manual.
	// For example, the agent duty code or agent city code. We assume that the
	// agent information
	// is present in this 3 codes, or they are only blanks spaces.
	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {
		int i = 2;
		addFieldValue(RECORD_IDENTIFIER, M0); // RECORD IDENTIFIER 2
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
		if (line.length()!=212) {
				addFieldValue(BLANK_not_used_SPARE, line.substring(i, i += 5)); // BLANK not
			// used
			// /SPARE 5
				addFieldValue(PREVIOUSLY_INVOICED_INDICATOR, line.substring(i, i += 1)); // PREVIOUSLY
								// INVOICED
								// INDICATOR
								// 1
					addFieldValue(PNR_RECORD_CONTROL_CHECK, line.substring(i, i += 2)); // PNR
					// RECORD
					// CONTROL
					// CHECK
					// 2
				addFieldValue(TYPE_OF_QUEUE_PROCESSING, line.substring(i, i += 1)); // TYPE
					// OF
					// QUEUE
					// PROCESSING
					// 1
					addFieldValue(SPARE, line.substring(i, i += 1)); // SPARE 1
					addFieldValue(INVOICE_NUMBER, line.substring(i, i += 7)); // INVOICE NUMBER
					// 7
					addFieldValue(AGENCY_ARC_IATA_NUMBER, line.substring(i, i += 10)); // AGENCY
					// ARC/IATA
					// NUMBER
					// 10
		
		} else {
			addFieldValue(SPARE, line.substring(i, i += 10)); // SPARE 1
			
			addFieldValue(AGENCY_ARC_IATA_NUMBER, line.substring(i, i += 10)); // AGENCY
			addFieldValue(PREVIOUSLY_INVOICED_INDICATOR,"N"); // PREVIOUSLY
			
		}
		addFieldValue(PNR_LOCATOR, line.substring(i, i += 8)); // PNR LOCATOR 8

		addFieldValue(LINK_PNR_LOCATOR, line.substring(i, i += 8)); // LINK PNR
																																// LOCATOR 8
		addFieldValue(SUBSCRIBER_INTERFACE_OPTION_INDICATOR, line.substring(i, i += 1)); // SUBSCRIBER
																																											// INTERFACE
																																											// OPTION
																																											// INDICATOR
																																											// 1
		addFieldValue(PHONE_AN_RECEIVED_INDICATOR, line.substring(i, i += 1)); // PHONE,
																																						// AND
																																						// RECEIVED
																																						// INDICATOR
																																						// 1
		addFieldValue(ENTITLEMENTS, line.substring(i, i += 1)); // ENTITLEMENTS 1
		addFieldValue(FARE_CALCULATION, line.substring(i, i += 1)); // FARE
																																// CALCULATION 1
		addFieldValue(INVOICE_ITINERARY_REMARKS, line.substring(i, i += 1)); // INVOICE/ITINERARY
																																					// REMARKS
																																					// 1
		addFieldValue(INTERFACE_REMARKS, line.substring(i, i += 1)); // INTERFACE
																																	// REMARKS 1
		addFieldValue(STATUTE_MILES, line.substring(i, i += 1)); // STATUTE MILES 1
		addFieldValue(OPTION_6_ITINERARIES_TO_POS_Q_DIT_ENTRY, line.substring(i, i += 1)); // OPTION
																																												// 6
																																												// ITINERARIES
																																												// TO
																																												// POS
																																												// Q
																																												// (DIT
																																												// ENTRY)
																																												// 1
		addFieldValue(CUSTOMER_PROFITABILITY_RECORDS, line.substring(i, i += 1)); // CUSTOMER
																																							// PROFITABILITY
																																							// RECORDS
																																							// 1
		addFieldValue(MISCELLANEOUS_CHARGE_ORDER_RECORDS, line.substring(i, i += 1)); // MISCELLANEOUS
																																									// CHARGE
																																									// ORDER
																																									// RECORDS
																																									// 1
		addFieldValue(PREPAID_TICKET_ADVICE_RECORDS, line.substring(i, i += 1)); // PRE-PAID
																																							// TICKET
																																							// ADVICE
																																							// RECORDS
																																							// 1
		addFieldValue(TOUR_ORDER_RECORDS, line.substring(i, i += 1)); // TOUR ORDER
																																	// RECORDS 1
		addFieldValue(SEGMENT_ASSOCIATED_REMARKS_RECORDS, line.substring(i, i += 1)); // SEGMENT
																																									// ASSOCIATED
																																									// REMARKS
																																									// RECORDS
																																									// 1
		addFieldValue(SPARE2, line.substring(i, i += 1)); // SPARE 1
		addFieldValue(SPARE3, line.substring(i, i += 1)); // SPARE 1
		addFieldValue(AMOUNT_RECORD_ID, line.substring(i, i += 1)); // SPARE 1
		addFieldValue(SPARE5, line.substring(i, i += 1)); // SPARE 1
		addFieldValue(ATB_INDICATORS, line.substring(i, i += 1)); // ATB INDICATORS
																															// 1
		addFieldValue(DUPLICATE_INTERFACE_INDICATOR, line.substring(i, i += 1)); // DUPLICATE
																																							// INTERFACE
																																							// INDICATOR
																																							// 1
		addFieldValue(BOOKING_AGENT_INFORMATION, line.substring(i, i += 10)); // BOOKING
																																					// AGENT
																																					// INFORMATION
																																					// 10
		addFieldValue(BOOKING_AGENT_LOCATION, line.substring(i - 10, i - 5)); // BOOKING
																																					// AGENT
																																					// LOCATION
																																					// 5
		addFieldValue(BOOKING_AGENT_DUTY_CODE, line.substring(i - 5, i - 3)); // BOOKING
																																					// AGENT
																																					// DUTY
																																					// CODE
																																					// 2
		addFieldValue(BOOKING_AGENT_SINE, line.substring(i - 3, i)); // BOOKING
																																	// AGENT SINE
																																	// 3
		addFieldValue(LNIATA, line.substring(i, i += 8)); // LNIATA 8
		addFieldValue(REMOTE_PRINTER_ARC_NUMBER, line.substring(i, i += 10)); // REMOTE
																																					// PRINTER
																																					// ARC
																																					// NUMBER
																																					// 10
		addFieldValue(PNR_CREATION_DATE, line.substring(i, i += 5)); // PNR CREATION
																																	// DATE 5
		addFieldValue(TIME_PNR_CREATED, line.substring(i, i += 5)); // TIME PNR
																																// CREATED 5
		addFieldValue(INVOICING_AGENCY_CITY_CODE, line.substring(i, i += 5)); // INVOICING
																																					// AGENCY
																																					// CITY
																																					// CODE
																																					// 5
		addFieldValue(INVOICING_AGENT_DUTY_CODE, line.substring(i, i += 2)); // INVOICING
																																					// AGENT
																																					// DUTY
																																					// CODE
																																					// 2
		addFieldValue(INVOICING_AGENT_CODE, line.substring(i, i += 3)); // INVOICING
																																		// AGENT
																																		// CODE 3
		addFieldValue(SPARE6, line.substring(i, i += 2)); // SPARE 2
		addFieldValue(BRANCH_ID, line.substring(i, i += 3)); // BRANCH ID 3
		addFieldValue(DEPARTURE_DATE, line.substring(i, i += 5)); // DEPARTURE DATE
																															// 5
		addFieldValue(ORIGIN_CITY_CODE, line.substring(i, i += 3)); // ORIGIN CITY
																																// CODE 3
		addFieldValue(ORIGIN_CITY_NAME, line.substring(i, i += 17)); // ORIGIN CITY
																																	// NAME 17
		addFieldValue(DESTINATION_CITY_CODE, line.substring(i, i += 3)); // DESTINATION
																																			// CITY
																																			// CODE 3
		addFieldValue(DESTINATION_CITY_NAME, line.substring(i, i += 17)); // DESTINATION
																																			// CITY
																																			// NAME 17
		addFieldValue(NUMBER_OF_PASSENGERS_M1, line.substring(i, i += 3)); // NUMBER
																																				// OF
																																				// PASSENGERS
																																				// . M1
																																				// 3
		addFieldValue(NUMBER_OF_TICKETED_PASSENGERS_M2, line.substring(i, i += 3)); // NUMBER
																																								// OF
																																								// TICKETED
																																								// PASSENGERS
																																								// .M2
																																								// 3
		addFieldValue(NUMBER_OF_ITINERARY_SEGMENTS_M3, line.substring(i, i += 3)); // NUMBER
																																								// OF
																																								// ITINERARY
																																								// SEGMENTS
																																								// .M3
																																								// 3
		addFieldValue(NUMBER_OF_ENTITLEMENTS_M4, line.substring(i, i += 3)); // NUMBER
																																					// OF
																																					// ENTITLEMENTS
																																					// .
																																					// M4
																																					// 3
		addFieldValue(NUMBER_OF_ACCOUNTING_LINES_M5, line.substring(i, i += 3)); // NUMBER
																																							// OF
																																							// ACCOUNTING
																																							// LINES
																																							// .
																																							// M5
																																							// 3
		addFieldValue(NUMBER_OF_FARE_CALC_LINES_M6, line.substring(i, i += 3)); // NUMBER
																																						// OF
																																						// FARE
																																						// CALC
																																						// LINES
																																						// .
																																						// M6
																																						// 3
		addFieldValue(NUMBER_OF_ITINERARY_REMARK_LINES_M7, line.substring(i, i += 3)); // NUMBER
																																										// OF
																																										// ITINERARY
																																										// REMARK
																																										// LINES.
																																										// M7
																																										// 3
		addFieldValue(NUMBER_OF_INVOICE_REMARK_LINES_M8, line.substring(i, i += 3)); // NUMBER
																																									// OF
																																									// INVOICE
																																									// REMARK
																																									// LINES
																																									// .M8
																																									// 3
		addFieldValue(NUMBER_OF_INTERFACE_REMARK_LINES_M9, line.substring(i, i += 3)); // NUMBER
																																										// OF
																																										// INTERFACE
																																										// REMARK
																																										// LINES
																																										// .
																																										// M9
																																										// 3
		addFieldValue(NUMBER_OF_CUSTOMER_PROFITABILITY_ITEMS, line.substring(i, i += 3)); // NUMBER
																																											// OF
																																											// CUSTOMER
																																											// PROFITABILITY
																																											// ITEMS
																																											// 3
		addFieldValue(SPARE7, line.substring(i, i += 2)); // SPARE 2
		addFieldValue(NUMBER_OF_PHONE_FIELDS_PRESENT, line.substring(i, i += 2)); // NUMBER
																																							// OF
																																							// PHONE
																																							// FIELDS
																																							// PRESENT
																																							// 2
		addFieldValue(IUR_M0_RECORD_CREATION_TIME, line.substring(i, i += 5)); // IUR
																																						// M0
																																						// RECORD
																																						// CREATION
																																						// TIME
																																						// 5
		addFieldValue(IUR_M0_RECORD_CREATION_DATE, line.substring(i, i += 5)); // IUR
																																						// M0
																																						// RECORD
																																						// CREATION
																																						// DATE
																																						// 5

		addFieldValue(TRAVEL_POLICY_NUMBER, line=this.getInput().readLine());

		addFieldValue(FIRST_ADDRESS_FIELD, getAddress());
		addFieldValue(SECOND_ADDRESS_FIELD, getAddress());

		if (((String) this.getFieldValue(PHONE_AN_RECEIVED_INDICATOR)).equalsIgnoreCase("0") == false) {
			this.getInput().mark(1000);
			addFieldValue(PHONE_FIELD_1, line=this.getInput().readLine());
			if (line.startsWith(SabreRecord.M1)) {
				this.getInput().reset();
			} else {
				this.getInput().mark(1000);
				addFieldValue(PHONE_FIELD_2, line=this.getInput().readLine());
				if (line.startsWith(SabreRecord.M1)) {
					this.getInput().reset();
				} else {
					this.getInput().mark(1000);
					addFieldValue(PHONE_FIELD_3, line=this.getInput().readLine());
					if (line.startsWith(SabreRecord.M1)) {
						this.getInput().reset();
					}
				}
			}
		}
		this.getInput().mark(1000);
		addFieldValue(RECEIVED_FIELD, line=this.getInput().readLine());
		if (line.startsWith(SabreRecord.M1)) {
			this.getInput().reset();
			return line;
		}
		
		while(true) {
			this.getInput().mark(1000);
			line=this.getInput().readLine();
			if (line==null) return line;
			if (line.startsWith(SabreRecord.M1)) {
				this.getInput().reset();
				return line;
			}
				
		}
		

	}
	
	
	

	/**
	 * @param addr1
	 * @return
	 * @throws IOException
	 */
	private String getAddress() throws IOException {
		String addr1 = "";
		this.getInput().mark(3000);
		for (int j = 0; j < 5; j++) {
			this.getInput().mark(3000);
			String linea  = this.getInput().readLine();
			if (linea.startsWith(SabreRecord.M1)) {
				this.getInput().reset();
				break;
			}
			addr1 += linea + "\n";
		}
		return addr1;
	}

	public int getNumberOfM1() {
		return Integer.parseInt((String) getFieldValue(M0Record.NUMBER_OF_PASSENGERS_M1));
	}

	public int getNumberOfM2() {
		return Integer.parseInt((String) getFieldValue(M0Record.NUMBER_OF_TICKETED_PASSENGERS_M2));
	}

	public int getNumberOfM3() {
		return Integer.parseInt((String) getFieldValue(M0Record.NUMBER_OF_ITINERARY_SEGMENTS_M3));
	}

	public int getNumberOfM4() {
		return Integer.parseInt((String) getFieldValue(M0Record.NUMBER_OF_ENTITLEMENTS_M4));
	}

	public int getNumberOfM5() {
		return Integer.parseInt((String) getFieldValue(M0Record.NUMBER_OF_ACCOUNTING_LINES_M5));
	}

	public int getNumberOfM6() {
		return Integer.parseInt((String) getFieldValue(M0Record.NUMBER_OF_FARE_CALC_LINES_M6));
	}

	public int getNumberOfM7() {
		return Integer.parseInt((String) getFieldValue(M0Record.NUMBER_OF_ITINERARY_REMARK_LINES_M7));
	}

	public int getNumberOfM8() {
		return Integer.parseInt((String) getFieldValue(M0Record.NUMBER_OF_INVOICE_REMARK_LINES_M8));
	}
	
	public boolean hasToUseMxRecord() {
		if (getFieldValue(M0Record.AMOUNT_RECORD_ID).equals("1"))
			return true;
		else 
			return false;
	}

	public String getPNRLocator() {
		return getFieldValue(M0Record.PNR_LOCATOR);
	}

	public String getTransactionType() {
		// 1 = INVOICE/TICKET
		// 2 = INVOICE ONLY (DIN ENTRIES)
		// 3 = ITINERARY ONLY (DIT ENTRIES) (MINI-ITIN ENTRIES)
		// 4 = BOARDING PASS ONLY (BOARDING PASS ENTRIES)
		// 5 = VOID TICKET (WV ENTRIES)
		// 6 = FARE INFORMATION
		// 7 = PURGED RECORD
		// D = REFUND
		return getFieldValue(M0Record.TRANSACTION_TYPE);
	}

	public String getOfficeLocation() {
		return getFieldValue(M0Record.BOOKING_AGENT_LOCATION);
	}
	public String getAgenteEmision() {
		return getFieldValue(M0Record.INVOICING_AGENCY_CITY_CODE);
	}
	public String getAgenteReserva() {
		return getFieldValue(M0Record.BOOKING_AGENT_LOCATION);
	}

	public String getVendedor() {
		
		return getFieldValue(M0Record.INVOICING_AGENT_CODE);
	}
	public String getVendedor2() {
		if (getFieldValue(M0Record.BOOKING_AGENT_INFORMATION).length()<9)
			return getFieldValue(M0Record.INVOICING_AGENT_CODE);
		return getFieldValue(M0Record.BOOKING_AGENT_INFORMATION).substring(7, 9);
	}

	public String getIATA() {
		return getFieldValue(M0Record.AGENCY_ARC_IATA_NUMBER).replace(" ", "");
	}

	public String getOriginCityCode() {
		return getFieldValue(M0Record.ORIGIN_CITY_CODE);
	}

	public String getCustomerNumber() {
		return getFieldValue(M0Record.CUSTOMER_NUMBER);
	}
	public String getCustomer() {
		return getFieldValue(M0Record.CUSTOMER);
	}

	public Date getCreationDate(long year) throws Exception {
		String creation = getFieldValue(M0Record.IUR_M0_RECORD_CREATION_DATE);
		return Tools.convertSpecialStringToDate(creation,false, year);
	}

	public Date getPNRDate(long year) throws Exception {
		String creation = getFieldValue(M0Record.PNR_CREATION_DATE);
		return Tools.convertSpecialStringToDate(creation,false, year);
	}

	
	public Date getDepartureDate(long year,Date fechaCreacion) throws Exception {
		String dep = getFieldValue(M0Record.DEPARTURE_DATE);
		if ( dep.equals("") ) return null;
		Date f= Tools.convertSpecialStringToDate(dep,year);
		Calendar fd = Calendar.getInstance();
		Calendar fc = Calendar.getInstance();
		fd.setTime(f);
		fc.setTime(fechaCreacion);
		if (fd.before(fc)) 
			return Tools.convertSpecialStringToDate(dep,year+1);
		return f;
	}

	public String getInterfaceVersionNumber() {
		return getFieldValue(M0Record.INTERFACE_VERSION_NUMBER);
	}

	public String getDuplicated() {
		if (getFieldValue(M0Record.DUPLICATE_INTERFACE_INDICATOR).equals("D"))
			return "1";
		return "0";
	}

	public boolean isPreviouslyInvoicedIndicator() {
	  return  getFieldValue(PREVIOUSLY_INVOICED_INDICATOR).equals("Y");
	}

	public String getBranch() {
		return getFieldValue(BOOKING_AGENT_LOCATION);
	}
	public boolean isReserva() {
		return getTransactionType() .equals("3");
	}
}
