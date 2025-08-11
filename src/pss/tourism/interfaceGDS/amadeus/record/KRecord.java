package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;

public class KRecord extends AmadeusRecord {
	
	protected String ISSUE_TYPE="ISSUE_TYPE";
	private String CURRENCY="CURRENCY";
	private String CURRENCY_TOTALFARE="CURRENCY_TOTALFARE";
	private String CURRENCY_FARE="CURRENCY_FARE";
	private String BASE_FARE="BASE_FARE";
	private String FARE="FARE";
	private String CURRENCY_EXCHANGE="CURRENCY_CHANGE";
	private String TOTALFARE="TOTALFARE";
	private String BASE_FAREF="BASE_FAREF";
	protected String ISSUE_TYPEF="ISSUE_TYPEF";
	private String CURRENCYF="CURRENCYF";
	
	public KRecord() {
		ID = K;
	}
	
	public boolean isReIssued() {
		if (getFieldValue(ISSUE_TYPE)==null) return false;
		return getFieldValue(ISSUE_TYPE).equals("R") ||  getFieldValue(ISSUE_TYPE).equals("Y") ||  getFieldValue(ISSUE_TYPE).equals("W");
	}

	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {
		int i = 0;
		if (line.substring(i += ID.length()).length() < 6) {
			return null;
		}

		JStringTokenizer tok = JCollectionFactory.createStringTokenizer(line.substring(i), AmadeusRecord.FIELD_SEPARATOR);
		tok.skipEmptyTokens(false);

		String aux = tok.nextToken();
		if (aux.startsWith("F-")) {
			i+=2;
			addFieldValue(ISSUE_TYPEF,aux.substring(i,i+=1));
			addFieldValue(CURRENCYF, aux.substring(i, i += 3));
			addFieldValue(BASE_FAREF, aux.substring(i));

		} else {
			i = 0;
			addFieldValue(ISSUE_TYPE,aux.substring(i,i+=1));
			addFieldValue(CURRENCY, aux.substring(i, i += 3));
			addFieldValue(BASE_FARE, aux.substring(i));
		
		}
		aux = tok.nextToken();
		if (aux.length() > 0) {
			i = 0;
			addFieldValue(CURRENCY_FARE, aux.substring(i, i += 3));
			addFieldValue(FARE, aux.substring(i));
		} 
		tok.nextToken();
		tok.nextToken();
		tok.nextToken();
		tok.nextToken();
		tok.nextToken();
		tok.nextToken();
		tok.nextToken();
		tok.nextToken();
		tok.nextToken();
		tok.nextToken();
		aux= tok.nextToken();
		if (aux.length() > 0) {
			i = 0;
			addFieldValue(CURRENCY_TOTALFARE, aux.substring(i, i += 3));
			addFieldValue(TOTALFARE, aux.substring(i));
		}
		aux = tok.nextToken();
		if (aux.length() > 0) {
			addFieldValue(CURRENCY_EXCHANGE, aux);
		} else {
			addFieldValue(CURRENCY_EXCHANGE, "1");
		}


		return line;
	}
	
	public String getBaseFare() {
		String f = getFieldValueAsNumber(BASE_FARE);
		if (f.trim().equalsIgnoreCase("free")) return "0";
		return f;
	}
	public String getFare() {
		String f = getFieldValueAsNumber(FARE);
		return f;
	}
	public String getTotalFare() {
		return getFieldValueAsNumber(TOTALFARE);
	}
	public String getCurrencyTotalFare() {
		return getFieldValue(CURRENCY_TOTALFARE);
	}
	public String getCurrencyFare() {
		return getFieldValue(CURRENCY_FARE);
	}
	public String getCurrencyBaseFare() {
		return getFieldValue(CURRENCY);
	}
	public String getCurrencyExchange() {
		String f = getFieldValueAsNumber(CURRENCY_EXCHANGE);
		return f;
	}

}
