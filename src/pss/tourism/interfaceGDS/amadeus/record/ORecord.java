package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.collections.JMap;

public class ORecord extends AmadeusRecord {
	
		public ORecord() {
			ID="O-";
		}
		
		// ==========================================================================
		// A Record 
		// ==========================================================================
		public String doProcess(JMap<String,Object>mRecords, String line) throws Exception {

			return line;
		}


}
