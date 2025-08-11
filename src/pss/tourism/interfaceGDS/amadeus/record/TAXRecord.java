package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.collections.JMap;

public class TAXRecord extends AmadeusRecord {
	
		public TAXRecord() {
			ID="TAX-";
		}
		
		// ==========================================================================
		// A Record 
		// ==========================================================================
		public String doProcess(JMap<String,Object>mRecords, String line) throws Exception {

			return line;
		}


}
