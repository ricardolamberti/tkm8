package pss.tourism.interfaceGDS.amadeus.record;

import com.ibm.icu.util.StringTokenizer;

import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;
import pss.tourism.interfaceGDS.Tools;

public class IRecord extends AmadeusRecord {

	private String IDPASS = "ID";
	private String NUMBER = "NUMBER";
	private String NAME = "NAME";
	private String IDENT_FISCAL = "IDENT_FISCAL";
	private String TYPE = "TYPE";
	private String TICKET_TYPE = "TICKET_TYPE";
	private String AIRLINE_NUMBER = "AIRLINE_NUMBER";
	private String TICKET_NUMBER = "TICKET_NUMBER";
	private String TMC_TICKET_TYPE = "TMC_TICKET_TYPE";
	private String TMC_AIRLINE_NUMBER = "TMC_AIRLINE_NUMBER";
	private String TMC_TICKET_NUMBER = "TMC_TICKET_NUMBER";
	private String TMC_ID = "TMC_ID";

	private String ORIGINAL_TICKET_TYPE = "ORIGINAL_TICKET_TYPE";
	private String ORIGINAL_AIRLINE_NUMBER = "ORIGINAL_AIRLINE_NUMBER";
	private String ORIGINAL_TICKET_NUMBER = "ORIGINAL_TICKET_NUMBER";
	private String COMISSION_AMOUNT = "COMISSION_AMOUNT";
	private String COMISSION_PERCENTAJE = "COMISSION_PERCENTAJE";
	private String FARE_CURRENCY = "FARE_CURRENCY";
	private String FARE_AMOUNT = "FARE_AMOUNT";
	private String FARE_PAYFORM = "FARE_PAYFORM";
	private String FARE_CARDNUMBER = "FARE_CARDNUMBER";
	private String FARE_AUTH = "FARE_AUTH";
	private String AIRLINE_CODE = "AIRLINE_CODE";
	private String NUMBER_OF_TICKETS = "NUMBER_OF_TICKETS";
	public static String TOUR_CODE="TOUR_CODE";
	public static String AITAN="AITAN";
	public static String ENDORSEMENT="ENDORSEMENT";

	public static JMap<String, EMD> mEMDs = JCollectionFactory.createMap();

	public static void resetEMDMap() {
		mEMDs = JCollectionFactory.createMap();
	}
	
	public static boolean isEMD() {
		return (mEMDs.isEmpty()==false);
	}
	
	public static int countEMDs() {
		return (mEMDs.size());
	}

	public static Object[] getIds() {
		return (mEMDs.getKeys());
	}
	
	public static String getEMDTicket(String id) {
		return mEMDs.getElement(id).ticket;
	}
	public static String getEMDAmount(String id) {
		return mEMDs.getElement(id).amount;
	}
	public static String getEMDCardnumber(String id) {
		return mEMDs.getElement(id).cardnumber;
	}
	public static String getEMDCardType(String id) {
		return mEMDs.getElement(id).cardtype;
	}
	public static String getEMDAuth(String id) {
		return mEMDs.getElement(id).auth;
	}
	
	public static String getEMDAirline(String id) {
		return mEMDs.getElement(id).airline;		
	}

	public static String getEMDName(String id) {
		return mEMDs.getElement(id).name;		
	}
	public static String getEMDType(String id) {
		return mEMDs.getElement(id).type;		
	}


	protected String currTicket;
	
	public class EMD {
		String ticket;
		String airline;
		String cardtype;
		String cardnumber;
		String auth;
		String amount;
		String currency;
		String name;
		String type;
	}

	public IRecord() {
		ID = I;
	}
	public static String EMAIL = "EMAIL";

	public String getEmail(int idx) {
		return getFieldValue(EMAIL+idx);
	}
//SSR CTCE AM  HK1/VENTAS//WAIKIKITOURS.COM.MX;P1

