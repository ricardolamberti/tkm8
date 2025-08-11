package pss.tourism.interfaceGDS.sabre.record;

import pss.common.regions.currency.BizMonedaPais;
import pss.common.regions.currency.GuiMoneda;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;
import pss.tourism.interfaceGDS.Tools;

public class M2Record extends SabreRecord {

  public static final String  MESSAGE_ID="IU2MID"; //
  public static final String  INTERFACE_NAME_ITEM_NUMBER="IU2PNO"; //
  public static final String  PASSENGER_TYPE="IU2PTY"; //
  public static final String  TCN_NUMBER_TRANSACTION_CONTROL_NUMBER="IU2TCN"; //
  public static final String  INTERNATIONAL_ITINERARY_INDICATOR="IU2INT"; //
  public static final String  FORMS_OF_PAYMENT_SELF_SALE_INDICATOR="IU2SSI"; //
  public static final String  TICKET_INDICATOR="IU2IND"; //
  public static final String  COMBINED_TICKET_BOARDING_PASS="IU2AP1"; //
  public static final String  TICKET_ONLY="IU2AP2"; //
  public static final String  BOARDING_PASS_ONLY="IU2AP3"; //
  public static final String  REMOTE_PRINT_AUDITORS_COUPON="IU2AP4"; //
  public static final String  PASSENGER_RECEIPT_ONLY_FUTURE="IU2AP5"; //
  public static final String  REMOTE_PRINT_AGENTS_COUPON="IU2AP6"; //
  public static final String  REMOTE_PRINT_CREDIT_CARD_CHARGE_FORM="IU2AP7"; //
  public static final String  INVOICE_DOCUMENT_FUTURE="IU2AP8"; //
  public static final String  MINI_ITINERARY="IU2AP9"; //
  public static final String  MAGNETIC_ENCODING_FUTURE="IU2APA"; //
  public static final String  US_CONTRACT_WHOLESALE_BULK_TICKET="IU2APB"; //
  public static final String  BSP_TICKET_SUPPRESSION_INDICATOR="IU2APZ"; //

  public static final String  FARE_SIGN="IU2FSN"; //
  public static final String  FARE_AREA="IU2FRE"; //
  public static final String  FARE_CURRENCY_CODE="IU2FCC"; //
  public static final String  FARE_AMOUNT="IU2FAR"; //

  public static final String  TAX_1_SIGN="IU2T1S"; //
  public static final String  TAX_1_AMOUNT="IU2TX1"; //
  public static final String  TAX_1_ID="IU2ID1"; //
  public static final String  TAX_2_SIGN="IU2T2S"; //
  public static final String  TAX_2_AMOUNT="IU2TX2"; //
  public static final String  TAX_2_ID="IU2ID2"; //
  public static final String  TAX_3_SIGN="IU2T3S"; //
  public static final String  TAX_3_AMOUNT="IU2TX3"; //
  public static final String  TAX_3_ID="IU2ID3"; //

  public static final String  TOTAL_FARE_SIGN="IU2TFS"; //
  public static final String  TOTAL_FARE_AREA="IU2TOT"; //
  public static final String  TOTAL_FARE_CURRENCY_CODE="IU2TFC"; //
  public static final String  TOTAL_FARE_AMOUNT="IU2TFR"; //
  
  public static final String  CANCELLATION_PENALTY_AMOUNT="IU2PEN"; //
  public static final String  COMMISSION_ON_PENALTY="IU2KXP"; //
  public static final String  SPARE="IU2SPX"; //

  public static final String  EQUIVALENT_PAID_SIGN="IU2EPS"; //
  public static final String  EQUIVALENT_PAID_AREA="IU2EQV"; //
  public static final String  EQUIVALENT_FARE_CURRENCY_CODE="IU2EFC"; //
  public static final String  EQUIVALENT_FARE_AMOUNT="IU2EFR"; //

