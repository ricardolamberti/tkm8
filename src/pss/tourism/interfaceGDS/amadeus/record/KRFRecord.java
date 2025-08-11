package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.collections.JMap;

public class KRFRecord extends AmadeusRecord {
	
		public KRFRecord() {
			ID="KRF";
		}
		
		// ==========================================================================
		// A Record 
		// ==========================================================================
		public String doProcess(JMap<String,Object>mRecords, String line) throws Exception {

			return line;
		}


}
