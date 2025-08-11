package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.collections.JMap;

public class XRecord extends AmadeusRecord {
	
		public XRecord() {
			ID="X-";
		}
		
		// ==========================================================================
		// A Record 
		// ==========================================================================
		public String doProcess(JMap<String,Object>mRecords, String line) throws Exception {

			return line;
		}


}
