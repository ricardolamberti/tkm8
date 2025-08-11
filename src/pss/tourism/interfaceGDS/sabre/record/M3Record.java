package pss.tourism.interfaceGDS.sabre.record;

import java.util.Date;
import java.util.StringTokenizer;

import pss.core.tools.JDateTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;
import pss.tourism.interfaceGDS.Tools;
import pss.tourism.pnr.BizPNRTicket;

public class M3Record extends SabreRecord {

	public static final String MESSAGE_ID = "IU3MID"; //
	public static final String ITINERARY_ITEM_NUMBER = "IU3ITN"; //
	public static final String PRODUCT_CODE = "IU3PRC"; //
	public static final String ACCOUNTING_LINK_CODE = "IU3LNK"; //
	public static final String CONTROL_DATA = "IU3CRL"; //
	public static final String ACTION_ADVICE_SEGMENT_STATUS_CODES = "IU3AAC"; //
	public static final String DEPARTURE_DATE_OR_RESERVATION_COMMENCEMENT_IN_DATE = "IU3DDT"; //
	public static final String SECONDARY_PRODUCT_CODE = "IU3PC2"; //

	public static final String ISSUE_A_BOARDING_PASS = "IU3BPI";
	public static final String DEPARTURE_CITY_CODE = "IU3DCC"; //
	public static final String DEPARTURE_CITY_NAME = "IU3DCY"; //
	public static final String ARRIVAL_CITY_CODE = "IU3ACC";
	public static final String ARRIVAL_CITY_NAME = "IU3ACY";
	public static final String CARRIER_CODE = "IU3CAR";
	public static final String FLIGHT_NUMBER = "IU3FLT";
	public static final String CLASS_OF_SERVICE = "IU3CLS";
	public static final String DEPARTURE_TIME = "IU3DTM";
	public static final String ARRIVAL_TIME = "IU3ATM";
	public static final String ELAPSED_FLYING_TIME = "IU3ELT"; //
	public static final String MEAL_SERVICE_INDICATOR = "IU3MLI"; //
	public static final String SUPPLEMENTAL_INFORMATION = "IU3SUP"; //
	public static final String FLIGHT_ARRIVAL_DATE_CHANGE_INDICATOR = "IU3DCH"; //
	public static final String NUMBER_OF_STOPS = "IU3NOS";
	public static final String STOPOVER_CITY_CODES = "IU3SCC"; //
	public static final String CARRIER_TYPE_CODE = "IU3CRT";
	public static final String EQUIPMENT_TYPE_CODE = "IU3EQP"; //
	public static final String STATUTE_MILES = "IU3ARM";
	public static final String FREQUENT_TRAVELER_MILES = "IU3AVM"; //
	public static final String PRE_RESERVED_SEAT_COUNTER = "IU3SCT"; //
	public static final String DEPARTURE_TERMINAL = "IU3TRM"; //
	public static final String DEPARTURE_GATE = "IU3GAT";
	public static final String ARRIVAL_TERMINAL = "IU3TMA";
	public static final String ARRIVAL_GATE = "IU3GAR";
	public static final String REPORT_TIME = "IU3RTM";
	public static final String CHANGE_OF_GAUGE_FUNNLE_FLIGHT_COUNTER = "IU3COG"; //
	public static final String COMMUTER_CARRIER_NAME = "IU3CRN"; //
	public static final String ITINERARY_ITEM_TICKETING_INDICATOR = "IU3TKT"; //
	public static final String SPECIAL_MEAL_REQUEST_COUNTER = "IU3MCT"; //
	public static final String FLIGHT_DEPARTURE_YEAR = "IU3YER"; //
	public static final String AIRLINE_PNR_LOCATOR = "IU3OAL"; //
	public static final String CARRIAGE_RETURN = "IU3CR1";

	public static final String INTERFACE_NAME_ITEM_NUMBER = "IU3PNO"; //
	public static final String PRE_RESERVED_SEAT_TYPE = "IU3IND";//
	public static final String SEAT_NUMBER = "IU3SET";//

	public static final String MEAL_INTERFACE_NAME_ITEM_NUMBER = "IU3PMO"; //
	public static final String SPECIAL_MEAL_REQUEST_TYPE_INDICATOR = "IU3ING";//
	public static final String SPECIAL_MEAL_TYPE_CODE = "IU3MEL";//

	public static final String COG_CITY_CODE = "IU3GCC";
	public static final String COG_CITY_NAME = "IU3GCY";
	public static final String COG_DEPARTURE_DATE = "IU3GDT";
	public static final String COG_DEPARTURE_TIME = "IU3GTD";
	public static final String COG_ARRIVAL_TIME = "IU3GTA";
	public static final String COG_TERMINAL_IDENTIFIER = "IU3GTR";
	public static final String COG_GATE_IDENTIFIER = "IU3GGT";
	public static final String COG_FREQUENT_TRAVELER_MILES = "IU3GMI";

	public static final String NUMBER_OF_CARS = "IU3CCN";
	public static final String NUMBER_IN_PARTY = "IU3PTY";
	public static final String CONFIRMATION_NUMBER = "IU3CFN";

	public static final String CITY_AIRPORT_CODE = "IU3CAC";
	public static final String OTHER_SERVICE_CODE = "IU3LCC";
	public static final String OTHER_VARIABLE_DATA = "IU3VR4";
	public static final String OTHER_VARIABLE_CITY_SERVICE = "IU3VR4_SC";
	public static final String OTHER_VARIABLE_OUT_DATE = "IU3VR4_OD";
	public static final String OTHER_VARIABLE_CHAIN_CODE = "IU3VR4_CC";
	public static final String OTHER_VARIABLE_ROOM_TYPE = "IU3VR4_RT";
	public static final String OTHER_VARIABLE_ROOM_RATE = "IU3VR4_RR";
	public static final String OTHER_VARIABLE_INFO1 = "IU3VR4_INFO";

	public static final String HOTEL_COMPANY_CHAIN_CODE = "IU3TDG";
	public static final String HOTEL_CONCLUSION= "IU3OUT";
	public static final String HOTEL_PROPERTY_CODE= "IU3PRP";
	public static final String HOTEL_HOTEL_NAME= "IU3ITT";
	public static final String HOTEL_NUMBER_OF_ROOMS= "IU3NRM";
	public static final String HOTEL_AUTOMATED_HOTEL= "IU3VR2";
	public static final String HOTEL_AUTOMATED_HOTEL_PRECIOM1= "IU3VR2_M1";
	public static final String HOTEL_AUTOMATED_HOTEL_PRECIO1= "IU3VR2_V1";
	public static final String HOTEL_AUTOMATED_HOTEL_PRECIOM2= "IU3VR2_M2";
	public static final String HOTEL_AUTOMATED_HOTEL_PRECIO2= "IU3VR2_V2";
	public static final String HOTEL_AUTOMATED_HOTEL_REQUESTCLIENTID= "IU3VR2_REQC";
	public static final String HOTEL_AUTOMATED_HOTEL_RESPONSECLIENTID= "IU3VR2_RESC";
	public static final String HOTEL_AUTOMATED_HOTEL_D= "IU3VR2_D";
	public static final String HOTEL_AUTOMATED_HOTEL_T= "IU3VR2_T";
	public static final String HOTEL_AUTOMATED_HOTEL_S= "IU3VR2_S";
	public static final String HOTEL_AUTOMATED_CF= "IU3VR2_CF";
	public static final String HOTEL_AUTOMATED_SI = "IU3VR2_SI";
	public static final String HOTEL_AUTOMATED_RG = "IU3VR2_RG";
	public static final String HOTEL_AUTOMATED_HFN= "IU3VR2_HFN";
	public static final String HOTEL_AUTOMATED_TTX= "IU3VR2_TTX";
	public static final String HOTEL_AUTOMATED_TSC= "IU3VR2_TSC";
	public static final String HOTEL_AUTOMATED_HTP= "IU3VR2_HTP";
	public static final String HOTEL_AUTOMATED_TX1= "IU3VR2_TX1";
	public static final String HOTEL_AUTOMATED_TX2= "IU3VR2_TX2";
	public static final String HOTEL_AUTOMATED_TX3= "IU3VR2_TX3";
	public static final String HOTEL_AUTOMATED_TX4= "IU3VR2_TX4";
	public static final String HOTEL_AUTOMATED_SC1= "IU3VR2_SC1";
	public static final String HOTEL_AUTOMATED_SC2= "IU3VR2_SC2";
	public static final String HOTEL_AUTOMATED_SC3= "IU3VR2_SC3";
	public static final String HOTEL_AUTOMATED_SC4= "IU3VR2_SC4";
	public static final String HOTEL_AUTOMATED_DS1= "IU3VR2_DS1";
	public static final String HOTEL_AUTOMATED_DS2= "IU3VR2_DS2";
	public static final String HOTEL_AUTOMATED_CMT= "IU3VR2_CMT";
	public static final String HOTEL_AUTOMATED_CMN= "IU3VR2_CMN";
	public static final String HOTEL_AUTOMATED_TAC= "IU3VR2_TAC";
	public static final String HOTEL_AUTOMATED_NAME= "IU3VR2_NAME";
	public static final String HOTEL_AUTOMATED_DOMICILIO= "IU3VR2_DOM";
	public static final String HOTEL_AUTOMATED_PHONE= "IU3VR2_PHONE";
	public static final String HOTEL_AUTOMATED_FAX= "IU3VR2_FAX";
	public static final String HOTEL_AUTOMATED_G= "IU3VR2_G";

