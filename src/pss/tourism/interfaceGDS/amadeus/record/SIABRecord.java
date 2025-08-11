package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.collections.JMap;

public class SIABRecord extends AmadeusRecord {
	
		public SIABRecord() {
			ID="SIAB";
		}
		
		// ==========================================================================
		// A Record 
		// ==========================================================================
		public String doProcess(JMap<String,Object>mRecords, String line) throws Exception {

			return line;
		}


}
