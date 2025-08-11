package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.collections.JMap;

public class ICWRecord extends AmadeusRecord {
	
		public ICWRecord() {
			ID="ICW";
		}
		
		// ==========================================================================
		// A Record 
		// ==========================================================================
		public String doProcess(JMap<String,Object>mRecords, String line) throws Exception {

			return line;
		}


}