	public static final String CAR_AUTOMATED_PICKUP= "IU3VR4_PICKUP";
	public static final String CAR_AUTOMATED_DROPOFF= "IU3VR4_DROPOFF";
	public static final String CAR_AUTOMATED_CAR_TYPE= "IU3VR4_";
	public static final String CAR_AUTOMATED_UPG= "IU3VR4_UPG";
	public static final String CAR_AUTOMATED_ARR= "IU3VR4_ARR";
	public static final String CAR_AUTOMATED_RET= "IU3VR4_RET";
	public static final String CAR_AUTOMATED_PH= "IU3VR4_PH";
	public static final String CAR_AUTOMATED_DO= "IU3VR4_DO";
	public static final String CAR_AUTOMATED_DOC= "IU3VR4_DOC";
	public static final String CAR_AUTOMATED_G= "IU3VR4_G";
	public static final String CAR_AUTOMATED_W= "IU3VR4_W";
	public static final String CAR_AUTOMATED_CD= "IU3VR4_CD";
	public static final String CAR_AUTOMATED_IT= "IU3VR4_IT";
	public static final String CAR_AUTOMATED_IR= "IU3VR4_IR";
	public static final String CAR_AUTOMATED_SQ= "IU3VR4_SQ";
	public static final String CAR_AUTOMATED_FT= "IU3VR4_FT";
	public static final String CAR_AUTOMATED_NM= "IU3VR4_NM";
	public static final String CAR_AUTOMATED_PC= "IU3VR4_PC";
	public static final String CAR_AUTOMATED_BA= "IU3VR4_BA";
	public static final String CAR_AUTOMATED_RG= "IU3VR4_RG";
	public static final String CAR_AUTOMATED_BS= "IU3VR4_BS";
	public static final String CAR_AUTOMATED_AP= "IU3VR4_AP";
	public static final String CAR_AUTOMATED_RC= "IU3VR4_RC";
	public static final String CAR_AUTOMATED_VP= "IU3VR4_VP";
	public static final String CAR_AUTOMATED_CF= "IU3VR4_CF";
	public static final String CAR_AUTOMATED_VV= "IU3VR4_VV";
	public static final String CAR_AUTOMATED_VN= "IU3VR4_VN";
	public static final String CAR_AUTOMATED_VB= "IU3VR4_VB";
	public static final String CAR_AUTOMATED_VF= "IU3VR4_VF";
	public static final String CAR_AUTOMATED_SI= "IU3VR4_SI";
	public static final String CAR_AUTOMATED_SQC= "IU3VR4_SQC";
	public static final String CAR_AUTOMATED_SSQ= "IU3VR4_SSQ";
	public static final String CAR_AUTOMATED_PGP= "IU3VR4_PGP";
	public static final String CAR_AUTOMATED_PGG= "IU3VR4_PGG";
	public static final String CAR_AUTOMATED_AMT= "IU3VR4_AMT";
	public static final String CAR_AUTOMATED_RF1= "IU3VR4_RF1";
	public static final String CAR_AUTOMATED_RF2= "IU3VR4_RF2";
	public static final String CAR_AUTOMATED_RF3= "IU3VR4_RF3";
	public static final String CAR_AUTOMATED_RF4= "IU3VR4_RF4";
	public static final String CAR_AUTOMATED_CX1= "IU3VR4_CX1";
	public static final String CAR_AUTOMATED_CX2= "IU3VR4_CX2";
	public static final String CAR_AUTOMATED_CX3= "IU3VR4_CX3";
	public static final String CAR_AUTOMATED_CX4= "IU3VR4_CX4";

	public static final String ETO_AUTOMATED_R= "IU3VR4_R";
	public static final String ETO_AUTOMATED_M= "IU3VR4_M";
	public static final String ETO_AUTOMATED_C= "IU3VR4_C";
	public static final String ETO_AUTOMATED_L= "IU3VR4_L";
	public static final String ETO_AUTOMATED_F= "IU3VR4_F";
	public static final String ETO_AUTOMATED_P= "IU3VR4_P";
	public static final String ETO_AUTOMATED_CF= "IU3VR4_CF";
	public static final String ESE_AUTOMATED_V= "IU3VR4_V";
	public static final String ESE_AUTOMATED_T= "IU3VR4_T";
	public static final String ESE_AUTOMATED_C= "IU3VR4_C";
	public static final String ESE_AUTOMATED_B= "IU3VR4_B";
	public static final String ESE_AUTOMATED_F= "IU3VR4_F";
	public static final String ESE_AUTOMATED_P= "IU3VR4_P";
	public static final String ESE_AUTOMATED_S= "IU3VR4_S";
	 public static final String ESE_AUTOMATED_CF= "IU3VR4_CF";
	 public static final String CRU_AUTOMATED_CS= "IU3VR4_CS";
	 public static final String CRU_AUTOMATED_LV= "IU3VR4_LV";
		public static final String CRU_AUTOMATED_AR= "IU3VR4_AR";
		public static final String CRU_AUTOMATED_DT= "IU3VR4_DT";
		public static final String CRU_AUTOMATED_NA= "IU3VR4_NA";
		public static final String CRU_AUTOMATED_SI= "IU3VR4_SI";
		public static final String CRU_AUTOMATED_CF= "IU3VR4_CF";
	 public static final String INS_AUTOMATED_NM= "IU3VR4_NM";
		public static final String INS_AUTOMATED_PD= "IU3VR4_PD";
		public static final String INS_AUTOMATED_LD= "IU3VR4_LD";
		public static final String INS_AUTOMATED_PT= "IU3VR4_PT";
		public static final String INS_AUTOMATED_FP= "IU3VR4_FP";
		public static final String INS_AUTOMATED_DE= "IU3VR4_DE";
		public static final String INS_AUTOMATED_CR= "IU3VR4_CR";
		public static final String INS_AUTOMATED_PR= "IU3VR4_PR";
		public static final String INS_AUTOMATED_CF= "IU3VR4_CF";
		public static final String INS_AUTOMATED_BS= "IU3VR4_BS";
	 public static final String RAL_AUTOMATED_NM= "IU3VR4_NM";
		public static final String RAL_AUTOMATED_AN= "IU3VR4_AN";
		public static final String RAL_AUTOMATED_TT= "IU3VR4_TT";
		public static final String RAL_AUTOMATED_TR= "IU3VR4_TR";
		public static final String RAL_AUTOMATED_AC= "IU3VR4_AC";
		public static final String RAL_AUTOMATED_IA= "IU3VR4_IA";
		public static final String RAL_AUTOMATED_TC= "IU3VR4_TC";
		public static final String RAL_AUTOMATED_PG= "IU3VR4_PG";
		public static final String RAL_AUTOMATED_CC= "IU3VR4_CC";
		public static final String RAL_AUTOMATED_FR= "IU3VR4_FR";
		public static final String RAL_AUTOMATED_CA= "IU3VR4_CA";
		public static final String RAL_AUTOMATED_CO= "IU3VR4_CO";
		public static final String RAL_AUTOMATED_CT= "IU3VR4_CT";
		public static final String RAL_AUTOMATED_ID= "IU3VR4_ID";
		public static final String RAL_AUTOMATED_PY= "IU3VR4_PY";
		public static final String RAL_AUTOMATED_CL= "IU3VR4_CL";
		public static final String RAL_AUTOMATED_OC= "IU3VR4_OC";
		public static final String RAL_AUTOMATED_DC= "IU3VR4_DC";
		public static final String RAL_AUTOMATED_PT= "IU3VR4_PT";
		public static final String RAL_AUTOMATED_TA= "IU3VR4_TA";
		public static final String RAL_AUTOMATED_MGRR= "IU3VR4_MGRR";

	
	public static final String ADD_AUTOMATED_BI= "IU3VR4_BI";
	public static final String ADD_AUTOMATED_VR= "IU3VR4_VR";
	public static final String ADD_AUTOMATED_SC= "IU3VR4_SC";
	public static final String ADD_AUTOMATED_TN= "IU3VR4_TN";
	public static final String ADD_AUTOMATED_CF= "IU3VR4_CF";
	public static final String ADD_AUTOMATED_SI= "IU3VR4_SI";
	public static final String ADD_AUTOMATED_VI= "IU3VR4_VI";
	public static final String ADD_AUTOMATED_BS= "IU3VR4_BS";
	public static final String ADD_AUTOMATED_ST= "IU3VR4_ST";
	public static final String ADD_AUTOMATED_SP= "IU3VR4_SP";
	public static final String ADD_AUTOMATED_SS= "IU3VR4_SS";
	public static final String ADD_AUTOMATED_NM= "IU3VR4_NM";
	public static final String ADD_AUTOMATED_PU= "IU3VR4_PU";
	public static final String ADD_AUTOMATED_DO= "IU3VR4_DO";
	public static final String ADD_AUTOMATED_SG= "IU3VR4_SG";
	public static final String ADD_AUTOMATED_SF= "IU3VR4_SF";

	public static final String LIMO_AUTOMATED_CITYCODE= "IU3VR4_CITYCODE";
	public static final String LIMO_AUTOMATED_SERVICETYPE= "IU3VR4_SERVICETYPE";
	public static final String LIMO_OPERATOR_CARRIER= "IU3VR4_CARRIER";
	public static final String LIMO_OPERATOR_DATE= "IU3VR4_DATE";
	public static final String LIMO_OPERATOR_POINTSALE= "IU3VR4_POINTSALE";
	public static final String LIMO_OPERATOR_SI= "IU3VR4_SI";

