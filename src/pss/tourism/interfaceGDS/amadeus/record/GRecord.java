package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;

public class GRecord extends AmadeusRecord {
	
		public static final String INTERNATIONAL="INTERNATIONAL";
		public static final String INDICADORVENTA="INDICADORVENTA";

		public GRecord() {
			ID=G;
		}
		
		public String doProcess(JMap<String,Object>mRecords, String line) throws Exception {
			int i=0;
			if ( line.substring(i+=2).length() < 6 ) return line;
			
			JStringTokenizer tok = JCollectionFactory.createStringTokenizer(line.substring(i),AmadeusRecord.FIELD_SEPARATOR);
			tok.skipEmptyTokens(false);
			
			String isinter = tok.nextToken().trim(); 
			tok.nextToken(); //spare1
			tok.nextToken(); //spare2
			String iventa = tok.nextToken();
			
			if ( isinter.equals("X") )
				isinter="1";
			else 
				isinter="0";
			
			addFieldValue(INTERNATIONAL, isinter );
			addFieldValue(INDICADORVENTA, iventa );
			
			return line.substring(i);
		}
		
		public boolean isInternational() {
			if (getFieldValue(INTERNATIONAL).equals("1")) return true;
			return false;
		}
		
		public String getIndicadorVenta() {
			return getFieldValue(INDICADORVENTA);
		}



}
