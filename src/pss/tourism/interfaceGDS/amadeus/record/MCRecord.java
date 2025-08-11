package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.collections.JMap;

public class MCRecord extends AmadeusRecord {
	
		public MCRecord() {
			ID="MC";
		}
		
		// ==========================================================================
		// A Record 
		// ==========================================================================
		public String doProcess(JMap<String,Object>mRecords, String line) throws Exception {

			return line;
		}


}
