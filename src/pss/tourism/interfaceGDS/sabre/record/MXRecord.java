package pss.tourism.interfaceGDS.sabre.record;

import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;
import pss.tourism.interfaceGDS.Tools;

public class MXRecord extends SabreRecord {

	public static final String MESSAGE_ID = "IU2MID"; //
	public static final String INTERFACE_NAME_ITEM_NUMBER = "IU2PNO"; //
	public static final String RECORD_TYPE = "IU2PTY"; //
	public static final String NUMBER_OF_RECORDS_OF_TYPE = "IU2TCN"; //
	public static final String SPARE1 = "IU2INT"; //
	public static final String FORMS_OF_PAYMENT_SELF_SALE_INDICATOR = "IU2SSI"; //
	public static final String TICKET_INDICATOR = "IU2IND"; //
	public static final String COMBINED_TICKET_BOARDING_PASS = "IU2AP1"; //
	public static final String TICKET_ONLY = "IU2AP2"; //
	public static final String BOARDING_PASS_ONLY = "IU2AP3"; //
	public static final String REMOTE_PRINT_AUDITORS_COUPON = "IU2AP4"; //
	public static final String PASSENGER_RECEIPT_ONLY_FUTURE = "IU2AP5"; //
	public static final String REMOTE_PRINT_AGENTS_COUPON = "IU2AP6"; //
	public static final String REMOTE_PRINT_CREDIT_CARD_CHARGE_FORM = "IU2AP7"; //
	public static final String INVOICE_DOCUMENT_FUTURE = "IU2AP8"; //
	public static final String MINI_ITINERARY = "IU2AP9"; //
	public static final String MAGNETIC_ENCODING_FUTURE = "IU2APA"; //
	public static final String US_CONTRACT_WHOLESALE_BULK_TICKET = "IU2APB"; //
	public static final String BSP_TICKET_SUPPRESSION_INDICATOR = "IU2APZ"; //

	public static final String FARE_SIGN = "IU2FSN"; //
	public static final String FARE_AREA = "IU2FRE"; //
	public static final String FARE_CURRENCY_CODE = "IU2FCC"; //
	public static final String FARE_AMOUNT = "IU2FAR"; //

	public static final String TAX_1_SIGN = "IU2T1S"; //
	public static final String TAX_1_AMOUNT = "IU2TX1"; //
	public static final String TAX_1_ID = "IU2ID1"; //
	public static final String TAX_2_SIGN = "IU2T2S"; //
	public static final String TAX_2_AMOUNT = "IU2TX2"; //
	public static final String TAX_2_ID = "IU2ID2"; //
	public static final String TAX_3_SIGN = "IU2T3S"; //
	public static final String TAX_3_AMOUNT = "IU2TX3"; //
	public static final String TAX_3_ID = "IU2ID3"; //

	public static final String TOTAL_FARE_SIGN = "IU2TFS"; //
	public static final String TOTAL_FARE_AREA = "IU2TOT"; //
	public static final String TOTAL_FARE_CURRENCY_CODE = "IU2TFC"; //
	public static final String TOTAL_FARE_AMOUNT = "IU2TFR"; //

	public static final String CANCELLATION_PENALTY_AMOUNT = "IU2PEN"; //
	public static final String COMMISSION_ON_PENALTY = "IU2KXP"; //
	public static final String SPARE = "IU2SPX"; //

	public static final String EQUIVALENT_PAID_SIGN = "IU2EPS"; //
	public static final String EQUIVALENT_PAID_AREA = "IU2EQV"; //
	public static final String EQUIVALENT_FARE_CURRENCY_CODE = "IU2EFC"; //
	public static final String EQUIVALENT_FARE_AMOUNT = "IU2EFR"; //