	public void analizeSSR(String allline, int idx) {
		int i = allline.indexOf("CTCE");
			if (i>=0) {
				addFieldValue(AIRLINE_CODE + idx, allline.substring(9, 11));
				i = allline.indexOf(" HK");
				if (i>=0) {
					if (allline.lastIndexOf(";")==-1)
						addFieldValue(EMAIL+ 0, allline.substring(i+5) );
					else
						addFieldValue(EMAIL+ 0, allline.substring(i+5,allline.lastIndexOf(";")) );
				}
			

		}	
		i = allline.indexOf("DOCS");
		if (i>=0) {
			addFieldValue(AIRLINE_CODE + idx, allline.substring(9, 11));
			i = allline.indexOf(" HK");
			if (i>=0) {
				if (allline.lastIndexOf(";")==-1)
					addFieldValue(EMAIL+ 0, allline.substring(i+5) );
				else
					addFieldValue(EMAIL+ 0, allline.substring(i+5,allline.lastIndexOf(";")) );
			}
		

	}
			

	}

	// ==========================================================================
	// Itinerary Record
	// ==========================================================================
	public String doProcess(JMap<String, Object> mRecords, String line)
			throws Exception {

		int idx = 0;
		int i = 0;
		if (line.substring(i += 2).length() < 6)
			return line;

		BRecord b = ((BRecord) mRecords.getElement(AmadeusRecord.B));
		if (b != null)
			if (b.isBT())
				return null;

		while (line.startsWith(ID)) {

			line = line.substring(2);

			JStringTokenizer tok = JCollectionFactory.createStringTokenizer(
					line, AmadeusRecord.FIELD_SEPARATOR);
			tok.skipEmptyTokens(false);

			String aux = tok.nextToken();
			i = 0;

			if (aux.startsWith("GRP")) {
				line = getInput().readLine();
				continue;
			}
			idx++;
			addFieldValue(IDPASS + idx, aux);
			aux = tok.nextToken();
			addFieldValue(NUMBER + idx, aux.substring(i, i += 2));
			String name = aux.substring(i);
			addFieldValue(NAME + idx, name);

			aux = tok.nextToken();
			if (aux.startsWith("("))
				addFieldValue(TYPE + idx, aux.substring(1, 4));
			else if (name.contains("(INF)"))
				addFieldValue(TYPE + idx, "INF");
			else if (name.contains("(PFA)"))
				addFieldValue(TYPE + idx, "PFA");
			else if (name.contains("(CNN)"))
				addFieldValue(TYPE + idx, "CNN");
			else if (name.contains("(CHD)"))
				addFieldValue(TYPE + idx, "CHD");
			else if (name.contains("(YTH)"))
				addFieldValue(TYPE + idx, "YTH");
			else
				addFieldValue(TYPE + idx, "ADT");

			while (true) {
				line = getInput().readLine();
				if (line == null)
					break;
				processPassInfo(line, idx);
				if (line.startsWith(ID) || line.startsWith("ENDX")|| line.startsWith("END")) {
					break;
				}
			}

			if (line == null)
				break;

		}

		addFieldValue(NUMBER_OF_TICKETS, idx);

		return line;
	}

	public String getEndorsement(int idx) {
		return getFieldValue(ENDORSEMENT + idx);
	}

	public String getPassengerTypeIn(int idx) {
		return getFieldValue(TYPE + idx);
	}

	public String getPaxId(int idx) {
		return Integer.parseInt(getFieldValue(NUMBER + idx)) + "";
	}

	public String getPaxName(int idx) {
		return getFieldValue(NAME + idx);
	}
	public String getPaxIdentFiscal() {
		return getFieldValue(IDENT_FISCAL);
	}

	public int getNumberOfTickets() {
		return Integer.parseInt(getFieldValue(NUMBER_OF_TICKETS));
	}

