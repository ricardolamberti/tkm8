package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.collections.JMap;

public class KSTIRecord extends AmadeusRecord {
	
		public KSTIRecord() {
			ID="KSTI";
		}
		
		// ==========================================================================
		// A Record 
		// ==========================================================================
		public String doProcess(JMap<String,Object>mRecords, String line) throws Exception {

			return line;
		}


}
