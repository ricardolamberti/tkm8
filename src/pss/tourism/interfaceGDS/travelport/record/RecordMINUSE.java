package pss.tourism.interfaceGDS.travelport.record;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;

public class RecordMINUSE extends TravelPortRecord {

	private String EMDID = "EMDID";
	private String CARRIER = "CARRIER";
	private String AMOUNT = "AMOUNT";
	private String CURRENCY = "CURRENCY";
	private String TAX = "TAX";
	private String TAX_CURRENCY = "TAX_CURRENCY";
	private String TOTAL_AMOUNT = "TOTAL_AMOUNT";
	private String TOTAL_CURRENCY = "TOTAL_CURRENCY";
	private String FP = "FP";
	private String CARD_TYPE = "CARD_TYPE";
	private String CARD_NUMBER = "CARD_NUMBER";
	private String FP_AMOUNT = "FP_AMOUNT";

	public RecordMINUSE() {
		ID = MINUSERECORD;
	}

	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {

		int i = 0;

		if (line.substring(i += 2).length() < 2)
			return line;

		JStringTokenizer tok = JCollectionFactory.createStringTokenizer(line.substring(i), TravelPortRecord.FIELD_SEPARATOR);
		tok.skipEmptyTokens(false);

		String cur;
		String amo;

		String value = "";
		while (true) {
		     value = tok.nextToken();
		     if (value==null) break;
			if (value.startsWith("EMD"))
				addFieldValue(EMDID, value.substring(7));
			else if (value.startsWith("VC"))
				addFieldValue(CARRIER, value.substring(3, 5));
			else if (value.startsWith("AMT")) {
				cur = value.substring(4, 7);
				amo = value.substring(7);
				addFieldValue(CURRENCY, cur);
				addFieldValue(AMOUNT, amo);
			} else if (value.startsWith("TX1")) {
				cur = value.substring(4, 7);
				amo = value.substring(7);
				addFieldValue(TAX_CURRENCY, cur);
				addFieldValue(TAX, amo);
			} else if (value.startsWith("TL")) {
				cur = value.substring(3, 6);
				amo = value.substring(6);
				addFieldValue(TOTAL_CURRENCY, cur);
				addFieldValue(TOTAL_AMOUNT, amo);
			} else if (value.startsWith("FP1")) {
				String type = value.substring(4, 6);
				addFieldValue(FP, type);
				if (type.equals("CC")) {
					addFieldValue(CARD_TYPE, value.substring(6,8));
					addFieldValue(CARD_NUMBER, value.substring(8,value.indexOf("EXP")));
				}
			}

		}
		return line;
	}
	public String getCardNumber() {
		return getFieldValue(CARD_NUMBER);
	}

	public String getCardType() {
		return getFieldValue(CARD_TYPE);
	}

	public String getFP() {
		return getFieldValue(FP);
	}
	
	
	public String getTotalAmount() {
		return getFieldValue(TOTAL_AMOUNT);
	}

	public String getTotalCurrency() {
		return getFieldValue(TOTAL_CURRENCY);
	}

	public String getTaxAmount() {
		if (getFieldValue(TAX)==null) return "0"	;
		return getFieldValue(TAX);
	}

	public String getTaxCurrency() {
		return getFieldValue(TAX_CURRENCY);
	}

	public String getAmount() {
		
		return getFieldValue(AMOUNT);
	}

	public String getCurrency() {
		return getFieldValue(CURRENCY);
	}

	public String getCarrier() {
		return getFieldValue(CARRIER);
	}

	public String getEMDID() {
		return getFieldValue(EMDID);
	}

}