  public static final String  COMMISSION_PERCENTAGE="IU2PCT"; //
  public static final String  COMMISSION_SIGN="IU2CSN"; //
  public static final String  COMMISSION_AMOUNT="IU2COM"; //
  public static final String  NET_AMOUNT_SIGN="IU2NAS"; //
  public static final String  NET_AMOUNT="IU2NET"; //
  public static final String  CANADIAN_TICKET_DESIGNATOR_CODE="IU2CDC"; //
  public static final String  CANADIAN_TICKET_TRAVEL_CODE="IU2CTT"; //
  public static final String  FOREIGN_TARIFF_FLAG="IU2FTF"; //
  public static final String  TRAVEL_AGENCY_TAX="IU2TAT"; //
  public static final String  INCLUSIVE_TOUR_IT_NUMBER="IU2TUR"; //
  public static final String  TICKETING_CITY="IU2TPC"; //
  public static final String  PRINT_MINI_ITINERARY="IU2MIT"; //
  public static final String  CREDIT_CARD_INFORMATION_PRINT_SUPPRESSION="IU2CCB"; //
  public static final String  CREDIT_CARD_AUTHORIZATION_SOURCE_CODE="IU2APC"; //
  
  public static final String  FARE_AGENT_SINE="IU2SNF"; //
  public static final String  PSEUDO_CITY_CODE="IU2SN4"; //
  public static final String  AGENT_DUTY_CODE="IU2SN3"; //
  public static final String  AGENT_SINE="IU2SN2"; //
  
  public static final String  PRINT_AGENT_SINE_FULL="IU2SNP"; //
  public static final String  PRINT_PSEUDO_CITY_CODE="IU2PN4"; //
  public static final String  PRINT_AGENT_DUTY_CODE="IU2PN3"; //
  public static final String  PRINT_AGENT_SINE="IU2PN2"; //
  
  public static final String  SPARE2="IU2AVI"; //
  public static final String  CREDIT_CARD_AUTHORIZATION="IU2ATH"; //
  public static final String  SPARES="IU2SPR"; //
  public static final String  MULTIPLECCFORINDICATOR="IU2MFP"; //
  public static final String  PRIVATEINDICATORFARE="IU2PVT"; //
  public static final String  CREDIT_CARD_EXPIRATION_DATE_Format_MMYY="IU2EXP"; //
  public static final String  CREDIT_CARD_EXTENDED_PAYMENT_MONTHS="IU2EXT"; //
  public static final String  COUNT_OF_M4_RECORDS="IU2M4C"; //
  public static final String  COUNT_OF_M6_RECORDS="IU2M6C"; //
  public static final String  VALIDATING_CARRIER_CODE="IU2VAL"; //
  public static final String  TICKET_NUMBER="IU2TNO"; //
  public static final String  CONJUNCTION_TICKET_COUNT="IU2DNO"; //
  public static final String  CARRIAGE_RETURN="IU2CR1"; //

  public static final String  ENTITLEMENT_ITEM_NUMBERS="IU2NM4"; //
	public static final String  M6_FARE_CALCULATION_ITEM_NUMBERS="IU2NM6"; //
	public static final String  FORM_OF_PAYMENT="IU2FOP"; //
	public static final String  ORIGINAL_ISSUE="IU2ORG";//
	public static final String  ISSUED_IN_EXCHANGE="IU2IIE"; //
	public static final String  ENDORSEMENTS="IU2END"; //
	public static final String  JOURNEY_IDENTIFICATION_CODE="IU2JIC"; //
	public static final String  NET_REMIT_TICKETING="IU2NRT"; //
	public static final String  REFUND_AUTHORIZATION_FOR_ETR="IU2REA"; //
	public static final String  DISCOUNT_CODE="IU2DCD"; //
	public static final String  SCN_for_ATB="IU2SCN"; //

	public M2Record() {
		ID=SabreRecord.M2;
	}
	
