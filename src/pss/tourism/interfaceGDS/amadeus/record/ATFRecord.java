package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.collections.JMap;

public class ATFRecord extends AmadeusRecord {
	
		public ATFRecord() {
			ID="ATF-";
		}
		
		// ==========================================================================
		// A Record 
		// ==========================================================================
		public String doProcess(JMap<String,Object>mRecords, String line, long year) throws Exception {

			return line;
		}


}