	public static final String TOR_AUTOMATED_CITY_SERVCE= "IU3VR4_CSE";
	public static final String TOR_AUTOMATED_TOURNAME= "IU3VR4_TOU";
	public static final String TOR_AUTOMATED_INCLUSIVE= "IU3VR4_INC";
	public static final String TOR_AUTOMATED_ACCOMMODATION= "IU3VR4_ACO";
	public static final String TOR_AUTOMATED_SUPLEMENTAL= "IU3VR4_SUP";

	public static final String CITY_OF_SERVICE = "IU3COS";
	public static final String OUT_DATE = "IU3OUT";
	public static final String HOTEL_NAME = "IU3ITT";
	public static final String ROOM_TYPE = "IU3RTP";
	public static final String ROOM_RATE_PRICE = "IU3RRP";
	public static final String OPTIONAL_HOTEL_INFO1 = "IU3VR2INFO1";
	public static final String OPTIONAL_HOTEL_INFO2 = "IU3VR2INFO2";

	public static final String TRANSPORT_TYPE = "TRANSPORT_TYPE";
	
	public M3Record() {
		ID = SabreRecord.M3;
	}

	// ==========================================================================
	// M3 Record - Passenger Ticket Data Record
	// ==========================================================================
	// The M3 data area can take various formats based on the PNR segment type
	// from
	// which the Itinerary item is built. Currently there are four different
	// formats based on the Product Code and Secondary Product Code.
	// Each of the various formats is described separately below
	public String doProcess(JMap<String, Object> mRecords, String line)
			throws Exception {

		int i = 2;
		addFieldValue(MESSAGE_ID, ID); // MESSAGE ID
		addFieldValue(ITINERARY_ITEM_NUMBER,
				Integer.parseInt(line.substring(i, i += 2)) + "");
		addFieldValue(PRODUCT_CODE, line.substring(i, i += 1));
		addFieldValue(ACCOUNTING_LINK_CODE, line.substring(i, i += 1));
		addFieldValue(CONTROL_DATA, line.substring(i, i += 1));
		addFieldValue(ACTION_ADVICE_SEGMENT_STATUS_CODES,
				line.substring(i, i += 2));
		addFieldValue(DEPARTURE_DATE_OR_RESERVATION_COMMENCEMENT_IN_DATE,
				line.substring(i, i += 5));
		addFieldValue(SECONDARY_PRODUCT_CODE, line.substring(i, i += 3));

		if (isAirOrAmtrack()) {
			readAirOrAMTRAKRailItinerary(i, line);
		} else if (this.isVoyEnBus(line)) {
			((M0Record) mRecords.getElement(SabreRecord.M0)).setTipoPrestacion(BizPNRTicket.TIPO_BUS);
			readVoyEnBus(i, line);
			this.setFieldValue(PRODUCT_CODE, "BUS");
		} else if (isAutomatedHotelItinerary()) {
			readOtherItineraries(i, line);
//			getInput().readLine();
//			getInput().readLine();
		} else if (isOther()) {
				
			readOtherItineraries(i, line);
		}
		while (true) {
			this.getInput().mark(1000);
			line = this.getInput().readLine();
			if (line == null)
				break;
			if ( line.trim().equals("") ) continue;
			if (line.startsWith(M3) || line.startsWith(M4)
					|| line.startsWith(M5) || line.startsWith(M6)
					|| line.startsWith(M7) || line.startsWith(M8)) {
				this.getInput().reset();
				break;
			}
		}

		return line;

	}
	private void readOtherItineraries(int i, String line) throws Exception {
		addFieldValue(NUMBER_OF_CARS, line.substring(i, i += 1));
		addFieldValue(NUMBER_IN_PARTY, line.substring(i, i += 2));
		addFieldValue(CONFIRMATION_NUMBER, line.substring(i, i += 15));
		if (isHotelItinerary()) {
			if (!line.substring(i+3).startsWith("/")) {
				addFieldValue(CITY_AIRPORT_CODE, line.substring(i, i += 3));
				addFieldValue(HOTEL_COMPANY_CHAIN_CODE, line.substring(i, i += 2));
				addFieldValue(HOTEL_CONCLUSION, line.substring(i, i += 14));
				addFieldValue(HOTEL_PROPERTY_CODE, line.substring(i, i += 6));
				addFieldValue(HOTEL_HOTEL_NAME, line.substring(i, i += 32));
				addFieldValue(HOTEL_NUMBER_OF_ROOMS, line.substring(i, i += 11));
				addFieldValue(HOTEL_AUTOMATED_HOTEL, line.substring(i));
				readHHTOrHTLVariableData();
			} else {
				addFieldValue(OTHER_VARIABLE_DATA, line.substring(i));
				readOtherHotelVariableData();
		}
		} else if (isLimoItinerary()) {
			addFieldValue(OTHER_SERVICE_CODE, line.substring(i, i += 2));
			addFieldValue(OTHER_VARIABLE_DATA, line.substring(i));
			readLimoItinerariesVariableData();
	  } else if (isCarItinerary()) {
			addFieldValue(OTHER_VARIABLE_DATA, line.substring(i));
			readCarItinerariesVariableData();
	  }  else if (isTorItinerary()) {
			addFieldValue(OTHER_VARIABLE_DATA, line.substring(i));
			readTorItinerariesVariableData();
	  } else if (isAddItinerary()) {
			addFieldValue(OTHER_VARIABLE_DATA, line.substring(i));
			readAddItinerariesVariableData();
	  } else if (isInsItinerary()) {
			addFieldValue(OTHER_VARIABLE_DATA, line.substring(i));
			readInsItinerariesVariableData();
	  } else if (isCruiseItinerary()) {
			addFieldValue(OTHER_VARIABLE_DATA, line.substring(i));
			readCruiseItinerariesVariableData();
	  } else if (isRalItinerary()) {
			addFieldValue(OTHER_VARIABLE_DATA, line.substring(i));
			readRalItinerariesVariableData();
	  } else if (isElvaSeaItinerary()) {
			addFieldValue(OTHER_VARIABLE_DATA, line.substring(i));
			readElvaSeaItinerariesVariableData();
	  } else if (isElvaTorItinerary()) {
			addFieldValue(OTHER_VARIABLE_DATA, line.substring(i));
			readElvaTorItinerariesVariableData();
	  } else if (isPrepayAdvice()) {
			addFieldValue(OTHER_VARIABLE_DATA, line.substring(i));
	  } else {
			addFieldValue(OTHER_VARIABLE_DATA, line.substring(i));
	//		readOtherItinerariesVariableData();
			
		}

	}

