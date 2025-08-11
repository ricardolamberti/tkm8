package pss.tourism.interfaceGDS.travelport.record;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;

public class Record9 extends TravelPortRecord {

	private String CARRIERCODE = "CARRIERCODE";
	private String CARRIERNUMBER = "CARRIERNUMBER";
	private String SALESINDICATOR = "SALESINDICATOR";
	private String INTERNATIONAL = "INTERNATIONAL";

	public Record9() {
		ID = NINE;
	}
	

	public String doProcess(JMap<String, Object> mRecords, String line)
			throws Exception {

		int i = 0;

		if (line.substring(i += 2).length() < 2)
			return line;

		JStringTokenizer tok = JCollectionFactory.createStringTokenizer(line
				.substring(i), TravelPortRecord.FIELD_SEPARATOR);
		tok.skipEmptyTokens(false);

		String carrier = tok.nextToken();
		String carriercode="",carriernumber="";
		int idx = carrier.indexOf("/");
		if ( idx < 0 ) 
			carriercode = carrier.substring(2);
		else {
		  carriercode   = carrier.substring(2,idx);
		  carriernumber = carrier.substring(idx+1); 
		}
		addFieldValue(CARRIERCODE, carriercode );
		addFieldValue(CARRIERNUMBER, carriernumber );
		
		if (tok.hasMoreTokens()==false) return "";
		String stok = tok.nextToken();
		addFieldValue(SALESINDICATOR, stok.substring(2,2+4) );
		 stok = tok.nextToken();
		addFieldValue(INTERNATIONAL, stok );
		
		return line;
		
	}

	public boolean isInternational() {
		if (getFieldValue(INTERNATIONAL)==null) return false;
		if (getFieldValue(INTERNATIONAL).equals("I-X"))
			return true;
		else
			return false;
	}

	public String getAirlineCode() {
		return getFieldValue(CARRIERCODE);
	}

	public String getSalesIndicator() {
		return getFieldValue(SALESINDICATOR);
	}


}
