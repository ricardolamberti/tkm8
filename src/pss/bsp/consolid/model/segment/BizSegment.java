package pss.bsp.consolid.model.segment;

import pss.core.services.fields.JDate;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizSegment extends JRecord {

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Declaración de variables
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Declaración de variables
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private JLong pIdSegment = new JLong();
	private JString pPnrLocator = new JString();
	private JString pProductCode = new JString();
	private JDate pDepartureDate = new JDate();
	private JString pOriginCityCode = new JString();
	private JString pOriginCityName = new JString();
	private JString pArrivalCityCode = new JString();
	private JString pArrivalCityName = new JString();
	private JString pCarrierCode = new JString();
	private JString pFlightNumber = new JString();
	private JString pClassOfService = new JString();
	private JString pDepartureTime = new JString();
	private JString pArrivalTime = new JString();
	private JString pElapsedFlyingTime = new JString();
	private JString pMealServiceIndicator = new JString();
	private JString pSupplementalInf = new JString();
	private JString pFlightArrDateChanInd = new JString();
	private JString pStopoverCityCodes = new JString();
	private JString pTerminalIdDeparture = new JString();
	private JString pGateDeparture = new JString();
	private JString pTerminalIdArrival = new JString();
	private JString pArrivalGate = new JString();
	private JString pSeatNumber = new JString();
	private JString pSecondaryProductCode = new JString();
	private JString pConfirmationNumber = new JString();
	private JString pStatus = new JString();
	private JString pItemNumber = new JString();
	private JString pQuantity = new JString();
	private JLong pLocalRate = new JLong();
	private JString pLocalCurrency = new JString();
	private JString pAddress = new JString();
	private JString pPhoneInformation = new JString();
	private JString pVA = new JString();
	private JString pVB = new JString();
	private JString pVC = new JString();
	private JString pVD = new JString();
	private JString pVE = new JString();
	private JString pVF = new JString();
	private JString pVG = new JString();
	private JString pVH = new JString();
	private JString pVI = new JString();
	private JString pVJ = new JString();
	private JString pVK = new JString();
	private JString pVL = new JString();
	private JString pVM = new JString();
	private JString pVN = new JString();
	private JString pVO = new JString();
	private JString pVP = new JString();
	private JString pVQ = new JString();
	private JString pVR = new JString();
	private JString pVS = new JString();
	private JString pVT = new JString();
	private JString pVU = new JString();
	private JString pVV = new JString();
	private JString pVW = new JString();
	private JString pVX = new JString();
	private JString pVY = new JString();
	private JString pVZ = new JString();
	private JString pBaggageAllowance = new JString();
	private JString pFareBasisCode = new JString();
	private JString pFareBasisCodeExp = new JString();
	private JDate pCreationDate = new JDate();
	private JDate pLastUpdateDate = new JDate();
	private JDate pArrivalDate = new JDate();
	private JLong pMiles = new JLong();
	private JString pConexion = new JString();
	private JLong pOrgId = new JLong();

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Constructor de la clase
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BizSegment() throws Exception {
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Métodos para crear propiedades
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void createProperties() throws Exception {
		this.addItem("id_segment", pIdSegment);
		this.addItem("pnr_locator", pPnrLocator);
		this.addItem("product_code", pProductCode);
		this.addItem("departure_date", pDepartureDate);
		this.addItem("origin_city_code", pOriginCityCode);
		this.addItem("origin_city_name", pOriginCityName);
		this.addItem("arrival_city_code", pArrivalCityCode);
		this.addItem("arrival_city_name", pArrivalCityName);
		this.addItem("carrier_code", pCarrierCode);
		this.addItem("flight_number", pFlightNumber);
		this.addItem("class_of_service", pClassOfService);
		this.addItem("departure_time", pDepartureTime);
		this.addItem("arrival_time", pArrivalTime);
		this.addItem("elapsed_flying_time", pElapsedFlyingTime);
		this.addItem("meal_service_indicator", pMealServiceIndicator);
		this.addItem("supplemental_inf", pSupplementalInf);
		this.addItem("flight_arr_date_chan_ind", pFlightArrDateChanInd);
		this.addItem("stopover_city_codes", pStopoverCityCodes);
		this.addItem("terminal_id_departure", pTerminalIdDeparture);
		this.addItem("gate_departure", pGateDeparture);
		this.addItem("terminal_id_arrival", pTerminalIdArrival);
		this.addItem("arrival_gate", pArrivalGate);
		this.addItem("seat_number", pSeatNumber);
		this.addItem("secondary_product_code", pSecondaryProductCode);
		this.addItem("confirmation_number", pConfirmationNumber);
		this.addItem("status", pStatus);
		this.addItem("item_number", pItemNumber);
		this.addItem("quantity", pQuantity);
		this.addItem("local_rate", pLocalRate);
		this.addItem("local_currency", pLocalCurrency);
		this.addItem("address", pAddress);
		this.addItem("phone_information", pPhoneInformation);
		this.addItem("v_a", pVA);
		this.addItem("v_b", pVB);
		this.addItem("v_c", pVC);
		this.addItem("v_d", pVD);
		this.addItem("v_e", pVE);
		this.addItem("v_f", pVF);
		this.addItem("v_g", pVG);
		this.addItem("v_h", pVH);
		this.addItem("v_i", pVI);
		this.addItem("v_j", pVJ);
		this.addItem("v_k", pVK);
		this.addItem("v_l", pVL);
		this.addItem("v_m", pVM);
		this.addItem("v_n", pVN);
		this.addItem("v_o", pVO);
		this.addItem("v_p", pVP);
		this.addItem("v_q", pVQ);
		this.addItem("v_r", pVR);
		this.addItem("v_s", pVS);
		this.addItem("v_t", pVT);
		this.addItem("v_u", pVU);
		this.addItem("v_v", pVV);
		this.addItem("v_w", pVW);
		this.addItem("v_x", pVX);
		this.addItem("v_y", pVY);
		this.addItem("v_z", pVZ);
		this.addItem("baggage_allowance", pBaggageAllowance);
		this.addItem("fare_basis_code", pFareBasisCode);
		this.addItem("fare_basis_code_exp", pFareBasisCodeExp);
		this.addItem("creation_date", pCreationDate);
		this.addItem("last_update_date", pLastUpdateDate);
		this.addItem("arrival_date", pArrivalDate);
		this.addItem("miles", pMiles);
		this.addItem("conexion", pConexion);
		this.addItem("org_id", pOrgId);
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Métodos para crear propiedades fijas
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "id_segment", "ID Segment", true, true, 22);
		this.addFixedItem(FIELD, "pnr_locator", "PNR Locator", true, false, 100);
		this.addFixedItem(FIELD, "product_code", "Product Code", true, false, 30);
		this.addFixedItem(FIELD, "departure_date", "Departure Date", true, false, 7);
		this.addFixedItem(FIELD, "origin_city_code", "Origin City Code", true, false, 30);
		this.addFixedItem(FIELD, "origin_city_name", "Origin City Name", true, false, 100);
		this.addFixedItem(FIELD, "arrival_city_code", "Arrival City Code", true, false, 30);
		this.addFixedItem(FIELD, "arrival_city_name", "Arrival City Name", true, false, 100);
		this.addFixedItem(FIELD, "carrier_code", "Carrier Code", true, false, 100);
		this.addFixedItem(FIELD, "flight_number", "Flight Number", true, false, 70);
		this.addFixedItem(FIELD, "class_of_service", "Class of Service", true, false, 20);
		this.addFixedItem(FIELD, "departure_time", "Departure Time", true, false, 50);
		this.addFixedItem(FIELD, "arrival_time", "Arrival Time", true, false, 50);
		this.addFixedItem(FIELD, "elapsed_flying_time", "Elapsed Flying Time", true, false, 80);
		this.addFixedItem(FIELD, "meal_service_indicator", "Meal Service Indicator", true, false, 40);
		this.addFixedItem(FIELD, "supplemental_inf", "Supplemental Inf", true, false, 30);
		this.addFixedItem(FIELD, "flight_arr_date_chan_ind", "Flight Arr Date Chan Ind", true, false, 30);
		this.addFixedItem(FIELD, "stopover_city_codes", "Stopover City Codes", true, false, 180);
		this.addFixedItem(FIELD, "terminal_id_departure", "Terminal ID Departure", true, false, 200);
		this.addFixedItem(FIELD, "gate_departure", "Gate Departure", true, false, 40);
		this.addFixedItem(FIELD, "terminal_id_arrival", "Terminal ID Arrival", true, false, 260);
		this.addFixedItem(FIELD, "arrival_gate", "Arrival Gate", true, false, 40);
		this.addFixedItem(FIELD, "seat_number", "Seat Number", true, false, 40);
		this.addFixedItem(FIELD, "secondary_product_code", "Secondary Product Code", true, false, 30);
		this.addFixedItem(FIELD, "confirmation_number", "Confirmation Number", true, false, 30);
		this.addFixedItem(FIELD, "status", "Status", true, false, 20);
		this.addFixedItem(FIELD, "item_number", "Item Number", true, false, 20);
		this.addFixedItem(FIELD, "quantity", "Quantity", true, false, 11);
		this.addFixedItem(FIELD, "local_rate", "Local Rate", true, false, 22);
		this.addFixedItem(FIELD, "local_currency", "Local Currency", true, false, 3);
		this.addFixedItem(FIELD, "address", "Address", true, false, 200);
		this.addFixedItem(FIELD, "phone_information", "Phone Information", true, false, 200);
		this.addFixedItem(FIELD, "v_a", "V A", true, false, 200);
		this.addFixedItem(FIELD, "v_b", "V B", true, false, 200);
		this.addFixedItem(FIELD, "v_c", "V C", true, false, 200);
		this.addFixedItem(FIELD, "v_d", "V D", true, false, 200);
		this.addFixedItem(FIELD, "v_e", "V E", true, false, 200);
		this.addFixedItem(FIELD, "v_f", "V F", true, false, 200);
		this.addFixedItem(FIELD, "v_g", "V G", true, false, 200);
		this.addFixedItem(FIELD, "v_h", "V H", true, false, 200);
		this.addFixedItem(FIELD, "v_i", "V I", true, false, 200);
		this.addFixedItem(FIELD, "v_j", "V J", true, false, 200);
		this.addFixedItem(FIELD, "v_k", "V K", true, false, 200);
		this.addFixedItem(FIELD, "v_l", "V L", true, false, 200);
		this.addFixedItem(FIELD, "v_m", "V M", true, false, 200);
		this.addFixedItem(FIELD, "v_n", "V N", true, false, 200);
		this.addFixedItem(FIELD, "v_o", "V O", true, false, 200);
		this.addFixedItem(FIELD, "v_p", "V P", true, false, 200);
		this.addFixedItem(FIELD, "v_q", "V Q", true, false, 200);
		this.addFixedItem(FIELD, "v_r", "V R", true, false, 200);
		this.addFixedItem(FIELD, "v_s", "V S", true, false, 200);
		this.addFixedItem(FIELD, "v_t", "V T", true, false, 200);
		this.addFixedItem(FIELD, "v_u", "V U", true, false, 200);
		this.addFixedItem(FIELD, "v_v", "V V", true, false, 200);
		this.addFixedItem(FIELD, "v_w", "V W", true, false, 200);
		this.addFixedItem(FIELD, "v_x", "V X", true, false, 200);
		this.addFixedItem(FIELD, "v_y", "V Y", true, false, 200);
		this.addFixedItem(FIELD, "v_z", "V Z", true, false, 200);
		this.addFixedItem(FIELD, "baggage_allowance", "Baggage Allowance", true, false, 30);
		this.addFixedItem(FIELD, "fare_basis_code", "Fare Basis Code", true, false, 70);
		this.addFixedItem(FIELD, "fare_basis_code_exp", "Fare Basis Code Exp", true, false, 120);
		this.addFixedItem(FIELD, "creation_date", "Creation Date", true, false, 7);
		this.addFixedItem(FIELD, "last_update_date", "Last Update Date", true, false, 7);
		this.addFixedItem(FIELD, "arrival_date", "Arrival Date", true, false, 7);
		this.addFixedItem(FIELD, "miles", "Miles", true, false, 22);
		this.addFixedItem(FIELD, "conexion", "Conexion", true, false, 1);
		this.addFixedItem(FIELD, "org_id", "Org ID", true, false, 22);
	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "apps.CNS_SEGMENTS_ALL_V";
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Default read() method
	 */
	public boolean read(long zId) throws Exception {
		addFilter("id_segment", zId);
		return read();
	}

}
