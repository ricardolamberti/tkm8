package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.collections.JMap;

public class SIAIRecord extends AmadeusRecord {
	
		public SIAIRecord() {
			ID="SIAI";
		}
		
		// ==========================================================================
		// A Record 
		// ==========================================================================
		public String doProcess(JMap<String,Object>mRecords, String line) throws Exception {

			return line;
		}


}