  //   ==========================================================================
  //   M2 Record - Passenger Ticket Data Record
  //   ==========================================================================
  //   This record contains passenger ticket data. This data is needed for the printing
  //   of the ticket. There is one M2 record per passenger ticketed.
	public String doProcess(JMap<String,Object>mRecords, String line) throws Exception {

		int i=2;
		addFieldValue(MESSAGE_ID,ID);  //MESSAGE ID
		addFieldValue(INTERFACE_NAME_ITEM_NUMBER,line.substring(i,i+=2));
		addFieldValue(PASSENGER_TYPE,line.substring(i,i+=3));
		addFieldValue(TCN_NUMBER_TRANSACTION_CONTROL_NUMBER,line.substring(i,i+=11));
		addFieldValue(INTERNATIONAL_ITINERARY_INDICATOR,line.substring(i,i+=1));
		addFieldValue(FORMS_OF_PAYMENT_SELF_SALE_INDICATOR,line.substring(i,i+=1));
		addFieldValue(TICKET_INDICATOR,line.substring(i,i+=1));
		addFieldValue(COMBINED_TICKET_BOARDING_PASS,line.substring(i,i+=1));
		addFieldValue(TICKET_ONLY,line.substring(i,i+=1));
		addFieldValue(BOARDING_PASS_ONLY,line.substring(i,i+=1));
		addFieldValue(REMOTE_PRINT_AUDITORS_COUPON,line.substring(i,i+=1));
		addFieldValue(PASSENGER_RECEIPT_ONLY_FUTURE,line.substring(i,i+=1));
		addFieldValue(REMOTE_PRINT_AGENTS_COUPON,line.substring(i,i+=1));
		addFieldValue(REMOTE_PRINT_CREDIT_CARD_CHARGE_FORM,line.substring(i,i+=1));
		addFieldValue(INVOICE_DOCUMENT_FUTURE,line.substring(i,i+=1));
		addFieldValue(MINI_ITINERARY,line.substring(i,i+=1));
		addFieldValue(MAGNETIC_ENCODING_FUTURE,line.substring(i,i+=1));
		addFieldValue(US_CONTRACT_WHOLESALE_BULK_TICKET,line.substring(i,i+=1));
		addFieldValue(BSP_TICKET_SUPPRESSION_INDICATOR,line.substring(i,i+=1));
		
		addFieldValue(FARE_SIGN,line.substring(i,i+=1));
		addFieldValue(FARE_AREA,line.substring(i,i+=11));
		addFieldValue(FARE_CURRENCY_CODE,line.substring(i-11,i-8));
		addFieldValue(FARE_AMOUNT,line.substring(i-8,i));

				
		addFieldValue(TAX_1_SIGN,line.substring(i,i+=1));
		addFieldValue(TAX_1_AMOUNT,checkAmount(mRecords, line.substring(i,i+=7)));
		addFieldValue(TAX_1_ID,line.substring(i,i+=2));
		addFieldValue(TAX_2_SIGN,line.substring(i,i+=1));
		addFieldValue(TAX_2_AMOUNT,checkAmount(mRecords, line.substring(i,i+=7)));
		addFieldValue(TAX_2_ID,line.substring(i,i+=2));
		addFieldValue(TAX_3_SIGN,line.substring(i,i+=1));
		addFieldValue(TAX_3_AMOUNT,checkAmount(mRecords, line.substring(i,i+=7)));
		addFieldValue(TAX_3_ID,line.substring(i,i+=2));

		addFieldValue(TOTAL_FARE_SIGN,line.substring(i,i+=1));
		addFieldValue(TOTAL_FARE_AREA,line.substring(i,i+=11));
		addFieldValue(TOTAL_FARE_CURRENCY_CODE,line.substring(i-11,i-8));
		addFieldValue(TOTAL_FARE_AMOUNT,line.substring(i-8,i));

		addFieldValue(CANCELLATION_PENALTY_AMOUNT,line.substring(i,i+=11));
		addFieldValue(COMMISSION_ON_PENALTY,line.substring(i,i+=11));
		addFieldValue(SPARE,line.substring(i,i+=6));

		addFieldValue(EQUIVALENT_PAID_SIGN,line.substring(i,i+=1));
		addFieldValue(EQUIVALENT_PAID_AREA,line.substring(i,i+=11));
		addFieldValue(EQUIVALENT_FARE_CURRENCY_CODE,line.substring(i-11,i-8));
		addFieldValue(EQUIVALENT_FARE_AMOUNT,line.substring(i-8,i));

		addFieldValue(COMMISSION_PERCENTAGE,line.substring(i,i+=8));
		addFieldValue(COMMISSION_SIGN,line.substring(i,i+=1));
		addFieldValue(COMMISSION_AMOUNT,line.substring(i,i+=8));
		addFieldValue(NET_AMOUNT_SIGN,line.substring(i,i+=1));
		addFieldValue(NET_AMOUNT,line.substring(i,i+=8));
		addFieldValue(CANADIAN_TICKET_DESIGNATOR_CODE,line.substring(i,i+=1));
		addFieldValue(CANADIAN_TICKET_TRAVEL_CODE,line.substring(i,i+=1));
		addFieldValue(FOREIGN_TARIFF_FLAG,line.substring(i,i+=1));
		addFieldValue(TRAVEL_AGENCY_TAX,line.substring(i,i+=8));
		addFieldValue(INCLUSIVE_TOUR_IT_NUMBER,line.substring(i,i+=15));
		addFieldValue(TICKETING_CITY,line.substring(i,i+=4));
		addFieldValue(PRINT_MINI_ITINERARY,line.substring(i,i+=1));
		addFieldValue(CREDIT_CARD_INFORMATION_PRINT_SUPPRESSION,line.substring(i,i+=1));
		addFieldValue(CREDIT_CARD_AUTHORIZATION_SOURCE_CODE,line.substring(i,i+=1));
		
		addFieldValue(FARE_AGENT_SINE,line.substring(i,i+=10));
		addFieldValue(PSEUDO_CITY_CODE,line.substring(i-10,i-5));
		addFieldValue(AGENT_DUTY_CODE,line.substring(i-5,i-3));
		addFieldValue(AGENT_SINE,line.substring(i-3,i));
		addFieldValue(PRINT_AGENT_SINE_FULL,line.substring(i,i+=10));
		addFieldValue(PRINT_PSEUDO_CITY_CODE,line.substring(i-10,i-5));
		addFieldValue(PRINT_AGENT_DUTY_CODE,line.substring(i-5,i-3));
		addFieldValue(PRINT_AGENT_SINE,line.substring(i-3,i));

		addFieldValue(SPARE2,line.substring(i,i+=1));
		addFieldValue(CREDIT_CARD_AUTHORIZATION,line.substring(i,i+=9));
		addFieldValue(MULTIPLECCFORINDICATOR,line.substring(i,i+=1));
		addFieldValue(PRIVATEINDICATORFARE,line.substring(i,i+=1));
		addFieldValue(SPARES,line.substring(i,i+=3));
		addFieldValue(CREDIT_CARD_EXPIRATION_DATE_Format_MMYY,line.substring(i,i+=4));
		addFieldValue(CREDIT_CARD_EXTENDED_PAYMENT_MONTHS,line.substring(i,i+=2));
		addFieldValue(COUNT_OF_M4_RECORDS,line.substring(i,i+=2));
		addFieldValue(COUNT_OF_M6_RECORDS,line.substring(i,i+=2));
		addFieldValue(VALIDATING_CARRIER_CODE,line.substring(i,i+=2));
		addFieldValue(TICKET_NUMBER,line.substring(i,i+=10));
		addFieldValue(CONJUNCTION_TICKET_COUNT,line.substring(i,i+=1));

		addFieldValue(ENTITLEMENT_ITEM_NUMBERS,this.getInput().readLine() );
		addFieldValue(M6_FARE_CALCULATION_ITEM_NUMBERS,this.getInput().readLine() );
		String fop = this.getInput().readLine() ;
		if (fop.startsWith("*") ) 
			fop = fop.substring(1, 3);
		addFieldValue(FORM_OF_PAYMENT, fop);
		addFieldValue(ORIGINAL_ISSUE,this.getInput().readLine() );
		addFieldValue(ISSUED_IN_EXCHANGE,this.getInput().readLine() );
		addFieldValue(ENDORSEMENTS,this.getInput().readLine() );
		addFieldValue(JOURNEY_IDENTIFICATION_CODE,this.getInput().readLine() );
		addFieldValue(NET_REMIT_TICKETING,this.getInput().readLine() );
		addFieldValue(REFUND_AUTHORIZATION_FOR_ETR,this.getInput().readLine() );
		addFieldValue(DISCOUNT_CODE,this.getInput().readLine() );
		addFieldValue(SCN_for_ATB,this.getInput().readLine() );
		
     
		return line.substring(i);

	}
	
