package pss.tourism.interfaceGDS.travelport.record;

import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;
import pss.tourism.interfaceGDS.Tools;

public class RecordX extends RecordG {

	boolean isEven=false;
	public boolean isEven() {
		return isEven;
	}

	public RecordX() {
		ID = XRECORD;
	}
	

	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {

		int i = 0;

		if (line.substring(i += 2).length() < 2)
			return line;

		// A\1.1\GUERRA/LEONARDO\ADT\*\*\9010738598599\E-
		JStringTokenizer tok = JCollectionFactory.createStringTokenizer(line.substring(i), TravelPortRecord.FIELD_SEPARATOR);
		tok.skipEmptyTokens(false);

		long passfareid = 0;
		String curr = "";
		String amount = "";
		String tax1="0";
		String tax1Code="";
		String tax2="0";
		String tax3="0";
		String tax2Code="";
		String tax3Code="";
		String baseCode="";
		String baseAmount="";
		
		String fareIndicator = tok.nextToken();
		if (fareIndicator!=null) {
			if (fareIndicator.equals("E"))
				isEven = true;
		}
		String taxval="";
		while (tok.hasMoreTokens()) {
			String stok = tok.nextToken();
			if (stok.startsWith("RFC-")) {
				baseCode = stok.substring(4);
			}
			if (stok.startsWith("RF-")) {
				baseAmount = stok.substring(3);
				passfareid = (long) Double.parseDouble(baseAmount);
			}
			if (stok.startsWith("TFC-"))
				curr = stok.substring(4);
			if (stok.startsWith("TF-"))
				amount = stok.substring(3);
			if (stok.startsWith("T1-")) {
				tax1Code = stok.substring(3,5);
				taxval= stok.substring(5);
				if (taxval.indexOf("PD" ) <0)
					tax1 = Tools.getOnlyNumbers(taxval);
			}
			if (stok.startsWith("T2-")) {
				tax2Code = stok.substring(3,5);
				taxval= stok.substring(5);
				if (taxval.indexOf("PD" ) <0)
					tax2 = Tools.getOnlyNumbers(taxval);
			}
			if (stok.startsWith("T3-")) {
				tax3Code = stok.substring(3,5);
				taxval= stok.substring(5);
				if (taxval.indexOf("PD" ) <0)
					tax3 = Tools.getOnlyNumbers(taxval);
			}
			
			
		}
		addFieldValue(TOTAL_FARE_CURRENCY, curr);
		if (amount.equals("NOADC"))
			amount = "0";
		addFieldValue(TOTAL_FARE_AMOUNT, amount);

		//String tax = tok.nextToken();
		addFieldValue(FARE_CURRENCY, curr );
		addFieldValue(FARE_AMOUNT, (Long.parseLong(amount)-Long.parseLong(tax1)-Long.parseLong(tax2)-Long.parseLong(tax3))+"" );

		addFieldValue(TAX2_CODE, tax1Code );
		addFieldValue(TAX2, tax1 );
		
		addFieldValue(TAX3_CODE, tax2 );
		addFieldValue(TAX3, tax2);

		addFieldValue(XT_TAX_CODE, tax3Code);
		addFieldValue(XT_TAX,  tax3);
		
		addFieldValue(BASE_FARE_CURRENCY,baseCode );
		addFieldValue(BASE_FARE_AMOUNT, baseAmount );
//
//		String tax = tok.nextToken();
//		addFieldValue(XT_TAX_CODE, tax.substring(1, 3) );
//		addFieldValue(XT_TAX, tax.substring(3) );
//		
//		tax = tok.nextToken();
//		addFieldValue(TAX2_CODE, tax.substring(1, 3) );
//		addFieldValue(TAX2, tax.substring(3) );
//		
//		tax = tok.nextToken();
//		addFieldValue(TAX3_CODE, tax.substring(1, 3) );
//		addFieldValue(TAX3, tax.substring(3) );
//		
//		if ( tok.hasMoreTokens() == false ) return "";
//		String tot = tok.nextToken();
//		addFieldValue(TOTAL_FARE_CURRENCY, tot.substring(0,3) );
//		addFieldValue(TOTAL_FARE_AMOUNT, tot.substring(3,11) );
//
//		if ( tok.hasMoreTokens() == false ) return "";
//		String eq = tok.nextToken();
//	  if ( eq.startsWith("*") ) { 
//			addFieldValue(FARE_CURRENCY, getFieldValue(BASE_FARE_CURRENCY) );
//			addFieldValue(FARE_AMOUNT, getFieldValue(BASE_FARE_AMOUNT) );
//	  	return "";
//	  }
//		addFieldValue(FARE_CURRENCY, eq.substring(0,3) );
//		addFieldValue(FARE_AMOUNT, eq.substring(3,11) );

		
		return line;

	}

	public String getCurrency() {
		return getFieldValue(FARE_CURRENCY);
	}

	public String getAmount() {
		return getFieldValue(FARE_AMOUNT);
	}

}