	public static final String COMMISSION_PERCENTAGE = "IU2PCT"; //
	public static final String COMMISSION_SIGN = "IU2CSN"; //
	public static final String COMMISSION_AMOUNT = "IU2COM"; //
	public static final String NET_AMOUNT_SIGN = "IU2NAS"; //
	public static final String NET_AMOUNT = "IU2NET"; //
	public static final String CANADIAN_TICKET_DESIGNATOR_CODE = "IU2CDC"; //
	public static final String CANADIAN_TICKET_TRAVEL_CODE = "IU2CTT"; //
	public static final String FOREIGN_TARIFF_FLAG = "IU2FTF"; //
	public static final String TRAVEL_AGENCY_TAX = "IU2TAT"; //
	public static final String INCLUSIVE_TOUR_IT_NUMBER = "IU2TUR"; //
	public static final String TICKETING_CITY = "IU2TPC"; //
	public static final String PRINT_MINI_ITINERARY = "IU2MIT"; //
	public static final String CREDIT_CARD_INFORMATION_PRINT_SUPPRESSION = "IU2CCB"; //
	public static final String CREDIT_CARD_AUTHORIZATION_SOURCE_CODE = "IU2APC"; //

	public static final String FARE_AGENT_SINE = "IU2SNF"; //
	public static final String PSEUDO_CITY_CODE = "IU2SN4"; //
	public static final String AGENT_DUTY_CODE = "IU2SN3"; //
	public static final String AGENT_SINE = "IU2SN2"; //

	public static final String PRINT_AGENT_SINE_FULL = "IU2SNP"; //
	public static final String PRINT_PSEUDO_CITY_CODE = "IU2PN4"; //
	public static final String PRINT_AGENT_DUTY_CODE = "IU2PN3"; //
	public static final String PRINT_AGENT_SINE = "IU2PN2"; //

	public static final String SPARE2 = "IU2AVI"; //
	public static final String CREDIT_CARD_AUTHORIZATION = "IU2ATH"; //
	public static final String SPARES = "IU2SPR"; //
	public static final String CREDIT_CARD_EXPIRATION_DATE_Format_MMYY = "IU2EXP"; //
	public static final String CREDIT_CARD_EXTENDED_PAYMENT_MONTHS = "IU2EXT"; //
	public static final String COUNT_OF_M4_RECORDS = "IU2M4C"; //
	public static final String COUNT_OF_M6_RECORDS = "IU2M6C"; //
	public static final String VALIDATING_CARRIER_CODE = "IU2VAL"; //
	public static final String TICKET_NUMBER = "IU2TNO"; //
	public static final String CONJUNCTION_TICKET_COUNT = "IU2DNO"; //
	public static final String CARRIAGE_RETURN = "IU2CR1"; //

	public static final String ENTITLEMENT_ITEM_NUMBERS = "IU2NM4"; //
	public static final String M6_FARE_CALCULATION_ITEM_NUMBERS = "IU2NM6"; //
	public static final String FORM_OF_PAYMENT = "IU2FOP"; //
	public static final String ORIGINAL_ISSUE = "IU2ORG";//
	public static final String ISSUED_IN_EXCHANGE = "IU2IIE"; //
	public static final String ENDORSEMENTS = "IU2END"; //
	public static final String JOURNEY_IDENTIFICATION_CODE = "IU2JIC"; //
	public static final String NET_REMIT_TICKETING = "IU2NRT"; //
	public static final String REFUND_AUTHORIZATION_FOR_ETR = "IU2REA"; //
	public static final String DISCOUNT_CODE = "IU2DCD"; //
	public static final String SCN_for_ATB = "IU2SCN"; //

	public MXRecord() {
		ID = SabreRecord.MX;
	}

	public boolean isTaxType() {
		if (getFieldValue(RECORD_TYPE).equals("01"))
			return true;
		else
			return false;
	}

	public boolean isTicketAmountType() {
		if (getFieldValue(RECORD_TYPE).equals("02"))
			return true;
		else
			return false;
	}

	public int getNunmberOfDetailRecords() {
		String recs = getFieldValue(NUMBER_OF_RECORDS_OF_TYPE);
		return Integer.parseInt(recs);
	}