	private String checkAmount(JMap<String,Object>mRecords,String amount) throws Exception {
		M0Record m0 = ((M0Record) mRecords.getElement(SabreRecord.M0));
	    if (JTools.isNumber(amount.trim()))
			 return amount; 
	    return "0";
	}
	public String getNetRemit() {
		return this.getFieldValue(NET_REMIT_TICKETING);
	}
	public double getCreditCardAmount() {
		String nrt = this.getFieldValue(NET_REMIT_TICKETING);
		JStringTokenizer tok = JCollectionFactory.createStringTokenizer(nrt, '/');
		while ( tok.hasMoreTokens() ) {
			String stok = tok.nextToken();
			if ( stok.startsWith("CCAM") ) {
				double am = Double.parseDouble( stok.substring(4).trim()) ;
			  return am;
			}
		}
		return 0.0f;
	}

	public String getCreditCardAutorization() {
		return this.getFieldValue(M2Record.CREDIT_CARD_AUTHORIZATION);
	}
	public String getMultipleCCForIndicator() {
		return this.getFieldValue(M2Record.MULTIPLECCFORINDICATOR);
	}
	public String getPrivateFareIndicator() {
		return this.getFieldValue(M2Record.PRIVATEINDICATORFARE);
	}

	public String getTax1Id() {
		return this.getFieldValue(M2Record.TAX_1_ID);
	}