	private void readAddItinerariesVariableData() throws Exception {
		String line = getFieldValue(OTHER_VARIABLE_DATA);
		String separator = "/";
		StringTokenizer tok = new StringTokenizer(line, separator);
;
		while (tok.hasMoreTokens()) {
			String val = tok.nextToken();
			if (val.startsWith("BI-"))	addFieldValue(ADD_AUTOMATED_BI, val.substring(3));
			else if (val.startsWith("VR-"))	addFieldValue(ADD_AUTOMATED_VR, val.substring(3));
			else if (val.startsWith("SC-"))	addFieldValue(ADD_AUTOMATED_SC, val.substring(3));
			else if (val.startsWith("TN-"))	addFieldValue(ADD_AUTOMATED_TN, val.substring(3));
			else if (val.startsWith("CF-"))	addFieldValue(ADD_AUTOMATED_CF, val.substring(3));
			else if (val.startsWith("SI-"))	addFieldValue(ADD_AUTOMATED_SI, val.substring(3));
			else if (val.startsWith("VI-"))	addFieldValue(ADD_AUTOMATED_VI, val.substring(3));
			else if (val.startsWith("BS-"))	addFieldValue(ADD_AUTOMATED_BS, val.substring(3));
			else if (val.startsWith("ST-"))	addFieldValue(ADD_AUTOMATED_ST, val.substring(3));
			else if (val.startsWith("SP-"))	addFieldValue(ADD_AUTOMATED_SP, val.substring(3));
			else if (val.startsWith("SS-"))	addFieldValue(ADD_AUTOMATED_SS, val.substring(3));
			else if (val.startsWith("NM-"))	addFieldValue(ADD_AUTOMATED_NM, val.substring(3));
			else if (val.startsWith("PU-"))	addFieldValue(ADD_AUTOMATED_PU, val.substring(3));
			else if (val.startsWith("DO-"))	addFieldValue(ADD_AUTOMATED_DO, val.substring(3));
			else if (val.startsWith("SG-"))	addFieldValue(ADD_AUTOMATED_SG, val.substring(3));
			else if (val.startsWith("SF-"))	addFieldValue(ADD_AUTOMATED_SF, val.substring(3));
		}
	}
	private void readTorItinerariesVariableData() throws Exception {
		String line = getFieldValue(OTHER_VARIABLE_DATA);
		String separator = "/";
		StringTokenizer tok = new StringTokenizer(line, separator);
		addFieldValue(TOR_AUTOMATED_CITY_SERVCE, tok.nextToken());
		addFieldValue(TOR_AUTOMATED_TOURNAME, tok.nextToken());
		addFieldValue(TOR_AUTOMATED_INCLUSIVE, tok.nextToken());
		addFieldValue(TOR_AUTOMATED_ACCOMMODATION, tok.nextToken());
		addFieldValue(TOR_AUTOMATED_SUPLEMENTAL, tok.nextToken());
	}
	private void readElvaTorItinerariesVariableData() throws Exception {
		String line = getFieldValue(OTHER_VARIABLE_DATA);
		String separator = "/";
		StringTokenizer tok = new StringTokenizer(line, separator);
		while (tok.hasMoreTokens()) {
			String val = tok.nextToken();
			if (val.startsWith("R-"))	addFieldValue(ETO_AUTOMATED_R, val.substring(2));
			else if (val.startsWith("M-"))	addFieldValue(ETO_AUTOMATED_M, val.substring(2));
			else if (val.startsWith("C-"))	addFieldValue(ETO_AUTOMATED_C, val.substring(2));
			else if (val.startsWith("L-"))	addFieldValue(ETO_AUTOMATED_L, val.substring(2));
			else if (val.startsWith("F-"))	addFieldValue(ETO_AUTOMATED_F, val.substring(2));
			else if (val.startsWith("P-"))	addFieldValue(ETO_AUTOMATED_P, val.substring(2));
			else if (val.startsWith("CF-"))	addFieldValue(ETO_AUTOMATED_CF, val.substring(3));
		}
	}
	private void readElvaSeaItinerariesVariableData() throws Exception {
		String line = getFieldValue(OTHER_VARIABLE_DATA);
		String separator = "/";
		StringTokenizer tok = new StringTokenizer(line, separator);
		while (tok.hasMoreTokens()) {
			String val = tok.nextToken();
			if (val.startsWith("V-"))	addFieldValue(ESE_AUTOMATED_V, val.substring(2));
			else if (val.startsWith("T-"))	addFieldValue(ESE_AUTOMATED_T, val.substring(2));
			else if (val.startsWith("C-"))	addFieldValue(ESE_AUTOMATED_C, val.substring(2));
			else if (val.startsWith("B-"))	addFieldValue(ESE_AUTOMATED_B, val.substring(2));
			else if (val.startsWith("F-"))	addFieldValue(ESE_AUTOMATED_F, val.substring(2));
			else if (val.startsWith("P-"))	addFieldValue(ESE_AUTOMATED_P, val.substring(2));
			else if (val.startsWith("S-"))	addFieldValue(ESE_AUTOMATED_S, val.substring(2));
			else if (val.startsWith("S"))	addFieldValue(ESE_AUTOMATED_S+"_"+ val.substring(1,2), val.substring(2));
			else if (val.startsWith("CF-"))	addFieldValue(ESE_AUTOMATED_CF, val.substring(3));
		}
	}
	private void readCruiseItinerariesVariableData() throws Exception {
		String line = getFieldValue(OTHER_VARIABLE_DATA);
		String separator = "/";
		StringTokenizer tok = new StringTokenizer(line, separator);
		addFieldValue(CRU_AUTOMATED_CS, tok.nextToken());
		while (tok.hasMoreTokens()) {
			String val = tok.nextToken();
			if (val.startsWith("LV-"))	addFieldValue(CRU_AUTOMATED_LV, val.substring(3));
			else if (val.startsWith("AR-"))	addFieldValue(CRU_AUTOMATED_AR, val.substring(3));
			else if (val.startsWith("DT-"))	addFieldValue(CRU_AUTOMATED_DT, val.substring(3));
			else if (val.startsWith("NA-"))	addFieldValue(CRU_AUTOMATED_NA, val.substring(3));
			else if (val.startsWith("SI-"))	addFieldValue(CRU_AUTOMATED_SI, val.substring(3));
			else if (val.startsWith("CF-"))	addFieldValue(CRU_AUTOMATED_CF, val.substring(3));
		}
	}
	
	private void readInsItinerariesVariableData() throws Exception {
		String line = getFieldValue(OTHER_VARIABLE_DATA);
		String separator = "/";
		StringTokenizer tok = new StringTokenizer(line, separator);
		while (tok.hasMoreTokens()) {
			String val = tok.nextToken();
			if (val.startsWith("NM-"))	addFieldValue(INS_AUTOMATED_NM, val.substring(3));
			else if (val.startsWith("PD-"))	addFieldValue(INS_AUTOMATED_PD, val.substring(3));
			else if (val.startsWith("LD-"))	addFieldValue(INS_AUTOMATED_LD, val.substring(3));
			else if (val.startsWith("PT-"))	addFieldValue(INS_AUTOMATED_PT, val.substring(3));
			else if (val.startsWith("FP-"))	addFieldValue(INS_AUTOMATED_FP, val.substring(3));
			else if (val.startsWith("DE-"))	addFieldValue(INS_AUTOMATED_DE, val.substring(3));
			else if (val.startsWith("CR-"))	addFieldValue(INS_AUTOMATED_CR, val.substring(3));
			else if (val.startsWith("PR-"))	addFieldValue(INS_AUTOMATED_PR, val.substring(3));
			else if (val.startsWith("CF-"))	addFieldValue(INS_AUTOMATED_CF, val.substring(3));
			else if (val.startsWith("BS-"))	addFieldValue(INS_AUTOMATED_BS, val.substring(3));
		}
	}
	private void readRalItinerariesVariableData() throws Exception {
		String line = getFieldValue(OTHER_VARIABLE_DATA);
		String separator = "/";
		StringTokenizer tok = new StringTokenizer(line, separator);
		while (tok.hasMoreTokens()) {
			String val = tok.nextToken();
			if (val.startsWith("AN-"))	addFieldValue(RAL_AUTOMATED_AN, val.substring(3));
			else if (val.startsWith("TT-"))	addFieldValue(RAL_AUTOMATED_TT, val.substring(3));
			else if (val.startsWith("TR-"))	addFieldValue(RAL_AUTOMATED_TR, val.substring(3));
			else if (val.startsWith("AC-"))	addFieldValue(RAL_AUTOMATED_AC, val.substring(3));
			else if (val.startsWith("IA-"))	addFieldValue(RAL_AUTOMATED_IA, val.substring(3));
			else if (val.startsWith("TC-"))	addFieldValue(RAL_AUTOMATED_TC, val.substring(3));
			else if (val.startsWith("PG-"))	addFieldValue(RAL_AUTOMATED_PG, val.substring(3));
			else if (val.startsWith("CC-"))	addFieldValue(RAL_AUTOMATED_CC, val.substring(3));
			else if (val.startsWith("FR-"))	addFieldValue(RAL_AUTOMATED_FR, val.substring(3));
			else if (val.startsWith("CA-"))	addFieldValue(RAL_AUTOMATED_CA, val.substring(3));
			else if (val.startsWith("CO-"))	addFieldValue(RAL_AUTOMATED_CO, val.substring(3));
			else if (val.startsWith("CT-"))	addFieldValue(RAL_AUTOMATED_CT, val.substring(3));
			else if (val.startsWith("ID-"))	addFieldValue(RAL_AUTOMATED_ID, val.substring(3));
			else if (val.startsWith("PY-"))	addFieldValue(RAL_AUTOMATED_PY, val.substring(3));
			else if (val.startsWith("CL-"))	addFieldValue(RAL_AUTOMATED_CL, val.substring(3));
			else if (val.startsWith("OC-"))	addFieldValue(RAL_AUTOMATED_OC, val.substring(3));
			else if (val.startsWith("DC-"))	addFieldValue(RAL_AUTOMATED_DC, val.substring(3));
			else if (val.startsWith("PT-"))	addFieldValue(RAL_AUTOMATED_PT, val.substring(3));
			else if (val.startsWith("TA-"))	addFieldValue(RAL_AUTOMATED_TA, val.substring(3));
			else if (val.startsWith("MGRR-"))	addFieldValue(RAL_AUTOMATED_TT, val.substring(5));
		}
	}
	private void readLimoItinerariesVariableData() throws Exception {
		String line = getFieldValue(OTHER_VARIABLE_DATA);
		String separator = "/";
		StringTokenizer tok = new StringTokenizer(line, separator);
		addFieldValue(LIMO_AUTOMATED_CITYCODE, tok.nextToken());
		addFieldValue(LIMO_AUTOMATED_SERVICETYPE, tok.nextToken());
		addFieldValue(LIMO_OPERATOR_CARRIER, tok.nextToken());
		addFieldValue(LIMO_OPERATOR_DATE, tok.nextToken());
		addFieldValue(LIMO_OPERATOR_POINTSALE, tok.nextToken());
		addFieldValue(LIMO_OPERATOR_SI, tok.nextToken());
	}
	private void readCarItinerariesVariableData() throws Exception {
		String line = getFieldValue(OTHER_VARIABLE_DATA);
		String separator = "/";
		StringTokenizer tok = new StringTokenizer(line, separator);
		addFieldValue(CAR_AUTOMATED_PICKUP, tok.nextToken());
		addFieldValue(CAR_AUTOMATED_DROPOFF, tok.nextToken());
		addFieldValue(CAR_AUTOMATED_CAR_TYPE, tok.nextToken());
		while (tok.hasMoreTokens()) {
			String val = tok.nextToken();
			if (val.startsWith("UPG-")&& val.length()>3)	addFieldValue(CAR_AUTOMATED_UPG, val.substring(3));
			else if (val.startsWith("ARR-")&& val.length()>3)	addFieldValue(CAR_AUTOMATED_ARR, val.substring(3));
			else if (val.startsWith("RET-")&& val.length()>3)	addFieldValue(CAR_AUTOMATED_RET, val.substring(3));
			else if (val.startsWith("PH-")&& val.length()>3)	addFieldValue(CAR_AUTOMATED_PH, val.substring(3));
			else if (val.startsWith("DO-") && val.length()>4)	addFieldValue(CAR_AUTOMATED_DO, val.substring(4));
			else if (val.startsWith("DOC-") && val.length()>4)	addFieldValue(CAR_AUTOMATED_DOC, val.substring(4));
			else if (val.startsWith("W-") && val.length()>2)	addFieldValue(CAR_AUTOMATED_W, val.substring(2));
			else if (val.startsWith("CD-") && val.length()>3)	addFieldValue(CAR_AUTOMATED_CD, val.substring(3));
			else if (val.startsWith("IT-") && val.length()>3)	addFieldValue(CAR_AUTOMATED_IT, val.substring(3));
			else if (val.startsWith("IR-") && val.length()>3)	addFieldValue(CAR_AUTOMATED_IR, val.substring(3));
			else if (val.startsWith("SQ-") && val.length()>3)	addFieldValue(CAR_AUTOMATED_SQ, val.substring(3));
			else if (val.startsWith("FT-") && val.length()>3)	addFieldValue(CAR_AUTOMATED_FT, val.substring(3));
			else if (val.startsWith("NM-") && val.length()>3)	addFieldValue(CAR_AUTOMATED_NM, val.substring(3));
			else if (val.startsWith("PC-") && val.length()>3)	addFieldValue(CAR_AUTOMATED_PC, val.substring(3));
			else if (val.startsWith("BA-") && val.length()>3)	addFieldValue(CAR_AUTOMATED_BA, val.substring(3));
			else if (val.startsWith("RG-") && val.length()>3)	addFieldValue(CAR_AUTOMATED_RG, val.substring(3));
			else if (val.startsWith("BS-") && val.length()>3)	addFieldValue(CAR_AUTOMATED_BS, val.substring(3));
			else if (val.startsWith("AP-") && val.length()>3)	addFieldValue(CAR_AUTOMATED_AP, val.substring(3)); 
			else if (val.startsWith("RC-") && val.length()>3)	addFieldValue(CAR_AUTOMATED_RC, val.substring(3));
			else if (val.startsWith("VP-") && val.length()>3)	addFieldValue(CAR_AUTOMATED_VP, val.substring(3));
			else if (val.startsWith("CF-") && val.length()>3)	addFieldValue(CAR_AUTOMATED_CF, val.substring(3));
			else if (val.startsWith("VV-") && val.length()>3)	addFieldValue(CAR_AUTOMATED_VV, val.substring(3));
			else if (val.startsWith("VN-") && val.length()>3)	addFieldValue(CAR_AUTOMATED_VN, val.substring(3));
			else if (val.startsWith("VB-") && val.length()>3)	addFieldValue(CAR_AUTOMATED_VB, val.substring(3));
			else if (val.startsWith("VF-") && val.length()>3)	addFieldValue(CAR_AUTOMATED_VF, val.substring(3));
			else if (val.startsWith("SI-") && val.length()>3)	addFieldValue(CAR_AUTOMATED_SI, val.substring(3));
			else if (val.startsWith("CMN-") && val.length()>4)	addFieldValue(HOTEL_AUTOMATED_CMN, val.substring(4));
			else if (val.startsWith("CMT-")&& val.length()>4)	addFieldValue(HOTEL_AUTOMATED_CMT, val.substring(4));
			else if (val.startsWith("TAC-")&& val.length()>4)	addFieldValue(HOTEL_AUTOMATED_TAC, val.substring(4));
			else if (val.startsWith("SQC-")&& val.length()>4)	addFieldValue(CAR_AUTOMATED_SQC, val.substring(4));
			else if (val.startsWith("SSQ-")&& val.length()>4)	addFieldValue(CAR_AUTOMATED_SSQ, val.substring(4));
			else if (val.startsWith("PG-@P")&& val.length()>5)	addFieldValue(CAR_AUTOMATED_PGP, val.substring(5));
			else if (val.startsWith("PG-@G")&& val.length()>5)	addFieldValue(CAR_AUTOMATED_PGG, val.substring(5));
			else if (val.startsWith("AMT-@")&& val.length()>5)	addFieldValue(CAR_AUTOMATED_AMT, val.substring(5));
			else if (val.startsWith("RF1-")&& val.length()>4)	addFieldValue(CAR_AUTOMATED_RF1, val.substring(4));
			else if (val.startsWith("RF2-")&& val.length()>4)	addFieldValue(CAR_AUTOMATED_RF2, val.substring(4));
			else if (val.startsWith("RF3-")&& val.length()>4)	addFieldValue(CAR_AUTOMATED_RF3, val.substring(4));
			else if (val.startsWith("RF4-")&& val.length()>4)	addFieldValue(CAR_AUTOMATED_RF4, val.substring(4));
			else if (val.startsWith("CX1-")&& val.length()>4)	addFieldValue(CAR_AUTOMATED_CX1, val.substring(4));
			else if (val.startsWith("CX2-")&& val.length()>4)	addFieldValue(CAR_AUTOMATED_CX2, val.substring(4));
			else if (val.startsWith("CX3-")&& val.length()>4)	addFieldValue(CAR_AUTOMATED_CX3, val.substring(4));
			else if (val.startsWith("CX4-")&& val.length()>4)	addFieldValue(CAR_AUTOMATED_CX4, val.substring(4));
			else if (val.startsWith("G")&& val.length()>1)	addFieldValue(CAR_AUTOMATED_G, val.substring(1));
		}
		
	}	
	
