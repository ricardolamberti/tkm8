package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.collections.JMap;

public class SIAARecord extends AmadeusRecord {
	
		public SIAARecord() {
			ID="SIAA";
		}
		
		// ==========================================================================
		// A Record 
		// ==========================================================================
		public String doProcess(JMap<String,Object>mRecords, String line) throws Exception {

			return line;
		}


}
