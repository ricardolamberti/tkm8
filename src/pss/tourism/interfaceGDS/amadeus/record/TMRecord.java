package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.collections.JMap;

public class TMRecord extends AmadeusRecord {
	
		public TMRecord() {
			ID="TM";
		}
		
		// ==========================================================================
		// A Record 
		// ==========================================================================
		public String doProcess(JMap<String,Object>mRecords, String line) throws Exception {

			return line;
		}


}
