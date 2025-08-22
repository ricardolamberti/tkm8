package pss.tourism.interfaceGDS.travelport.record;

import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;

public class RecordG extends TravelPortRecord {
	
	protected String TOTAL_FARE_CURRENCY="TOTAL_FARE_CURRENCY";
	protected String TOTAL_FARE_AMOUNT="TOTAL_FARE_AMOUNT";
	protected String FARE_CURRENCY="FARE_CURRENCY";
	protected String FARE_AMOUNT="FARE_AMOUNT";
	protected String BASE_FARE_AMOUNT="BASE_FARE_AMOUNT";
	protected String BASE_FARE_CURRENCY="BASE_FARE_CURRENCY";
	protected String XT_TAX="XT_TAX";
	protected String TAX2="TAX2";
	protected String TAX3="TAX3";
	protected String XT_TAX_CODE="XT_TAX_CODE";
	protected String TAX2_CODE="TAX2_CODE";
	protected String TAX3_CODE="TAX3_CODE";

	protected String FARES = "FARES";
	protected String FAREID = "FAREID";
	protected String FARERONDA= "FARERONDA";
	boolean isEven=false;
	public boolean isEven() {
		return isEven;
	}

	public RecordG() {
		ID = GRECORD;
	}

	public String doProcess(JMap<String, Object> mRecords, String line)
			throws Exception {

		int i = 0;

		if (line.substring(i += 1).length() < 2)
			return line;
		
		// A\1.1\GUERRA/LEONARDO\ADT\*\*\9010738598599\E-
		JStringTokenizer tok = JCollectionFactory.createStringTokenizer(line
				.substring(i), TravelPortRecord.FIELD_SEPARATOR);
		tok.skipEmptyTokens(false);
		
		if ( tok.hasMoreTokens() == false ) return "";
		String passid = tok.nextToken();
		
		if ( passid.equals("*" ) ) return "";
	
		int fares = 1;//Integer.parseInt(getFieldValueAsNumber(FARES))+1;
		setFieldValue(FARES, fares+"");

		String curr = passid.substring(0, 3);
		passid = passid.substring(3);
		
		long passfareid = (long)Double.parseDouble(passid);

		setFieldValue(FARERONDA+fares, getRonda()+"");

		addFieldValue(FAREID+fares,passfareid);
		addFieldValue(BASE_FARE_CURRENCY+fares,curr );
		addFieldValue(BASE_FARE_AMOUNT+fares, passid );

		String tax = tok.nextToken();
		addFieldValue(XT_TAX_CODE+fares, tax.substring(1, 3) );
		addFieldValue(XT_TAX+fares, tax.substring(3) );
		
		tax = tok.nextToken();
		addFieldValue(TAX2_CODE+fares, tax.substring(1, 3) );
		addFieldValue(TAX2+fares, tax.substring(3) );
		
		tax = tok.nextToken();
		addFieldValue(TAX3_CODE+fares, tax.substring(1, 3) );
		addFieldValue(TAX3+fares, tax.substring(3) );
		
		if ( tok.hasMoreTokens() == false ) return "";
		String tot = tok.nextToken();
		addFieldValue(TOTAL_FARE_CURRENCY+fares, tot.substring(0,3) );
		addFieldValue(TOTAL_FARE_AMOUNT+fares, tot.substring(3,11) );

		if ( tok.hasMoreTokens() == false ) return "";
		String eq = tok.nextToken();
	  if ( eq.startsWith("*") ) { 
			addFieldValue(FARE_CURRENCY+fares, getFieldValue(BASE_FARE_CURRENCY+fares) );
			addFieldValue(FARE_AMOUNT+fares, getFieldValue(BASE_FARE_AMOUNT+fares) );
	  	return "";
	  }
		addFieldValue(FARE_CURRENCY+fares, eq.substring(0,3) );
		addFieldValue(FARE_AMOUNT+fares, eq.substring(3,11) );
		
		return line;
		
	}

	public int getNumberOfFares() {
		return Integer.parseInt(getFieldValueAsNumber(FARES));
	}
	
	public String getFareId(int i) {
		return getFieldValue(FAREID+i);
	}
	
	public String getFare( int i) {
		return getFieldValueAsNumber(FARE_AMOUNT+i);
	}
	public int getFareRonda( int i) {
		return Integer.parseInt(getFieldValueAsNumber(FARERONDA+i));
	}
	
	public String getTaxXTCode(int i) {
		return getFieldValue(XT_TAX_CODE+i);
	}

	public String getTaxXT(int i) {
		return getFieldValue(XT_TAX+i);
	}

	
	public String getTax2Code(int i) {
		return getFieldValue(TAX2_CODE+i);
	}
	public String getTax2(int i) {
		return getFieldValueAsNumber(TAX2+i);
	}
	public String getTax3(int i) {
		return getFieldValueAsNumber(TAX3+i);
	}
	
	public String getTax3Code(int i) {
		return getFieldValue(TAX3_CODE+i);
	}
	

	public String getBaseFare(int i) {
		return getFieldValueAsNumber(BASE_FARE_AMOUNT+i);
	}
	
	public String getTotalFare(int i ) {
		return getFieldValueAsNumber(TOTAL_FARE_AMOUNT+i);
	}

	public String getFareCurreny(int i ) {
		return getFieldValue(FARE_CURRENCY+i);
	}

	public String getBaseFareCurreny(int i ) {
		return getFieldValue(BASE_FARE_CURRENCY+i);
	}

	public String getCurrencyTotalFare(int i ) {
		String curr = getFieldValue(TOTAL_FARE_CURRENCY+i);
		if ( curr==null ) curr="MXN";
		return curr;
	
	}

	public String getCurrencyExchange(int i ) {
		Double ba = Double.parseDouble(getFieldValueAsNumber(BASE_FARE_AMOUNT+i));
		Double fa = Double.parseDouble(getFieldValueAsNumber(FARE_AMOUNT+i));
		if (ba==0) return "1";
		Double ex = fa/ba;
		return JTools.rd(ex,2)+"";
	}
	



}
