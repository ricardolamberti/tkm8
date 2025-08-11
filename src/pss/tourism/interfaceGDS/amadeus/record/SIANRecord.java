package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.collections.JMap;

public class SIANRecord extends AmadeusRecord {
	
		public SIANRecord() {
			ID="SIAN";
		}
		
		// ==========================================================================
		// A Record 
		// ==========================================================================
		public String doProcess(JMap<String,Object>mRecords, String line) throws Exception {

			return line;
		}


}