	private void readOtherItinerariesVariableData() throws Exception {
		String line = getFieldValue(OTHER_VARIABLE_DATA);
		String separator = "/";
		StringTokenizer tok = new StringTokenizer(line, separator);
		String valor = "";
		addFieldValue(OTHER_VARIABLE_CITY_SERVICE, tok.nextToken());
		addFieldValue(OTHER_VARIABLE_OUT_DATE, tok.nextToken());
		addFieldValue(OTHER_VARIABLE_CHAIN_CODE, tok.nextToken());
		addFieldValue(OTHER_VARIABLE_ROOM_TYPE, tok.nextToken());
		addFieldValue(OTHER_VARIABLE_ROOM_RATE, tok.nextToken());
		addFieldValue(OTHER_VARIABLE_INFO1, tok.nextToken());
		addFieldValue(HOTEL_AUTOMATED_HOTEL_PRECIO1, valor.substring(0,valor.length()-3));
		addFieldValue(HOTEL_AUTOMATED_HOTEL_PRECIOM1, valor.substring(valor.length()-3));
		valor = tok.nextToken();
		addFieldValue(HOTEL_AUTOMATED_HOTEL_PRECIO2, valor.substring(0,valor.length()-3));
		addFieldValue(HOTEL_AUTOMATED_HOTEL_PRECIOM2, valor.substring(valor.length()-3));
		while (tok.hasMoreTokens()) {
			String val = tok.nextToken();
			if (val.startsWith("RQ-"))	addFieldValue(HOTEL_AUTOMATED_HOTEL_REQUESTCLIENTID, val.substring(3));
			else if (val.startsWith("RC-"))	addFieldValue(HOTEL_AUTOMATED_HOTEL_RESPONSECLIENTID, val.substring(3));
			else if (val.startsWith("CF-"))	addFieldValue(HOTEL_AUTOMATED_CF, val.substring(3));
			else if (val.startsWith("SI-"))	addFieldValue(HOTEL_AUTOMATED_SI, val.substring(3));
			else if (val.startsWith("HFN-"))	addFieldValue(HOTEL_AUTOMATED_HFN, val.substring(4));
			else if (val.startsWith("TTX-"))	addFieldValue(HOTEL_AUTOMATED_TTX, val.substring(4));
			else if (val.startsWith("TSC-"))	addFieldValue(HOTEL_AUTOMATED_TSC, val.substring(4));
			else if (val.startsWith("HTP-"))	addFieldValue(HOTEL_AUTOMATED_HTP, val.substring(4));
			else if (val.startsWith("TX1-"))	addFieldValue(HOTEL_AUTOMATED_TX1, val.substring(4));
			else if (val.startsWith("TX2-"))	addFieldValue(HOTEL_AUTOMATED_TX2, val.substring(4));
			else if (val.startsWith("TX3-"))	addFieldValue(HOTEL_AUTOMATED_TX3, val.substring(4));
			else if (val.startsWith("TX4-"))	addFieldValue(HOTEL_AUTOMATED_TX4, val.substring(4));
			else if (val.startsWith("SC1-"))	addFieldValue(HOTEL_AUTOMATED_SC1, val.substring(4));
			else if (val.startsWith("SC2-"))	addFieldValue(HOTEL_AUTOMATED_SC2, val.substring(4));
			else if (val.startsWith("SC3-"))	addFieldValue(HOTEL_AUTOMATED_SC3, val.substring(4));
			else if (val.startsWith("SC4-"))	addFieldValue(HOTEL_AUTOMATED_SC4, val.substring(4));
			else if (val.startsWith("DS1-"))	addFieldValue(HOTEL_AUTOMATED_DS1, val.substring(4));
			else if (val.startsWith("DS2-"))	addFieldValue(HOTEL_AUTOMATED_DS2, val.substring(4));
			else if (val.startsWith("CMN-"))	addFieldValue(HOTEL_AUTOMATED_CMN, val.substring(4));
			else if (val.startsWith("CMT-"))	addFieldValue(HOTEL_AUTOMATED_CMT, val.substring(4));
			else if (val.startsWith("TAC-"))	addFieldValue(HOTEL_AUTOMATED_TAC, val.substring(4));
			else if (val.startsWith("G"))	addFieldValue(HOTEL_AUTOMATED_G, val.substring(4));
			else if (val.startsWith("D"))	addFieldValue(HOTEL_AUTOMATED_HOTEL_D+"_"+ val.substring(1,2),val.substring(3));
			else if (val.startsWith("T"))	addFieldValue(HOTEL_AUTOMATED_HOTEL_T+"_"+ val.substring(1,2),val.substring(3));
			else if (val.startsWith("S"))	addFieldValue(HOTEL_AUTOMATED_HOTEL_S+"_"+ val.substring(1,2),val.substring(3));
		}
		line = getInput().readLine();
		addFieldValue(OPTIONAL_HOTEL_INFO2, line.substring(4));
		line = getInput().readLine();
		addFieldValue(OPTIONAL_HOTEL_INFO1, line.substring(4));
		
	}	
	private void readOtherHotelVariableData() throws Exception {
		String line = getFieldValue(OTHER_VARIABLE_DATA);
		String separator = "/";
		StringTokenizer tok = new StringTokenizer(line, separator);
		String valor = "";
		addFieldValue(CITY_AIRPORT_CODE, tok.nextToken());
		if (!tok.hasMoreElements()) return;
		addFieldValue(HOTEL_CONCLUSION, tok.nextToken());
		if (!tok.hasMoreElements()) return;
		addFieldValue(HOTEL_HOTEL_NAME, tok.nextToken());
		if (!tok.hasMoreElements()) return;
		addFieldValue(HOTEL_PROPERTY_CODE, tok.nextToken());
		if (!tok.hasMoreElements()) return;
		valor = tok.nextToken();
		if (valor.startsWith("RG-")) {
			addFieldValue(HOTEL_AUTOMATED_RG, valor.substring(3));
		} else if (valor.length()>=3) {
			addFieldValue(HOTEL_AUTOMATED_HOTEL_PRECIO1, valor.substring(0,valor.length()-3));
		  addFieldValue(HOTEL_AUTOMATED_HOTEL_PRECIOM1, valor.substring(valor.length()-3));
		}
		while (tok.hasMoreTokens()) {
			String val = tok.nextToken();
			if (val.startsWith("RQ-"))	addFieldValue(HOTEL_AUTOMATED_HOTEL_REQUESTCLIENTID, val.substring(3));
			else if (val.startsWith("RC-"))	addFieldValue(HOTEL_AUTOMATED_HOTEL_RESPONSECLIENTID, val.substring(3));
			else if (val.startsWith("RG-"))	addFieldValue(HOTEL_AUTOMATED_RG, val.substring(3));
			else if (val.startsWith("CF-"))	addFieldValue(HOTEL_AUTOMATED_CF, val.substring(3));
			else if (val.startsWith("SI-"))	addFieldValue(HOTEL_AUTOMATED_SI, val.substring(3));
			else if (val.startsWith("HFN-"))	addFieldValue(HOTEL_AUTOMATED_HFN, val.substring(4));
			else if (val.startsWith("TTX-"))	addFieldValue(HOTEL_AUTOMATED_TTX, val.substring(4));
			else if (val.startsWith("TSC-"))	addFieldValue(HOTEL_AUTOMATED_TSC, val.substring(4));
			else if (val.startsWith("HTP-"))	addFieldValue(HOTEL_AUTOMATED_HTP, val.substring(4));
			else if (val.startsWith("TX1-"))	addFieldValue(HOTEL_AUTOMATED_TX1, val.substring(4));
			else if (val.startsWith("TX2-"))	addFieldValue(HOTEL_AUTOMATED_TX2, val.substring(4));
			else if (val.startsWith("TX3-"))	addFieldValue(HOTEL_AUTOMATED_TX3, val.substring(4));
			else if (val.startsWith("TX4-"))	addFieldValue(HOTEL_AUTOMATED_TX4, val.substring(4));
			else if (val.startsWith("SC1-"))	addFieldValue(HOTEL_AUTOMATED_SC1, val.substring(4));
			else if (val.startsWith("SC2-"))	addFieldValue(HOTEL_AUTOMATED_SC2, val.substring(4));
			else if (val.startsWith("SC3-"))	addFieldValue(HOTEL_AUTOMATED_SC3, val.substring(4));
			else if (val.startsWith("SC4-"))	addFieldValue(HOTEL_AUTOMATED_SC4, val.substring(4));
			else if (val.startsWith("DS1-"))	addFieldValue(HOTEL_AUTOMATED_DS1, val.substring(4));
			else if (val.startsWith("DS2-"))	addFieldValue(HOTEL_AUTOMATED_DS2, val.substring(4));
			else if (val.startsWith("CMN-"))	addFieldValue(HOTEL_AUTOMATED_CMN, val.substring(4));
			else if (val.startsWith("CMT-"))	addFieldValue(HOTEL_AUTOMATED_CMT, val.substring(4));
			else if (val.startsWith("TAC-"))	addFieldValue(HOTEL_AUTOMATED_TAC, val.substring(4));
			else if (val.startsWith("SI"))	addFieldValue(HOTEL_AUTOMATED_SI, val.substring(2));
			else if (val.startsWith("G"))	addFieldValue(HOTEL_AUTOMATED_G, val.substring(1));
			else if (val.startsWith("D"))	addFieldValue(HOTEL_AUTOMATED_HOTEL_D+"_"+ val.substring(1,2),val.substring(3));
			else if (val.startsWith("T"))	addFieldValue(HOTEL_AUTOMATED_HOTEL_T+"_"+ val.substring(1,2),val.substring(3));
			else if (val.startsWith("S") && val.length()>3)	addFieldValue(HOTEL_AUTOMATED_HOTEL_S+"_"+ val.substring(1,2),val.substring(3));
		}
		
	}	
	private void readHHTOrHTLVariableData() throws Exception {
		String line = getFieldValue(HOTEL_AUTOMATED_HOTEL);
		int posF=line.lastIndexOf("@/7P");
		if (posF!=-1) {
			int posI=line.lastIndexOf("@", posF-1);
			if (posI!=-1) {
				StringTokenizer tokI = new StringTokenizer(line.substring(posI+1,posF), "#");
				if (tokI.hasMoreTokens())	addFieldValue(HOTEL_AUTOMATED_NAME,  tokI.nextToken());
				if (tokI.hasMoreTokens()) addFieldValue(HOTEL_AUTOMATED_DOMICILIO,  tokI.nextToken());
				while (tokI.hasMoreTokens()) {
					String valI = tokI.nextToken();
					if (valI.startsWith("FONE")) addFieldValue(HOTEL_AUTOMATED_PHONE, valI.substring(5));
					else if (valI.startsWith("FAX")) addFieldValue(HOTEL_AUTOMATED_FAX, valI.substring(5));
				}
				line = line.substring(0,posI-1);
			}
		}

		
		String separator = "/";
		StringTokenizer tok = new StringTokenizer(line, separator);
		if (tok.hasMoreTokens()) {
			String valor = tok.nextToken();
			if (valor.length()>3) {
				addFieldValue(HOTEL_AUTOMATED_HOTEL_PRECIO1, valor.substring(0,valor.length()-3));
				addFieldValue(HOTEL_AUTOMATED_HOTEL_PRECIOM1, valor.substring(valor.length()-3));
			}
		}
		if (tok.hasMoreTokens()) {
			String valor = tok.nextToken();
			if (valor.length()>3) {
				addFieldValue(HOTEL_AUTOMATED_HOTEL_PRECIO2, valor.substring(0,valor.length()-3));
				addFieldValue(HOTEL_AUTOMATED_HOTEL_PRECIOM2, valor.substring(valor.length()-3));
			}
		}
		while (tok.hasMoreTokens()) {
			String val = tok.nextToken();
			if (val.startsWith("RQ-"))	addFieldValue(HOTEL_AUTOMATED_HOTEL_REQUESTCLIENTID, val.substring(3));
			else if (val.startsWith("RC-"))	addFieldValue(HOTEL_AUTOMATED_HOTEL_RESPONSECLIENTID, val.substring(3));
			else if (val.startsWith("RG-"))	addFieldValue(HOTEL_AUTOMATED_RG, val.substring(3));
			else if (val.startsWith("CF-"))	addFieldValue(HOTEL_AUTOMATED_CF, val.substring(3));
			else if (val.startsWith("SI-"))	addFieldValue(HOTEL_AUTOMATED_SI, val.substring(3));
			else if (val.startsWith("HFN-"))	addFieldValue(HOTEL_AUTOMATED_HFN, val.substring(4));
			else if (val.startsWith("TTX-"))	addFieldValue(HOTEL_AUTOMATED_TTX, val.substring(4));
			else if (val.startsWith("TSC-"))	addFieldValue(HOTEL_AUTOMATED_TSC, val.substring(4));
			else if (val.startsWith("HTP-"))	addFieldValue(HOTEL_AUTOMATED_HTP, val.substring(4));
			else if (val.startsWith("TX1-"))	addFieldValue(HOTEL_AUTOMATED_TX1, val.substring(4));
			else if (val.startsWith("TX2-"))	addFieldValue(HOTEL_AUTOMATED_TX2, val.substring(4));
			else if (val.startsWith("TX3-"))	addFieldValue(HOTEL_AUTOMATED_TX3, val.substring(4));
			else if (val.startsWith("TX4-"))	addFieldValue(HOTEL_AUTOMATED_TX4, val.substring(4));
			else if (val.startsWith("SC1-"))	addFieldValue(HOTEL_AUTOMATED_SC1, val.substring(4));
			else if (val.startsWith("SC2-"))	addFieldValue(HOTEL_AUTOMATED_SC2, val.substring(4));
			else if (val.startsWith("SC3-"))	addFieldValue(HOTEL_AUTOMATED_SC3, val.substring(4));
			else if (val.startsWith("SC4-"))	addFieldValue(HOTEL_AUTOMATED_SC4, val.substring(4));
			else if (val.startsWith("DS1-"))	addFieldValue(HOTEL_AUTOMATED_DS1, val.substring(4));
			else if (val.startsWith("DS2-"))	addFieldValue(HOTEL_AUTOMATED_DS2, val.substring(4));
			else if (val.startsWith("CMN-"))	addFieldValue(HOTEL_AUTOMATED_CMN, val.substring(4));
			else if (val.startsWith("CMT-"))	addFieldValue(HOTEL_AUTOMATED_CMT, val.substring(4));
			else if (val.startsWith("TAC-"))	addFieldValue(HOTEL_AUTOMATED_TAC, val.substring(4));
			else if (val.startsWith("G"))	addFieldValue(HOTEL_AUTOMATED_G, val.substring(4));
			else if (val.startsWith("D"))	addFieldValue(HOTEL_AUTOMATED_HOTEL_D+"_"+ val.substring(1,2),val.substring(3));
			else if (val.startsWith("T"))	addFieldValue(HOTEL_AUTOMATED_HOTEL_T+"_"+ val.substring(1,2),val.substring(3));
			else if (val.startsWith("S"))	addFieldValue(HOTEL_AUTOMATED_HOTEL_S+"_"+ val.substring(1,2),val.substring(3));
		}
		getInput().mark(1000);
		line = getInput().readLine();
		if (line.startsWith("M3")) {
			getInput().reset();
			return;
		}
			
		if (line.length()>=4)
		addFieldValue(OPTIONAL_HOTEL_INFO2, line.substring(4));
		line = getInput().readLine();
		if (line.length()>=4)
			addFieldValue(OPTIONAL_HOTEL_INFO1, line.substring(4));
	}