	// ==========================================================================
	// M2 Record - Passenger Ticket Data Record
	// ==========================================================================
	// This record contains passenger ticket data. This data is needed for the
	// printing
	// of the ticket. There is one M2 record per passenger ticketed.
	public String doProcess(JMap<String, Object> mRecords, String line)
			throws Exception {
		if (line.startsWith("MXP")) {
			this.getInput().reset();
			return line;
			
		}
		if (line.startsWith("MXL")) {
			this.getInput().reset();
			return line;
			
		}
		if (line.startsWith("MXR")) {
			this.getInput().reset();
			return line;
			
		}
		int i = 2;
		addFieldValue(MESSAGE_ID, ID); // MESSAGE ID

		int rmi = ((M0Record) mRecords.getElement(SabreRecord.M0)).getNumberOfM5();

//		for (int index = 1; index <= rmi * 2 ; index++) {
		for (int index = 1; line.startsWith("MX") ; index++) {
			if (index > 1) {
				line = getInput().readLine();
				if (line == null)
					break;
				if (line.trim().equals("")) {
					index--;
					continue;
				}
			}
			processData(line);
		}

		return line;

	}

	public String getFareAmount(String ticket, String code) throws Exception {
		JMap<String, String> aux = mfares.getElement(ticket);
		String amt = aux.getElement(code);
		if (amt == null)
			return "0";
		if (amt.trim().equals(""))
			return "0";
		if (amt.substring(amt.length() - 4, amt.length() - 3).equals("A"))
			return amt.substring(0, amt.length() - 4);
		return amt.substring(0, amt.length() - 3);
	}

	public String getFareCurrency(String ticket, String code) throws Exception {
		JMap<String, String> aux = mfares.getElement(ticket);
		String cur = aux.getElement(code);
		return cur.substring(cur.length() - 3);
	}

	public JMap<String, String> getTaxes(String ticket) throws Exception {
		return mtaxes.getElement(ticket);
	}

	JMap<String, JMap<String, String>> mtaxes = JCollectionFactory.createMap(10);
	JMap<String, JMap<String, String>> mfares = JCollectionFactory.createMap(10);

	JMap<String, String> taxes = JCollectionFactory.createMap(10);
	JMap<String, String> fares = JCollectionFactory.createMap(10);

	public void processData(String line) throws Exception {
		if (line.equals("MX" ))
			return;
		int i = 2;
		if (line.length()<i+2) return;
		addFieldValue(INTERFACE_NAME_ITEM_NUMBER, line.substring(i, i += 3));
		if (line.length()<i+2) return;
		addFieldValue(RECORD_TYPE, line.substring(i, i += 2));
		if (line.length()<i+5) return;
		
		addFieldValue(SPARE1, line.substring(i, i += 5));
	
		if (line.length()<i+10)
			return;
		addFieldValue(TICKET_NUMBER, line.substring(i, i += 10));

		addFieldValue(NUMBER_OF_RECORDS_OF_TYPE, line.substring(i, i += 4));

		String aux = line.substring(i);

		if (isTaxType())
			processTaxType(aux.trim());

		if (isTicketAmountType())
			processAmountType(aux.trim());

		while (true) {
			this.getInput().mark(1000);
			line = this.getInput().readLine();
			if (line == null)
				break;
			if (line.startsWith(MX)) {
				this.getInput().reset();
				break;
			}
		}

	}

	private void processAmountType(String line) throws Exception {
		String ticket = getFieldValue(TICKET_NUMBER);
		int recs = this.getNunmberOfDetailRecords();

		String code = line.substring(0, 1);
		String value = line.substring(1);
		PssLogger.logDebug("Fare: " + code + "-" + value);

		fares = JCollectionFactory.createMap();
		fares.addElement(code, value);

		for (int i = 1; i <= recs - 1; i++) {
			String line2 = (getInput().readLine()).trim();

			code = line2.substring(0, 1);
			value = line2.substring(1);
			PssLogger.logDebug("Fare: " + code + "-" + value);

			fares.addElement(code, value);
		}
		mfares.addElement(ticket, fares);

	}

