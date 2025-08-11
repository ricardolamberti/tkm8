package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.collections.JMap;

public class NRecord extends AmadeusRecord {
	
		public NRecord() {
			ID="N-";
		}
		
		// ==========================================================================
		// A Record 
		// ==========================================================================
		public String doProcess(JMap<String,Object>mRecords, String line) throws Exception {

			return line;
		}


}