	private boolean isHotelItinerary() {
		return getProductCode().equals("3");
	}
	private boolean isLimoItinerary() {
		return getSecondaryProductCode().equals("LIM");
	}
	private boolean isCarItinerary() {
		return getProductCode().equals("A");
	}
	private boolean isTorItinerary() {
		return getSecondaryProductCode().equals("TOR");
	}
	private boolean isAddItinerary() {
		return getSecondaryProductCode().equals("ADD");
	}
	private boolean isRalItinerary() {
		return getProductCode().equals("6");
	}
	private boolean isInsItinerary() {
		return getProductCode().equals("7");
	}
	private boolean isElvaTorItinerary() {
		return getProductCode().equals("G");
	}
	private boolean isPrepayAdvice() {
		return getProductCode().equals("B")||getSecondaryProductCode().equals("BUS");
	}
	private boolean isElvaSeaItinerary() {
		return getProductCode().equals("F");
	}
	private boolean isCruiseItinerary() {
		return getProductCode().equals("D");
	}
	private void readVoyEnBus(int i, String line) throws Exception {
		
		JStringTokenizer tok = JCollectionFactory.createStringTokenizer(line.substring(i),'/');
		tok.skipEmptyTokens(false);
		
		String tok2=tok.nextToken();
		String tok3=tok.nextToken();
		
		addFieldValue(CARRIER_CODE, tok.nextToken());
		
		String departure=tok.nextToken();
		i=5;
		addFieldValue(DEPARTURE_TIME, departure.substring(i,i+=4));
		addFieldValue(DEPARTURE_CITY_NAME, departure.substring(i+1));
		
		String arrive=tok.nextToken();
		i=6;
		addFieldValue(ARRIVAL_TIME, arrive.substring(i, i += 4));
		addFieldValue(ARRIVAL_CITY_NAME, arrive.substring(i+1));

		String classofsrv=tok.nextToken();
		addFieldValue(CLASS_OF_SERVICE, classofsrv);

	}