	public String getTicketNumber() {
		return this.getFieldValue(M2Record.TICKET_NUMBER);
	}
	public String getTourCode() {
		return this.getFieldValue(M2Record.INCLUSIVE_TOUR_IT_NUMBER);
	}
	
	public String getTax1Amount() throws Exception {
		return Tools.getOnlyNumbers(this.getFieldValue(M2Record.TAX_1_AMOUNT)	);
	}
	public String getTax2Amount() throws Exception  {
		return Tools.getOnlyNumbers(this.getFieldValue(M2Record.TAX_2_AMOUNT));
	}
	public String getTax3Amount() throws Exception  {
		return Tools.getOnlyNumbers(this.getFieldValue(M2Record.TAX_3_AMOUNT));
	}
	
	public double getTaxXTAmount() throws Exception  {
		return Double.parseDouble(getTax3Amount());
	}
	
	public String getTotalFareCurrencyCode() throws Exception {
		String curr = this.getFieldValue(M2Record.TOTAL_FARE_CURRENCY_CODE);
		if ( curr == null || curr.length() == 0 ) curr=this.getFieldValue(M2Record.FARE_CURRENCY_CODE);
		if ( curr == null || curr.length() == 0 ) curr="";
		return curr;
	}

	public String getTax2Id() {
		return this.getFieldValue(M2Record.TAX_2_ID);
	}

	public String getTax3Id() {
		return this.getFieldValue(M2Record.TAX_3_ID);
	}
	
	public String getPassengerType() {
		return this.getFieldValue(M2Record.PASSENGER_TYPE);
	}
	
	public String getFareAmount() throws Exception {
		return Tools.getOnlyNumbers(this.getFieldValue(M2Record.FARE_AMOUNT));
	}
	
	public String getTravelAgencyTax() throws Exception {
		return Tools.getOnlyNumbers(this.getFieldValue(M2Record.TRAVEL_AGENCY_TAX));
	}
	
	public String getPaxId() {
		return Integer.parseInt( this.getFieldValue(M2Record.INTERFACE_NAME_ITEM_NUMBER) ) + "";
	}
	
	public String getTotalFareAmount() throws Exception  {
		String sfa= Tools.getOnlyNumbers(this.getFieldValue(M2Record.TOTAL_FARE_AMOUNT));
		if ( sfa == null || sfa.length() == 0 ) return "0";
    return sfa;
	}
	
