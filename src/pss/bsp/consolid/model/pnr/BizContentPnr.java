package pss.bsp.consolid.model.pnr;

import pss.core.services.fields.JDate;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizContentPnr extends JRecord {

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Declaración de variables
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private JLong pIdCtsContentPnr = new JLong();
	private JString pNameRec = new JString();
	private JDate pDateEmision = new JDate();
	private JString pTimeEmision = new JString();
	private JString pCustomerNumber = new JString();
	private JString pCustomerName = new JString();
	private JString pIata = new JString();
	private JString pPnrLocator = new JString();
	private JString pCustomerBranch = new JString();
	private JDate pDateRec = new JDate();
	private JString pTimeRec = new JString();
	private JString pInvoicingAgencyCityCode = new JString();
	private JString pInvoicingAgentDutyCode = new JString();
	private JString pAgencyPolicy = new JString();
	private JString pTelephoneNumber = new JString();
	private JString pTelephoneNumber2 = new JString();
	private JString pTelephoneNumber3 = new JString();
	private JString pAgenteReservaPnr = new JString();
	private JString pAgenteEmitePnr = new JString();
	private JString pPassengerManNumber = new JString();
	private JString pFrequentTravelerNumber = new JString();
	private JString pFareCurrencyCode = new JString();
	private JString pFareAmount = new JString();
	private JString pTax1Id = new JString();
	private JString pTax2Id = new JString();
	private JString pTax3Id = new JString();
	private JString pTotalFaeSign = new JString();
	private JString pCommissionPercentage = new JString();
	private JString pCommissionSign = new JString();
	private JString pCommissionAmount = new JString();
	private JString pNetAmount = new JString();
	private JString pTypeOfPayment = new JString();
	private JString pOriginalIssue = new JString();
	private JString pCreditCardNumber = new JString();
	private JString pCodeAut = new JString();
	private JString pCanceledTicket = new JString();
	private JString pCarrierCodeM5 = new JString();
	private JLong pTicketNumberM5 = new JLong();
	private JLong pCommissionAmountM5 = new JLong();
	private JLong pBaseFareAmountM5 = new JLong();
	private JLong pTax1AmountM5 = new JLong();
	private JLong pTax2AmountM5 = new JLong();
	private JLong pTax3AmountM5 = new JLong();
	private JString pFormPaymentM5 = new JString();
	private JString pPassengerNameM5 = new JString();
	private JLong pConjunctionDocuments = new JLong();
	private JString pRoutIndicator = new JString();
	private JString pTicketNumberEx = new JString();
	private JString pExchangedCoupons = new JString();
	private JString pTypeInvoice = new JString();
	private JDate pCreationDate = new JDate();
	private JDate pLastUpdateDate = new JDate();
	private JString pError = new JString();
	private JLong pCustomerTrxId = new JLong();
	private JString pOriginGds = new JString();
	private JString pStatusFlag = new JString();
	private JString pItemNumber = new JString();
	private JLong pExchangeRate = new JLong();
	private JString pHist = new JString();
	private JLong pServiceCharge = new JLong();
	private JString pServiceChargeName = new JString();
	private JLong pCustomerTrxIdCs = new JLong();
	private JString pTrxNumberCs = new JString();
	private JString pTrxNumber = new JString();
	private JString pCustomerAddress = new JString();
	private JString pCustomerRfc = new JString();
	private JLong pTrxNumberNpd = new JLong();
	private JLong pCustomerTrxIdNpd = new JLong();
	private JLong pTrxNumberNc = new JLong();
	private JLong pCustomerTrxIdNc = new JLong();
	private JString pItemPax = new JString();
	private JLong pCustomerTrxIdNpdCs = new JLong();
	private JString pTrxNumberNpdCs = new JString();
	private JString pFlagNpd = new JString();
	private JLong pControl = new JLong();
	private JLong pTrxNumberNd = new JLong();
	private JLong pCustomerTrxIdNd = new JLong();
	private JString pCreditCardNumberCs = new JString();
	private JString pTypeOfPaymentCs = new JString();
	private JString pFormPaymentM5Cs = new JString();
	private JString pComments = new JString();
	private JLong pOrgId = new JLong();
	private JString pInvoiceNumber = new JString();
	private JString pNameRecExt = new JString();
	private JString pPseudoReserva = new JString();

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Constructor de la clase
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BizContentPnr() throws Exception {
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Métodos para crear propiedades
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void createProperties() throws Exception {
		this.addItem("id_ctscontent_pnr", pIdCtsContentPnr);
		this.addItem("name_rec", pNameRec);
		this.addItem("date_emision", pDateEmision);
		this.addItem("time_emision", pTimeEmision);
		this.addItem("customer_number", pCustomerNumber);
		this.addItem("customer_name", pCustomerName);
		this.addItem("iata", pIata);
		this.addItem("pnr_locator", pPnrLocator);
		this.addItem("customer_branch", pCustomerBranch);
		this.addItem("date_rec", pDateRec);
		this.addItem("time_rec", pTimeRec);
		this.addItem("invoicing_agency_city_code", pInvoicingAgencyCityCode);
		this.addItem("invoicing_agent_duty_code", pInvoicingAgentDutyCode);
		this.addItem("agency_policy", pAgencyPolicy);
		this.addItem("telephone_number", pTelephoneNumber);
		this.addItem("telephone_number2", pTelephoneNumber2);
		this.addItem("telephone_number3", pTelephoneNumber3);
		this.addItem("agente_reserva_pnr", pAgenteReservaPnr);
		this.addItem("agente_emite_pnr", pAgenteEmitePnr);
		this.addItem("passenger_man_number", pPassengerManNumber);
		this.addItem("frequent_traveler_number", pFrequentTravelerNumber);
		this.addItem("fare_currency_code", pFareCurrencyCode);
		this.addItem("fare_amount", pFareAmount);
		this.addItem("tax_1_id", pTax1Id);
		this.addItem("tax_2_id", pTax2Id);
		this.addItem("tax_3_id", pTax3Id);
		this.addItem("total_fae_sign", pTotalFaeSign);
		this.addItem("commission_percentage", pCommissionPercentage);
		this.addItem("commission_sign", pCommissionSign);
		this.addItem("commission_amount", pCommissionAmount);
		this.addItem("net_amount", pNetAmount);
		this.addItem("type_of_payment", pTypeOfPayment);
		this.addItem("original_issue", pOriginalIssue);
		this.addItem("credit_card_number", pCreditCardNumber);
		this.addItem("code_aut", pCodeAut);
		this.addItem("canceled_ticket", pCanceledTicket);
		this.addItem("carrier_code_m5", pCarrierCodeM5);
		this.addItem("ticket_number_m5", pTicketNumberM5);
		this.addItem("commission_amount_m5", pCommissionAmountM5);
		this.addItem("base_fare_amount_m5", pBaseFareAmountM5);
		this.addItem("tax_1_amount_m5", pTax1AmountM5);
		this.addItem("tax_2_amount_m5", pTax2AmountM5);
		this.addItem("tax_3_amount_m5", pTax3AmountM5);
		this.addItem("form_payment_m5", pFormPaymentM5);
		this.addItem("passenger_name_m5", pPassengerNameM5);
		this.addItem("conjunction_documents", pConjunctionDocuments);
		this.addItem("rout_indicator", pRoutIndicator);
		this.addItem("ticket_number_ex", pTicketNumberEx);
		this.addItem("exchanged_coupons", pExchangedCoupons);
		this.addItem("type_invoice", pTypeInvoice);
		this.addItem("creation_date", pCreationDate);
		this.addItem("last_update_date", pLastUpdateDate);
		this.addItem("error", pError);
		this.addItem("customer_trx_id", pCustomerTrxId);
		this.addItem("origin_gds", pOriginGds);
		this.addItem("status_flag", pStatusFlag);
		this.addItem("item_number", pItemNumber);
		this.addItem("exchange_rate", pExchangeRate);
		this.addItem("hist", pHist);
		this.addItem("service_charge", pServiceCharge);
		this.addItem("service_charge_name", pServiceChargeName);
		this.addItem("customer_trx_id_cs", pCustomerTrxIdCs);
		this.addItem("trx_number_cs", pTrxNumberCs);
		this.addItem("trx_number", pTrxNumber);
		this.addItem("customer_address", pCustomerAddress);
		this.addItem("customer_rfc", pCustomerRfc);
		this.addItem("trx_number_npd", pTrxNumberNpd);
		this.addItem("customer_trx_id_npd", pCustomerTrxIdNpd);
		this.addItem("trx_number_nc", pTrxNumberNc);
		this.addItem("customer_trx_id_nc", pCustomerTrxIdNc);
		this.addItem("item_pax", pItemPax);
		this.addItem("customer_trx_id_npd_cs", pCustomerTrxIdNpdCs);
		this.addItem("trx_number_npd_cs", pTrxNumberNpdCs);
		this.addItem("flag_npd", pFlagNpd);
		this.addItem("control", pControl);
		this.addItem("trx_number_nd", pTrxNumberNd);
		this.addItem("customer_trx_id_nd", pCustomerTrxIdNd);
		this.addItem("credit_card_number_cs", pCreditCardNumberCs);
		this.addItem("type_of_payment_cs", pTypeOfPaymentCs);
		this.addItem("form_payment_m5_cs", pFormPaymentM5Cs);
		this.addItem("comments", pComments);
		this.addItem("org_id", pOrgId);
		this.addItem("invoice_number", pInvoiceNumber);
		this.addItem("name_rec_ext", pNameRecExt);
		this.addItem("pseudo_reserva", pPseudoReserva);
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Métodos para crear propiedades fijas
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "id_ctscontent_pnr", "ID CTS Content PNR", true, true, 22);
		this.addFixedItem(FIELD, "name_rec", "Name Rec", true, false, 100);
		this.addFixedItem(FIELD, "date_emision", "Date Emision", true, false, 7);
		this.addFixedItem(FIELD, "time_emision", "Time Emision", true, false, 5);
		this.addFixedItem(FIELD, "customer_number", "Customer Number", true, false, 10);
		this.addFixedItem(FIELD, "customer_name", "Customer Name", true, false, 300);
		this.addFixedItem(FIELD, "iata", "IATA", true, false, 20);
		this.addFixedItem(FIELD, "pnr_locator", "PNR Locator", true, false, 100);
		this.addFixedItem(FIELD, "customer_branch", "Customer Branch", true, false, 20);
		this.addFixedItem(FIELD, "date_rec", "Date Rec", true, false, 7);
		this.addFixedItem(FIELD, "time_rec", "Time Rec", true, false, 5);
		this.addFixedItem(FIELD, "invoicing_agency_city_code", "Invoicing Agency City Code", true, false, 9);
		this.addFixedItem(FIELD, "invoicing_agent_duty_code", "Invoicing Agent Duty Code", true, false, 3);
		this.addFixedItem(FIELD, "agency_policy", "Agency Policy", true, false, 350);
		this.addFixedItem(FIELD, "telephone_number", "Telephone Number", true, false, 200);
		this.addFixedItem(FIELD, "telephone_number2", "Telephone Number 2", true, false, 200);
		this.addFixedItem(FIELD, "telephone_number3", "Telephone Number 3", true, false, 200);
		this.addFixedItem(FIELD, "agente_reserva_pnr", "Agente Reserva PNR", true, false, 200);
		this.addFixedItem(FIELD, "agente_emite_pnr", "Agente Emite PNR", true, false, 200);
		this.addFixedItem(FIELD, "passenger_man_number", "Passenger Man Number", true, false, 3000);
		this.addFixedItem(FIELD, "frequent_traveler_number", "Frequent Traveler Number", true, false, 200);
		this.addFixedItem(FIELD, "fare_currency_code", "Fare Currency Code", true, false, 30);
		this.addFixedItem(FIELD, "fare_amount", "Fare Amount", true, false, 30);
		this.addFixedItem(FIELD, "tax_1_id", "Tax 1 ID", true, false, 20);
		this.addFixedItem(FIELD, "tax_2_id", "Tax 2 ID", true, false, 20);
		this.addFixedItem(FIELD, "tax_3_id", "Tax 3 ID", true, false, 2);
		this.addFixedItem(FIELD, "total_fae_sign", "Total Fae Sign", true, false, 10);
		this.addFixedItem(FIELD, "commission_percentage", "Commission Percentage", true, false, 80);
		this.addFixedItem(FIELD, "commission_sign", "Commission Sign", true, false, 10);
		this.addFixedItem(FIELD, "commission_amount", "Commission Amount", true, false, 30);
		this.addFixedItem(FIELD, "net_amount", "Net Amount", true, false, 30);
		this.addFixedItem(FIELD, "type_of_payment", "Type of Payment", true, false, 20);
		this.addFixedItem(FIELD, "original_issue", "Original Issue", true, false, 360);
		this.addFixedItem(FIELD, "credit_card_number", "Credit Card Number", true, false, 30);
		this.addFixedItem(FIELD, "code_aut", "Code Aut", true, false, 10);
		this.addFixedItem(FIELD, "canceled_ticket", "Canceled Ticket", true, false, 120);
		this.addFixedItem(FIELD, "carrier_code_m5", "Carrier Code M5", true, false, 100);
		this.addFixedItem(FIELD, "ticket_number_m5", "Ticket Number M5", true, false, 22);
		this.addFixedItem(FIELD, "commission_amount_m5", "Commission Amount M5", true, false, 22);
		this.addFixedItem(FIELD, "base_fare_amount_m5", "Base Fare Amount M5", true, false, 22);
		this.addFixedItem(FIELD, "tax_1_amount_m5", "Tax 1 Amount M5", true, false, 22);
		this.addFixedItem(FIELD, "tax_2_amount_m5", "Tax 2 Amount M5", true, false, 22);
		this.addFixedItem(FIELD, "tax_3_amount_m5", "Tax 3 Amount M5", true, false, 22);
		this.addFixedItem(FIELD, "form_payment_m5", "Form Payment M5", true, false, 20);
		this.addFixedItem(FIELD, "passenger_name_m5", "Passenger Name M5", true, false, 200);
		this.addFixedItem(FIELD, "conjunction_documents", "Conjunction Documents", true, false, 22);
		this.addFixedItem(FIELD, "rout_indicator", "Rout Indicator", true, false, 200);
		this.addFixedItem(FIELD, "ticket_number_ex", "Ticket Number Ex", true, false, 40);
		this.addFixedItem(FIELD, "exchanged_coupons", "Exchanged Coupons", true, false, 40);
		this.addFixedItem(FIELD, "type_invoice", "Type Invoice", true, false, 20);
		this.addFixedItem(FIELD, "creation_date", "Creation Date", true, false, 7);
		this.addFixedItem(FIELD, "last_update_date", "Last Update Date", true, false, 7);
		this.addFixedItem(FIELD, "error", "Error", true, false, 1000);
		this.addFixedItem(FIELD, "customer_trx_id", "Customer Trx ID", true, false, 22);
		this.addFixedItem(FIELD, "origin_gds", "Origin GDS", true, false, 30);
		this.addFixedItem(FIELD, "status_flag", "Status Flag", true, false, 200);
		this.addFixedItem(FIELD, "item_number", "Item Number", true, false, 20);
		this.addFixedItem(FIELD, "exchange_rate", "Exchange Rate", true, false, 22);
		this.addFixedItem(FIELD, "hist", "Hist", true, false, 3000);
		this.addFixedItem(FIELD, "service_charge", "Service Charge", true, false, 22);
		this.addFixedItem(FIELD, "service_charge_name", "Service Charge Name", true, false, 6);
		this.addFixedItem(FIELD, "customer_trx_id_cs", "Customer Trx ID CS", true, false, 22);
		this.addFixedItem(FIELD, "trx_number_cs", "Trx Number CS", true, false, 20);
		this.addFixedItem(FIELD, "trx_number", "Trx Number", true, false, 20);
		this.addFixedItem(FIELD, "customer_address", "Customer Address", true, false, 500);
		this.addFixedItem(FIELD, "customer_rfc", "Customer RFC", true, false, 40);
		this.addFixedItem(FIELD, "trx_number_npd", "Trx Number NPD", true, false, 22);
		this.addFixedItem(FIELD, "customer_trx_id_npd", "Customer Trx ID NPD", true, false, 22);
		this.addFixedItem(FIELD, "trx_number_nc", "Trx Number NC", true, false, 22);
		this.addFixedItem(FIELD, "customer_trx_id_nc", "Customer Trx ID NC", true, false, 22);
		this.addFixedItem(FIELD, "item_pax", "Item Pax", true, false, 20);
		this.addFixedItem(FIELD, "customer_trx_id_npd_cs", "Customer Trx ID NPD CS", true, false, 22);
		this.addFixedItem(FIELD, "trx_number_npd_cs", "Trx Number NPD CS", true, false, 20);
		this.addFixedItem(FIELD, "flag_npd", "Flag NPD", true, false, 3);
		this.addFixedItem(FIELD, "control", "Control", true, false, 22);
		this.addFixedItem(FIELD, "trx_number_nd", "Trx Number ND", true, false, 22);
		this.addFixedItem(FIELD, "customer_trx_id_nd", "Customer Trx ID ND", true, false, 22);
		this.addFixedItem(FIELD, "credit_card_number_cs", "Credit Card Number CS", true, false, 30);
		this.addFixedItem(FIELD, "type_of_payment_cs", "Type of Payment CS", true, false, 20);
		this.addFixedItem(FIELD, "form_payment_m5_cs", "Form Payment M5 CS", true, false, 20);
		this.addFixedItem(FIELD, "comments", "Comments", true, false, 1000);
		this.addFixedItem(FIELD, "org_id", "Org ID", true, false, 22);
		this.addFixedItem(FIELD, "invoice_number", "Invoice Number", true, false, 15);
		this.addFixedItem(FIELD, "name_rec_ext", "Name Rec Ext", true, false, 50);
		this.addFixedItem(FIELD, "pseudo_reserva", "Pseudo Reserva", true, false, 100);
	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "apps.CNS_CONTENTPNR_GDS_ALL_V";
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Default read() method
	 */
	public boolean read(long zId) throws Exception {
		addFilter("id_ctscontent_pnr", zId);
		return read();
	}

}
