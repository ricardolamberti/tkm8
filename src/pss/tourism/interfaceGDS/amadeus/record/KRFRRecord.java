package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.collections.JMap;

public class KRFRRecord extends AmadeusRecord {
	
		public KRFRRecord() {
			ID="KRFR";
		}
		
		// ==========================================================================
		// A Record 
		// ==========================================================================
		public String doProcess(JMap<String,Object>mRecords, String line) throws Exception {

			return line;
		}


}