	private void processTaxType(String line) throws Exception {
		String ticket = getFieldValue(TICKET_NUMBER);
		int recs = this.getNunmberOfDetailRecords();
		
		if (line.trim().equals("")==true) return;
		
		String code = line.substring(line.length() - 2);
		String value = "";
		taxes = JCollectionFactory.createMap();

		if (line.trim().startsWith("P") == false) {
			value = line.substring(0, line.length() - 2);
			PssLogger.logDebug("Impuesto: " + code + "-" + value);
			taxes.addElement(code, value);
		}

		for (int i = 1; i <= recs - 1; i++) {
			String line2 = (getInput().readLine()).trim();

			code = line2.substring(line2.length() - 2);
			// value = line2.substring(0, line2.length() - 2);
			if (line2.trim().startsWith("P")==false) {
				value = line2.substring(0, line2.length() - 2);
				PssLogger.logDebug("Impuesto: " + code + "-" + value);
				String oldValue=taxes.getElement(code);
				if (oldValue!=null) {
					oldValue=taxes.getElement(code+"2");
					if (oldValue!=null) 
						taxes.addElement(code+"3", value);
					else
						taxes.addElement(code+"2", value);
				} else
					taxes.addElement(code, value);
			}
		}
		mtaxes.addElement(ticket, taxes);

	}

	public double getCreditCardAmount() {
		String nrt = this.getFieldValue(NET_REMIT_TICKETING);
		JStringTokenizer tok = JCollectionFactory.createStringTokenizer(nrt,
				'/');
		while (tok.hasMoreTokens()) {
			String stok = tok.nextToken();
			if (stok.startsWith("CCAM")) {
				double am = Double.parseDouble(stok.substring(4).trim());
				return am;
			}
		}
		return 0.0f;
	}

	public String getTax1Id() {
		return this.getFieldValue(MXRecord.TAX_1_ID);
	}

	public String getTicketNumber() {
		return this.getFieldValue(MXRecord.TICKET_NUMBER);
	}

	public String getTax1Amount() throws Exception {
		return Tools.getOnlyNumbers(this.getFieldValue(MXRecord.TAX_1_AMOUNT));
	}

	public String getTax2Amount() throws Exception {
		return Tools.getOnlyNumbers(this.getFieldValue(MXRecord.TAX_2_AMOUNT));
	}

	public String getTax3Amount() throws Exception {
		return Tools.getOnlyNumbers(this.getFieldValue(MXRecord.TAX_3_AMOUNT));
	}

	public double getTaxXTAmount() throws Exception {
		return Double.parseDouble(getTax3Amount());
	}

	public String getTotalFareCurrencyCode() throws Exception {
		String curr = this.getFieldValue(MXRecord.TOTAL_FARE_CURRENCY_CODE);
		if (curr == null || curr.length() == 0)
			return "ARS";
		return curr;
	}

	public String getTax2Id() {
		return this.getFieldValue(MXRecord.TAX_2_ID);
	}

	public String getTax3Id() {
		return this.getFieldValue(MXRecord.TAX_3_ID);
	}

	public String getPassengerType() {
		return this.getFieldValue(MXRecord.RECORD_TYPE);
	}

	public String getFareAmount() throws Exception {
		return Tools.getOnlyNumbers(this.getFieldValue(MXRecord.FARE_AMOUNT));
	}

	public String getTravelAgencyTax() throws Exception {
		return Tools.getOnlyNumbers(this
				.getFieldValue(MXRecord.TRAVEL_AGENCY_TAX));
	}

	public String getPaxId() {
		return Integer.parseInt(this
				.getFieldValue(MXRecord.INTERFACE_NAME_ITEM_NUMBER)) + "";
	}

	public String getTotalFareAmount() throws Exception {
		String sfa = Tools.getOnlyNumbers(this
				.getFieldValue(MXRecord.TOTAL_FARE_AMOUNT));
		if (sfa == null || sfa.length() == 0)
			return "0";
		return sfa;
	}

	public String getNetAmount() throws Exception {
		String na =  Tools.getOnlyNumbers(this
				.getFieldValue(MXRecord.EQUIVALENT_FARE_AMOUNT));
		if (na==null || na.length()==0)
			return "0";
		return na;
	}
	