	private void processPassInfo(String line, int idx) throws Exception {

		if (line.startsWith("T-"))
			processTicketInfo(line, idx);
		if (line.startsWith("TMC"))
			processTicketAdditionalInfo(line, idx);
		if (line.startsWith("FM"))
			processComissionInfo(line, idx);
		if (line.startsWith("FOI"))
			processAirlineInfoFOI(line, idx);
		if (line.startsWith("SSR"))
			analizeSSR(line, idx);
		else if (line.startsWith("FO"))
			processOriginalTicket(line, idx);
		if (line.startsWith("FP"))
			processPayInfo(line, idx);
		if (line.startsWith("MFP"))
			processEMDPayInfo(line, idx);
		if (line.startsWith("FT"))
			processTourCode(line, idx);
		if (line.startsWith("FV"))
			processAirlineInfo(line, idx);
	 	if (line.startsWith("RM"))
	 		processRemarks(line, idx);
	 	if (line.startsWith("AITAN"))
	 		processAitan(line, idx);
	 	if (line.startsWith("FSR"))
	 		processFSR(line, idx);
	 	if (line.startsWith("FE"))
	 		addFieldValue(ENDORSEMENT + idx, line.substring(2));;
			
		// if (line.startsWith("TK"))
		// processLineInfo(line, idx);

	}

	// REMARKS
	private void processRemarks(String allline, int idx) {
		StringTokenizer toks = new StringTokenizer(allline,"/");
		while (toks.hasMoreTokens()) {
			String line =toks.nextToken();
			int i = line.indexOf("FFA=");
			if (i>0) {
			  addFieldValue("REMARK_FEE"+idx, line.substring(i+4) );
			}
			i = line.indexOf("FFA-");
			if (i>0) {
			  addFieldValue("REMARK_FEE"+idx, line.substring(i+4) );
			}
			i = line.indexOf("FFA-");
			if (i>0) {
			  addFieldValue("REMARK_FEE"+idx, line.substring(i+4) );
			}
			i = line.indexOf("ACECLN-");
			if (i>0) {
				addFieldValue("REMARK_ACECLN"+idx, line.substring(i+7) );
			}
			i = line.indexOf("CC-");
			if (i>0) {
				addFieldValue("REMARK_ACECLN"+idx, line.substring(i+3) );
			}
			i = line.indexOf("CO2");
			if (i>0) {
				addFieldValue("REMARK_CO2"+idx, line.substring(i+3) );
			}
			i = line.indexOf("FFB-");
			if (i>0) {
			  addFieldValue("REMARK_FFB"+idx, line.substring(i+4) );
			}
			i = line.indexOf("FFC-");
			if (i>0) {
			  addFieldValue("REMARK_FFC"+idx, line.substring(i+4) );
			}
			i = line.indexOf("ST-");
			if (i>0) {
			  addFieldValue("REMARK_CC"+idx, line.substring(i+3) );
			}
			i = line.indexOf("RQ=");
			if (i>0) {
				if (JTools.isAlpha(line.substring(i+3)))
					addFieldValue("REMARK_RQ"+idx, line.substring(i+3) );
			}
			i = line.indexOf("RQ-");
			if (i>0) {
				if (JTools.isAlpha(line.substring(i+3)))
					addFieldValue("REMARK_RQ"+idx, line.substring(i+3) );
			}
			i = line.indexOf("FFB=");
			if (i>0) {
			  addFieldValue("REMARK_FFB"+idx, line.substring(i+4) );
			}
			i = line.indexOf("FFB-");
			if (i>0) {
			  addFieldValue("REMARK_FFB"+idx, line.substring(i+4) );
			}
			i = line.indexOf("FFC=");
			if (i>0) {
			  addFieldValue("REMARK_FFC"+idx, line.substring(i+4) );
			}
			i = line.indexOf("ST=");
			if (i>0) {
			  addFieldValue("REMARK_CC"+idx, line.substring(i+3) );
			}
			i = line.indexOf("ST-");
			if (i>0) {
			  addFieldValue("REMARK_CC"+idx, line.substring(i+3) );
			}
			i = line.indexOf("TKM*SC-");
			if (i>0) {
			  addFieldValue("REMARK_SC", line.substring(i+7) );
			}
			i = line.indexOf("*DP=");
			if (i>0) {
			  addFieldValue("REMARK_DP", line.substring(i+4) );
			}
		}

	}
	private void processAitan(String allline, int idx) {
		StringTokenizer toks = new StringTokenizer(allline,";");
		while (toks.hasMoreTokens()) {
			String line =toks.nextToken();
		  addFieldValue("AITAN", line.substring(5) );
		  break;
			
		}

	}
//FSR/CUIT20178175840
	private void processFSR(String allline, int idx) {
		StringTokenizer toks = new StringTokenizer(allline,"/;,.");
		while (toks.hasMoreTokens()) {
			String line =toks.nextToken();
			if (line.startsWith("CUI")) {
				String cuit = line;
				cuit = JTools.replace(cuit, " ", "");
				cuit = JTools.replace(cuit, "-", "");
				cuit = JTools.replace(cuit, ".", "");
			  addFieldValue(IDENT_FISCAL, cuit );
			  break;
			}
			
		}

	}