	private void readAirOrAMTRAKRailItinerary(int i, String line)
			throws Exception {
		addFieldValue(ISSUE_A_BOARDING_PASS, line.substring(i, i += 1));
		addFieldValue(DEPARTURE_CITY_CODE, line.substring(i, i += 3));
		addFieldValue(DEPARTURE_CITY_NAME, line.substring(i, i += 17));
		addFieldValue(ARRIVAL_CITY_CODE, line.substring(i, i += 3));
		addFieldValue(ARRIVAL_CITY_NAME, line.substring(i, i += 17));
		addFieldValue(CARRIER_CODE, line.substring(i, i += 2));
		addFieldValue(FLIGHT_NUMBER, line.substring(i, i += 5));
		addFieldValue(CLASS_OF_SERVICE, line.substring(i, i += 2));
		addFieldValue(DEPARTURE_TIME, line.substring(i, i += 5));
		addFieldValue(ARRIVAL_TIME, line.substring(i, i += 5));
		addFieldValue(ELAPSED_FLYING_TIME, line.substring(i, i += 8));
		addFieldValue(MEAL_SERVICE_INDICATOR, line.substring(i, i += 4));
		addFieldValue(SUPPLEMENTAL_INFORMATION, line.substring(i, i += 1));
		addFieldValue(FLIGHT_ARRIVAL_DATE_CHANGE_INDICATOR,				line.substring(i, i += 1));
		addFieldValue(NUMBER_OF_STOPS, line.substring(i, i += 1));
		addFieldValue(STOPOVER_CITY_CODES, line.substring(i, i += 18));
		addFieldValue(CARRIER_TYPE_CODE, line.substring(i, i += 2));
		addFieldValue(EQUIPMENT_TYPE_CODE, line.substring(i, i += 3));
		addFieldValue(STATUTE_MILES, line.substring(i, i += 6));
		addFieldValue(FREQUENT_TRAVELER_MILES, line.substring(i, i += 6));
		addFieldValue(PRE_RESERVED_SEAT_COUNTER, line.substring(i, i += 2));
		addFieldValue(DEPARTURE_TERMINAL, line.substring(i, i += 26));
		addFieldValue(DEPARTURE_GATE, line.substring(i, i += 4));
		addFieldValue(ARRIVAL_TERMINAL, line.substring(i, i += 26));
		addFieldValue(ARRIVAL_GATE, line.substring(i, i += 4));
		addFieldValue(REPORT_TIME, line.substring(i, i += 5));
		addFieldValue(CHANGE_OF_GAUGE_FUNNLE_FLIGHT_COUNTER,				line.substring(i, i += 1));
		if (i+37<=line.length()) 
		addFieldValue(COMMUTER_CARRIER_NAME, line.substring(i, i += 37));
		if (i+1<=line.length()) 
		addFieldValue(ITINERARY_ITEM_TICKETING_INDICATOR,				line.substring(i, i += 1));
		if (i+2<=line.length()) 
		addFieldValue(SPECIAL_MEAL_REQUEST_COUNTER, line.substring(i, i += 2));
		if (i+4<=line.length()) 
		addFieldValue(FLIGHT_DEPARTURE_YEAR, line.substring(i, i += 4));
		if (i+8<=line.length()) 
			addFieldValue(AIRLINE_PNR_LOCATOR, line.substring(i, i += 8));
		boolean bseat = readPreReservedSeatSection();
		boolean bmeal = readSpecialMealRequestSection();
		boolean both = readFunnelOrChangeOfGaugeSection();
		String aux;
		if (bseat == true) {
			aux = readLine();
			if (aux.length() > 0)
				readLine();
		}
		if (bmeal == true) {
			aux = readLine();
			if (aux.length() > 0)
				readLine();
		}
		if (both == true) {
			aux = readLine();
			if (aux.length() > 0)
				readLine();
		}
	}

	private String readLine() throws Exception {
		getInput().mark(1000);
		String aux = getInput().readLine();
		if (aux.startsWith("M3") || aux.startsWith("M4")
				|| aux.startsWith("M5") || aux.startsWith("M6")
				|| aux.startsWith("M7") || aux.startsWith("M8"))
			getInput().reset();
		return aux;
	}

	private boolean readFunnelOrChangeOfGaugeSection() throws Exception {
		String line = getInput().readLine();
		int i = 0;
		if (line.trim().length() >= 50) {
			addFieldValue(COG_CITY_CODE, line.substring(i, i += 3));
			addFieldValue(COG_CITY_NAME, line.substring(i, i += 17));
			addFieldValue(COG_DEPARTURE_DATE, line.substring(i, i += 5));
			addFieldValue(COG_DEPARTURE_TIME, line.substring(i, i += 5));
			addFieldValue(COG_ARRIVAL_TIME, line.substring(i, i += 5));
			addFieldValue(COG_TERMINAL_IDENTIFIER, line.substring(i, i += 5));
			addFieldValue(COG_GATE_IDENTIFIER, line.substring(i, i += 4));
			addFieldValue(COG_FREQUENT_TRAVELER_MILES,
					line.substring(i, i += 6));
			return true;
		}

		addFieldValue(COG_CITY_CODE, "");
		addFieldValue(COG_CITY_NAME, "");
		addFieldValue(COG_DEPARTURE_DATE, "");
		addFieldValue(COG_DEPARTURE_TIME, "");
		addFieldValue(COG_ARRIVAL_TIME, "");
		addFieldValue(COG_TERMINAL_IDENTIFIER, "");
		addFieldValue(COG_GATE_IDENTIFIER, "");
		addFieldValue(COG_FREQUENT_TRAVELER_MILES, "");
		if (line.trim().length() > 0)
			return true;
		return false;

	}