	public String getNetAmountInCurrentCurrency() throws Exception {
		
		String nac= Tools.getOnlyNumbers(this.getFieldValue(MXRecord.FARE_AMOUNT));
		if (nac==null || nac.length()==0)
			return "0";
		return nac;
	}

	public String getRealNetAmount() throws Exception {
		String rna= Tools.getOnlyNumbers(this.getFieldValue(MXRecord.NET_AMOUNT));
		if (rna==null || rna.length()==0)
			return "0";
		return rna;
	}

	public String getFareCurrencyCode() {
		return this.getFieldValue(MXRecord.FARE_CURRENCY_CODE);
	}

	public String getCarrierCode() {
		return this.getFieldValue(MXRecord.VALIDATING_CARRIER_CODE);
	}

	public int getConjuctionTicketCount() {
		return Integer.parseInt(this
				.getFieldValue(MXRecord.CONJUNCTION_TICKET_COUNT));
	}

	public double getCommision() {
		String ca = this.getFieldValue(MXRecord.COMMISSION_AMOUNT);
		if (ca.equals(""))
			ca = "0.0";
		return Double.parseDouble(ca);
	}

	public double getCommisionPercentaje() {
		String ca = this.getFieldValue(MXRecord.COMMISSION_PERCENTAGE);
		if (ca.equals(""))
			ca = "0.0";
		return Double.parseDouble(ca);
	}

	public boolean isElectronicTicket() {
		return this.getFieldValue(MXRecord.TICKET_INDICATOR).equals("2");
	}

	public double getExchangeRate() throws Exception {
		double na = Double.parseDouble(this.getNetAmount());
		double fa = Double.parseDouble(this.getFareAmount());
		if (na == 0 || fa == 0)
			return 1;
		return na / fa;
	}

	public boolean isReIssue() throws Exception {
		String sfa = this.getTotalFareAmount();
		double fa = Double.parseDouble(sfa);
		return fa == 0;
	}

	public boolean isSomeKindOfError() throws Exception {
		double tfa = Double.parseDouble(this.getTotalFareAmount());
		double fa = Double.parseDouble(this.getFareAmount());

		return (tfa < fa && this.getFareCurrencyCode().equals(
				SabreRecord.CURR_ARS));
	}

	// Public Function isSomeKindOfError() As Boolean
	// Return ((Decimal.Compare(New Decimal(Conversion.Val(("" &
	// InterfaceCommonFunctions.getFieldValueString(Me.fields, "IU2TFR")))),
	// Me.fareAmount) < 0) And (Strings.Trim(Me.fareCurrencyCode) = "ARS"))
	// End Function
	//
	// Public Function isReIssue() As Boolean
	// ' Answer true whether the receiver is a reissue ticket
	// Return (Decimal.Compare(New Decimal(Conversion.Val(("" &
	// InterfaceCommonFunctions.getFieldValueString(Me.fields, "IU2TFR")))),
	// Decimal.Zero) = 0)
	// End Function
	//
	// Public Function CreditCardAuthCode() As String
	// Return
	// Strings.Trim(InterfaceCommonFunctions.getFieldValueString(Me.fields,
	// "IU2ATH"))
	// End Function
	//
	//
	// Public Function CreditCardExpirationDate() As String
	// Return InterfaceCommonFunctions.getFieldValueString(Me.fields, "IU2EXP")
	// End Function
	//
	//
	//
	// Public Function EquivalentAmount() As Decimal
	// Return New Decimal(Conversion.Val(("" &
	// InterfaceCommonFunctions.getFieldValueString(Me.fields, "IU2EFR"))))
	// End Function
	//
	//
	// Public Function formOfPaymentCode() As String
	// Return RemarkFunctions.formOfPaymentCodeFrom(Me)
	// End Function

	//
	// Public Function isSomeKindOfError() As Boolean
	// Return ((Decimal.Compare(New Decimal(Conversion.Val(("" &
	// InterfaceCommonFunctions.getFieldValueString(Me.fields, "IU2TFR")))),
	// Me.fareAmount) < 0) And (Strings.Trim(Me.fareCurrencyCode) = "ARS"))
	// End Function

}
