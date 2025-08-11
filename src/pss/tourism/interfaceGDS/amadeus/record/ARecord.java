package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;

public class ARecord extends AmadeusRecord {
	
		private String AIRLINE_DESCRIPTION="AIRLINE_DESCRIPTION";
		private String AIRLINE_CODE="AIRLINE_CODE";
		private String AIRLINE_FLIGHT="AIRLINE_FLIGHT";

		public ARecord() {
			ID="A-";
		}
		
		// ==========================================================================
		// A Record 
		// ==========================================================================
		public String doProcess(JMap<String,Object>mRecords, String line, long year) throws Exception {

			int i=0;
			
			if ( line.substring(i+=2).length() < 2 ) return line;

			JStringTokenizer tok = JCollectionFactory.createStringTokenizer(line.substring(i), AmadeusRecord.FIELD_SEPARATOR);
			tok.skipEmptyTokens(false);
			
			String airline = tok.nextToken();

			addFieldValue(AIRLINE_DESCRIPTION, airline );
			
			String airflight = tok.nextToken();
			i = 0;
			addFieldValue(AIRLINE_CODE, airflight.substring(i,i+=2) );
			addFieldValue(AIRLINE_FLIGHT, airflight.substring(i) );
			
			
			return line;

			
		}


}