	private boolean readPreReservedSeatSection() throws Exception {
		String line = getInput().readLine();
		int i = 0;
		if (line.trim().equalsIgnoreCase("") == false) {
			// IU3SST PRE-RESERVED SEAT SECTION
			addFieldValue(INTERFACE_NAME_ITEM_NUMBER, line.substring(i, i += 2));
			addFieldValue(PRE_RESERVED_SEAT_TYPE, line.substring(i, i += 1));
			addFieldValue(SEAT_NUMBER, line.length()<i+4?"":line.substring(i, i += 4));
			return true;
		}
		addFieldValue(INTERFACE_NAME_ITEM_NUMBER, "");
		addFieldValue(PRE_RESERVED_SEAT_TYPE, "");
		addFieldValue(SEAT_NUMBER, "");
		return false;

	}

	private boolean readSpecialMealRequestSection() throws Exception {
		String line = getInput().readLine();
		int i = 0;
		if (line.trim().equalsIgnoreCase("") == false) {
			addFieldValue(MEAL_INTERFACE_NAME_ITEM_NUMBER,
					line.substring(i, i += 2));
			addFieldValue(SPECIAL_MEAL_REQUEST_TYPE_INDICATOR,
					line.substring(i, i += 1));
			if (line.length()>=i+4)
				addFieldValue(SPECIAL_MEAL_TYPE_CODE, line.substring(i, i += 4));
			else
				addFieldValue(SPECIAL_MEAL_TYPE_CODE, line.substring(i));
			return true;
		}
		addFieldValue(MEAL_INTERFACE_NAME_ITEM_NUMBER, "");
		addFieldValue(SPECIAL_MEAL_REQUEST_TYPE_INDICATOR, "");
		addFieldValue(SPECIAL_MEAL_TYPE_CODE, "");
		return false;
	}

	private String getProductCode() {
		return this.getFieldValue(PRODUCT_CODE);
	}

	private String getSecondaryProductCode() {
		return (String) this.getFieldValue(SECONDARY_PRODUCT_CODE);
	}

	private boolean isAirOrAmtrack() {
		return isAMTRAKRailItinerary() || isAirItinerary();
	}

	private boolean isVoyEnBus(String line) {
		if (line.indexOf("VOYENBUS")>=0) 
			return getProductCode().equals("8");
		else 
			return false;
	}

	private boolean isAMTRAKRailItinerary() {
		return getProductCode().equals("1")
				&& (getSecondaryProductCode().equalsIgnoreCase("RAL"));
	}

	public boolean isAirItinerary() {
		// Answer true whether the receiver is an Air itinerary
		return getProductCode().equals("1")
				&& (getSecondaryProductCode().equalsIgnoreCase("AIR"));
	}
	public boolean isBusItinerary() {
		// Answer true whether the receiver is an Air itinerary
		return getProductCode().equals("BUS")
				;
	}

	private boolean isAutomatedHotelItinerary() {
		return getProductCode().equals("3")
				&& (getSecondaryProductCode().equalsIgnoreCase("HHL"));
	}
	private boolean isOther() {
		return !getProductCode().equals("8");
	}
	private boolean isReserva() {
		return !getProductCode().equals("3");
	}

	public String getArrivalCityCode() {
		if (this.getFieldValue(M3Record.ARRIVAL_CITY_CODE).trim().equals(""))
			return "XXX";//pasaje abierto
		return this.getFieldValue(M3Record.ARRIVAL_CITY_CODE);
	}

	public String getDepartureCityCode() {
		if (this.getFieldValue(M3Record.DEPARTURE_CITY_CODE).trim().equals(""))
			return "XXX";//pasaje abierto
		return this.getFieldValue(M3Record.DEPARTURE_CITY_CODE);
	}

	public String getItemNumber() {
		return this.getFieldValue(M3Record.ITINERARY_ITEM_NUMBER);
	}

	public String getCarrierCode() {
		return this.getFieldValue(M3Record.CARRIER_CODE);
	}
	public String getCarrierCodeOp() {
		String cop = this.getFieldValue(M3Record.CARRIER_TYPE_CODE);
		if (cop.trim().isEmpty()) 
			return this.getFieldValue(M3Record.CARRIER_CODE);
		return cop;
	}
	
	public String getFlightNumber() {
		return this.getFieldValue(M3Record.FLIGHT_NUMBER);
	}

	public String getDepartureTime() {
		return Tools.formatTime(this.getFieldValue(M3Record.DEPARTURE_TIME));
	}

	public String getArrivalTime() {
		return Tools.formatTime(this.getFieldValue(M3Record.ARRIVAL_TIME));
	}

	public String getClassOfService() {
		return this.getFieldValue(M3Record.CLASS_OF_SERVICE);
	}

	public String getDepartureDate(long y,Date fechaEmision) throws Exception {
		if (this.getFieldValue(M3Record.DEPARTURE_DATE_OR_RESERVATION_COMMENCEMENT_IN_DATE).equals(""))
				return "";
		String day = this.getFieldValue(M3Record.DEPARTURE_DATE_OR_RESERVATION_COMMENCEMENT_IN_DATE).substring(0, 2);
		String month = Tools.convertMonthStringToNumber(this.getFieldValue(M3Record.DEPARTURE_DATE_OR_RESERVATION_COMMENCEMENT_IN_DATE).substring(2));
		String year = this.getFieldValue(M3Record.FLIGHT_DEPARTURE_YEAR);
		if (year == null || year.equals("")) {
		 	if (y==0)
				year = JDateTools.DateToString(new Date(), "yyyy");
			else
				year = ""+y;
		 	
		 	if ((year + month + day).compareTo(JDateTools.DateToString(fechaEmision,"yyyyMMdd"))<1) year=""+(y+1);
		}
		return year + month + day;
	}
	public String getFechaSalida(long y,Date fechaEmision) throws Exception {
		String l = this.getFieldValue(M3Record.HOTEL_CONCLUSION);
		if (l.equals(""))
				return "";
		
		//-OUT27FEB  1NT
		if (!l.startsWith("-"))
			l="-"+l;
		String day = l.substring(4, 6);
		String month = Tools.convertMonthStringToNumber(l.substring(6,9));
		String year =  ""+y;
		return year + month + day;
	}
	public long getNoches() throws Exception {
		String l = this.getFieldValue(M3Record.HOTEL_CONCLUSION);

		if (!l.endsWith("NT")) return 0;
		if (l.equals(""))
				return 0;
		
		//-OUT27FEB  1NT
		String noches = l.substring(9,12).trim();
		if (noches.equals("")) return 0;
		return Long.parseLong(noches);
	}
	public long getHabitaciones() throws Exception {
		
		String l = this.getFieldValue(M3Record.HOTEL_NUMBER_OF_ROOMS);

		if (l==null || l.equals(""))
				return 0;
		
		String hab = ""+l.charAt(0);
		return Long.parseLong(hab);
	}
	public String getProductCodeDet() throws Exception {
		String l = this.getFieldValue(M3Record.HOTEL_NUMBER_OF_ROOMS);

		if (l==null || l.equals(""))
				return "1";
		
		return this.getFieldValue(M3Record.HOTEL_NUMBER_OF_ROOMS).substring(1);
	}

	public String getArrivalDate(long y,Date fechaEmision) throws Exception {
		Date dep = JDateTools.StringToDate(this.getDepartureDate(y,fechaEmision), "yyyyMMdd");
		if (dep==null) return "";
		int daysfromdeparture = this.getDifferenceDaysFromDeparture();
		Date arr;
		arr = JDateTools.addDays(dep, daysfromdeparture);
		String ret = JDateTools.DateToString(arr, "yyyyMMdd");
		return ret;
	}

	public int getDifferenceDaysFromDeparture() {
		String fa = this.getFieldValue(FLIGHT_ARRIVAL_DATE_CHANGE_INDICATOR);
		if (fa == null)
			return 0;
		if (fa.equals(""))
			return 0;
		int id = Integer.parseInt(fa);
		switch (id) {
		case 0:
		case 1:
		case 2:
			return id;
		case 4:
			return -1;
		case 5:
			return -2;
		}
		return 0;
	}

	public String getSegmentStatusCode() {
		return this.getFieldValue(ACTION_ADVICE_SEGMENT_STATUS_CODES);
	}

	public boolean isHoldingConfirmed() {
		return getSegmentStatusCode().equals("HK");
	}

	public String getMealServiceCode() {
		return this.getFieldValue(M3Record.MEAL_SERVICE_INDICATOR);
	}

	public String getEquipmentTypeCode() {
		return this.getFieldValue(M3Record.EQUIPMENT_TYPE_CODE);
	}

	public String getElapsedTimeCode() {
		return this.getFieldValue(M3Record.ELAPSED_FLYING_TIME).replace(".", "");
	}



}