	public String getAdditionalFee(int idx) {
		
		return getFieldValue("REMARK_FEE"+idx);
	}
	public String getCostoFee(int idx) {
		
		return getFieldValue("REMARK_FFA"+idx);
	}
	public String getOrdenFee(int idx) {
		
		return getFieldValue("REMARK_FFB"+idx);
	}
	public String getCliente(int idx) {
		
		return getFieldValue("REMARK_ACECLN"+idx);
	}
	public String getConsumo(int idx) {
		
		return getFieldValue("REMARK_CO2"+idx);
	}
	public String getFormaPagoFee(int idx) {
		
		return getFieldValue("REMARK_FFC"+idx);
	}
	public String getCentroCosto(int idx) {
		
		return getFieldValue("REMARK_CC"+idx);
	}
	public String getSolicitante(int idx) {
		
		return getFieldValue("REMARK_RQ"+idx);
	}
	public String getAitan() {
		
		return getFieldValue("AITAN");
	}	
	public String getDepartamento() {
		
		return getFieldValue("REMARK_DP");
	}	
	
	public String getSucursal() {
		
		return getFieldValue("REMARK_SC");
	}	
	
	
	private void processTicketAdditionalInfo(String line, int idx) {
		int i = 3;

		JStringTokenizer tok2 = JCollectionFactory.createStringTokenizer(line,
				';');
		tok2.skipEmptyTokens(false);

		tok2.nextToken();
		tok2.nextToken();
		String id = tok2.nextToken();
		if (id == null)
		      id ="";
		addFieldValue(TMC_ID, id);
	
		
		addFieldValue(TMC_TICKET_TYPE + idx + id, line.substring(i, i += 1));
		
		String airline = line.substring(i, i += 3);
		addFieldValue(TMC_AIRLINE_NUMBER + idx + id, airline);

		String ticket = line.substring(i + 1, i += 11);
		addFieldValue(TMC_TICKET_NUMBER + idx + id, ticket );
		
		EMD e = new EMD();
		e.ticket = ticket;
		e.airline = airline;
		e.name = this.getFieldValue(NAME + idx);
		e.type =this.getFieldValue(TYPE + idx );
		
		currTicket = id;
		
		mEMDs.addElement(id, e);
		

	}

	private void processOriginalTicket(String line, int idx) {
		int i = 2;
		// addFieldValue(ORIGINAL_TICKET_TYPE + idx, line.substring(i, i += 1));
		addFieldValue(ORIGINAL_AIRLINE_NUMBER + idx, line.substring(i, i += 3));
		addFieldValue(ORIGINAL_TICKET_NUMBER + idx,
				line.substring(i + 1, i += 11));
	}

	private void processLineInfo(String line, int idx) {
	}

	private void processAirlineInfo(String line, int idx) {
		int i = 2;
		line = line.substring(i);
		JStringTokenizer tok = JCollectionFactory.createStringTokenizer(line,
				AmadeusRecord.FIELD_SEPARATOR);
		tok.skipEmptyTokens(false);

		String aux = tok.nextToken();
		if (aux.startsWith("*") && aux.length() > 3)
			aux = aux.substring(3);
		addFieldValue(AIRLINE_CODE + idx, aux);
	}

	private void processAirlineInfoFOI(String line, int idx) {
		int i = 3;
		line = line.substring(i).trim();

		addFieldValue(AIRLINE_CODE + idx, line.substring(0, 2));
	}

	public String getAirlineCode(int idx) {
		return getFieldValue(AIRLINE_CODE + idx);
	}
	
	private void processTourCode(String line, int idx) {
		int i = 2;
		line = line.substring(i);
		addFieldValue(TOUR_CODE + idx, line);
	}

	
	public String getTourCode(int idx) {
		return getFieldValue(TOUR_CODE + idx);
	}

	
	