	public String getNetAmount() throws Exception {
		return Tools.getOnlyNumbers(this.getFieldValue(M2Record.EQUIVALENT_FARE_AMOUNT));
	}
	
	public String getNetAmountInCurrentCurrency() throws Exception {
		return Tools.getOnlyNumbers(this.getFieldValue(M2Record.FARE_AMOUNT));
	}
	
	
	public String getRealNetAmount() throws Exception {
		return Tools.getOnlyNumbers(this.getFieldValue(M2Record.NET_AMOUNT));
	}
	
	public String getFareCurrencyCode() {
		return this.getFieldValue(M2Record.FARE_CURRENCY_CODE);
	}
	
	public String getCarrierCode() {
		return this.getFieldValue(M2Record.VALIDATING_CARRIER_CODE);
	}
	
	public int getConjuctionTicketCount() {
		return (int)JTools.getLongNumberEmbedded( this.getFieldValue(M2Record.CONJUNCTION_TICKET_COUNT) );
	}
	
	public double getCommision() {
		String ca = this.getFieldValue(M2Record.COMMISSION_AMOUNT);
		if ( ca.equals("") ) ca = "0.0";
		return Double.parseDouble( ca );
	}
	public double getCommisionPercentaje() {
		String ca = this.getFieldValue(M2Record.COMMISSION_PERCENTAGE);
		if ( ca.equals("") ) ca = "0.0";
		return Double.parseDouble( ca  );
	}
	
	public boolean isElectronicTicket() {
		return this.getFieldValue(M2Record.TICKET_INDICATOR).equals("2");
	}
	
	public double getExchangeRate() throws Exception {
		double na = Double.parseDouble(this.getNetAmount());
		double fa = Double.parseDouble(this.getFareAmount());
		if ( na == 0 || fa == 0 ) return 1;
		return na/fa;
	}

	public boolean isReIssue() throws Exception {
		String sfa = this.getTotalFareAmount();
		double fa = Double.parseDouble(sfa);
		return fa == 0;
	}
	
	public boolean isSomeKindOfError() throws Exception {
		double tfa = Double.parseDouble(this.getTotalFareAmount());
		double fa = Double.parseDouble(this.getFareAmount());

		return ( tfa < fa && this.getFareCurrencyCode().equals(SabreRecord.CURR_ARS)); 
	}
	
//Public Function isSomeKindOfError() As Boolean
//Return ((Decimal.Compare(New Decimal(Conversion.Val(("" & InterfaceCommonFunctions.getFieldValueString(Me.fields, "IU2TFR")))), Me.fareAmount) < 0) And (Strings.Trim(Me.fareCurrencyCode) = "ARS"))
//End Function
//	
//  Public Function isReIssue() As Boolean
//  ' Answer true whether the receiver is a reissue ticket
//  Return (Decimal.Compare(New Decimal(Conversion.Val(("" & InterfaceCommonFunctions.getFieldValueString(Me.fields, "IU2TFR")))), Decimal.Zero) = 0)
//End Function
//
//Public Function CreditCardAuthCode() As String
//  Return Strings.Trim(InterfaceCommonFunctions.getFieldValueString(Me.fields, "IU2ATH"))
//End Function
//
//
//Public Function CreditCardExpirationDate() As String
//  Return InterfaceCommonFunctions.getFieldValueString(Me.fields, "IU2EXP")
//End Function
//
//
//
//Public Function EquivalentAmount() As Decimal
//  Return New Decimal(Conversion.Val(("" & InterfaceCommonFunctions.getFieldValueString(Me.fields, "IU2EFR"))))
//End Function
//
//
//Public Function formOfPaymentCode() As String
//  Return RemarkFunctions.formOfPaymentCodeFrom(Me)
//End Function

//
//Public Function isSomeKindOfError() As Boolean
//  Return ((Decimal.Compare(New Decimal(Conversion.Val(("" & InterfaceCommonFunctions.getFieldValueString(Me.fields, "IU2TFR")))), Me.fareAmount) < 0) And (Strings.Trim(Me.fareCurrencyCode) = "ARS"))
//End Function


	
	
}
