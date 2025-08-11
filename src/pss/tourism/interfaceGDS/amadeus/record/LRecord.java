package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.collections.JMap;

public class LRecord extends AmadeusRecord {
	
		public LRecord() {
			ID="L-";
		}
		
		// ==========================================================================
		// A Record 
		// ==========================================================================
		public String doProcess(JMap<String,Object>mRecords, String line) throws Exception {

			return line;
		}


}
