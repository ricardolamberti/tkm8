package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.collections.JMap;

public class KRFYRecord extends AmadeusRecord {
	
		public KRFYRecord() {
			ID="KRFY";
		}
		
		// ==========================================================================
		// A Record 
		// ==========================================================================
		public String doProcess(JMap<String,Object>mRecords, String line) throws Exception {

			return line;
		}


}