	private void processEMDPayInfo(String line, int idx) throws Exception {

		int i = 3;
		line = line.substring(i);

		EMD currEMD = mEMDs.getElement(currTicket);
		if (currEMD==null) return;
		
		currEMD.cardtype = null;

		int mas = line.indexOf("+");
		if (mas > 0)
			line = line.substring(mas + 1);
		if (line.startsWith("/"))
			line = line.substring(1);

		if (line.startsWith("CA") || line.startsWith("MS")) { // CASH payment
			JStringTokenizer tok = JCollectionFactory.createStringTokenizer(
					line, '+');
			tok.skipEmptyTokens(false);

			if (tok.hasMoreTokens())
				tok.nextToken();
			if (tok.hasMoreTokens())
				line = tok.nextToken();

		}
		if (line.startsWith("CC") && line.length()>6) { // Credit Card Payment
			i = 0;
			currEMD.cardtype = line.substring(2, i += 4);
			currEMD.cardnumber = getCardNumber(line.substring(i));

			JStringTokenizer tok = JCollectionFactory.createStringTokenizer(
					line, '/');
			tok.skipEmptyTokens(false);
			tok.nextToken();
			tok.nextToken();
			String curram = tok.nextToken();
			i = 0;
			if (curram != null) {
				if (curram.startsWith("N"))  {
					int pos = curram.indexOf(";");
					currEMD.auth= (pos==-1?curram.substring(1):curram.substring(1,pos));
				}	else {
					currEMD.currency = curram.substring(0, i += 3);
					String fare = getFareAmount(i, curram);
					if (fare!=null){
						if (JTools.isNumber(fare)) {
							addFieldValue(FARE_AMOUNT + idx, fare);
						} else
							addFieldValue(FARE_AMOUNT + idx, "0");
					}
					curram = tok.nextToken();
					if (curram!=null && curram.startsWith("N")) {
						int pos = curram.indexOf(";");
						addFieldValue(FARE_AUTH + idx, pos==-1?curram.substring(1):curram.substring(1,pos));
					}
				}
			}
		} else if (line.startsWith("O/")) { // Credit Card Payment
			if (line.substring(2).startsWith("CASH"))
				currEMD.cardtype = null;
			else
				PssLogger.logDebug("No implementado Amadeus Pagos: " + line);
		} else {
			PssLogger.logDebug("No implementado Amadeus Pagos DESCONOCIDO");
		}

	}
	
	
	private void processPayInfo(String line, int idx) throws Exception {

		int i = 2;
		line = line.substring(i);

		addFieldValue(FARE_PAYFORM + idx, "CASH");

		int mas = line.indexOf("+");
		if (mas > 0)
			line = line.substring(mas + 1);
		if (line.startsWith("/"))
			line = line.substring(1);

		if (line.startsWith("CA") || line.startsWith("MS")) { // CASH payment
			JStringTokenizer tok = JCollectionFactory.createStringTokenizer(
					line, '+');
			tok.skipEmptyTokens(false);

			if (tok.hasMoreTokens())
				tok.nextToken();
			if (tok.hasMoreTokens())
				line = tok.nextToken();

		}
		if (line.startsWith("CC") && line.length()>6) { // Credit Card Payment
			i = 0;
			addFieldValue(FARE_PAYFORM + idx, line.substring(2, i += 4));
			addFieldValue(FARE_CARDNUMBER + idx,
					getCardNumber(line.substring(i)));

			JStringTokenizer tok = JCollectionFactory.createStringTokenizer(
					line, '/');
			tok.skipEmptyTokens(false);
			tok.nextToken();
			tok.nextToken();
			String curram = tok.nextToken();
			i = 0;
			if (curram != null) {
				if (curram.startsWith("N"))  {
					int pos = curram.indexOf(";");
					addFieldValue(FARE_AUTH + idx, pos==-1?curram.substring(1):curram.substring(1,pos));
				}	else {
					addFieldValue(FARE_CURRENCY + idx, curram.substring(0, i += 3));
					String fare = getFareAmount(i, curram);
					if (getFieldValue(FARE_CURRENCY + idx).equals("ARS")) {
						if (JTools.isNumber(fare)) {
							addFieldValue(FARE_AMOUNT + idx, fare);
						} else
							addFieldValue(FARE_AMOUNT + idx, "0");
					}
					curram = tok.nextToken();
					if (curram!=null && curram.startsWith("N")) {
						int pos = curram.indexOf(";");
						addFieldValue(FARE_AUTH + idx, pos==-1?curram.substring(1):curram.substring(1,pos));
					}
				}
			}
		} else if (line.startsWith("O/")) { // Credit Card Payment
			if (line.substring(2).startsWith("CASH"))
				addFieldValue(FARE_PAYFORM + idx, "CASH");
			else
				PssLogger.logDebug("No implementado Amadeus Pagos: " + line);
		} else {
			PssLogger.logDebug("No implementado Amadeus Pagos DESCONOCIDO");
		}

	}

	private String getFareAmount(int i, String curram) {
		String fare = curram.substring(i);
		int idx2 = fare.indexOf(";");
		if (idx2 > 0)
			fare = fare.substring(0, idx2);
		return fare;
	}

	public String getFormOfPaymentDesc(int idx) throws Exception {
		return this.getFieldValue(FARE_PAYFORM + idx);
	}

	public String getCreditCardNumber(int idx) throws Exception {
		return this.getFieldValue(FARE_CARDNUMBER + idx);
	}

	public String getCreditCardAuth(int idx) throws Exception {
		return this.getFieldValue(FARE_AUTH + idx);
	}
	public String getAmountPaid(int idx) throws Exception {
		return this.getFieldValueAsNumber(FARE_AMOUNT + idx);
	}

	private String getCardNumber(String text) {
		if (text.length()<10) return "";
		StringBuffer aux = new StringBuffer();
		int i = 0;
		while (true) {
			if (text.charAt(i) < '0' || text.charAt(i) > '9')
				break;
			aux.append(text.charAt(i++));
		}
		return aux.toString();
	}

	private void processComissionInfo(String line, int idx) throws Exception {
		int i = 0;
		i = line.indexOf("*");
		if (i < 0)
			return;
		line = line.substring(i + 1);
		i = line.indexOf("*");
		if (i < 0)
			return;
		line = line.substring(i + 1);
		i = line.indexOf("A");
		if (i > 0) {
			addFieldValue(COMISSION_AMOUNT + idx, line.substring(0, i));
		} else {
			int idot = line.indexOf(";");
			if (idot < 0)
				idot = line.length();
			addFieldValue(COMISSION_PERCENTAJE + idx,
					Tools.getOnlyNumbers(line.substring(0, idot)));
		}
	}

	public String getCommissionPercentaje(int idx) {
		return getFieldValueAsNumber(COMISSION_PERCENTAJE + idx);
	}

	public String getCommissionAmount(int idx) {
		return getFieldValueAsNumber(COMISSION_AMOUNT + idx);
	}

	private void processTicketInfo(String line, int idx) {
		int i = 2;
		if (line.length()<10)
			return;
		addFieldValue(TICKET_TYPE + idx, line.substring(i, i += 1));
		addFieldValue(AIRLINE_NUMBER + idx, line.substring(i, i += 3));
		addFieldValue(TICKET_NUMBER + idx, line.substring(i + 1, i += 11));

	}

	public boolean isEMD(int idx) {
		String emd = getFieldValue(TMC_TICKET_NUMBER + idx);
		if (emd != null)
			return true;

		return false;
	}

	public String getTicketNumber(int idx) {
		String emd = getFieldValue(TMC_TICKET_NUMBER + idx);
		if (emd != null)
			return emd;

		return getFieldValue(TICKET_NUMBER + idx);
	}

	public String getOriginalTicketNumber(int idx) {
		return getFieldValue(ORIGINAL_TICKET_NUMBER + idx);
	}

	public String getTicketType(int idx) {

		if (getFieldValue(TMC_TICKET_TYPE + idx) != null) {
			return getFieldValue(TMC_TICKET_TYPE + idx);
		}
		return getFieldValue(TICKET_TYPE + idx);
	}

}
